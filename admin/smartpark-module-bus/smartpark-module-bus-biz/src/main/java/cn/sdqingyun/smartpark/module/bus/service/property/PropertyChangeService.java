package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyChangeDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产变更领用人 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyChangeService {

    /**
     * 创建资产变更领用人
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyChange(@Valid PropertyChangeSaveReqVO createReqVO);

    /**
     * 更新资产变更领用人
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyChange(@Valid PropertyChangeSaveReqVO updateReqVO);

    /**
     * 删除资产变更领用人
     *
     * @param id 编号
     */
    void deletePropertyChange(Long id);

    /**
     * 获得资产变更领用人
     *
     * @param id 编号
     * @return 资产变更领用人
     */
    PropertyChangeDO getPropertyChange(Long id);

    /**
     * 获得资产变更领用人分页
     *
     * @param pageReqVO 分页查询
     * @return 资产变更领用人分页
     */
    PageResult<PropertyChangeDO> getPropertyChangePage(PropertyChangePageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 分页查询vo
    * @Date 9:43 2024/9/20
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyChangeDO>
    **/
    PageResult<PropertyChangeRespVO> getPropertyChangePageVO(PropertyChangePageReqVO pageReqVO);

}