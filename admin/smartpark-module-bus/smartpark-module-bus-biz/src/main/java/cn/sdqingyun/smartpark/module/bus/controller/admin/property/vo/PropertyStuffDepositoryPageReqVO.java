package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材档案信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffDepositoryPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "7260")
    private Long orgId;

    @Schema(description = "仓库编号")
    private String number;

    @Schema(description = "仓库名称", example = "李四")
    private String name;

    @Schema(description = "上级仓库id", example = "25605")
    private Long parentId;

    @Schema(description = "耗材仓库状态;1=启用,0=禁用", example = "1")
    private Short status;

    @Schema(description = "上下层级关系")
    private String level;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "序号")
    private Integer sort;

    @Schema(description = "操作人uid", example = "3632")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "29032")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}