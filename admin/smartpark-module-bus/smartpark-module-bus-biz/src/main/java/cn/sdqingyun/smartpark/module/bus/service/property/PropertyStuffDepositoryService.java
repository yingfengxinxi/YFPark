package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffDepositoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffDepositoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffDepositorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDepositoryDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 耗材档案信息 Service 接口
 *
 * @author 智慧园区
 */
public interface PropertyStuffDepositoryService {

    /**
     * 创建耗材档案信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPropertyStuffDepository(@Valid PropertyStuffDepositorySaveReqVO createReqVO);

    /**
     * 更新耗材档案信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePropertyStuffDepository(@Valid PropertyStuffDepositorySaveReqVO updateReqVO);

    /**
     * 删除耗材档案信息
     *
     * @param id 编号
     */
    void deletePropertyStuffDepository(Long id);

    /**
     * 获得耗材档案信息
     *
     * @param id 编号
     * @return 耗材档案信息
     */
    PropertyStuffDepositoryDO getPropertyStuffDepository(Long id);

    /**
     * 获得耗材档案信息分页
     *
     * @param pageReqVO 分页查询
     * @return 耗材档案信息分页
     */
    PageResult<PropertyStuffDepositoryRespVO> getPropertyStuffDepositoryPage(PropertyStuffDepositoryPageReqVO pageReqVO);

    /**
     * @Author SUNk
     * @Description 获得耗材档案信息树
     * @Date 9:47 2024/8/11
     * @Param [updateReqVO]
     * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO>
     **/
    List<PropertyStuffDepositoryRespVO> getTree();
}