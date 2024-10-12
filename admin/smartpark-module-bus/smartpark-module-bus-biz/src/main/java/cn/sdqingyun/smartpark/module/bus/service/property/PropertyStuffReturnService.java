package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffReturnPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffReturnRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffReturnSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffReturnDO;
import jakarta.validation.Valid;

/**
 * 耗材业务耗材退还 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffReturnService {

    /**
     * 创建耗材业务耗材退还
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffReturn(@Valid PropertyStuffReturnSaveReqVO createReqVO);

    /**
     * 更新耗材业务耗材退还
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffReturn(@Valid PropertyStuffReturnSaveReqVO updateReqVO);

    /**
     * 删除耗材业务耗材退还
     *
     * @param id 编号
     */
    void deletePropertyStuffReturn(Long id);

    /**
     * 获得耗材业务耗材退还
     *
     * @param id 编号
     * @return 耗材业务耗材退还
     */
    PropertyStuffReturnDO getPropertyStuffReturn(Long id);

    /**
     * 获得耗材业务耗材退还分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材业务耗材退还分页
     */
    PageResult<PropertyStuffReturnDO> getPropertyStuffReturnPage(PropertyStuffReturnPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 分页查询vo
    * @Date 10:01 2024/9/22
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffReturnRespVO>
    **/
    PageResult<PropertyStuffReturnRespVO> getPropertyStuffReturnPageVO(PropertyStuffReturnPageReqVO pageReqVO);

}