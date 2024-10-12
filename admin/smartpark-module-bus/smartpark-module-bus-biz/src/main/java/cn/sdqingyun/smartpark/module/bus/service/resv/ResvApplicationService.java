package cn.sdqingyun.smartpark.module.bus.service.resv;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvApplicationDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 预约应用 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvApplicationService {

    /**
     * 创建预约应用
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvApplication(@Valid ResvApplicationSaveReqVO createReqVO);

    /**
     * 更新预约应用
     *
     * @param updateReqVO 更新信息
     */
    void updateResvApplication(@Valid ResvApplicationSaveReqVO updateReqVO);

    /**
     * 删除预约应用
     *
     * @param id 编号
     */
    void deleteResvApplication(Long id);

    /**
     * 获得预约应用
     *
     * @param id 编号
     * @return 预约应用
     */
    ResvApplicationDO getResvApplication(Long id);

    /**
     * 获得预约应用分页
     *
     * @param pageReqVO 分页查询
     * @return 预约应用分页
     */
    PageResult<ResvApplicationDO> getResvApplicationPage(ResvApplicationPageReqVO pageReqVO);

}