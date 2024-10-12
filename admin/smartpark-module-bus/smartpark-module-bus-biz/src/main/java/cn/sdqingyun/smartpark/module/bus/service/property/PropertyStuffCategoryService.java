package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffCategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffCategoryDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 资产耗材分类 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffCategoryService {

    /**
     * 创建资产耗材分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffCategory(@Valid PropertyStuffCategorySaveReqVO createReqVO);

    /**
     * 更新资产耗材分类
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffCategory(@Valid PropertyStuffCategorySaveReqVO updateReqVO);

    /**
     * 删除资产耗材分类
     *
     * @param id 编号
     */
    void deletePropertyStuffCategory(Long id);

    /**
     * 获得资产耗材分类
     *
     * @param id 编号
     * @return 资产耗材分类
     */
    PropertyStuffCategoryDO getPropertyStuffCategory(Long id);

    /**
     * 获得资产耗材分类分页
     *
     * @param pageReqVO 分页查询
     * @return 资产耗材分类分页
     */
    PageResult<PropertyStuffCategoryRespVO> getPropertyStuffCategoryPage(PropertyStuffCategoryPageReqVO pageReqVO);

    /**
     * @Author SUNk
     * @Description 获得资产分类树
     * @Date 9:47 2024/8/11
     * @Param [updateReqVO]
     * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO>
     **/
    List<PropertyStuffCategoryRespVO> getTree();
}