package cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderAdminDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工单应用管理员信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface WorkOrderAdminMapper extends BaseMapperX<WorkOrderAdminDO> {

}