package cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPositionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检点位数据 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PatrolPositionMapper extends BaseMapperX<PatrolPositionDO> {

}