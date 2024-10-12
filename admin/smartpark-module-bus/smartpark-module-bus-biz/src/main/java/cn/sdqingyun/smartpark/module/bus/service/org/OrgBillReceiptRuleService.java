package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptRuleDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 账单收据编号规则 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillReceiptRuleService {

    /**
     * 创建账单收据编号规则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillReceiptRuleSaveReqVO createReqVO);

    /**
     * 更新账单收据编号规则
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillReceiptRuleSaveReqVO updateReqVO);

    /**
     * 删除账单收据编号规则
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得账单收据编号规则
     *
     * @param id 编号
     * @return 账单收据编号规则
     */
    OrgBillReceiptRuleDO get(Long id);

    /**
     * 获得账单收据编号规则分页
     *
     * @param pageReqVO 分页查询
     * @return 账单收据编号规则分页
     */
    PageResult<OrgBillReceiptRuleDO> getPage(OrgBillReceiptRulePageReqVO pageReqVO);

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