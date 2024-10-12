package cn.sdqingyun.smartpark.module.bus.controller.admin.tag;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagTerminationPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagTerminationRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagTerminationSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagTerminationDO;
import cn.sdqingyun.smartpark.module.bus.service.tag.TagTerminationService;
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

@Tag(name = "管理后台 - 退租原因")
@RestController
@RequestMapping("bus/tagTermination/")
@Validated
public class TagTerminationController {

    @Resource
    private TagTerminationService Service;

    /**
     * {
     * "name":"退租标签名称",
     * "remarks":"描述",
     * "color":"{\"borderColor\":\"178,121,176\",\"bgColor\":\"227,235,255\"}",
     * "status":1
     * }
     *
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建退租原因")
    @PreAuthorize("@ss.hasPermission('bus:tagTermination:create')")
    public CommonResult<Long> create(@Valid @RequestBody TagTerminationSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新退租原因")
    @PreAuthorize("@ss.hasPermission('bus:tagTermination:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody TagTerminationSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除退租原因")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:tagTermination:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得退租原因")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:tagTermination:query')")
    public CommonResult<TagTerminationRespVO> get(@RequestParam("id") Long id) {
        TagTerminationDO tagTerminationDO = Service.get(id);
        return success(BeanUtils.toBean(tagTerminationDO, TagTerminationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得退租原因分页")
    @PreAuthorize("@ss.hasPermission('bus:tagTermination:query')")
    public CommonResult<PageResult<TagTerminationRespVO>> getPage(@Valid TagTerminationPageReqVO pageReqVO) {
        PageResult<TagTerminationDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagTerminationRespVO.class));
    }

    @GetMapping("/getList")
    @Operation(summary = "获得退租原因列表")
    @PreAuthorize("@ss.hasPermission('bus:tagTermination:query')")
    public CommonResult<PageResult<TagTerminationRespVO>> getList(@Valid TagTerminationPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TagTerminationDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagTerminationRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出退租原因 Excel")
    @PreAuthorize("@ss.hasPermission('bus:tagTermination:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid TagTerminationPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagTerminationDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "退租原因.xls", "数据", TagTerminationRespVO.class,
                BeanUtils.toBean(list, TagTerminationRespVO.class));
    }

}