package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPublicRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPublicRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPublicRecordDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 分摊记录 Service 接口
 *
 * @author 管理员
 */
public interface EnergyPublicRecordService {

    /**
     * 创建分摊记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyPublicRecordSaveReqVO createReqVO);

    /**
     * 更新分摊记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyPublicRecordSaveReqVO updateReqVO);

    /**
     * 删除分摊记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得分摊记录
     *
     * @param id 编号
     * @return 分摊记录
     */
    EnergyPublicRecordDO get(Long id);

    /**
     * 获得分摊记录分页
     *
     * @param pageReqVO 分页查询
     * @return 分摊记录分页
     */
    PageResult<EnergyPublicRecordDO> getPage(EnergyPublicRecordPageReqVO pageReqVO);

}