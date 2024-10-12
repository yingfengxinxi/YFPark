package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyCategoryDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 资产分类 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyCategoryService {

    /**
     * 创建资产分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyCategory(@Valid PropertyCategorySaveReqVO createReqVO);

    /**
     * 更新资产分类
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyCategory(@Valid PropertyCategorySaveReqVO updateReqVO);

    /**
     * 删除资产分类
     *
     * @param id 编号
     */
    void deletePropertyCategory(Long id);

    /**
     * 获得资产分类
     *
     * @param id 编号
     * @return 资产分类
     */
    PropertyCategoryDO getPropertyCategory(Long id);

    /**
     * 获得资产分类分页
     *
     * @param pageReqVO 分页查询
     * @return 资产分类分页
     */
    PageResult<PropertyCategoryRespVO> getPropertyCategoryPage(PropertyCategoryPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获得资产分类树
    * @Date 9:47 2024/8/11
    * @Param [updateReqVO]
    * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO>
    **/
    List<PropertyCategoryRespVO> getTree();
}