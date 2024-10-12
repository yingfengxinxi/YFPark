package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyMaintainSetDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产保养设置 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyMaintainSetService {

    /**
     * 创建资产保养设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyMaintainSet(@Valid PropertyMaintainSetSaveReqVO createReqVO);

    /**
     * 更新资产保养设置
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyMaintainSet(@Valid PropertyMaintainSetSaveReqVO updateReqVO);

    /**
     * 删除资产保养设置
     *
     * @param id 编号
     */
    void deletePropertyMaintainSet(Long id);

    /**
     * 获得资产保养设置
     *
     * @param id 编号
     * @return 资产保养设置
     */
    PropertyMaintainSetDO getPropertyMaintainSet(Long id);

    /**
     * 获得资产保养设置分页
     *
     * @param pageReqVO 分页查询
     * @return 资产保养设置分页
     */
    PageResult<PropertyMaintainSetDO> getPropertyMaintainSetPage(PropertyMaintainSetPageReqVO pageReqVO);

}