package cn.sdqingyun.smartpark.module.bus.dal.mysql.bill;

import java.math.BigDecimal;
import java.util.*;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OwnerIdLoanTypeInfoListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OwnerIdStreamTotalMoneyVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构账单收支流水 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface BillStreamMapper extends BaseMapperX<BillStreamDO> {


    /**
     * @param ownerId
     * @return
     */
    List<OwnerIdStreamTotalMoneyVO> getOwnerIdStreamTotalMoney(@Param("ownerId") Long ownerId);

    /**
     * @param ownerId
     * @param tenantId
     * @param loanType
     * @return
     */
    List<OwnerIdLoanTypeInfoListVO> getOwnerIdLoanTypeInfoList(@Param("ownerId") Long ownerId, @Param("loanType") String loanType);


    /**
     * @param ownerId
     * @param roomId
     * @return
     */
    BigDecimal getOwnerIdAmountTotal(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId);

    /**
     * 未匹配金额
     *
     * @param ownerId
     * @param roomId
     * @return
     */
    BigDecimal getOwnerIdNomatchPriceTotal(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId);

    /**
     * @param contractId
     * @param isClose
     * @param closeReason
     */
    void updateByContractIdIsClose(@Param("contractId") Long contractId, @Param("isClose") String isClose, @Param("closeReason") String closeReason);
}