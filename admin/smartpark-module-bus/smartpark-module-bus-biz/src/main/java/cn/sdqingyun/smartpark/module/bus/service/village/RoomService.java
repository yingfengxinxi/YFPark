package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.RoomImportExcelVO;
import com.alibaba.fastjson.JSONObject;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目房间 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface RoomService {

    /**
     * 创建项目房间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoom(@Valid RoomSaveReqVO createReqVO);

    /**
     *
     * @param roomId
     * @param roomStatus
     */
    void updateByIdRoomStatus(Long roomId,Integer roomStatus);

    /**
     * 更新项目房间
     *
     * @param updateReqVO 更新信息
     */
    void updateRoom(@Valid RoomSaveReqVO updateReqVO);

    /**
     * 删除项目房间
     *
     * @param id 编号
     */
    void deleteRoom(Long id);

    /**
     * 获得项目房间
     *
     * @param id 编号
     * @return 项目房间
     */
    RoomDO getRoom(Long id);


    /**
     *
     * @param villageId
     * @param buildId
     * @param roomName
     * @return
     */
    Long getRoomNameId(Long villageId,Long buildId,String roomName );

    /**
     * 获得项目房间分页
     *
     * @param pageReqVO 分页查询
     * @return 项目房间分页
     */
    PageResult<RoomDO> getRoomPage(RoomPageReqVO pageReqVO);

    Boolean importExcel(List<RoomImportExcelVO> roomImportExcelVOS);

    /**
     * 获得项目房间
     *
     * @param id 编号
     * @return 项目房间
     */
    RoomRespVO getOneRoom(Long id);

    /**
     *
     * @param buildId
     * @param roomNumber
     * @return
     */
    Long getByRoomNameId(Long buildId,String roomNumber);

    List<JSONObject> resourceList(RoomPageReqVO pageReqVO);
}