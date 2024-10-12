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
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目楼栋 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_build")
@KeySequence("village_build_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 楼栋编号
     */
    private String buildNumber;
    /**
     * 楼栋名称
     */
    private String buildName;
    /**
     * 楼宇lOGO
     */
    private String logo;
    /**
     * 分区ID
     */
    private Long zoneId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 有没有单元，1有，0没有
     */
    private Integer haveUnit;
    /**
     * 项目省市区地址
     */
    private String districtTxt;
    /**
     * 国家ID
     */
    private Long countryId;
    /**
     * 省份ID
     */
    private Long provinceId;
    /**
     * 城市ID
     */
    private Long cityId;
    /**
     * 区县ID
     */
    private Long districtId;
    /**
     * 街道/乡镇ID
     */
    private Long streetId;
    /**
     * 社区/村ID
     */
    private Long communityId;
    /**
     * 落座经度（gcj02）
     */
    private BigDecimal lng;
    /**
     * 落座纬度（gcj02）
     */
    private BigDecimal lat;
    /**
     * 地址
     */
    private String address;
    /**
     * 标准层高
     */
    private BigDecimal floorHeight;
    /**
     * 产权性质
     */
    private String propertyRight;
    /**
     * 建筑面积
     */
    private BigDecimal buildArea;
    /**
     * 产权面积
     */
    private BigDecimal propertyArea;
    /**
     * 可租面积
     */
    private BigDecimal rentableArea;
    /**
     * 自用面积
     */
    private BigDecimal selfUseArea;
    /**
     * 配套面积
     */
    private BigDecimal supportingArea;
    /**
     * 产权编号
     */
    private String propertyNumber;
    /**
     * 土地编号
     */
    private String landNumber;
    /**
     * 不动产证编号
     */
    private String estateNumber;
    /**
     * 车位面积
     */
    private BigDecimal parkingArea;
    /**
     * 车位数量
     */
    private Integer parkingCount;
    /**
     * 管理面积
     */
    private BigDecimal managementArea;
    /**
     * 房间总数
     */
    private Integer roomCount;
    /**
     * 在租面积
     */
    private BigDecimal rentInArea;
    /**
     * 在租房间数
     */
    private Integer rentInRoom;
    /**
     * 在租合同数
     */
    private Integer rentInContract;
    /**
     * 招商房间总数
     */
    private Integer invitationRoomCount;
    /**
     * 营收目标(数组存储)
     */
    private String revenueTarget;
    /**
     * 默认收支账户
     */
    private Long accountDefault;
    /**
     * 默认配置
     */
    private String extraConfig;
    /**
     * 楼宇标签
     */
    private String tagInfo;
    /**
     * 是否热门
     */
    private Integer isHot;
    /**
     * 排序，越大越前
     */
    private Integer sort;
    /**
     * 数据状态(1使用，0隐藏)
     */
    private Integer status;
    /**
     * 3D模型
     */
    private String threeDimensionalFile;
    /**
     * 3D模型物体ID
     */
    private String threeDimensionalId;
    /**
     * 实景图
     */
    private String dimensionalBgImg;

    /**
     * 当前管理的机构ID
     */
    private Long orgId;
}