package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产盘点员工记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyInventoryEmployeePageReqVO extends PageParam {

    @Schema(description = "机构id", example = "24926")
    private Long orgId;

    @Schema(description = "盘点清单id", example = "8453")
    private Long inventoryId;

    @Schema(description = "盘点员工id", example = "8609")
    private Long userId;

    @Schema(description = "资产分类id")
    private String typeIds;

    @Schema(description = "部门id")
    private String departmentIds;

    @Schema(description = "位置id")
    private String positionIds;

    @Schema(description = "支付状态", example = "2")
    private String propertyStatus;

    @Schema(description = "管理员id", example = "13174")
    private String adminId;

    @Schema(description = "购置方式", example = "1")
    private String purchaseType;

    @Schema(description = "完成状态 1是0否", example = "2")
    private Integer status;

    @Schema(description = "是否是全部权限")
    private Integer isAll;

    @Schema(description = "每个用户的范围回显冗余字段")
    private String exterData;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}