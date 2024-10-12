package cn.sdqingyun.smartpark.module.bus.dal.mysql.park;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkChargeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 停车场收费标准 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ParkChargeMapper extends BaseMapperX<ParkChargeDO> {

}