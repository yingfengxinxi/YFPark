package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceCategoryDO;
import jakarta.validation.Valid;

/**
 * 预约场地分类 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvPlaceCategoryService {

    /**
     * 创建预约场地分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvPlaceCategory(@Valid ResvPlaceCategorySaveReqVO createReqVO);

    /**
     * 更新预约场地分类
     *
     * @param updateReqVO 更新信息
     */
    void updateResvPlaceCategory(@Valid ResvPlaceCategorySaveReqVO updateReqVO);

    /**
     * 删除预约场地分类
     *
     * @param id 编号
     */
    void deleteResvPlaceCategory(Long id);

    /**
     * 获得预约场地分类
     *
     * @param id 编号
     * @return 预约场地分类
     */
    ResvPlaceCategoryDO getResvPlaceCategory(Long id);

    /**
     * 获得预约场地分类分页
     *
     * @param pageReqVO 分页查询
     * @return 预约场地分类分页
     */
    PageResult<ResvPlaceCategoryDO> getResvPlaceCategoryPage(ResvPlaceCategoryPageReqVO pageReqVO);

}