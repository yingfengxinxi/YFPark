package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyApproveDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产单据审批记录 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyApproveService {

    /**
     * 创建资产单据审批记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyApprove(@Valid PropertyApproveSaveReqVO createReqVO);

    /**
     * 更新资产单据审批记录
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyApprove(@Valid PropertyApproveSaveReqVO updateReqVO);

    /**
     * 删除资产单据审批记录
     *
     * @param id 编号
     */
    void deletePropertyApprove(Long id);

    /**
     * 获得资产单据审批记录
     *
     * @param id 编号
     * @return 资产单据审批记录
     */
    PropertyApproveDO getPropertyApprove(Long id);

    /**
     * 获得资产单据审批记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产单据审批记录分页
     */
    PageResult<PropertyApproveDO> getPropertyApprovePage(PropertyApprovePageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 查询资产待审批单据
    * @Date 10:17 2024/9/24
    * @Param []
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getApproveCount();

}