package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomLockLogsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目房间锁定日志 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface RoomLockLogsMapper extends BaseMapperX<RoomLockLogsDO> {

    default PageResult<RoomLockLogsDO> selectPage(RoomLockLogsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomLockLogsDO>()
                .eqIfPresent(RoomLockLogsDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(RoomLockLogsDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(RoomLockLogsDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RoomLockLogsDO::getReason, reqVO.getReason())
                .betweenIfPresent(RoomLockLogsDO::getLockTime, reqVO.getLockTime())
                .eqIfPresent(RoomLockLogsDO::getOperationUid, reqVO.getOperationUid())
                .betweenIfPresent(RoomLockLogsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoomLockLogsDO::getId));
    }

}