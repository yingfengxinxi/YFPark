package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingOrderDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;

/**
 * 预约订单 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvBookingOrderMapper extends BaseMapperX<ResvBookingOrderDO> {

    default PageResult<ResvBookingOrderDO> selectPage(ResvBookingOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvBookingOrderDO>()
                .eqIfPresent(ResvBookingOrderDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvBookingOrderDO::getAppSign, reqVO.getAppSign())
                .eqIfPresent(ResvBookingOrderDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ResvBookingOrderDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(ResvBookingOrderDO::getOrderTotal, reqVO.getOrderTotal())
                .eqIfPresent(ResvBookingOrderDO::getDiscountAmount, reqVO.getDiscountAmount())
                .eqIfPresent(ResvBookingOrderDO::getRefundAmount, reqVO.getRefundAmount())
                .eqIfPresent(ResvBookingOrderDO::getPayOrderNo, reqVO.getPayOrderNo())
                .eqIfPresent(ResvBookingOrderDO::getPayAmount, reqVO.getPayAmount())
                .eqIfPresent(ResvBookingOrderDO::getPayMethod, reqVO.getPayMethod())
                .eqIfPresent(ResvBookingOrderDO::getPayMethodTxt, reqVO.getPayMethodTxt())
                .betweenIfPresent(ResvBookingOrderDO::getPayTime, reqVO.getPayTime())
                .betweenIfPresent(ResvBookingOrderDO::getEffectivePayTime, reqVO.getEffectivePayTime())
                .eqIfPresent(ResvBookingOrderDO::getPayStatus, reqVO.getPayStatus())
                .eqIfPresent(ResvBookingOrderDO::getThirdOrderNo, reqVO.getThirdOrderNo())
                .eqIfPresent(ResvBookingOrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(ResvBookingOrderDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ResvBookingOrderDO::getOwnerId, reqVO.getOwnerId())
                .betweenIfPresent(ResvBookingOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvBookingOrderDO::getId));
    }

}