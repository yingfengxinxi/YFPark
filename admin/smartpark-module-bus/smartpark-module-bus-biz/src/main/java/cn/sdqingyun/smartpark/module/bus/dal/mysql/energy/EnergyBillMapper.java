package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;


import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyBillDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自定义账单 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyBillMapper extends BaseMapperX<EnergyBillDO> {


}