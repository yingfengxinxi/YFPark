package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRoomPriceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房间对应自定义价格 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyRoomPriceMapper extends BaseMapperX<EnergyRoomPriceDO> {

}