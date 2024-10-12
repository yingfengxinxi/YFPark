package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 巡检点位数据分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatrolPositionPageReqVO extends PageParam {

    @Schema(description = "机构Id", example = "28237")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "巡检点编码")
    private String number;

    @Schema(description = "巡检点名称", example = "李四")
    private String name;

    @Schema(description = "资产位置id", example = "30735")
    private Long positionId;

    @Schema(description = "资产位置名称", example = "王五")
    private String positionName;

    @Schema(description = "资产位置父级id集合（多个值使用逗号(,)隔开）", example = "1,2,3,4")
    private String positionParentIds;

    @Schema(description = "nfc卡号ID", example = "22491")
    private String nfcCardId;

    @Schema(description = "巡检表单id", example = "7330")
    private Long formId;

    @Schema(description = "巡检点图片")
    private String images;

    @Schema(description = "绑定的资产Id")
    private String propertyId;

    @Schema(description = "启用状态;0=禁用,1=启用", example = "1")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}