package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffHandleDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 耗材业务处置 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffHandleService {

    /**
     * 创建耗材业务处置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffHandle(@Valid PropertyStuffHandleSaveReqVO createReqVO);

    /**
     * 更新耗材业务处置
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffHandle(@Valid PropertyStuffHandleSaveReqVO updateReqVO);

    /**
     * 删除耗材业务处置
     *
     * @param id 编号
     */
    void deletePropertyStuffHandle(Long id);

    /**
     * 获得耗材业务处置
     *
     * @param id 编号
     * @return 耗材业务处置
     */
    PropertyStuffHandleDO getPropertyStuffHandle(Long id);

    /**
     * 获得耗材业务处置分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材业务处置分页
     */
    PageResult<PropertyStuffHandleDO> getPropertyStuffHandlePage(PropertyStuffHandlePageReqVO pageReqVO);
    PageResult<PropertyStuffHandleRespVO> getPropertyStuffHandlePageVO(PropertyStuffHandlePageReqVO pageReqVO);

}