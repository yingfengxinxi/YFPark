package cn.sdqingyun.smartpark.module.bus.dal.mysql.pay;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderBillDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 支付订单和账单表中间表	 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PayOrderBillMapper extends BaseMapperX<PayOrderBillDO> {

    /**
     *
     * @param merchantOrderId
     * @return
     */
    Integer getByMerchantOrderIdStatus(@Param("merchantOrderId") String merchantOrderId);

}