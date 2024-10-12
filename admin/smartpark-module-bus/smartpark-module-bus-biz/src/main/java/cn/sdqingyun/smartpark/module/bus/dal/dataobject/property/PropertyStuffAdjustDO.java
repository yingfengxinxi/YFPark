package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 耗材业务调整 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_adjust")
@KeySequence("property_stuff_adjust_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffAdjustDO extends TenantBaseDO {

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
     * 流程编号
     */
    private String processCode;
    /**
     * 单据编号
     */
    private String processNumber;
    /**
     * 调整仓库id
     */
    private Long depositoryId;
    /**
     * 调整时间
     */
    private LocalDateTime adjustTime;
    /**
     * 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
     */
    private Integer status;
    /**
     * 调整备注
     */
    private String remark;
    /**
     * 处理人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}