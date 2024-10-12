package cn.sdqingyun.smartpark.module.bus.service.ownerPaySet;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerPaySet.OwnerPaySetDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 企业自动缴费费用配置 Service 接口
 *
 * @author 智慧园区
 */
public interface OwnerPaySetService {

    /**
     * 创建企业自动缴费费用配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOwnerPaySet(@Valid OwnerPaySetSaveReqVO createReqVO);

    /**
     * 更新企业自动缴费费用配置
     *
     * @param updateReqVO 更新信息
     */
    void updateOwnerPaySet(@Valid OwnerPaySetSaveReqVO updateReqVO);

    /**
     * 删除企业自动缴费费用配置
     *
     * @param id 编号
     */
    void deleteOwnerPaySet(Long id);

    /**
     * 获得企业自动缴费费用配置
     *
     * @param id 编号
     * @return 企业自动缴费费用配置
     */
    OwnerPaySetDO getOwnerPaySet(Long id);

    /**
     * 获得企业自动缴费费用配置分页
     *
     * @param pageReqVO 分页查询
     * @return 企业自动缴费费用配置分页
     */
    PageResult<OwnerPaySetDO> getOwnerPaySetPage(OwnerPaySetPageReqVO pageReqVO);

}