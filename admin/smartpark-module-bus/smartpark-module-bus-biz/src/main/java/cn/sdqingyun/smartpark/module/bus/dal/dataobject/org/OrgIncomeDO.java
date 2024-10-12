package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 机构收入 DO
 *
 * @author 智慧园区
 */
@TableName("org_income")
@KeySequence("org_income_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgIncomeDO extends TenantBaseDO {

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
     * 合同id
     */
    private Long contractId;
    /**
     * 账单id
     */
    private Long billId;
    /**
     * 账单类型;1=收款;2=付款;
     */
    private String billType;
    /**
     * 子流水id
     */
    private Long streamMiddleId;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 租客所属行业ID
     */
    private Long industryId;
    /**
     * 楼层-房间号;{"room": [{"id":"1001","room_id":"","room_name":"","layer_id":"","unit_id":"","zone_id":""}]}
     */
    private String roomNumber;
    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 收入类型;1=应收(含税);2=应收(不含税);3=实收(含税);4=实收(不含税);
     */
    private String incomeType;
    /**
     * 是否确认;1=当月待确认;2=当月已确认;
     */
    private String isConfirm;
    /**
     * 确认时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date confirmTime;
    /**
     * 确认人员uid
     */
    private String confirmUid;
    /**
     * 取消确认时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date unconfirmTime;
    /**
     * 取消确认人员uid
     */
    private String unconfirmUid;
    /**
     * 合同费用类型
     */
    private String contractCostType;
    /**
     * 租客费用类型
     */
    private String ownerCostType;
    /**
     * 账单费用类型
     */
    private String costType;
    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;
    /**
     * 含/不含税金额
     */
    private BigDecimal amount;
    /**
     * 税额
     */
    private BigDecimal taxAmount;
    /**
     * 交易时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date tradeTime;
    /**
     * 房屋虚拟标识.0=真实房屋;1=非真实房屋
     */
    private String isUnrealRoom;
    /**
     * 虚拟房屋信息
     */
    private String unrealRoom;

}