package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageCollectionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目集合表，方便快速选择 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface VillageCollectionMapper extends BaseMapperX<VillageCollectionDO> {

    default PageResult<VillageCollectionDO> selectPage(VillageCollectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VillageCollectionDO>()
                .eqIfPresent(VillageCollectionDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(VillageCollectionDO::getUid, reqVO.getUid())
                .likeIfPresent(VillageCollectionDO::getCollectionName, reqVO.getCollectionName())
                .eqIfPresent(VillageCollectionDO::getCollectionBuild, reqVO.getCollectionBuild())
                .eqIfPresent(VillageCollectionDO::getVillageType, reqVO.getVillageType())
                .betweenIfPresent(VillageCollectionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VillageCollectionDO::getId));
    }

}