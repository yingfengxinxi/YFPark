package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuSortDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目租控管理菜单排序 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface ControlMenuSortService {

    /**
     * 创建项目租控管理菜单排序
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createControlMenuSort(@Valid ControlMenuSortSaveReqVO createReqVO);

    /**
     * 更新项目租控管理菜单排序
     *
     * @param updateReqVO 更新信息
     */
    void updateControlMenuSort(@Valid ControlMenuSortSaveReqVO updateReqVO);

    /**
     * 删除项目租控管理菜单排序
     *
     * @param id 编号
     */
    void deleteControlMenuSort(Long id);

    /**
     * 获得项目租控管理菜单排序
     *
     * @param id 编号
     * @return 项目租控管理菜单排序
     */
    ControlMenuSortDO getControlMenuSort(Long id);

    /**
     * 获得项目租控管理菜单排序分页
     *
     * @param pageReqVO 分页查询
     * @return 项目租控管理菜单排序分页
     */
    PageResult<ControlMenuSortDO> getControlMenuSortPage(ControlMenuSortPageReqVO pageReqVO);

}