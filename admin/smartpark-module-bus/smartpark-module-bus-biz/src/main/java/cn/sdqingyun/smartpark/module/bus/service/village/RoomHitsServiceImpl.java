package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomHitsDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomHitsMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 房间点击量 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class RoomHitsServiceImpl implements RoomHitsService {

    @Resource
    private RoomHitsMapper roomHitsMapper;

    @Override
    public Long createRoomHits(RoomHitsSaveReqVO createReqVO) {
        // 插入
        RoomHitsDO roomHits = BeanUtils.toBean(createReqVO, RoomHitsDO.class);
        roomHitsMapper.insert(roomHits);
        // 返回
        return roomHits.getId();
    }

    @Override
    public void updateRoomHits(RoomHitsSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomHitsExists(updateReqVO.getId());
        // 更新
        RoomHitsDO updateObj = BeanUtils.toBean(updateReqVO, RoomHitsDO.class);
        roomHitsMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoomHits(Long id) {
        // 校验存在
        validateRoomHitsExists(id);
        // 删除
        roomHitsMapper.deleteById(id);
    }

    private void validateRoomHitsExists(Long id) {
        if (roomHitsMapper.selectById(id) == null) {
            throw exception(ROOM_HITS_NOT_EXISTS);
        }
    }

    @Override
    public RoomHitsDO getRoomHits(Long id) {
        return roomHitsMapper.selectById(id);
    }

    @Override
    public PageResult<RoomHitsDO> getRoomHitsPage(RoomHitsPageReqVO pageReqVO) {
        return roomHitsMapper.selectPage(pageReqVO);
    }

}