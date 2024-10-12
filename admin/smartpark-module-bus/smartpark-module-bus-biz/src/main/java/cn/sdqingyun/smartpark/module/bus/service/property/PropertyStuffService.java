package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDO;
import jakarta.validation.Valid;

import java.util.Map;

/**
 * 耗材档案信息 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffService {

    /**
     * 创建耗材档案信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuff(@Valid PropertyStuffSaveReqVO createReqVO);

    /**
     * 更新耗材档案信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuff(@Valid PropertyStuffSaveReqVO updateReqVO);

    /**
     * 删除耗材档案信息
     *
     * @param id 编号
     */
    void deletePropertyStuff(Long id);

    /**
     * 获得耗材档案信息
     *
     * @param id 编号
     * @return 耗材档案信息
     */
    PropertyStuffDO getPropertyStuff(Long id);

    /**
     * 获得耗材档案信息分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材档案信息分页
     */
    PageResult<PropertyStuffDO> getPropertyStuffPage(PropertyStuffPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 分页查询VO
    * @Date 9:31 2024/9/18
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffRespVO>
    **/
    PageResult<PropertyStuffRespVO> getPropertyStuffPageVO(PropertyStuffPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 预警与提醒
    * @Date 15:34 2024/9/13
    * @Param []
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getHightLow();


}