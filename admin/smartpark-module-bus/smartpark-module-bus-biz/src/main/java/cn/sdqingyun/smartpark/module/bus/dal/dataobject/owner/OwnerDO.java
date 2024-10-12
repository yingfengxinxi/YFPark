package cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 租客信息 DO
 *
 * @author 智慧园区管理员
 */
@TableName("owner")
@KeySequence("owner_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDO extends TenantBaseDO {

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
     * 租客名称
     */
    private String name;
    /**
     * 是否个人，1个人，0公司
     */
    private Integer isPersonal;
    /**
     * 是否推荐;0=不推荐;1=推荐;
     */
    private Short isSuggest;
    /**
     * 目标对象;0=正常租客;1=收款对象;2=付款对象;
     */
    private Short type;
    /**
     * 虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;
     */
    private Short sham;
    /**
     * 联系人
     */
    private Long contactId;
    /**
     * 租客合同签署人id
     */
    private Long contactSignId;
    /**
     * 缴费通知单联系人
     */
    private Long contactNoticeId;
    /**
     * 审批联系人id
     */
    private Long approvalContactId;
    /**
     * 证件号码
     */
    private String certificateNumber;
    /**
     * 行业分类id
     */
    private Long industryId;
    /**
     * 租客绑定的项目id
     */
    private String villageIdList;
    /**
     * 租客绑定的项目楼宇房源信息
     */
    private String buildBind;
    /**
     * 联系方式
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 租客编码
     */
    private String tenantNo;
    /**
     * 官网地址
     */
    private String websiteLink;
    /**
     * 企业logo
     */
    private String logo;
    /**
     * 企业简介
     */
    private String companyDesc;
    /**
     * 成立日期
     */
    private LocalDate businessInfoFoundingTime;
    /**
     * 营业期限
     */
    private LocalDate businessInfoBusinessTerm;
    /**
     * 注册资金
     */
    private BigDecimal registeredCapital;
    /**
     * 开票信息
     */
    private String invoiceInfo;
    /**
     * 工商信息
     */
    private String businessInfo;
    /**
     * 租客标签
     */
    private String tagInfo;
    /**
     * 自定义字段
     */
    private String diyField;
    /**
     * 是否开启公司代付通知
     */
    private Short isAdvanceNotice;
    /**
     * 是否归档
     */
    private Short isArchive;

    /**
     * 开户行
     */
    private String bank;
    /**
     * 账号
     */
    private String account;
    /**
     * 电话
     */
    private String bankPhone;
    /**
     * 纳税人识别号
     */
    private String taxpayerIdentificationNumber;

    /**
     * 发票类型0=增值税普通发票1=增值税专用发票2=增值税电子普通发票
     */
    private String invoiceType;
    /**
     * 开票地址
     */
    private String billingAddress;

    /**
     * 合同id
     */
    @TableField(exist = false)
    private Long contractId;

}