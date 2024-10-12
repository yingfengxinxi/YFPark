package cn.sdqingyun.smartpark.module.bus.service.property;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffProcessDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 耗材业务流程 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffProcessService {

    /**
     * 创建耗材业务流程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffProcess(@Valid PropertyStuffProcessSaveReqVO createReqVO);

    /**
     * 更新耗材业务流程
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffProcess(@Valid PropertyStuffProcessSaveReqVO updateReqVO);

    /**
     * 删除耗材业务流程
     *
     * @param id 编号
     */
    void deletePropertyStuffProcess(Long id);

    /**
     * 获得耗材业务流程
     *
     * @param id 编号
     * @return 耗材业务流程
     */
    PropertyStuffProcessDO getPropertyStuffProcess(Long id);

    /**
     * 获得耗材业务流程分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材业务流程分页
     */
    PageResult<PropertyStuffProcessDO> getPropertyStuffProcessPage(PropertyStuffProcessPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 查询分页VO
    * @Date 17:46 2024/9/21
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffProcessRespVO>
    **/
    PageResult<PropertyStuffProcessRespVO> getPropertyStuffProcessPageVO(PropertyStuffProcessPageReqVO pageReqVO);
    
    /**
    * @Author SUNk
    * @Description 查询耗材待审批单据
    * @Date 10:53 2024/9/24
    * @Param []
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getProcessCount();

}