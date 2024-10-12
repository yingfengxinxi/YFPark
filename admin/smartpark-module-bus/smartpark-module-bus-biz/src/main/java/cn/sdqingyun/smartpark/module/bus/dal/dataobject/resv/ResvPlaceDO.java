package cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 预约场地 DO
 *
 * @author 智慧园区
 */
@TableName("resv_place")
@KeySequence("resv_place_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResvPlaceDO extends TenantBaseDO {

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
     * 应用标识
     */
    private String appSign;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 场地名称
     */
    private String name;
    /**
     * 场地图片
     */
    private String images;
    /**
     * 场地地址
     */
    private String address;
    /**
     * 地址的冗余字段 用于前端回显
     */
    private String addressRest;
    /**
     * 场地纬度
     */
    private String latitude;
    /**
     * 场地经度
     */
    private String longitude;
    /**
     * 场地设施
     */
    private String facilityArr;
    /**
     * 收费规则ID
     */
    private Long billRuleId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 场地预约成功、通知的员工id
     */
    private String notifier;
    /**
     * 通知人json回显
     */
    private String notifierData;
    /**
     * 详细介绍
     */
    private String description;
    /**
     * 状态: 1为开启 0为关闭
     */
    private Integer status;

}