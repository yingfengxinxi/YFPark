package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptTemplateDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 收据模板 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillReceiptTemplateService {

    /**
     * 创建收据模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillReceiptTemplateSaveReqVO createReqVO);

    /**
     * 更新收据模板
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillReceiptTemplateSaveReqVO updateReqVO);

    /**
     * 删除收据模板
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得收据模板
     *
     * @param id 编号
     * @return 收据模板
     */
    OrgBillReceiptTemplateDO get(Long id);

    /**
     * 获得收据模板分页
     *
     * @param pageReqVO 分页查询
     * @return 收据模板分页
     */
    PageResult<OrgBillReceiptTemplateDO> getPage(OrgBillReceiptTemplatePageReqVO pageReqVO);


    /**
     *
     * @param name
     * @param id
     * @return
     */
    Boolean isCheckName(String name,Long id);



    /**
     * @param buildBind
     * @return
     */
    List<OrgBillReceiptTemplateDO> getByBuildsList(Long buildBind);
}