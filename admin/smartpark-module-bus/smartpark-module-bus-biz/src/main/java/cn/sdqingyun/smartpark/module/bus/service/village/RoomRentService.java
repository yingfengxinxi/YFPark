package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomRentDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 租客在租/绑定房间 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface RoomRentService {

    /**
     * 创建租客在租/绑定房间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoomRent(@Valid RoomRentSaveReqVO createReqVO);

    /**
     * 更新租客在租/绑定房间
     *
     * @param updateReqVO 更新信息
     */
    void updateRoomRent(@Valid RoomRentSaveReqVO updateReqVO);

    /**
     * 删除租客在租/绑定房间
     *
     * @param id 编号
     */
    void deleteRoomRent(Long id);

    /**
     * 获得租客在租/绑定房间
     *
     * @param id 编号
     * @return 租客在租/绑定房间
     */
    RoomRentDO getRoomRent(Long id);

    /**
     * 获得租客在租/绑定房间分页
     *
     * @param pageReqVO 分页查询
     * @return 租客在租/绑定房间分页
     */
    PageResult<RoomRentDO> getRoomRentPage(RoomRentPageReqVO pageReqVO);

}