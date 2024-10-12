package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvApplicationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;

/**
 * 预约应用 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvApplicationMapper extends BaseMapperX<ResvApplicationDO> {

    default PageResult<ResvApplicationDO> selectPage(ResvApplicationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvApplicationDO>()
                .likeIfPresent(ResvApplicationDO::getName, reqVO.getName())
                .likeIfPresent(ResvApplicationDO::getShortName, reqVO.getShortName())
                .eqIfPresent(ResvApplicationDO::getSign, reqVO.getSign())
                .eqIfPresent(ResvApplicationDO::getIcon, reqVO.getIcon())
                .eqIfPresent(ResvApplicationDO::getCancelOrderRule, reqVO.getCancelOrderRule())
                .eqIfPresent(ResvApplicationDO::getRefundSupported, reqVO.getRefundSupported())
                .eqIfPresent(ResvApplicationDO::getRefundRule, reqVO.getRefundRule())
                .betweenIfPresent(ResvApplicationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvApplicationDO::getId));
    }

}