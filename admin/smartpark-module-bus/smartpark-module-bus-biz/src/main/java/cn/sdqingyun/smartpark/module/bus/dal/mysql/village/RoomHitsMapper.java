package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomHitsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 房间点击量 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface RoomHitsMapper extends BaseMapperX<RoomHitsDO> {

    default PageResult<RoomHitsDO> selectPage(RoomHitsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomHitsDO>()
                .eqIfPresent(RoomHitsDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(RoomHitsDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(RoomHitsDO::getDay, reqVO.getDay())
                .eqIfPresent(RoomHitsDO::getHits, reqVO.getHits())
                .betweenIfPresent(RoomHitsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoomHitsDO::getId));
    }

}