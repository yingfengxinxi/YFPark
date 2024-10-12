package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目类型 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface VillageTypeMapper extends BaseMapperX<VillageTypeDO> {

    default PageResult<VillageTypeDO> selectPage(VillageTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VillageTypeDO>()
                .likeIfPresent(VillageTypeDO::getName, reqVO.getName())
                .eqIfPresent(VillageTypeDO::getAlias, reqVO.getAlias())
                .eqIfPresent(VillageTypeDO::getBgImg, reqVO.getBgImg())
                .eqIfPresent(VillageTypeDO::getIconImg, reqVO.getIconImg())
                .eqIfPresent(VillageTypeDO::getMenu, reqVO.getMenu())
                .eqIfPresent(VillageTypeDO::getFilterMenu, reqVO.getFilterMenu())
                .betweenIfPresent(VillageTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VillageTypeDO::getId));
    }

}