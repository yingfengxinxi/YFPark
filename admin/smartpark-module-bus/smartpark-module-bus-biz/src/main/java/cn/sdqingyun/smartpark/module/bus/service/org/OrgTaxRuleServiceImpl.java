package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxCodeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgTaxCodeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgTaxRuleMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.TAX_RULE_NOT_EXISTS;

/**
 * 税率规则配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgTaxRuleServiceImpl implements OrgTaxRuleService {

    @Resource
    private OrgTaxRuleMapper Mapper;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Resource
    private OrgTaxCodeMapper orgTaxCodeMapper;

    @Override
    public Long create(OrgTaxRuleSaveReqVO createReqVO) {
        // 插入
        OrgTaxRuleDO orgTaxRuleDO = BeanUtils.toBean(createReqVO, OrgTaxRuleDO.class);
        Mapper.insert(orgTaxRuleDO);
        // 返回
        return orgTaxRuleDO.getId();
    }

    @Override
    public void update(OrgTaxRuleSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgTaxRuleDO updateObj = BeanUtils.toBean(updateReqVO, OrgTaxRuleDO.class);
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
            throw exception(TAX_RULE_NOT_EXISTS);
        }
    }

    @Override
    public OrgTaxRuleDO get(Long id) {
        return Mapper.selectById(id);
    }

    /**
     *
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<OrgTaxRuleDO> getPage(OrgTaxRulePageReqVO pageReqVO) {
        LambdaQueryWrapper<OrgTaxRuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(OrgTaxRuleDO::getCreateTime);
        PageResult<OrgTaxRuleDO> orgTaxRuleDOPageResult = Mapper.selectPage(pageReqVO, queryWrapper);
        List<OrgTaxRuleDO> list = orgTaxRuleDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(orgTaxRuleDO -> {
                OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(orgTaxRuleDO.getCostType());
                if (orgBillCostTypeDO != null) {
                    orgTaxRuleDO.setCostType(orgBillCostTypeDO.getName());
                }


                OrgTaxCodeDO orgTaxCodeDO = orgTaxCodeMapper.selectById(orgTaxRuleDO.getTaxCodeId());
                if (orgTaxCodeDO != null) {
                    orgTaxRuleDO.setTaxCodeName(orgTaxCodeDO.getName());
                }

            });
        }
        return orgTaxRuleDOPageResult;
    }

    /**
     * @param costType
     * @param id
     * @return
     */
    @Override
    public Boolean isCheckCostType(String costType, Long id) {
        LambdaQueryWrapperX<OrgTaxRuleDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgTaxRuleDO::getCostType, costType);
        if (id != null) {
            queryWrapperX.apply(" id!='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<OrgTaxRuleDO> getAllTaxRuleList() {
        return Mapper.getAllTaxRuleList();
    }
}