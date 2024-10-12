package cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill;


import cn.sdqingyun.smartpark.framework.excel.core.annotations.DictFormat;
import cn.sdqingyun.smartpark.module.infra.enums.DictTypeConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;
import static cn.sdqingyun.smartpark.module.bus.enums.DictTypeConstants.IS_CONFIRM;
import static cn.sdqingyun.smartpark.module.bus.enums.DictTypeConstants.LOAN_TYPE;

/**
 * 机构账单收支流水 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_stream")
@KeySequence("org_bill_stream_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillStreamDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 项目id
     */
    private Long villageId;

    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 绑定的楼宇json
     */
    private String buildIds;
    /**
     * 绑定楼宇信息json
     */
    private String buildBind;
    /**
     * 房间号json
     */
    private String roomNumber;
    /**
     * 账单id
     */
    private Long billId;
    /**
     * 收款(业主)流水账号id
     */
    private Long accountId;
    /**
     * 收款流水账户名称
     */
    private String accountName;
    /**
     * 流水号
     */
    private String streamNumber;
    /**
     * 流水来源;1=手动创建;
     */
    private String streamSource;
    /**
     * 账单类型;1=收款;2=付款;
     */
    private String billType;
    /**
     * 流水是否开启;0=关闭;1=开启;默认开启
     */
    private String isClose;
    /**
     * 借贷类型
     */
    @DictFormat(LOAN_TYPE)
    private String loanType;
    /**
     * 是否确认;1=待确认;2=已确认;
     */
    @DictFormat(IS_CONFIRM)
    private String isConfirm;
    /**
     * 发生额
     */
    private BigDecimal amount;
    /**
     * 入账日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date incomeDate;
    /**
     * 取消匹配日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date cancelMatchDate;
    /**
     * 对账收付款公司id
     */
    private Long companyId;
    /**
     * 对方单位名称
     */
    private String companyName;
    /**
     * 收付款公司类型
     */
    private Integer companyType;
    /**
     * 租客开户银行名称
     */
    private String streamAccount;
    /**
     * 匹配日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date matchDate;
    /**
     * 匹配状态;1=完全匹配;2=未匹配;3=部分匹配;
     */
    private String matchStatus;
    /**
     * 已匹配金额
     */
    private BigDecimal matchPrice;
    /**
     * 未匹配金额
     */
    private BigDecimal nomatchPrice;
    /**
     * 退款金额
     */
    private BigDecimal refundPrice;
    /**
     * 币种
     */
    private String currency;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 联系人
     */
    private String linkName;
    /**
     * 汇款方式
     */
    private String remitType;
    /**
     * 对方账号
     */
    private String otherAccount;
    /**
     * 对方子账户
     */
    private String sonAccount;
    /**
     * 对方子账户名称
     */
    private String sonAccountName;
    /**
     * 对方子账户余额调整状态
     */
    private String sonAccountStatus;
    /**
     * 凭证号
     */
    private String voucherNo;
    /**
     * 收据编号
     */
    private String receiptNo;
    /**
     * 摘要
     */
    private String abstractc;
    /**
     * 房屋虚拟标识.0=真实房屋;1=非真实房屋
     */
    private Short isUnrealRoom;
    /**
     * 虚拟房屋信息
     */
    private String unrealRoom;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关闭流水的原因
     */
    private String closeReason;
    /**
     * 预存的额外信息，比如电表id
     */
    private String extra;
    /**
     * 删除流水申请的状态0正常1删除中2已删除
     */
    private String approvalStatus;
    /**
     * 删除审批中是否允许撤销0不允许1允许
     */
    private String approvalCancel;
    /**
     * 删除流水的原因
     */
    private String reason;
    /**
     * 专项名称
     */
    private String singleType;

    @Schema(description = "匹配流水Id", example = "2")
    private String ppStreamId;


    @Schema(description = "流水来源0=收款中创建数据", example = "2")
    private String dataSource;

    @TableField(exist = false)
    private Integer isPersonal;
}