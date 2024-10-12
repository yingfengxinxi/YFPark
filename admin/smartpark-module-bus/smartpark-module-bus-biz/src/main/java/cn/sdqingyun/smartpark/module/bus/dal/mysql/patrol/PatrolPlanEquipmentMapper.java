package cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanEquipmentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用巡检计划 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PatrolPlanEquipmentMapper extends BaseMapperX<PatrolPlanEquipmentDO> {

}