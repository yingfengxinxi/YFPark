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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomHitsDO;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomHitsService;

@Tag(name = "管理后台 - 房间点击量")
@RestController
@RequestMapping("/bus/room-hits")
@Validated
public class RoomHitsController {

    @Resource
    private RoomHitsService roomHitsService;

    @PostMapping("/create")
    @Operation(summary = "创建房间点击量")
    @PreAuthorize("@ss.hasPermission('bus:room-hits:create')")
    public CommonResult<Long> createRoomHits(@Valid @RequestBody RoomHitsSaveReqVO createReqVO) {
        return success(roomHitsService.createRoomHits(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新房间点击量")
    @PreAuthorize("@ss.hasPermission('bus:room-hits:update')")
    public CommonResult<Boolean> updateRoomHits(@Valid @RequestBody RoomHitsSaveReqVO updateReqVO) {
        roomHitsService.updateRoomHits(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除房间点击量")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:room-hits:delete')")
    public CommonResult<Boolean> deleteRoomHits(@RequestParam("id") Long id) {
        roomHitsService.deleteRoomHits(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得房间点击量")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:room-hits:query')")
    public CommonResult<RoomHitsRespVO> getRoomHits(@RequestParam("id") Long id) {
        RoomHitsDO roomHits = roomHitsService.getRoomHits(id);
        return success(BeanUtils.toBean(roomHits, RoomHitsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得房间点击量分页")
    @PreAuthorize("@ss.hasPermission('bus:room-hits:query')")
    public CommonResult<PageResult<RoomHitsRespVO>> getRoomHitsPage(@Valid RoomHitsPageReqVO pageReqVO) {
        PageResult<RoomHitsDO> pageResult = roomHitsService.getRoomHitsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomHitsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出房间点击量 Excel")
    @PreAuthorize("@ss.hasPermission('bus:room-hits:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomHitsExcel(@Valid RoomHitsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomHitsDO> list = roomHitsService.getRoomHitsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "房间点击量.xls", "数据", RoomHitsRespVO.class,
                        BeanUtils.toBean(list, RoomHitsRespVO.class));
    }

}