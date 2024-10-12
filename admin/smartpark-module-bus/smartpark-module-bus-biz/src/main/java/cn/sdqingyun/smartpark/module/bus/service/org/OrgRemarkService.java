package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgRemarkPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgRemarkSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgRemarkDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构业务备注 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgRemarkService {

    /**
     * 创建机构业务备注
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgRemarkSaveReqVO createReqVO);

    /**
     * 更新机构业务备注
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgRemarkSaveReqVO updateReqVO);

    /**
     * 删除机构业务备注
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构业务备注
     *
     * @param id 编号
     * @return 机构业务备注
     */
    OrgRemarkDO get(Long id);

    /**
     * 获得机构业务备注分页
     *
     * @param pageReqVO 分页查询
     * @return 机构业务备注分页
     */
    PageResult<OrgRemarkDO> getPage(OrgRemarkPageReqVO pageReqVO);

}