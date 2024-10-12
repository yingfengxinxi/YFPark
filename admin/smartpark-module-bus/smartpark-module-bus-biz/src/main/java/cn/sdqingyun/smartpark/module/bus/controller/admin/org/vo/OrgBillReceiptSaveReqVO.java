package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.util.*;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 机构账单收据新增/修改 Request VO")
@Data
public class OrgBillReceiptSaveReqVO extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4636")
    private Long id;

    @Schema(description = "项目id", example = "2224")
    private Long villageId;

    @Schema(description = "楼宇id", example = "24668")
    private Long buildId;

    @Schema(description = "账单id", example = "1088")
    private Long billId;

    @Schema(description = "子账单ID", example = "22438")
    private Long subbillId;

    @Schema(description = "账单收据编号规则id", example = "20383")
    private Long ruleId;

    @Schema(description = "房号")
    private String roomNumber;

    @Schema(description = "交款公司/单位id", example = "31190")
    private Long paymentCompanyId;

    @Schema(description = "交款单位")
    private String paymentCompany;

    @Schema(description = "交款人", example = "王五")
    private String paymentUname;

    @Schema(description = "交款方地址")
    private String paymentAddress;

    @Schema(description = "交款方电话")
    private String paymentPhone;

    @Schema(description = "收据编号")
    private String receiptNumber;

    @Schema(description = "编号数值")
    private Integer receiptInt;

    @Schema(description = "生成编号方式;1=规则生成;2=自定义", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "生成编号方式;1=规则生成;2=自定义不能为空")
    private String numberType;

    @Schema(description = "收据金额", example = "8399")
    private BigDecimal price;

    @Schema(description = "可开据金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "可开据金额不能为空")
    private BigDecimal canReceiptAmount;

    @Schema(description = "申请开据金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请开据金额不能为空")
    private BigDecimal applyReceiptAmount;

    @Schema(description = "币种")
    private String currency;

    @Schema(description = "收据状态;1=已发出;2=已回收;3=新建待审批;4=作废待审批;5=已作废;6=未发出;", example = "1")
    private String status;

    @Schema(description = "交款人uid", example = "26489")
    private String paymentUid;

    @Schema(description = "收款公司/单位id", example = "12701")
    private Long collectionCompanyId;

    @Schema(description = "收款单位")
    private String collectionCompany;

    @Schema(description = "收款人uid", example = "28171")
    private String collectionUid;

    @Schema(description = "收款方收款人", example = "王五")
    private String collectionUname;

    @Schema(description = "收款方地址")
    private String collectionAddress;

    @Schema(description = "收款方电话")
    private String collectionPhone;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "汇款方式", example = "1")
    private String remitType;

    @Schema(description = "费用类型", example = "2")
    private String costType;

    @Schema(description = "费用名称", example = "赵六")
    private String costName;

    @Schema(description = "开据批次凭证")
    private String taskKey;

    @Schema(description = "开据分组凭证")
    private String groupKey;

    @Schema(description = "开据人", example = "12288")
    private String issuerUid;

    @Schema(description = "开据时间")
    private LocalDateTime issuerTime;

    @Schema(description = "收据当前应用模板id", example = "1132")
    private Long applyTemplateId;

    /**
     * 生成收据url
     */
    @Schema(description = "生成收据url")
    private String receoptFileUrl;

    @Schema(description = "收据当前应用模板生成数据")
    private String applyTemplate;

    @Schema(description = "发票生成状态;0=未生成 1=生成中 2=已生成", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "发票生成状态;0=未生成 1=生成中 2=已生成不能为空")
    private String applyStatus;

}