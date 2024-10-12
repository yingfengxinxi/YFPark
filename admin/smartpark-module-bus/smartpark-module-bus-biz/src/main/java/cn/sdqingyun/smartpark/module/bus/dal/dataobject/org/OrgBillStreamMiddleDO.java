package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 机构流水账单中间表【匹配】 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_stream_middle")
@KeySequence("org_bill_stream_middle_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillStreamMiddleDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 账单id
     */
    private Long billId;
    /**
     * 账单类型
     */
    private Long billType;
    /**
     * 流水id
     */
    private Long streamId;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 公司名称(收款/付款地方单位名称)
     */
    private String companyName;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 入账日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date incomeDate;
    /**
     * 发生额
     */
    private BigDecimal amount;
    /**
     * 已匹配金额
     */
    private BigDecimal matchPrice;
    /**
     * 未匹配金额
     */
    private BigDecimal nomatchPrice;
    /**
     * 匹配日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date matchDate;
    /**
     * 取消匹配日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date cancelMatchDate;
    /**
     * 匹配状态;1=完全匹配;2=未匹配;3=部分匹配;
     */
    private String matchStatus;
    /**
     * 是否确认;1=待确认;2=已确认;
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
    private Integer confirmUid;
    /**
     * 取消确认时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date unconfirmTime;
    /**
     * 取消确认人员uid
     */
    private Integer unconfirmUid;
    /**
     * 操作权限
     */
    private String operate;

}