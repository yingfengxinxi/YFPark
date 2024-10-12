package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractExpireRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractExpireRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractExpireRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractExpireRuleMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 合同到期提醒规则 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ContractExpireRuleServiceImpl implements ContractExpireRuleService {

    @Resource
    private ContractExpireRuleMapper expireRuleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createExpireRule(ContractExpireRuleSaveReqVO createReqVO) {
        // 插入
        ContractExpireRuleDO expireRule = BeanUtils.toBean(createReqVO, ContractExpireRuleDO.class);
        extracted(expireRule);

        expireRuleMapper.insert(expireRule);
        // 返回
        return expireRule.getId();
    }

    private void extracted(ContractExpireRuleDO expireRule) {
        Long tenantId = TenantContextHolder.getTenantId();
        //校验规则名称是否重复
        int ruleNameCount = expireRuleMapper.getTenantIdExpireRuleCount(tenantId, expireRule.getRuleName(), "", expireRule.getId());
        if (ruleNameCount >= 1) {
            throw exception(EXPIRE_RULE_RELATION_NAME_NOT_EXISTS);
        }
        String relationBuilds = expireRule.getRelationBuilds();
        String[] relationBuildsArray = relationBuilds.split(",");
        for (String relationBuild : relationBuildsArray) {
            int size = expireRuleMapper.getTenantIdExpireRuleCount(tenantId, "", relationBuild, expireRule.getId());
            if (size >= 1) {
                throw exception(EXPIRE_RULE_RELATION_BUILD_NOT_EXISTS);
            }
        }
    }

    @Override
    public void updateExpireRule(ContractExpireRuleSaveReqVO updateReqVO) {
        // 校验存在
        validateExpireRuleExists(updateReqVO.getId());
        // 更新
        ContractExpireRuleDO updateObj = BeanUtils.toBean(updateReqVO, ContractExpireRuleDO.class);
        extracted(updateObj);
        expireRuleMapper.updateById(updateObj);
    }

    @Override
    public void deleteExpireRule(Long id) {
        // 校验存在
        validateExpireRuleExists(id);
        // 删除
        expireRuleMapper.deleteById(id);
    }

    private void validateExpireRuleExists(Long id) {
        if (expireRuleMapper.selectById(id) == null) {
            throw exception(EXPIRE_RULE_NOT_EXISTS);
        }
    }

    @Override
    public ContractExpireRuleDO getExpireRule(Long id) {
        return expireRuleMapper.selectById(id);
    }

    @Override
    public PageResult<ContractExpireRuleDO> getExpireRulePage(ContractExpireRulePageReqVO pageReqVO) {
        LambdaQueryWrapperX<ContractExpireRuleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.orderByDesc(ContractExpireRuleDO::getCreateTime);
        return expireRuleMapper.selectPage(pageReqVO, queryWrapperX);
    }

    @Override
    public List<ContractExpireRuleDO> getExpireRuleList() {
        LambdaQueryWrapperX<ContractExpireRuleDO> queryWrapperX = new LambdaQueryWrapperX();
        queryWrapperX.apply("status !='0'");
        return expireRuleMapper.selectList(queryWrapperX);
    }

}