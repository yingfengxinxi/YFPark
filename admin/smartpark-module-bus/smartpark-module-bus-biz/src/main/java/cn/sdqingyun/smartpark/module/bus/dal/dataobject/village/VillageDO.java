package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 社区 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village")
@KeySequence("village_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillageDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目名称缩写
     */
    private String shortName;
    /**
     * 项目介绍
     */
    @TableField("`describe`")
    private String describe;
    /**
     * 项目LOGO
     */
    private String logo;
    /**
     * 项目省市区地址
     */
    private String districtTxt;
    /**
     * 项目详细地址
     */
    private String address;
    /**
     * 落座经度（gcj02）
     */
    private BigDecimal lng;
    /**
     * 落座纬度（gcj02）
     */
    private BigDecimal lat;
    /**
     * 管理面积
     */
    private BigDecimal managementArea;
    /**
     * 可招商面积
     */
    private BigDecimal rentableArea;
    /**
     * 可招商房源数量
     */
    private Integer roomRentableCount;
    /**
     * 总房源数量
     */
    private Integer roomCount;
    /**
     * 标签（数组存储）
     */
    private String tagIdArr;
    /**
     * 项目公众号的微信号
     */
    private String wechatNumber;
    /**
     * 数据状态
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
     * 楼宇图片
     */
    private String dimensionalBgImg;
    /**
     * 房源到期的颜色值数组
     */
    private String roomStatusColor;
    /**
     * 当前管理的机构ID
     */
    private Long orgId;
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
     * 房间最低单价（月）
     */
    private BigDecimal roomMinPrice;
    /**
     * 房间平均单价（月）
     */
    private BigDecimal roomAveragePrice;
    /**
     * 附近公共交通相关信息
     */
    private String trafficInfo;
    /**
     * 业态
     */
    private String type;
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
     * 排序值
     */
    private Integer sort;
    /**
     * 围绕项目服务的子项目配置信息，例如人脸识别设备
     */
    private String microServiceConfig;

}