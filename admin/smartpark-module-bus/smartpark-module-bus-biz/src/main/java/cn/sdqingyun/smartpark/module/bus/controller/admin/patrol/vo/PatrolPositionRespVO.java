package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 巡检点位数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolPositionRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10598")
    private Long id;

    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "巡检点编码")
    @ExcelProperty("巡检点编码")
    private String number;

    @Schema(description = "巡检点名称", example = "李四")
    @ExcelProperty("巡检点名称")
    private String name;

    @Schema(description = "资产位置id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30735")
    private Long positionId;

    @Schema(description = "资产位置名称", example = "王五")
    @ExcelProperty("资产位置名称")
    private String positionName;

    @Schema(description = "资产位置父级id集合（多个值使用逗号(,)隔开）", example = "1,2,3,4")
    private String positionParentIds;

    @Schema(description = "nfc卡号ID", example = "22491")
    private String nfcCardId;

    @Schema(description = "巡检表单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7330")
    private Long formId;

    /**
     * 巡检表单名称
     */
    @ExcelProperty("巡检内容")
    @Schema(description = "巡检内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "7330")
    @TableField(exist = false)
    private String formTitle;

    @Schema(description = "巡检点图片")
    private String images;



    @Schema(description = "绑定的资产Id")
    private String propertyId;

    @Schema(description = "启用状态;0=禁用,1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;

    @ExcelProperty("启用状态")
    @TableField(exist = false)
    private String statusName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}