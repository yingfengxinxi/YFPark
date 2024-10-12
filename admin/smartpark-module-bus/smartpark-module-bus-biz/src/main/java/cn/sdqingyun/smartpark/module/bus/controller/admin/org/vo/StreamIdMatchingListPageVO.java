package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/8/24 16:42
 */
@Data
public class StreamIdMatchingListPageVO extends PageParam {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "对方名称")
    private String companyName;


    @Schema(description = "费用类型")
    private String costName;


    @Schema(description = "费用周期【开始时间】")
    private String payStartDate;

    @Schema(description = "费用周期【结束时间】")
    private String payEndDate;


    @Schema(description = "应交金额")
    private String receivable;


    @Schema(description = "匹配金额")
    private String matchPrice;

    @Schema(description = "匹配日期")
    private String matchDate;

    @Schema(description = "取消匹配日期")
    private String cancelMatchDate;

    @Schema(description = "匹配状态")
    private String matchStatus;
}
