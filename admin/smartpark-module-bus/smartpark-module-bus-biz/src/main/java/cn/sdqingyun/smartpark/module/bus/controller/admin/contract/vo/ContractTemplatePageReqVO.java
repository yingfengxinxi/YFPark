package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构合同模板配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractTemplatePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "7157")
    private Long orgId;

    @Schema(description = "水印模板id", example = "14254")
    private Long watermarkId;

    @Schema(description = "应用场景;普通合同(normal)/退租合同(retreat)等等")
    private String scene;

    @Schema(description = "模板名称", example = "李四")
    private String templateName;

    @Schema(description = "模板文件")
    private String templatePath;

    @Schema(description = "模板主条款信息")
    private String basicClause;

    @Schema(description = "模板合同编号规则")
    private String numberRule;

    @Schema(description = "滞纳金条款")
    private String latefeeClause;

    @Schema(description = "综合条款")
    private String multipleClause;

    @Schema(description = "楼宇id,多个id使用逗号隔开")
    private String relationBuilds;

    @Schema(description = "模板变量自定义表头集合")
    private String tableFields;

    @Schema(description = "永久标识;1=永久")
    private String dateFlag;

    @Schema(description = "过期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;

    @Schema(description = "模板使用状态;1=启用;0=关闭", example = "1")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}