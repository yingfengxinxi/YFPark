package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyCallbackRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyCallbackRecordRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyCallbackRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyCallbackRecordDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 终端数据推送记录 Service 接口
 *
 * @author 管理员
 */
public interface EnergyCallbackRecordService {

    /**
     * 创建终端数据推送记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyCallbackRecordSaveReqVO createReqVO);

    /**
     * 更新终端数据推送记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyCallbackRecordSaveReqVO updateReqVO);

    /**
     * 删除终端数据推送记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得终端数据推送记录
     *
     * @param id 编号
     * @return 终端数据推送记录
     */
    EnergyCallbackRecordDO get(Long id);

    /**
     * 获得终端数据推送记录分页
     *
     * @param pageReqVO 分页查询
     * @return 终端数据推送记录分页
     */
    PageResult<EnergyCallbackRecordRespVO> getPage(EnergyCallbackRecordPageReqVO pageReqVO);

    /**
     *
     * @param energyId
     */
    void read(Long energyId);

    /**
     *
     * @param id
     */
    void voidReadRecord(Long id);


}