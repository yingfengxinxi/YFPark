package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomRemarksDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 房源操作记录 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface RoomRemarksService {

    /**
     * 创建房源操作记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoomRemarks(@Valid RoomRemarksSaveReqVO createReqVO);

    /**
     * 更新房源操作记录
     *
     * @param updateReqVO 更新信息
     */
    void updateRoomRemarks(@Valid RoomRemarksSaveReqVO updateReqVO);

    /**
     * 删除房源操作记录
     *
     * @param id 编号
     */
    void deleteRoomRemarks(Long id);

    /**
     * 获得房源操作记录
     *
     * @param id 编号
     * @return 房源操作记录
     */
    RoomRemarksDO getRoomRemarks(Long id);

    /**
     * 获得房源操作记录分页
     *
     * @param pageReqVO 分页查询
     * @return 房源操作记录分页
     */
    PageResult<RoomRemarksDO> getRoomRemarksPage(RoomRemarksPageReqVO pageReqVO);

}