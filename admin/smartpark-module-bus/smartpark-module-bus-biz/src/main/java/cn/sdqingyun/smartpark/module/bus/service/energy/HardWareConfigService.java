package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HardWareConfigPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HardWareConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HardWareConfigDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 智能硬件配置 Service 接口
 *
 * @author 管理员
 */
public interface HardWareConfigService {

    /**
     * 创建智能硬件配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid HardWareConfigSaveReqVO createReqVO);

    /**
     * 更新智能硬件配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid HardWareConfigSaveReqVO updateReqVO);

    /**
     * 删除智能硬件配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得智能硬件配置
     *
     * @param id 编号
     * @return 智能硬件配置
     */
    HardWareConfigDO get(Long id);

    /**
     * 获得智能硬件配置分页
     *
     * @param pageReqVO 分页查询
     * @return 智能硬件配置分页
     */
    PageResult<HardWareConfigDO> getPage(HardWareConfigPageReqVO pageReqVO);

}