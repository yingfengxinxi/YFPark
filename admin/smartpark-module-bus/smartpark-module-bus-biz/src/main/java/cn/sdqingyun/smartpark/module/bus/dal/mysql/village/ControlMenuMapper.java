package cn.sdqingyun.smartpark.module.bus.dal.mysql.village;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;

/**
 * 项目租控管理菜单 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface ControlMenuMapper extends BaseMapperX<ControlMenuDO> {

    default PageResult<ControlMenuDO> selectPage(ControlMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ControlMenuDO>()
                .likeIfPresent(ControlMenuDO::getName, reqVO.getName())
                .eqIfPresent(ControlMenuDO::getIcon, reqVO.getIcon())
                .eqIfPresent(ControlMenuDO::getActiveIcon, reqVO.getActiveIcon())
                .eqIfPresent(ControlMenuDO::getMenuType, reqVO.getMenuType())
                .eqIfPresent(ControlMenuDO::getAlias, reqVO.getAlias())
                .eqIfPresent(ControlMenuDO::getSort, reqVO.getSort())
                .eqIfPresent(ControlMenuDO::getMicro, reqVO.getMicro())
                .eqIfPresent(ControlMenuDO::getApp, reqVO.getApp())
                .betweenIfPresent(ControlMenuDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ControlMenuDO::getId));
    }

}