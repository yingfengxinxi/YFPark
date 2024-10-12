package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPageDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomPageMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 招商中心装修页 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class RoomPageServiceImpl implements RoomPageService {

    @Resource
    private RoomPageMapper roomPageMapper;

    @Override
    public Long createRoomPage(RoomPageSaveReqVO createReqVO) {
        // 插入
        RoomPageDO roomPage = BeanUtils.toBean(createReqVO, RoomPageDO.class);
        roomPageMapper.insert(roomPage);
        // 返回
        return roomPage.getId();
    }

    @Override
    public void updateRoomPage(RoomPageSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomPageExists(updateReqVO.getId());
        // 更新
        RoomPageDO updateObj = BeanUtils.toBean(updateReqVO, RoomPageDO.class);
        roomPageMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoomPage(Long id) {
        // 校验存在
        validateRoomPageExists(id);
        // 删除
        roomPageMapper.deleteById(id);
    }

    private void validateRoomPageExists(Long id) {
        if (roomPageMapper.selectById(id) == null) {
            throw exception(ROOM_PAGE_NOT_EXISTS);
        }
    }

    @Override
    public RoomPageDO getRoomPage(Long id) {
        return roomPageMapper.selectById(id);
    }

    @Override
    public PageResult<RoomPageDO> getRoomPagePage(RoomPagePageReqVO pageReqVO) {
        return roomPageMapper.selectPage(pageReqVO);
    }

}