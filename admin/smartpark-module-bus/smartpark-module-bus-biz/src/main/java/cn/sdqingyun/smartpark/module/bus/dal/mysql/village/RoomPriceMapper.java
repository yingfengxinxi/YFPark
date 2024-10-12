package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPriceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 房间价格 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface RoomPriceMapper extends BaseMapperX<RoomPriceDO> {

    default PageResult<RoomPriceDO> selectPage(RoomPricePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomPriceDO>()
                .eqIfPresent(RoomPriceDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(RoomPriceDO::getCreatedDay, reqVO.getCreatedDay())
                .eqIfPresent(RoomPriceDO::getBuildArea, reqVO.getBuildArea())
                .eqIfPresent(RoomPriceDO::getSquareDay, reqVO.getSquareDay())
                .eqIfPresent(RoomPriceDO::getSquareMonth, reqVO.getSquareMonth())
                .eqIfPresent(RoomPriceDO::getDay, reqVO.getDay())
                .eqIfPresent(RoomPriceDO::getMonth, reqVO.getMonth())
                .eqIfPresent(RoomPriceDO::getYear, reqVO.getYear())
                .eqIfPresent(RoomPriceDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RoomPriceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoomPriceDO::getId));
    }

}