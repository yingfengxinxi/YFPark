package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业务流程单据关联资产分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyProcessDataPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "11914")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "流程单据编号")
    private String processNumber;

    @Schema(description = "资产id", example = "12453")
    private Long propertyId;

    @Schema(description = "资产编号")
    private String propertyNumber;

    @Schema(description = "资产分类", example = "1")
    private Integer type;

    @Schema(description = "资产名称", example = "张三")
    private String name;

    @Schema(description = "资产状态", example = "2")
    private Integer status;

    @Schema(description = "所在位置配置信息id", example = "24517")
    private Long positionId;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "型号", example = "赵六")
    private String modelName;

    @Schema(description = "目标房源")
    private String buildBind;

    @Schema(description = "资产保养上传的文件")
    private String maintainFile;

    @Schema(description = "资产保养上传的金额", example = "17149")
    private BigDecimal maintainPrice;

    @Schema(description = "操作人uid", example = "23598")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "14025")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}