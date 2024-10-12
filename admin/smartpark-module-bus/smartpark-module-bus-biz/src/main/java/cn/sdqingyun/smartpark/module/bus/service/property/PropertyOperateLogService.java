package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyOperateLogDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产操作日志 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyOperateLogService {

    /**
     * 创建资产操作日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyOperateLog(@Valid PropertyOperateLogSaveReqVO createReqVO);

    /**
     * 更新资产操作日志
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyOperateLog(@Valid PropertyOperateLogSaveReqVO updateReqVO);

    /**
     * 删除资产操作日志
     *
     * @param id 编号
     */
    void deletePropertyOperateLog(Long id);

    /**
     * 获得资产操作日志
     *
     * @param id 编号
     * @return 资产操作日志
     */
    PropertyOperateLogDO getPropertyOperateLog(Long id);

    /**
     * 获得资产操作日志分页
     *
     * @param pageReqVO 分页查询
     * @return 资产操作日志分页
     */
    PageResult<PropertyOperateLogDO> getPropertyOperateLogPage(PropertyOperateLogPageReqVO pageReqVO);

}