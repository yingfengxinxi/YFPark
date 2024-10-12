package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTypeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表种类管理 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyTypeMapper extends BaseMapperX<EnergyTypeDO> {


}