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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserIcCardDO;
import cn.sdqingyun.smartpark.module.bus.service.village.UserIcCardService;

@Tag(name = "管理后台 - 住户的IC卡")
@RestController
@RequestMapping("/bus/user-ic-card")
@Validated
public class UserIcCardController {

    @Resource
    private UserIcCardService userIcCardService;

    @PostMapping("/create")
    @Operation(summary = "创建住户的IC卡")
    @PreAuthorize("@ss.hasPermission('bus:user-ic-card:create')")
    public CommonResult<Long> createUserIcCard(@Valid @RequestBody UserIcCardSaveReqVO createReqVO) {
        return success(userIcCardService.createUserIcCard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新住户的IC卡")
    @PreAuthorize("@ss.hasPermission('bus:user-ic-card:update')")
    public CommonResult<Boolean> updateUserIcCard(@Valid @RequestBody UserIcCardSaveReqVO updateReqVO) {
        userIcCardService.updateUserIcCard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除住户的IC卡")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:user-ic-card:delete')")
    public CommonResult<Boolean> deleteUserIcCard(@RequestParam("id") Long id) {
        userIcCardService.deleteUserIcCard(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得住户的IC卡")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:user-ic-card:query')")
    public CommonResult<UserIcCardRespVO> getUserIcCard(@RequestParam("id") Long id) {
        UserIcCardDO userIcCard = userIcCardService.getUserIcCard(id);
        return success(BeanUtils.toBean(userIcCard, UserIcCardRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得住户的IC卡分页")
    @PreAuthorize("@ss.hasPermission('bus:user-ic-card:query')")
    public CommonResult<PageResult<UserIcCardRespVO>> getUserIcCardPage(@Valid UserIcCardPageReqVO pageReqVO) {
        PageResult<UserIcCardDO> pageResult = userIcCardService.getUserIcCardPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserIcCardRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出住户的IC卡 Excel")
    @PreAuthorize("@ss.hasPermission('bus:user-ic-card:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserIcCardExcel(@Valid UserIcCardPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserIcCardDO> list = userIcCardService.getUserIcCardPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "住户的IC卡.xls", "数据", UserIcCardRespVO.class,
                        BeanUtils.toBean(list, UserIcCardRespVO.class));
    }

}