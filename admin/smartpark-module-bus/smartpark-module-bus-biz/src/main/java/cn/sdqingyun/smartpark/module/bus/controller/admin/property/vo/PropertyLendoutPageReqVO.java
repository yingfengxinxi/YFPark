package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产借出分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyLendoutPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "11387")
    private Long orgId;

    @Schema(description = "项目id", example = "18099")
    private Long villageId;

    @Schema(description = "楼宇id", example = "25285")
    private Long buildId;

    @Schema(description = "房间id", example = "30850")
    private Long roomId;

    @Schema(description = "资产id集合json")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "数据类型;1=借出;2=归还", example = "1")
    private Short type;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "借用人uid", example = "7141")
    private Long lendUid;

    @Schema(description = "借用部门id", example = "2325")
    private Long departmentId;

    @Schema(description = "借出时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] lendTime;

    @Schema(description = "预计归还时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expectRevertTime;

    @Schema(description = "处理人", example = "10181")
    private Long operateUid;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "借出备注", example = "你猜")
    private String remark;

    @Schema(description = "操作人uid", example = "22583")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "29507")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}