package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDrawDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目绘制数据 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface VillageDrawMapper extends BaseMapperX<VillageDrawDO> {

    default PageResult<VillageDrawDO> selectPage(VillageDrawPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VillageDrawDO>()
                .eqIfPresent(VillageDrawDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(VillageDrawDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(VillageDrawDO::getDrawInfo, reqVO.getDrawInfo())
                .eqIfPresent(VillageDrawDO::getSettingInfo, reqVO.getSettingInfo())
                .betweenIfPresent(VillageDrawDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VillageDrawDO::getId));
    }

}