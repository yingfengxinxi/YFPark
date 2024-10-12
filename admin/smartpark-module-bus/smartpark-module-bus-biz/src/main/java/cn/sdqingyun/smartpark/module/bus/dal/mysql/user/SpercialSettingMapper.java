package cn.sdqingyun.smartpark.module.bus.dal.mysql.user;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.user.SpercialSettingDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo.*;

/**
 * 机构用户自定义操作配置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface SpercialSettingMapper extends BaseMapperX<SpercialSettingDO> {

    default PageResult<SpercialSettingDO> selectPage(SpercialSettingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SpercialSettingDO>()
                .eqIfPresent(SpercialSettingDO::getUid, reqVO.getUid())
                .eqIfPresent(SpercialSettingDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(SpercialSettingDO::getType, reqVO.getType())
                .eqIfPresent(SpercialSettingDO::getContent, reqVO.getContent())
                .betweenIfPresent(SpercialSettingDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SpercialSettingDO::getId));
    }

}