package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPageDO;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomPageService;

@Tag(name = "管理后台 - 招商中心装修页")
@RestController
@RequestMapping("/bus/room-page")
@Validated
public class RoomPageController {

    @Resource
    private RoomPageService roomPageService;

    @PostMapping("/create")
    @Operation(summary = "创建招商中心装修页")
    @PreAuthorize("@ss.hasPermission('bus:room-page:create')")
    public CommonResult<Long> createRoomPage(@Valid @RequestBody RoomPageSaveReqVO createReqVO) {
        return success(roomPageService.createRoomPage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新招商中心装修页")
    @PreAuthorize("@ss.hasPermission('bus:room-page:update')")
    public CommonResult<Boolean> updateRoomPage(@Valid @RequestBody RoomPageSaveReqVO updateReqVO) {
        roomPageService.updateRoomPage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除招商中心装修页")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:room-page:delete')")
    public CommonResult<Boolean> deleteRoomPage(@RequestParam("id") Long id) {
        roomPageService.deleteRoomPage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得招商中心装修页")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:room-page:query')")
    public CommonResult<RoomPageRespVO> getRoomPage(@RequestParam("id") Long id) {
        RoomPageDO roomPage = roomPageService.getRoomPage(id);
        return success(BeanUtils.toBean(roomPage, RoomPageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得招商中心装修页分页")
    @PreAuthorize("@ss.hasPermission('bus:room-page:query')")
    public CommonResult<PageResult<RoomPageRespVO>> getRoomPagePage(@Valid RoomPagePageReqVO pageReqVO) {
        PageResult<RoomPageDO> pageResult = roomPageService.getRoomPagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomPageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出招商中心装修页 Excel")
    @PreAuthorize("@ss.hasPermission('bus:room-page:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomPageExcel(@Valid RoomPagePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomPageDO> list = roomPageService.getRoomPagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "招商中心装修页.xls", "数据", RoomPageRespVO.class,
                        BeanUtils.toBean(list, RoomPageRespVO.class));
    }

}