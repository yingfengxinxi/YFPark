package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceCategoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约场地分类 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvPlaceCategoryMapper extends BaseMapperX<ResvPlaceCategoryDO> {

    default PageResult<ResvPlaceCategoryDO> selectPage(ResvPlaceCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvPlaceCategoryDO>()
                .eqIfPresent(ResvPlaceCategoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvPlaceCategoryDO::getAppSign, reqVO.getAppSign())
                .eqIfPresent(ResvPlaceCategoryDO::getVillageId, reqVO.getVillageId())
                .likeIfPresent(ResvPlaceCategoryDO::getName, reqVO.getName())
                .eqIfPresent(ResvPlaceCategoryDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(ResvPlaceCategoryDO::getReservationRule, reqVO.getReservationRule())
                .eqIfPresent(ResvPlaceCategoryDO::getCapacity, reqVO.getCapacity())
                .eqIfPresent(ResvPlaceCategoryDO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(ResvPlaceCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(ResvPlaceCategoryDO::getDescription, reqVO.getDescription())
                .eqIfPresent(ResvPlaceCategoryDO::getCache, reqVO.getCache())
                .eqIfPresent(ResvPlaceCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ResvPlaceCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvPlaceCategoryDO::getId));
    }

}