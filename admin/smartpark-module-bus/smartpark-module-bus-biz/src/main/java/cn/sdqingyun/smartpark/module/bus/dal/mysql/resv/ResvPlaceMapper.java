package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlacePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约场地 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvPlaceMapper extends BaseMapperX<ResvPlaceDO> {

    default PageResult<ResvPlaceDO> selectPage(ResvPlacePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvPlaceDO>()
                .eqIfPresent(ResvPlaceDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvPlaceDO::getAppSign, reqVO.getAppSign())
                .eqIfPresent(ResvPlaceDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ResvPlaceDO::getCategoryId, reqVO.getCategoryId())
                .likeIfPresent(ResvPlaceDO::getName, reqVO.getName())
                .eqIfPresent(ResvPlaceDO::getImages, reqVO.getImages())
                .eqIfPresent(ResvPlaceDO::getAddress, reqVO.getAddress())
                .eqIfPresent(ResvPlaceDO::getAddressRest, reqVO.getAddressRest())
                .eqIfPresent(ResvPlaceDO::getLatitude, reqVO.getLatitude())
                .eqIfPresent(ResvPlaceDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(ResvPlaceDO::getFacilityArr, reqVO.getFacilityArr())
                .eqIfPresent(ResvPlaceDO::getBillRuleId, reqVO.getBillRuleId())
                .eqIfPresent(ResvPlaceDO::getSort, reqVO.getSort())
                .eqIfPresent(ResvPlaceDO::getNotifier, reqVO.getNotifier())
                .eqIfPresent(ResvPlaceDO::getNotifierData, reqVO.getNotifierData())
                .eqIfPresent(ResvPlaceDO::getDescription, reqVO.getDescription())
                .eqIfPresent(ResvPlaceDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ResvPlaceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvPlaceDO::getId));
    }

}