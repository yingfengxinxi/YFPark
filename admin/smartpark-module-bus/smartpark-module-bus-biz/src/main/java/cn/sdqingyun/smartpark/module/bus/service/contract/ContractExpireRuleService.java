package cn.sdqingyun.smartpark.module.bus.service.contract;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractExpireRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractExpireRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractExpireRuleDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

/**
 * 合同到期提醒规则 Service 接口
 *
 * @author 智慧园区
 */
public interface ContractExpireRuleService {

    /**
     * 创建合同到期提醒规则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createExpireRule(@Valid ContractExpireRuleSaveReqVO createReqVO);

    /**
     * 更新合同到期提醒规则
     *
     * @param updateReqVO 更新信息
     */
    void updateExpireRule(@Valid ContractExpireRuleSaveReqVO updateReqVO);

    /**
     * 删除合同到期提醒规则
     *
     * @param id 编号
     */
    void deleteExpireRule(Long id);

    /**
     * 获得合同到期提醒规则
     *
     * @param id 编号
     * @return 合同到期提醒规则
     */
    ContractExpireRuleDO getExpireRule(Long id);

    /**
     * 获得合同到期提醒规则分页
     *
     * @param pageReqVO 分页查询
     * @return 合同到期提醒规则分页
     */
    PageResult<ContractExpireRuleDO> getExpireRulePage(ContractExpireRulePageReqVO pageReqVO);

    /**
     *
     * @return
     */
    List<ContractExpireRuleDO> getExpireRuleList();

}