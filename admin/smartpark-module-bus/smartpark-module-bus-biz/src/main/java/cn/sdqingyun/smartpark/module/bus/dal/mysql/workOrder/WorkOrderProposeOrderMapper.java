package cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 付费工单订单 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface WorkOrderProposeOrderMapper extends BaseMapperX<WorkOrderProposeOrderDO> {

}