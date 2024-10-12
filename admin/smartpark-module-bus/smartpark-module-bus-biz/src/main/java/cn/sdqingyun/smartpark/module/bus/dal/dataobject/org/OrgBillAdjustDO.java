package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 机构账单调整 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_adjust")
@KeySequence("org_bill_adjust_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillAdjustDO extends TenantBaseDO {

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
    private String billType;
    /**
     * 调整日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date adjustDate;
    /**
     * 调整类型1=调增2=调减
     */
    private String adjustType;
    /**
     * 调整方式
     */
    private String adjustMode;
    /**
     * 调整前账单金额
     */
    private BigDecimal adjustBeforeAmount;
    /**
     * 调整比例
     */
    private String adjustProportion;
    /**
     * 调整金额
     */
    private BigDecimal adjustPrice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 作废日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date cancelAdjustDate;
    /**
     * 调整状态;1=调整待审核;2=调整已审核;3=作废调整待审核;4=作废已审核;
     */
    private String adjustStatus;

}