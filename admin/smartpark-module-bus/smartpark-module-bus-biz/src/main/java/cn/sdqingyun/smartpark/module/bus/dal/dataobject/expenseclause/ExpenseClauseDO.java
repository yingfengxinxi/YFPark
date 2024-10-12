package cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 机构合同费用条款附加 DO
 *
 * @author 智慧园区管理员
 */
@TableName("contract_expense_clause")
@KeySequence("contract_expense_clause_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseClauseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构ID
     */
    private Integer orgId;
    /**
     * 基础合同,合同主表id
     */
    private Integer contractId;
    /**
     * 条款id
     */
    private Integer clauseId;
    /**
     * 条款类型(属性);1=租赁条款;2=物业条款
     */
    private Integer clauseType;
    /**
     * 费用条款属性
     */
    private String attribute;

    /**
     * 基本条款
     */
    private String basicClause;
    /**
     * 保证金条款
     */
    private String bondClause;
    /**
     * 税率规则条款
     */
    private String taxClause;
    /**
     * 综合条款(租期条款/物业费条款/经营管理费/停车费/)
     */
    private String multipleClause;
    /**
     * 递增条款
     */
    private String incrementClause;
    /**
     * 优惠条款
     */
    private String discountClause;
    /**
     * 备注条款
     */
    private String remarkClause;
    /**
     * 租金明细报表json
     */
    private String reportDetail;
    /**
     * 优惠金额
     */
    private BigDecimal discountPrice;
    /**
     * 优惠信息
     */
    private String discountData;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;
    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date updateTime;
    /**
     * 创建者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String creator;
    /**
     * 更新者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    private String updater;
    /**
     * 是否删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;
}