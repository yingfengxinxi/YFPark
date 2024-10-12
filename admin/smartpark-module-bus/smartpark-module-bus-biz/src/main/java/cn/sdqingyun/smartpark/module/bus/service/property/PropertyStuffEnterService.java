package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffEnterDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 耗材入库记录 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffEnterService {

    /**
     * 创建耗材入库记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffEnter(@Valid PropertyStuffEnterSaveReqVO createReqVO);

    /**
     * 更新耗材入库记录
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffEnter(@Valid PropertyStuffEnterSaveReqVO updateReqVO);

    /**
     * 删除耗材入库记录
     *
     * @param id 编号
     */
    void deletePropertyStuffEnter(Long id);

    /**
     * 获得耗材入库记录
     *
     * @param id 编号
     * @return 耗材入库记录
     */
    PropertyStuffEnterDO getPropertyStuffEnter(Long id);

    /**
     * 获得耗材入库记录分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材入库记录分页
     */
    PageResult<PropertyStuffEnterDO> getPropertyStuffEnterPage(PropertyStuffEnterPageReqVO pageReqVO);
    PageResult<PropertyStuffEnterRespVO> getPropertyStuffEnterPageVO(PropertyStuffEnterPageReqVO pageReqVO);

}