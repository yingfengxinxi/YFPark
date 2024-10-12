package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.util.MyX509TrustManagerUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplateRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptTemplateDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillReceiptTemplateService;
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

@Tag(name = "管理后台 - 收据模板")
@RestController
@RequestMapping("/bus/orgBillReceiptTemplate")
@Validated
public class OrgBillReceiptTemplateController {

    @Resource
    private OrgBillReceiptTemplateService Service;

    @PostMapping("/create")
    @Operation(summary = "创建收据模板")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptTemplate:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgBillReceiptTemplateSaveReqVO createReqVO) {
        Boolean checkName = Service.isCheckName(createReqVO.getName(), null);
        if (checkName) {
            throw new ServiceException(406, "模板名称已存在");
        }
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收据模板")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptTemplate:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillReceiptTemplateSaveReqVO updateReqVO) {
        Boolean checkName = Service.isCheckName(updateReqVO.getName(), updateReqVO.getId());
        if (checkName) {
            throw new ServiceException(406, "模板名称已存在");
        }
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收据模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptTemplate:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收据模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptTemplate:query')")
    public CommonResult<OrgBillReceiptTemplateRespVO> get(@RequestParam("id") Long id) {
        OrgBillReceiptTemplateDO receiptTemplateDO = Service.get(id);
        return success(BeanUtils.toBean(receiptTemplateDO, OrgBillReceiptTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收据模板分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptTemplate:query')")
    public CommonResult<PageResult<OrgBillReceiptTemplateRespVO>> getPage(@Valid OrgBillReceiptTemplatePageReqVO pageReqVO) {
        PageResult<OrgBillReceiptTemplateDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillReceiptTemplateRespVO.class));
    }

    /**
     * @param buildBind
     * @return
     */
    @GetMapping("/getByBuildsTemplateList")
    @Operation(summary = "获得收据模板下拉列表【生成收据时使用】")
    @Parameter(name = "buildBind", description = "楼栋id", required = true)
    public CommonResult<?> getByBuildsTemplateList(@RequestParam("buildBind") Long buildBind) {

        return success(Service.getByBuildsList(buildBind));
    }


}