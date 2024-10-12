package cn.sdqingyun.smartpark.module.bus.service.energy;


import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBindPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBindSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyBindDO;
import jakarta.validation.Valid;

/**
 * 自定义关联分总 Service 接口
 *
 * @author 管理员
 */
public interface EnergyBindService {

    /**
     * 创建自定义关联分总
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyBindSaveReqVO createReqVO);

    /**
     * 更新自定义关联分总
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyBindSaveReqVO updateReqVO);

    /**
     * 删除自定义关联分总
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义关联分总
     *
     * @param id 编号
     * @return 自定义关联分总
     */
    EnergyBindDO get(Long id);

    /**
     * 获得自定义关联分总分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义关联分总分页
     */
    PageResult<EnergyBindDO> getPage(EnergyBindPageReqVO pageReqVO);

}