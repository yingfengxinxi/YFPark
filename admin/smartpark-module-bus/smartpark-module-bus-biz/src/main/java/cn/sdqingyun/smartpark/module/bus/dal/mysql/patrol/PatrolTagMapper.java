package cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTagDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检标签模板 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PatrolTagMapper extends BaseMapperX<PatrolTagDO> {

}