package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产仓库信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyDepositoryPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "29787")
    private Long orgId;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "入库处理人", example = "31171")
    private Long operateUid;

    @Schema(description = "入库处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "入库备注", example = "随便")
    private String remark;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "操作人uid", example = "31948")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "18639")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}