package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffTransferDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 耗材业务调拨 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffTransferService {

    /**
     * 创建耗材业务调拨
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffTransfer(@Valid PropertyStuffTransferSaveReqVO createReqVO);

    /**
     * 更新耗材业务调拨
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffTransfer(@Valid PropertyStuffTransferSaveReqVO updateReqVO);

    /**
     * 删除耗材业务调拨
     *
     * @param id 编号
     */
    void deletePropertyStuffTransfer(Long id);

    /**
     * 获得耗材业务调拨
     *
     * @param id 编号
     * @return 耗材业务调拨
     */
    PropertyStuffTransferDO getPropertyStuffTransfer(Long id);

    /**
     * 获得耗材业务调拨分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材业务调拨分页
     */
    PageResult<PropertyStuffTransferDO> getPropertyStuffTransferPage(PropertyStuffTransferPageReqVO pageReqVO);
    PageResult<PropertyStuffTransferRespVO> getPropertyStuffTransferPageVO(PropertyStuffTransferPageReqVO pageReqVO);

}