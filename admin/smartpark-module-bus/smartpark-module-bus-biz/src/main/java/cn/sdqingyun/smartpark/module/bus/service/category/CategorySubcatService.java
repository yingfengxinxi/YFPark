package cn.sdqingyun.smartpark.module.bus.service.category;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单分类子类信息 Service 接口
 *
 * @author 管理员
 */
public interface CategorySubcatService {

    /**
     * 创建工单分类子类信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid CategorySubcatSaveReqVO createReqVO);

    /**
     * 更新工单分类子类信息
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid CategorySubcatSaveReqVO updateReqVO);

    /**
     * 删除工单分类子类信息
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单分类子类信息
     *
     * @param id 编号
     * @return 工单分类子类信息
     */
    CategorySubcatDO get(Long id);

    /**
     * 获得工单分类子类信息分页
     *
     * @param pageReqVO 分页查询
     * @return 工单分类子类信息分页
     */
    PageResult<CategorySubcatRespVO> getPage(CategorySubcatPageReqVO pageReqVO);

}