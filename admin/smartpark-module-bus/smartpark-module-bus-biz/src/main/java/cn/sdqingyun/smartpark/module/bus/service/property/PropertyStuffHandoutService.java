package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffHandoutDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 耗材业务派发 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffHandoutService {

    /**
     * 创建耗材业务派发
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffHandout(@Valid PropertyStuffHandoutSaveReqVO createReqVO);

    /**
     * 更新耗材业务派发
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffHandout(@Valid PropertyStuffHandoutSaveReqVO updateReqVO);

    /**
     * 删除耗材业务派发
     *
     * @param id 编号
     */
    void deletePropertyStuffHandout(Long id);

    /**
     * 获得耗材业务派发
     *
     * @param id 编号
     * @return 耗材业务派发
     */
    PropertyStuffHandoutDO getPropertyStuffHandout(Long id);

    /**
     * 获得耗材业务派发分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材业务派发分页
     */
    PageResult<PropertyStuffHandoutDO> getPropertyStuffHandoutPage(PropertyStuffHandoutPageReqVO pageReqVO);
    PageResult<PropertyStuffHandoutRespVO> getPropertyStuffHandoutPageVO(PropertyStuffHandoutPageReqVO pageReqVO);

}