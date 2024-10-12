package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyOprPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanEnergyOprSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HengHanEnergyOprDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 电表操作id关联表、回调用 Service 接口
 *
 * @author 管理员
 */
public interface HengHanEnergyOprService {

    /**
     * 创建电表操作id关联表、回调用
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid HengHanEnergyOprSaveReqVO createReqVO);

    /**
     * 更新电表操作id关联表、回调用
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid HengHanEnergyOprSaveReqVO updateReqVO);

    /**
     * 删除电表操作id关联表、回调用
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得电表操作id关联表、回调用
     *
     * @param id 编号
     * @return 电表操作id关联表、回调用
     */
    HengHanEnergyOprDO get(Long id);

    /**
     * 获得电表操作id关联表、回调用分页
     *
     * @param pageReqVO 分页查询
     * @return 电表操作id关联表、回调用分页
     */
    PageResult<HengHanEnergyOprDO> getPage(HengHanEnergyOprPageReqVO pageReqVO);

}