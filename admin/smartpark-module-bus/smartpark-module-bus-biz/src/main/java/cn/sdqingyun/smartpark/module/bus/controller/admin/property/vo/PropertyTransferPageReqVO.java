package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产调拨分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyTransferPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "22525")
    private Long orgId;

    @Schema(description = "资产id")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "1")
    private Integer status;

    @Schema(description = "调出管理员uid", example = "27333")
    private Long outAdminUid;

    @Schema(description = "调入管理员uid", example = "22111")
    private Long inAdminUid;

    @Schema(description = "调入管理员姓名", example = "22111")
    private String inAdminUidName;

    @Schema(description = "调入位置id", example = "18013")
    private Long inLocationId;

    @Schema(description = "处理人", example = "22115")
    private Long operateUid;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "借出备注", example = "你说的对")
    private String remark;

    @Schema(description = "操作人uid", example = "4451")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "22593")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}