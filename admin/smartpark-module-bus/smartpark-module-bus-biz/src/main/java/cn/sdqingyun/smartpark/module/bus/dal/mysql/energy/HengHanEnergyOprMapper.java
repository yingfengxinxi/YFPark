package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HengHanEnergyOprDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电表操作id关联表、回调用 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface HengHanEnergyOprMapper extends BaseMapperX<HengHanEnergyOprDO> {


}