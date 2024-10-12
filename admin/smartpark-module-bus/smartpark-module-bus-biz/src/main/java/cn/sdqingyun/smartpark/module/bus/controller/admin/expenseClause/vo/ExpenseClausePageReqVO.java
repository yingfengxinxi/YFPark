package cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 机构合同费用条款附加分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExpenseClausePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "11950")
    private Integer orgId;

    @Schema(description = "基础合同,合同主表id", example = "12560")
    private Integer contractId;

    @Schema(description = "条款id", example = "26869")
    private Integer clauseId;

    @Schema(description = "条款类型(属性);1=租赁条款;2=物业条款", example = "2")
    private Integer clauseType;

    @Schema(description = "费用条款属性")
    private String attribute;

    @Schema(description = "基本条款")
    private String basicClause;

    @Schema(description = "保证金条款")
    private String bondClause;

    @Schema(description = "税率规则条款")
    private String taxClause;

    @Schema(description = "综合条款(租期条款/物业费条款/经营管理费/停车费/)")
    private String multipleClause;

    @Schema(description = "递增条款")
    private String incrementClause;

    @Schema(description = "优惠条款")
    private String discountClause;

    @Schema(description = "备注条款")
    private String remarkClause;

    @Schema(description = "租金明细报表json")
    private String reportDetail;

    @Schema(description = "优惠金额", example = "10626")
    private BigDecimal discountPrice;

    @Schema(description = "优惠信息")
    private String discountData;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}