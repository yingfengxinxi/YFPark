package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPriceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自定义价格标准 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyPriceMapper extends BaseMapperX<EnergyPriceDO> {

}