package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目楼栋 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface BuildMapper extends BaseMapperX<BuildDO> {

    default PageResult<BuildDO> selectPage(BuildPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BuildDO>()
                .eqIfPresent(BuildDO::getBuildNumber, reqVO.getBuildNumber())
                .likeIfPresent(BuildDO::getBuildName, reqVO.getBuildName())
                .eqIfPresent(BuildDO::getLogo, reqVO.getLogo())
                .eqIfPresent(BuildDO::getZoneId, reqVO.getZoneId())
                .eqIfPresent(BuildDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(BuildDO::getHaveUnit, reqVO.getHaveUnit())
                .eqIfPresent(BuildDO::getDistrictTxt, reqVO.getDistrictTxt())
                .eqIfPresent(BuildDO::getCountryId, reqVO.getCountryId())
                .eqIfPresent(BuildDO::getProvinceId, reqVO.getProvinceId())
                .eqIfPresent(BuildDO::getCityId, reqVO.getCityId())
                .eqIfPresent(BuildDO::getDistrictId, reqVO.getDistrictId())
                .eqIfPresent(BuildDO::getStreetId, reqVO.getStreetId())
                .eqIfPresent(BuildDO::getCommunityId, reqVO.getCommunityId())
                .eqIfPresent(BuildDO::getLng, reqVO.getLng())
                .eqIfPresent(BuildDO::getLat, reqVO.getLat())
                .eqIfPresent(BuildDO::getAddress, reqVO.getAddress())
                .eqIfPresent(BuildDO::getFloorHeight, reqVO.getFloorHeight())
                .eqIfPresent(BuildDO::getPropertyRight, reqVO.getPropertyRight())
                .eqIfPresent(BuildDO::getBuildArea, reqVO.getBuildArea())
                .eqIfPresent(BuildDO::getPropertyArea, reqVO.getPropertyArea())
                .eqIfPresent(BuildDO::getRentableArea, reqVO.getRentableArea())
                .eqIfPresent(BuildDO::getSelfUseArea, reqVO.getSelfUseArea())
                .eqIfPresent(BuildDO::getSupportingArea, reqVO.getSupportingArea())
                .eqIfPresent(BuildDO::getPropertyNumber, reqVO.getPropertyNumber())
                .eqIfPresent(BuildDO::getLandNumber, reqVO.getLandNumber())
                .eqIfPresent(BuildDO::getEstateNumber, reqVO.getEstateNumber())
                .eqIfPresent(BuildDO::getParkingArea, reqVO.getParkingArea())
                .eqIfPresent(BuildDO::getParkingCount, reqVO.getParkingCount())
                .eqIfPresent(BuildDO::getManagementArea, reqVO.getManagementArea())
                .eqIfPresent(BuildDO::getRoomCount, reqVO.getRoomCount())
                .eqIfPresent(BuildDO::getRentInArea, reqVO.getRentInArea())
                .eqIfPresent(BuildDO::getRentInRoom, reqVO.getRentInRoom())
                .eqIfPresent(BuildDO::getRentInContract, reqVO.getRentInContract())
                .eqIfPresent(BuildDO::getInvitationRoomCount, reqVO.getInvitationRoomCount())
                .eqIfPresent(BuildDO::getRevenueTarget, reqVO.getRevenueTarget())
                .eqIfPresent(BuildDO::getAccountDefault, reqVO.getAccountDefault())
                .eqIfPresent(BuildDO::getExtraConfig, reqVO.getExtraConfig())
                .eqIfPresent(BuildDO::getTagInfo, reqVO.getTagInfo())
                .eqIfPresent(BuildDO::getIsHot, reqVO.getIsHot())
                .eqIfPresent(BuildDO::getSort, reqVO.getSort())
                .eqIfPresent(BuildDO::getStatus, reqVO.getStatus())
                .eqIfPresent(BuildDO::getThreeDimensionalFile, reqVO.getThreeDimensionalFile())
                .eqIfPresent(BuildDO::getThreeDimensionalId, reqVO.getThreeDimensionalId())
                .eqIfPresent(BuildDO::getDimensionalBgImg, reqVO.getDimensionalBgImg())
                .betweenIfPresent(BuildDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BuildDO::getId));
    }

}