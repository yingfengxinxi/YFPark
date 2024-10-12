package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 自定义抄表记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyRecordMapper extends BaseMapperX<EnergyRecordDO> {


}