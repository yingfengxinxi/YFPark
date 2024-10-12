package cn.sdqingyun.smartpark.module.bus.dal.mysql.pay;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderWorkOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付订单和工单订单记录中间 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PayOrderWorkOrderMapper extends BaseMapperX<PayOrderWorkOrderDO> {

    /**
     *
     * @param merchantOrderId
     * @return
     */
    Integer getByMerchantOrderIdStatus(String merchantOrderId);
}