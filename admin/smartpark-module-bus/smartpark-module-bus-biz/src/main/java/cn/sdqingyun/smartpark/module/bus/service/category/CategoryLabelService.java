package cn.sdqingyun.smartpark.module.bus.service.category;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryLabelPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryLabelRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryLabelSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryLabelDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单分类标签配置 Service 接口
 *
 * @author 管理员
 */
public interface CategoryLabelService {

    /**
     * 创建工单分类标签配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid CategoryLabelSaveReqVO createReqVO);

    /**
     * 更新工单分类标签配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid CategoryLabelSaveReqVO updateReqVO);

    /**
     * 删除工单分类标签配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单分类标签配置
     *
     * @param id 编号
     * @return 工单分类标签配置
     */
    CategoryLabelDO get(Long id);

    /**
     * 获得工单分类标签配置分页
     *
     * @param pageReqVO 分页查询
     * @return 工单分类标签配置分页
     */
    PageResult<CategoryLabelRespVO> getPage(CategoryLabelPageReqVO pageReqVO);

}