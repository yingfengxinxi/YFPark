package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffLogDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产耗材业务记录 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffLogService {

    /**
     * 创建资产耗材业务记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffLog(@Valid PropertyStuffLogSaveReqVO createReqVO);

    /**
     * 更新资产耗材业务记录
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffLog(@Valid PropertyStuffLogSaveReqVO updateReqVO);

    /**
     * 删除资产耗材业务记录
     *
     * @param id 编号
     */
    void deletePropertyStuffLog(Long id);

    /**
     * 获得资产耗材业务记录
     *
     * @param id 编号
     * @return 资产耗材业务记录
     */
    PropertyStuffLogDO getPropertyStuffLog(Long id);

    /**
     * 获得资产耗材业务记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产耗材业务记录分页
     */
    PageResult<PropertyStuffLogDO> getPropertyStuffLogPage(PropertyStuffLogPageReqVO pageReqVO);

}