package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/7/25 16:34
 */
@Data
public class AccountsReceivableReportVO extends PageParam {

    @Schema(description = "账单id", example = "1")
    private Long billId;

    @Schema(description = "租客id", example = "1")
    private Long ownerId;

    @Schema(description = "租客名称", example = "1")
    @ExcelProperty("租客名称")
    private String ownerName;

    @Schema(description = "合同Id", example = "1")
    private Long contractId;

    @Schema(description = "默认联系人", example = "1")
    @ExcelProperty("默认联系人")
    private String ownerContactName;

    @Schema(description = "联系方式", example = "1")
    @ExcelProperty("联系方式")
    private String phone;

    @Schema(description = "楼宇名称", example = "1")
    @ExcelProperty("楼宇名称")
    private String buildName;

    @Schema(description = "房间号", example = "1")
    private String roomNumber;

    @ExcelProperty("房间号")
    private String roomNumberName;

    @Schema(description = "是否个人，1个人，0公司")
    private Integer isPersonal;

    @Schema(description = "有效期开始日期", example = "1")
    @ExcelProperty("有效期开始日期")
    private String payStartDate;

    @Schema(description = "有效期结束日期", example = "1")
    @ExcelProperty("有效期结束日期")
    private String payEndDate;

    @Schema(description = "费用类型id", example = "1")
    private String costType;

    @Schema(description = "费用类型", example = "1")
    @ExcelProperty("费用类型")
    private String costTypeName;

    @Schema(description = "应收金额", example = "1")
    @ExcelProperty("应收金额")
    private BigDecimal receivable;

    @Schema(description = "应收时间", example = "1")
    @ExcelProperty("应收时间")
    private String payDate;

    @Schema(description = "滞纳金", example = "1")
    @ExcelProperty("滞纳金")
    private BigDecimal lateFee;

    @Schema(description = "实收金额", example = "1")
    @ExcelProperty("实收金额")
    private BigDecimal receivablePayment;

    @Schema(description = "账单状态", example = "1")
    @ExcelProperty("账单状态")
    private String billStatus;

    @Schema(description = "付款方式", example = "1")
    @ExcelProperty("付款方式")
    private String remitType;


    @Schema(description = "统计方式【start_date按有效期统计receivable_payment_date按应收时间统计】", example = "1")
    private String method;

    @Schema(description = "违约金查询条件0=否1=是", example = "1")
    private String isLateFee;

    private Long tenantId;

    private String billType;
    private String clauseType;
    private String streamSource;

    @Schema(description = "房间号", example = "1")
    private List<Long> roomNumberList;

}
