package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryRecordDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产盘点记录 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyInventoryRecordService {

    /**
     * 创建资产盘点记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyInventoryRecord(@Valid PropertyInventoryRecordSaveReqVO createReqVO);

    /**
     * 更新资产盘点记录
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyInventoryRecord(@Valid PropertyInventoryRecordSaveReqVO updateReqVO);

    /**
     * 删除资产盘点记录
     *
     * @param id 编号
     */
    void deletePropertyInventoryRecord(Long id);

    /**
     * 获得资产盘点记录
     *
     * @param id 编号
     * @return 资产盘点记录
     */
    PropertyInventoryRecordDO getPropertyInventoryRecord(Long id);

    /**
     * 获得资产盘点记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产盘点记录分页
     */
    PageResult<PropertyInventoryRecordDO> getPropertyInventoryRecordPage(PropertyInventoryRecordPageReqVO pageReqVO);

}