package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPriceDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 房间价格 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface RoomPriceService {

    /**
     * 创建房间价格
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoomPrice(@Valid RoomPriceSaveReqVO createReqVO);

    /**
     * 更新房间价格
     *
     * @param updateReqVO 更新信息
     */
    void updateRoomPrice(@Valid RoomPriceSaveReqVO updateReqVO);

    /**
     * 删除房间价格
     *
     * @param id 编号
     */
    void deleteRoomPrice(Long id);

    /**
     * 获得房间价格
     *
     * @param id 编号
     * @return 房间价格
     */
    RoomPriceDO getRoomPrice(Long id);

    /**
     * 获得房间价格分页
     *
     * @param pageReqVO 分页查询
     * @return 房间价格分页
     */
    PageResult<RoomPriceDO> getRoomPricePage(RoomPricePageReqVO pageReqVO);

}