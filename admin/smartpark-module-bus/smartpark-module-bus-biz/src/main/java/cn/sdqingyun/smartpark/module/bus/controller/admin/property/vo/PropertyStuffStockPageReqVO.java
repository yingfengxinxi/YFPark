package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材即时库存分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffStockPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "29345")
    private Long orgId;

    @Schema(description = "耗材物料id", example = "18539")
    private Long stuffId;

    @Schema(description = "仓库id;需同步该信息", example = "19709")
    private Long depositoryId;

    @Schema(description = "入库流程编号")
    private String processCode;

    @Schema(description = "可用库存")
    private BigDecimal usableNum;

    @Schema(description = "冻结库存")
    private BigDecimal frozenNum;

    @Schema(description = "总库存")
    private BigDecimal totalNum;

    @Schema(description = "总金额", example = "15952")
    private BigDecimal totalPrice;

    @Schema(description = "耗材定价", example = "30366")
    private BigDecimal chargePrice;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "其他")
    private String extra;

    @Schema(description = "是否达到安全库存上限;默认安全")
    private Short isStockUp;

    @Schema(description = "是否达到安全库存下限;默认安全")
    private Short isStockLower;

    @Schema(description = "操作人uid", example = "24099")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "4194")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "可用库存开始")
    private BigDecimal usableNumStart;
    @Schema(description = "可用库存结束")
    private BigDecimal usableNumEnd;

    @Schema(description = "总库存开始")
    private BigDecimal totalNumStart;
    @Schema(description = "总库存结束")
    private BigDecimal totalNumEnd;
}