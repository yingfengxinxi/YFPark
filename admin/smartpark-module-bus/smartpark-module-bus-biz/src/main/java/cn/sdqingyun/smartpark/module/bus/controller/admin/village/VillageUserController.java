package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.VillageUserImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageUserDO;
import cn.sdqingyun.smartpark.module.bus.service.village.VillageUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 项目用户/租客")
@RestController
@RequestMapping("/bus/village-user")
@Validated
public class VillageUserController {

    @Resource
    private VillageUserService villageUserService;

    @PostMapping("/create")
    @Operation(summary = "创建项目用户/租客")
    @PreAuthorize("@ss.hasPermission('bus:village-user:create')")
    public CommonResult<Long> createVillageUser(@Valid @RequestBody VillageUserSaveReqVO createReqVO) {
        return success(villageUserService.createVillageUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目用户/租客")
    @PreAuthorize("@ss.hasPermission('bus:village-user:update')")
    public CommonResult<Boolean> updateVillageUser(@Valid @RequestBody VillageUserSaveReqVO updateReqVO) {
        villageUserService.updateVillageUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目用户/租客")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:village-user:delete')")
    public CommonResult<Boolean> deleteVillageUser(@RequestParam("id") Long id) {
        villageUserService.deleteVillageUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目用户/租客")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village-user:query')")
    public CommonResult<VillageUserRespVO> getVillageUser(@RequestParam("id") Long id) {
        VillageUserDO villageUser = villageUserService.getVillageUser(id);
        return success(BeanUtils.toBean(villageUser, VillageUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目用户/租客分页")
    @PreAuthorize("@ss.hasPermission('bus:village-user:query')")
    public CommonResult<PageResult<VillageUserRespVO>> getVillageUserPage(@Valid VillageUserPageReqVO pageReqVO) {
        return success(villageUserService.getVillageUserPageVO(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目用户/租客 Excel")
    @PreAuthorize("@ss.hasPermission('bus:village-user:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVillageUserExcel(@Valid VillageUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageUserDO> list = villageUserService.getVillageUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目用户/租客.xls", "数据", VillageUserRespVO.class,
                        BeanUtils.toBean(list, VillageUserRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入租客列表模板")
    @ApiAccessLog(operateType = EXPORT)
    @PreAuthorize("@ss.hasPermission('bus:village-user:export')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 输出
        ExcelUtils.write(response, "租客列表导入模板.xls", "租客列表", VillageUserImportExcelVO.class, new ArrayList<>());
    }

    @PostMapping("/import")
    @Operation(summary = "导入租客列表")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('bus:village-user:create')")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        List<VillageUserImportExcelVO> list = new ArrayList<>();
        try {
         list = ExcelUtils.read(file, VillageUserImportExcelVO.class);
        }catch (Exception e){
            throw new ServiceException(406,"获取文件失败，请稍后重试");
        }
        return success(villageUserService.importExcel( list,response ));
    }

    @GetMapping("/getList")
    @Operation(summary = "获得项目用户/租客列表")
    @PreAuthorize("@ss.hasPermission('bus:village-user:query')")
    public CommonResult<List<VillageUserRespVO>> getList(@Valid VillageUserPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageUserDO> list = villageUserService.getVillageUserPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, VillageUserRespVO.class));
    }



    @GetMapping("/getUserOwnerList")
    @Operation(summary = "创建工单->根据上报人手机号查询租客")
    @Parameter(name = "phone", description = "上报人手机号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:owner:getUserOwnerList')")
    public CommonResult<List<VillageUserRespVO>> getUserOwnerList(@RequestParam("phone")String phone) {
        List<VillageUserDO> list = villageUserService.getByPhoneOwnerList(phone);
        return success(BeanUtils.toBean(list, VillageUserRespVO.class));
    }
}