package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyForewarningValueSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyForewarningValueDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 误抄表预警 Service 接口
 *
 * @author 管理员
 */
public interface EnergyForewarningValueService {

    /**
     * 创建误抄表预警
     *
     * @param energyForewarningValueSaveReqVO 创建信息
     * @return 编号
     */
    Long save(@Valid EnergyForewarningValueSaveReqVO energyForewarningValueSaveReqVO);




    /**
     * 获得误抄表预警
     *
     * @param id 编号
     * @return 误抄表预警
     */
    EnergyForewarningValueDO get();



}