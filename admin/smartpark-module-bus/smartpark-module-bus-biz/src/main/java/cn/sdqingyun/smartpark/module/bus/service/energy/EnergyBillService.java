package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBillPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBillSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyBillDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义账单 Service 接口
 *
 * @author 管理员
 */
public interface EnergyBillService {

    /**
     * 创建自定义账单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyBillSaveReqVO createReqVO);

    /**
     * 更新自定义账单
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyBillSaveReqVO updateReqVO);

    /**
     * 删除自定义账单
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义账单
     *
     * @param id 编号
     * @return 自定义账单
     */
    EnergyBillDO get(Long id);

    /**
     * 获得自定义账单分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义账单分页
     */
    PageResult<EnergyBillDO> getPage(EnergyBillPageReqVO pageReqVO);

    /**
     *
     * @param energyIds
     */
    JSONObject check(String energyIds);

    /**
     *
     * @param energyIds
     * @param receivablePaymentTime
     * @return
     */
    JSONObject preview(String energyIds, String receivablePaymentTime);

}