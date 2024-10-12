package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.BillingStatementVO;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.util.*;
import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 机构账单收据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillReceiptRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4636")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "项目id", example = "2224")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "24668")
    @ExcelProperty("楼宇id")
    private Long buildId;
    @Schema(description = "楼宇名称")
    @TableField(exist = false)
    private String buildName;

    @Schema(description = "账单id", example = "1088")
    @ExcelProperty("账单id")
    private Long billId;

    @Schema(description = "子账单ID", example = "22438")
    @ExcelProperty("子账单ID")
    private Long subbillId;

    @Schema(description = "账单收据编号规则id", example = "20383")
    @ExcelProperty("账单收据编号规则id")
    private Long ruleId;

    @Schema(description = "房号")
    @ExcelProperty("房号")
    private String roomNumber;

    @Schema(description = "交款公司/单位id", example = "31190")
    @ExcelProperty("交款公司/单位id")
    private Long paymentCompanyId;

    @Schema(description = "交款单位")
    @ExcelProperty("交款单位")
    private String paymentCompany;

    @Schema(description = "交款人", example = "王五")
    @ExcelProperty("交款人")
    private String paymentUname;

    @Schema(description = "交款方地址")
    @ExcelProperty("交款方地址")
    private String paymentAddress;

    @Schema(description = "交款方电话")
    @ExcelProperty("交款方电话")
    private String paymentPhone;

    @Schema(description = "收据编号")
    @ExcelProperty("收据编号")
    private String receiptNumber;

    @Schema(description = "编号数值")
    @ExcelProperty("编号数值")
    private Integer receiptInt;

    @Schema(description = "生成编号方式;1=规则生成;2=自定义", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("生成编号方式;1=规则生成;2=自定义")
    private String numberType;

    @Schema(description = "收据金额", example = "8399")
    @ExcelProperty("收据金额")
    private BigDecimal price;

    @Schema(description = "可开据金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("可开据金额")
    private BigDecimal canReceiptAmount;

    @Schema(description = "申请开据金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("申请开据金额")
    private BigDecimal applyReceiptAmount;

    @Schema(description = "币种")
    @ExcelProperty("币种")
    private String currency;

    @Schema(description = "收据状态;1=已发出;2=已回收;3=新建待审批;4=作废待审批;5=已作废;6=未发出;", example = "1")
    @ExcelProperty("收据状态;1=已发出;2=已回收;3=新建待审批;4=作废待审批;5=已作废;6=未发出;")
    private String status;
    @TableField(exist = false)
    private String statusName;

    @Schema(description = "交款人uid", example = "26489")
    @ExcelProperty("交款人uid")
    private String paymentUid;

    @Schema(description = "收款公司/单位id", example = "12701")
    @ExcelProperty("收款公司/单位id")
    private Long collectionCompanyId;

    @Schema(description = "收款单位")
    @ExcelProperty("收款单位")
    private String collectionCompany;

    @Schema(description = "收款人uid", example = "28171")
    @ExcelProperty("收款人uid")
    private String collectionUid;

    @Schema(description = "收款方收款人", example = "王五")
    @ExcelProperty("收款方收款人")
    private String collectionUname;

    @Schema(description = "收款方地址")
    @ExcelProperty("收款方地址")
    private String collectionAddress;

    @Schema(description = "收款方电话")
    @ExcelProperty("收款方电话")
    private String collectionPhone;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "汇款方式", example = "1")
    @ExcelProperty("汇款方式")
    private String remitType;

    @Schema(description = "费用类型", example = "2")
    @ExcelProperty("费用类型")
    private String costType;

    @Schema(description = "费用名称", example = "赵六")
    @ExcelProperty("费用名称")
    private String costName;

    @Schema(description = "开据批次凭证")
    @ExcelProperty("开据批次凭证")
    private String taskKey;

    @Schema(description = "开据分组凭证")
    @ExcelProperty("开据分组凭证")
    private String groupKey;

    @Schema(description = "开据人", example = "12288")
    @ExcelProperty("开据人")
    private String issuerUid;

    @Schema(description = "开据时间")
    @ExcelProperty("开据时间")
    private LocalDateTime issuerTime;

    @Schema(description = "收据当前应用模板id", example = "1132")
    @ExcelProperty("收据当前应用模板id")
    private Long applyTemplateId;

    /**
     * 生成收据url
     */
    @Schema(description = "生成收据url")
    private String receoptFileUrl;

    @Schema(description = "收据当前应用模板生成数据")
    @ExcelProperty("收据当前应用模板生成数据")
    private String applyTemplate;

    @Schema(description = "发票生成状态;0=未生成 1=生成中 2=已生成", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("发票生成状态;0=未生成 1=生成中 2=已生成")
    private String applyStatus;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    //开据账单
    @TableField(exist = false)
    @Schema(description = "开据账单")
    private BillingStatementVO billingStatement;
}