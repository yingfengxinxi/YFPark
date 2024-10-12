package cn.sdqingyun.smartpark.module.bus.controller.admin.invoice;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplateRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo.InvoiceTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.invoice.InvoiceTemplateDO;
import cn.sdqingyun.smartpark.module.bus.service.invoice.InvoiceTemplateService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 发票模板配置")
@RestController
@RequestMapping("/bus/invoiceTemplate/")
@Validated
public class InvoiceTemplateController {

    @Resource
    private InvoiceTemplateService Service;

    /**
     * {
     * "name": "发票模板名称",
     * "sellerId": "5",
     * "taxRule": "10",
     * "eInvoice":"0",
     * "orgAuto":"0",
     * "userAuto":"0",
     * "buildBind":"12",
     * "isApproval":"0",
     * "sptAppid":"",
     * "sptAppsecret":"",
     * "sptNumber":""
     * }
     *
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建发票模板配置")
    @PreAuthorize("@ss.hasPermission('bus:invoiceTemplate:create')")
    public CommonResult<Long> create(@Valid @RequestBody InvoiceTemplateSaveReqVO createReqVO) {

        Boolean checkName = Service.isCheckName(createReqVO.getName(), null);
        if (checkName) {
            throw new ServiceException(406, "该发票模板名称已存在");
        }

        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新发票模板配置")
    @PreAuthorize("@ss.hasPermission('bus:invoiceTemplate:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody InvoiceTemplateSaveReqVO updateReqVO) {
        Boolean checkName = Service.isCheckName(updateReqVO.getName(), updateReqVO.getId());
        if (checkName) {
            throw new ServiceException(406, "该发票模板名称已存在");
        }
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除发票模板配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:invoiceTemplate:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得发票模板配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:invoiceTemplate:query')")
    public CommonResult<InvoiceTemplateRespVO> get(@RequestParam("id") Long id) {
        InvoiceTemplateDO invoiceTemplateDO = Service.get(id);
        return success(BeanUtils.toBean(invoiceTemplateDO, InvoiceTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得发票模板配置分页")
    @PreAuthorize("@ss.hasPermission('bus:invoiceTemplate:query')")
    public CommonResult<PageResult<InvoiceTemplateRespVO>> getPage(@Valid InvoiceTemplatePageReqVO pageReqVO) {
        PageResult<InvoiceTemplateDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InvoiceTemplateRespVO.class));
    }


}