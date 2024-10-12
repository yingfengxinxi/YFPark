package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserFieldExtendPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserFieldExtendRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserFieldExtendSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserFieldExtendDO;
import cn.sdqingyun.smartpark.module.bus.service.village.UserFieldExtendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用户扩展信息自定义系统设置")
@RestController
@RequestMapping("/bus/user-field-extend")
@Validated
public class UserFieldExtendController {

    @Resource
    private UserFieldExtendService userFieldExtendService;

    @PostMapping("/create")
    @Operation(summary = "创建用户扩展信息自定义系统设置")
    @PreAuthorize("@ss.hasPermission('bus:user-field-extend:create')")
    public CommonResult<Long> createUserFieldExtend(@Valid @RequestBody UserFieldExtendSaveReqVO createReqVO) {
        return success(userFieldExtendService.createUserFieldExtend(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户扩展信息自定义系统设置")
    @PreAuthorize("@ss.hasPermission('bus:user-field-extend:update')")
    public CommonResult<Boolean> updateUserFieldExtend(@Valid @RequestBody UserFieldExtendSaveReqVO updateReqVO) {
        userFieldExtendService.updateUserFieldExtend(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户扩展信息自定义系统设置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:user-field-extend:delete')")
    public CommonResult<Boolean> deleteUserFieldExtend(@RequestParam("id") Long id) {
        userFieldExtendService.deleteUserFieldExtend(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户扩展信息自定义系统设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:user-field-extend:query')")
    public CommonResult<UserFieldExtendRespVO> getUserFieldExtend(@RequestParam("id") Long id) {
        UserFieldExtendDO userFieldExtend = userFieldExtendService.getUserFieldExtend(id);
        return success(BeanUtils.toBean(userFieldExtend, UserFieldExtendRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户扩展信息自定义系统设置分页")
    @PreAuthorize("@ss.hasPermission('bus:user-field-extend:query')")
    public CommonResult<PageResult<UserFieldExtendRespVO>> getUserFieldExtendPage(@Valid UserFieldExtendPageReqVO pageReqVO) {
        PageResult<UserFieldExtendDO> pageResult = userFieldExtendService.getUserFieldExtendPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserFieldExtendRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户扩展信息自定义系统设置 Excel")
    @PreAuthorize("@ss.hasPermission('bus:user-field-extend:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserFieldExtendExcel(@Valid UserFieldExtendPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserFieldExtendDO> list = userFieldExtendService.getUserFieldExtendPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户扩展信息自定义系统设置.xls", "数据", UserFieldExtendRespVO.class,
                        BeanUtils.toBean(list, UserFieldExtendRespVO.class));
    }

    @GetMapping("/getUserFieldExtendList")
    @Operation(summary = "获得用户扩展信息自定义系统设置列表")
    @PreAuthorize("@ss.hasPermission('bus:user-field-extend:query')")
    public CommonResult<List<UserFieldExtendRespVO>> getUserFieldExtendList(@Valid UserFieldExtendPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserFieldExtendDO> list = userFieldExtendService.getUserFieldExtendPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, UserFieldExtendRespVO.class));
    }
}