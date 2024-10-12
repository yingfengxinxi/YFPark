package cn.sdqingyun.smartpark.module.bus.controller.admin.bpm;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeavePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeaveRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeaveSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bpm.ContractLeaveDO;
import cn.sdqingyun.smartpark.module.bus.service.bpm.ContractLeaveService;
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

@Tag(name = "管理后台 - 合同审批")
@RestController
@RequestMapping("/bus/contract-leave")
@Validated
public class ContractLeaveController {

    @Resource
    private ContractLeaveService contractLeaveService;

    @PostMapping("/create")
    @Operation(summary = "创建合同审批")
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:create')")
    public CommonResult<Long> createContractLeave(@Valid @RequestBody ContractLeaveSaveReqVO createReqVO) {
        return success(contractLeaveService.createContractLeave(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同审批")
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:update')")
    public CommonResult<Boolean> updateContractLeave(@Valid @RequestBody ContractLeaveSaveReqVO updateReqVO) {
        contractLeaveService.updateContractLeave(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除合同审批")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:delete')")
    public CommonResult<Boolean> deleteContractLeave(@RequestParam("id") Long id) {
        contractLeaveService.deleteContractLeave(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得合同审批")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:query')")
    public CommonResult<ContractLeaveRespVO> getContractLeave(@RequestParam("id") Long id) {
        ContractLeaveDO contractLeave = contractLeaveService.getContractLeave(id);
        return success(BeanUtils.toBean(contractLeave, ContractLeaveRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得合同审批分页")
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:query')")
    public CommonResult<PageResult<ContractLeaveRespVO>> getContractLeavePage(@Valid ContractLeavePageReqVO pageReqVO) {
        PageResult<ContractLeaveDO> pageResult = contractLeaveService.getContractLeavePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ContractLeaveRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同审批 Excel")
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportContractLeaveExcel(@Valid ContractLeavePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ContractLeaveDO> list = contractLeaveService.getContractLeavePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "合同审批.xls", "数据", ContractLeaveRespVO.class,
                        BeanUtils.toBean(list, ContractLeaveRespVO.class));
    }

    @GetMapping("/getList")
    @Operation(summary = "获得合同审批列表")
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:query')")
    public CommonResult<List<ContractLeaveRespVO>> getList(@Valid ContractLeaveSaveReqVO contractLeaveSaveReqVO) {
        List<ContractLeaveRespVO> result =  contractLeaveService.getList(contractLeaveSaveReqVO);
        return success(result);
    }

    @PostMapping("/getOneByContractId")
    @Operation(summary = "根据合同id查询审批")
    @PreAuthorize("@ss.hasPermission('bus:contract-leave:query')")
    public CommonResult<ContractLeaveRespVO> getOneByContractId(@RequestBody ContractLeaveSaveReqVO contractLeaveSaveReqVO) {
        return success( contractLeaveService.getOneByContractId(contractLeaveSaveReqVO));
    }
}