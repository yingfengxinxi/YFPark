package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 机构合同退租 DO
 *
 * @author 智慧园区
 */
@TableName("org_contract_retreat")
@KeySequence("org_contract_retreat_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgContractRetreatDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 所应用的退租合同模板id
     */
    private Long applyTemplateId;
    /**
     * 房源信息
     */
    private String buildBind;
    /**
     * 历史账单信息
     */
    private String billInfo;
    /**
     * 保证金
     */
    private String bondInfo;
    /**
     * 费用总计
     */
    private String totalCost;
    /**
     * 退租类型
     */
    private Integer retreatType;
    /**
     * 退租原因
     */
    private String retreatReason;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 根据所选模板生成的退租合同
     */
    private String applyContract;
    /**
     * 退租日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date retreatDate;
    /**
     * 工商注册地址变更日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date bccChangeDate;
    /**
     * 协议签订日期(申请退租日期)
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date applyRetreatDate;

}