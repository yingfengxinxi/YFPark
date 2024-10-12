package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPriceDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomPriceMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 房间价格 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class RoomPriceServiceImpl implements RoomPriceService {

    @Resource
    private RoomPriceMapper roomPriceMapper;

    @Override
    public Long createRoomPrice(RoomPriceSaveReqVO createReqVO) {
        // 插入
        RoomPriceDO roomPrice = BeanUtils.toBean(createReqVO, RoomPriceDO.class);
        roomPriceMapper.insert(roomPrice);
        // 返回
        return roomPrice.getId();
    }

    @Override
    public void updateRoomPrice(RoomPriceSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomPriceExists(updateReqVO.getId());
        // 更新
        RoomPriceDO updateObj = BeanUtils.toBean(updateReqVO, RoomPriceDO.class);
        roomPriceMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoomPrice(Long id) {
        // 校验存在
        validateRoomPriceExists(id);
        // 删除
        roomPriceMapper.deleteById(id);
    }

    private void validateRoomPriceExists(Long id) {
        if (roomPriceMapper.selectById(id) == null) {
            throw exception(ROOM_PRICE_NOT_EXISTS);
        }
    }

    @Override
    public RoomPriceDO getRoomPrice(Long id) {
        return roomPriceMapper.selectById(id);
    }

    @Override
    public PageResult<RoomPriceDO> getRoomPricePage(RoomPricePageReqVO pageReqVO) {
        return roomPriceMapper.selectPage(pageReqVO);
    }

}