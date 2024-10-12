package cn.sdqingyun.smartpark.module.bus.job.contract;

import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractExpireRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts.OwnerContactsDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractSelectedPropertieMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.service.bpm.ContractLeaveService;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractSelectedPropertieService;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractService;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractExpireRuleService;
import cn.sdqingyun.smartpark.module.bus.service.owner.OwnerService;
import cn.sdqingyun.smartpark.module.bus.service.ownerContacts.OwnerContactsService;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomService;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.sdqingyun.smartpark.module.system.api.sms.SmsSendApi;
import cn.sdqingyun.smartpark.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/6/24 17:31
 * 合同到期定时任务
 */
@Component
@Slf4j
public class ContractTask {

    @Autowired
    private ContractExpireRuleService contractExpireRuleService;

    @Autowired
    private ContractService contractService;

    @Resource
    private NotifyMessageSendApi notifySendService;


    @Resource
    private ContractSelectedPropertieService contractSelectedPropertieService;

    @Resource
    private SmsSendApi smsSendApi;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerContactsService ownerContactsService;

    @Autowired
    private ContractLeaveService contractLeaveService;

    /**
     * 定时任务名称名称:模块名称:业务名称
     */
    @TenantIgnore
    @XxlJob("bus-server:contractUpdateStatus")
    public void contractUpdateStatus() {
        log.info("========================START执行合同状态为执行中定时任务========================");
        try {
            List<ContractDO> list = contractService.getPendingExecution();
            if (CollectionUtils.isNotEmpty(list)) {
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                list.forEach(contractDO -> {
                    Date contractStartTime = contractDO.getContractStartTime();
                    try {
                        Date parse = sim.parse(sim.format(new Date()));
                        Long dayCount = DateUtils.getDayCount(contractStartTime, parse);
                        if (dayCount == 0) {
                            contractDO.setStatus("1");
                            contractService.updateById(contractDO);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("========================END执行合同状态为执行中定时任务========================");
    }

    /**
     * 定时任务名称名称:模块名称:业务名称
     */
    @TenantIgnore
    @XxlJob("bus-server:contractApproachingExpire")
    public void contractApproachingExpire() {
        log.info("========================START执行合同即将到期定时任务========================");
        try {
            List<ContractExpireRuleDO> list = contractExpireRuleService.getExpireRuleList();
            if (CollectionUtils.isNotEmpty(list)) {
                list.forEach(contractExpireRuleDO -> {
                    String[] relationBuildArray = contractExpireRuleDO.getRelationBuilds().split(",");
                    for (String relationBuild : relationBuildArray) {
                        List<ContractDO> buildIdContractList = contractService.getByBuildIdContractList(Long.valueOf(relationBuild));
                        if (CollectionUtils.isNotEmpty(buildIdContractList)) {
                            buildIdContractList.forEach(contractDO -> {
                                Date contractEndTime = contractDO.getContractEndTime();
                                String dateJiaDay = DateUtils.getDateJDay(contractEndTime, contractExpireRuleDO.getReminderDay());
                                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    if (DateUtils.isToday(sim.parse(dateJiaDay))) {
                                        //发送消息
                                        try {
                                            getcontractApproachingExpireReminderMethod(contractExpireRuleDO, contractDO);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("========================END执行合同到期定时任务========================");
    }

    /**
     * 合同即将到期发送消息
     *
     * @param contractExpireRuleDO
     * @param contractDO
     */
    private void getcontractApproachingExpireReminderMethod(ContractExpireRuleDO contractExpireRuleDO, ContractDO contractDO) throws Exception {
        String[] reminderMethods = contractExpireRuleDO.getReminderMethod().split(",");
        for (String method : reminderMethods) {
            if (StringUtils.equals(method, "0")) {
                //站内消息
                Map<String, Object> map = new HashMap<>();
                map.put("contractNumber", contractDO.getContractNumber());
                Integer reminderDay = contractExpireRuleDO.getReminderDay();
                map.put("day", reminderDay);
                map.put("link", "链接");
                NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                notifySendSingleToUserReqDTO.setTemplateCode("SMS_468715011");
                notifySendSingleToUserReqDTO.setUserId(contractDO.getOwnerId());
                notifySendSingleToUserReqDTO.setTemplateParams(map);
                notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
            }
            if (StringUtils.equals(method, "1")) {
                //公众号消息
            }
            if (StringUtils.equals(method, "2")) {
                //短信消息
                //站内消息
                Long ownerId = contractDO.getOwnerId();
                OwnerDO ownerDO = ownerService.get(ownerId);
                Long contactId = ownerDO.getContactId();
                OwnerContactsDO contacts = ownerContactsService.getContacts(contactId);
                Map<String, Object> templateParams = new HashMap<>();
                templateParams.put("name", contacts.getName());
                SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO();
                smsSendSingleToUserReqDTO.setMobile(contacts.getPhone());
                smsSendSingleToUserReqDTO.setUserId(ownerId);
                smsSendSingleToUserReqDTO.setTemplateCode("SMS_467510404");
                smsSendSingleToUserReqDTO.setTemplateParams(templateParams);
                smsSendApi.sendSingleSmsToOwner(smsSendSingleToUserReqDTO);
            }

        }
    }


    /**
     * 定时任务名称名称:模块名称:业务名称
     */
    @TenantIgnore
    @XxlJob("bus-server:contractExpire")
    public void contractExpire() {
        log.info("========================START执行合同到期定时任务========================");
        try {
            List<ContractDO> buildIdContractList = contractService.getExpireContractList();
            if (CollectionUtils.isNotEmpty(buildIdContractList)) {
                buildIdContractList.forEach(contractDO -> {
                    //修改合同状态为已到期
                    contractDO.setStatus("15");
                    contractService.updateById(contractDO);
                    contractSelectedPropertieService.updateByContractIdStatus(contractDO.getId());
                    //修改房屋状态
                    contractLeaveService.updateOwnerAndRoomsForTerminatedOrCancelledContract(contractDO);

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("========================END执行合同到期定时任务========================");
    }
}
