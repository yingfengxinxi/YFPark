package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptLogRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptLogDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillReceiptLogService;
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
@Tag(name = "管理后台 - 账单开据记录")
@RestController
@RequestMapping("/bus/orgBillReceiptLog/")
@Validated
public class OrgBillReceiptLogController {

    @Resource
    private OrgBillReceiptLogService Service;

    @PostMapping("/create")
    @Operation(summary = "创建账单开据记录")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptLog:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgBillReceiptLogSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新账单开据记录")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptLog:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillReceiptLogSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除账单开据记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptLog:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得账单开据记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptLog:query')")
    public CommonResult<OrgBillReceiptLogRespVO> get(@RequestParam("id") Long id) {
        OrgBillReceiptLogDO billReceiptLogDO = Service.get(id);
        return success(BeanUtils.toBean(billReceiptLogDO, OrgBillReceiptLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得账单开据记录分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptLog:query')")
    public CommonResult<PageResult<OrgBillReceiptLogRespVO>> getPage(@Valid OrgBillReceiptLogPageReqVO pageReqVO) {
        PageResult<OrgBillReceiptLogDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillReceiptLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出账单开据记录 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptLog:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgBillReceiptLogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgBillReceiptLogDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "账单开据记录.xls", "数据", OrgBillReceiptLogRespVO.class,
                        BeanUtils.toBean(list, OrgBillReceiptLogRespVO.class));
    }

}