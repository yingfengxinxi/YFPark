package cn.sdqingyun.smartpark.module.bus.dal.dataobject.invoice;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 发票模板配置 DO
 *
 * @author 智慧园区
 */
@TableName("invoice_template")
@KeySequence("invoice_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceTemplateDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 发票模板名称
     */
    private String name;
    /**
     * 售方id
     */
    private Long sellerId;

    /**
     * 售方公司名称
     */
    @TableField(exist = false)
    private String companyName;
    /**
     * 税率规则id;多个id使用逗号隔开(1,2,3)
     */
    private String taxRule;
    /**
     * 是否开通电子发票 0不开通
     */
    private String eInvoice;
    /**
     * 税票通配置信息-appid
     */
    private String sptAppid;
    /**
     * 税票通配置信息-appsecret
     */
    private String sptAppsecret;
    /**
     * 税票通配置信息-税号
     */
    private String sptNumber;
    /**
     * 后台账单缴费自动开票（1是/0否）
     */
    private String orgAuto;
    /**
     * 用户账单缴费自动开票（1是/0否）
     */
    private String userAuto;
    /**
     * 应用楼宇id;多个id使用逗号隔开(1,2,3)
     */
    private String buildBind;
    /**
     * 状态;0=禁用；1=启用
     */
    private String status;
    /**
     * 模板是否开启审批;0=否1=是
     */
    private String isApproval;

}