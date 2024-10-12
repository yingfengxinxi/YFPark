package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillReceiptRuleMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RECEIPT_RULE_NOT_EXISTS;

/**
 * 账单收据编号规则 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillReceiptRuleServiceImpl implements OrgBillReceiptRuleService {

    @Resource
    private OrgBillReceiptRuleMapper Mapper;


    @Override
    public Long create(OrgBillReceiptRuleSaveReqVO createReqVO) {
        // 插入
        OrgBillReceiptRuleDO orgBillReceiptRuleDO = BeanUtils.toBean(createReqVO, OrgBillReceiptRuleDO.class);
        Mapper.insert(orgBillReceiptRuleDO);
        // 返回
        return orgBillReceiptRuleDO.getId();
    }

    @Override
    public void update(OrgBillReceiptRuleSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillReceiptRuleDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillReceiptRuleDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(RECEIPT_RULE_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillReceiptRuleDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillReceiptRuleDO> getPage(OrgBillReceiptRulePageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }

    @Override
    public Boolean isCheckName(String name, Long id) {

        LambdaQueryWrapperX<OrgBillReceiptRuleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillReceiptRuleDO::getName, name);
        if (id != null) {
            queryWrapperX.apply("id !='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isCheckBuild(String buildBind, Long id) {

        LambdaQueryWrapperX<OrgBillReceiptRuleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("REGEXP_LIKE(build_bind, '(^|,)" + buildBind + "($|,)')");
        if (id != null) {
            queryWrapperX.apply("id!=" + id + "");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

}