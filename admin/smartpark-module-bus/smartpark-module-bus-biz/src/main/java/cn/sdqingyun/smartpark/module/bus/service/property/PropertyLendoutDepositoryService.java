package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLendoutDepositoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产借出仓库信息 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyLendoutDepositoryService {

    /**
     * 创建资产借出仓库信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyLendoutDepository(@Valid PropertyLendoutDepositorySaveReqVO createReqVO);

    /**
     * 更新资产借出仓库信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyLendoutDepository(@Valid PropertyLendoutDepositorySaveReqVO updateReqVO);

    /**
     * 删除资产借出仓库信息
     *
     * @param id 编号
     */
    void deletePropertyLendoutDepository(Long id);

    /**
     * 获得资产借出仓库信息
     *
     * @param id 编号
     * @return 资产借出仓库信息
     */
    PropertyLendoutDepositoryDO getPropertyLendoutDepository(Long id);

    /**
     * 获得资产借出仓库信息分页
     *
     * @param pageReqVO 分页查询
     * @return 资产借出仓库信息分页
     */
    PageResult<PropertyLendoutDepositoryDO> getPropertyLendoutDepositoryPage(PropertyLendoutDepositoryPageReqVO pageReqVO);

}