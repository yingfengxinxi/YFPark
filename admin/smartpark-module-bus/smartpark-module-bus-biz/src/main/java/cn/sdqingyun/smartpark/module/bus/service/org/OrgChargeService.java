package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgChargePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgChargeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgChargeDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 收费标准 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgChargeService {

    /**
     * 创建收费标准
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgChargeSaveReqVO createReqVO);

    /**
     * 更新收费标准
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgChargeSaveReqVO updateReqVO);

    /**
     * 删除收费标准
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得收费标准
     *
     * @param id 编号
     * @return 收费标准
     */
    OrgChargeDO get(Long id);

    /**
     * 获得收费标准分页
     *
     * @param pageReqVO 分页查询
     * @return 收费标准分页
     */
    PageResult<OrgChargeDO> getPage(OrgChargePageReqVO pageReqVO);

}