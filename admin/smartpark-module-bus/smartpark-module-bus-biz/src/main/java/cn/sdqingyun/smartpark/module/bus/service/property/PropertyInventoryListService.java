package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryListDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产盘点清单 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyInventoryListService {

    /**
     * 创建资产盘点清单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyInventoryList(@Valid PropertyInventoryListSaveReqVO createReqVO);

    /**
     * 更新资产盘点清单
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyInventoryList(@Valid PropertyInventoryListSaveReqVO updateReqVO);

    /**
     * 删除资产盘点清单
     *
     * @param id 编号
     */
    void deletePropertyInventoryList(Long id);

    /**
     * 获得资产盘点清单
     *
     * @param id 编号
     * @return 资产盘点清单
     */
    PropertyInventoryListDO getPropertyInventoryList(Long id);

    /**
     * 获得资产盘点清单分页
     *
     * @param pageReqVO 分页查询
     * @return 资产盘点清单分页
     */
    PageResult<PropertyInventoryListDO> getPropertyInventoryListPage(PropertyInventoryListPageReqVO pageReqVO);

}