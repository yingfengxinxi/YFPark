package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyUnitPricePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyUnitPriceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyUnitPriceDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义价格标准表-阶梯单价 Service 接口
 *
 * @author 管理员
 */
public interface EnergyUnitPriceService {

    /**
     * 创建自定义价格标准表-阶梯单价
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyUnitPriceSaveReqVO createReqVO);

    /**
     * 更新自定义价格标准表-阶梯单价
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyUnitPriceSaveReqVO updateReqVO);

    /**
     * 删除自定义价格标准表-阶梯单价
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义价格标准表-阶梯单价
     *
     * @param id 编号
     * @return 自定义价格标准表-阶梯单价
     */
    EnergyUnitPriceDO get(Long id);



}