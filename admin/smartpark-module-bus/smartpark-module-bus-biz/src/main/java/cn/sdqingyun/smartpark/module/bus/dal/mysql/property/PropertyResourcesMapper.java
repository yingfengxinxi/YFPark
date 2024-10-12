package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyResourcesDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产静态资源管理 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyResourcesMapper extends BaseMapperX<PropertyResourcesDO> {

    default PageResult<PropertyResourcesDO> selectPage(PropertyResourcesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyResourcesDO>()
                .eqIfPresent(PropertyResourcesDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyResourcesDO::getType, reqVO.getType())
                .eqIfPresent(PropertyResourcesDO::getUrl, reqVO.getUrl())
                .eqIfPresent(PropertyResourcesDO::getOssHash, reqVO.getOssHash())
                .eqIfPresent(PropertyResourcesDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyResourcesDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyResourcesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyResourcesDO::getId));
    }

}