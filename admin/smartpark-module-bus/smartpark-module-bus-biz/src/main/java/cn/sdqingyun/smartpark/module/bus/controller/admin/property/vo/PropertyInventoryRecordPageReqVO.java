package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产盘点记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyInventoryRecordPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "7485")
    private Long orgId;

    @Schema(description = "盘点清单id", example = "2469")
    private Long inventoryId;

    @Schema(description = "资产id", example = "576")
    private Long propertyId;

    @Schema(description = "资产分类id", example = "17141")
    private Long typeId;

    @Schema(description = "0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束", example = "2")
    private Integer status;

    @Schema(description = "资产状态", example = "2")
    private Integer propertyStatus;

    @Schema(description = "是否在盘点范围之内")
    private Integer isRange;

    @Schema(description = "是否修改资产信息 0否 1是")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] isUpdate;

    @Schema(description = "盘点备注", example = "你说的对")
    private String remark;

    @Schema(description = "盘点图片")
    private String image;

    @Schema(description = "盘点标签")
    private String tag;

    @Schema(description = "盘点时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] inventoryTime;

    @Schema(description = "盘点人", example = "19108")
    private String inventoryUid;

    @Schema(description = "当前位置", example = "11648")
    private Long positionId;

    @Schema(description = "处理人", example = "5098")
    private Long handleUid;

    @Schema(description = "资产所属部门id", example = "26725")
    private Long departmentId;

    @Schema(description = "资产盘点信息")
    private String propertyInfo;

    @Schema(description = "资产名称", example = "李四")
    private String name;

    @Schema(description = "资产编码")
    private String propertyNumber;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}