package cn.sdqingyun.smartpark.module.bus.dal.mysql.bill;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillStreamDeleteHistoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDeleteHistoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流水删除历史 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface BillStreamDeleteHistoryMapper extends BaseMapperX<BillStreamDeleteHistoryDO> {

    default PageResult<BillStreamDeleteHistoryDO> selectPage(BillStreamDeleteHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BillStreamDeleteHistoryDO>()
                .eqIfPresent(BillStreamDeleteHistoryDO::getStreamId, reqVO.getStreamId())
                .eqIfPresent(BillStreamDeleteHistoryDO::getApprovalId, reqVO.getApprovalId())
                .eqIfPresent(BillStreamDeleteHistoryDO::getApprovalStatus, reqVO.getApprovalStatus())
                .eqIfPresent(BillStreamDeleteHistoryDO::getType, reqVO.getType())
                .betweenIfPresent(BillStreamDeleteHistoryDO::getApprovalTime, reqVO.getApprovalTime())
                .betweenIfPresent(BillStreamDeleteHistoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BillStreamDeleteHistoryDO::getId));
    }

}