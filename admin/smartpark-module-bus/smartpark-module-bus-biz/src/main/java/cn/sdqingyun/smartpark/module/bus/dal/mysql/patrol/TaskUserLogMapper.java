package cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol;


import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.TaskUserLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用巡检任务变更执行人日志 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TaskUserLogMapper extends BaseMapperX<TaskUserLogDO> {

}