package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffStockDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 耗材即时库存 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffStockService {

    /**
     * 创建耗材即时库存
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffStock(@Valid PropertyStuffStockSaveReqVO createReqVO);

    /**
     * 更新耗材即时库存
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffStock(@Valid PropertyStuffStockSaveReqVO updateReqVO);

    /**
     * 删除耗材即时库存
     *
     * @param id 编号
     */
    void deletePropertyStuffStock(Long id);

    /**
     * 获得耗材即时库存
     *
     * @param id 编号
     * @return 耗材即时库存
     */
    PropertyStuffStockDO getPropertyStuffStock(Long id);

    /**
     * 获得耗材即时库存分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材即时库存分页
     */
    PageResult<PropertyStuffStockDO> getPropertyStuffStockPage(PropertyStuffStockPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 耗材入库申请
    * @Date 14:47 2024/8/30
    * @Param [list]
    * @return java.lang.Long
    **/
    Long savePropertyStuff(List<PropertyStuffProcessSaveReqVO> list);

    /**
     * @return void
     * @Author SUNk
     * @Description 耗材审批回调接口
     * @Date 14:46 2024/8/14
     * @Param [id 流程ID, status 流程审批状态]
     **/
    void updatePropertyStuffStatus(String id, Integer status);

    /**
     * 获得耗材即时库存分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材即时库存分页
     */
    PageResult<PropertyStuffStockRespVO> getPropertyStuffStockPageVO(PropertyStuffStockPageReqVO pageReqVO);

    /**
     *
     * @param pageReqVO
     * @return
     */
    PageResult<StockListPageVO> stockListPage(StockListPageVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 根据审批流程获得分页
    * @Date 9:32 2024/9/20
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.StockListPageVO>
    **/
    PageResult<PropertyStuffStockRespVO> getPropertyStuffPageByApprove(PropertyStuffProcessPageReqVO pageReqVO);
}