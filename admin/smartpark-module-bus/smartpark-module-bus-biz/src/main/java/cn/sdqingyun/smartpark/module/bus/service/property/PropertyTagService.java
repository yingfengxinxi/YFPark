package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyTagDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产标签模板 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyTagService {

    /**
     * 创建资产标签模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyTag(@Valid PropertyTagSaveReqVO createReqVO);

    /**
     * 更新资产标签模板
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyTag(@Valid PropertyTagSaveReqVO updateReqVO);

    /**
     * 删除资产标签模板
     *
     * @param id 编号
     */
    void deletePropertyTag(Long id);

    /**
     * 获得资产标签模板
     *
     * @param id 编号
     * @return 资产标签模板
     */
    PropertyTagDO getPropertyTag(Long id);

    /**
     * 获得资产标签模板分页
     *
     * @param pageReqVO 分页查询
     * @return 资产标签模板分页
     */
    PageResult<PropertyTagDO> getPropertyTagPage(PropertyTagPageReqVO pageReqVO);

}