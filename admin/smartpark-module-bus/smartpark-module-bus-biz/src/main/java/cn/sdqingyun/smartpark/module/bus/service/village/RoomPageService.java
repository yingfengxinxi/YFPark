package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPageDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 招商中心装修页 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface RoomPageService {

    /**
     * 创建招商中心装修页
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoomPage(@Valid RoomPageSaveReqVO createReqVO);

    /**
     * 更新招商中心装修页
     *
     * @param updateReqVO 更新信息
     */
    void updateRoomPage(@Valid RoomPageSaveReqVO updateReqVO);

    /**
     * 删除招商中心装修页
     *
     * @param id 编号
     */
    void deleteRoomPage(Long id);

    /**
     * 获得招商中心装修页
     *
     * @param id 编号
     * @return 招商中心装修页
     */
    RoomPageDO getRoomPage(Long id);

    /**
     * 获得招商中心装修页分页
     *
     * @param pageReqVO 分页查询
     * @return 招商中心装修页分页
     */
    PageResult<RoomPageDO> getRoomPagePage(RoomPagePageReqVO pageReqVO);

}