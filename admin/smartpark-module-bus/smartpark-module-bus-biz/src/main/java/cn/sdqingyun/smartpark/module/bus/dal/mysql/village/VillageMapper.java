package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 社区 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface VillageMapper extends BaseMapperX<VillageDO> {

    default PageResult<VillageDO> selectPage(VillagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VillageDO>()
                .likeIfPresent(VillageDO::getName, reqVO.getName())
                .likeIfPresent(VillageDO::getShortName, reqVO.getShortName())
                .eqIfPresent(VillageDO::getDescribe, reqVO.getDescribe())
                .eqIfPresent(VillageDO::getLogo, reqVO.getLogo())
                .eqIfPresent(VillageDO::getDistrictTxt, reqVO.getDistrictTxt())
                .eqIfPresent(VillageDO::getAddress, reqVO.getAddress())
                .eqIfPresent(VillageDO::getLng, reqVO.getLng())
                .eqIfPresent(VillageDO::getLat, reqVO.getLat())
                .eqIfPresent(VillageDO::getManagementArea, reqVO.getManagementArea())
                .eqIfPresent(VillageDO::getRentableArea, reqVO.getRentableArea())
                .eqIfPresent(VillageDO::getRoomRentableCount, reqVO.getRoomRentableCount())
                .eqIfPresent(VillageDO::getRoomCount, reqVO.getRoomCount())
                .eqIfPresent(VillageDO::getTagIdArr, reqVO.getTagIdArr())
                .eqIfPresent(VillageDO::getWechatNumber, reqVO.getWechatNumber())
                .eqIfPresent(VillageDO::getStatus, reqVO.getStatus())
                .eqIfPresent(VillageDO::getThreeDimensionalFile, reqVO.getThreeDimensionalFile())
                .eqIfPresent(VillageDO::getThreeDimensionalId, reqVO.getThreeDimensionalId())
                .eqIfPresent(VillageDO::getDimensionalBgImg, reqVO.getDimensionalBgImg())
                .eqIfPresent(VillageDO::getRoomStatusColor, reqVO.getRoomStatusColor())
                .eqIfPresent(VillageDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(VillageDO::getCountryId, reqVO.getCountryId())
                .eqIfPresent(VillageDO::getProvinceId, reqVO.getProvinceId())
                .eqIfPresent(VillageDO::getCityId, reqVO.getCityId())
                .eqIfPresent(VillageDO::getDistrictId, reqVO.getDistrictId())
                .eqIfPresent(VillageDO::getStreetId, reqVO.getStreetId())
                .eqIfPresent(VillageDO::getCommunityId, reqVO.getCommunityId())
                .eqIfPresent(VillageDO::getRoomMinPrice, reqVO.getRoomMinPrice())
                .eqIfPresent(VillageDO::getRoomAveragePrice, reqVO.getRoomAveragePrice())
                .eqIfPresent(VillageDO::getTrafficInfo, reqVO.getTrafficInfo())
                .eqIfPresent(VillageDO::getType, reqVO.getType())
                .eqIfPresent(VillageDO::getExtraConfig, reqVO.getExtraConfig())
                .eqIfPresent(VillageDO::getVrLink, reqVO.getVrLink())
                .eqIfPresent(VillageDO::getVideo, reqVO.getVideo())
                .eqIfPresent(VillageDO::getVrVideoSort, reqVO.getVrVideoSort())
                .eqIfPresent(VillageDO::getMonthHits, reqVO.getMonthHits())
                .eqIfPresent(VillageDO::getSort, reqVO.getSort())
                .eqIfPresent(VillageDO::getMicroServiceConfig, reqVO.getMicroServiceConfig())
                .betweenIfPresent(VillageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VillageDO::getId));
    }

}