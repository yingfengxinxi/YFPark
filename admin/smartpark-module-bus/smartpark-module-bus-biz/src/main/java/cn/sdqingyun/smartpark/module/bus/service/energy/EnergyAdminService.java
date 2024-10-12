package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyAdminPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyAdminSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyAdminDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义抄表管理员 Service 接口
 *
 * @author 管理员
 */
public interface EnergyAdminService {

    /**
     * 创建自定义抄表管理员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyAdminSaveReqVO createReqVO);

    /**
     * 更新自定义抄表管理员
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyAdminSaveReqVO updateReqVO);

    /**
     * 删除自定义抄表管理员
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义抄表管理员
     *
     * @param id 编号
     * @return 自定义抄表管理员
     */
    EnergyAdminDO get(Long id);

    /**
     * 获得自定义抄表管理员分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义抄表管理员分页
     */
    PageResult<EnergyAdminDO> getPage(EnergyAdminPageReqVO pageReqVO);

}