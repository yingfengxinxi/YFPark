package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLendoutDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产借出 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyLendoutService {

    /**
     * 创建资产借出
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyLendout(@Valid PropertyLendoutSaveReqVO createReqVO);

    /**
     * 更新资产借出
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyLendout(@Valid PropertyLendoutSaveReqVO updateReqVO);

    /**
     * 删除资产借出
     *
     * @param id 编号
     */
    void deletePropertyLendout(Long id);

    /**
     * 获得资产借出
     *
     * @param id 编号
     * @return 资产借出
     */
    PropertyLendoutDO getPropertyLendout(Long id);

    /**
     * 获得资产借出分页
     *
     * @param pageReqVO 分页查询
     * @return 资产借出分页
     */
    PageResult<PropertyLendoutDO> getPropertyLendoutPage(PropertyLendoutPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 查询列表
    * @Date 10:57 2024/9/13
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLendoutRespVO>
    **/
    PageResult<PropertyLendoutRespVO> getPropertyLendoutPageVO(PropertyLendoutPageReqVO pageReqVO);

}