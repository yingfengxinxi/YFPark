package cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 机构合同模板配置 DO
 *
 * @author 智慧园区
 */
@TableName("contract_template")
@KeySequence("contract_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractTemplateDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 水印模板id
     */
    private Long watermarkId;
    /**
     * 应用场景;普通合同(normal)/退租合同(retreat)等等
     */
    private String scene;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板文件
     */
    private String templatePath;
    /**
     * 模板主条款信息
     */
    private String basicClause;
    /**
     * 模板合同编号规则
     */
    private String numberRule;
    /**
     * 滞纳金条款
     */
    private String latefeeClause;
    /**
     * 综合条款
     */
    private String multipleClause;
    /**
     * 楼宇id,多个id使用逗号隔开
     */
    private String relationBuilds;
    @TableField(exist = false)
    private String relationBuildName;
    /**
     * 模板变量自定义表头集合
     */
    private String tableFields;
    /**
     * 永久标识;1=永久
     */
    private String dateFlag;
    /**
     * 过期时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;
    /**
     * 模板使用状态;1=启用;0=关闭
     */
    private String status;

}