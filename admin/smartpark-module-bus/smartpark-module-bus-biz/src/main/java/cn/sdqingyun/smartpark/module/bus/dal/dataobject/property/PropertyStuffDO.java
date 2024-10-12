package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 耗材档案信息 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff")
@KeySequence("property_stuff_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffDO extends TenantBaseDO {

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
     * 物料编码
     */
    private String number;
    /**
     * 物料名称
     */
    private String name;
    /**
     * 物料分类id
     */
    private Long categoryId;
    /**
     * 商品条码
     */
    private String barCode;
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
     * 小数位数(数量)
     */
    private BigDecimal quantityDigit;
    /**
     * 小数位数(单价)
     */
    private BigDecimal priceDigit;
    /**
     * 成本计算方法;1=加权平均,2=批次管理
     */
    private Integer computeMethod;
    /**
     * 是否固定入库单价;0=否1=是
     */
    private Integer lockPrice;
    /**
     * 入库单价;最多保留小数点后四位
     */
    private BigDecimal price;
    /**
     * 安全固定上限数量
     */
    private BigDecimal safeStockUp;
    /**
     * 安全固定下限数量
     */
    private BigDecimal safeStockLower;
    /**
     * 限领数量(人/月)
     */
    private BigDecimal receiveLimit;
    /**
     * 是否允许退库;0=否;1=是
     */
    private Short hasReturn;
    /**
     * 物料照片;支持多张
     */
    private String imageUrl;
    /**
     * 耗材状态;1=启用;0=禁用
     */
    private Short status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 操作人uid
     */
    private Integer cuserUid;
    /**
     * 修改人uid
     */
    private Integer muserUid;

}