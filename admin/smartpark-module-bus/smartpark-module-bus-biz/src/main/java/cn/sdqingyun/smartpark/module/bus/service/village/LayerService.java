package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目楼层 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface LayerService {

    /**
     * 创建项目楼层
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLayer(@Valid LayerSaveReqVO createReqVO);

    /**
     * 更新项目楼层
     *
     * @param updateReqVO 更新信息
     */
    void updateLayer(@Valid LayerSaveReqVO updateReqVO);

    /**
     * 删除项目楼层
     *
     * @param id 编号
     */
    void deleteLayer(Long id);

    /**
     * 获得项目楼层
     *
     * @param id 编号
     * @return 项目楼层
     */
    LayerDO getLayer(Long id);

    /**
     * 获得项目楼层分页
     *
     * @param pageReqVO 分页查询
     * @return 项目楼层分页
     */
    PageResult<LayerDO> getLayerPage(LayerPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获取楼层列表
    * @Date 9:32 2024/9/29
    * @Param [pageReqVO]
    * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.LayerRespVO>
    **/
    List<LayerRespVO> getLayerList(LayerPageReqVO pageReqVO);

}