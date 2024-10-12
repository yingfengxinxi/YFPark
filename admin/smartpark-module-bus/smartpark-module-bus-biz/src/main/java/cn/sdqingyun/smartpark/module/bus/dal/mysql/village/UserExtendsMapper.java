package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserExtendsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 用户信息扩展 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface UserExtendsMapper extends BaseMapperX<UserExtendsDO> {

    default PageResult<UserExtendsDO> selectPage(UserExtendsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserExtendsDO>()
                .eqIfPresent(UserExtendsDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(UserExtendsDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(UserExtendsDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserExtendsDO::getVillageUserId, reqVO.getVillageUserId())
                .eqIfPresent(UserExtendsDO::getNation, reqVO.getNation())
                .eqIfPresent(UserExtendsDO::getEducation, reqVO.getEducation())
                .eqIfPresent(UserExtendsDO::getMajor, reqVO.getMajor())
                .eqIfPresent(UserExtendsDO::getJobTitle, reqVO.getJobTitle())
                .eqIfPresent(UserExtendsDO::getPoliticalStatus, reqVO.getPoliticalStatus())
                .eqIfPresent(UserExtendsDO::getMaritalStatus, reqVO.getMaritalStatus())
                .eqIfPresent(UserExtendsDO::getCareLevel, reqVO.getCareLevel())
                .eqIfPresent(UserExtendsDO::getLivingConditions, reqVO.getLivingConditions())
                .eqIfPresent(UserExtendsDO::getServiceType, reqVO.getServiceType())
                .eqIfPresent(UserExtendsDO::getFailure, reqVO.getFailure())
                .eqIfPresent(UserExtendsDO::getChronic, reqVO.getChronic())
                .eqIfPresent(UserExtendsDO::getBloodType, reqVO.getBloodType())
                .eqIfPresent(UserExtendsDO::getDisabled, reqVO.getDisabled())
                .eqIfPresent(UserExtendsDO::getCustody, reqVO.getCustody())
                .eqIfPresent(UserExtendsDO::getContacTel, reqVO.getContacTel())
                .eqIfPresent(UserExtendsDO::getPosition, reqVO.getPosition())
                .likeIfPresent(UserExtendsDO::getCallName, reqVO.getCallName())
                .eqIfPresent(UserExtendsDO::getCompanyFax, reqVO.getCompanyFax())
                .eqIfPresent(UserExtendsDO::getRemark, reqVO.getRemark())
                .eqIfPresent(UserExtendsDO::getQq, reqVO.getQq())
                .eqIfPresent(UserExtendsDO::getAliwangwang, reqVO.getAliwangwang())
                .eqIfPresent(UserExtendsDO::getWeixin, reqVO.getWeixin())
                .eqIfPresent(UserExtendsDO::getOtherContacMethod, reqVO.getOtherContacMethod())
                .eqIfPresent(UserExtendsDO::getHomeAddress, reqVO.getHomeAddress())
                .eqIfPresent(UserExtendsDO::getHomeTel, reqVO.getHomeTel())
                .eqIfPresent(UserExtendsDO::getHouseRegistration, reqVO.getHouseRegistration())
                .eqIfPresent(UserExtendsDO::getCountryRegistration, reqVO.getCountryRegistration())
                .eqIfPresent(UserExtendsDO::getPlaceResidence, reqVO.getPlaceResidence())
                .eqIfPresent(UserExtendsDO::getEmergeContact, reqVO.getEmergeContact())
                .eqIfPresent(UserExtendsDO::getEmployer, reqVO.getEmployer())
                .eqIfPresent(UserExtendsDO::getWorkingYears, reqVO.getWorkingYears())
                .eqIfPresent(UserExtendsDO::getCompanyType, reqVO.getCompanyType())
                .eqIfPresent(UserExtendsDO::getWorkAddress, reqVO.getWorkAddress())
                .eqIfPresent(UserExtendsDO::getHouseType, reqVO.getHouseType())
                .betweenIfPresent(UserExtendsDO::getJoinPartyTime, reqVO.getJoinPartyTime())
                .eqIfPresent(UserExtendsDO::getMonthIncome, reqVO.getMonthIncome())
                .eqIfPresent(UserExtendsDO::getRetiree, reqVO.getRetiree())
                .eqIfPresent(UserExtendsDO::getNatureEmployment, reqVO.getNatureEmployment())
                .eqIfPresent(UserExtendsDO::getCommunityGroup, reqVO.getCommunityGroup())
                .eqIfPresent(UserExtendsDO::getFoucusChildren, reqVO.getFoucusChildren())
                .eqIfPresent(UserExtendsDO::getIsDibaohu, reqVO.getIsDibaohu())
                .eqIfPresent(UserExtendsDO::getInsuranceType, reqVO.getInsuranceType())
                .eqIfPresent(UserExtendsDO::getReligiousBelief, reqVO.getReligiousBelief())
                .eqIfPresent(UserExtendsDO::getSkillCertificate, reqVO.getSkillCertificate())
                .eqIfPresent(UserExtendsDO::getGraduatedShcool, reqVO.getGraduatedShcool())
                .eqIfPresent(UserExtendsDO::getUniqueId, reqVO.getUniqueId())
                .betweenIfPresent(UserExtendsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserExtendsDO::getId));
    }

}