package cn.sdqingyun.smartpark.module.bus.service.ownerFiles;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerFiles.OwnerFilesDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 租客附件 Service 接口
 *
 * @author 智慧园区
 */
public interface OwnerFilesService {

    /**
     * 创建租客附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOwnerFiles(@Valid OwnerFilesSaveReqVO createReqVO);

    /**
     * 更新租客附件
     *
     * @param updateReqVO 更新信息
     */
    void updateOwnerFiles(@Valid OwnerFilesSaveReqVO updateReqVO);

    /**
     * 删除租客附件
     *
     * @param id 编号
     */
    void deleteOwnerFiles(Long id);

    /**
     * 获得租客附件
     *
     * @param id 编号
     * @return 租客附件
     */
    OwnerFilesDO getOwnerFiles(Long id);

    /**
     * 获得租客附件分页
     *
     * @param pageReqVO 分页查询
     * @return 租客附件分页
     */
    PageResult<OwnerFilesDO> getOwnerFilesPage(OwnerFilesPageReqVO pageReqVO);

}