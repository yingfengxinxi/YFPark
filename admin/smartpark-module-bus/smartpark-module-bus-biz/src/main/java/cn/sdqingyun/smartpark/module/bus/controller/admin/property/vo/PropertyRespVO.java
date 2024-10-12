package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20507")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "24074")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "资产编码")
    @ExcelProperty("资产编码")
    private String propertyNumber;

    @Schema(description = "资产标签链接")
    @ExcelProperty("资产标签链接")
    private String labelLink;

    @Schema(description = "资产分类", example = "1")
    @ExcelProperty("资产分类")
    private Long type;

    @Schema(description = "资产分类名称")
    private String categoryName;

    @Schema(description = "资产名称", example = "李四")
    @ExcelProperty("资产名称")
    private String name;

    @Schema(description = "资产状态")
    private Integer status;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "型号", example = "赵六")
    @ExcelProperty("型号")
    private String modelName;

    @Schema(description = "设备序列号")
    @ExcelProperty("设备序列号")
    private String deviceCode;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "管理员id")
    @ExcelProperty("管理员id")
    private Long adminId;

    @Schema(description = "管理员名称")
    @ExcelProperty("管理员名称")
    private String adminName;

    @Schema(description = "目标房源项目id", example = "3602")
    @ExcelProperty("目标房源项目id")
    private Long villageId;

    @Schema(description = "目标房源楼宇id", example = "16252")
    @ExcelProperty("目标房源楼宇id")
    private Long buildId;

    @Schema(description = "目标房源房间id", example = "28140")
    @ExcelProperty("目标房源房间id")
    private String roomId;

    @Schema(description = "绑定的目标房源json")
    @ExcelProperty("绑定的目标房源json")
    private String buildBind;

    @Schema(description = "所在位置配置信息id", example = "10141")
    @ExcelProperty("所在位置配置信息id")
    private Long positionId;

    @Schema(description = "资产操作时的位置信息", example = "王五")
    @ExcelProperty("资产操作时的位置信息")
    private String positionName;

    @Schema(description = "购置时间")
    @ExcelProperty("购置时间")
    private LocalDateTime purchaseTime;

    @Schema(description = "购置方式", example = "2")
    @ExcelProperty("购置方式")
    private Integer purchaseType;

    @Schema(description = "购置金额(含税)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("购置金额(含税)")
    private BigDecimal purchaseAmount;

    @Schema(description = "入库时间")
    @ExcelProperty("入库时间")
    private LocalDateTime stockTime;

    @Schema(description = "预计使用期限(月)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预计使用期限(月)")
    private Long expectMonths;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "图片hash值")
    @ExcelProperty("图片hash值")
    private String imageHash;

    @Schema(description = "资产图片url", example = "https://xxx")
    @ExcelProperty("资产图片url")
    private String imageUrl;

    @Schema(description = "使用人", requiredMode = Schema.RequiredMode.REQUIRED, example = "18970")
    @ExcelProperty("使用人")
    private Long userId;

    @Schema(description = "使用部门id", example = "5829")
    @ExcelProperty("使用部门id")
    private Long departmentId;

    @Schema(description = "领用日期")
    @ExcelProperty("领用日期")
    private LocalDateTime receiveTime;

    @Schema(description = "保养到期时间")
    @ExcelProperty("保养到期时间")
    private LocalDateTime maintainTime;

    @Schema(description = "保养说明")
    @ExcelProperty("保养说明")
    private String maintainInfo;

    @Schema(description = "预计折旧期限(月)")
    private Integer depreciationMonths;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

    @Schema(description = "所属资产的知识库")
    @ExcelProperty("所属资产的知识库")
    private String knowledgeBase;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "使用人姓名")
    private String userName;

    @Schema(description = "使用部门名称")
    private String departmentName;
}