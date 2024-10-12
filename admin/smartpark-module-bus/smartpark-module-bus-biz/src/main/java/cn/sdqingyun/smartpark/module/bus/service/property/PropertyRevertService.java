package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRevertDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产归还 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyRevertService {

    /**
     * 创建资产归还
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyRevert(@Valid PropertyRevertSaveReqVO createReqVO);

    /**
     * 更新资产归还
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyRevert(@Valid PropertyRevertSaveReqVO updateReqVO);

    /**
     * 删除资产归还
     *
     * @param id 编号
     */
    void deletePropertyRevert(Long id);

    /**
     * 获得资产归还
     *
     * @param id 编号
     * @return 资产归还
     */
    PropertyRevertDO getPropertyRevert(Long id);

    /**
     * 获得资产归还分页
     *
     * @param pageReqVO 分页查询
     * @return 资产归还分页
     */
    PageResult<PropertyRevertDO> getPropertyRevertPage(PropertyRevertPageReqVO pageReqVO);

}