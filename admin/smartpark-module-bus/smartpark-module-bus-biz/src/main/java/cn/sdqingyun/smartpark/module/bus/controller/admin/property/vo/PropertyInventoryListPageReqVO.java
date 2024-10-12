package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产盘点清单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyInventoryListPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "30094")
    private Long orgId;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "盘点清单名称", example = "芋艿")
    private String inventoryName;

    @Schema(description = "盘点清单状态 0 盘点中 1盘点审批中 2已完结", example = "2")
    private Integer inventoryStatus;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "盘点人", example = "27867")
    private String inventoryUid;

    @Schema(description = "资产限制字段")
    private String restrictField;

    @Schema(description = "盘点范围（使用部门）")
    private String departmentIds;

    @Schema(description = "盘点范围（资产分类）")
    private String typeIds;

    @Schema(description = "盘点范围（资产位置）")
    private String positionIds;

    @Schema(description = "资产状态", example = "2")
    private String propertyStatus;

    @Schema(description = "主付状态", example = "2")
    private Integer initiativePayStatus;

    @Schema(description = "管理员id", example = "29507")
    private String adminId;

    @Schema(description = "所属公司")
    private Long company;

    @Schema(description = "购置方式", example = "1")
    private String purchaseType;

    @Schema(description = "仅扫码盘点的分类", example = "1")
    private String codeInventoryType;

    @Schema(description = "不可批量盘点的分类", example = "2")
    private String notbreachInventoryType;

    @Schema(description = "ntake_pic:仅上传拍照 ntask_pic_after:在原有基础在补充", example = "1")
    private String uploadType;

    @Schema(description = "是否批量分配 1是0否")
    private Integer isBatch;

    @Schema(description = "单个分配 分配范围")
    private String distributionRange;

    @Schema(description = "额外数据、部门和分类的回显")
    private String exterData;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}