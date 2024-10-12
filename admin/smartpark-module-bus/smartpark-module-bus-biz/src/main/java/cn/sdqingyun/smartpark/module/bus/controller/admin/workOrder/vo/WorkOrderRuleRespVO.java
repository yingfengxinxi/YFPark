package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单规则设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WorkOrderRuleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23572")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "1517")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "规则名称", example = "李四")
    @ExcelProperty("规则名称")
    private String name;

    @Schema(description = "抢单数上限;/个", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("抢单数上限;/个")
    private Integer snatchLimit;

    @Schema(description = "抢单前置时长;/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("抢单前置时长;/分钟")
    private Integer preposeTime;

    @Schema(description = "抢单限制时长;单位/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("抢单限制时长;单位/分钟")
    private Integer robTime;

    @Schema(description = "可退款时长;/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("可退款时长;/分钟")
    private Integer refundTime;

    @Schema(description = "取消订单时长;/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("取消订单时长;/分钟")
    private Integer cancelTime;

    @Schema(description = "重新打开时长;/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("重新打开时长;/分钟")
    private Integer restartTime;

    @Schema(description = "绑定的楼宇信息json")

    private String buildBind;

    @ExcelProperty("绑定的楼宇")
    private String buildNames;

    @Schema(description = "是否为默认配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否为默认配置")
    private String isDefault;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    //是否可以删除数据
    @Schema(description = "是否可以删除数据0=否1=是")
    @ExcelProperty("是否可以删除数据0=否1=是")
    private String isDelete;
}