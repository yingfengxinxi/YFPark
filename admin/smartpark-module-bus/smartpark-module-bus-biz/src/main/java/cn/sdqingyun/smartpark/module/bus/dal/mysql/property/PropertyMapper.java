package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资产管理 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyMapper extends BaseMapperX<PropertyDO> {

    default PageResult<PropertyDO> selectPage(PropertyPageReqVO reqVO) {
        if(CollUtil.isNotEmpty( reqVO.getStatusAll() )){
            reqVO.setStatus( null );
        }
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyDO>()
                .eqIfPresent(PropertyDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(PropertyDO::getPropertyNumber, reqVO.getPropertyNumber())
                .eqIfPresent(PropertyDO::getLabelLink, reqVO.getLabelLink())
                .eqIfPresent(PropertyDO::getType, reqVO.getType())
                .likeIfPresent(PropertyDO::getName, reqVO.getName())
                .eqIfPresent(PropertyDO::getStatus, reqVO.getStatus())
                .inIfPresent( PropertyDO::getStatus, reqVO.getStatusAll() )
                .eqIfPresent(PropertyDO::getBrand, reqVO.getBrand())
                .likeIfPresent(PropertyDO::getModelName, reqVO.getModelName())
                .eqIfPresent(PropertyDO::getDeviceCode, reqVO.getDeviceCode())
                .eqIfPresent(PropertyDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(PropertyDO::getAdminName, reqVO.getAdminName())
                .eqIfPresent(PropertyDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(PropertyDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(PropertyDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(PropertyDO::getBuildBind, reqVO.getBuildBind())
                .eqIfPresent(PropertyDO::getPositionId, reqVO.getPositionId())
                .likeIfPresent(PropertyDO::getPositionName, reqVO.getPositionName())
                .betweenIfPresent(PropertyDO::getPurchaseTime, reqVO.getPurchaseTime())
                .eqIfPresent(PropertyDO::getPurchaseType, reqVO.getPurchaseType())
                .eqIfPresent(PropertyDO::getPurchaseAmount, reqVO.getPurchaseAmount())
                .betweenIfPresent(PropertyDO::getStockTime, reqVO.getStockTime())
                .eqIfPresent(PropertyDO::getExpectMonths, reqVO.getExpectMonths())
                .eqIfPresent(PropertyDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyDO::getImageHash, reqVO.getImageHash())
                .eqIfPresent(PropertyDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(PropertyDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PropertyDO::getDepartmentId, reqVO.getDepartmentId())
                .betweenIfPresent(PropertyDO::getReceiveTime, reqVO.getReceiveTime())
                .betweenIfPresent(PropertyDO::getMaintainTime, reqVO.getMaintainTime())
                .eqIfPresent(PropertyDO::getMaintainInfo, reqVO.getMaintainInfo())
                .eqIfPresent(PropertyDO::getDepreciationMonths, reqVO.getDepreciationMonths())
                .eqIfPresent(PropertyDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyDO::getMuserUid, reqVO.getMuserUid())
                .eqIfPresent(PropertyDO::getKnowledgeBase, reqVO.getKnowledgeBase())
                .betweenIfPresent(PropertyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyDO::getId));
    }

    void updateCancelById(PropertyDO propertyDO);
}