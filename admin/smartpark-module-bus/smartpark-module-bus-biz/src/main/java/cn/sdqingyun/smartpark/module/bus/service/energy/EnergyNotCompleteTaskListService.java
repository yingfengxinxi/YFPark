package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyNotCompleteTaskListVO;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/10/8 8:42
 */
public interface EnergyNotCompleteTaskListService {


    /**
     *
     * @param planId
     * @return
     */
    List<EnergyNotCompleteTaskListVO> notCompleteTaskList(Long planId);
}
