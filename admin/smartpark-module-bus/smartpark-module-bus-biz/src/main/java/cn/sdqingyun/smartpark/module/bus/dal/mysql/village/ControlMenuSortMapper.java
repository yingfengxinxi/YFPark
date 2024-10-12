package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuSortDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目租控管理菜单排序 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface ControlMenuSortMapper extends BaseMapperX<ControlMenuSortDO> {

    default PageResult<ControlMenuSortDO> selectPage(ControlMenuSortPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ControlMenuSortDO>()
                .eqIfPresent(ControlMenuSortDO::getMenuId, reqVO.getMenuId())
                .eqIfPresent(ControlMenuSortDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ControlMenuSortDO::getSort, reqVO.getSort())
                .betweenIfPresent(ControlMenuSortDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ControlMenuSortDO::getId));
    }

}