package cn.sdqingyun.smartpark.module.bus.controller.admin.owner;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo.OwnerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.OwnImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.service.owner.OwnerService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 租客信息")
@RestController
@RequestMapping("/bus/owner/")
@Validated
public class OwnerController {

    @Resource
    private OwnerService Service;

    @PostMapping("/create")
    @Operation(summary = "创建租客信息")
    @PreAuthorize("@ss.hasPermission('bus:owner:create')")
    public CommonResult<Long> create(@Valid @RequestBody OwnerSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租客信息")
    @PreAuthorize("@ss.hasPermission('bus:owner:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OwnerSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租客信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:owner:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租客信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:owner:query')")
    public CommonResult<OwnerRespVO> get(@RequestParam("id") Long id) {
        OwnerDO ownerDO = Service.get(id);
        return success(BeanUtils.toBean(ownerDO, OwnerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租客信息分页")
    @PreAuthorize("@ss.hasPermission('bus:owner:query')")
    public CommonResult<PageResult<OwnerRespVO>> getPage(@Valid OwnerPageReqVO pageReqVO) {
        PageResult<OwnerRespVO> pageResult = Service.getOwnerRespVOPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OwnerRespVO.class));
    }


    /**
     * @return
     */
    @GetMapping("/getByRoomIdOwnerList")
    @Operation(summary = "根据房间号查询租客信息")
    @Parameter(name = "roomId", description = "房间号", required = true)
    public CommonResult<List<OwnerRespVO>> getByRoomIdOwnerList(@RequestParam("roomId") Long roomId) {
        List<OwnerDO> ownerList = Service.getByRoomIdOwnerList(roomId);
        return success(BeanUtils.toBean(ownerList, OwnerRespVO.class));
    }

    @GetMapping("/getByIdOwnerInfo")
    @Operation(summary = "收银台-租客详情顶部数据接口")
    @Parameter(name = "id", description = "编号id", required = true)
    @TenantIgnore
    public CommonResult<?> getByIdOwnerInfo(@RequestParam("id") Long id) {

        return success(Service.getByIdOwnerInfo(id));
    }

    /**
     * @return
     */
    @GetMapping("/getOwnerList")
    @Operation(summary = "获得租客信息下拉信息")
    public CommonResult<List<OwnerRespVO>> getOwnerList() {
        List<OwnerDO> ownerList = Service.getOwnerList();
        return success(BeanUtils.toBean(ownerList, OwnerRespVO.class));
    }

    @GetMapping("/export")
    @Operation(summary = "导出租客信息 Excel")
    @PreAuthorize("@ss.hasPermission('bus:owner:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OwnerPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OwnerDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "租客信息.xls", "数据", OwnerRespVO.class,
                BeanUtils.toBean(list, OwnerRespVO.class));
    }

    @GetMapping("/getOwnerListByName")
    @Operation(summary = "根据名称获得租客信息列表")
    @PreAuthorize("@ss.hasPermission('bus:owner:query')")
    public CommonResult<List<OwnerRespVO>> getOwnerListByName(@Valid OwnerPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OwnerDO> list = Service.getPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, OwnerRespVO.class));
    }

    @GetMapping("/getCountOwnerMap")
    @Operation(summary = "获取租客列表信息统计")
    @PreAuthorize("@ss.hasPermission('bus:owner:query')")
    public CommonResult<Map<String, Object>> getCountOwnerMap() {
        return success(Service.getCountOwnerMap());
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入租客列表模板")
    @ApiAccessLog(operateType = EXPORT)
    @PreAuthorize("@ss.hasPermission('bus:owner:export')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<OwnImportExcelVO> list = Arrays.asList(
                OwnImportExcelVO.builder().isPersonal("公司/个人（必填）").name("公司全称/个人姓名（必填）")
                        .contactName("填写联系人名称（必填）").VillageType("超级管理员")
                        .tel("填写联系人电话（必填）").idcardType("证件类型（选填）").certificateNumber("填写租客证件号码（选填）")
                        .tenantNo("填写租客编码（选填）").address("填写联系人通讯地址（选填）").importResult("非填项，如上传失败请下载反馈文档，该列会予以问题说明").build()
        );
        // 输出
        ExcelUtils.write(response, "租客列表导入模板.xls", "租客列表", OwnImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入租客列表")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('bus:owner:create')")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        List<OwnImportExcelVO> list = new ArrayList<>();
        try {
            list = ExcelUtils.read(file, OwnImportExcelVO.class);
        } catch (Exception e) {
            throw new ServiceException(406, "获取文件失败，请稍后重试");
        }
        return success(Service.importExcel(list, response));
    }

    @PostMapping("/getCountOwnerAnnularRing")
    @Operation(summary = "租客行业环形图")
    @PreAuthorize("@ss.hasPermission('bus:owner:query')")
    public CommonResult<List<Map<String, Object>>> getCountOwnerAnnularRing(@RequestBody OwnerSaveReqVO createReqVO) {
        return success(Service.getCountOwnerAnnularRing(createReqVO));
    }

    @PostMapping("/getCountOwnerTagAnnularRing")
    @Operation(summary = "租客标签环形图")
    @PreAuthorize("@ss.hasPermission('bus:owner:query')")
    public CommonResult<List<Map<String, Object>>> getCountOwnerTagAnnularRing(@RequestBody OwnerSaveReqVO createReqVO) {
        return success(Service.getCountOwnerTagAnnularRing(createReqVO));
    }


    @PostMapping("/ownerSearchList")
    @Operation(summary = "收银台租客查询")
    @Parameters({
            @Parameter(name = "name", description = "租客名称", required = false),
            @Parameter(name = "tel", description = "手机号", required = false)
    })
    @PreAuthorize("@ss.hasPermission('bus:owner:ownerSearchList')")
    public CommonResult<List<OwnerRespVO>> ownerSearchList(@Valid @RequestBody OwnerPageReqVO pageReqVO) {
        List<OwnerDO> list = Service.getList(pageReqVO);
        return success(BeanUtils.toBean(list, OwnerRespVO.class));
    }
}