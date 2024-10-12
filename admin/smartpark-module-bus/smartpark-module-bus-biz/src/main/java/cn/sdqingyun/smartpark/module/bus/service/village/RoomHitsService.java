package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomHitsDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 房间点击量 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface RoomHitsService {

    /**
     * 创建房间点击量
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoomHits(@Valid RoomHitsSaveReqVO createReqVO);

    /**
     * 更新房间点击量
     *
     * @param updateReqVO 更新信息
     */
    void updateRoomHits(@Valid RoomHitsSaveReqVO updateReqVO);

    /**
     * 删除房间点击量
     *
     * @param id 编号
     */
    void deleteRoomHits(Long id);

    /**
     * 获得房间点击量
     *
     * @param id 编号
     * @return 房间点击量
     */
    RoomHitsDO getRoomHits(Long id);

    /**
     * 获得房间点击量分页
     *
     * @param pageReqVO 分页查询
     * @return 房间点击量分页
     */
    PageResult<RoomHitsDO> getRoomHitsPage(RoomHitsPageReqVO pageReqVO);

}