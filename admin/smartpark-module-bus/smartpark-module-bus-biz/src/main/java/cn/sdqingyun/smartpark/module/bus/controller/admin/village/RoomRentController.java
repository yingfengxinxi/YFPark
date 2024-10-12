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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomRentDO;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomRentService;

@Tag(name = "管理后台 - 租客在租/绑定房间")
@RestController
@RequestMapping("/bus/room-rent")
@Validated
public class RoomRentController {

    @Resource
    private RoomRentService roomRentService;

    @PostMapping("/create")
    @Operation(summary = "创建租客在租/绑定房间")
    @PreAuthorize("@ss.hasPermission('bus:room-rent:create')")
    public CommonResult<Long> createRoomRent(@Valid @RequestBody RoomRentSaveReqVO createReqVO) {
        return success(roomRentService.createRoomRent(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租客在租/绑定房间")
    @PreAuthorize("@ss.hasPermission('bus:room-rent:update')")
    public CommonResult<Boolean> updateRoomRent(@Valid @RequestBody RoomRentSaveReqVO updateReqVO) {
        roomRentService.updateRoomRent(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租客在租/绑定房间")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:room-rent:delete')")
    public CommonResult<Boolean> deleteRoomRent(@RequestParam("id") Long id) {
        roomRentService.deleteRoomRent(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租客在租/绑定房间")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:room-rent:query')")
    public CommonResult<RoomRentRespVO> getRoomRent(@RequestParam("id") Long id) {
        RoomRentDO roomRent = roomRentService.getRoomRent(id);
        return success(BeanUtils.toBean(roomRent, RoomRentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租客在租/绑定房间分页")
    @PreAuthorize("@ss.hasPermission('bus:room-rent:query')")
    public CommonResult<PageResult<RoomRentRespVO>> getRoomRentPage(@Valid RoomRentPageReqVO pageReqVO) {
        PageResult<RoomRentDO> pageResult = roomRentService.getRoomRentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomRentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出租客在租/绑定房间 Excel")
    @PreAuthorize("@ss.hasPermission('bus:room-rent:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomRentExcel(@Valid RoomRentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomRentDO> list = roomRentService.getRoomRentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "租客在租/绑定房间.xls", "数据", RoomRentRespVO.class,
                        BeanUtils.toBean(list, RoomRentRespVO.class));
    }

}