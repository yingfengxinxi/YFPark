package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;


import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyCallbackRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 终端数据推送记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyCallbackRecordMapper extends BaseMapperX<EnergyCallbackRecordDO> {

}