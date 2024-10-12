package cn.sdqingyun.smartpark.module.bus.dal.mysql.park;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 停车场（一个项目可以有多个场） Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ParkMapper extends BaseMapperX<ParkDO> {

}