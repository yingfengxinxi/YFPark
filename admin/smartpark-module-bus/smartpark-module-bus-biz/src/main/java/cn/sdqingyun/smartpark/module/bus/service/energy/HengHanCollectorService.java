package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanCollectorPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanCollectorRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HengHanCollectorSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HengHanCollectorDO;
import jakarta.validation.Valid;

/**
 * henghan_collector Service 接口
 *
 * @author 管理员
 */
public interface HengHanCollectorService {

    /**
     * 创建henghan_collector
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid HengHanCollectorSaveReqVO createReqVO);

    /**
     * 更新henghan_collector
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid HengHanCollectorSaveReqVO updateReqVO);

    /**
     * 删除henghan_collector
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得henghan_collector
     *
     * @param id 编号
     * @return henghan_collector
     */
    HengHanCollectorDO get(Long id);

    /**
     * 获得henghan_collector分页
     *
     * @param pageReqVO 分页查询
     * @return henghan_collector分页
     */
    PageResult<HengHanCollectorRespVO> getPage(HengHanCollectorPageReqVO pageReqVO);

}