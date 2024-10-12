package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目房间 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_room")
@KeySequence("village_room_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 房间编号
     */
    private Integer roomNumber;
    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 房间管理编号
     */
    private String roomAliasId;
    /**
     * 父级房间编号
     */
    private Long parentRoomId;
    /**
     * 子房间总数
     */
    private Short subRoomCount;
    /**
     * 子房间已租总数
     */
    private Short subRoomRentCount;
    /**
     * 建筑面积
     */
    private BigDecimal buildArea;
    /**
     * 套内面积（实际使用面积）
     */
    private BigDecimal insideArea;
    /**
     * 计租面积
     */
    private BigDecimal rentalArea;
    /**
     * 计租面积（在租）
     */
    private BigDecimal rentalAreaIn;
    /**
     * 计费面积
     */
    private BigDecimal chargingArea;
    /**
     * 计费面积（在租）
     */
    private BigDecimal chargingAreaIn;
    /**
     * 租赁开始时间
     */
    private LocalDateTime leaseStart;
    /**
     * 租赁结束时间
     */
    private LocalDateTime leaseEnd;
    /**
     * 交付时间
     */
    private LocalDateTime deliverTime;
    /**
     * 1招商，0不招商（出租后默认设置不招商）
     */
    private Integer invitationStatus;
    /**
     * 招商政策
     */
    private String investmentPolicy;
    /**
     * 招商条件
     */
    private String investmentConditions;
    /**
     * 图集数组
     */
    private String images;
    /**
     * 价格单位
     */
    private String priceUnit;
    /**
     * 底价单位
     */
    private String priceUnitMin;
    /**
     * 预租单价
     */
    private BigDecimal preUnitPrice;
    /**
     * 最低价格
     */
    private BigDecimal preUnitPriceMin;
    /**
     * 房源标签ID数组
     */
    private String tagIdArr;
    /**
     * 合同情况，记录时间、执行情况，用于判断是否逾期
     */
    private String contractInfo;
    /**
     * 在租合同数
     */
    private Integer contractCount;
    /**
     * 装修情况
     */
    private String decoration;
    /**
     * 产权性质
     */
    private String propertyRight;
    /**
     * 标准层高
     */
    private BigDecimal floorHeight;
    /**
     * 荷载值
     */
    private String loadMax;
    /**
     * 楼层ID
     */
    private Long layerId;
    /**
     * 单元ID
     */
    private Long unitId;
    /**
     * 楼栋ID
     */
    private Long buildId;
    /**
     * 分区ID
     */
    private Long zoneId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 排序，越大越前
     */
    private Integer sort;
    /**
     * 数据状态(1使用，0隐藏)
     */
    private Integer status;
    /**
     * 3D模型物体ID
     */
    private String threeDimensionalId;
    /**
     * 状态(<10空置状态，<20已预订，<30出租中)
     */
    private Integer roomStatus;
    /**
     * 房源类型
     */
    private String houseType;
    /**
     * 房源备案号
     */
    private String recordNo;
    /**
     * 推广佣金
     */
    private BigDecimal promoterMoney;
    /**
     * 推广佣金单位
     */
    private String promoterMoneyUnit;
    /**
     * 额外扩展配置
     */
    private String extraConfig;
    /**
     * vr链接
     */
    private String vrLink;
    /**
     * 视频地址
     */
    private String video;
    /**
     * VR视频导致的排序（VR和视频30，VR20，视频10，没有0）
     */
    private Integer vrVideoSort;
    /**
     * 月浏览数（计划任务统计）
     */
    private Integer monthHits;
    /**
     * 是否占用了父房间的面积
     */
    private Integer splitParentArea;
    /**
     * 是否锁定房源(1锁定，0正常(取消锁定))
     */
    private Integer isLock;
    /**
     * 0=真实房间;1=非真实房间
     */
    private Short isUnreal;
    /**
     * 锁定房源面积信息
     */
    private String extraLock;

}