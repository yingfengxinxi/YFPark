package cn.sdqingyun.smartpark.module.bus.service.invoice;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.invoice.InvoiceTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgSellerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxCodeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.invoice.InvoiceTemplateMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgSellerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgTaxCodeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgTaxRuleMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.INVOICE_TEMPLATE_NOT_EXISTS;

/**
 * 发票模板配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class InvoiceTemplateServiceImpl implements InvoiceTemplateService {

    @Resource
    private InvoiceTemplateMapper Mapper;

    @Resource
    private OrgSellerMapper orgSellerMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private OrgTaxRuleMapper orgTaxRuleMapper;

    @Resource
    private OrgTaxCodeMapper orgTaxCodeMapper;

    @Override
    public Long create(InvoiceTemplateSaveReqVO createReqVO) {
        // 插入
        InvoiceTemplateDO invoiceTemplateDO = BeanUtils.toBean(createReqVO, InvoiceTemplateDO.class);
        Mapper.insert(invoiceTemplateDO);
        // 返回
        return invoiceTemplateDO.getId();
    }

    @Override
    public void update(InvoiceTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        InvoiceTemplateDO updateObj = BeanUtils.toBean(updateReqVO, InvoiceTemplateDO.class);
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
            throw exception(INVOICE_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public InvoiceTemplateDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<InvoiceTemplateDO> getPage(InvoiceTemplatePageReqVO pageReqVO) {
        LambdaQueryWrapperX<InvoiceTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.orderByDesc(InvoiceTemplateDO::getCreateTime);
        PageResult<InvoiceTemplateDO> invoiceTemplateDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<InvoiceTemplateDO> list = invoiceTemplateDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(invoiceTemplateDO -> {
                //售房公司名称
                getCompanyName(invoiceTemplateDO);
                //楼宇
                getBuildName(invoiceTemplateDO);
            });
        }
        return invoiceTemplateDOPageResult;
    }


    private void getBuildName(InvoiceTemplateDO invoiceTemplateDO) {
        String[] split = invoiceTemplateDO.getBuildBind().split(",");
        StringBuilder sb = new StringBuilder();
        for (String buildBindId : split) {
            BuildDO buildDO = buildMapper.selectById(buildBindId);
            if (buildDO != null) {
                sb.append(buildDO.getBuildName()).append(",");
            }
        }
        String buildName = sb.toString();
        if (StringUtils.isNotEmpty(buildName)) {
            buildName = buildName.substring(0, buildName.length() - 1);
            invoiceTemplateDO.setBuildBind(buildName);
        }
    }

    private void getCompanyName(InvoiceTemplateDO invoiceTemplateDO) {
        Long sellerId = invoiceTemplateDO.getSellerId();
        OrgSellerDO orgSellerDO = orgSellerMapper.selectById(sellerId);
        if (orgSellerDO != null) {
            String companyName = orgSellerDO.getCompanyName();
            invoiceTemplateDO.setCompanyName(companyName);
        }
    }

    @Override
    public Boolean isCheckName(String name, Long id) {

        LambdaQueryWrapperX<InvoiceTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(InvoiceTemplateDO::getName, name);
        if (id != null) {
            queryWrapperX.apply("id !='" + id + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

}