package cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工单操作日志 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface WorkOrderProposeLogMapper extends BaseMapperX<WorkOrderProposeLogDO> {


}