package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 自定义抄表记录 DO
 *
 * @author 管理员
 */
@TableName("energy_record")
@KeySequence("energy_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyRecordDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 类型 1分表 2总表 3公摊表
     */
    private String type;
    /**
     * 设备表种类
     */
    private Long meterType;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 楼宇ID
     */
    private Long buildId;
    /**
     * 关联自定义表ID
     */
    private Long energyId;
    /**
     * 关联自定义价格标准ID
     */
    private Long energyPriceId;
    /**
     * 关联抄表任务ID
     */
    private Long energyTaskId;
    /**
     * 抄表负责人
     */
    private Long leadUid;
    /**
     * 上次抄表时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;
    /**
     * 上次读数
     */
    private BigDecimal lastNumber;
    /**
     * 上次用量
     */
    private BigDecimal lastUse;
    /**
     * 本次抄表时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date thisTime;
    /**
     * 本次读数
     */
    private BigDecimal thisNumber;
    /**
     * 本次用量
     */
    private BigDecimal thisUse;
    /**
     * 抄表图片
     */
    private String image;
    /**
     * 状态，1启动，0审核中，4禁用
     */
    private String status;
    /**
     * 来源 0 pc端 1 移动端
     */
    private String isMobile;
    /**
     * 是否确认 0未确认 1已确认
     */
    private String isConfirm;
    /**
     * 自动抄表 0=否;1=是
     */
    private String isAuto;

}