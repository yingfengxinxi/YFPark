package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddleRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAnnexDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillAnnexService;
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

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 机构账单收支流水附件")
@RestController
@RequestMapping("/bus/orgBillAnnex")
@Validated
public class OrgBillAnnexController {

    @Resource
    private OrgBillAnnexService billAnnexService;

    @PostMapping("/create")
    @Operation(summary = "创建机构账单收支流水附件")
    @Parameters({
            @Parameter(name = "sourceId", description = "流水id【" +
                    "账单附件就是账单id," +
                    "流水附件就是流水id】", required = true),
            @Parameter(name = "name", description = "文件名称", required = true),
            @Parameter(name = "type", description = "账单操作类型;1=账单附件;2=流水附件", required = true),
            @Parameter(name = "annexSource", description = "1=pc;2=phone", required = true),
            @Parameter(name = "filePath", description = "文件路径", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:orgBillAnnex:create')")
    public CommonResult<Long> createBillAnnex(@Valid @RequestBody OrgBillAnnexSaveReqVO createReqVO) {
        return success(billAnnexService.createBillAnnex(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构账单收支流水附件")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAnnex:update')")
    public CommonResult<Boolean> updateBillAnnex(@Valid @RequestBody OrgBillAnnexSaveReqVO updateReqVO) {
        billAnnexService.updateBillAnnex(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构账单收支流水附件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillAnnex:delete')")
    public CommonResult<Boolean> deleteBillAnnex(@RequestParam("id") Long id) {
        billAnnexService.deleteBillAnnex(id);
        return success(true);
    }


    /**
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getBillAnnexList")
    @Operation(summary = "获得机构账单收支流水附件分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillAnnex:getBillAnnexList')")
    @Parameters({
            @Parameter(name = "pageSize", description = "显示条数", required = true),
            @Parameter(name = "pageNo", description = "第几页", required = true),
            @Parameter(name = "sourceId", description = "流水id【账单附件就是账单id,流水附件就是流水id】", required = true),
            @Parameter(name = "type", description = "账单操作类型;1=账单附件;2=流水附件", required = true)
    })
    public CommonResult<PageResult<OrgBillAnnexRespVO>> getBillAnnexList(@Valid OrgBillAnnexPageReqVO pageReqVO) {
        PageResult<OrgBillAnnexDO> pageResult = billAnnexService.getBillAnnexList(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillAnnexRespVO.class));
    }


}