package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目租控管理菜单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ControlMenuPageReqVO extends PageParam {

    @Schema(description = "菜单名称", example = "王五")
    private String name;

    @Schema(description = "菜单图标（http开头视为图片）")
    private String icon;

    @Schema(description = "菜单选中图标（http开头视为图片）")
    private String activeIcon;

    @Schema(description = "按钮集合类型，暂定 village、build、room、villageUser", example = "1")
    private String menuType;

    @Schema(description = "路由别名，http开头即认定为iframe")
    private String alias;

    @Schema(description = "菜单排序，越高越前")
    private Integer sort;

    @Schema(description = "微服务提供")
    private String micro;

    @Schema(description = "前端服务")
    private String app;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}