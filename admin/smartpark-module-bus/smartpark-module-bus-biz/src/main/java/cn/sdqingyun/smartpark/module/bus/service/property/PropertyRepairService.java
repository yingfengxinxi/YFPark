package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRepairDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产维修 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyRepairService {

    /**
     * 创建资产维修
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyRepair(@Valid PropertyRepairSaveReqVO createReqVO);

    /**
     * 更新资产维修
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyRepair(@Valid PropertyRepairSaveReqVO updateReqVO);

    /**
     * 删除资产维修
     *
     * @param id 编号
     */
    void deletePropertyRepair(Long id);

    /**
     * 获得资产维修
     *
     * @param id 编号
     * @return 资产维修
     */
    PropertyRepairDO getPropertyRepair(Long id);

    /**
     * 获得资产维修分页
     *
     * @param pageReqVO 分页查询
     * @return 资产维修分页
     */
    PageResult<PropertyRepairDO> getPropertyRepairPage(PropertyRepairPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 分页查询VO
    * @Date 10:18 2024/9/14
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyRepairRespVO>
    **/
    PageResult<PropertyRepairRespVO> getPropertyRepairPageVO(PropertyRepairPageReqVO pageReqVO);

}