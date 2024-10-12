package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDrawDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目绘制数据 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface VillageDrawService {

    /**
     * 创建项目绘制数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVillageDraw(@Valid VillageDrawSaveReqVO createReqVO);

    /**
     * 更新项目绘制数据
     *
     * @param updateReqVO 更新信息
     */
    void updateVillageDraw(@Valid VillageDrawSaveReqVO updateReqVO);

    /**
     * 删除项目绘制数据
     *
     * @param id 编号
     */
    void deleteVillageDraw(Long id);

    /**
     * 获得项目绘制数据
     *
     * @param id 编号
     * @return 项目绘制数据
     */
    VillageDrawDO getVillageDraw(Long id);

    /**
     * 获得项目绘制数据分页
     *
     * @param pageReqVO 分页查询
     * @return 项目绘制数据分页
     */
    PageResult<VillageDrawDO> getVillageDrawPage(VillageDrawPageReqVO pageReqVO);

}