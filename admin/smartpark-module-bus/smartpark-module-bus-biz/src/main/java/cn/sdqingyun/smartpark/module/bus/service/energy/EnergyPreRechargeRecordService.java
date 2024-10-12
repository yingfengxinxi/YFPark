package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPreRechargeRecordListPageVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPreRechargeRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPreRechargeRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPreRechargeRecordDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 水电表预充值记录 Service 接口
 *
 * @author 管理员
 */
public interface EnergyPreRechargeRecordService {

    /**
     * 创建水电表预充值记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyPreRechargeRecordSaveReqVO createReqVO);

    /**
     * 更新水电表预充值记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyPreRechargeRecordSaveReqVO updateReqVO);

    /**
     * 删除水电表预充值记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得水电表预充值记录
     *
     * @param id 编号
     * @return 水电表预充值记录
     */
    EnergyPreRechargeRecordDO get(Long id);

    /**
     * 获得水电表预充值记录分页
     *
     * @param pageReqVO 分页查询
     * @return 水电表预充值记录分页
     */
    PageResult<EnergyPreRechargeRecordListPageVO> getPage(EnergyPreRechargeRecordListPageVO pageReqVO);

}