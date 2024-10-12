package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskLogDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义抄表任务日志 Service 接口
 *
 * @author 管理员
 */
public interface EnergyTaskLogService {

    /**
     * 创建自定义抄表任务日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyTaskLogSaveReqVO createReqVO);

    /**
     * 更新自定义抄表任务日志
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyTaskLogSaveReqVO updateReqVO);

    /**
     * 删除自定义抄表任务日志
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义抄表任务日志
     *
     * @param id 编号
     * @return 自定义抄表任务日志
     */
    EnergyTaskLogDO get(Long id);

    /**
     * 获得自定义抄表任务日志分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义抄表任务日志分页
     */
    PageResult<EnergyTaskLogDO> getPage(EnergyTaskLogPageReqVO pageReqVO);

}