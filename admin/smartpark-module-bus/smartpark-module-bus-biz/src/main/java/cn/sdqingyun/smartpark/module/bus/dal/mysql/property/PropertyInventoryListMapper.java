package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryListDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产盘点清单 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyInventoryListMapper extends BaseMapperX<PropertyInventoryListDO> {

    default PageResult<PropertyInventoryListDO> selectPage(PropertyInventoryListPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInventoryListDO>()
                .eqIfPresent(PropertyInventoryListDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyInventoryListDO::getNumber, reqVO.getNumber())
                .likeIfPresent(PropertyInventoryListDO::getInventoryName, reqVO.getInventoryName())
                .eqIfPresent(PropertyInventoryListDO::getInventoryStatus, reqVO.getInventoryStatus())
                .eqIfPresent(PropertyInventoryListDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyInventoryListDO::getInventoryUid, reqVO.getInventoryUid())
                .eqIfPresent(PropertyInventoryListDO::getRestrictField, reqVO.getRestrictField())
                .eqIfPresent(PropertyInventoryListDO::getDepartmentIds, reqVO.getDepartmentIds())
                .eqIfPresent(PropertyInventoryListDO::getTypeIds, reqVO.getTypeIds())
                .eqIfPresent(PropertyInventoryListDO::getPositionIds, reqVO.getPositionIds())
                .eqIfPresent(PropertyInventoryListDO::getPropertyStatus, reqVO.getPropertyStatus())
                .eqIfPresent(PropertyInventoryListDO::getInitiativePayStatus, reqVO.getInitiativePayStatus())
                .eqIfPresent(PropertyInventoryListDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(PropertyInventoryListDO::getCompany, reqVO.getCompany())
                .eqIfPresent(PropertyInventoryListDO::getPurchaseType, reqVO.getPurchaseType())
                .eqIfPresent(PropertyInventoryListDO::getCodeInventoryType, reqVO.getCodeInventoryType())
                .eqIfPresent(PropertyInventoryListDO::getNotbreachInventoryType, reqVO.getNotbreachInventoryType())
                .eqIfPresent(PropertyInventoryListDO::getUploadType, reqVO.getUploadType())
                .eqIfPresent(PropertyInventoryListDO::getIsBatch, reqVO.getIsBatch())
                .eqIfPresent(PropertyInventoryListDO::getDistributionRange, reqVO.getDistributionRange())
                .eqIfPresent(PropertyInventoryListDO::getExterData, reqVO.getExterData())
                .betweenIfPresent(PropertyInventoryListDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyInventoryListDO::getId));
    }

}