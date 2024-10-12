package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 账单开据记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillReceiptLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22715")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "开票批次凭证")
    @ExcelProperty("开票批次凭证")
    private String taskKey;

    @Schema(description = "收据模板id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4161")
    @ExcelProperty("收据模板id")
    private Long templateId;

    @Schema(description = "所选账单id集合")
    @ExcelProperty("所选账单id集合")
    private String billIds;

    @Schema(description = "所选子账单id集合")
    @ExcelProperty("所选子账单id集合")
    private String subbillIds;

    @Schema(description = "收据账单信息(包含合并账单)")
    @ExcelProperty("收据账单信息(包含合并账单)")
    private String receiptJson;

    @Schema(description = "本次选择的收据账单组数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("本次选择的收据账单组数")
    private Integer chooseNum;

    @Schema(description = "本次选择的账单开据金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("本次选择的账单开据金额")
    private BigDecimal chooseAmount;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}