package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyNotCompleteTaskListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPlanPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPlanRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPlanSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPlanDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

import java.text.ParseException;

/**
 * 自定义抄表计划 Service 接口
 *
 * @author 管理员
 */
public interface EnergyPlanService {

    /**
     * 创建自定义抄表计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyPlanSaveReqVO createReqVO) throws Exception;

    /**
     * 更新自定义抄表计划
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyPlanSaveReqVO updateReqVO) throws Exception;

    /**
     * 删除自定义抄表计划
     *
     * @param id 编号
     */
    void delete(Long id) throws Exception;

    /**
     * 获得自定义抄表计划
     *
     * @param id 编号
     * @return 自定义抄表计划
     */
    EnergyPlanDO get(Long id);

    /**
     * 获得自定义抄表计划分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义抄表计划分页
     */
    PageResult<EnergyPlanRespVO> getPage(EnergyPlanPageReqVO pageReqVO);
}