package cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 预约场地分类 DO
 *
 * @author 智慧园区
 */
@TableName("resv_place_category")
@KeySequence("resv_place_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResvPlaceCategoryDO extends TenantBaseDO {

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
     * 所属项目ID
     */
    private Long villageId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类图片
     */
    private String imageUrl;
    /**
     * 预约限制
     */
    private String reservationRule;
    /**
     * 场地可容纳人数
     */
    private Integer capacity;
    /**
     * 联系人电话
     */
    private String contactMobile;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 详细介绍
     */
    private String description;
    /**
     * 冗余字段
     */
    private String cache;
    /**
     * 状态: 1为开启 0为关闭
     */
    private Integer status;

    /**
     * 二维码路径
     */
    private String qrCode;
}