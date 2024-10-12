package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandoutDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产派发/退库 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyHandoutService {

    /**
     * 创建资产派发/退库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyHandout(@Valid PropertyHandoutSaveReqVO createReqVO);

    /**
     * 更新资产派发/退库
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyHandout(@Valid PropertyHandoutSaveReqVO updateReqVO);

    /**
     * 删除资产派发/退库
     *
     * @param id 编号
     */
    void deletePropertyHandout(Long id);

    /**
     * 获得资产派发/退库
     *
     * @param id 编号
     * @return 资产派发/退库
     */
    PropertyHandoutDO getPropertyHandout(Long id);

    /**
     * 获得资产派发/退库分页
     *
     * @param pageReqVO 分页查询
     * @return 资产派发/退库分页
     */
    PageResult<PropertyHandoutDO> getPropertyHandoutPage(PropertyHandoutPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 分页查询VO
    * @Date 10:34 2024/9/13
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyHandoutRespVO>
    **/
    PageResult<PropertyHandoutRespVO> getPropertyHandoutPageVO(PropertyHandoutPageReqVO pageReqVO);

}