package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomRemarksDO;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomRemarksService;

@Tag(name = "管理后台 - 房源操作记录")
@RestController
@RequestMapping("/bus/room-remarks")
@Validated
public class RoomRemarksController {

    @Resource
    private RoomRemarksService roomRemarksService;

    /**
     * {
     * "roomId":"1",
     * "villageId":"1",
     * "remark":"我是备注"
     * }
     *
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建房源操作记录【备忘提醒】")
    @Parameters({
            @Parameter(name = "roomId", description = "房间id", required = true),
            @Parameter(name = "villageId", description = "园区id", required = true),
            @Parameter(name = "remark", description = "备注", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:room-remarks:create')")
    public CommonResult<Long> createRoomRemarks(@Valid @RequestBody RoomRemarksSaveReqVO createReqVO) {
        return success(roomRemarksService.createRoomRemarks(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新房源操作记录")
    @PreAuthorize("@ss.hasPermission('bus:room-remarks:update')")
    public CommonResult<Boolean> updateRoomRemarks(@Valid @RequestBody RoomRemarksSaveReqVO updateReqVO) {
        roomRemarksService.updateRoomRemarks(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除房源操作记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:room-remarks:delete')")
    public CommonResult<Boolean> deleteRoomRemarks(@RequestParam("id") Long id) {
        roomRemarksService.deleteRoomRemarks(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得房源操作记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:room-remarks:query')")
    public CommonResult<RoomRemarksRespVO> getRoomRemarks(@RequestParam("id") Long id) {
        RoomRemarksDO roomRemarks = roomRemarksService.getRoomRemarks(id);
        return success(BeanUtils.toBean(roomRemarks, RoomRemarksRespVO.class));
    }

    /**
     *
     * @param pageReqVO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "获得房源操作记录分页")
    @Parameters({
            @Parameter(name = "roomId", description = "房间id", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:room-remarks:query')")
    public CommonResult<PageResult<RoomRemarksRespVO>> getRoomRemarksPage(@Valid RoomRemarksPageReqVO pageReqVO) {
        PageResult<RoomRemarksDO> pageResult = roomRemarksService.getRoomRemarksPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomRemarksRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出房源操作记录 Excel")
    @PreAuthorize("@ss.hasPermission('bus:room-remarks:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomRemarksExcel(@Valid RoomRemarksPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomRemarksDO> list = roomRemarksService.getRoomRemarksPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "房源操作记录.xls", "数据", RoomRemarksRespVO.class,
                BeanUtils.toBean(list, RoomRemarksRespVO.class));
    }

}