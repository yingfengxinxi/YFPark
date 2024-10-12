package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业务流程单据关联资产 DO
 *
 * @author 智慧园区
 */
@TableName("property_process_data")
@KeySequence("property_process_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyProcessDataDO extends TenantBaseDO {

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
     * 流程编号
     */
    private String processCode;
    /**
     * 流程单据编号
     */
    private String processNumber;
    /**
     * 资产id
     */
    private Long propertyId;
    /**
     * 资产编号
     */
    private String propertyNumber;
    /**
     * 资产分类
     */
    private Integer type;
    /**
     * 资产名称
     */
    private String name;
    /**
     * 资产状态
     */
    private Integer status;
    /**
     * 所在位置配置信息id
     */
    private Long positionId;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String modelName;
    /**
     * 目标房源
     */
    private String buildBind;
    /**
     * 资产保养上传的文件
     */
    private String maintainFile;
    /**
     * 资产保养上传的金额
     */
    private BigDecimal maintainPrice;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}