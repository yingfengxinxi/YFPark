package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
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
 * 耗材业务流程 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_process")
@KeySequence("property_stuff_process_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffProcessDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 审批ID
     */
    private String processCode;
    /**
     * 流程单据编号
     */
    private String processNumber;
    /**
     * 业务流程类型
     */
    private String processType;
    /**
     * 耗材物料id
     */
    private Long stuffId;
    /**
     * 所属分类id
     */
    private Long categoryId;
    /**
     * 所属仓库id
     */
    private Long depositoryId;
    /**
     * 调入仓库id
     */
    private Long inDepositoryId;
    /**
     * 物料名称
     */
    private String name;
    /**
     * 物料编码
     */
    private String number;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 规格型号
     */
    private String modelName;
    /**
     * 计量单位
     */
    private String meterUnit;
    /**
     * 入库单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 物料照片;支持多张
     */
    private String imageUrl;
    /**
     * 耗材物料信息状态
     */
    private Integer status;
    /**
     * 调整前数量-调整使用
     */
    private BigDecimal handoutNum;
    /**
     * 调整前单价-调整使用
     */
    private BigDecimal retreatNum;
    /**
     * 其他
     */
    private String extra;
    /**
     * stockId 即时库存ID
     */
    private Long cuserUid;
    /**
     * 处理uid
     */
    private Long muserUid;

}