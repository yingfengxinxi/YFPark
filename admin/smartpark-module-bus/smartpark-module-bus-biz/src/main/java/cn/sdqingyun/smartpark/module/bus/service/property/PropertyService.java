package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 * 资产管理 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyService {

    /**
     * 创建资产管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProperty(@Valid PropertySaveReqVO createReqVO);

    /**
     * 更新资产管理
     *
     * @param updateReqVO 更新信息
     */
    void updateProperty(@Valid PropertySaveReqVO updateReqVO);

    /**
     * 删除资产管理
     *
     * @param id 编号
     */
    void deleteProperty(Long id);

    /**
     * 获得资产管理
     *
     * @param id 编号
     * @return 资产管理
     */
    PropertyRespVO getProperty(Long id);

    /**
     * 获得资产管理分页
     *
     * @param pageReqVO 分页查询
     * @return 资产管理分页
     */
    PageResult<PropertyDO> getPropertyPage(PropertyPageReqVO pageReqVO);

    /**
     * 巡检点绑定资产分页
     *
     * @param pageReqVO
     * @return
     */
    PageResult<PropertyRespVO> getBindAssetsPropertyPage(PropertyPageReqVO pageReqVO);

    /**
     * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyRespVO>
     * @Author SUNk
     * @Description 获得资产管理分页VO
     * @Date 15:55 2024/8/12
     * @Param [pageReqVO]
     **/
    PageResult<PropertyRespVO> getPropertyPageVO(PropertyPageReqVO pageReqVO);

    /**
     * @return void
     * @Author SUNk
     * @Description 资产入库审批回调接口
     * @Date 14:46 2024/8/14
     * @Param [id 流程ID, status 流程审批状态]
     **/
    void updatePropertyStatus(String id, Integer status);

    /**
    * @Author SUNk
    * @Description 资产派发/退库
    * @Date 17:50 2024/8/24
    * @Param [createReqVO]
    * @return java.lang.Long
    **/
    Long createHandoutProperty(@Valid PropertyHandoutSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 资产借出/归还
    * @Date 9:38 2024/8/25
    * @Param [createReqVO]
    * @return java.lang.Long
    **/
    Long createLendoutProperty(@Valid PropertyLendoutSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 变更领用人
    * @Date 16:01 2024/8/25
    * @Param [createReqVO]
    * @return java.lang.Long
    **/
    Long createChangeProperty(@Valid PropertyChangeSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 资产调拨申请
    * @Date 17:08 2024/8/25
    * @Param [createReqVO]
    * @return java.lang.Long
    **/
    Long createTransferProperty(@Valid PropertyTransferSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 资产维修申请
    * @Date 17:08 2024/8/25
    * @Param [createReqVO]
    * @return java.lang.Long
    **/
    Long createRepairProperty(@Valid PropertyRepairSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 资产处置申请
    * @Date 17:08 2024/8/25
    * @Param [createReqVO]
    * @return java.lang.Long
    **/
    Long createHandleProperty(@Valid PropertyHandleSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 资产保养申请
    * @Date 17:08 2024/8/25
    * @Param [createReqVO]
    * @return java.lang.Long
    **/
    Long createMaintainProperty(@Valid PropertyMaintainSaveReqVO createReqVO);

    /**
    * @Author SUNk
    * @Description 根据审批单查询资产分页
    * @Date 11:00 2024/9/11
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyRespVO>
    **/
    PageResult<PropertyRespVO> getPropertyPageByApprove(PropertyApprovePageReqVO pageReqVO);

    /**
     * @Author SUNk
     * @Description 资产概况
     * @Date 16:26 2024/9/13
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> getAllProperty(Integer type);

    /**
    * @Author SUNk
    * @Description 我的资产
    * @Date 9:06 2024/9/24
    * @Param []
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getMyProperty();

    /**
    * @Author SUNk
    * @Description 近30日需归还资产
    * @Date 10:12 2024/9/24
    * @Param [pageReqVO]
    * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyRespVO>
    **/
    List<PropertyRespVO> returnPropertyPage();
}