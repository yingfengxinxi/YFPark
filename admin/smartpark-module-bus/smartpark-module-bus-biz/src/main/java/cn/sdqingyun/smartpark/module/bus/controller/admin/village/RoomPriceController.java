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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomPriceDO;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomPriceService;

@Tag(name = "管理后台 - 房间价格")
@RestController
@RequestMapping("/bus/room-price")
@Validated
public class RoomPriceController {

    @Resource
    private RoomPriceService roomPriceService;

    @PostMapping("/create")
    @Operation(summary = "创建房间价格")
    @PreAuthorize("@ss.hasPermission('bus:room-price:create')")
    public CommonResult<Long> createRoomPrice(@Valid @RequestBody RoomPriceSaveReqVO createReqVO) {
        return success(roomPriceService.createRoomPrice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新房间价格")
    @PreAuthorize("@ss.hasPermission('bus:room-price:update')")
    public CommonResult<Boolean> updateRoomPrice(@Valid @RequestBody RoomPriceSaveReqVO updateReqVO) {
        roomPriceService.updateRoomPrice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除房间价格")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:room-price:delete')")
    public CommonResult<Boolean> deleteRoomPrice(@RequestParam("id") Long id) {
        roomPriceService.deleteRoomPrice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得房间价格")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:room-price:query')")
    public CommonResult<RoomPriceRespVO> getRoomPrice(@RequestParam("id") Long id) {
        RoomPriceDO roomPrice = roomPriceService.getRoomPrice(id);
        return success(BeanUtils.toBean(roomPrice, RoomPriceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得房间价格分页")
    @PreAuthorize("@ss.hasPermission('bus:room-price:query')")
    public CommonResult<PageResult<RoomPriceRespVO>> getRoomPricePage(@Valid RoomPricePageReqVO pageReqVO) {
        PageResult<RoomPriceDO> pageResult = roomPriceService.getRoomPricePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomPriceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出房间价格 Excel")
    @PreAuthorize("@ss.hasPermission('bus:room-price:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomPriceExcel(@Valid RoomPricePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomPriceDO> list = roomPriceService.getRoomPricePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "房间价格.xls", "数据", RoomPriceRespVO.class,
                        BeanUtils.toBean(list, RoomPriceRespVO.class));
    }

}