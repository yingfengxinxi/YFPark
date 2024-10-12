package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyProcessDataDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 业务流程单据关联资产 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyProcessDataService {

    /**
     * 创建业务流程单据关联资产
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyProcessData(@Valid PropertyProcessDataSaveReqVO createReqVO);

    /**
     * 更新业务流程单据关联资产
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyProcessData(@Valid PropertyProcessDataSaveReqVO updateReqVO);

    /**
     * 删除业务流程单据关联资产
     *
     * @param id 编号
     */
    void deletePropertyProcessData(Long id);

    /**
     * 获得业务流程单据关联资产
     *
     * @param id 编号
     * @return 业务流程单据关联资产
     */
    PropertyProcessDataDO getPropertyProcessData(Long id);

    /**
     * 获得业务流程单据关联资产分页
     *
     * @param pageReqVO 分页查询
     * @return 业务流程单据关联资产分页
     */
    PageResult<PropertyProcessDataDO> getPropertyProcessDataPage(PropertyProcessDataPageReqVO pageReqVO);

}