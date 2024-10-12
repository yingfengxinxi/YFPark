package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRevertDepositoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产归还仓库信息 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyRevertDepositoryService {

    /**
     * 创建资产归还仓库信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyRevertDepository(@Valid PropertyRevertDepositorySaveReqVO createReqVO);

    /**
     * 更新资产归还仓库信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyRevertDepository(@Valid PropertyRevertDepositorySaveReqVO updateReqVO);

    /**
     * 删除资产归还仓库信息
     *
     * @param id 编号
     */
    void deletePropertyRevertDepository(Long id);

    /**
     * 获得资产归还仓库信息
     *
     * @param id 编号
     * @return 资产归还仓库信息
     */
    PropertyRevertDepositoryDO getPropertyRevertDepository(Long id);

    /**
     * 获得资产归还仓库信息分页
     *
     * @param pageReqVO 分页查询
     * @return 资产归还仓库信息分页
     */
    PageResult<PropertyRevertDepositoryDO> getPropertyRevertDepositoryPage(PropertyRevertDepositoryPageReqVO pageReqVO);

}