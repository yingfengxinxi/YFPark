package cn.sdqingyun.smartpark.module.bus.service.ownerRemarks;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerRemarks.OwnerRemarksDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 租客备注信息 Service 接口
 *
 * @author 智慧园区
 */
public interface OwnerRemarksService {

    /**
     * 创建租客备注信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOwnerRemarks(@Valid OwnerRemarksSaveReqVO createReqVO);

    /**
     * 更新租客备注信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOwnerRemarks(@Valid OwnerRemarksSaveReqVO updateReqVO);

    /**
     * 删除租客备注信息
     *
     * @param id 编号
     */
    void deleteOwnerRemarks(Long id);

    /**
     * 获得租客备注信息
     *
     * @param id 编号
     * @return 租客备注信息
     */
    OwnerRemarksDO getOwnerRemarks(Long id);

    /**
     * 获得租客备注信息分页
     *
     * @param pageReqVO 分页查询
     * @return 租客备注信息分页
     */
    PageResult<OwnerRemarksDO> getOwnerRemarksPage(OwnerRemarksPageReqVO pageReqVO);

}