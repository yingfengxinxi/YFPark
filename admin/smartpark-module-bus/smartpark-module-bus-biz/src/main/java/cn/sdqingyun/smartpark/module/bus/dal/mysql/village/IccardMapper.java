package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.IccardDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目IC卡表（可能会绑定人员，因不同设备需要而定） Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface IccardMapper extends BaseMapperX<IccardDO> {

    default PageResult<IccardDO> selectPage(IccardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IccardDO>()
                .eqIfPresent(IccardDO::getCardId, reqVO.getCardId())
                .eqIfPresent(IccardDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(IccardDO::getLayerId, reqVO.getLayerId())
                .eqIfPresent(IccardDO::getUnitId, reqVO.getUnitId())
                .eqIfPresent(IccardDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(IccardDO::getZoneId, reqVO.getZoneId())
                .eqIfPresent(IccardDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(IccardDO::getRoomUserId, reqVO.getRoomUserId())
                .eqIfPresent(IccardDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(IccardDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IccardDO::getId));
    }

}