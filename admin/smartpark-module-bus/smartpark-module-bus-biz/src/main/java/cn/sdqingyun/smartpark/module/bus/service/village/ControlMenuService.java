package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目租控管理菜单 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface ControlMenuService {

    /**
     * 创建项目租控管理菜单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createControlMenu(@Valid ControlMenuSaveReqVO createReqVO);

    /**
     * 更新项目租控管理菜单
     *
     * @param updateReqVO 更新信息
     */
    void updateControlMenu(@Valid ControlMenuSaveReqVO updateReqVO);

    /**
     * 删除项目租控管理菜单
     *
     * @param id 编号
     */
    void deleteControlMenu(Long id);

    /**
     * 获得项目租控管理菜单
     *
     * @param id 编号
     * @return 项目租控管理菜单
     */
    ControlMenuDO getControlMenu(Long id);

    /**
     * 获得项目租控管理菜单分页
     *
     * @param pageReqVO 分页查询
     * @return 项目租控管理菜单分页
     */
    PageResult<ControlMenuDO> getControlMenuPage(ControlMenuPageReqVO pageReqVO);

}