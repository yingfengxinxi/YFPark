package cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.flowable.bpmn.model.TextAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 机构合同 DO
 *
 * @author 智慧园区管理员
 */
@TableName("contract")
@KeySequence("contract_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 租客名称
     */
    @TableField(exist = false)
    private String ownerName;
    /**
     * 行业id
     */
    private String industry;
    /**
     * 法人姓名
     */
    private String legalPerson;
    /**
     * 签订人姓名
     */
    private String signedName;
    /**
     * 跟进人uid
     */
    private Long followupUid;


    /**
     * 已选中楼宇资源
     */
    private String checkedBuild;

    /**
     * 合同模板id
     */
    private Long templateId;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 经办人id
     */
    private Long operatorId;
    /**
     * 经办人
     */
    @TableField(exist = false)
    private String operatorName;
    /**
     * 签订日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private Date signingDate;
    /**
     * 单价保留小数点几位
     */
    private Integer unitPricePoint;
    /**
     * 计算精度0=精确计算结果保留两位1=每步计算保留两位
     */
    private String calculationAccuracy;
    /**
     * 计算顺序0=单价*面积*时间1=单价*时间*面积
     */
    private String calculationOrder;
    /**
     * 应收整数是否四舍五入0=否1=是
     */
    private String isReceivableInteger;
    /**
     * 滞纳金起算天数
     */
    private Integer startingLateFeeDay;
    /**
     * 滞纳金比例%
     */
    private String lateFeeRatio;
    /**
     * 滞纳金上限%
     */
    private String upperLimitLateFee;
    /**
     * 楼宇名称
     */
    private String buildName;
    /**
     * 房号,多个用英文逗号拼接;例:1-101,1-103
     */
    private String roomNumber;
    /**
     * 租赁数(单位㎡/个)
     */
    private String leaseSquare;

    @Schema(description = "合同起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date contractStartTime;

    @Schema(description = "合同结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date contractEndTime;
    /**
     * 退租时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date leaseRetreatTime;
    /**
     * 跟进人名称
     */
    private String followupUname;
    /**
     * 租赁条款单价
     */
    private String leaseUnitPrice;
    /**
     * 物业条款单价
     */
    private String propertyUnitPrice;
    /**
     * 租赁条款实时单价;若存在实时变更,可关联查询楼宇实时单价
     */
    private String leaseUnitRealprice;
    /**
     * 物业条款实时单价;若存在实时变更,可关联查询楼宇实时单价
     */
    private String propertyUnitRealprice;
    /**
     * 总计租金;保留两位小数
     */
    private BigDecimal leasePrice;
    /**
     * 合同类型/合同应用场景;普通租赁合同(normal)/退租合同(retreat)/物业合同(property)等等 多个用英文逗号连接
     */
    private String scene;
    /**
     * 条款类型;普通租赁条款(normal)/退租条款(retreat)/物业条款(property)等等 多个用英文逗号连接
     */
    private String clause;

    /**
     * 是否续租0=否1=是
     */
    private String isWhetherLease;

    /**
     * 合同状态0=新建待审核1=正常执行中2=变更待审核3=变更待修改4=退租待审批5=退租待执行6=已退租7=作废待审批8=作废待修改9=到期未处理10=已驳回11=已作废12=已撤回13=待执行14=续租待审批15=已到期
     */
    private String status;

    /**
     * 园区id
     */
    private Long parkId;

    /**
     * 租赁面积
     */
    private String leaseArea;

    /**
     * 生成的pdf合同路径
     */
    private String pdfFileUrl;

    /**
     * 合同来源0=新建1=导入
     */
    private String dataSource;

    /**
     * 到期天数
     */
    @TableField(exist = false)
    private Integer expirationDay;

    /**
     * 租赁单价
     */
    @TableField(exist = false)
    private String zlDjMoney;

    /**
     * 物业单价
     */
    @TableField(exist = false)
    private String wyDjMoney;
    /**
     * 租赁保证金
     */
    @TableField(exist = false)
    private String zlBondClause;
    /**
     * 物业保证金
     */
    @TableField(exist = false)
    private String wyBondClause;
    /**
     * 是否产生逾期0=否1=是
     */
    private String isBillOverdue;
    //续租合同Id
    private Long renewalContractId;


}