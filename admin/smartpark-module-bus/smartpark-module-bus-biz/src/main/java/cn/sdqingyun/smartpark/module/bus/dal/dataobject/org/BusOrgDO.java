package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 机构 DO
 *
 * @author 智慧园区管理员
 */
@TableName("org")
@KeySequence("org_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusOrgDO extends TenantBaseDO{

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构缩写名称
     */
    private String name;
    /**
     * 管理员用户id
     */
    private String adminUid;
    /**
     * 机构全名称
     */
    private String company;
    /**
     * 省份id
     */
    private Long provinceId;
    /**
     * 市id
     */
    private Long cityId;
    /**
     * 省市地址;英文逗号,拼接
     */
    private String districtName;
    /**
     * 机构介绍
     */
    private String info;
    /**
     * 机构logo
     */
    private String logo;
    /**
     * 正方形logo（建议透明的白色）
     */
    private String squareLogo;
    /**
     * 机构总人数
     */
    private Integer total;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 机构类别
     */
    private String type;
    /**
     * 机构专属域名前缀（若不填写则访问专属域名时展示平台信息，例如 admin-id.xxx.com）
     */
    private String domainPrefix;
    /**
     * 是否为OEM
     */
    private Integer isOem;
    /**
     * 公众号的微信号
     */
    private String wechatNumber;
    /**
     * 机构成员工号前缀(支持自定义)
     */
    private String orgPrefix;
    /**
     * 机构码(8位随机字符串)
     */
    private String joinCode;
    /**
     * 机构状态，1启动，0审核中，4禁用
     */
    private Integer status;
    /**
     * 字典（替换页面上的字）
     */
    private String dictionary;
    /**
     * 支持开放的模块权限
     */
    private String module;
    /**
     * 允许的项目类型
     */
    private String villageType;
    /**
     * 过期日期（当天23:59才过期）
     */
    private LocalDateTime overdueDay;
    /**
     * 招商电话
     */
    private String clueTel;
    /**
     * 招商时间
     */
    private String clueTime;
    /**
     * 注册来源(0自己注册，1企微，2后台添加，3钉钉...)
     */
    private Integer regFrom;
    /**
     * 最大可使用面积
     */
    private BigDecimal maxUseArea;
    /**
     * 最大可使用房间数
     */
    private Integer maxUseRoom;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

}