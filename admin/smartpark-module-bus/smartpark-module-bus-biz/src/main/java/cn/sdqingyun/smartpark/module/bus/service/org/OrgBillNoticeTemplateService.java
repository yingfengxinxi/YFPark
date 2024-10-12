package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 账单缴费通知单模板 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillNoticeTemplateService {

    /**
     * 创建账单缴费通知单模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillNoticeTemplateSaveReqVO createReqVO);

    /**
     * 更新账单缴费通知单模板
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillNoticeTemplateSaveReqVO updateReqVO);

    /**
     * 删除账单缴费通知单模板
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得账单缴费通知单模板
     *
     * @param id 编号
     * @return 账单缴费通知单模板
     */
    OrgBillNoticeTemplateDO get(Long id);

    /**
     * 获得账单缴费通知单模板分页
     *
     * @param pageReqVO 分页查询
     * @return 账单缴费通知单模板分页
     */
    PageResult<OrgBillNoticeTemplateDO> getPage(OrgBillNoticeTemplatePageReqVO pageReqVO);

    /**
     *
     * @param name
     * @param id
     * @return
     */
    Boolean isCheckName(String name,Long id);

    /**
     *
     * @param buildBind
     * @param id
     * @return
     */
    Boolean isCheckBuild(String buildBind,Long id);
}