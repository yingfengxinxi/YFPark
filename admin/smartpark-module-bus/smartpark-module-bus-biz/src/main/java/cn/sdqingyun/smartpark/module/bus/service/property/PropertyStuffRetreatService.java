package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffRetreatDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 耗材业务退库 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffRetreatService {

    /**
     * 创建耗材业务退库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffRetreat(@Valid PropertyStuffRetreatSaveReqVO createReqVO);

    /**
     * 更新耗材业务退库
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffRetreat(@Valid PropertyStuffRetreatSaveReqVO updateReqVO);

    /**
     * 删除耗材业务退库
     *
     * @param id 编号
     */
    void deletePropertyStuffRetreat(Long id);

    /**
     * 获得耗材业务退库
     *
     * @param id 编号
     * @return 耗材业务退库
     */
    PropertyStuffRetreatDO getPropertyStuffRetreat(Long id);

    /**
     * 获得耗材业务退库分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材业务退库分页
     */
    PageResult<PropertyStuffRetreatDO> getPropertyStuffRetreatPage(PropertyStuffRetreatPageReqVO pageReqVO);
    PageResult<PropertyStuffRetreatRespVO> getPropertyStuffRetreatPageVO(PropertyStuffRetreatPageReqVO pageReqVO);

}