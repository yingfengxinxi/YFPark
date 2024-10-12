package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.BusOrgDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface BusOrgService {

    /**
     * 创建机构
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrg(@Valid BusOrgSaveReqVO createReqVO);

    /**
     * 更新机构
     *
     * @param updateReqVO 更新信息
     */
    void updateOrg(@Valid BusOrgSaveReqVO updateReqVO);

    /**
     * 删除机构
     *
     * @param id 编号
     */
    void deleteOrg(Long id);

    /**
     * 获得机构
     *
     * @param id 编号
     * @return 机构
     */
    BusOrgDO getOrg(Long id);

    /**
     * 获得机构分页
     *
     * @param pageReqVO 分页查询
     * @return 机构分页
     */
    PageResult<BusOrgDO> getOrgPage(BusOrgPageReqVO pageReqVO);

    /**
     * 根据租户code获得机构
     *
     * @return 机构
     */
    BusOrgDO getOrgByCode();
}