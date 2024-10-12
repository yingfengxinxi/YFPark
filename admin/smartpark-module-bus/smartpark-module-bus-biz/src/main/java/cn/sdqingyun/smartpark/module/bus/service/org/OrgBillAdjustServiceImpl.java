package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.AdjustBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAdjustPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAdjustSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAdjustDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillAdjustMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_ADJUST_NOT_EXISTS;
import static com.baomidou.mybatisplus.core.toolkit.IdWorker.getId;

/**
 * 机构账单调整 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Transactional(readOnly = true)
public class OrgBillAdjustServiceImpl implements OrgBillAdjustService {

    @Resource
    private OrgBillAdjustMapper Mapper;

    @Autowired
    private ContractOrderBillMapper contractOrderBillMapper;

    /**
     * @param createReqVO 创建信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long create(OrgBillAdjustSaveReqVO createReqVO) throws Exception {

        LambdaQueryWrapperX<OrgBillAdjustDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillAdjustDO::getBillId, createReqVO.getBillId());
        queryWrapperX.eq(OrgBillAdjustDO::getAdjustStatus, "1");
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new RuntimeException("当前账单正在调整金额审批中,请勿重复操作");
        }

        // 插入
        OrgBillAdjustDO orgBillAdjustDO = BeanUtils.toBean(createReqVO, OrgBillAdjustDO.class);

        //校验调整金额
        getContractOrderBillDO(orgBillAdjustDO);


        orgBillAdjustDO.setAdjustStatus("1");
        ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(orgBillAdjustDO.getBillId());
        if (contractOrderBillDO.getBillStatus().equals("1")) {
            throw new RuntimeException("当前账单已支付，请勿调整金额");
        }
        orgBillAdjustDO.setBillType(contractOrderBillDO.getBillType());
        Mapper.insert(orgBillAdjustDO);
        // 返回
        return orgBillAdjustDO.getId();
    }

    /**
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approved(Long id) {
        OrgBillAdjustDO orgBillAdjustDO = Mapper.selectById(id);

        ContractOrderBillDO contractOrderBillDO = getContractOrderBillDO(orgBillAdjustDO);

        contractOrderBillMapper.updateById(contractOrderBillDO);
        //orgBillAdjustDO.setAdjustDate(new Date());
        orgBillAdjustDO.setAdjustStatus("2");
        Mapper.updateById(orgBillAdjustDO);
    }

    @NotNull
    private ContractOrderBillDO getContractOrderBillDO(OrgBillAdjustDO orgBillAdjustDO) {
        ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(orgBillAdjustDO.getBillId());

        BigDecimal receivable = new BigDecimal(contractOrderBillDO.getReceivable());
        orgBillAdjustDO.setAdjustBeforeAmount(receivable);
        //实收金额
        BigDecimal receivablePayment = new BigDecimal(contractOrderBillDO.getReceivablePayment());
        receivable = receivable.subtract(receivablePayment);
        if (StringUtils.equals(orgBillAdjustDO.getAdjustType(), "1")) {
            //调增
            if (StringUtils.equals(orgBillAdjustDO.getAdjustMode(), "0")) {
                //按金额调整
                int comparisonResult = receivable.compareTo(orgBillAdjustDO.getAdjustPrice());
                if (comparisonResult == 0 || comparisonResult == -1) {
                    contractOrderBillDO.setReceivable(Tools.DecimalFormat(new BigDecimal(contractOrderBillDO.getReceivable()).add(orgBillAdjustDO.getAdjustPrice())));
                } else {
                    throw new ServiceException(406, "调整金额不能小于应交金额");
                }
            }
            if (StringUtils.equals(orgBillAdjustDO.getAdjustMode(), "1")) {
                //按比例调整
                System.out.println("账单应收金额:" + receivable);
                //计算增加金额
                BigDecimal adjustProportion = new BigDecimal(orgBillAdjustDO.getAdjustProportion());
                System.out.println("调增比例:" + adjustProportion);
                BigDecimal proportion = adjustProportion.divide(new BigDecimal(100));
                System.out.println("调增比例/100:" + proportion);
                BigDecimal adjustPrice = receivable.multiply(proportion);
                System.out.println("账单应收金额*调增比例/100:" + adjustPrice);
                orgBillAdjustDO.setAdjustPrice(adjustPrice);
                contractOrderBillDO.setReceivable(Tools.DecimalFormat(new BigDecimal(contractOrderBillDO.getReceivable()).add(adjustPrice)));


            }
        }
        if (StringUtils.equals(orgBillAdjustDO.getAdjustType(), "2")) {
            //调减
            if (StringUtils.equals(orgBillAdjustDO.getAdjustMode(), "0")) {
                //按金额调整

                //按金额调整
                int comparisonResult = receivable.compareTo(orgBillAdjustDO.getAdjustPrice());
                if (comparisonResult == 0 || comparisonResult == -1) {
                    throw new ServiceException(406, "调整金额不能大于应交金额");
                } else {
                    contractOrderBillDO.setReceivable(Tools.DecimalFormat(new BigDecimal(contractOrderBillDO.getReceivable()).subtract(orgBillAdjustDO.getAdjustPrice())));
                }
            }
            if (StringUtils.equals(orgBillAdjustDO.getAdjustMode(), "1")) {
                //按比例调整

                //计算增加金额
                BigDecimal adjustProportion = new BigDecimal(orgBillAdjustDO.getAdjustProportion());
                BigDecimal proportion = adjustProportion.divide(new BigDecimal(100));
                BigDecimal adjustPrice = receivable.multiply(proportion);
                //应收金额大于等于调整金额
                int comparisonResult = receivable.compareTo(adjustPrice);
                if (comparisonResult == 0 || comparisonResult == -1) {
                    throw new ServiceException(406, "调整金额不能大于应交金额");
                }
                orgBillAdjustDO.setAdjustPrice(adjustPrice);
                contractOrderBillDO.setReceivable(Tools.DecimalFormat(new BigDecimal(contractOrderBillDO.getReceivable()).subtract(adjustPrice)));
            }
        }
        return contractOrderBillDO;
    }

    public static void main(String[] args) {
        BigDecimal receivable = new BigDecimal("1");
        BigDecimal adjustPrice = new BigDecimal("1.11");
        int comparisonResult = receivable.compareTo(adjustPrice);
        if (comparisonResult == 0 || comparisonResult == -1) {
            System.out.println("调整金额不能大于应交金额");
        }
    }

    /**
     * @param id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void applicationToVoid(Long id) throws Exception {
        // 校验存在
        OrgBillAdjustDO orgBillAdjustDO = Mapper.selectById(id);
        // 校验存在
        if (orgBillAdjustDO == null) {
            throw exception(BILL_ADJUST_NOT_EXISTS);
        }
        ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(orgBillAdjustDO.getBillId());
        if (contractOrderBillDO.getBillStatus().equals("1")) {
            throw new RuntimeException("当前订单已支付，申请作废调整失败!");
        }
        // 更新
        orgBillAdjustDO.setAdjustStatus("3");
        Mapper.updateById(orgBillAdjustDO);
    }

    /**
     * @param id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void voidAdjustmentApproval(Long id) throws Exception {
        // 校验存在
        OrgBillAdjustDO orgBillAdjustDO = Mapper.selectById(id);
        ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(orgBillAdjustDO.getBillId());
        contractOrderBillDO.setReceivable(orgBillAdjustDO.getAdjustBeforeAmount().toString());
        contractOrderBillMapper.updateById(contractOrderBillDO);
        orgBillAdjustDO.setAdjustStatus("4");
        orgBillAdjustDO.setCancelAdjustDate(new Date());
        Mapper.updateById(orgBillAdjustDO);
    }

    @Transactional(readOnly = false)
    @Override
    public void update(OrgBillAdjustSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillAdjustDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillAdjustDO.class);
        Mapper.updateById(updateObj);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(BILL_ADJUST_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillAdjustDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillAdjustDO> getPage(OrgBillAdjustPageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillAdjustDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillAdjustDO::getBillId, pageReqVO.getBillId());
        if (StringUtils.isEmpty(pageReqVO.getAdjustStatus())) {
            queryWrapperX.apply(" adjust_status !='4'");
        }
        queryWrapperX.orderByDesc(OrgBillAdjustDO::getAdjustDate);
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    /**
     * @param adjustBillListVO
     * @return
     */
    @Override
    public PageResult<AdjustBillListVO> getAdjustBillList(AdjustBillListVO adjustBillListVO) {
        IPage<AdjustBillListVO> adjustBillList = Mapper.getAdjustBillList(MyBatisUtils.buildPage(adjustBillListVO), adjustBillListVO);
        List<AdjustBillListVO> records = adjustBillList.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(adjustBillListVO1 -> {
                Long billId = adjustBillListVO1.getBillId();
                LambdaQueryWrapperX<OrgBillAdjustDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(OrgBillAdjustDO::getBillId, billId);
                List<OrgBillAdjustDO> billAdjustDOList = Mapper.selectList(queryWrapperX);
                if (CollectionUtils.isNotEmpty(billAdjustDOList)) {
                    adjustBillListVO1.setAdjustList(billAdjustDOList);
                }
            });
        }
        return new PageResult<>(adjustBillList.getRecords(), adjustBillList.getTotal());
    }

    /**
     * @param adjustBillListVO
     * @return
     */
    @Override
    public Map<String, Object> getAdjustBillTopStatistic(AdjustBillListVO adjustBillListVO) {
        adjustBillListVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        Map<String, Object> map = new HashMap<>();
        List<String> adjustTypeList = Lists.newArrayList();
        adjustTypeList.add("2");
        adjustTypeList.add("3");
        adjustBillListVO.setAdjustTypeList(adjustTypeList);
        IPage<AdjustBillListVO> adjustBillList = Mapper.getAdjustBillList(MyBatisUtils.buildPage(adjustBillListVO), adjustBillListVO);
        List<AdjustBillListVO> records = adjustBillList.getRecords();
        Long adjustContractNum = 0L;
        Long adjustBillNum = 0L;
        BigDecimal adjustPriceTotal = new BigDecimal("0.00");
        Integer adjustMode1 = 0;
        Integer adjustMode2 = 0;
        if (CollectionUtils.isNotEmpty(records)) {
            adjustContractNum = records.stream().map(AdjustBillListVO::getContractNumber).distinct().count();
            adjustBillNum = records.stream().map(AdjustBillListVO::getBillId).distinct().count();
            adjustPriceTotal = records.stream().
                    map(aa -> aa.getAdjustPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
            //按金额调整
            List<AdjustBillListVO> adjustMode1List = records.stream().filter(aa -> aa.getAdjustMode().equals("1")).collect(Collectors.toList());
            adjustMode1 = adjustMode1List.size();
            //按比例调整
            List<AdjustBillListVO> adjustMode2List = records.stream().filter(aa -> aa.getAdjustMode().equals("2")).collect(Collectors.toList());
            adjustMode2 = adjustMode2List.size();

        }
        //调整合同总数
        map.put("adjustContractNum", adjustContractNum);
        //调整总金额
        map.put("adjustPriceTotal", adjustPriceTotal);
        //调整账单总数
        map.put("adjustBillNum", adjustBillNum);
        //金额调整数
        map.put("adjustMode1", adjustMode1);
        //比例调整数
        map.put("adjustMode2", adjustMode2);
        return map;
    }

}