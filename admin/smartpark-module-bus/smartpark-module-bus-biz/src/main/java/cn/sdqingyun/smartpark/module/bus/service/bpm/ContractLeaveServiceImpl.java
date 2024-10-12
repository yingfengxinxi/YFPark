package cn.sdqingyun.smartpark.module.bus.service.bpm;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeavePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeaveRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeaveSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractSaveVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bpm.ContractLeaveDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOperateLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillStreamMiddleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgContractRetreatDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bpm.ContractLeaveMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOperateLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillStreamMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillReceiptMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgContractRetreatMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgIncomeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.enums.ContractAuditStatusEnum;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.ContractAuditStatusUtils;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractSaveService;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillStreamMiddleService;
import cn.sdqingyun.smartpark.module.crm.enums.common.CrmAuditStatusEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.mzt.logapi.context.LogRecordContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.sdqingyun.smartpark.module.bus.dal.redis.RedisKeyConstants.CONTRACT_CHANGE;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.CONTRACT_LEAVE_NOT_EXISTS;
import static cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.ContractAuditStatusUtils.convertBpmResultToAuditStatus;

/**
 * 合同审批 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Slf4j
public class ContractLeaveServiceImpl implements ContractLeaveService {

    @Resource
    private ContractLeaveMapper contractLeaveMapper;

    @Resource
    private ContractMapper contractMapper;


    @Resource
    private OrgContractRetreatMapper contractRetreatMapper;


    @Resource
    private ContractSaveService contractSaveService;

    @Resource
    private OwnerMapper ownerMapper;

    @Resource
    private RoomMapper roomMapper;


    @Resource
    private BillStreamMapper billStreamMapper;

    @Resource
    private OrgBillStreamMiddleService billStreamMiddleService;

    @Resource
    private ContractOrderBillMapper contractOrderBillMapper;

    @Resource
    private OrgIncomeMapper orgIncomeMapper;

    @Resource
    private OrgBillReceiptMapper orgBillReceiptMapper;

    @Resource
    private ContractOperateLogMapper contractOperateLogMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContractLeave(ContractLeaveSaveReqVO createReqVO) {
        //获取当前登录人
        Long loginUserId = getLoginUserId();

        // 校验合同是否在审批
        LambdaQueryWrapperX<ContractLeaveDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(ContractLeaveDO::getContractId, createReqVO.getContractId())
                .in(ContractLeaveDO::getStatus, ContractAuditStatusEnum.PROCESS.getStatus());
        Long selectCount = contractLeaveMapper.selectCount(wrapperX);
        if (selectCount > 0) {
            throw new ServiceException(406, "合同正在审批中，请勿重复提交！");
        }

        //校验合同是否存在
        ContractDO contract = contractMapper.selectById(createReqVO.getContractId());
        if (ObjUtil.isNull(contract)) {
            throw new ServiceException(406, "未查询到相关合同，请检查后再提交审批！");
        }

        // 插入
        ContractLeaveDO contractLeave = BeanUtils.toBean(createReqVO, ContractLeaveDO.class);
        contractLeave.setStatus(ContractAuditStatusEnum.DRAFT.getStatus());
        contractLeave.setUserId(loginUserId);
        contractLeave.setContractNumber(contract.getContractNumber());
        contractLeave.setStartTime(LocalDateTime.now());
        contractLeaveMapper.insert(contractLeave);

        // 创建合同审批流程实例
        String processInstanceId = UuidUtils.generateUuid();

        // 更新合同工作流编号
        contractLeaveMapper.updateById(new ContractLeaveDO().setId(contractLeave.getId()).setProcessInstanceId(processInstanceId)
                .setStatus(CrmAuditStatusEnum.PROCESS.getStatus()).setStartTime(contractLeave.getStartTime()));

        // 记录日志
        LogRecordContext.putVariable("contractName", "合同编号" + contract.getContractNumber());

        // 返回
        return contractLeave.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateContractLeave(ContractLeaveSaveReqVO updateReqVO) {
        // 校验存在
        validateContractLeaveExists(updateReqVO.getId());
        // 更新
        ContractLeaveDO updateObj = BeanUtils.toBean(updateReqVO, ContractLeaveDO.class);
        contractLeaveMapper.updateById(updateObj);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteContractLeave(Long id) {
        // 校验存在
        validateContractLeaveExists(id);
        // 删除
        contractLeaveMapper.deleteById(id);
    }

    private void validateContractLeaveExists(Long id) {
        if (contractLeaveMapper.selectById(id) == null) {
            throw exception(CONTRACT_LEAVE_NOT_EXISTS);
        }
    }

    @Override
    public ContractLeaveDO getContractLeave(Long id) {
        return contractLeaveMapper.selectById(id);
    }

    @Override
    public PageResult<ContractLeaveDO> getContractLeavePage(ContractLeavePageReqVO pageReqVO) {
        return contractLeaveMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateContractAuditStatus(Long id, Integer status) throws ParseException {
        log.info("合同审批结果：{},合同审批id{}", JSONObject.toJSONString(status), id);
        // 1.1 校验合同是否存在
        ContractLeaveDO contract = contractLeaveMapper.selectById(id);
        if (contract == null || !CrmAuditStatusEnum.PROCESS.getStatus().equals(contract.getStatus())) {
            return;
        }

        // 2. 更新合同审批结果
        Integer auditStatus = convertBpmResultToAuditStatus(status);

        // 查询合同详细信息
        ContractDO contractDO = contractMapper.selectById(contract.getContractId());
        if (contractDO == null) {
            return;
        }

        log.info("--------------contractMapper.selectById：{}--------------------", JSONObject.toJSONString(contractDO));
        // 根据审核状态更新合同及相关信息

        if (auditStatus == 20) {
            //审批通过
            handleContractUpdate(contractDO);
        }

        //待完善
//        if (auditStatus == 20) {
//            //驳回
//            handleContractUpdate(contractDO);
//        }

        if (auditStatus == 30) {
            //不通过
            handleContractNotApprovedUpdate(contractDO);
        }

        // 更新合同审批记录
        contractLeaveMapper.updateById(
                new ContractLeaveDO().setId(id).setStatus(auditStatus).setEndTime(LocalDateTime.now())
        );
    }

    /**
     * 0=新建合同：填写完合同内容点击保存按钮
     * 16=新建待审核：填写完合同内容后，发起审批后的状态【0->16】
     * <p>
     * 1=正常执行中：审批完成后，在合同履行期内 【16->1】
     * 13=待执行：为到合同开始日期，签订好的合同【16->13】
     * <p>
     * 10=已驳回：退回协议 【16->10】【待完善】
     * 17=合同不通过：审批不通过的 【16->17】
     * <p>
     * 2=变更待审核：提交合同变更后，发起审核【1->2】
     * 3=变更待修改：变更审核同意后，未进行修改时，修改后继续执行为正常执行中，修改后退租、作废显示已退租、已作废；【2->3】  变更修改数据完成后【3-1】(后端修改合同接口时判断状态是否是3，是 的话修改状态为1)
     * <p>
     * 4=退租待审批：执行退租操作发起审批，未审批时；【1->4】
     * 5=退租待执行：审批通过后需要填写退租金额等，在这个操作期间；【4->6】
     * 6=已退租：退租操作完成后；
     * <p>
     * 14=续租待审批：合同发起续租待审批时(没有产生违约金可以续租)，【14->1/13】
     * <p>
     * 7=作废待审批：发起作废后，审批过程中；(没有产生违约金可以续租)【任意状态下都可以作废合同】
     * 8=作废待修改：作废审批通过后，未进行操作时；【7->8】【去掉此状态】
     * 11=已作废：作废审批通过后，进行了修改操作后；【8->11】
     * <p>
     * 15=已到期：合同执行期过期；【任何状态下都可以变到此状态】
     *
     * @param contractDO
     */

    private void handleContractUpdate(ContractDO contractDO) throws ParseException {
        log.info("--------------handleContractUpdate：{}--------------------", JSONObject.toJSONString(contractDO));
        //新建合同审核通过和续租审核通过
        String contractStatus = contractDO.getStatus();
        if (contractStatus.equals("14") || contractStatus.equals("16")) {
            System.out.println("=====START新建合同审核通过和续租审核通过修改状态=====");
            updateOwnerAndRoomsForNewContract(contractDO);
            // 1=正常执行中：审批完成后，在合同履行期内 【16->1】
            // 13=待执行：为到合同开始日期，签订好的合同【16->13】
            Long dayCount = DateUtils.getDayCount(contractDO.getContractStartTime(), new Date()) + 1;
            if (dayCount >= 1) {
                //正常执行中
                contractDO.setStatus("1");
            } else {
                //待执行
                contractDO.setStatus("13");
            }
            String[] roomNumbers = contractDO.getRoomNumber().split(",");
            for (String roomNumber : roomNumbers) {
                RoomDO roomDO = roomMapper.selectById(roomNumber);
                roomDO.setRoomStatus(30);
                roomMapper.updateById(roomDO);
            }
            System.out.println("=====END新建合同审核通过和续租审核通过修改状态=====");
        }
        //变更合同审核通过
        if (contractStatus.equals("2")) {
            System.out.println("=====START变更合同审核通过修改状态=====");
            updateOwnerAndRoomsForChangedContract(contractDO);
            contractDO.setStatus("3");
            //修改合同内容
            String redisKey = CONTRACT_CHANGE + contractDO.getParkId() + ":" + contractDO.getBuildId() + contractDO.getId();
            String json = redisTemplate.opsForValue().get(redisKey);

            ContractSaveVO contractSaveVO = getContractSaveVO(json);
            contractSaveService.update(contractSaveVO);
            redisTemplate.delete(redisKey);
            System.out.println("=====END变更合同审核通过修改状态=====");
        }
        //退租审批通过
        if (contractStatus.equals("4")) {
            System.out.println("=====START退租审批通过修改状态=====");
            //更新账单明细
            LambdaQueryWrapperX<OrgContractRetreatDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(OrgContractRetreatDO::getContractId, contractDO.getId());
            OrgContractRetreatDO orgContractRetreatDO = contractRetreatMapper.selectOne(queryWrapperX);
            String status = rentTerminationUpdateOrderBill(orgContractRetreatDO);
            contractDO.setStatus(status);
            contractDO.setLeaseRetreatTime(orgContractRetreatDO.getRetreatDate());
            System.out.println("=====START退租审批通过修改状态=====" + contractDO);
        }
        //作废审批通过
        if (contractStatus.equals("7")) {
            System.out.println("=====START作废审批通过修改状态=====");
            contractDO.setStatus("11");
            //作废
            toVoid(contractDO.getId());
            updateOwnerAndRoomsForTerminatedOrCancelledContract(contractDO);
            System.out.println("=====END作废审批通过修改状态=====");
        }
        contractMapper.updateById(contractDO);
    }

    @Nullable
    private static ContractSaveVO getContractSaveVO(String json) throws ParseException {
        JSONObject jsonObject = JSONObject.parseObject(json);

        String signingDate = jsonObject.get("signingDate").toString();
        String contractStartTime = jsonObject.get("contractStartTime").toString();
        String contractEndTime = jsonObject.get("contractEndTime").toString();
        jsonObject.put("signingDate", null);
        jsonObject.put("contractStartTime", null);
        jsonObject.put("contractEndTime", null);
        ContractSaveVO contractSaveVO = JSONObject.parseObject(jsonObject.toJSONString(), ContractSaveVO.class);

        if (signingDate.contains("AM")) {
            contractSaveVO.setSigningDate(DateUtils.getSimpleDateFormat(signingDate));
        }

        if (signingDate.contains("AM")) {
            contractSaveVO.setContractStartTime(DateUtils.getSimpleDateFormat(contractStartTime));
        }
        if (signingDate.contains("AM")) {
            contractSaveVO.setContractEndTime(DateUtils.getSimpleDateFormat(contractEndTime));
        }
        return contractSaveVO;
    }

    /**
     * 退租更新账单明细
     *
     * @param orgContractRetreatDO
     * @return
     */
    private String rentTerminationUpdateOrderBill(OrgContractRetreatDO orgContractRetreatDO) {

        //账单
        String billInfo = orgContractRetreatDO.getBillInfo();
        List<ContractOrderBillDO> contractOrderBillInfoList = JSONArray.parseArray(billInfo, ContractOrderBillDO.class);
        if (CollectionUtils.isNotEmpty(contractOrderBillInfoList)) {
            contractOrderBillInfoList.forEach(contractOrderBillDO -> {
                if (contractOrderBillDO.getId() == null) {
                    contractOrderBillDO.setBillStatus("1");
                    contractOrderBillDO.setDataSource("1");
                    contractOrderBillDO.setClauseType("1");
                    contractOrderBillDO.setCloseStatus("1");
                    contractOrderBillDO.setBillSource("1");
                    contractOrderBillDO.setOrderNumber(UuidUtils.generateUuid().replaceAll("-", ""));
                    contractOrderBillMapper.insert(contractOrderBillDO);
                } else {
                    contractOrderBillMapper.updateById(contractOrderBillDO);
                }

            });
        }
        //保证金
        String bondInfo = orgContractRetreatDO.getBondInfo();
        List<ContractOrderBillDO> contractOrderBillBondInfoList = JSONArray.parseArray(bondInfo, ContractOrderBillDO.class);
        if (CollectionUtils.isNotEmpty(contractOrderBillBondInfoList)) {
            contractOrderBillInfoList.forEach(contractOrderBillDO -> {

                contractOrderBillMapper.updateById(contractOrderBillDO);
            });
        }

        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(ContractOrderBillDO::getContractId, orgContractRetreatDO.getContractId());
        queryWrapperX1.in(ContractOrderBillDO::getBillStatus, Arrays.asList("0,2".split(",")));
        queryWrapperX1.eq(ContractOrderBillDO::getDataSource, "1");
        int size = contractOrderBillMapper.selectList(queryWrapperX1).size();
        if (size >= 1) {
            return "5";
        }
        return "6";
    }

    /**
     * 作废
     *
     * @param contractId
     */
    private void toVoid(Long contractId) {
        LambdaQueryWrapperX<ContractOperateLogDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(ContractOperateLogDO::getContractId, contractId);
        queryWrapperX1.eq(ContractOperateLogDO::getOperateType, "void");
        ContractOperateLogDO contractOperateLogDO = contractOperateLogMapper.selectOne(queryWrapperX1);
        String otherRemark = contractOperateLogDO.getOtherRemark();
        JSONObject jsonObject = JSONObject.parseObject(otherRemark);
        String isToVoidBill = jsonObject.getString("isToVoidBill");
        if (StringUtils.isNotEmpty(isToVoidBill)) {
            //作废账单

            //关闭合同账单
            contractOrderBillMapper.updateByContractIdCloseStatus(Long.valueOf(contractOperateLogDO.getContractId()), "0");
            //删除机构收入表
            LambdaQueryWrapperX<OrgIncomeDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(OrgIncomeDO::getContractId, Long.valueOf(contractOperateLogDO.getContractId()));
            orgIncomeMapper.delete(queryWrapperX);

            //作废收据
            orgBillReceiptMapper.updateByBillIdStatus(contractId, "5");
        }
        String isCloseFlow = jsonObject.getString("isCloseFlow");
        if (StringUtils.isNotEmpty(isCloseFlow)) {
            //关闭流水
            billStreamMapper.updateByContractIdIsClose(contractId, "0", "合同作废");
            //匹配状态改为已取消
            LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ContractOrderBillDO::getContractId, contractId);
            List<ContractOrderBillDO> contractOrderBillDOS = contractOrderBillMapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(contractOrderBillDOS)) {
                contractOrderBillDOS.forEach(contractOrderBillDO -> {
                    List<OrgBillStreamMiddleDO> billIdStreamMiddleList = billStreamMiddleService.getBillIdStreamMiddleList(contractOrderBillDO.getId());
                    if (CollectionUtils.isNotEmpty(billIdStreamMiddleList)) {
                        billIdStreamMiddleList.forEach(orgBillStreamMiddleDO -> {
                            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                billStreamMiddleService.cancelMatch(orgBillStreamMiddleDO.getId(), sim.format(new Date()));
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                });
            }
        }
    }

    /**
     * 0=新建合同：填写完合同内容点击保存按钮
     * 16=新建待审核：填写完合同内容后，发起审批后的状态【0->16】
     * <p>
     * 1=正常执行中：审批完成后，在合同履行期内 【16->1】
     * 13=待执行：为到合同开始日期，签订好的合同【16->13】
     * <p>
     * 10=已驳回：退回协议 【16->10】【待完善】
     * 17=合同不通过：审批不通过的 【16->17】
     * <p>
     * 2=变更待审核：提交合同变更后，发起审核【1->2】
     * 3=变更待修改：变更审核同意后，未进行修改时，修改后继续执行为正常执行中，修改后退租、作废显示已退租、已作废；【2->3】  变更修改数据完成后【3-1】(后端修改合同接口时判断状态是否是3，是 的话修改状态为1)
     * <p>
     * 4=退租待审批：执行退租操作发起审批，未审批时；【1->4】
     * 5=退租待执行：审批通过后需要填写退租金额等，在这个操作期间；【4->6】
     * 6=已退租：退租操作完成后；
     * <p>
     * 14=续租待审批：合同发起续租待审批时(没有产生违约金可以续租)，【14->1/13】
     * <p>
     * 7=作废待审批：发起作废后，审批过程中；(没有产生违约金可以续租)【任意状态下都可以作废合同】
     * 8=作废待修改：作废审批通过后，未进行操作时；【7->8】
     * 11=已作废：作废审批通过后，进行了修改操作后；【8->11】
     * <p>
     * 15=已到期：合同执行期过期；【任何状态下都可以变到此状态】
     *
     * @param contractDO
     */
    private void handleContractNotApprovedUpdate(ContractDO contractDO) {
        //新建合同审批不通过
        String contractStatus = contractDO.getStatus();
        if (contractStatus.equals("16")) {
            contractDO.setStatus("11");
            String[] roomNumbers = contractDO.getRoomNumber().split(",");
            for (String roomNumber : roomNumbers) {
                RoomDO roomDO = roomMapper.selectById(roomNumber);
                roomDO.setRoomStatus(10);
                roomMapper.updateById(roomDO);
            }
        }
        //变更审核不通过、退租审核不通过、作废审核不通过、续租审核不通过
        if (contractStatus.equals("2") ||
                contractStatus.equals("4") ||
                contractStatus.equals("7") ||
                contractStatus.equals("14")) {
            Long dayCount = DateUtils.getDayCount(contractDO.getContractStartTime(), new Date()) + 1;
            if (dayCount >= 1) {
                //正常执行中
                contractDO.setStatus("1");
            } else {
                //待执行
                contractDO.setStatus("13");
            }
        }
        //变更审核不通过
        if (contractStatus.equals("2")) {
            String redisKey = CONTRACT_CHANGE + contractDO.getParkId() + ":" + contractDO.getBuildId() + contractDO.getId();
            redisTemplate.delete(redisKey);
        }

        //作废审核不通过
        if (contractStatus.equals("7")) {
            LambdaQueryWrapperX<ContractOperateLogDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(ContractOperateLogDO::getContractId, contractDO.getId());
            queryWrapperX1.eq(ContractOperateLogDO::getOperateType, "void");
            ContractOperateLogDO contractOperateLogDO = contractOperateLogMapper.selectOne(queryWrapperX1);
            contractOperateLogMapper.deleteById(contractOperateLogDO.getId());
        }
        //退租审核不通过
        if (StringUtils.equals(contractStatus, "4")) {
            //修改合同内容
            contractRetreatMapper.deleteByContractId(contractDO.getId());
        }
        //续租审核不通过
        if (StringUtils.equals(contractStatus, "14")) {
            //修改合同内容
            LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ContractDO::getRenewalContractId, contractDO.getId());
            Long oldContractId = contractMapper.selectOne(queryWrapperX).getId();
            contractMapper.updateById(new ContractDO().setId(oldContractId).setIsWhetherLease("0").setRenewalContractId(null));
            contractMapper.deleteByIdContract(contractDO.getId());
        }
        contractMapper.updateById(contractDO);

    }


    private void updateOwnerAndRoomsForNewContract(ContractDO contractDO) {
        OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
        if (ownerDO == null) {
            return;
        }

        ownerDO.setBuildBind( ContractAuditStatusUtils.convertBuildBingder(ownerDO.getBuildBind(),contractDO.getCheckedBuild()) );
        ownerDO.setVillageIdList(ContractAuditStatusUtils.convertVillageList(ownerDO.getVillageIdList(),contractDO.getParkId()));
        ownerMapper.updateById(ownerDO);

        updateRoomsForContract(contractDO, 30, 1, true);
    }

    private void updateOwnerAndRoomsForChangedContract(ContractDO contractDO) {
        OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
        if (ownerDO == null) {
            return;
        }

        ownerDO.setBuildBind(ContractAuditStatusUtils.convertBuildBingder(ownerDO.getBuildBind(),contractDO.getCheckedBuild()));
        ownerDO.setVillageIdList( ContractAuditStatusUtils.convertVillageList(ownerDO.getVillageIdList(),contractDO.getParkId()));
        ownerMapper.updateById(ownerDO);

        updateRoomsForContract(contractDO, 30, 1, true);
    }

    /**
     * 退租和作废调用
     *
     * @param contractDO
     */
    @Override
    public void updateOwnerAndRoomsForTerminatedOrCancelledContract(ContractDO contractDO) {
        OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
        if (ownerDO == null) {
            return;
        }

        ownerDO.setBuildBind(ContractAuditStatusUtils.subtractBuildBinder(ownerDO.getBuildBind(),contractDO.getCheckedBuild()));
        ownerDO.setVillageIdList( ContractAuditStatusUtils.subtractVillageId(ownerDO.getVillageIdList(),contractDO.getParkId()));
        ownerMapper.updateById(ownerDO);

        updateRoomsForContract(contractDO, 10, 0, false);
    }

    private void updateRoomsForContract(ContractDO contractDO, int roomStatus, int contractCount, boolean isActive) {
        log.info("》》》》》》》》》》》updateRoomsForContract：RoomNumber{}《《《《《《《《《", contractDO.getRoomNumber());
        List<RoomDO> roomDOS = roomMapper.selectBatchIds(Arrays.asList(contractDO.getRoomNumber().split(",")));
        if (CollUtil.isEmpty(roomDOS)) {
            return;
        }
        log.info("》》》》》》》》》》》roomMapper.selectBatchIds{}《《《《《《《《《", JSONObject.toJSONString(roomDOS));

        for (RoomDO roomDO : roomDOS) {
            if (isActive) {
                roomDO.setRentalAreaIn(roomDO.getRentalArea());
                roomDO.setChargingAreaIn(roomDO.getChargingArea());
                roomDO.setLeaseStart(LocalDateTime.ofInstant(contractDO.getContractStartTime().toInstant(), ZoneId.systemDefault()));
                roomDO.setLeaseEnd(LocalDateTime.ofInstant(contractDO.getContractEndTime().toInstant(), ZoneId.systemDefault()));
                roomDO.setInvitationStatus(0);
                roomDO.setContractInfo(JSONObject.toJSONString(contractDO));
            } else {
                roomDO.setRentalAreaIn(BigDecimal.ZERO);
                roomDO.setChargingAreaIn(BigDecimal.ZERO);
                roomDO.setLeaseStart(null);
                roomDO.setLeaseEnd(null);
                roomDO.setInvitationStatus(1);
                roomDO.setContractInfo(null);
            }
            roomDO.setContractCount(contractCount);
            roomDO.setRoomStatus(roomStatus);
            roomMapper.updateById(roomDO);
        }
    }

    @Override
    public List<ContractLeaveRespVO> getList(ContractLeaveSaveReqVO contractLeaveSaveReqVO) {
        LambdaQueryWrapperX<ContractLeaveDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eqIfPresent(ContractLeaveDO::getContractId, contractLeaveSaveReqVO.getContractId())
                .eqIfPresent(ContractLeaveDO::getStatus, contractLeaveSaveReqVO.getStatus())
                .eqIfPresent(ContractLeaveDO::getProcessInstanceId, contractLeaveSaveReqVO.getProcessInstanceId());
        if (contractLeaveSaveReqVO.getProcessSelectStatus() == 1) {
            wrapperX.isNotNull(ContractLeaveDO::getEndTime);
        } else {
            wrapperX.isNull(ContractLeaveDO::getEndTime);
        }

        List<ContractLeaveDO> contractLeaveDOS = contractLeaveMapper.selectList(wrapperX);

        return BeanUtils.toBean(contractLeaveDOS, ContractLeaveRespVO.class);
    }

    @Override
    public ContractLeaveRespVO getOneByContractId(ContractLeaveSaveReqVO contractLeaveSaveReqVO) {
        LambdaQueryWrapperX<ContractLeaveDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(ContractLeaveDO::getContractId, contractLeaveSaveReqVO.getContractId())
                .orderByDesc(ContractLeaveDO::getCreateTime);
        ContractLeaveDO contractLeaveDO = contractLeaveMapper.selectList(wrapperX).get(0);
        return BeanUtils.toBean(contractLeaveDO, ContractLeaveRespVO.class);
    }
}