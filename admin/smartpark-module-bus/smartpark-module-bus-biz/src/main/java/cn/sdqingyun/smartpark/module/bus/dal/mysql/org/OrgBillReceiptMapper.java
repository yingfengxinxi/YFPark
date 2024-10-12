package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构账单收据 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgBillReceiptMapper extends BaseMapperX<OrgBillReceiptDO> {

    /**
     *
     * @param contractId
     * @param status
     */
    void updateByBillIdStatus(@Param("contractId") Long contractId, @Param("status") String status);
}