package cn.sdqingyun.smartpark.module.bus.controller.admin.tag;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagContractPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagContractRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagContractSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagContractDO;
import cn.sdqingyun.smartpark.module.bus.service.tag.TagContractService;
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

@Tag(name = "管理后台 - 合同标签")
@RestController
@RequestMapping("/bus/tag-contract")
@Validated
public class TagContractController {

    @Resource
    private TagContractService tagContractService;

    @PostMapping("/create")
    @Operation(summary = "创建合同标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-contract:create')")
    public CommonResult<Long> createTagContract(@Valid @RequestBody TagContractSaveReqVO createReqVO) {
        return success(tagContractService.createTagContract(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-contract:update')")
    public CommonResult<Boolean> updateTagContract(@Valid @RequestBody TagContractSaveReqVO updateReqVO) {
        tagContractService.updateTagContract(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除合同标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:tag-contract:delete')")
    public CommonResult<Boolean> deleteTagContract(@RequestParam("id") Long id) {
        tagContractService.deleteTagContract(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得合同标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:tag-contract:query')")
    public CommonResult<TagContractRespVO> getTagContract(@RequestParam("id") Long id) {
        TagContractDO tagContract = tagContractService.getTagContract(id);
        return success(BeanUtils.toBean(tagContract, TagContractRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得合同标签分页")
    @PreAuthorize("@ss.hasPermission('bus:tag-contract:query')")
    public CommonResult<PageResult<TagContractRespVO>> getTagContractPage(@Valid TagContractPageReqVO pageReqVO) {
        PageResult<TagContractDO> pageResult = tagContractService.getTagContractPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagContractRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同标签 Excel")
    @PreAuthorize("@ss.hasPermission('bus:tag-contract:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagContractExcel(@Valid TagContractPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagContractDO> list = tagContractService.getTagContractPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "合同标签.xls", "数据", TagContractRespVO.class,
                        BeanUtils.toBean(list, TagContractRespVO.class));
    }

    @GetMapping("/getTagContractList")
    @Operation(summary = "获得合同标签列表")
    @PreAuthorize("@ss.hasPermission('bus:tag-contract:query')")
    public CommonResult<List<TagContractRespVO>> getTagContractList(@Valid TagContractPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagContractDO> list = tagContractService.getTagContractPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, TagContractRespVO.class));
    }
}