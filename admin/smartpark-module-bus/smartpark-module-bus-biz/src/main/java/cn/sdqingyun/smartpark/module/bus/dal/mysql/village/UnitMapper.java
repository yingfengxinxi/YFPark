package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UnitDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目单元 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface UnitMapper extends BaseMapperX<UnitDO> {

    default PageResult<UnitDO> selectPage(UnitPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UnitDO>()
                .eqIfPresent(UnitDO::getUnitNumber, reqVO.getUnitNumber())
                .likeIfPresent(UnitDO::getUnitName, reqVO.getUnitName())
                .eqIfPresent(UnitDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(UnitDO::getZoneId, reqVO.getZoneId())
                .eqIfPresent(UnitDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(UnitDO::getSort, reqVO.getSort())
                .eqIfPresent(UnitDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(UnitDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UnitDO::getId));
    }

}