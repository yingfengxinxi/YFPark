package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingVerificationDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约核销 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvBookingVerificationMapper extends BaseMapperX<ResvBookingVerificationDO> {

    default PageResult<ResvBookingVerificationDO> selectPage(ResvBookingVerificationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvBookingVerificationDO>()
                .eqIfPresent(ResvBookingVerificationDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvBookingVerificationDO::getAppSign, reqVO.getAppSign())
                .eqIfPresent(ResvBookingVerificationDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ResvBookingVerificationDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(ResvBookingVerificationDO::getBookingId, reqVO.getBookingId())
                .eqIfPresent(ResvBookingVerificationDO::getCode, reqVO.getCode())
                .eqIfPresent(ResvBookingVerificationDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ResvBookingVerificationDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ResvBookingVerificationDO::getHandler, reqVO.getHandler())
                .likeIfPresent(ResvBookingVerificationDO::getHandlerName, reqVO.getHandlerName())
                .betweenIfPresent(ResvBookingVerificationDO::getHandleTime, reqVO.getHandleTime())
                .betweenIfPresent(ResvBookingVerificationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvBookingVerificationDO::getId));
    }

}