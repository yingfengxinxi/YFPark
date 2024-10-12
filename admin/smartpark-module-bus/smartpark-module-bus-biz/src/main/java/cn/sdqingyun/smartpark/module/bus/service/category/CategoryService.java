package cn.sdqingyun.smartpark.module.bus.service.category;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单分类配置 Service 接口
 *
 * @author 管理员
 */
public interface CategoryService {

    /**
     * 创建工单分类配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid CategorySaveReqVO createReqVO);

    /**
     * 更新工单分类配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid CategorySaveReqVO updateReqVO);

    /**
     * 删除工单分类配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单分类配置
     *
     * @param id 编号
     * @return 工单分类配置
     */
    CategoryDO get(Long id);

    /**
     * 获得工单分类配置分页
     *
     * @param pageReqVO 分页查询
     * @return 工单分类配置分页
     */
    PageResult<CategoryRespVO> getPage(CategoryPageReqVO pageReqVO);

    /**
     *
     * @param application
     * @param buildBind
     * @return
     */
    List<CategoryRespVO> getList(String application,String buildBind);
}