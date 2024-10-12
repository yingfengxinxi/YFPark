package cn.sdqingyun.smartpark.module.bus.service.park;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkChargePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkChargeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkChargeDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 停车场收费标准 Service 接口
 *
 * @author 智慧园区
 */
public interface ParkChargeService {

    /**
     * 创建停车场收费标准
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ParkChargeSaveReqVO createReqVO);

    /**
     * 更新停车场收费标准
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid ParkChargeSaveReqVO updateReqVO);

    /**
     * 删除停车场收费标准
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得停车场收费标准
     *
     * @param id 编号
     * @return 停车场收费标准
     */
    ParkChargeDO get(Long id);

    /**
     * 获得停车场收费标准分页
     *
     * @param pageReqVO 分页查询
     * @return 停车场收费标准分页
     */
    PageResult<ParkChargeDO> getPage(ParkChargePageReqVO pageReqVO);

    /**
     *
     * @param parkId
     * @param villageId
     * @param chargeName
     * @param id
     * @return
     */
    Boolean isCheckName(Long parkId,Long villageId,String chargeName,Long id);
}