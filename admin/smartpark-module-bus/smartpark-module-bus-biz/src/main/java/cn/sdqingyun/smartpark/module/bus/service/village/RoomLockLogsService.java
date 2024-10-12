package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomLockLogsDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目房间锁定日志 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface RoomLockLogsService {

    /**
     * 创建项目房间锁定日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoomLockLogs(@Valid RoomLockLogsSaveReqVO createReqVO);

    /**
     * 更新项目房间锁定日志
     *
     * @param updateReqVO 更新信息
     */
    void updateRoomLockLogs(@Valid RoomLockLogsSaveReqVO updateReqVO);

    /**
     * 删除项目房间锁定日志
     *
     * @param id 编号
     */
    void deleteRoomLockLogs(Long id);

    /**
     * 获得项目房间锁定日志
     *
     * @param id 编号
     * @return 项目房间锁定日志
     */
    RoomLockLogsDO getRoomLockLogs(Long id);

    /**
     * 获得项目房间锁定日志分页
     *
     * @param pageReqVO 分页查询
     * @return 项目房间锁定日志分页
     */
    PageResult<RoomLockLogsDO> getRoomLockLogsPage(RoomLockLogsPageReqVO pageReqVO);

}