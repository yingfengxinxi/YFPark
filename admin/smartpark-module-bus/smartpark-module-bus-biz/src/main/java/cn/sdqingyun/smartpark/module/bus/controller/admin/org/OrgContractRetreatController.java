package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgContractRetreatDO;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractService;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgContractRetreatService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

@Tag(name = "管理后台 - 机构合同退租")
@RestController
@RequestMapping("bus/orgContractRetreat")
@Validated
public class OrgContractRetreatController {

    @Resource
    private OrgContractRetreatService Service;

    @Resource
    private ContractService contractService;


    @PostMapping("/create")
    @Operation(summary = "创建机构合同退租")
    @Parameters({
            @Parameter(name = "contractId", description = "合同id", required = true),
            @Parameter(name = "billInfo", description = "历史账单信息", required = true),
            @Parameter(name = "bondInfo", description = "历史保证金信息", required = true),
            @Parameter(name = "retreatType", description = "退租类型", required = true),
            @Parameter(name = "retreatReason", description = "退租原因", required = true),
            @Parameter(name = "remark", description = "备注信息", required = true),
            @Parameter(name = "retreatDate", description = "退租日期", required = true),
            @Parameter(name = "bccChangeDate", description = "工商注册地址变更日期", required = true),
            @Parameter(name = "applyRetreatDate", description = "协议签订日期(申请退租日期)", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:orgContractRetreat:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgContractRetreatSaveReqVO createReqVO) {
        ContractDO contractDO = contractService.selectById(createReqVO.getContractId());
        contractDO.setStatus("4");
        contractService.updateById(contractDO);
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构合同退租")
    @PreAuthorize("@ss.hasPermission('bus:orgContractRetreat:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgContractRetreatSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构合同退租")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgContractRetreat:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构合同退租")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgContractRetreat:query')")
    public CommonResult<OrgContractRetreatRespVO> get(@RequestParam("id") Long id) {
        OrgContractRetreatDO contractRetreatDO = Service.get(id);
        return success(BeanUtils.toBean(contractRetreatDO, OrgContractRetreatRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构合同退租分页")
    @PreAuthorize("@ss.hasPermission('bus:orgContractRetreat:query')")
    public CommonResult<PageResult<OrgContractRetreatRespVO>> getPage(@Valid OrgContractRetreatPageReqVO pageReqVO) {
        PageResult<OrgContractRetreatDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgContractRetreatRespVO.class));
    }


    @GetMapping("/downloadProtocol")
    @Operation(summary = "生成&下载协议")
    @Parameters({
            @Parameter(name = "contractTemplateId", description = "合同模板id", required = true, example = "1024"),
            @Parameter(name = "contractId", description = "合同id", required = true, example = "1024")
    })
    @PreAuthorize("@ss.hasPermission('bus:orgContractRetreat:downloadProtocol')")
    public CommonResult<String> downloadProtocol(
            @RequestParam("contractTemplateId") Long contractTemplateId,
            @RequestParam("contractId") Long contractId
    ) {
        try {
            return success(Service.downloadProtocol(contractTemplateId, contractId));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(406, e.getMessage());
        }
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构合同退租 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgContractRetreat:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgContractRetreatPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgContractRetreatDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构合同退租.xls", "数据", OrgContractRetreatRespVO.class,
                BeanUtils.toBean(list, OrgContractRetreatRespVO.class));
    }

}