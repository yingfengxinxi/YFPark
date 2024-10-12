package cn.sdqingyun.smartpark.module.bus.dal.mysql.owner;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerInfoVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 租客信息 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface OwnerMapper extends BaseMapperX<OwnerDO> {

    default PageResult<OwnerDO> selectPage(OwnerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OwnerDO>()
                .eqIfPresent(OwnerDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(OwnerDO::getName, reqVO.getName())
                .eqIfPresent(OwnerDO::getIsPersonal, reqVO.getIsPersonal())
                .eqIfPresent(OwnerDO::getIsSuggest, reqVO.getIsSuggest())
                .eqIfPresent(OwnerDO::getType, reqVO.getType())
                .eqIfPresent(OwnerDO::getSham, reqVO.getSham())
                .eqIfPresent(OwnerDO::getContactId, reqVO.getContactId())
                .eqIfPresent(OwnerDO::getContactSignId, reqVO.getContactSignId())
                .eqIfPresent(OwnerDO::getContactNoticeId, reqVO.getContactNoticeId())
                .eqIfPresent(OwnerDO::getApprovalContactId, reqVO.getApprovalContactId())
                .eqIfPresent(OwnerDO::getCertificateNumber, reqVO.getCertificateNumber())
                .eqIfPresent(OwnerDO::getIndustryId, reqVO.getIndustryId())
                .eqIfPresent(OwnerDO::getVillageIdList, reqVO.getVillageIdList())
                .eqIfPresent(OwnerDO::getBuildBind, reqVO.getBuildBind())
                .likeIfPresent(OwnerDO::getTel, reqVO.getTel())
                .eqIfPresent(OwnerDO::getEmail, reqVO.getEmail())
                .eqIfPresent(OwnerDO::getTenantNo, reqVO.getTenantNo())
                .eqIfPresent(OwnerDO::getWebsiteLink, reqVO.getWebsiteLink())
                .eqIfPresent(OwnerDO::getLogo, reqVO.getLogo())
                .eqIfPresent(OwnerDO::getCompanyDesc, reqVO.getCompanyDesc())
                .betweenIfPresent(OwnerDO::getBusinessInfoFoundingTime, reqVO.getBusinessInfoFoundingTime())
                .eqIfPresent(OwnerDO::getBusinessInfoBusinessTerm, reqVO.getBusinessInfoBusinessTerm())
                .eqIfPresent(OwnerDO::getRegisteredCapital, reqVO.getRegisteredCapital())
                .eqIfPresent(OwnerDO::getInvoiceInfo, reqVO.getInvoiceInfo())
                .eqIfPresent(OwnerDO::getBusinessInfo, reqVO.getBusinessInfo())
                .eqIfPresent(OwnerDO::getTagInfo, reqVO.getTagInfo())
                .eqIfPresent(OwnerDO::getDiyField, reqVO.getDiyField())
                .eqIfPresent(OwnerDO::getIsAdvanceNotice, reqVO.getIsAdvanceNotice())
                .eqIfPresent(OwnerDO::getIsArchive, reqVO.getIsArchive())
                .betweenIfPresent(OwnerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OwnerDO::getId));
    }

    /**
    * @Author SUNk
    * @Description 租客行业环形图
    * @Date 15:53 2024/7/10
    * @Param [createReqVO]
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getCountOwnerAnnularRing(OwnerSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 租客标签环形图
    * @Date 16:17 2024/7/10
    * @Param [createReqVO]
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getCountOwnerTagAnnularRing(OwnerSaveReqVO createReqVO);


    /**
     *
     * @param id
     * @return
     */
    OwnerInfoVO getByIdOwnerInfo(@Param("id")Long id);
}