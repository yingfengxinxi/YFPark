package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomLockLogsDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomLockLogsMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目房间锁定日志 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class RoomLockLogsServiceImpl implements RoomLockLogsService {

    @Resource
    private RoomLockLogsMapper roomLockLogsMapper;

    @Override
    public Long createRoomLockLogs(RoomLockLogsSaveReqVO createReqVO) {
        // 插入
        RoomLockLogsDO roomLockLogs = BeanUtils.toBean(createReqVO, RoomLockLogsDO.class);
        roomLockLogsMapper.insert(roomLockLogs);
        // 返回
        return roomLockLogs.getId();
    }

    @Override
    public void updateRoomLockLogs(RoomLockLogsSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomLockLogsExists(updateReqVO.getId());
        // 更新
        RoomLockLogsDO updateObj = BeanUtils.toBean(updateReqVO, RoomLockLogsDO.class);
        roomLockLogsMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoomLockLogs(Long id) {
        // 校验存在
        validateRoomLockLogsExists(id);
        // 删除
        roomLockLogsMapper.deleteById(id);
    }

    private void validateRoomLockLogsExists(Long id) {
        if (roomLockLogsMapper.selectById(id) == null) {
            throw exception(ROOM_LOCK_LOGS_NOT_EXISTS);
        }
    }

    @Override
    public RoomLockLogsDO getRoomLockLogs(Long id) {
        return roomLockLogsMapper.selectById(id);
    }

    @Override
    public PageResult<RoomLockLogsDO> getRoomLockLogsPage(RoomLockLogsPageReqVO pageReqVO) {
        return roomLockLogsMapper.selectPage(pageReqVO);
    }

}