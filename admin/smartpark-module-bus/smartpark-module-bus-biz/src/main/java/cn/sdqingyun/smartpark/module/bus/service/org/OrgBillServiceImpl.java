package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillCollectionAllListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillImportVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.VillageUserImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageUserDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillStreamMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CoverUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/7/15 15:18
 */
@Service
public class OrgBillServiceImpl implements OrgBillService {

    @Autowired
    private OrgBillMapper orgBillMapper;

    @Autowired
    private ContractOrderBillMapper contractOrderBillMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;


    @Override
    public PageResult<BillCollectionAllListVO> getBillCollectionAllListPage(BillCollectionAllListVO billCollectionAllListVO) {

        if (!billCollectionAllListVO.getIsShow()) {
            Date payDate = DateUtils.getDayTime();
            billCollectionAllListVO.setPayDate(payDate);
        }

        IPage<BillCollectionAllListVO> billCollectionAllListPage = orgBillMapper.getBillCollectionAllListPage(MyBatisUtils.buildPage(billCollectionAllListVO), billCollectionAllListVO);

        extracted(billCollectionAllListPage);

        return new PageResult<>(billCollectionAllListPage.getRecords(), billCollectionAllListPage.getTotal());
    }

    private static void extracted(IPage<BillCollectionAllListVO> billCollectionAllListPage) {
        List<BillCollectionAllListVO> records = billCollectionAllListPage.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(billCollectionAllListVO1 -> {
                String billStatus = billCollectionAllListVO1.getBillStatus();
                if (StringUtils.isNotEmpty(billStatus)) {
                    String billStatusName = DictFrameworkUtils.getDictDataLabel("BILL_STATUS", billStatus);
                    billCollectionAllListVO1.setBillStatusName(billStatusName);
                }

                String billSource = billCollectionAllListVO1.getBillSource();
                if (StringUtils.isNotEmpty(billSource)) {
                    String billSourceName = DictFrameworkUtils.getDictDataLabel("BILL_SOURCE", billSource);
                    billCollectionAllListVO1.setBillSourceName(billSourceName);
                }

                String billType = billCollectionAllListVO1.getBillType();
                if (StringUtils.isNotEmpty(billType)) {
                    String billTypeName = DictFrameworkUtils.getDictDataLabel("BILL_TYPE", billType);
                    billCollectionAllListVO1.setBillTypeName(billTypeName);
                }

                billCollectionAllListVO1.setNoticeCount(Integer.valueOf(billCollectionAllListVO1.getNoticeCount()) >= 1 ? "已发送" : "未发送");
            });
        }
    }

    /**
     * @param billCollectionAllListVO
     * @return
     */
    @Override
    public Map<String, Object> getBillCollectionAllTotalMoney(BillCollectionAllListVO billCollectionAllListVO) {
        Map<String, Object> map = new HashMap<>();
        //收款金额
        if (!billCollectionAllListVO.getIsShow()) {
            Date payDate = DateUtils.getDayTime();
            billCollectionAllListVO.setPayDate(payDate);
        }
        //应收
        BigDecimal receivable = new BigDecimal("0.00");
        //滞纳金
        BigDecimal lateFee = new BigDecimal("0.00");
        //实收金额
        BigDecimal receivablePayment = new BigDecimal("0.00");
        //调增金额
        BigDecimal jiaAdjustPrice = new BigDecimal("0.00");
        //调减金额
        BigDecimal jianAdjustPrice = new BigDecimal("0.00");
        //需收金额
        BigDecimal collectedPrice = new BigDecimal("0.00");
        //需收笔数
        Integer collectedCount = 0;
        IPage<BillCollectionAllListVO> billCollectionAllListPage = orgBillMapper.getBillCollectionAllListPage(MyBatisUtils.buildPage(billCollectionAllListVO), billCollectionAllListVO);
        List<BillCollectionAllListVO> records = billCollectionAllListPage.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            //应收金额
            receivable = receivable.add(records.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getReceivable()), BigDecimal::add));
            //滞纳金
            lateFee = lateFee.add(records.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getLateFee()), BigDecimal::add));
            //实收金额
            receivablePayment = receivablePayment.add(records.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getReceivablePayment()), BigDecimal::add));
            //调增金额
            jiaAdjustPrice = jiaAdjustPrice.add(records.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getJiaAdjustPrice()), BigDecimal::add));
            //调减金额
            jianAdjustPrice = jiaAdjustPrice.add(records.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getJianAdjustPrice()), BigDecimal::add));

            List<BillCollectionAllListVO> collectedList = records.stream().filter(vo -> !vo.getBillStatus().equals("1")).collect(Collectors.toList());
            collectedCount = collectedList.size();
            if (CollectionUtils.isNotEmpty(collectedList)) {
                //计算需收金额
                BigDecimal reduce = collectedList.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getReceivable()), BigDecimal::add);
                BigDecimal reduce1 = collectedList.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getLateFee()), BigDecimal::add);
                BigDecimal reduce2 = collectedList.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getReceivablePayment()), BigDecimal::add);
                collectedPrice = collectedPrice.add(new BigDecimal(Tools.DecimalFormat(reduce.add(reduce1).subtract(reduce2))));
            }
        }
        //应收金额
        map.put("receivable", receivable);
        //应收笔数
        map.put("receivableCount", records.size());
        //滞纳金
        map.put("lateFee", lateFee);
        //调增金额
        map.put("jiaAdjustPrice", jiaAdjustPrice);
        //调减金额
        map.put("jianAdjustPrice", jianAdjustPrice);
        //实收金额
        map.put("receivablePayment", receivablePayment);
        //需收金额
        map.put("collectedPrice", collectedPrice);
        //需收笔数
        map.put("collectedCount", collectedCount);

        return map;
    }

    /**
     * @param billCollectionAllListVO
     * @return
     */
    @Override
    public PageResult<BillCollectionAllListVO> getBillBeOverdueListPage(BillCollectionAllListVO billCollectionAllListVO) {


        IPage<BillCollectionAllListVO> billCollectionAllListPage = orgBillMapper.getBillBeOverdueListPage(MyBatisUtils.buildPage(billCollectionAllListVO), billCollectionAllListVO);
        extracted(billCollectionAllListPage);

        List<BillCollectionAllListVO> records = billCollectionAllListPage.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(billCollectionAllListVO1 -> {
                //计算预期天数
                Long dayCount = DateUtils.getDayCount(billCollectionAllListVO1.getPayDate(), new Date()) + 1;
                billCollectionAllListVO1.setOverdueDay(dayCount);
            });
        }
        return new PageResult<>(billCollectionAllListPage.getRecords(), billCollectionAllListPage.getTotal());
    }

    /**
     * @param billCollectionAllListVO
     * @return
     */
    @Override
    public Map<String, Object> getBillBeOverdueTotalMoney(BillCollectionAllListVO billCollectionAllListVO) {
        Map<String, Object> map = new HashMap<>();
        IPage<BillCollectionAllListVO> billCollectionAllListPage = orgBillMapper.getBillBeOverdueListPage(MyBatisUtils.buildPage(billCollectionAllListVO), billCollectionAllListVO);
        List<BillCollectionAllListVO> records = billCollectionAllListPage.getRecords();
        BigDecimal receivable = new BigDecimal("0.00");
        BigDecimal lateFee = new BigDecimal("0.00");
        Integer ownerCount = 0;
        if (CollectionUtils.isNotEmpty(records)) {
            receivable = receivable.add(records.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getReceivable()), BigDecimal::add));
            lateFee = lateFee.add(records.stream().reduce(BigDecimal.ZERO, (sum, vo) -> sum.add(vo.getLateFee()), BigDecimal::add));
            ownerCount = records.stream().collect(Collectors.groupingBy(BillCollectionAllListVO::getOwnerId)).size();
        }
        //逾期收款单数
        map.put("overdueNumber", records.size());
        //逾期收款金额
        map.put("receivable", receivable);
        //逾期租客数量
        map.put("ownerCount", ownerCount);
        //滞纳金额金额
        map.put("lateFee", lateFee);
        return map;
    }

    @Override
    public Boolean importExcel(List<BillImportVO> list, HttpServletResponse response) {
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        List<ContractOrderBillDO> billList = Lists.newArrayList();
        list.forEach(item -> {
            StringBuilder builder = new StringBuilder();
            if (StringUtils.isEmpty(item.getContractNumber())) {
                flag.set(false);
                builder.append("对应合同(合同编号)不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            ContractDO contractDO = contractMapper.selectOne(new LambdaQueryWrapperX<ContractDO>().eq(ContractDO::getContractNumber, item.getContractNumber()));

            if (contractDO == null) {
                flag.set(false);
                builder.append("合同编号不存在,请检查合同编号是否正确/");
                throw new ServiceException(406, builder.toString());
            }


            if (!StringUtils.equals(contractDO.getStatus(), "13")) {
                flag.set(false);
                builder.append("当前合同编号【" + contractDO.getContractNumber() + "】状态非执行中,请核对合同状态/");
                throw new ServiceException(406, builder.toString());
            }
            String billType = item.getBillType();

            if (StringUtils.isEmpty(billType)) {
                flag.set(false);
                builder.append("账单类型不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.equals(billType, "收款")) {
                billType = "1";
            } else if (StringUtils.equals(billType, "付款")) {
                billType = "2";
            } else {
                billType = "";
            }
            if (StringUtils.isEmpty(billType)) {
                flag.set(false);
                builder.append("请输入正确的账单类型/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isBlank(item.getFeeTypeName())) {
                flag.set(false);
                builder.append("费用类型不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            List<OrgBillCostTypeDO> orgBillCostTypeDOS = orgBillCostTypeMapper.selectList(new LambdaQueryWrapperX<OrgBillCostTypeDO>().like(OrgBillCostTypeDO::getName, item.getFeeTypeName()));
            if (orgBillCostTypeDOS.size() <= 0) {
                flag.set(false);
                builder.append("费用类型不存在,请在财务->费用类型中核对是否正确/");
                throw new ServiceException(406, builder.toString());
            }
            if (item.getReceivable() == null) {
                flag.set(false);
                builder.append("应收金额不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            if (item.getReceivable().compareTo(BigDecimal.ZERO) <= 0) {
                flag.set(false);
                builder.append("应交金额请输入大于0的数值金额/");
                throw new ServiceException(406, builder.toString());
            }

            if (item.getReceivablePayment() != null) {
                if (item.getReceivablePayment().compareTo(item.getReceivable()) == 1) {
                    flag.set(false);
                    builder.append("已交金额不能大于应交金额/");
                    throw new ServiceException(406, builder.toString());
                }
            }
            String billingCycle = item.getBillingCycle();
            if (StringUtils.isEmpty(billingCycle)) {
                flag.set(false);
                builder.append("计费周期不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            List<String> billingCycleList = List.of(billingCycle.split(","));
            if (billingCycleList.size() <= 1) {
                flag.set(false);
                builder.append("请输入正确格式的计费周期/");
                throw new ServiceException(406, builder.toString());
            }
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            ContractOrderBillDO contractOrderBillDO = new ContractOrderBillDO();
            try {
                String format1 = billingCycleList.get(0);
                contractOrderBillDO.setPayStartDate(sim.parse(format1));
                String format2 = billingCycleList.get(1);
                contractOrderBillDO.setPayStartDate(sim.parse(format2));
            } catch (ParseException e) {
                e.printStackTrace();
                builder.append("请输入正确格式的计费周期/");
                throw new ServiceException(406, builder.toString());

            }

            if (StringUtils.isEmpty(item.getPayDate())) {
                flag.set(false);
                builder.append("应交时间不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            try {
                contractOrderBillDO.setPayDate(sim.parse(item.getPayDate()));
            } catch (ParseException e) {
                builder.append("请输入正确格式的应交时间/");
                throw new ServiceException(406, builder.toString());
            }

            contractOrderBillDO.setContractId(contractDO.getId());
            contractOrderBillDO.setOrderNumber(UuidUtils.generateUuid().replaceAll("-", ""));
            contractOrderBillDO.setFeeType(String.valueOf(orgBillCostTypeDOS.get(0).getId()));
            contractOrderBillDO.setReceivable(String.valueOf(item.getReceivable()));
            if (StringUtils.isNotEmpty(String.valueOf(item.getReceivablePayment()))) {
                contractOrderBillDO.setReceivablePayment(String.valueOf(item.getReceivablePayment()));
                if (item.getReceivablePayment().compareTo(item.getReceivable()) == 0) {
                    contractOrderBillDO.setBillStatus("1");
                }
            } else {
                contractOrderBillDO.setBillStatus("0");
            }
            contractOrderBillDO.setBillType(billType);
            contractOrderBillDO.setDataSource("2");
            contractOrderBillDO.setClauseType("1");
            contractOrderBillDO.setBillSource("1");
            contractOrderBillDO.setRemark(item.getRemarks());
            contractOrderBillDO.setCloseStatus("1");
            billList.add(contractOrderBillDO);
            item.setImportResult(String.valueOf(builder));
        });
        if (flag.get()) {
            billList.forEach(item -> {
                //根据名称查询ID
                contractOrderBillMapper.insert(item);
            });
            return true;
        } else {
            try {
                ExcelUtils.write(response, "账单列表导入错误.xls", "账单列表列表", BillImportVO.class, list);
            } catch (Exception e) {
                throw new ServiceException(406, "账单列表导入错误");
            }
            return false;
        }
    }
}
