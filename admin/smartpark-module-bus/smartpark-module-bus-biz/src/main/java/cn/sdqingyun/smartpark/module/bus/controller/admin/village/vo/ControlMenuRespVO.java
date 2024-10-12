package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目租控管理菜单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ControlMenuRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2627")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("菜单名称")
    private String name;

    @Schema(description = "菜单图标（http开头视为图片）")
    @ExcelProperty("菜单图标（http开头视为图片）")
    private String icon;

    @Schema(description = "菜单选中图标（http开头视为图片）")
    @ExcelProperty("菜单选中图标（http开头视为图片）")
    private String activeIcon;

    @Schema(description = "按钮集合类型，暂定 village、build、room、villageUser", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("按钮集合类型，暂定 village、build、room、villageUser")
    private String menuType;

    @Schema(description = "路由别名，http开头即认定为iframe")
    @ExcelProperty("路由别名，http开头即认定为iframe")
    private String alias;

    @Schema(description = "菜单排序，越高越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("菜单排序，越高越前")
    private Integer sort;

    @Schema(description = "微服务提供")
    @ExcelProperty("微服务提供")
    private String micro;

    @Schema(description = "前端服务")
    @ExcelProperty("前端服务")
    private String app;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}