package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyMaintainDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产保养记录 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyMaintainService {

    /**
     * 创建资产保养记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyMaintain(@Valid PropertyMaintainSaveReqVO createReqVO);

    /**
     * 更新资产保养记录
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyMaintain(@Valid PropertyMaintainSaveReqVO updateReqVO);

    /**
     * 删除资产保养记录
     *
     * @param id 编号
     */
    void deletePropertyMaintain(Long id);

    /**
     * 获得资产保养记录
     *
     * @param id 编号
     * @return 资产保养记录
     */
    PropertyMaintainDO getPropertyMaintain(Long id);

    /**
     * 获得资产保养记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产保养记录分页
     */
    PageResult<PropertyMaintainDO> getPropertyMaintainPage(PropertyMaintainPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 分页查询VO
    * @Date 9:49* @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyMaintainRespVO>
    **/
    PageResult<PropertyMaintainRespVO> getPropertyMaintainPageVO(PropertyMaintainPageReqVO pageReqVO);

}