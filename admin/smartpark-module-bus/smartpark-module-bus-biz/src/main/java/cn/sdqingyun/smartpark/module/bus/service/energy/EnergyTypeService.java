package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTypePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTypeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTypeDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 表种类管理 Service 接口
 *
 * @author 管理员
 */
public interface EnergyTypeService {

    /**
     * 创建表种类管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyTypeSaveReqVO createReqVO);

    /**
     * 更新表种类管理
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyTypeSaveReqVO updateReqVO);


    /**
     *
     * @param updateReqVO
     */
    void lockout(EnergyTypeSaveReqVO updateReqVO);

    /**
     * 删除表种类管理
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得表种类管理
     *
     * @param id 编号
     * @return 表种类管理
     */
    EnergyTypeDO get(Long id);

    /**
     * 获得表种类管理分页
     *
     * @param pageReqVO 分页查询
     * @return 表种类管理分页
     */
    PageResult<EnergyTypeDO> getPage(EnergyTypePageReqVO pageReqVO);

}