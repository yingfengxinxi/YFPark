package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 智能表参数配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BailingOrgConfigPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "8904")
    private Long orgId;

    @Schema(description = "类型【数据字典值BAILING_ORG_CONFIG】", example = "芋艿")
    private String type;

    @Schema(description = "名称", example = "芋艿")
    private String key;

    @Schema(description = "值")
    private String value;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}