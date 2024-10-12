package cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderHourDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工单工时配置 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface WorkOrderHourMapper extends BaseMapperX<WorkOrderHourDO> {

}