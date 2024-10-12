package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产变更领用人分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyChangePageReqVO extends PageParam {

    @Schema(description = "机构id", example = "29372")
    private Long orgId;

    @Schema(description = "资产id")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "变更后使用人uid", example = "16926")
    private Long afterUseUid;

    @Schema(description = "变更后使用部门id", example = "20832")
    private Long afterUseDepartmentId;

    @Schema(description = "变更时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] afterTime;

    @Schema(description = "处理人", example = "21094")
    private Long operateUid;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "变更说明", example = "随便")
    private String remark;

    @Schema(description = "操作人uid", example = "3815")
    private Long cuserUid;

    @Schema(description = "选择的派发单据编号property_handout中的id", example = "30010")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}