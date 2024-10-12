package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.PhoneCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目电话类型 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface PhoneCategoryMapper extends BaseMapperX<PhoneCategoryDO> {

    default PageResult<PhoneCategoryDO> selectPage(PhoneCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PhoneCategoryDO>()
                .eqIfPresent(PhoneCategoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PhoneCategoryDO::getVillageId, reqVO.getVillageId())
                .likeIfPresent(PhoneCategoryDO::getCatName, reqVO.getCatName())
                .eqIfPresent(PhoneCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(PhoneCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PhoneCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PhoneCategoryDO::getId));
    }

}