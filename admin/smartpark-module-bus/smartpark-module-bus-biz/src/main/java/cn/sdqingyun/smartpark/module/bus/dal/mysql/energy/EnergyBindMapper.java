package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyBindDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自定义关联分总 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EnergyBindMapper extends BaseMapperX<EnergyBindDO> {

}