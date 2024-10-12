package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构合同模板配置新增/修改 Request VO")
@Data
public class ContractTemplateSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3761")
    private Long id;

    @Schema(description = "机构ID", example = "7157")
    private Long orgId;

    @Schema(description = "水印模板id", example = "14254")
    private Long watermarkId;

    @Schema(description = "应用场景;普通合同(normal)/退租合同(retreat)等等")
    private String scene;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "模板名称不能为空")
    private String templateName;

    @Schema(description = "模板文件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模板文件不能为空")
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
    @NotEmpty(message = "楼宇不能为空")
    private String relationBuilds;

    @Schema(description = "模板变量自定义表头集合")
    private String tableFields;

    @Schema(description = "永久标识;1=永久", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "永久标识;1=永久不能为空")
    private String dateFlag;

    @Schema(description = "过期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;

    @Schema(description = "模板使用状态;1=启用;0=关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "模板使用状态;1=启用;0=关闭不能为空")
    private String status;

}