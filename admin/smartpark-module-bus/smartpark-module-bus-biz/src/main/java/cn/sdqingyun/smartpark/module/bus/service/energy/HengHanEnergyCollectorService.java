package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyCollectorPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyCollectorSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HengHanEnergyCollectorDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 采集器电表关联表（一对多的关系） Service 接口
 *
 * @author 管理员
 */
public interface HengHanEnergyCollectorService {

    /**
     * 创建采集器电表关联表（一对多的关系）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid HengHanEnergyCollectorSaveReqVO createReqVO);

    /**
     * 更新采集器电表关联表（一对多的关系）
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid HengHanEnergyCollectorSaveReqVO updateReqVO);

    /**
     * 删除采集器电表关联表（一对多的关系）
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得采集器电表关联表（一对多的关系）
     *
     * @param id 编号
     * @return 采集器电表关联表（一对多的关系）
     */
    HengHanEnergyCollectorDO get(Long id);

    /**
     * 获得采集器电表关联表（一对多的关系）分页
     *
     * @param pageReqVO 分页查询
     * @return 采集器电表关联表（一对多的关系）分页
     */
    PageResult<HengHanEnergyCollectorDO> getPage(HengHanEnergyCollectorPageReqVO pageReqVO);

}