package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillagePhoneDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目电话 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface VillagePhoneMapper extends BaseMapperX<VillagePhoneDO> {

    default PageResult<VillagePhoneDO> selectPage(VillagePhonePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VillagePhoneDO>()
                .eqIfPresent(VillagePhoneDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(VillagePhoneDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(VillagePhoneDO::getCatId, reqVO.getCatId())
                .likeIfPresent(VillagePhoneDO::getPhoneName, reqVO.getPhoneName())
                .eqIfPresent(VillagePhoneDO::getPhone, reqVO.getPhone())
                .eqIfPresent(VillagePhoneDO::getSort, reqVO.getSort())
                .eqIfPresent(VillagePhoneDO::getStatus, reqVO.getStatus())
                .eqIfPresent(VillagePhoneDO::getUrgent, reqVO.getUrgent())
                .betweenIfPresent(VillagePhoneDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VillagePhoneDO::getSort));
    }

}