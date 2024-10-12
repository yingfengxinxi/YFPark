package cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerRemarks;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerRemarks.OwnerRemarksDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks.vo.*;

/**
 * 租客备注信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OwnerRemarksMapper extends BaseMapperX<OwnerRemarksDO> {

    default PageResult<OwnerRemarksDO> selectPage(OwnerRemarksPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OwnerRemarksDO>()
                .eqIfPresent(OwnerRemarksDO::getOwnId, reqVO.getOwnId())
                .eqIfPresent(OwnerRemarksDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(OwnerRemarksDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OwnerRemarksDO::getOperationUid, reqVO.getOperationUid())
                .likeIfPresent(OwnerRemarksDO::getOperationName, reqVO.getOperationName())
                .betweenIfPresent(OwnerRemarksDO::getOperationTime, reqVO.getOperationTime())
                .betweenIfPresent(OwnerRemarksDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OwnerRemarksDO::getId));
    }

}