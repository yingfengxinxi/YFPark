package cn.sdqingyun.smartpark.module.bus.job.contract;

import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOrderBillSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractExpireRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts.OwnerContactsDO;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractExpireRuleService;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractOrderBillService;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractService;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillCostTypeService;
import cn.sdqingyun.smartpark.module.bus.service.owner.OwnerService;
import cn.sdqingyun.smartpark.module.bus.service.ownerContacts.OwnerContactsService;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/6/24 17:31
 * 合同账单明细逾期定时任务
 */
@Component
@Slf4j
public class ContractBillTask {

    @Autowired
    private ContractOrderBillService contractOrderBillService;

    @Autowired
    private ContractService contractService;

    @Resource
    private NotifyMessageSendApi notifySendService;


    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerContactsService ownerContactsService;

    @Resource
    private OrgBillCostTypeService orgBillCostTypeService;
    /**
     * 定时任务名称名称:模块名称:业务名称
     * 缴费通知
     */
    @TenantIgnore
    @XxlJob("bus-server:paymentNotice")
    public void paymentNotice() {
        log.info("========================START执行合同账单明细缴费通知定时任务========================");
        try {
            List<ContractOrderBillDO> orderBillBeOverdueList = contractOrderBillService.getPaymentNoticeList();
            if (CollectionUtils.isNotEmpty(orderBillBeOverdueList)) {
                orderBillBeOverdueList.forEach(contractOrderBillDO -> {
                    try {
                        Long contractId = contractOrderBillDO.getContractId();
                        ContractDO contractDO = contractService.selectById(contractId);
                        //发送站内消息
                        Long ownerId = contractDO.getOwnerId();
                        OwnerDO ownerDO = ownerService.get(ownerId);
                        String ownerName = "";
                        String ownerPhone = "";
                        if (ownerDO != null) {
                            Long contactId = ownerDO.getContactId();
                            OwnerContactsDO contacts = ownerContactsService.getContacts(contactId);
                            ownerName = contacts.getName();
                            ownerPhone = contacts.getPhone();
                        }


                        Map<String, Object> templateParams = new HashMap<>();
                        //尊敬的{1}，本期账单最终应付日期为{2}，请及时支付避免逾期产生滞纳金，详情请点击 {3}
                        templateParams.put("1", ownerName);
                        templateParams.put("2", contractOrderBillDO.getPayDateStr());
                        templateParams.put("3", "访问地址");
                        NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                        notifySendSingleToUserReqDTO.setTemplateCode("PAYMENT_NOTICE");
                        notifySendSingleToUserReqDTO.setUserId(contractDO.getOwnerId());
                        notifySendSingleToUserReqDTO.setTemplateParams(templateParams);
                        notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("========================END执行合同账单明细缴费通知定时任务========================");
    }

    /**
     * 定时任务名称名称:模块名称:业务名称
     * 逾期
     */
    @TenantIgnore
    @XxlJob("bus-server:beOverdue")
    public void beOverdue() {
        log.info("========================START执行合同账单明细逾期定时任务========================");
        try {
            List<ContractOrderBillDO> orderBillBeOverdueList = contractOrderBillService.getBeOverdueList();
            if (CollectionUtils.isNotEmpty(orderBillBeOverdueList)) {
                orderBillBeOverdueList.forEach(contractOrderBillDO -> {
                    try {
                        Long contractId = contractOrderBillDO.getContractId();
                        ContractDO contractDO = contractService.selectById(contractId);
                        //滞纳金起算天数(逾期几天算起) 3
                        Integer startingLateFeeDay = contractOrderBillDO.getStartingLateFeeDay();
                        //计算预期天数
                        Long dayCount = DateUtils.getDayCount(contractOrderBillDO.getPayDate(), new Date()) + 1;
                        if (dayCount >= startingLateFeeDay) {
                            //计算违约金
                            //滞纳金比例 20%
                            String lateFeeRatio = contractOrderBillDO.getLateFeeRatio();
                            Double lateFeeRatioValue = Double.valueOf(lateFeeRatio) / 100;
                            BigDecimal beOverdueMoney = new BigDecimal(contractOrderBillDO.getReceivable()).multiply(new BigDecimal(lateFeeRatioValue)).multiply(new BigDecimal(dayCount));
                            //滞纳金上限% 300%
                            String upperLimitLateFee = contractOrderBillDO.getUpperLimitLateFee();
                            Double upperLimitLateFeeValue = Double.valueOf(upperLimitLateFee) / 100;
                            Double znjsxMoney = Double.valueOf(contractOrderBillDO.getReceivable()) / upperLimitLateFeeValue;
                            if (Double.valueOf(beOverdueMoney.toString()) > znjsxMoney) {
                                beOverdueMoney = new BigDecimal(znjsxMoney);
                            }

                            String overdueMoney = Tools.DecimalFormat(beOverdueMoney);
                            System.out.println("逾期天数:" + dayCount + "产生的滞纳金金额为:" + overdueMoney);
                            //向账单表中更新滞纳金和逾期天数
                            contractOrderBillService.updateByIdBillLateFeeOverdue(contractOrderBillDO.getId(), "2", overdueMoney, Integer.valueOf(String.valueOf(dayCount)));
                            contractDO.setIsBillOverdue("1");
                            contractService.updateById(contractDO);

                            //发送站内消息
                            Long ownerId = contractDO.getOwnerId();
                            OwnerDO ownerDO = ownerService.get(ownerId);
                            String ownerName = "";
                            String ownerPhone = "";
                            if (ownerDO != null) {
                               // Long contactId = ownerDO.getContactId();
                              //  OwnerContactsDO contacts = ownerContactsService.getContacts(contactId);
                                ownerName = ownerDO.getName();
                              //  ownerPhone = ownerDO.getTel();
                            }
                            Map<String, Object> templateParams = new HashMap<>();
                            //尊敬的{1}，合同编号为{2}的账单类型为{3}的账单第{4}期已逾期{5}天,滞纳金为{6}元，请及时缴费，点击查看详情：{6}
                            templateParams.put("1", ownerName);
                            templateParams.put("2", contractDO.getContractNumber());
                            String feeType = contractOrderBillDO.getFeeType();
                            OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeService.get(Long.valueOf(feeType));
                            templateParams.put("3",orgBillCostTypeDO.getName());
                            templateParams.put("4", contractOrderBillDO.getNumber());
                            templateParams.put("5", dayCount);
                            templateParams.put("6", overdueMoney);
                            templateParams.put("7", "访问地址");
                            NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                            notifySendSingleToUserReqDTO.setTemplateCode("BE_OVERDUE");
                            notifySendSingleToUserReqDTO.setUserId(contractDO.getOwnerId());
                            notifySendSingleToUserReqDTO.setTemplateParams(templateParams);
                            notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("========================END执行合同账单明细逾期定时任务========================");
    }
}
