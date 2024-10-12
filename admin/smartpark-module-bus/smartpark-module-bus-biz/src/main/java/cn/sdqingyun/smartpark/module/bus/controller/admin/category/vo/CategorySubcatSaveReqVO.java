package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 工单分类子类信息新增/修改 Request VO")
@Data
public class CategorySubcatSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18040")
    private Long id;

    @Schema(description = "机构ID", example = "2482")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "子类名称", example = "赵六")
    private String name;

    @Schema(description = "工单大类id", example = "25667")
    private Long categoryId;

    @Schema(description = "子类绑定的岗位信息json")
    private String stationJson;

    @Schema(description = "部门id", example = "31943")
    private Long departmentId;

    @Schema(description = "标签id集合")
    private String labelIds;

    @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "提交工单面向类型;1=所有人,2=租客,3=工作人员", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String submitType;

    @Schema(description = "启用状态;0=否;1=是", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;

}