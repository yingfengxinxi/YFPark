package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材业务流程分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffProcessPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "280")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "流程单据编号")
    private String processNumber;

    @Schema(description = "业务流程类型", example = "2")
    private String processType;

    @Schema(description = "耗材物料id", example = "18478")
    private Long stuffId;

    @Schema(description = "所属分类id", example = "30565")
    private Long categoryId;

    @Schema(description = "所属仓库id", example = "17783")
    private Long depositoryId;

    @Schema(description = "调入仓库id", example = "23099")
    private Long inDepositoryId;

    @Schema(description = "物料名称", example = "王五")
    private String name;

    @Schema(description = "物料编码")
    private String number;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格型号", example = "赵六")
    private String modelName;

    @Schema(description = "计量单位")
    private String meterUnit;

    @Schema(description = "入库单价", example = "453")
    private BigDecimal price;

    @Schema(description = "数量")
    private BigDecimal num;

    @Schema(description = "总价", example = "31872")
    private BigDecimal totalPrice;

    @Schema(description = "物料照片;支持多张", example = "https://xxx")
    private String imageUrl;

    @Schema(description = "耗材物料信息状态", example = "2")
    private Short status;

    @Schema(description = "派发数量")
    private BigDecimal handoutNum;

    @Schema(description = "退库数量")
    private BigDecimal retreatNum;

    @Schema(description = "其他")
    private String extra;

    @Schema(description = "操作人uid", example = "11166")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "19166")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}