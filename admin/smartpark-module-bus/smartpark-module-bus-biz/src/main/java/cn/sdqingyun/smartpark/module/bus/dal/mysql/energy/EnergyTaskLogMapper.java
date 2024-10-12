package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;


import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自定义抄表任务日志 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyTaskLogMapper extends BaseMapperX<EnergyTaskLogDO> {

}