package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "24074")
    private Long orgId;

    @Schema(description = "资产编码")
    private String propertyNumber;

    @Schema(description = "资产标签链接")
    private String labelLink;

    @Schema(description = "资产分类", example = "1")
    private Long type;

    @Schema(description = "资产分类集合", example = "1")
    @TableField(exist = false)
    private String types;

    @Schema(description = "资产名称", example = "李四")
    private String name;

    @Schema(description = "资产状态", example = "1")
    private Integer status;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "型号", example = "赵六")
    private String modelName;

    @Schema(description = "设备序列号")
    private String deviceCode;
    //是否只返回设备序列号有值的0=否1=是
    private String isDeviceCode;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "管理员id", example = "18658")
    private Long adminId;

    @Schema(description = "管理员姓名")
    private String adminName;

    @Schema(description = "目标房源项目id", example = "3602")
    private Long villageId;

    @Schema(description = "目标房源楼宇id", example = "16252")
    private Long buildId;

    @Schema(description = "目标房源房间id", example = "28140")
    private String roomId;

    @Schema(description = "绑定的目标房源json")
    private String buildBind;

    @Schema(description = "所在位置配置信息id", example = "10141")
    private Long positionId;

    @Schema(description = "资产操作时的位置信息", example = "王五")
    private String positionName;

    @Schema(description = "购置时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] purchaseTime;

    @Schema(description = "购置方式", example = "2")
    private Integer purchaseType;

    @Schema(description = "购置金额(含税)")
    private BigDecimal purchaseAmount;

    @Schema(description = "入库时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] stockTime;

    @Schema(description = "预计使用期限(月)")
    private Long expectMonths;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "图片hash值")
    private String imageHash;

    @Schema(description = "资产图片url", example = "https://xxx")
    private String imageUrl;

    @Schema(description = "使用人", example = "18970")
    private Long userId;

    @Schema(description = "使用部门id", example = "5829")
    private Long departmentId;

    @Schema(description = "领用日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] receiveTime;

    @Schema(description = "保养到期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] maintainTime;

    @Schema(description = "保养说明")
    private String maintainInfo;

    @Schema(description = "预计折旧期限(月)")
    private Integer depreciationMonths;

    @Schema(description = "操作人uid", example = "31482")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "4804")
    private Long muserUid;

    @Schema(description = "所属资产的知识库")
    private String knowledgeBase;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "资产状态集合，查询不同状态下列表")
    private List<Integer> statusAll;
}