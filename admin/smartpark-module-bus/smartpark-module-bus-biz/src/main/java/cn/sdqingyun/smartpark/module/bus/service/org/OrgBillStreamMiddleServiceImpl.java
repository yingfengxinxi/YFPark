package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddlePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.StreamIdMatchingListPageVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillStreamMiddleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillStreamMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillStreamMiddleMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgIncomeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_STREAM_MIDDLE_NOT_EXISTS;

/**
 * 机构流水账单中间表【匹配】 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Transactional
public class OrgBillStreamMiddleServiceImpl implements OrgBillStreamMiddleService {

    @Resource
    private OrgBillStreamMiddleMapper billStreamMiddleMapper;

    @Autowired
    private ContractOrderBillMapper contractOrderBillMapper;

    @Autowired
    private BillStreamMapper billStreamMapper;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private OwnerMapper ownerMapper;

    @Resource
    private OrgIncomeMapper orgIncomeMapper;


    @Override
    public void updateBillStreamMiddle(OrgBillStreamMiddleSaveReqVO updateReqVO) {
        // 校验存在
        validateBillStreamMiddleExists(updateReqVO.getId());
        // 更新
        OrgBillStreamMiddleDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillStreamMiddleDO.class);
        billStreamMiddleMapper.updateById(updateObj);
    }

    /**
     * @param id 编号
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteBillStreamMiddle(Long id) {
        // 校验存在
        validateBillStreamMiddleExists(id);
        // 删除
        billStreamMiddleMapper.deleteById(id);
    }

    private void validateBillStreamMiddleExists(Long id) {
        if (billStreamMiddleMapper.selectById(id) == null) {
            throw exception(BILL_STREAM_MIDDLE_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillStreamMiddleDO getBillStreamMiddle(Long id) {
        return billStreamMiddleMapper.selectById(id);
    }


    /**
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<StreamIdMatchingListPageVO> getBillStreamMiddlePage(OrgBillStreamMiddlePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillStreamMiddleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillStreamMiddleDO::getStreamId, pageReqVO.getStreamId());
        IPage<StreamIdMatchingListPageVO> byStreamIdMatchingListPage = billStreamMiddleMapper.getByStreamIdMatchingListPage(MyBatisUtils.buildPage(pageReqVO), pageReqVO);
        return new PageResult<>(byStreamIdMatchingListPage.getRecords(), byStreamIdMatchingListPage.getTotal());
    }

    @Override
    public List<OrgBillStreamMiddleDO> getBillIdStreamMiddleList(Long billId) {

        LambdaQueryWrapperX<OrgBillStreamMiddleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillStreamMiddleDO::getBillId, billId);
        queryWrapperX.apply("match_status!='4'");
        return billStreamMiddleMapper.selectList(queryWrapperX);
    }

    /**
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<OrgBillStreamMiddleDO> getBillStreamListPage(OrgBillStreamMiddlePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillStreamMiddleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillStreamMiddleDO::getBillId, pageReqVO.getBillId());
        if (StringUtils.isNotEmpty(pageReqVO.getMatchStatus())) {
            queryWrapperX.eq(OrgBillStreamMiddleDO::getMatchStatus, pageReqVO.getMatchStatus());
        }
        queryWrapperX.orderByDesc(OrgBillStreamMiddleDO::getCreateTime);
        return billStreamMiddleMapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public Integer getByStreamIdCount(Long streamId) {
        LambdaQueryWrapperX<OrgBillStreamMiddleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillStreamMiddleDO::getStreamId, streamId);
        queryWrapperX.apply("match_status !='4'");
        return billStreamMiddleMapper.selectList(queryWrapperX).size();
    }

    /**
     * @param billIds
     * @param streamId
     * @param matchPrice
     * @param matchDate
     * @throws ParseException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMiddle(List<Long> billIds, Long streamId, BigDecimal matchPrice, String matchDate) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sim.parse(matchDate);

        BillStreamDO billStreamDO = billStreamMapper.selectById(streamId);
        if (billStreamDO != null) {
            if (billStreamDO.getMatchStatus().equals("1")) {
                throw new ServiceException(406, "当前流水已匹配账单,请勿重复操作");
            }
            for (Long billId : billIds) {
                OrgBillStreamMiddleDO orgBillStreamMiddleDO = new OrgBillStreamMiddleDO();
                orgBillStreamMiddleDO.setBillId(billId);
                ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billId);
                orgBillStreamMiddleDO.setBillType(Long.valueOf(contractOrderBillDO.getBillType()));
                orgBillStreamMiddleDO.setStreamId(billStreamDO.getId());
                orgBillStreamMiddleDO.setCompanyId(billStreamDO.getCompanyId());
                orgBillStreamMiddleDO.setCompanyName(billStreamDO.getCompanyName());
                orgBillStreamMiddleDO.setCostType(contractOrderBillDO.getFeeType());
                orgBillStreamMiddleDO.setIncomeDate(billStreamDO.getIncomeDate());
                orgBillStreamMiddleDO.setAmount(billStreamDO.getAmount());
                orgBillStreamMiddleDO.setMatchPrice(matchPrice);
                orgBillStreamMiddleDO.setNomatchPrice(new BigDecimal("0.00"));
                orgBillStreamMiddleDO.setMatchDate(parse);
                orgBillStreamMiddleDO.setMatchStatus("1");
                orgBillStreamMiddleDO.setIsConfirm("1");
                billStreamMiddleMapper.insert(orgBillStreamMiddleDO);

                OrgIncomeDO orgIncomeDO = new OrgIncomeDO();
                ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
                orgIncomeDO.setTenantId(contractDO.getTenantId());
                orgIncomeDO.setVillageId(contractDO.getParkId());
                orgIncomeDO.setContractId(contractDO.getId());
                orgIncomeDO.setBillId(contractOrderBillDO.getId());
                orgIncomeDO.setBillType(contractOrderBillDO.getBillType());
                orgIncomeDO.setContractNumber(contractDO.getContractNumber());
                orgIncomeDO.setBuildId(contractDO.getBuildId());
                OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
                if (ownerDO != null) {
                    orgIncomeDO.setIndustryId(ownerDO.getIndustryId());
                }

                orgIncomeDO.setRoomNumber(contractDO.getRoomNumber());
                orgIncomeDO.setOwnerId(contractDO.getOwnerId());
                orgIncomeDO.setTaxAmount(new BigDecimal(contractOrderBillDO.getTaxAmount()));
                orgIncomeDO.setTradeTime(contractOrderBillDO.getPayTime());
                orgIncomeDO.setIncomeType("3");
                orgIncomeDO.setIsConfirm("1");
                if (StringUtils.isNotEmpty(contractOrderBillDO.getFeeType())) {
                    orgIncomeDO.setCostType(contractOrderBillDO.getFeeType());
                }
                orgIncomeDO.setTradeAmount(orgBillStreamMiddleDO.getAmount());
                orgIncomeDO.setAmount(orgBillStreamMiddleDO.getAmount());
                orgIncomeDO.setIsUnrealRoom("0");
                orgIncomeDO.setStreamMiddleId(orgBillStreamMiddleDO.getId());
                orgIncomeMapper.insert(orgIncomeDO);

            }
            Long billId = billIds.get(billIds.size() - 1);
            ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billId);
            ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
            //修改流水匹配金额
            billStreamDO.setBillId(billId);
            billStreamDO.setVillageId(contractDO.getParkId());
            billStreamDO.setBuildId(contractDO.getBuildId());
            billStreamDO.setRoomNumber(contractDO.getRoomNumber());
            billStreamDO.setMatchPrice(billStreamDO.getMatchPrice().add(matchPrice));
            billStreamDO.setMatchDate(parse);
            BigDecimal nomatchPrice = billStreamDO.getAmount().subtract(billStreamDO.getMatchPrice());
            billStreamDO.setNomatchPrice(nomatchPrice);
            if (nomatchPrice.compareTo(BigDecimal.ZERO) > 0) {
                billStreamDO.setMatchStatus("3");
            } else {
                billStreamDO.setMatchStatus("1");
            }
            billStreamDO.setCostType(contractOrderBillDO.getFeeType());
            billStreamMapper.updateById(billStreamDO);
        } else {
            throw new ServiceException(406, "当前选中流水不存在,请刷新后重新操作");
        }
    }

    /**
     * @param billId
     * @param streamIds
     * @param matchPrice
     * @param matchDate
     * @throws ParseException
     */
    @Override
    public void addCollectionMiddle(Long billId, List<Long> streamIds, BigDecimal matchPrice, String matchDate) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sim.parse(matchDate);

        LambdaQueryWrapperX<BillStreamDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(BillStreamDO::getId, streamIds);
        List<BillStreamDO> billStreamList = billStreamMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(billStreamList)) {
            if (billStreamList.size() != streamIds.size()) {
                throw new ServiceException(406, "当前选中的流水不存在,请刷新后重新操作");
            }
            long count = billStreamList.stream().filter(aa -> aa.getMatchStatus().equals("1")).count();
            if (count >= 1) {
                throw new ServiceException(406, "当前流水已匹配账单,请勿重复操作");
            }

            for (BillStreamDO billStreamDO : billStreamList) {
                OrgBillStreamMiddleDO orgBillStreamMiddleDO = new OrgBillStreamMiddleDO();
                orgBillStreamMiddleDO.setBillId(billId);
                ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billId);
                orgBillStreamMiddleDO.setBillType(Long.valueOf(contractOrderBillDO.getBillType()));
                orgBillStreamMiddleDO.setStreamId(billStreamDO.getId());
                orgBillStreamMiddleDO.setCompanyId(billStreamDO.getCompanyId());
                orgBillStreamMiddleDO.setCompanyName(billStreamDO.getCompanyName());
                orgBillStreamMiddleDO.setCostType(contractOrderBillDO.getFeeType());
                orgBillStreamMiddleDO.setIncomeDate(billStreamDO.getIncomeDate());
                orgBillStreamMiddleDO.setAmount(billStreamDO.getAmount());
                orgBillStreamMiddleDO.setMatchPrice(matchPrice);
                orgBillStreamMiddleDO.setNomatchPrice(new BigDecimal("0.00"));
                orgBillStreamMiddleDO.setMatchDate(parse);
                orgBillStreamMiddleDO.setMatchStatus("1");
                orgBillStreamMiddleDO.setIsConfirm("1");
                billStreamMiddleMapper.insert(orgBillStreamMiddleDO);

                OrgIncomeDO orgIncomeDO = new OrgIncomeDO();
                ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
                orgIncomeDO.setTenantId(contractDO.getTenantId());
                orgIncomeDO.setVillageId(contractDO.getParkId());
                orgIncomeDO.setContractId(contractDO.getId());
                orgIncomeDO.setBillId(contractOrderBillDO.getId());
                orgIncomeDO.setBillType(contractOrderBillDO.getBillType());
                orgIncomeDO.setContractNumber(contractDO.getContractNumber());
                orgIncomeDO.setBuildId(contractDO.getBuildId());
                OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
                if (ownerDO != null) {
                    orgIncomeDO.setIndustryId(ownerDO.getIndustryId());
                }

                orgIncomeDO.setRoomNumber(contractDO.getRoomNumber());
                orgIncomeDO.setOwnerId(contractDO.getOwnerId());
                orgIncomeDO.setTaxAmount(new BigDecimal(contractOrderBillDO.getTaxAmount()));
                orgIncomeDO.setTradeTime(contractOrderBillDO.getPayTime());
                orgIncomeDO.setIncomeType("3");
                orgIncomeDO.setIsConfirm("1");
                if (StringUtils.isNotEmpty(contractOrderBillDO.getFeeType())) {
                    orgIncomeDO.setCostType(contractOrderBillDO.getFeeType());
                }
                orgIncomeDO.setStreamMiddleId(orgBillStreamMiddleDO.getId());
                orgIncomeDO.setTradeAmount(orgBillStreamMiddleDO.getAmount());
                orgIncomeDO.setAmount(orgBillStreamMiddleDO.getAmount());
                orgIncomeDO.setIsUnrealRoom("0");
                orgIncomeMapper.insert(orgIncomeDO);

                //修改流水匹配金额
                billStreamDO.setMatchPrice(billStreamDO.getMatchPrice().add(matchPrice));
                billStreamDO.setMatchDate(parse);
                BigDecimal nomatchPrice = billStreamDO.getAmount().subtract(billStreamDO.getMatchPrice());
                billStreamDO.setNomatchPrice(nomatchPrice);
                if (nomatchPrice.compareTo(BigDecimal.ZERO) > 0) {
                    billStreamDO.setMatchStatus("3");
                } else {
                    billStreamDO.setMatchStatus("1");
                }
                billStreamDO.setCostType(contractOrderBillDO.getFeeType());
                billStreamMapper.updateById(billStreamDO);
            }


        }
    }

    /**
     * @param id
     * @param cancelMatchDate
     * @throws ParseException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelMatch(Long id, String cancelMatchDate) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sim.parse(cancelMatchDate);
        //修改中间表匹配状态为取消状态
        OrgBillStreamMiddleDO orgBillStreamMiddleDO = billStreamMiddleMapper.selectById(id);
        orgBillStreamMiddleDO.setCancelMatchDate(parse);
        orgBillStreamMiddleDO.setMatchStatus("4");
        billStreamMiddleMapper.updateById(orgBillStreamMiddleDO);
        //删除收入信息
        LambdaQueryWrapperX<OrgIncomeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgIncomeDO::getStreamMiddleId, id);
        orgIncomeMapper.delete(queryWrapperX);

        //恢复流水表数据
        BillStreamDO billStreamDO = billStreamMapper.selectById(orgBillStreamMiddleDO.getStreamId());
        BigDecimal amount = billStreamDO.getAmount();
        BigDecimal matchPrice = orgBillStreamMiddleDO.getMatchPrice();
        if (amount.equals(matchPrice)) {
            billStreamDO.setMatchPrice(matchPrice);
            billStreamDO.setNomatchPrice(new BigDecimal("0.00"));
            billStreamDO.setMatchStatus("1");
        } else {
            matchPrice = billStreamDO.getAmount().subtract(orgBillStreamMiddleDO.getMatchPrice());
            billStreamDO.setMatchPrice(matchPrice);
            BigDecimal nomatchPrice = billStreamDO.getAmount().subtract(matchPrice);
            billStreamDO.setNomatchPrice(nomatchPrice);
            billStreamDO.setMatchStatus("3");
        }
        billStreamDO.setCancelMatchDate(parse);
        billStreamDO.setBillId(null);
        billStreamDO.setVillageId(null);
        billStreamDO.setBuildId(null);
        billStreamDO.setRoomNumber(null);
        billStreamMapper.updateById(billStreamDO);
    }


}