package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomRentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 租客在租/绑定房间 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface RoomRentMapper extends BaseMapperX<RoomRentDO> {

    default PageResult<RoomRentDO> selectPage(RoomRentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomRentDO>()
                .eqIfPresent(RoomRentDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(RoomRentDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(RoomRentDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(RoomRentDO::getLayerId, reqVO.getLayerId())
                .eqIfPresent(RoomRentDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(RoomRentDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(RoomRentDO::getContractId, reqVO.getContractId())
                .betweenIfPresent(RoomRentDO::getExpireTime, reqVO.getExpireTime())
                .eqIfPresent(RoomRentDO::getSource, reqVO.getSource())
                .eqIfPresent(RoomRentDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RoomRentDO::getExtra, reqVO.getExtra())
                .betweenIfPresent(RoomRentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoomRentDO::getId));
    }

}