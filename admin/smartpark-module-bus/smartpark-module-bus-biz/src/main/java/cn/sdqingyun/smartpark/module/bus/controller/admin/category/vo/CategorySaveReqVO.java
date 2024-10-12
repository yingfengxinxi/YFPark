package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 工单分类配置新增/修改 Request VO")
@Data
public class CategorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15398")
    private Long id;

    @Schema(description = "机构ID", example = "10955")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "大类名称", example = "芋艿")
    private String name;

    @Schema(description = "运作模式;1=派单+抢单2=派单", example = "1")
    private String type;

    @Schema(description = "完成时限", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer overHour;

    @Schema(description = "是否允许转单;1=开启,0=关闭", requiredMode = Schema.RequiredMode.REQUIRED)
    private String hasChange;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "应用楼宇json")
    private String buildBind;

    @Schema(description = "工单子类id集合")
    private String subcatIds;

}