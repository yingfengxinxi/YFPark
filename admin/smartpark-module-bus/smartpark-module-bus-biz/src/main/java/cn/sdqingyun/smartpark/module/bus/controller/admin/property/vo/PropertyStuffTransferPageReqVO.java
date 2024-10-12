package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材业务调拨分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffTransferPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "9169")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "调出管理员", example = "11997")
    private Long outAdminUid;

    @Schema(description = "调入管理员", example = "32348")
    private Long inAdminUid;

    @Schema(description = "调出仓库", example = "29406")
    private Long outDepositoryId;

    @Schema(description = "调入仓库", example = "23490")
    private Long inDepositoryId;

    @Schema(description = "合计数量")
    private BigDecimal totalNum;

    @Schema(description = "合计金额", example = "31682")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", example = "1")
    private Short status;

    @Schema(description = "调拨备注", example = "你猜")
    private String remark;

    @Schema(description = "处理人uid", example = "17329")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "14488")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}