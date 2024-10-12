package cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用巡检任务日志 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PatrolTaskEquipmentLogMapper extends BaseMapperX<PatrolTaskEquipmentLogDO> {

}