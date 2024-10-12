package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;

/**
 * 预约 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvBookingMapper extends BaseMapperX<ResvBookingDO> {

    default PageResult<ResvBookingDO> selectPage(ResvBookingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvBookingDO>()
                .eqIfPresent(ResvBookingDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvBookingDO::getAppSign, reqVO.getAppSign())
                .eqIfPresent(ResvBookingDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ResvBookingDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(ResvBookingDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(ResvBookingDO::getPlaceId, reqVO.getPlaceId())
                .eqIfPresent(ResvBookingDO::getCapacity, reqVO.getCapacity())
                .betweenIfPresent(ResvBookingDO::getDate, reqVO.getDate())
                .eqIfPresent(ResvBookingDO::getTimeSlots, reqVO.getTimeSlots())
                .betweenIfPresent(ResvBookingDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(ResvBookingDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(ResvBookingDO::getWeekText, reqVO.getWeekText())
                .betweenIfPresent(ResvBookingDO::getRemindTime, reqVO.getRemindTime())
                .eqIfPresent(ResvBookingDO::getReminded, reqVO.getReminded())
                .eqIfPresent(ResvBookingDO::getRemarks, reqVO.getRemarks())
                .eqIfPresent(ResvBookingDO::getUserId, reqVO.getUserId())
                .likeIfPresent(ResvBookingDO::getUserName, reqVO.getUserName())
                .eqIfPresent(ResvBookingDO::getUserMobile, reqVO.getUserMobile())
                .eqIfPresent(ResvBookingDO::getOwnerId, reqVO.getOwnerId())
                .likeIfPresent(ResvBookingDO::getOwnerName, reqVO.getOwnerName())
                .eqIfPresent(ResvBookingDO::getRest, reqVO.getRest())
                .eqIfPresent(ResvBookingDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ResvBookingDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvBookingDO::getId));
    }

}