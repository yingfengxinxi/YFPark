package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.sdqingyun.smartpark.framework.common.util.servlet.ServletUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomRemarksDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomRemarksMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 房源操作记录 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class RoomRemarksServiceImpl implements RoomRemarksService {

    @Resource
    private RoomRemarksMapper roomRemarksMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Long createRoomRemarks(RoomRemarksSaveReqVO createReqVO) {
        // 插入
        RoomRemarksDO roomRemarks = BeanUtils.toBean(createReqVO, RoomRemarksDO.class);
        roomRemarksMapper.insert(roomRemarks);
        // 返回
        return roomRemarks.getId();
    }

    @Override
    public void updateRoomRemarks(RoomRemarksSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomRemarksExists(updateReqVO.getId());
        // 更新
        RoomRemarksDO updateObj = BeanUtils.toBean(updateReqVO, RoomRemarksDO.class);
        roomRemarksMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoomRemarks(Long id) {
        // 校验存在
        validateRoomRemarksExists(id);
        // 删除
        roomRemarksMapper.deleteById(id);
    }

    private void validateRoomRemarksExists(Long id) {
        if (roomRemarksMapper.selectById(id) == null) {
            throw exception(ROOM_REMARKS_NOT_EXISTS);
        }
    }

    @Override
    public RoomRemarksDO getRoomRemarks(Long id) {
        return roomRemarksMapper.selectById(id);
    }

    /**
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<RoomRemarksDO> getRoomRemarksPage(RoomRemarksPageReqVO pageReqVO) {
        LambdaQueryWrapperX<RoomRemarksDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getRoomId() != null) {
            queryWrapperX.eq(RoomRemarksDO::getRoomId, pageReqVO.getRoomId());
        }
        queryWrapperX.orderByDesc(RoomRemarksDO::getCreateTime);
        PageResult<RoomRemarksDO> roomRemarksDOPageResult = roomRemarksMapper.selectPage(pageReqVO, queryWrapperX);
        List<RoomRemarksDO> list = roomRemarksDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(roomRemarksDO -> {
                String creator = roomRemarksDO.getCreator();
                String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                roomRemarksDO.setCreator(userName);
            });
        }
        return roomRemarksDOPageResult;
    }

}