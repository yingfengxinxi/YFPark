package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.*;

import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 机构账单收支流水分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BillStreamPageReqVO extends PageParam {

    @Schema(description = "项目id", example = "16168")
    private Long villageId;

    @Schema(description = "租客id", example = "19986")
    private Long ownerId;

    @Schema(description = "楼宇id", example = "19986")
    private Long buildId;

    @Schema(description = "绑定的楼宇json")
    private String buildIds;

    @Schema(description = "绑定楼宇信息json")
    private String buildBind;

    @Schema(description = "房间号json")
    private String roomNumber;

    @Schema(description = "账单id", example = "8867")
    private Long billId;

    @Schema(description = "收款(业主)流水账号id", example = "21095")
    private Long accountId;

    @Schema(description = "收款流水账户名称", example = "王五")
    private String accountName;

    @Schema(description = "流水号")
    private String streamNumber;

    @Schema(description = "流水来源;1=手动创建;")
    private String streamSource;

    @Schema(description = "账单类型;1=收款;2=付款;", example = "1")
    private String billType;

    @Schema(description = "流水是否开启;0=关闭;1=开启;默认开启")
    private String isClose;

    @Schema(description = "借贷类型", example = "1")
    private String loanType;

    @Schema(description = "是否确认;1=待确认;2=已确认;")
    private String isConfirm;

    @Schema(description = "发生额")
    private BigDecimal amount;

    @Schema(description = "开始发生额")
    private String startAmount;
    @Schema(description = "结束发生额")
    private String endAmount;

    @Schema(description = "入账日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date incomeDate;

    @Schema(description = "入账开始日期")
    private String startIncomeDate;

    @Schema(description = "入账结束日期")
    private String endIncomeDate;

    @Schema(description = "取消匹配日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date cancelMatchDate;

    @Schema(description = "对账收付款公司id", example = "16314")
    private Long companyId;

    @Schema(description = "对方单位名称", example = "赵六")
    private String companyName;

    @Schema(description = "收付款公司类型", example = "1")
    private Integer companyType;

    @Schema(description = "租客开户银行名称", example = "24266")
    private String streamAccount;

    @Schema(description = "匹配日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date matchDate;
    @TableField(exist = false)
    private String matchDateStr;

    @Schema(description = "匹配状态;1=完全匹配;2=未匹配;3=部分匹配;", example = "1")
    private String matchStatus;

    @Schema(description = "已匹配金额", example = "28918")
    private BigDecimal matchPrice;

    @Schema(description = "未匹配金额", example = "7967")
    private BigDecimal nomatchPrice;

    @Schema(description = "退款金额", example = "10024")
    private BigDecimal refundPrice;

    @Schema(description = "币种")
    private String currency;

    @Schema(description = "费用类型", example = "1")
    private String costType;

    @Schema(description = "联系人", example = "赵六")
    private String linkName;

    @Schema(description = "汇款方式", example = "1")
    private String remitType;

    @Schema(description = "对方账号", example = "27002")
    private String otherAccount;

    @Schema(description = "对方子账户", example = "26839")
    private String sonAccount;

    @Schema(description = "对方子账户名称", example = "芋艿")
    private String sonAccountName;

    @Schema(description = "对方子账户余额调整状态", example = "1")
    private String sonAccountStatus;

    @Schema(description = "凭证号")
    private String voucherNo;

    @Schema(description = "收据编号")
    private String receiptNo;

    @Schema(description = "摘要")
    private String abstractc;

    @Schema(description = "房屋虚拟标识.0=真实房屋;1=非真实房屋")
    private Short isUnrealRoom;

    @Schema(description = "虚拟房屋信息")
    private String unrealRoom;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "关闭流水的原因", example = "不对")
    private String closeReason;

    @Schema(description = "预存的额外信息，比如电表id")
    private String extra;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
    @Schema(description = "创建开始时间")
    private String startCreateTime;
    @Schema(description = "创建结束时间")
    private String endCreateTime;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "删除流水申请的状态0正常1删除中2已删除", example = "1")
    private String approvalStatus;

    @Schema(description = "删除审批中是否允许撤销0不允许1允许")
    private String approvalCancel;

    @Schema(description = "删除流水的原因", example = "不喜欢")
    private String reason;

    @Schema(description = "专项名称", example = "2")
    private String singleType;


    @Schema(description = "流水来源0=收款中创建数据", example = "2")
    private String dataSource;
}