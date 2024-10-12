package cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 机构合同费用条款附加 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ExpenseClauseRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10589")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11950")
    @ExcelProperty("机构ID")
    private Integer orgId;

    @Schema(description = "基础合同,合同主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12560")
    @ExcelProperty("基础合同,合同主表id")
    private Integer contractId;

    @Schema(description = "条款id", example = "26869")
    @ExcelProperty("条款id")
    private Integer clauseId;

    @Schema(description = "条款类型(属性);1=租赁条款;2=物业条款", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("条款类型(属性);1=租赁条款;2=物业条款")
    private Integer clauseType;

    @Schema(description = "费用条款属性")
    @ExcelProperty("费用条款属性")
    private String attribute;


    @Schema(description = "基本条款")
    @ExcelProperty("基本条款")
    private String basicClause;

    @Schema(description = "保证金条款")
    @ExcelProperty("保证金条款")
    private String bondClause;

    @Schema(description = "税率规则条款")
    @ExcelProperty("税率规则条款")
    private String taxClause;

    @Schema(description = "综合条款(租期条款/物业费条款/经营管理费/停车费/)")
    @ExcelProperty("综合条款(租期条款/物业费条款/经营管理费/停车费/)")
    private String multipleClause;

    @Schema(description = "递增条款")
    @ExcelProperty("递增条款")
    private String incrementClause;

    @Schema(description = "优惠条款")
    @ExcelProperty("优惠条款")
    private String discountClause;

    @Schema(description = "备注条款")
    @ExcelProperty("备注条款")
    private String remarkClause;

    @Schema(description = "租金明细报表json")
    @ExcelProperty("租金明细报表json")
    private String reportDetail;

    @Schema(description = "优惠金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "10626")
    @ExcelProperty("优惠金额")
    private BigDecimal discountPrice;

    @Schema(description = "优惠信息")
    @ExcelProperty("优惠信息")
    private String discountData;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}