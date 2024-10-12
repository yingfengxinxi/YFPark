package cn.sdqingyun.smartpark.module.bus.service.park;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsOperatorLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsOperatorLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarsOperatorLogDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 车辆操作日志 Service 接口
 *
 * @author 智慧园区
 */
public interface ParkCarsOperatorLogService {

    /**
     * 创建车辆操作日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ParkCarsOperatorLogSaveReqVO createReqVO);

    /**
     * 更新车辆操作日志
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid ParkCarsOperatorLogSaveReqVO updateReqVO);

    /**
     * 删除车辆操作日志
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得车辆操作日志
     *
     * @param id 编号
     * @return 车辆操作日志
     */
    ParkCarsOperatorLogDO get(Long id);

    /**
     * 获得车辆操作日志分页
     *
     * @param pageReqVO 分页查询
     * @return 车辆操作日志分页
     */
    PageResult<ParkCarsOperatorLogDO> getPage(ParkCarsOperatorLogPageReqVO pageReqVO);

}