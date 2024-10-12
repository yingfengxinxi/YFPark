package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目租控管理菜单新增/修改 Request VO")
@Data
public class ControlMenuSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2627")
    private Long id;

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "菜单名称不能为空")
    private String name;

    @Schema(description = "菜单图标（http开头视为图片）")
    private String icon;

    @Schema(description = "菜单选中图标（http开头视为图片）")
    private String activeIcon;

    @Schema(description = "按钮集合类型，暂定 village、build、room、villageUser", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "按钮集合类型，暂定 village、build、room、villageUser不能为空")
    private String menuType;

    @Schema(description = "路由别名，http开头即认定为iframe")
    private String alias;

    @Schema(description = "菜单排序，越高越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "菜单排序，越高越前不能为空")
    private Integer sort;

    @Schema(description = "微服务提供")
    private String micro;

    @Schema(description = "前端服务")
    private String app;

}