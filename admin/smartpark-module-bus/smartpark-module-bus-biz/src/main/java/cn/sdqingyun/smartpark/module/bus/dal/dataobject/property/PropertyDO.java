package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产管理 DO
 *
 * @author 智慧园区
 */
@TableName("property")
@KeySequence("property_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDO extends TenantBaseDO {

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
     * 资产编码
     */
    private String propertyNumber;
    /**
     * 资产标签链接
     */
    private String labelLink;
    /**
     * 资产分类
     */
    private Long type;
    /**
     * 资产名称
     */
    private String name;
    /**
     * 资产状态
     */
    private Integer status;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String modelName;
    /**
     * 设备序列号
     */
    private String deviceCode;
    /**
     * 流程编号
     */
    private String processCode;
    /**
     * 管理员id
     */
    private Long adminId;
    /**
     * 管理员uid
     */
    private String adminName;
    /**
     * 目标房源项目id
     */
    private Long villageId;
    /**
     * 目标房源楼宇id
     */
    private Long buildId;
    /**
     * 目标房源房间id
     */
    private String roomId;
    /**
     * 绑定的目标房源json
     */
    private String buildBind;
    /**
     * 所在位置配置信息id
     */
    private Long positionId;
    /**
     * 资产操作时的位置信息
     */
    private String positionName;
    /**
     * 购置时间
     */
    private LocalDateTime purchaseTime;
    /**
     * 购置方式
     */
    private Integer purchaseType;
    /**
     * 购置金额(含税)
     */
    private BigDecimal purchaseAmount;
    /**
     * 入库时间
     */
    private LocalDateTime stockTime;
    /**
     * 预计使用期限(月)
     */
    private Long expectMonths;
    /**
     * 备注
     */
    private String remark;
    /**
     * 图片hash值
     */
    private String imageHash;
    /**
     * 资产图片url
     */
    private String imageUrl;
    /**
     * 使用人
     */
    private Long userId;
    /**
     * 使用部门id
     */
    private Long departmentId;
    /**
     * 领用日期
     */
    private LocalDateTime receiveTime;
    /**
     * 保养到期时间
     */
    private LocalDateTime maintainTime;
    /**
     * 保养说明
     */
    private String maintainInfo;
    /**
     * 预计折旧期限(月)
     */
    private Integer depreciationMonths;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;
    /**
     * 所属资产的知识库
     */
    private String knowledgeBase;

}