package cn.sdqingyun.smartpark.module.bus.service.contract;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import org.apache.ibatis.annotations.Param;

/**
 * 机构合同账单明细 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface ContractOrderBillService {

    /**
     * 创建机构合同账单明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ContractOrderBillSaveReqVO createReqVO);

    /**
     *
     * @param createBillVO
     * @return
     * @throws Exception
     */
    Long createBill(@Valid CreateBillVO createBillVO) throws Exception;

    /**
     * 更新机构合同账单明细
     *
     * @param updateReqVO 更新信息
     */
    void updateOrderBill(@Valid ContractOrderBillSaveReqVO updateReqVO);

    /**
     * 删除机构合同账单明细
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构合同账单明细
     *
     * @param id 编号
     * @return 机构合同账单明细
     */
    ContractOrderBillDO getOrderBill(Long id);


    /**
     * @param billIdList
     * @return
     */
    List<Long> getOrderBillContractIdList(List<String> billIdList);


    /**
     *
     * @param contractId
     * @return
     */
    List<ContractOrderBillDO> getByContractIdOrderBillList(Long contractId);


    /**
     * @param orderNumber
     * @return
     */
    ContractOrderBillDO getByOrderNumberInfo(String orderNumber);

    /**
     * 获得机构合同账单明细分页
     *
     * @param pageReqVO 分页查询
     * @return 机构合同账单明细分页
     */
    PageResult<ContractOrderBillDO> getOrderBillPage(ContractOrderBillPageReqVO pageReqVO);


    List<ContractOrderBillDO> getOrderBillList(LambdaQueryWrapper<ContractOrderBillDO> queryWrapper);

    /**
     * @return
     */
    public List<ContractOrderBillDO> getOrderBillBeOverdueList();

    /**
     * 查询企业账单
     *
     * @param contractId
     * @param startPayDate
     * @param endPayDate
     * @return
     */
    List<ContractIdCompyOrderBillListVO> getByContractIdCompyOrderBillList(Long contractId, String startPayDate, String endPayDate);

    /**
     * 生成账单
     *
     * @param dCount
     * @param termType
     * @param feeType
     * @param leaseStartDate
     * @param leaseEndDate
     * @param day
     * @param bondMoney
     * @param payDay
     * @param unitPricePoint
     * @param calculationOrder
     * @param taxInclusiveRules
     * @param taxInclusiveValue
     * @param totalArea
     * @param contractUnitPrice
     * @param dMoney
     * @param isReceivableInteger
     * @return
     * @throws ParseException
     */
    public List<ContractOrderBillSaveReqVO> getOrderBillList(
            Integer dCount,
            String termType,
            String feeType,
            String leaseStartDate,
            String leaseEndDate,
            int day,
            String bondMoney,
            int payDay,
            int unitPricePoint,
            String calculationOrder,
            String taxInclusiveRules,
            BigDecimal taxInclusiveValue,
            BigDecimal totalArea,
            String contractUnitPrice,
            BigDecimal dMoney,
            String isReceivableInteger
    ) throws ParseException;

    /**
     * @param contractId
     */
    void deleteByContractId(Long contractId);

    /**
     * @param contractId
     * @return
     */
    List<ContractOrderBillDO> getByContractIdRentingTerminationBondBillList(Long contractId);

    List<ContractOrderBillDO> getByContractIdRentingTerminationBillList(Long contractId);

    /**
     * @return
     */
    List<ContractOrderBillDO> getPaymentNoticeList();

    /**
     * @return
     */
    List<ContractOrderBillDO> getBeOverdueList();

    /**
     * @param id
     * @param billStatus
     * @param lateFee
     * @param overdueDay
     */
    void updateByIdBillLateFeeOverdue(Long id, String billStatus, String lateFee, Integer overdueDay);


    /**
     * @param ownerId
     * @param roomId
     * @param isDisplayReceived
     * @param isAccountsReceivable
     * @return
     */
    List<Map<String, Object>> getByOwnerIdRoomIdBillList(Long ownerId, Long roomId, String isDisplayReceived, String isAccountsReceivable);

    /**
     * @param ids
     * @param buildType
     * @return
     */
    Map<String, Object> getNotificationNumber(List<String> ids, String buildType);

    /**
     * @param idList
     * @return
     */
    List<BillListVO> getByIdBillList(List<String> idList);

    /**
     * @param ids
     * @return
     */
    Map<String, Object> getCollectionBillList(List<String> ids);

    /**
     * @param thisPayment
     * @param id
     */
    void updateOrderBillReceivablePayment(String thisPayment, Long id);


    /**
     * @param getBillListVO
     * @return
     */
    public PageResult<GetBillListVO> getBillList(GetBillListVO getBillListVO);

    /**
     * @param ownerId
     * @param roomId
     * @return
     */
    Map<String, Object> getOwnerIdOverdueIds(Long ownerId, Long roomId);

    /**
     *
     * @param pageVo
     * @return
     */
    PageResult<OwnerFinanceListVO> ownerFinanceList(OwnerFinanceListVO pageVo);

    /**
     * @param billId
     * @return
     */
    ContractOrderBillDO detail(Long billId);

    /**
     *
     * @param billId
     * @return
     */
    IncomeDetailVO incomeDetail(Long billId);

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    PageResult<AccountsReceivableReportVO> getAccountsReceivableReportPage(AccountsReceivableReportVO accountsReceivableReportVO);


    /**
     * @param billType
     * @param isBond
     * @param contractId
     * @param startPayDate
     * @param endPayDate
     * @return
     */
    List<BillTypeContractIdBillVO> getByBillTypeContractIdBillInfoList(String billType, String isBond, Long contractId, String startPayDate, String endPayDate);

    /**
     * @param contractId
     * @return
     */
    Map<String, Object> getByBillTypeContractIdBillMoneyTotal(Long contractId);

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    Map<String, Object> getAccountsReceivableSummary(AccountsReceivableReportVO accountsReceivableReportVO);

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    Map<String, Object> getProportionReceivedAmountMap(AccountsReceivableReportVO accountsReceivableReportVO);

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    Map<String, Object> getTrendCostTypeMap(AccountsReceivableReportVO accountsReceivableReportVO);

    /**
     * @param ownerId
     * @param isDisplayReceived
     * @param isAccountsReceivable
     * @return
     */
    List<Map<String, Object>> getByOwnerIdRoomIdCashierBillList(Long ownerId, String isDisplayReceived, String isAccountsReceivable);

    /**
     * @param billId
     * @param payType
     * @return
     */
    String scanCodeBillPay(String billId, String payType);

    /**
     *
     * @param contractOrderBillSaveReqVO
     */
    void close(ContractOrderBillSaveReqVO contractOrderBillSaveReqVO);

    /**
     *
     * @param billId
     * @return
     */
    BillInformationVO billInformation(Long billId);

    /**
     *
     * @param billId
     * @return
     */
    Map<String, Object> billDetails(Long billId);

    /**
     *
     * @param billId
     * @param lateFee
     */
    void collectLateFee(Long billId,BigDecimal lateFee);
}