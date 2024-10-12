package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLocationPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLocationRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLocationSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 位置 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyLocationService {

    /**
     * 创建位置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyLocation(@Valid PropertyLocationSaveReqVO createReqVO);

    /**
     * 更新位置
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyLocation(@Valid PropertyLocationSaveReqVO updateReqVO);

    /**
     * 删除位置
     *
     * @param id 编号
     */
    void deletePropertyLocation(Long id);

    /**
     * 获得位置
     *
     * @param id 编号
     * @return 位置
     */
    PropertyLocationDO getPropertyLocation(Long id);

    /**
     * 获得位置分页
     *
     * @param pageReqVO 分页查询
     * @return 位置分页
     */
    PageResult<PropertyLocationDO> getPropertyLocationPage(PropertyLocationPageReqVO pageReqVO);
    
    /**
    * @Author SUNk
    * @Description 获得位置分页VO
    * @Date 15:48 2024/8/11
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLocationRespVO>
    **/
    PageResult<PropertyLocationRespVO> getPropertyLocationPageVO(PropertyLocationPageReqVO pageReqVO);

    /**
     * @Author SUNk
     * @Description 获得资产位置树
     * @Date 9:47 2024/8/11
     * @Param [updateReqVO]
     * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO>
     **/
    List<PropertyLocationRespVO> getTree();
}