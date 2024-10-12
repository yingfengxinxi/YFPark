package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingOrderRefundDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约订单退款 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvBookingOrderRefundMapper extends BaseMapperX<ResvBookingOrderRefundDO> {

    default PageResult<ResvBookingOrderRefundDO> selectPage(ResvBookingOrderRefundPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvBookingOrderRefundDO>()
                .eqIfPresent(ResvBookingOrderRefundDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvBookingOrderRefundDO::getAppSign, reqVO.getAppSign())
                .eqIfPresent(ResvBookingOrderRefundDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ResvBookingOrderRefundDO::getBookingId, reqVO.getBookingId())
                .eqIfPresent(ResvBookingOrderRefundDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(ResvBookingOrderRefundDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(ResvBookingOrderRefundDO::getAmount, reqVO.getAmount())
                .eqIfPresent(ResvBookingOrderRefundDO::getActualAmount, reqVO.getActualAmount())
                .eqIfPresent(ResvBookingOrderRefundDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ResvBookingOrderRefundDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(ResvBookingOrderRefundDO::getHandler, reqVO.getHandler())
                .likeIfPresent(ResvBookingOrderRefundDO::getHandlerName, reqVO.getHandlerName())
                .betweenIfPresent(ResvBookingOrderRefundDO::getHandleTime, reqVO.getHandleTime())
                .eqIfPresent(ResvBookingOrderRefundDO::getRefundType, reqVO.getRefundType())
                .eqIfPresent(ResvBookingOrderRefundDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ResvBookingOrderRefundDO::getErrorMsg, reqVO.getErrorMsg())
                .betweenIfPresent(ResvBookingOrderRefundDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvBookingOrderRefundDO::getId));
    }

}