package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillagePhonePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillagePhoneRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillagePhoneSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillagePhoneDO;
import cn.sdqingyun.smartpark.module.bus.service.village.VillagePhoneService;
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

@Tag(name = "管理后台 - 项目电话")
@RestController
@RequestMapping("/bus/village-phone")
@Validated
public class VillagePhoneController {

    @Resource
    private VillagePhoneService villagePhoneService;

    @PostMapping("/create")
    @Operation(summary = "创建项目电话")
    @PreAuthorize("@ss.hasPermission('bus:village-phone:create')")
    public CommonResult<Long> createVillagePhone(@Valid @RequestBody VillagePhoneSaveReqVO createReqVO) {
        return success(villagePhoneService.createVillagePhone(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目电话")
    @PreAuthorize("@ss.hasPermission('bus:village-phone:update')")
    public CommonResult<Boolean> updateVillagePhone(@Valid @RequestBody VillagePhoneSaveReqVO updateReqVO) {
        villagePhoneService.updateVillagePhone(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目电话")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:village-phone:delete')")
    public CommonResult<Boolean> deleteVillagePhone(@RequestParam("id") Long id) {
        villagePhoneService.deleteVillagePhone(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目电话")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village-phone:query')")
    public CommonResult<VillagePhoneRespVO> getVillagePhone(@RequestParam("id") Long id) {
        VillagePhoneDO villagePhone = villagePhoneService.getVillagePhone(id);
        return success(BeanUtils.toBean(villagePhone, VillagePhoneRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目电话分页")
    @PreAuthorize("@ss.hasPermission('bus:village-phone:query')")
    public CommonResult<PageResult<VillagePhoneRespVO>> getVillagePhonePage(@Valid VillagePhonePageReqVO pageReqVO) {
        PageResult<VillagePhoneDO> pageResult = villagePhoneService.getVillagePhonePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VillagePhoneRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目电话 Excel")
    @PreAuthorize("@ss.hasPermission('bus:village-phone:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVillagePhoneExcel(@Valid VillagePhonePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillagePhoneDO> list = villagePhoneService.getVillagePhonePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目电话.xls", "数据", VillagePhoneRespVO.class,
                        BeanUtils.toBean(list, VillagePhoneRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得项目电话列表")
    @PreAuthorize("@ss.hasPermission('bus:village-phone:query')")
    public CommonResult<List<VillagePhoneRespVO>> getVillagePhoneList(@Valid VillagePhonePageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillagePhoneDO> list = villagePhoneService.getVillagePhonePage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, VillagePhoneRespVO.class));
    }
}