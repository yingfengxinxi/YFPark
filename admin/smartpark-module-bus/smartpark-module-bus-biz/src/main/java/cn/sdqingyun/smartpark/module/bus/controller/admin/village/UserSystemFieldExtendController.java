package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserSystemFieldExtendDO;
import cn.sdqingyun.smartpark.module.bus.service.village.UserSystemFieldExtendService;

@Tag(name = "管理后台 - 用户扩展信息自定义系统设置")
@RestController
@RequestMapping("/bus/user-system-field-extend")
@Validated
public class UserSystemFieldExtendController {

    @Resource
    private UserSystemFieldExtendService userSystemFieldExtendService;

    @PostMapping("/create")
    @Operation(summary = "创建用户扩展信息自定义系统设置")
    @PreAuthorize("@ss.hasPermission('bus:user-system-field-extend:create')")
    public CommonResult<Long> createUserSystemFieldExtend(@Valid @RequestBody UserSystemFieldExtendSaveReqVO createReqVO) {
        return success(userSystemFieldExtendService.createUserSystemFieldExtend(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户扩展信息自定义系统设置")
    @PreAuthorize("@ss.hasPermission('bus:user-system-field-extend:update')")
    public CommonResult<Boolean> updateUserSystemFieldExtend(@Valid @RequestBody UserSystemFieldExtendSaveReqVO updateReqVO) {
        userSystemFieldExtendService.updateUserSystemFieldExtend(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户扩展信息自定义系统设置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:user-system-field-extend:delete')")
    public CommonResult<Boolean> deleteUserSystemFieldExtend(@RequestParam("id") Long id) {
        userSystemFieldExtendService.deleteUserSystemFieldExtend(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户扩展信息自定义系统设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:user-system-field-extend:query')")
    public CommonResult<UserSystemFieldExtendRespVO> getUserSystemFieldExtend(@RequestParam("id") Long id) {
        UserSystemFieldExtendDO userSystemFieldExtend = userSystemFieldExtendService.getUserSystemFieldExtend(id);
        return success(BeanUtils.toBean(userSystemFieldExtend, UserSystemFieldExtendRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户扩展信息自定义系统设置分页")
    @PreAuthorize("@ss.hasPermission('bus:user-system-field-extend:query')")
    public CommonResult<PageResult<UserSystemFieldExtendRespVO>> getUserSystemFieldExtendPage(@Valid UserSystemFieldExtendPageReqVO pageReqVO) {
        PageResult<UserSystemFieldExtendDO> pageResult = userSystemFieldExtendService.getUserSystemFieldExtendPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserSystemFieldExtendRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户扩展信息自定义系统设置 Excel")
    @PreAuthorize("@ss.hasPermission('bus:user-system-field-extend:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserSystemFieldExtendExcel(@Valid UserSystemFieldExtendPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserSystemFieldExtendDO> list = userSystemFieldExtendService.getUserSystemFieldExtendPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户扩展信息自定义系统设置.xls", "数据", UserSystemFieldExtendRespVO.class,
                        BeanUtils.toBean(list, UserSystemFieldExtendRespVO.class));
    }

}