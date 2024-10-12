package cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 发票模板配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InvoiceTemplateRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2652")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "发票模板名称", example = "李四")
    @ExcelProperty("发票模板名称")
    private String name;

    @Schema(description = "售方id", example = "5249")
    private Long sellerId;

    /**
     * 售方公司名称
     */
    @Schema(description = "售方公司名称", example = "5249")
    @ExcelProperty("售方公司名称")
    @TableField(exist = false)
    private String companyName;

    @Schema(description = "税率规则id;多个id使用逗号隔开(1,2,3)")
    @ExcelProperty("税率规则id;多个id使用逗号隔开(1,2,3)")
    private String taxRule;

    @Schema(description = "是否开通电子发票 0不开通", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否开通电子发票 0不开通")
    private String eInvoice;

    @Schema(description = "税票通配置信息-appid", example = "14490")
    @ExcelProperty("税票通配置信息-appid")
    private String sptAppid;

    @Schema(description = "税票通配置信息-appsecret")
    @ExcelProperty("税票通配置信息-appsecret")
    private String sptAppsecret;

    @Schema(description = "税票通配置信息-税号")
    @ExcelProperty("税票通配置信息-税号")
    private String sptNumber;

    @Schema(description = "后台账单缴费自动开票（1是/0否）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("后台账单缴费自动开票（1是/0否）")
    private String orgAuto;

    @Schema(description = "用户账单缴费自动开票（1是/0否）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("用户账单缴费自动开票（1是/0否）")
    private String userAuto;

    @Schema(description = "应用楼宇id;多个id使用逗号隔开(1,2,3)")
    @ExcelProperty("应用楼宇id;多个id使用逗号隔开(1,2,3)")
    private String buildBind;

    @Schema(description = "状态;0=禁用；1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态;0=禁用；1=启用")
    private String status;

    @Schema(description = "模板是否开启审批;0=否1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("模板是否开启审批;0=否1=是")
    private String isApproval;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}