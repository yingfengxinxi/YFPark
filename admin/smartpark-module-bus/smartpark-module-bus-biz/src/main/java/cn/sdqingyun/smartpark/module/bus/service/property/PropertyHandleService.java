package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandleDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产处置单据记录 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyHandleService {

    /**
     * 创建资产处置单据记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyHandle(@Valid PropertyHandleSaveReqVO createReqVO);

    /**
     * 更新资产处置单据记录
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyHandle(@Valid PropertyHandleSaveReqVO updateReqVO);

    /**
     * 删除资产处置单据记录
     *
     * @param id 编号
     */
    void deletePropertyHandle(Long id);

    /**
     * 获得资产处置单据记录
     *
     * @param id 编号
     * @return 资产处置单据记录
     */
    PropertyHandleDO getPropertyHandle(Long id);

    /**
     * 获得资产处置单据记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产处置单据记录分页
     */
    PageResult<PropertyHandleDO> getPropertyHandlePage(PropertyHandlePageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 查询分页VO
    * @Date 9:43 2024/9/14
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyHandleRespVO>
    **/
    PageResult<PropertyHandleRespVO> getPropertyHandlePageVO(PropertyHandlePageReqVO pageReqVO);

}