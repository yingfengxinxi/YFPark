package cn.sdqingyun.smartpark.module.bus.service.invoice;

import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.invoice.InvoiceTemplateDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 发票模板配置 Service 接口
 *
 * @author 智慧园区
 */
public interface InvoiceTemplateService {

    /**
     * 创建发票模板配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid InvoiceTemplateSaveReqVO createReqVO);

    /**
     * 更新发票模板配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid InvoiceTemplateSaveReqVO updateReqVO);

    /**
     * 删除发票模板配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得发票模板配置
     *
     * @param id 编号
     * @return 发票模板配置
     */
    InvoiceTemplateDO get(Long id);

    /**
     * 获得发票模板配置分页
     *
     * @param pageReqVO 分页查询
     * @return 发票模板配置分页
     */
    PageResult<InvoiceTemplateDO> getPage(InvoiceTemplatePageReqVO pageReqVO);


    /**
     *
     * @param name
     * @param id
     * @return
     */
    Boolean isCheckName(String name,Long id);
}