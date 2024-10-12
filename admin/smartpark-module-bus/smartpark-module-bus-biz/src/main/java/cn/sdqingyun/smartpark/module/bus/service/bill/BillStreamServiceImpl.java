package cn.sdqingyun.smartpark.module.bus.service.bill;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOrderBillCollectionVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPreRechargeRecordDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPriceDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillStreamMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyPreRechargeRecordMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyPriceMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.service.bpm.ContractLeaveService;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillStreamMiddleService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_STREAM_NOT_EXISTS;

/**
 * 机构账单收支流水 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Transactional(readOnly = true)
public class BillStreamServiceImpl implements BillStreamService {

    @Resource
    private BillStreamMapper billStreamMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractOrderBillMapper contractOrderBillMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private OrgAccountMapper accountMapper;

    @Autowired
    private OrgBillAnnexMapper orgBillAnnexMapper;


    @Resource
    private OrgIncomeMapper orgIncomeMapper;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Resource
    private OrgBillStreamMiddleService orgBillStreamMiddleService;

    @Resource
    private ContractLeaveService contractLeaveService;

    @Resource
    private EnergyPreRechargeRecordMapper energyPreRechargeRecordMapper;

    @Resource
    private EnergyPriceMapper energyPriceMapper;

    @Resource
    private EnergyMapper energyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(BillStreamSaveVO billStreamSaveVO) {
        // 插入
        List<ContractOrderBillCollectionVO> billCollectionList = billStreamSaveVO.getBillCollectionList();
        if (CollectionUtils.isNotEmpty(billCollectionList)) {
            billCollectionList.forEach(billCollectionVO -> {
                contractOrderBillMapper.updateOrderBillReceivablePayment(billCollectionVO.getThisPayment(), billCollectionVO.getId());

                ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billCollectionVO.getId());
                Double receivablePayment = Double.valueOf(StringUtils.isEmpty(contractOrderBillDO.getReceivablePayment()) ? "0.00" : contractOrderBillDO.getReceivablePayment());
                Double receivable = Double.valueOf(contractOrderBillDO.getReceivable()) + Double.valueOf(contractOrderBillDO.getLateFee());
                if (receivablePayment == receivable) {
                    contractOrderBillDO.setBillStatus("1");
                    contractOrderBillMapper.updateById(contractOrderBillDO);
                }
                ContractDO contractDO = contractMapper.selectById(billCollectionVO.getContractId());

                BillStreamDO billStream = new BillStreamDO();
                billStream.setVillageId(contractDO.getParkId());
                billStream.setBuildId(contractDO.getBuildId());
                billStream.setRoomNumber(contractDO.getRoomNumber());
                billStream.setBillId(billCollectionVO.getId());
                billStream.setStreamNumber(UuidUtils.generateUuid().replace("-", ""));
                billStream.setStreamSource("1");
                billStream.setBillType("1");
                billStream.setIsClose("1");
                billStream.setIsConfirm("1");
                billStream.setAmount(new BigDecimal(billCollectionVO.getThisPayment()));
                if (billCollectionVO.getIncomeDate() == null) {
                    throw new ServiceException(406, "入账时间不能为空");
                }
                billStream.setIncomeDate(billCollectionVO.getIncomeDate());
                billStream.setMatchDate(new Date());
                billStream.setCostType(billCollectionVO.getFeeType());
                billStream.setOwnerId(contractDO.getOwnerId());
                if (StringUtils.isNotEmpty(billStream.getPpStreamId())) {
                    BillStreamDO billStreamDO = billStreamMapper.selectById(billStream.getPpStreamId());
                    billStream.setNomatchPrice(new BigDecimal("0.00"));
                    BigDecimal amountOld = billStreamDO.getAmount();
                    BigDecimal amountNew = billStream.getAmount();
                    int comparisonResult = amountOld.compareTo(amountNew);
                    switch (comparisonResult) {
                        case -1:
                            System.out.println("BigDecimal a is less than BigDecimal b");
                            break;
                        case 0:
                            billStream.setMatchStatus("1");
                            break;
                        case 1:
                            billStream.setMatchStatus("3");
                            BigDecimal subtract = amountOld.subtract(amountNew);
                            billStream.setNomatchPrice(subtract);
                            break;
                    }
                    billStream.setMatchPrice(new BigDecimal(billCollectionVO.getThisPayment()));
                } else {
                    billStream.setNomatchPrice(new BigDecimal(billCollectionVO.getThisPayment()));
                    billStream.setMatchStatus("2");
                }

                OwnerDO ownerDO = ownerMapper.selectById(billStream.getOwnerId());
                Long industryId = null;
                if (ownerDO != null) {
                    billStream.setCompanyId(ownerDO.getId());
                    billStream.setCompanyName(ownerDO.getName());
                    industryId = ownerDO.getIndustryId();
                }

                billStream.setCurrency("CNY");
                billStream.setLoanType("INCOME");
                billStreamMapper.insert(billStream);

                //记录机构收支流水
                OrgIncomeDO orgIncomeDO = new OrgIncomeDO();
                orgIncomeDO.setTenantId(contractDO.getTenantId());
                orgIncomeDO.setVillageId(contractDO.getParkId());
                orgIncomeDO.setContractId(contractDO.getId());
                orgIncomeDO.setBillId(contractOrderBillDO.getId());
                orgIncomeDO.setBillType(contractOrderBillDO.getBillType());
                orgIncomeDO.setContractNumber(contractDO.getContractNumber());
                orgIncomeDO.setBuildId(contractDO.getBuildId());
                if (industryId != null) {
                    orgIncomeDO.setIndustryId(industryId);
                }
                orgIncomeDO.setRoomNumber(contractDO.getRoomNumber());
                orgIncomeDO.setOwnerId(contractDO.getOwnerId());
                orgIncomeDO.setIncomeType("3");
                orgIncomeDO.setIsConfirm("1");
                orgIncomeDO.setCostType(contractOrderBillDO.getFeeType());
                orgIncomeDO.setTradeAmount(billStream.getAmount());
                orgIncomeDO.setAmount(billStream.getAmount());
                orgIncomeDO.setTaxAmount(new BigDecimal(contractOrderBillDO.getTaxAmount()));
                orgIncomeDO.setTradeTime(contractOrderBillDO.getPayTime());
                orgIncomeDO.setIsUnrealRoom("0");
                orgIncomeMapper.insert(orgIncomeDO);


                if (contractDO.getStatus().equals("5")) {
                    LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
                    queryWrapperX.eq(ContractOrderBillDO::getContractId, contractDO.getOwnerId());
                    queryWrapperX.in(ContractOrderBillDO::getBillStatus, Arrays.asList("0,2".split(",")));
                    queryWrapperX.eq(ContractOrderBillDO::getDataSource, "1");
                    int size = contractOrderBillMapper.selectList(queryWrapperX).size();
                    if (size <= 0) {

                        //结清
                        contractDO.setStatus("6");
                        contractMapper.updateById(contractDO);

                        contractLeaveService.updateOwnerAndRoomsForTerminatedOrCancelledContract(contractDO);

                    }
                }

            });
        }


    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBillStream(BillStreamSaveVO billStreamSaveVO) {
        if (billStreamSaveVO.getIncomeDate() == null) {
            throw new ServiceException(406, "入账时间不能为空");
        }
//        LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
//        queryWrapperX.like(OrgBillCostTypeDO::getName, "滞纳金").or().like(OrgBillCostTypeDO::getName, "违约金");
//        List<OrgBillCostTypeDO> orgBillCostTypeDOS = orgBillCostTypeMapper.selectList(queryWrapperX);
//        if (CollectionUtils.isNotEmpty(orgBillCostTypeDOS)) {
//            billStreamSaveVO.setCostType(String.valueOf(orgBillCostTypeDOS.get(0).getId()));
//        }

        //记录机构收款流水
        OrgIncomeDO orgIncomeDO = new OrgIncomeDO();
        OwnerDO ownerDO = ownerMapper.selectById(billStreamSaveVO.getCompanyId());
        if (billStreamSaveVO.getBillId() != null) {
            ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billStreamSaveVO.getBillId());
            ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
            orgIncomeDO.setTenantId(contractDO.getTenantId());
            orgIncomeDO.setVillageId(contractDO.getParkId());
            orgIncomeDO.setContractId(contractDO.getId());
            orgIncomeDO.setBillId(contractOrderBillDO.getId());
            orgIncomeDO.setBillType(contractOrderBillDO.getBillType());
            orgIncomeDO.setContractNumber(contractDO.getContractNumber());
            orgIncomeDO.setBuildId(contractDO.getBuildId());
            if (ownerDO != null) {
                orgIncomeDO.setIndustryId(ownerDO.getIndustryId());
            }

            orgIncomeDO.setRoomNumber(contractDO.getRoomNumber());
            orgIncomeDO.setOwnerId(contractDO.getOwnerId());
            orgIncomeDO.setTaxAmount(new BigDecimal(contractOrderBillDO.getTaxAmount()));
            orgIncomeDO.setTradeTime(contractOrderBillDO.getPayTime());
            orgIncomeDO.setIncomeType("3");
            orgIncomeDO.setIsConfirm("1");
            if (StringUtils.isNotEmpty(billStreamSaveVO.getCostType())) {
                orgIncomeDO.setCostType(billStreamSaveVO.getCostType());
            }

            orgIncomeDO.setTradeAmount(billStreamSaveVO.getAmount());
            orgIncomeDO.setAmount(billStreamSaveVO.getAmount());
            orgIncomeDO.setIsUnrealRoom("0");
            orgIncomeMapper.insert(orgIncomeDO);
        }

        BillStreamDO billStream = BeanUtils.toBean(billStreamSaveVO, BillStreamDO.class);
        billStream.setStreamSource("1");
        billStream.setBillType("1");
        billStream.setIsClose("1");
        billStream.setIsConfirm("1");
        billStream.setMatchDate(new Date());
        billStream.setNomatchPrice(billStream.getAmount());
        billStream.setMatchStatus("2");
        billStream.setOwnerId(billStream.getCompanyId());
        billStream.setCompanyName(ownerDO.getName());
        billStreamMapper.insert(billStream);
        billStreamSaveVO.setId(billStream.getId());

        // 插入
        LambdaQueryWrapper<OrgBillAnnexDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrgBillAnnexDO::getSourceId, billStream.getId());
        orgBillAnnexMapper.delete(queryWrapper);
        List<OrgBillAnnexSaveReqVO> annexList = billStreamSaveVO.getAnnexList();
        if (CollectionUtils.isNotEmpty(annexList)) {
            annexList.forEach(orgBillAnnexSaveReqVO -> {
                OrgBillAnnexDO orgBillAnnexDO = BeanUtils.toBean(orgBillAnnexSaveReqVO, OrgBillAnnexDO.class);
                orgBillAnnexDO.setSourceId(billStream.getId());
                orgBillAnnexMapper.insert(orgBillAnnexDO);
            });
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lateSettle(BillStreamSaveVO billStreamSaveVO) throws ParseException {
        ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billStreamSaveVO.getBillId());
        if (contractOrderBillDO.getCloseStatus().equals("0")) {
            throw new ServiceException(406, "关闭状态下账单不可进行该操作");
        }
        BigDecimal amount = billStreamSaveVO.getAmount();
        BigDecimal lateFee = new BigDecimal(contractOrderBillDO.getLateFee());
        int comparisonResult = amount.compareTo(lateFee);
        if (comparisonResult == 1) {
            throw new ServiceException(406, "发生额不能大于需收滞纳金金额");
        }


        BillStreamDO billStream = BeanUtils.toBean(billStreamSaveVO, BillStreamDO.class);
        if (billStream.getIncomeDate() == null) {
            throw new ServiceException(406, "入账时间不能为空");
        }
        billStream.setStreamNumber(UuidUtils.generateUuid().replace("-", ""));
        billStream.setStreamSource("1");
        billStream.setBillType("1");
        billStream.setIsClose("1");
        billStream.setIsConfirm("1");
        billStream.setMatchDate(billStreamSaveVO.getMatchDate());
        billStream.setNomatchPrice(billStream.getAmount());
        billStream.setMatchStatus("1");
        billStream.setOwnerId(billStream.getCompanyId());
        billStream.setCostType(contractOrderBillDO.getFeeType());
        billStreamMapper.insert(billStream);


        ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
        OwnerDO ownerDO = ownerMapper.selectById(billStream.getCompanyId());
        //记录机构收款流水
        OrgIncomeDO orgIncomeDO = new OrgIncomeDO();
        orgIncomeDO.setTenantId(contractDO.getTenantId());
        orgIncomeDO.setVillageId(contractDO.getParkId());
        orgIncomeDO.setContractId(contractDO.getId());
        orgIncomeDO.setBillId(contractOrderBillDO.getId());
        orgIncomeDO.setBillType(contractOrderBillDO.getBillType());
        orgIncomeDO.setContractNumber(contractDO.getContractNumber());
        orgIncomeDO.setBuildId(contractDO.getBuildId());
        if (ownerDO != null) {
            orgIncomeDO.setIndustryId(ownerDO.getIndustryId());
        }

        orgIncomeDO.setRoomNumber(contractDO.getRoomNumber());
        orgIncomeDO.setOwnerId(contractDO.getOwnerId());
        orgIncomeDO.setIncomeType("3");
        orgIncomeDO.setIsConfirm("1");
        if (StringUtils.isNotEmpty(billStream.getCostType())) {
            orgIncomeDO.setCostType(billStream.getCostType());
        }
        orgIncomeDO.setTradeAmount(billStream.getAmount());
        orgIncomeDO.setAmount(billStream.getAmount());
        orgIncomeDO.setTaxAmount(new BigDecimal(contractOrderBillDO.getTaxAmount()));
        orgIncomeDO.setTradeTime(contractOrderBillDO.getPayTime());
        orgIncomeDO.setIsUnrealRoom("0");
        orgIncomeMapper.insert(orgIncomeDO);

        // 插入
        LambdaQueryWrapper<OrgBillAnnexDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrgBillAnnexDO::getSourceId, billStream.getId());
        orgBillAnnexMapper.delete(queryWrapper);
        List<OrgBillAnnexSaveReqVO> annexList = billStreamSaveVO.getAnnexList();
        if (CollectionUtils.isNotEmpty(annexList)) {
            annexList.forEach(orgBillAnnexSaveReqVO -> {
                OrgBillAnnexDO orgBillAnnexDO = BeanUtils.toBean(orgBillAnnexSaveReqVO, OrgBillAnnexDO.class);
                orgBillAnnexDO.setSourceId(billStream.getId());
                orgBillAnnexMapper.insert(orgBillAnnexDO);
            });
        }
        List<Long> streamIdList = Lists.newArrayList();
        streamIdList.add(billStream.getId());
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String matchDate = sim.format(new Date());
        orgBillStreamMiddleService.addCollectionMiddle(contractOrderBillDO.getId(), streamIdList, amount, matchDate);

        contractOrderBillDO.setLateFeePayStatus("2");
        contractOrderBillMapper.updateById(contractOrderBillDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rechargeCreateBillStream(BillStreamSaveVO billStreamSaveVO) {
        billStreamSaveVO.setStreamNumber(UuidUtils.generateUuid().replace("-", ""));
        //创建收支流水
        this.createBillStream(billStreamSaveVO);

        EnergyDO energyDO = energyMapper.selectById(billStreamSaveVO.getEnergyId());

        EnergyPreRechargeRecordDO energyPreRechargeRecordDO = new EnergyPreRechargeRecordDO();
        energyPreRechargeRecordDO.setEnergyId(billStreamSaveVO.getEnergyId());
        energyPreRechargeRecordDO.setRechargePrice(billStreamSaveVO.getAmount());
        LambdaQueryWrapperX<EnergyPriceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("REGEXP_LIKE(room_ids, '(^|,)" + List.of(energyDO.getRoomIds().split(",")).get(0) + "($|,)')");
        EnergyPriceDO energyPriceDO = energyPriceMapper.selectOne(queryWrapperX);
        if (energyPriceDO.getIsStagePrice().equals("0")) {
            String unitPrice = energyPriceDO.getUnitPrice();
            BigDecimal rechargeDegree = billStreamSaveVO.getAmount().divide(new BigDecimal(unitPrice));
            energyPreRechargeRecordDO.setRechargeDegree(rechargeDegree);
        } else {
            //阶梯价格
            energyPreRechargeRecordDO.setRechargeDegree(new BigDecimal("0.00"));
        }

        energyPreRechargeRecordDO.setBillId(null);
        energyPreRechargeRecordDO.setBillStreamId(billStreamSaveVO.getId());
        energyPreRechargeRecordDO.setStreamNumber(billStreamSaveVO.getStreamNumber());
        energyPreRechargeRecordDO.setResult("下发成功");

        energyPreRechargeRecordMapper.insert(energyPreRechargeRecordDO);

    }

    /**
     * billStreamSaveReqVO
     *
     * @param billStreamSaveReqVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(BillStreamSaveReqVO billStreamSaveReqVO) {
        if (billStreamSaveReqVO.getIncomeDate() == null) {
            throw new ServiceException(406, "入账时间不能为空");
        }
        BillStreamDO billStream = BeanUtils.toBean(billStreamSaveReqVO, BillStreamDO.class);
        billStream.setStreamSource("2");
        billStream.setIsClose("1");
        billStream.setIsConfirm("1");
        billStream.setMatchDate(new Date());
        billStream.setNomatchPrice(billStream.getAmount());
        billStream.setMatchStatus("1");
        billStream.setOwnerId(billStream.getCompanyId());
        billStream.setCurrency("CNY");
        billStreamMapper.insert(billStream);
        return billStream.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBillStream(BillStreamSaveReqVO updateReqVO) {
        Integer count = orgBillStreamMiddleService.getByStreamIdCount(updateReqVO.getId());
        if (count >= 1) {
            throw new ServiceException(406, "关闭流水前需要取消流水与账单的匹配");
        }
        // 校验存在
        validateBillStreamExists(updateReqVO.getId());
        // 更新
        BillStreamDO updateObj = BeanUtils.toBean(updateReqVO, BillStreamDO.class);
        billStreamMapper.updateById(updateObj);
    }

    @Override
    public void deleteBillStream(BillStreamSaveReqVO deleteReqVO) {
        // 校验存在
        validateBillStreamExists(deleteReqVO.getId());
        // 删除
        BillStreamDO deleteObj = BeanUtils.toBean(deleteReqVO, BillStreamDO.class);
        billStreamMapper.updateById(deleteObj);
    }

    private void validateBillStreamExists(Long id) {
        if (billStreamMapper.selectById(id) == null) {
            throw exception(BILL_STREAM_NOT_EXISTS);
        }
    }

    @Override
    public BillStreamDO getBillStream(Long id) {

        return billStreamMapper.selectById(id);
    }

    @Override
    public PageResult<BillStreamRespVO> getBillStreamPage(BillStreamPageReqVO pageReqVO) {
        LambdaQueryWrapperX<BillStreamDO> queryWrapperX = new LambdaQueryWrapperX<>();

        LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(OrgBillCostTypeDO::getIsBond, "0");
        List<OrgBillCostTypeDO> orgBillCostTypeDOS = orgBillCostTypeMapper.selectList(queryWrapperX1);
        if (CollectionUtils.isNotEmpty(orgBillCostTypeDOS)) {
            List<Long> costTypeList = orgBillCostTypeDOS.stream().map(OrgBillCostTypeDO::getId).collect(Collectors.toList());
            queryWrapperX.in(BillStreamDO::getCostType, costTypeList);
        }
//
//        if (StringUtils.isNotEmpty(pageReqVO.getCostType())) {
//            queryWrapperX.eq(BillStreamDO::getCostType, pageReqVO.getCostType());
//        }
        if (StringUtils.isNotEmpty(pageReqVO.getBillType())) {
            queryWrapperX.eq(BillStreamDO::getBillType, pageReqVO.getBillType());
        }
        if (pageReqVO.getMatchDate() != null) {
            queryWrapperX.eq(BillStreamDO::getMatchDate, pageReqVO.getMatchDateStr());
        }

        if (pageReqVO.getOwnerId() != null) {
            queryWrapperX.eq(BillStreamDO::getOwnerId, pageReqVO.getOwnerId());
        }

        queryWrapperX.apply("match_status !='1'");
        queryWrapperX.orderByDesc(BillStreamDO::getCreateTime);
        PageResult<BillStreamDO> pageResult = billStreamMapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<BillStreamRespVO> billStreamDOPageResult = BeanUtils.toBean(pageResult, BillStreamRespVO.class);
        return billStreamDOPageResult;
    }


    @Override
    public PageResult<BillStreamDO> getOwnerStreamPage(BillStreamPageReqVO pageReqVO) {
        LambdaQueryWrapperX<BillStreamDO> queryWrapperX = new LambdaQueryWrapperX<>();

        if (pageReqVO.getOwnerId() != null) {
            queryWrapperX.eq(BillStreamDO::getOwnerId, pageReqVO.getOwnerId());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getLoanType())) {
            queryWrapperX.eq(BillStreamDO::getLoanType, pageReqVO.getLoanType());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getMatchStatus())) {
            queryWrapperX.eq(BillStreamDO::getMatchStatus, pageReqVO.getMatchStatus());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getRemitType())) {
            queryWrapperX.eq(BillStreamDO::getRemitType, pageReqVO.getRemitType());
        }
        if (pageReqVO.getAccountId() != null) {
            queryWrapperX.eq(BillStreamDO::getAccountId, pageReqVO.getAccountId());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getCompanyName())) {
            queryWrapperX.like(BillStreamDO::getCompanyName, pageReqVO.getCompanyName());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getCreator())) {
            queryWrapperX.eq(BillStreamDO::getCreator, pageReqVO.getCreator());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStartIncomeDate())) {
            queryWrapperX.apply("DATE_FORMAT(income_date,'%Y-%m-%d') between DATE_FORMAT(" + pageReqVO.getStartIncomeDate() + ",'%Y-%m-%d') and DATE_FORMAT(" + pageReqVO.getEndIncomeDate() + ",'%Y-%m-%d') ");
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStartCreateTime())) {
            queryWrapperX.apply("DATE_FORMAT(create_time,'%Y-%m-%d') between DATE_FORMAT(" + pageReqVO.getStartCreateTime() + ",'%Y-%m-%d') and DATE_FORMAT(" + pageReqVO.getEndCreateTime() + ",'%Y-%m-%d') ");
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStartAmount())) {
            queryWrapperX.apply("amount between " + pageReqVO.getStartAmount() + " and " + pageReqVO.getEndAmount());
        }
        queryWrapperX.orderByDesc(BillStreamDO::getCreateTime);
        PageResult<BillStreamDO> billStreamDOPageResult = billStreamMapper.selectPage(pageReqVO, queryWrapperX);
        List<BillStreamDO> list = billStreamDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(billStreamDO -> {
                //房间
                getRoomName(billStreamDO);

                if (billStreamDO.getOwnerId() != null) {
                    OwnerDO ownerDO = ownerMapper.selectById(billStreamDO.getOwnerId());
                    if (ownerDO != null) {
                        Integer isPersonal = ownerDO.getIsPersonal();
                        billStreamDO.setIsPersonal(isPersonal);
                    }
                }

                //创建人
                if (StringUtils.isNotEmpty(billStreamDO.getCreator())) {
                    String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(billStreamDO.getCreator()));
                    billStreamDO.setCreator(userName);
                }


                //借贷标
                if (StringUtils.isNotEmpty(billStreamDO.getLoanType())) {
                    String loanType = DictFrameworkUtils.getDictDataLabel("LOAN_TYPE", billStreamDO.getLoanType());
                    billStreamDO.setLoanType(loanType);
                }


                //币种
                if (StringUtils.isNotEmpty(billStreamDO.getCurrency())) {
                    String currency = DictFrameworkUtils.getDictDataLabel("CURRENCY", billStreamDO.getCurrency());
                    billStreamDO.setCurrency(currency);
                }


                //汇款方式
                if (StringUtils.isNotEmpty(billStreamDO.getRemitType())) {
                    String remitType = DictFrameworkUtils.getDictDataLabel("REMIT_TYPE", billStreamDO.getRemitType());
                    billStreamDO.setRemitType(remitType);
                }


                //到账确认
                String isConfirm = DictFrameworkUtils.getDictDataLabel("IS_CONFIRM", billStreamDO.getIsConfirm());
                billStreamDO.setIsConfirm(isConfirm);
            });
        }
        return billStreamDOPageResult;
    }

    /**
     * @param ownerId
     * @return
     */
    @Override
    public Map<String, Object> getOwnerIdStreamTotalMoney(Long ownerId) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal totalMoney = new BigDecimal("0.00");
        Integer totalCount = 0;

        BigDecimal matchMoney1 = new BigDecimal("0.00");
        Integer matchCount1 = 0;

        BigDecimal matchMoney2 = new BigDecimal("0.00");
        Integer matchCount2 = 0;

        BigDecimal matchMoney3 = new BigDecimal("0.00");
        Integer matchCount3 = 0;

        List<OwnerIdStreamTotalMoneyVO> streamList = billStreamMapper.getOwnerIdStreamTotalMoney(ownerId);
        if (CollectionUtils.isNotEmpty(streamList)) {
            //总金额
            List<BigDecimal> collect = streamList.stream().map(OwnerIdStreamTotalMoneyVO::getAmount).collect(Collectors.toList());
            BigDecimal reduce = collect.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            totalMoney = totalMoney.add(reduce);
            totalCount = collect.size();

            //完全匹配金额
            List<OwnerIdStreamTotalMoneyVO> matchStatusList1 = streamList.stream().filter(OwnerIdStreamTotalMoneyVO -> OwnerIdStreamTotalMoneyVO.getMatchStatus().equals("1")).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(matchStatusList1)) {
                List<BigDecimal> matchMoneyList1 = matchStatusList1.stream().map(OwnerIdStreamTotalMoneyVO::getAmount).collect(Collectors.toList());
                matchMoney1 = matchMoney1.add(matchMoneyList1.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                matchCount1 = matchStatusList1.size();
            }

            //未匹配
            List<OwnerIdStreamTotalMoneyVO> matchStatusList2 = streamList.stream().filter(OwnerIdStreamTotalMoneyVO -> OwnerIdStreamTotalMoneyVO.getMatchStatus().equals("2")).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(matchStatusList2)) {
                List<BigDecimal> matchMoneyList2 = matchStatusList2.stream().map(OwnerIdStreamTotalMoneyVO::getAmount).collect(Collectors.toList());
                matchMoney2 = matchMoney2.add(matchMoneyList2.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                matchCount2 = matchStatusList2.size();
            }

            //部分匹配
            List<OwnerIdStreamTotalMoneyVO> matchStatusList3 = streamList.stream().filter(OwnerIdStreamTotalMoneyVO -> OwnerIdStreamTotalMoneyVO.getMatchStatus().equals("3")).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(matchStatusList3)) {
                List<BigDecimal> matchMoneyList3 = matchStatusList3.stream().map(OwnerIdStreamTotalMoneyVO::getAmount).collect(Collectors.toList());
                matchMoney3 = matchMoney3.add(matchMoneyList3.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                matchCount3 = matchStatusList3.size();
            }
        }

        //匹配状态;1=完全匹配;2=未匹配;3=部分匹配;

        //发生总额
        map.put("totalMoney", totalMoney);
        //发生总额笔数
        map.put("totalCount", totalCount);

        //完全匹配金额
        map.put("matchMoney1", matchMoney1);
        //完全匹配笔数
        map.put("matchCount1", matchCount1);

        //未匹配金额
        map.put("matchMoney2", matchMoney2);
        //未匹配笔数
        map.put("matchCount2", matchCount2);

        //部分匹配金额
        map.put("matchMoney3", matchMoney3);
        //部分匹配笔数
        map.put("matchCount3", matchCount3);
        return map;
    }

    /**
     * @param ownerId
     * @param loanType
     * @return
     */
    @Override
    public List<OwnerIdLoanTypeInfoListVO> getOwnerIdLoanTypeInfoList(Long ownerId, String loanType) {
        List<OwnerIdLoanTypeInfoListVO> ownerIdLoanTypeInfoList = billStreamMapper.getOwnerIdLoanTypeInfoList(ownerId, loanType);
        if (CollectionUtils.isNotEmpty(ownerIdLoanTypeInfoList)) {
            ownerIdLoanTypeInfoList.stream().forEach(ownerIdLoanTypeInfoListVO -> {
                OrgAccountDO orgAccountDO = accountMapper.selectById(ownerIdLoanTypeInfoListVO.getAccountId());
                if (orgAccountDO != null) {
                    ownerIdLoanTypeInfoListVO.setAccountName(orgAccountDO.getName());
                }
            });
        }
        return ownerIdLoanTypeInfoList;
    }

    /**
     * 导入
     *
     * @param list
     */
    @Override
    public void importStreamList(List<BillStreamImportVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("导入账单流水数据不能为空");
        }
        list.stream().forEach(streamImportVO -> {

            BillStreamDO billStreamDO = new BillStreamDO();
            billStreamDO.setVillageId(streamImportVO.getVillageId());
            billStreamDO.setBuildId(streamImportVO.getBuildId());
            billStreamDO.setRoomNumber(String.valueOf(streamImportVO.getRoomNumber()));
            billStreamDO.setLoanType(streamImportVO.getLoanType());
            billStreamDO.setCurrency(streamImportVO.getCurrency());
            billStreamDO.setAmount(streamImportVO.getAmount());
            billStreamDO.setIncomeDate(streamImportVO.getIncomeDate());
            if (streamImportVO.getIncomeDate() == null) {
                throw new ServiceException(406, "入账时间不能为空");
            }

            String accountName = streamImportVO.getAccountName();
            LambdaQueryWrapperX<OrgAccountDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(OrgAccountDO::getName, accountName);
            List<OrgAccountDO> orgAccountDOS = accountMapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(orgAccountDOS)) {
                billStreamDO.setAccountId(orgAccountDOS.get(0).getId());
            } else {
                throw new RuntimeException("流水账户名称不存在当前机构中");
            }
            if (StringUtils.isNotEmpty(streamImportVO.getVoucherNo())) {
                billStreamDO.setVoucherNo(streamImportVO.getVoucherNo());
            }
            String companyName = streamImportVO.getCompanyName();
            LambdaQueryWrapperX<OwnerDO> ownerDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            ownerDOLambdaQueryWrapperX.eq(OwnerDO::getName, companyName);
            List<OwnerDO> ownerDOList = ownerMapper.selectList(ownerDOLambdaQueryWrapperX);
            if (CollectionUtils.isNotEmpty(ownerDOList)) {
                OwnerDO ownerDO = ownerDOList.get(0);
                billStreamDO.setCompanyId(ownerDO.getId());
                if (StringUtils.isNotEmpty(ownerDO.getAccount())) {
                    billStreamDO.setOtherAccount(ownerDO.getAccount());
                }
                if (StringUtils.isNotEmpty(ownerDO.getBank())) {
                    billStreamDO.setStreamAccount(ownerDO.getBank());
                }
            } else {
                throw new RuntimeException("对方单位名称不存在当前机构中");
            }

            if (StringUtils.isNotEmpty(streamImportVO.getLinkName())) {
                billStreamDO.setLinkName(streamImportVO.getLinkName());
            }
            billStreamDO.setLinkName(streamImportVO.getRemitType());

            if (StringUtils.isNotEmpty(streamImportVO.getAbstractc())) {
                billStreamDO.setLinkName(streamImportVO.getAbstractc());
            }

            if (StringUtils.isNotEmpty(streamImportVO.getRemark())) {
                billStreamDO.setRemark(streamImportVO.getRemark());
            }
            billStreamMapper.insert(billStreamDO);
        });
    }

    @Override
    public BillStreamDO incomeStreamDetail(Long streamMiddleId) {
        OrgBillStreamMiddleDO billStreamMiddle = orgBillStreamMiddleService.getBillStreamMiddle(streamMiddleId);
        if (billStreamMiddle != null) {
            Long streamId = billStreamMiddle.getStreamId();
            return billStreamMapper.selectById(streamId);
        }
        return new BillStreamDO();
    }

    private void getRoomName(BillStreamDO billStreamDO) {
        String roomNumber = billStreamDO.getRoomNumber();
        List<JSONObject> roomJson = Lists.newArrayList();
        if (StringUtils.isNotEmpty(roomNumber)) {
            String[] split = roomNumber.split(",");
            for (String roomId : split) {
                String roomName = roomMapper.selectById(Long.valueOf(roomId)).getRoomName();
                String lc = roomName.substring(0, roomName.length() - 2);
                roomName = lc + "-" + roomName;
                JSONObject json = new JSONObject();
                json.put("roomId", roomId);
                json.put("roomName", roomName);
                roomJson.add(json);
            }
            billStreamDO.setRoomNumber(new Gson().toJson(roomJson));
        }
    }

}