package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPageDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 招商中心装修页 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface RoomPageMapper extends BaseMapperX<RoomPageDO> {

    default PageResult<RoomPageDO> selectPage(RoomPagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomPageDO>()
                .eqIfPresent(RoomPageDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(RoomPageDO::getContent, reqVO.getContent())
                .betweenIfPresent(RoomPageDO::getLastTime, reqVO.getLastTime())
                .eqIfPresent(RoomPageDO::getIsMobile, reqVO.getIsMobile())
                .eqIfPresent(RoomPageDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RoomPageDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(RoomPageDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(RoomPageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoomPageDO::getId));
    }

}