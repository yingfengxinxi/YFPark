package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 项目用户/租客 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface VillageUserMapper extends BaseMapperX<VillageUserDO> {

    default PageResult<VillageUserDO> selectPage(VillageUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VillageUserDO>()
                .eqIfPresent(VillageUserDO::getUniqidId, reqVO.getUniqidId())
                .eqIfPresent(VillageUserDO::getUserId, reqVO.getUserId())
                .eqIfPresent(VillageUserDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(VillageUserDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(VillageUserDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(VillageUserDO::getVillageId, reqVO.getVillageId())
                .likeIfPresent(VillageUserDO::getName, reqVO.getName())
                .eqIfPresent(VillageUserDO::getPhone, reqVO.getPhone())
                .eqIfPresent(VillageUserDO::getEmail, reqVO.getEmail())
                .eqIfPresent(VillageUserDO::getAddress, reqVO.getAddress())
                .eqIfPresent(VillageUserDO::getIsDefault, reqVO.getIsDefault())
                .eqIfPresent(VillageUserDO::getType, reqVO.getType())
                .eqIfPresent(VillageUserDO::getEffectiveTimeStart, reqVO.getEffectiveTimeStart())
                .eqIfPresent(VillageUserDO::getEffectiveTimeEnd, reqVO.getEffectiveTimeEnd())
                .eqIfPresent(VillageUserDO::getIdcardType, reqVO.getIdcardType())
                .eqIfPresent(VillageUserDO::getIdcard, reqVO.getIdcard())
                .eqIfPresent(VillageUserDO::getBirthYear, reqVO.getBirthYear())
                .eqIfPresent(VillageUserDO::getBirthMonth, reqVO.getBirthMonth())
                .eqIfPresent(VillageUserDO::getBirthDay, reqVO.getBirthDay())
                .eqIfPresent(VillageUserDO::getSex, reqVO.getSex())
                .eqIfPresent(VillageUserDO::getEduType, reqVO.getEduType())
                .eqIfPresent(VillageUserDO::getWorkYear, reqVO.getWorkYear())
                .eqIfPresent(VillageUserDO::getGradSchool, reqVO.getGradSchool())
                .eqIfPresent(VillageUserDO::getSkCert, reqVO.getSkCert())
                .eqIfPresent(VillageUserDO::getIdcardImg, reqVO.getIdcardImg())
                .eqIfPresent(VillageUserDO::getPhoto, reqVO.getPhoto())
                .eqIfPresent(VillageUserDO::getPhotoStatus, reqVO.getPhotoStatus())
                .eqIfPresent(VillageUserDO::getPhotoStatusDesc, reqVO.getPhotoStatusDesc())
                .eqIfPresent(VillageUserDO::getInAdvance, reqVO.getInAdvance())
                .eqIfPresent(VillageUserDO::getInAdvanceNotice, reqVO.getInAdvanceNotice())
                .eqIfPresent(VillageUserDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(VillageUserDO::getLastChooseTime, reqVO.getLastChooseTime())
                .betweenIfPresent(VillageUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VillageUserDO::getId));
    }

    /**
     *
     * @param phone
     * @return
     */
    List<VillageUserDO> getByPhoneOwnerList(@Param("phone") String phone);
}