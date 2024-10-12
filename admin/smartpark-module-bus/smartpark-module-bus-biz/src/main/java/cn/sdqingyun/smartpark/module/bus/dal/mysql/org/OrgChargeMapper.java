package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgChargeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收费标准 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgChargeMapper extends BaseMapperX<OrgChargeDO> {

}