package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyTransferDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产调拨 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyTransferService {

    /**
     * 创建资产调拨
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyTransfer(@Valid PropertyTransferSaveReqVO createReqVO);

    /**
     * 更新资产调拨
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyTransfer(@Valid PropertyTransferSaveReqVO updateReqVO);

    /**
     * 删除资产调拨
     *
     * @param id 编号
     */
    void deletePropertyTransfer(Long id);

    /**
     * 获得资产调拨
     *
     * @param id 编号
     * @return 资产调拨
     */
    PropertyTransferDO getPropertyTransfer(Long id);

    /**
     * 获得资产调拨分页
     *
     * @param pageReqVO 分页查询
     * @return 资产调拨分页
     */
    PageResult<PropertyTransferDO> getPropertyTransferPage(PropertyTransferPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 查询分页VO
    * @Date 9:30 2024/9/14
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyTransferRespVO>
    **/
    PageResult<PropertyTransferRespVO> getPropertyTransferPageVO(PropertyTransferPageReqVO pageReqVO);

}