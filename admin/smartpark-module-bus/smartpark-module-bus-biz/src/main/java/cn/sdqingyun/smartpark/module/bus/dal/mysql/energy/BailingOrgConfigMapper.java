package cn.sdqingyun.smartpark.module.bus.dal.mysql.energy;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.BailingOrgConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 智能表参数配置 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BailingOrgConfigMapper extends BaseMapperX<BailingOrgConfigDO> {

}