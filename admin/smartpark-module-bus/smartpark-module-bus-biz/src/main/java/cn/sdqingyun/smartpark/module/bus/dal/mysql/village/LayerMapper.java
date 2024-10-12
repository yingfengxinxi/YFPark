package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目楼层 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface LayerMapper extends BaseMapperX<LayerDO> {

    default PageResult<LayerDO> selectPage(LayerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LayerDO>()
                .eqIfPresent(LayerDO::getLayerNumber, reqVO.getLayerNumber())
                .likeIfPresent(LayerDO::getLayerName, reqVO.getLayerName())
                .eqIfPresent(LayerDO::getUnitId, reqVO.getUnitId())
                .eqIfPresent(LayerDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(LayerDO::getZoneId, reqVO.getZoneId())
                .eqIfPresent(LayerDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(LayerDO::getSort, reqVO.getSort())
                .eqIfPresent(LayerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(LayerDO::getThreeDimensionalFile, reqVO.getThreeDimensionalFile())
                .eqIfPresent(LayerDO::getThreeDimensionalId, reqVO.getThreeDimensionalId())
                .betweenIfPresent(LayerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(LayerDO::getId));
    }

}