package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构合同新增/修改 Request VO")
@Data
public class ContractSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32481")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29323")
    private Long orgId;

    @Schema(description = "楼宇id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7452")
    @NotNull(message = "楼宇id不能为空")
    private Long buildId;

    @Schema(description = "租客id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27502")
    @NotNull(message = "租客id不能为空")
    private Long ownerId;

    @Schema(description = "行业id")
    private String industry;

    @Schema(description = "法人姓名")
    private String legalPerson;

    @Schema(description = "签订人姓名", example = "张三")
    private String signedName;

    @Schema(description = "跟进人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "124")
    private Long followupUid;

    @Schema(description = "合同模板id", example = "25961")
    private Long templateId;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "经办人id", example = "32679")
    private Long operatorId;

    @Schema(description = "签订日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private Date signingDate;

    @Schema(description = "单价保留小数点几位")
    private Integer unitPricePoint;

    @Schema(description = "计算精度0=精确计算结果保留两位1=每步计算保留两位")
    private String calculationAccuracy;

    @Schema(description = "计算顺序0=单价*面积*时间1=单价*时间*面积")
    private String calculationOrder;

    @Schema(description = "应收整数是否四舍五入0=否1=是")
    private String isReceivableInteger;

    @Schema(description = "滞纳金起算天数")
    private Integer startingLateFeeDay;

    @Schema(description = "滞纳金比例%")
    private String lateFeeRatio;

    @Schema(description = "滞纳金上限%")
    private String upperLimitLateFee;

    @Schema(description = "楼宇名称", example = "赵六")
    private String buildName;

    @Schema(description = "房号id,多个用英文逗号拼接;例:101,103")
    private String roomNumber;

    @Schema(description = "租赁数(单位㎡/个)")
    private String leaseSquare;

    @Schema(description = "合同起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date contractStartTime;

    @Schema(description = "合同结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date contractEndTime;


    @Schema(description = "退租时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date leaseRetreatTime;

    @Schema(description = "跟进人名称", example = "王五")
    private String followupUname;

    @Schema(description = "租赁条款单价", example = "3082")
    private String leaseUnitPrice;

    @Schema(description = "物业条款单价", example = "28710")
    private String propertyUnitPrice;

    @Schema(description = "租赁条款实时单价;若存在实时变更,可关联查询楼宇实时单价", example = "6493")
    private String leaseUnitRealprice;

    @Schema(description = "物业条款实时单价;若存在实时变更,可关联查询楼宇实时单价", example = "650")
    private String propertyUnitRealprice;

    @Schema(description = "总计租金;保留两位小数", example = "2068")
    private BigDecimal leasePrice;

    @Schema(description = "合同类型/合同应用场景;普通租赁合同(normal)/退租合同(retreat)/物业合同(property)等等 多个用英文逗号连接", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "合同类型/合同应用场景;普通租赁合同(normal)/退租合同(retreat)/物业合同(property)等等 多个用英文逗号连接不能为空")
    private String scene;

    @Schema(description = "条款类型;普通租赁条款(normal)/退租条款(retreat)/物业条款(property)等等 多个用英文逗号连接")
    private String clause;

    @Schema(description = "是否续租0=否1=是")
    private String isWhetherLease;

    @Schema(description = "合同状态0=新建待审核1=正常执行中2=变更待审核3=变更待修改4=退租待审批5=退租待执行6=已退租7=作废待审批8=作废待修改9=到期未处理10=已驳回11=已作废12=已撤回13=待执行14=续租待审批15=已到期")
    private String status;

    @Schema(description = "园区id")
    private Long parkId;

    @Schema(description = "生成的pdf合同路径")
    private String pdfFileUrl;


    @Schema(description = "账单明细是否存在预期0=否1=是")
    private String isBillOverdue;
}