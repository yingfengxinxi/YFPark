package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPayOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPayOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单支付 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvPayOrderMapper extends BaseMapperX<ResvPayOrderDO> {

    default PageResult<ResvPayOrderDO> selectPage(ResvPayOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvPayOrderDO>()
                .eqIfPresent(ResvPayOrderDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvPayOrderDO::getAppSign, reqVO.getAppSign())
                .eqIfPresent(ResvPayOrderDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ResvPayOrderDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(ResvPayOrderDO::getOrderIds, reqVO.getOrderIds())
                .eqIfPresent(ResvPayOrderDO::getPayAmount, reqVO.getPayAmount())
                .eqIfPresent(ResvPayOrderDO::getPayOrderNo, reqVO.getPayOrderNo())
                .eqIfPresent(ResvPayOrderDO::getPayMethod, reqVO.getPayMethod())
                .eqIfPresent(ResvPayOrderDO::getPayMethodTxt, reqVO.getPayMethodTxt())
                .betweenIfPresent(ResvPayOrderDO::getPayTime, reqVO.getPayTime())
                .eqIfPresent(ResvPayOrderDO::getPayStatus, reqVO.getPayStatus())
                .eqIfPresent(ResvPayOrderDO::getThirdOrderNo, reqVO.getThirdOrderNo())
                .eqIfPresent(ResvPayOrderDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ResvPayOrderDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(ResvPayOrderDO::getResponseParams, reqVO.getResponseParams())
                .betweenIfPresent(ResvPayOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvPayOrderDO::getId));
    }

}