package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomTaskPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomTaskSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRoomTaskDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 房间对应抄表任务 Service 接口
 *
 * @author 管理员
 */
public interface EnergyRoomTaskService {

    /**
     * 创建房间对应抄表任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyRoomTaskSaveReqVO createReqVO);

    /**
     * 更新房间对应抄表任务
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyRoomTaskSaveReqVO updateReqVO);

    /**
     * 删除房间对应抄表任务
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得房间对应抄表任务
     *
     * @param id 编号
     * @return 房间对应抄表任务
     */
    EnergyRoomTaskDO get(Long id);

    /**
     * 获得房间对应抄表任务分页
     *
     * @param pageReqVO 分页查询
     * @return 房间对应抄表任务分页
     */
    PageResult<EnergyRoomTaskDO> getPage(EnergyRoomTaskPageReqVO pageReqVO);

}