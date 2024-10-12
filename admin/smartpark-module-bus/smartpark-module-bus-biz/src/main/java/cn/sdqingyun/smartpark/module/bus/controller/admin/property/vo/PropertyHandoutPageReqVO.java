package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产派发/退库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyHandoutPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "12362")
    private Long orgId;

    @Schema(description = "项目id", example = "30963")
    private Long villageId;

    @Schema(description = "楼宇id", example = "25748")
    private Long buildId;

    @Schema(description = "房间id", example = "4303")
    private Long roomId;

    @Schema(description = "资产id集合json")
    private String propertyIds;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "数据类型;1=派发;2=退库", example = "1")
    private Short type;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "单据状态", example = "1")
    private Integer status;

    @Schema(description = "所选部门id", example = "13761")
    private Long departmentId;

    @Schema(description = "领用人", example = "29669")
    private Long receiveUid;

    @Schema(description = "派发日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] handoutTime;

    @Schema(description = "退库日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] returnTime;

    @Schema(description = "处理人", example = "31773")
    private Long operateUid;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "处理备注", example = "你说的对")
    private String remark;

    @Schema(description = "操作人uid", example = "5575")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "12201")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}