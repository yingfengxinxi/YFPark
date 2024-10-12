package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产单据审批记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyApprovePageReqVO extends PageParam {

    @Schema(description = "机构id", example = "7510")
    private Long orgId;

    @Schema(description = "项目id", example = "14153")
    private Long villageId;

    @Schema(description = "楼宇id", example = "2597")
    private Long buildId;

    @Schema(description = "房间id", example = "10603")
    private String roomId;

    @Schema(description = "审批单据编号")
    private String approveNumber;

    @Schema(description = "关联单据编号")
    private String relationNumber;

    @Schema(description = "关联单据类型", example = "2")
    private String relationType;

    @Schema(description = "1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);", example = "2")
    private Integer status;

    @Schema(description = "审批业务类型", example = "1")
    private String processType;

    @Schema(description = "审批内容")
    private String content;

    @Schema(description = "发起时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] launchTime;

    @Schema(description = "审批完结时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] approveOvertime;

    @Schema(description = "预计归还时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expectRevertTime;

    @Schema(description = "原因备注", example = "你说的对")
    private String remark;

    @Schema(description = "申请部门id", example = "21028")
    private Long departmentId;

    @Schema(description = "交接后使用人uid", example = "24634")
    private Long handoverUid;

    @Schema(description = "交接后使用部门id", example = "26866")
    private Long handoverDepartmentId;

    @Schema(description = "审批应用流程id", example = "12932")
    private Long approvalId;

    @Schema(description = "提交人uid", example = "16222")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "9663")
    private Long muserUid;

    @Schema(description = "结束盘点之后的操作1已盘资产更新内容、2盘盈资产自动入库、3未盘资产自动盘亏")
    private String endInventoryAfter;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}