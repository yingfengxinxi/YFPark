package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomPricePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomPriceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRoomPriceDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 房间对应自定义价格 Service 接口
 *
 * @author 管理员
 */
public interface EnergyRoomPriceService {

    /**
     * 创建房间对应自定义价格
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyRoomPriceSaveReqVO createReqVO);

    /**
     * 更新房间对应自定义价格
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyRoomPriceSaveReqVO updateReqVO);

    /**
     * 删除房间对应自定义价格
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得房间对应自定义价格
     *
     * @param id 编号
     * @return 房间对应自定义价格
     */
    EnergyRoomPriceDO get(Long id);

    /**
     * 获得房间对应自定义价格分页
     *
     * @param pageReqVO 分页查询
     * @return 房间对应自定义价格分页
     */
    PageResult<EnergyRoomPriceDO> getPage(EnergyRoomPricePageReqVO pageReqVO);

}