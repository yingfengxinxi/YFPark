package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts;

import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo.OwnerContactsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts.OwnerContactsDO;
import cn.sdqingyun.smartpark.module.bus.service.ownerContacts.OwnerContactsService;
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
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 租客联系人")
@RestController
@RequestMapping("/bus/ownerContacts")
@Validated
public class OwnerContactsController {

    @Resource
    private OwnerContactsService contactsService;

    @PostMapping("/create")
    @Operation(summary = "创建租客联系人")
    @PreAuthorize("@ss.hasPermission('bus:ownerContacts:create')")
    public CommonResult<Long> createContacts(@Valid @RequestBody OwnerContactsSaveReqVO createReqVO) {
        return success(contactsService.createContacts(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租客联系人")
    @PreAuthorize("@ss.hasPermission('bus:ownerContacts:update')")
    public CommonResult<Boolean> updateContacts(@Valid @RequestBody OwnerContactsSaveReqVO updateReqVO) {
        contactsService.updateContacts(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租客联系人")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:ownerContacts:delete')")
    public CommonResult<Boolean> deleteContacts(@RequestParam("id") Long id) {
        contactsService.deleteContacts(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租客联系人")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:ownerContacts:query')")
    public CommonResult<OwnerContactsRespVO> getContacts(@RequestParam("id") Long id) {
        OwnerContactsDO contacts = contactsService.getContacts(id);
        return success(BeanUtils.toBean(contacts, OwnerContactsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租客联系人分页")
    @PreAuthorize("@ss.hasPermission('bus:ownerContacts:query')")
    public CommonResult<PageResult<OwnerContactsRespVO>> getContactsPage(@Valid OwnerContactsPageReqVO pageReqVO) {
        PageResult<OwnerContactsDO> pageResult = contactsService.getContactsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OwnerContactsRespVO.class));
    }

    @GetMapping("/export")
    @Operation(summary = "导出租客联系人 Excel")
    @PreAuthorize("@ss.hasPermission('bus:ownerContacts:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportContactsExcel(@Valid OwnerContactsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OwnerContactsDO> list = contactsService.getContactsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "租客联系人.xls", "数据", OwnerContactsRespVO.class,
                        BeanUtils.toBean(list, OwnerContactsRespVO.class));
    }

}