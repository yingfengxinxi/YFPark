package cn.sdqingyun.smartpark.module.bus.dal.mysql.contract;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构合同账单明细 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface ContractOrderBillMapper extends BaseMapperX<ContractOrderBillDO> {


    /**
     * 查询企业账单
     *
     * @param contractId
     * @param startPayDate
     * @param endPayDate
     * @return
     */
    List<ContractIdCompyOrderBillListVO> getByContractIdCompyOrderBillList(@Param("contractId") Long contractId, @Param("startPayDate") String startPayDate, @Param("endPayDate") String endPayDate);


    List<ContractOrderBillDO> getByContractIdRentingTerminationBondBillList(@Param("contractId") Long contractId);

    List<ContractOrderBillDO> getByContractIdRentingTerminationBillList(@Param("contractId") Long contractId);

    /**
     * @return
     */
    List<ContractOrderBillDO> getPaymentNoticeList();

    /**
     * 逾期
     *
     * @return
     */
    List<ContractOrderBillDO> getBeOverdueList();

    /**
     * @param id
     * @param billStatus
     * @param lateFee
     * @param overdueDay
     */
    void updateByIdBillLateFeeOverdue(@Param("id") Long id, @Param("billStatus") String billStatus, @Param("lateFee") String lateFee, @Param("overdueDay") Integer overdueDay);


    /**
     * @param ownerId
     * @param roomId
     * @param billStatusList
     * @param isAccountsReceivable 已显示未到应收期账单0=隐藏1=显示
     * @return
     */
    List<ContractOrderBillDO> getByOwnerIdRoomIdBillList(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId, @Param("billStatusList") List<String> billStatusList, @Param("isAccountsReceivable") String isAccountsReceivable);

    /**
     * @param ownerId
     * @param billStatusList
     * @param isAccountsReceivable 已显示未到应收期账单0=隐藏1=显示
     * @return
     */
    List<ContractOrderBillDO> getByOwnerIdRoomIdCashierBillList(@Param("ownerId") Long ownerId, @Param("billStatusList") List<String> billStatusList, @Param("isAccountsReceivable") String isAccountsReceivable);

    /**
     * @param idList
     * @return
     */
    List<BillListVO> getByIdBillList(List<String> idList);


    /**
     * @param ids
     * @return
     */
    List<ContractOrderBillCollectionVO> getCollectionBillList(@Param("ids") List<String> ids);

    /**
     * @param thisPayment
     * @param id
     */
    void updateOrderBillReceivablePayment(@Param("thisPayment") String thisPayment, @Param("id") Long id);


    /**
     * @param page
     * @param ownerId
     * @param billType
     * @param roomNumberList
     * @return
     */
    IPage<GetBillListVO> getBillList(Page<?> page,
                                     @Param("ownerId") Long ownerId,
                                     @Param("billType") String billType
    );

    /**
     * @param ownerId
     * @param roomId
     * @return
     */
    List<Long> getOwnerIdOverdueIds(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId);

    /**
     * 需收金额
     *
     * @param ownerId
     * @param roomId
     * @return
     */
    BigDecimal getOwnerIdAmountCollectedTotal(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId);

    /**
     * 应收金额
     *
     * @param ownerId
     * @param roomId
     * @return
     */
    BigDecimal getOwnerIdReceivableTotal(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId);


    /**
     *
     * @param page
     * @param pageVo
     * @return
     */
    IPage<OwnerFinanceListVO> ownerFinanceList(Page<?> page, @Param("param") OwnerFinanceListVO pageVo);

    /**
     * @param page
     * @param accountsReceivableReportVO
     * @return
     */
    IPage<AccountsReceivableReportVO> getAccountsReceivableReportList(Page<?> page, @Param("param") AccountsReceivableReportVO accountsReceivableReportVO);


    /**
     * @param billType
     * @param isBond
     * @param contractId
     * @param startPayDate
     * @param endPayDate
     * @return
     */
    List<BillTypeContractIdBillVO> getByBillTypeContractIdBillInfoList(@Param("billType") String billType, @Param("isBond") String isBond, @Param("contractId") Long contractId, @Param("startPayDate") String startPayDate, @Param("endPayDate") String endPayDate);


    /**
     * @param contractId
     * @param billType
     * @return
     */
    BigDecimal getReceivableTotalMoney(@Param("contractId") Long contractId, @Param("billType") String billType);

    /**
     * @param contractId
     * @param billType
     * @return
     */
    BigDecimal getReceivablePaymentTotalMoney(@Param("contractId") Long contractId, @Param("billType") String billType);

    /**
     * @param contractId
     * @param closeStatus
     */
    void updateByContractIdCloseStatus(@Param("contractId") Long contractId, @Param("closeStatus") String closeStatus);

    /**
     * @param contractId
     */
    void deleteByContractId(@Param("contractId") Long contractId);
}