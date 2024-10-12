package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyResourcesDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产静态资源管理 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyResourcesService {

    /**
     * 创建资产静态资源管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyResources(@Valid PropertyResourcesSaveReqVO createReqVO);

    /**
     * 更新资产静态资源管理
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyResources(@Valid PropertyResourcesSaveReqVO updateReqVO);

    /**
     * 删除资产静态资源管理
     *
     * @param id 编号
     */
    void deletePropertyResources(Long id);

    /**
     * 获得资产静态资源管理
     *
     * @param id 编号
     * @return 资产静态资源管理
     */
    PropertyResourcesDO getPropertyResources(Long id);

    /**
     * 获得资产静态资源管理分页
     *
     * @param pageReqVO 分页查询
     * @return 资产静态资源管理分页
     */
    PageResult<PropertyResourcesDO> getPropertyResourcesPage(PropertyResourcesPageReqVO pageReqVO);

}