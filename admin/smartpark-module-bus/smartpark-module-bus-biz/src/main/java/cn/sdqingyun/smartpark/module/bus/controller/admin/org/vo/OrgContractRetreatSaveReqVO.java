package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 机构合同退租新增/修改 Request VO")
@Data
public class OrgContractRetreatSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22942")
    private Long id;

    @Schema(description = "租客id", example = "18663")
    private Long ownerId;

    @Schema(description = "合同id", example = "16647")
    private Long contractId;

    @Schema(description = "所应用的退租合同模板id", example = "30956")
    private Long applyTemplateId;

    @Schema(description = "房源信息")
    private String buildBind;

    @Schema(description = "历史账单信息")
    private String billInfo;

    @Schema(description = "保证金")
    private String bondInfo;

    @Schema(description = "费用总计")
    private String totalCost;

    @Schema(description = "退租类型", example = "2")
    private Integer retreatType;

    @Schema(description = "退租原因", example = "不喜欢")
    private String retreatReason;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "根据所选模板生成的退租合同")
    private String applyContract;

    @Schema(description = "退租日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date retreatDate;

    @Schema(description = "工商注册地址变更日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date bccChangeDate;

    @Schema(description = "协议签订日期(申请退租日期)")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date applyRetreatDate;

}