package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产保养记录新增/修改 Request VO")
@Data
public class PropertyMaintainSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26509")
    private Long id;

    @Schema(description = "机构id", example = "11036")
    private Long orgId;

    @Schema(description = "保养人uid", example = "11772")
    private Long maintainUid;

    @Schema(description = "保养人所在部门", example = "30809")
    private Long departmentId;

    @Schema(description = "保养人所在部门名称", example = "赵六")
    private String departmentName;

    @Schema(description = "保养项目id", example = "23931")
    private String maintainVillageId;

    @Schema(description = "资产id集合json")
    private String propertyIds;

    @Schema(description = "资产集合json")
    private String propertyData;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "保养总金额", example = "30690")
    private BigDecimal maintainTotalPrice;

    @Schema(description = "保养时间")
    private LocalDateTime maintainDate;

    @Schema(description = "下次保养时间")
    private LocalDateTime nextMaintainDate;

    @Schema(description = "处理人", example = "22505")
    private Long operateUid;

    @Schema(description = "处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "保养备注", example = "你说的对")
    private String remark;

    @Schema(description = "操作人", example = "5222")
    private Long cuserUid;

    @Schema(description = "修改人", example = "16190")
    private Long muserUid;

}