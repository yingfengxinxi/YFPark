package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.AdjustBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAdjustPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAdjustRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAdjustSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAdjustDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillAdjustService;
import io.swagger.v3.oas.annotations.Parameters;
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

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 机构账单调整")
@RestController
@RequestMapping("/bus/orgBillAdjust")
@Validated
public class OrgBillAdjustController {

    @Resource
    private OrgBillAdjustService Service;

    /**
     * {
     * "billId":"22",
     * "adjustType":"1",
     * "adjustMode":"1",
     * "adjustProportion":"10",
     * "adjustPrice":"",
     * "remark":"我是备注1"
     * }
     *
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建机构账单调整")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:create')")
    @Parameters({
            @Parameter(name = "billId", description = "账单id", required = true),
            @Parameter(name = "adjustType", description = "调整类型【数据字典ADJUST_TYPE】", required = true),
            @Parameter(name = "adjustMode", description = "调整方式【数据字典ADJUST_MODE】", required = true),
            @Parameter(name = "adjustDate", description = "调整日期", required = true),
            @Parameter(name = "adjustProportion", description = "调整比例【只有调整模式是按比例调整时才会使用】", required = false),
            @Parameter(name = "adjustPrice", description = "调整金额【只有调整模式是按金额调整时才会使用】", required = false),
            @Parameter(name = "remark", description = "备注", required = false)
    })
    public CommonResult<Long> create(@Valid @RequestBody OrgBillAdjustSaveReqVO createReqVO) {
        try {
            return CommonResult.success(Service.create(createReqVO));
        } catch (Exception e) {
            return CommonResult.error(406, e.getMessage());
        }

    }


    @GetMapping("/approved")
    @Operation(summary = "创建机构账单调整审核通过")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:create')")
    @Parameter(name = "id", description = "账单调整id", required = true)
    public CommonResult<?> approved(@RequestParam("id") Long id) {
        Service.approved(id);
        return success(null);
    }


    @GetMapping("/applicationToVoid")
    @Operation(summary = "申请作废调整")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:applicationToVoid')")
    @Parameter(name = "id", description = "账单调整id", required = true)
    public CommonResult<?> applicationToVoid(@RequestParam("id") Long id) {
        try {
            Service.applicationToVoid(id);
            return success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(406, e.getMessage());
        }
    }

    @GetMapping("/voidAdjustmentApproval")
    @Operation(summary = "申请作废调整审核通过")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:voidAdjustmentApproval')")
    @Parameter(name = "id", description = "账单调整id", required = true)
    public CommonResult<?> voidAdjustmentApproval(@RequestParam("id") Long id) {
        try {
            Service.voidAdjustmentApproval(id);
            return success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(406, e.getMessage());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构账单调整")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillAdjustSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构账单调整")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构账单调整")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:query')")
    public CommonResult<OrgBillAdjustRespVO> get(@RequestParam("id") Long id) {
        OrgBillAdjustDO orgBillAdjustDO = Service.get(id);
        return success(BeanUtils.toBean(orgBillAdjustDO, OrgBillAdjustRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构账单调整分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:query')")
    @Parameters({
            @Parameter(name = "billId", description = "账单id", required = true),
            @Parameter(name = "adjustStatus", description = "状态【选中显示作废调整时传值为4，不选时传值为空】", required = true)
    })
    public CommonResult<PageResult<OrgBillAdjustRespVO>> getPage(@Valid OrgBillAdjustPageReqVO pageReqVO) {
        PageResult<OrgBillAdjustDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillAdjustRespVO.class));
    }


    @GetMapping("/getAdjustBillTopStatistic")
    @Operation(summary = "财务报表-调整账单顶部统计")
    @Parameters({
            @Parameter(name = "ownerName", description = "租客名称", required = false),
            @Parameter(name = "contractNumber", description = "合同编号", required = false),
            @Parameter(name = "orderNumber", description = "账单编号", required = false),
            @Parameter(name = "adjustType", description = "调整状态【数据字典ADJUST_TYPE】", required = false),
            @Parameter(name = "parkIdList", description = "项目id集合", required = false),
            @Parameter(name = "billIdList", description = "楼宇id集合", required = false)
    })
    public CommonResult<?> getAdjustBillTopStatistic(@Valid AdjustBillListVO adjustBillListVO) {

        return success(Service.getAdjustBillTopStatistic(adjustBillListVO));
    }

    @GetMapping("/getAdjustBillListPage")
    @Operation(summary = "财务报表-调整账单")
    @Parameters({
            @Parameter(name = "pageSize", description = "条数", required = true),
            @Parameter(name = "pageNo", description = "页数", required = true),
            @Parameter(name = "ownerName", description = "租客名称", required = false),
            @Parameter(name = "contractNumber", description = "合同编号", required = false),
            @Parameter(name = "orderNumber", description = "账单编号", required = false),
            @Parameter(name = "adjustType", description = "调整状态【数据字典ADJUST_TYPE】", required = false),
            @Parameter(name = "parkIdList", description = "项目id集合", required = false),
            @Parameter(name = "billIdList", description = "楼宇id集合", required = false)
    })
    public CommonResult<PageResult<AdjustBillListVO>> getAdjustBillListPage(@Valid AdjustBillListVO adjustBillListVO) {

        return success(Service.getAdjustBillList(adjustBillListVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构账单调整 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAdjust:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgBillAdjustPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgBillAdjustDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构账单调整.xls", "数据", OrgBillAdjustRespVO.class,
                BeanUtils.toBean(list, OrgBillAdjustRespVO.class));
    }


}