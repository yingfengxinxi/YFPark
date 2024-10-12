package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.PhoneCategoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目电话类型 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface PhoneCategoryService {

    /**
     * 创建项目电话类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPhoneCategory(@Valid PhoneCategorySaveReqVO createReqVO);

    /**
     * 更新项目电话类型
     *
     * @param updateReqVO 更新信息
     */
    void updatePhoneCategory(@Valid PhoneCategorySaveReqVO updateReqVO);

    /**
     * 删除项目电话类型
     *
     * @param id 编号
     */
    void deletePhoneCategory(Long id);

    /**
     * 获得项目电话类型
     *
     * @param id 编号
     * @return 项目电话类型
     */
    PhoneCategoryDO getPhoneCategory(Long id);

    /**
     * 获得项目电话类型分页
     *
     * @param pageReqVO 分页查询
     * @return 项目电话类型分页
     */
    PageResult<PhoneCategoryDO> getPhoneCategoryPage(PhoneCategoryPageReqVO pageReqVO);
    PageResult<PhoneCategoryRespVO> getPhoneCategoryVOPage(PhoneCategoryPageReqVO pageReqVO);

}