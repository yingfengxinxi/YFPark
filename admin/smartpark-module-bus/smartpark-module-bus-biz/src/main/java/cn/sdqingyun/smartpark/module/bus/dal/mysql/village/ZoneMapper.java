package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ZoneDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目分区 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface ZoneMapper extends BaseMapperX<ZoneDO> {

    default PageResult<ZoneDO> selectPage(ZonePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZoneDO>()
                .eqIfPresent(ZoneDO::getZoneNumber, reqVO.getZoneNumber())
                .likeIfPresent(ZoneDO::getZoneName, reqVO.getZoneName())
                .eqIfPresent(ZoneDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ZoneDO::getSort, reqVO.getSort())
                .eqIfPresent(ZoneDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ZoneDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZoneDO::getId));
    }

}