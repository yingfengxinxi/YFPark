package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyConfigDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产配置信息 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyConfigService {

    /**
     * 创建资产配置信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyConfig(@Valid PropertyConfigSaveReqVO createReqVO);

    /**
     * 更新资产配置信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyConfig(@Valid PropertyConfigSaveReqVO updateReqVO);

    /**
     * 删除资产配置信息
     *
     * @param id 编号
     */
    void deletePropertyConfig(Long id);

    /**
     * 获得资产配置信息
     *
     * @param id 编号
     * @return 资产配置信息
     */
    PropertyConfigDO getPropertyConfig(Long id);

    /**
     * 获得资产配置信息分页
     *
     * @param pageReqVO 分页查询
     * @return 资产配置信息分页
     */
    PageResult<PropertyConfigDO> getPropertyConfigPage(PropertyConfigPageReqVO pageReqVO);

}