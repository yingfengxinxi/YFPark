package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolAdminPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolAdminSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolAdminDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 资产管理子应用管理员配置 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolAdminService {

    /**
     * 创建资产管理子应用管理员配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolAdminSaveReqVO createReqVO);

    /**
     * 更新资产管理子应用管理员配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolAdminSaveReqVO updateReqVO);

    /**
     * 删除资产管理子应用管理员配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得资产管理子应用管理员配置
     *
     * @param id 编号
     * @return 资产管理子应用管理员配置
     */
    PatrolAdminDO get(Long id);

    /**
     * 获得资产管理子应用管理员配置分页
     *
     * @param pageReqVO 分页查询
     * @return 资产管理子应用管理员配置分页
     */
    PageResult<PatrolAdminDO> getPage(PatrolAdminPageReqVO pageReqVO);

}