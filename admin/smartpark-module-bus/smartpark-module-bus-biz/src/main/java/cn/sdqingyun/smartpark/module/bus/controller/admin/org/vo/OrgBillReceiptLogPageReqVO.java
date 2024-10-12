package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 账单开据记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgBillReceiptLogPageReqVO extends PageParam {

    @Schema(description = "开票批次凭证")
    private String taskKey;

    @Schema(description = "收据模板id", example = "4161")
    private Long templateId;

    @Schema(description = "所选账单id集合")
    private String billIds;

    @Schema(description = "所选子账单id集合")
    private String subbillIds;

    @Schema(description = "收据账单信息(包含合并账单)")
    private String receiptJson;

    @Schema(description = "本次选择的收据账单组数")
    private Integer chooseNum;

    @Schema(description = "本次选择的账单开据金额")
    private BigDecimal chooseAmount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}