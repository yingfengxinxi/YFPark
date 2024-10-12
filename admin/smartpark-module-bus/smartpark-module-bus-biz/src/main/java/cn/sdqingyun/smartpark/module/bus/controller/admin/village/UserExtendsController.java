package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserExtendsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserExtendsRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserExtendsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserExtendsDO;
import cn.sdqingyun.smartpark.module.bus.service.village.UserExtendsService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Map;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用户信息扩展")
@RestController
@RequestMapping("/bus/user-extends")
@Validated
public class UserExtendsController {

    @Resource
    private UserExtendsService userExtendsService;

    @PostMapping("/create")
    @Operation(summary = "创建用户信息扩展")
    @PreAuthorize("@ss.hasPermission('bus:user-extends:create')")
    public CommonResult<Long> createUserExtends(@Valid @RequestBody UserExtendsSaveReqVO createReqVO) {
        return success(userExtendsService.createUserExtends(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户信息扩展")
    @PreAuthorize("@ss.hasPermission('bus:user-extends:update')")
    public CommonResult<Boolean> updateUserExtends(@Valid @RequestBody UserExtendsSaveReqVO updateReqVO) {
        userExtendsService.updateUserExtends(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户信息扩展")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:user-extends:delete')")
    public CommonResult<Boolean> deleteUserExtends(@RequestParam("id") Long id) {
        userExtendsService.deleteUserExtends(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户信息扩展")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:user-extends:query')")
    public CommonResult<UserExtendsRespVO> getUserExtends(@RequestParam("id") Long id) {
        UserExtendsDO userExtends = userExtendsService.getUserExtends(id);
        return success(BeanUtils.toBean(userExtends, UserExtendsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户信息扩展分页")
    @PreAuthorize("@ss.hasPermission('bus:user-extends:query')")
    public CommonResult<PageResult<UserExtendsRespVO>> getUserExtendsPage(@Valid UserExtendsPageReqVO pageReqVO) {
        PageResult<UserExtendsDO> pageResult = userExtendsService.getUserExtendsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserExtendsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户信息扩展 Excel")
    @PreAuthorize("@ss.hasPermission('bus:user-extends:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserExtendsExcel(@Valid UserExtendsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserExtendsDO> list = userExtendsService.getUserExtendsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户信息扩展.xls", "数据", UserExtendsRespVO.class,
                        BeanUtils.toBean(list, UserExtendsRespVO.class));
    }

    @PostMapping("/getCountUserExtends")
    @Operation(summary = "获得用户信息扩展信息汇总")
    @PreAuthorize("@ss.hasPermission('bus:village-extends:query')")
    public CommonResult<Map<String,Object>> getCountVillageUser(@RequestBody UserExtendsSaveReqVO createReqVO) throws JsonProcessingException {
        Map<String,Object> villageUser = userExtendsService.getCountUserExtends(createReqVO);
        return success(villageUser);
    }

    @GetMapping("/getUserExtendsByUser")
    @Operation(summary = "通过villageUserId获得用户信息扩展")
    @Parameter(name = "villageUserId", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:user-extends:query')")
    public CommonResult<UserExtendsRespVO> getUserExtendsByUser(@RequestParam("villageUserId") Long villageUserId) {
        UserExtendsDO userExtends = userExtendsService.getUserExtendsByUser(villageUserId);
        return success(BeanUtils.toBean(userExtends, UserExtendsRespVO.class));
    }
}