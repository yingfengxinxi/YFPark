package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.RoomImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.service.village.RoomService;
import com.alibaba.nacos.common.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 项目房间")
@RestController
@RequestMapping("/bus/room")
@Validated
public class RoomController {

    @Resource
    private RoomService roomService;

    @PostMapping("/create")
    @Operation(summary = "创建项目房间")
    @PreAuthorize("@ss.hasPermission('bus:room:create')")
    public CommonResult<Long> createRoom(@Valid @RequestBody RoomSaveReqVO createReqVO) {
        return success(roomService.createRoom(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目房间")
    @PreAuthorize("@ss.hasPermission('bus:room:update')")
    public CommonResult<Boolean> updateRoom(@Valid @RequestBody RoomSaveReqVO updateReqVO) {
        roomService.updateRoom(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目房间")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:room:delete')")
    public CommonResult<Boolean> deleteRoom(@RequestParam("id") Long id) {
        roomService.deleteRoom(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目房间")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:room:query')")
    public CommonResult<RoomRespVO> getRoom(@RequestParam("id") Long id) {
        return success(roomService.getOneRoom(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目房间分页")
    @PreAuthorize("@ss.hasPermission('bus:room:query')")
    public CommonResult<PageResult<RoomRespVO>> getRoomPage(@Valid RoomPageReqVO pageReqVO) {
        PageResult<RoomDO> pageResult = roomService.getRoomPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目房间 Excel")
    @PreAuthorize("@ss.hasPermission('bus:room:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomExcel(@Valid RoomPageReqVO pageReqVO,
                                HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomDO> list = roomService.getRoomPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目房间.xls", "数据", RoomRespVO.class,
                BeanUtils.toBean(list, RoomRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入房间模板")
    @ApiAccessLog(operateType = EXPORT)
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<RoomImportExcelVO> list = Arrays.asList(
                RoomImportExcelVO.builder().name("请与设置的项目名称相匹配\n" +
                                "例：智慧产业园").buildName("请与设置的楼宇名称相匹配\n" +
                                "例：C2").layerName("请与设置的楼层名称相匹配\n" +
                                "例：5").roomNumber("重复则无法导入\n" +
                                "例：301").roomName("重名则无法导入\n" +
                                "例：A301").rentalArea("支持两位小数\n" +
                                "例：125.95").chargingArea("支持两位小数（选填）\n" +
                                "例：125.95").buildArea("支持两位小数（选填）").floorHeight("支持两位小数（选填）").preUnitPrice("支持两位小数（选填）")
                        .preUnitPriceMin("支持两位小数（选填）").priceUnit("例元/㎡·月，元/㎡·天，元/天，元/月，元/年").priceUnitMin("（选填）\n" +
                                "下拉选择 例元/㎡·月，元/㎡·天，元/天，元/月，元/年").recordNo("选填").decoration("朝南; 朝北; 有窗;交通便利;商务圈；景观 ;（填入内容分号分隔，选填）").build()
        );
        // 输出
        ExcelUtils.write(response, "房间导入模板.xls", "用户列表", RoomImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入房间")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:room:create')")
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        List<RoomImportExcelVO> list = ExcelUtils.read(file, RoomImportExcelVO.class);
        return success(roomService.importExcel(list));
    }

    @GetMapping("/getRoomListByLayerId")
    @Operation(summary = "获得项目房间列表")
    @PreAuthorize("@ss.hasPermission('bus:room:query')")
    public CommonResult<List<RoomRespVO>> getRoomListByLayerId(@Valid RoomPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomDO> list = roomService.getRoomPage(pageReqVO).getList();
        List<RoomRespVO> roomRespVOS = new ArrayList<>();
        if (CollUtil.isNotEmpty(list)) {
            for (RoomDO roomDO : list) {
                RoomRespVO oneRoom = roomService.getOneRoom(roomDO.getId());
                roomRespVOS.add(oneRoom);
            }
        }
        return success(roomRespVOS);
    }


    @GetMapping("/resourceList")
    @Operation(summary = "收银台-资源搜索")
    @Parameters({
            @Parameter(name = "roomName", description = "房间号", required = false),
            @Parameter(name = "roomAliasId", description = "房间管理编号", required = false)
    })
    @PreAuthorize("@ss.hasPermission('bus:room:resourceList')")
    public CommonResult<?> resourceList(@Valid RoomPageReqVO pageReqVO) {
        if (StringUtils.isEmpty(pageReqVO.getRoomName()) && StringUtils.isEmpty(pageReqVO.getRoomAliasId())) {
            throw new ServiceException(406, "请传入正确的房间号或者房间管理编号");
        }
        return success(roomService.resourceList(pageReqVO));
    }
}