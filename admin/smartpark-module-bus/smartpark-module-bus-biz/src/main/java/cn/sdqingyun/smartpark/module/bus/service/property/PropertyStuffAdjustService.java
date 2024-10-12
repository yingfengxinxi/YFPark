package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffAdjustDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 耗材业务调整 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffAdjustService {

    /**
     * 创建耗材业务调整
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffAdjust(@Valid PropertyStuffAdjustSaveReqVO createReqVO);

    /**
     * 更新耗材业务调整
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffAdjust(@Valid PropertyStuffAdjustSaveReqVO updateReqVO);

    /**
     * 删除耗材业务调整
     *
     * @param id 编号
     */
    void deletePropertyStuffAdjust(Long id);

    /**
     * 获得耗材业务调整
     *
     * @param id 编号
     * @return 耗材业务调整
     */
    PropertyStuffAdjustDO getPropertyStuffAdjust(Long id);

    /**
     * 获得耗材业务调整分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材业务调整分页
     */
    PageResult<PropertyStuffAdjustDO> getPropertyStuffAdjustPage(PropertyStuffAdjustPageReqVO pageReqVO);
    PageResult<PropertyStuffAdjustRespVO> getPropertyStuffAdjustPageVO(PropertyStuffAdjustPageReqVO pageReqVO);

}