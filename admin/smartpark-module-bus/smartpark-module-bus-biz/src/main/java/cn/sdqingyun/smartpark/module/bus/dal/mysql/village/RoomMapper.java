package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目房间 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface RoomMapper extends BaseMapperX<RoomDO> {

    default PageResult<RoomDO> selectPage(RoomPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomDO>()
                .eqIfPresent(RoomDO::getRoomNumber, reqVO.getRoomNumber())
                .likeIfPresent(RoomDO::getRoomName, reqVO.getRoomName())
                .eqIfPresent(RoomDO::getRoomAliasId, reqVO.getRoomAliasId())
                .eqIfPresent(RoomDO::getParentRoomId, reqVO.getParentRoomId())
                .eqIfPresent(RoomDO::getSubRoomCount, reqVO.getSubRoomCount())
                .eqIfPresent(RoomDO::getSubRoomRentCount, reqVO.getSubRoomRentCount())
                .eqIfPresent(RoomDO::getBuildArea, reqVO.getBuildArea())
                .eqIfPresent(RoomDO::getInsideArea, reqVO.getInsideArea())
                .eqIfPresent(RoomDO::getRentalArea, reqVO.getRentalArea())
                .eqIfPresent(RoomDO::getRentalAreaIn, reqVO.getRentalAreaIn())
                .eqIfPresent(RoomDO::getChargingArea, reqVO.getChargingArea())
                .eqIfPresent(RoomDO::getChargingAreaIn, reqVO.getChargingAreaIn())
                .eqIfPresent(RoomDO::getLeaseStart, reqVO.getLeaseStart())
                .eqIfPresent(RoomDO::getLeaseEnd, reqVO.getLeaseEnd())
                .betweenIfPresent(RoomDO::getDeliverTime, reqVO.getDeliverTime())
                .eqIfPresent(RoomDO::getInvitationStatus, reqVO.getInvitationStatus())
                .eqIfPresent(RoomDO::getInvestmentPolicy, reqVO.getInvestmentPolicy())
                .eqIfPresent(RoomDO::getInvestmentConditions, reqVO.getInvestmentConditions())
                .eqIfPresent(RoomDO::getImages, reqVO.getImages())
                .eqIfPresent(RoomDO::getPriceUnit, reqVO.getPriceUnit())
                .eqIfPresent(RoomDO::getPriceUnitMin, reqVO.getPriceUnitMin())
                .eqIfPresent(RoomDO::getPreUnitPrice, reqVO.getPreUnitPrice())
                .eqIfPresent(RoomDO::getPreUnitPriceMin, reqVO.getPreUnitPriceMin())
                .eqIfPresent(RoomDO::getTagIdArr, reqVO.getTagIdArr())
                .eqIfPresent(RoomDO::getContractInfo, reqVO.getContractInfo())
                .eqIfPresent(RoomDO::getContractCount, reqVO.getContractCount())
                .eqIfPresent(RoomDO::getDecoration, reqVO.getDecoration())
                .eqIfPresent(RoomDO::getPropertyRight, reqVO.getPropertyRight())
                .eqIfPresent(RoomDO::getFloorHeight, reqVO.getFloorHeight())
                .eqIfPresent(RoomDO::getLoadMax, reqVO.getLoadMax())
                .eqIfPresent(RoomDO::getLayerId, reqVO.getLayerId())
                .eqIfPresent(RoomDO::getUnitId, reqVO.getUnitId())
                .eqIfPresent(RoomDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(RoomDO::getZoneId, reqVO.getZoneId())
                .eqIfPresent(RoomDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(RoomDO::getSort, reqVO.getSort())
                .eqIfPresent(RoomDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RoomDO::getThreeDimensionalId, reqVO.getThreeDimensionalId())
                .eqIfPresent(RoomDO::getRoomStatus, reqVO.getRoomStatus())
                .eqIfPresent(RoomDO::getHouseType, reqVO.getHouseType())
                .eqIfPresent(RoomDO::getRecordNo, reqVO.getRecordNo())
                .eqIfPresent(RoomDO::getPromoterMoney, reqVO.getPromoterMoney())
                .eqIfPresent(RoomDO::getPromoterMoneyUnit, reqVO.getPromoterMoneyUnit())
                .eqIfPresent(RoomDO::getExtraConfig, reqVO.getExtraConfig())
                .eqIfPresent(RoomDO::getVrLink, reqVO.getVrLink())
                .eqIfPresent(RoomDO::getVideo, reqVO.getVideo())
                .eqIfPresent(RoomDO::getVrVideoSort, reqVO.getVrVideoSort())
                .eqIfPresent(RoomDO::getMonthHits, reqVO.getMonthHits())
                .eqIfPresent(RoomDO::getSplitParentArea, reqVO.getSplitParentArea())
                .eqIfPresent(RoomDO::getIsLock, reqVO.getIsLock())
                .eqIfPresent(RoomDO::getIsUnreal, reqVO.getIsUnreal())
                .eqIfPresent(RoomDO::getExtraLock, reqVO.getExtraLock())
                .betweenIfPresent(RoomDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoomDO::getId));
    }

}