package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomRentDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomRentMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 租客在租/绑定房间 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class RoomRentServiceImpl implements RoomRentService {

    @Resource
    private RoomRentMapper roomRentMapper;

    @Override
    public Long createRoomRent(RoomRentSaveReqVO createReqVO) {
        // 插入
        RoomRentDO roomRent = BeanUtils.toBean(createReqVO, RoomRentDO.class);
        roomRentMapper.insert(roomRent);
        // 返回
        return roomRent.getId();
    }

    @Override
    public void updateRoomRent(RoomRentSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomRentExists(updateReqVO.getId());
        // 更新
        RoomRentDO updateObj = BeanUtils.toBean(updateReqVO, RoomRentDO.class);
        roomRentMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoomRent(Long id) {
        // 校验存在
        validateRoomRentExists(id);
        // 删除
        roomRentMapper.deleteById(id);
    }

    private void validateRoomRentExists(Long id) {
        if (roomRentMapper.selectById(id) == null) {
            throw exception(ROOM_RENT_NOT_EXISTS);
        }
    }

    @Override
    public RoomRentDO getRoomRent(Long id) {
        return roomRentMapper.selectById(id);
    }

    @Override
    public PageResult<RoomRentDO> getRoomRentPage(RoomRentPageReqVO pageReqVO) {
        return roomRentMapper.selectPage(pageReqVO);
    }

}