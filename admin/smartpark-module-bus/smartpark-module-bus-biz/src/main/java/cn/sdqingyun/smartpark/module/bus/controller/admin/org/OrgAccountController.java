package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgAccountSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgAccountDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgAccountService;
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

@Tag(name = "管理后台 - 收支账户配置")
@RestController
@RequestMapping("bus/orgAccount/")
@Validated
public class OrgAccountController {

    @Resource
    private OrgAccountService Service;

    @PostMapping("/create")
    @Operation(summary = "创建收支账户配置")
    @PreAuthorize("@ss.hasPermission('bus:orgAccount:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgAccountSaveReqVO createReqVO) {

        Integer nameCount = Service.getNameCount(createReqVO.getName(), null);
        if (nameCount >= 1) {
            throw new RuntimeException("条目名称已存在请勿重复添加");
        }
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收支账户配置")
    @PreAuthorize("@ss.hasPermission('bus:orgAccount:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgAccountSaveReqVO updateReqVO) {

        Integer nameCount = Service.getNameCount(updateReqVO.getName(), updateReqVO.getId());
        if (nameCount >= 1) {
            throw new RuntimeException("条目名称已存在请勿重复添加");
        }
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收支账户配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgAccount:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收支账户配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgAccount:query')")
    public CommonResult<OrgAccountRespVO> get(@RequestParam("id") Long id) {
        OrgAccountDO orgAccountDO = Service.get(id);
        return success(BeanUtils.toBean(orgAccountDO, OrgAccountRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收支账户配置分页")
    @PreAuthorize("@ss.hasPermission('bus:orgAccount:query')")
    public CommonResult<PageResult<OrgAccountRespVO>> getPage(@Valid OrgAccountPageReqVO pageReqVO) {
        PageResult<OrgAccountDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgAccountRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得所有收支账户配置【下拉】")
    public CommonResult<List<OrgAccountRespVO>> getList(@RequestParam(required = false,value = "build") String build) {

        return success(BeanUtils.toBean(Service.getList(build), OrgAccountRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收支账户配置 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgAccount:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgAccountPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgAccountDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "收支账户配置.xls", "数据", OrgAccountRespVO.class,
                BeanUtils.toBean(list, OrgAccountRespVO.class));
    }

    @GetMapping("/getAccountPage")
    @Operation(summary = "获得收支账户配置分页（包含楼宇名称)")
    @PreAuthorize("@ss.hasPermission('bus:orgAccount:query')")
    public CommonResult<PageResult<OrgAccountRespVO>> getAccountPage(@Valid OrgAccountPageReqVO pageReqVO) {
        PageResult<OrgAccountRespVO> pageResult = Service.getAccountPage(pageReqVO);
        return success(pageResult);
    }
}