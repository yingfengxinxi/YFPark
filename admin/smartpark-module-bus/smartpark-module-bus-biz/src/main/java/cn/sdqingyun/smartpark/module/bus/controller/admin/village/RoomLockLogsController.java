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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomLockLogsDO;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomLockLogsService;

@Tag(name = "管理后台 - 项目房间锁定日志")
@RestController
@RequestMapping("/bus/room-lock-logs")
@Validated
public class RoomLockLogsController {

    @Resource
    private RoomLockLogsService roomLockLogsService;

    @PostMapping("/create")
    @Operation(summary = "创建项目房间锁定日志")
    @PreAuthorize("@ss.hasPermission('bus:room-lock-logs:create')")
    public CommonResult<Long> createRoomLockLogs(@Valid @RequestBody RoomLockLogsSaveReqVO createReqVO) {
        return success(roomLockLogsService.createRoomLockLogs(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目房间锁定日志")
    @PreAuthorize("@ss.hasPermission('bus:room-lock-logs:update')")
    public CommonResult<Boolean> updateRoomLockLogs(@Valid @RequestBody RoomLockLogsSaveReqVO updateReqVO) {
        roomLockLogsService.updateRoomLockLogs(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目房间锁定日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:room-lock-logs:delete')")
    public CommonResult<Boolean> deleteRoomLockLogs(@RequestParam("id") Long id) {
        roomLockLogsService.deleteRoomLockLogs(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目房间锁定日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:room-lock-logs:query')")
    public CommonResult<RoomLockLogsRespVO> getRoomLockLogs(@RequestParam("id") Long id) {
        RoomLockLogsDO roomLockLogs = roomLockLogsService.getRoomLockLogs(id);
        return success(BeanUtils.toBean(roomLockLogs, RoomLockLogsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目房间锁定日志分页")
    @PreAuthorize("@ss.hasPermission('bus:room-lock-logs:query')")
    public CommonResult<PageResult<RoomLockLogsRespVO>> getRoomLockLogsPage(@Valid RoomLockLogsPageReqVO pageReqVO) {
        PageResult<RoomLockLogsDO> pageResult = roomLockLogsService.getRoomLockLogsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomLockLogsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目房间锁定日志 Excel")
    @PreAuthorize("@ss.hasPermission('bus:room-lock-logs:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomLockLogsExcel(@Valid RoomLockLogsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomLockLogsDO> list = roomLockLogsService.getRoomLockLogsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目房间锁定日志.xls", "数据", RoomLockLogsRespVO.class,
                        BeanUtils.toBean(list, RoomLockLogsRespVO.class));
    }

}