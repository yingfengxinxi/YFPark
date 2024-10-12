package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxRuleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxRuleDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgTaxRuleService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;

import java.util.*;
import java.io.IOException;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 发票设置->相关设置->税率规则配置")
@RestController
@RequestMapping("/bus/orgTaxRule")
@Validated
public class OrgTaxRuleController {

    @Resource
    private OrgTaxRuleService Service;

    @PostMapping("/create")
    @Operation(summary = "创建税率规则配置")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxRule:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgTaxRuleSaveReqVO createReqVO) {
        createReqVO.setStatus("1");
        Boolean checkCostType = Service.isCheckCostType(createReqVO.getCostType(), null);
        if (checkCostType) {
            throw new ServiceException(406, "该费用类型税率已添加,请勿重复操作");
        }
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新税率规则配置")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxRule:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgTaxRuleSaveReqVO updateReqVO) {
        Boolean checkCostType = Service.isCheckCostType(updateReqVO.getCostType(), updateReqVO.getId());
        if (checkCostType) {
            throw new ServiceException(406, "该费用类型税率已添加,请勿重复操作");
        }
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除税率规则配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgTaxRule:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得税率规则配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxRule:query')")
    public CommonResult<OrgTaxRuleRespVO> get(@RequestParam("id") Long id) {
        OrgTaxRuleDO orgTaxRuleDO = Service.get(id);
        return success(BeanUtils.toBean(orgTaxRuleDO, OrgTaxRuleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得税率规则配置分页")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxRule:query')")
    public CommonResult<PageResult<OrgTaxRuleRespVO>> getPage(@Valid OrgTaxRulePageReqVO pageReqVO) {
        PageResult<OrgTaxRuleDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgTaxRuleRespVO.class));
    }


    @GetMapping("/getList")
    @Operation(summary = "获得税率规则配置下拉")
    public CommonResult<List<OrgTaxRuleDO>> getList() {

        List<OrgTaxRuleDO> allTaxRuleList = Service.getAllTaxRuleList();
        return success(allTaxRuleList);
    }

}