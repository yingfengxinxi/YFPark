package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPricePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPriceRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPriceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPriceDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义价格标准 Service 接口
 *
 * @author 管理员
 */
public interface EnergyPriceService {

    /**
     * 创建自定义价格标准
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyPriceSaveReqVO createReqVO);

    /**
     * 更新自定义价格标准
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyPriceSaveReqVO updateReqVO);

    /**
     * 删除自定义价格标准
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义价格标准
     *
     * @param id 编号
     * @return 自定义价格标准
     */
    EnergyPriceDO get(Long id);

    /**
     * 获得自定义价格标准分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义价格标准分页
     */
    PageResult<EnergyPriceRespVO> getPage(EnergyPricePageReqVO pageReqVO);

}