package cn.sdqingyun.smartpark.module.bus.dal.mysql.park;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 月租车白名单 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ParkCarsMapper extends BaseMapperX<ParkCarsDO> {

}