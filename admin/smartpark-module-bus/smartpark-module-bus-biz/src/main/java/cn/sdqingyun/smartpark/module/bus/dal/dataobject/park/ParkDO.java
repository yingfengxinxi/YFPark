package cn.sdqingyun.smartpark.module.bus.dal.dataobject.park;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 停车场（一个项目可以有多个场） DO
 *
 * @author 智慧园区
 */
@TableName("park")
@KeySequence("park_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 停车场名称
     */
    private String parkName;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 上级停车场ID
     */
    private Long parentId;
    /**
     * 车场类型 0-默认 1-大华8900
     */
    private String parkType;
    /**
     * ip端口
     */
    private String parkIpPort;
    /**
     * 停车场级别字符串，逗号拼接上级id（便于场中场中场，例如地下经过地上，地下某区域被承包）
     */
    private String level;
    /**
     * 临时车政策（0允许进入，1登记/预约进入，2禁止进入）
     */
    private String tempCar;
    /**
     * 免费车类型（数字数组）
     */
    private String freeCar;
    /**
     * 限制车主自主绑定车辆数量
     */
    private Integer bindCarNumber;
    /**
     * 车主必须要绑定租客（0不绑定，1必须绑定）
     */
    private String mustBindCompany;
    /**
     * 车主月租缴费（0不开启，1必须开启）
     */
    private String mobilePayMonthFee;
    /**
     * 车主余额储值（0不开启，1必须开启）
     */
    private String mobilePayStoreFee;
    /**
     * 在场车辆总数
     */
    private Integer presenceCarTotal;
    /**
     * 停车位总数
     */
    private Integer parkSpaceTotal;
    /**
     * 出口车辆匹配规则
     */
    private String outRule;
    /**
     * 落座经度（gcj02）
     */
    private BigDecimal lng;
    /**
     * 落座纬度（gcj02）
     */
    private BigDecimal lat;
    /**
     * 状态（1正常，0关闭）
     */
    private String status;
    /**
     * 是否开启免费车申请1是0否
     */
    private String freeCarApply;
    /**
     * 无牌车是否开闸0不开闸，1开闸
     */
    private String unCarNumberOpen;
    /**
     * 额外信息
     */
    private String extra;
    /**
     * 负载占比
     */
    private Integer loadProp;
    /**
     * 开发票配置
     */
    private String invoice;
    /**
     * 一个车位多辆车配置,0临时收费1禁止驶入
     */
    private Integer moreCar;

}