package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 耗材业务调拨 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffTransferRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16719")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9169")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String processNumber;

    @Schema(description = "调出管理员", requiredMode = Schema.RequiredMode.REQUIRED, example = "11997")
    @ExcelProperty("调出管理员")
    private Long outAdminUid;

    @Schema(description = "调入管理员", requiredMode = Schema.RequiredMode.REQUIRED, example = "32348")
    @ExcelProperty("调入管理员")
    private Long inAdminUid;

    @Schema(description = "调出仓库", requiredMode = Schema.RequiredMode.REQUIRED, example = "29406")
    @ExcelProperty("调出仓库")
    private Long outDepositoryId;

    @Schema(description = "调入仓库", requiredMode = Schema.RequiredMode.REQUIRED, example = "23490")
    @ExcelProperty("调入仓库")
    private Long inDepositoryId;

    @Schema(description = "合计数量")
    @ExcelProperty("合计数量")
    private BigDecimal totalNum;

    @Schema(description = "合计金额", example = "31682")
    @ExcelProperty("合计金额")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("单据状态;1=进行中;2=已打回;3=已撤销;4=已完结")
    private Short status;

    @Schema(description = "调拨备注", example = "你猜")
    @ExcelProperty("调拨备注")
    private String remark;

    @Schema(description = "处理人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "17329")
    @ExcelProperty("处理人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "14488")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "调出管理员")
    private String outAdminName;

    @Schema(description = "调入管理员")
    private String inAdminName;

    @Schema(description = "调出仓库")
    private String outDepositorName;

    @Schema(description = "调入仓库")
    private String inDepositoryName;
}