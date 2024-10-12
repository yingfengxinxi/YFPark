package cn.sdqingyun.smartpark.module.bus.service.park;

import java.text.ParseException;
import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarOrderDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 车的收费订单 Service 接口
 *
 * @author 智慧园区
 */
public interface ParkCarOrderService {

    /**
     * 创建车的收费订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ParkCarOrderSaveReqVO createReqVO) throws JsonProcessingException, ParseException;

    /**
     * 更新车的收费订单
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid ParkCarOrderSaveReqVO updateReqVO);

    /**
     * 删除车的收费订单
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得车的收费订单
     *
     * @param id 编号
     * @return 车的收费订单
     */
    ParkCarOrderDO get(Long id);

    /**
     * 获得车的收费订单分页
     *
     * @param pageReqVO 分页查询
     * @return 车的收费订单分页
     */
    PageResult<ParkCarOrderDO> getPage(ParkCarOrderPageReqVO pageReqVO);

}