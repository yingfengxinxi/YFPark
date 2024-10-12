package cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerFiles;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerFiles.OwnerFilesDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles.vo.*;

/**
 * 租客附件 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OwnerFilesMapper extends BaseMapperX<OwnerFilesDO> {

    default PageResult<OwnerFilesDO> selectPage(OwnerFilesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OwnerFilesDO>()
                .eqIfPresent(OwnerFilesDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(OwnerFilesDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(OwnerFilesDO::getUid, reqVO.getUid())
                .likeIfPresent(OwnerFilesDO::getName, reqVO.getName())
                .eqIfPresent(OwnerFilesDO::getUrl, reqVO.getUrl())
                .betweenIfPresent(OwnerFilesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OwnerFilesDO::getId));
    }

}