package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyConfigDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产配置信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyConfigMapper extends BaseMapperX<PropertyConfigDO> {

    default PageResult<PropertyConfigDO> selectPage(PropertyConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyConfigDO>()
                .eqIfPresent(PropertyConfigDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyConfigDO::getBuildBind, reqVO.getBuildBind())
                .eqIfPresent(PropertyConfigDO::getNumberRule, reqVO.getNumberRule())
                .eqIfPresent(PropertyConfigDO::getExtra, reqVO.getExtra())
                .eqIfPresent(PropertyConfigDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyConfigDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyConfigDO::getId));
    }

}