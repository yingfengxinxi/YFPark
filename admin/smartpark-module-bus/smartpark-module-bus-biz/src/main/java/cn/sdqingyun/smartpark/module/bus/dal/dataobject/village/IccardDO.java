package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目IC卡表（可能会绑定人员，因不同设备需要而定） DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_iccard")
@KeySequence("village_iccard_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IccardDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 卡ID（128位加密）
     */
    private String cardId;
    /**
     * 房间ID
     */
    private Long roomId;
    /**
     * 楼层ID
     */
    private Long layerId;
    /**
     * 单元ID
     */
    private Long unitId;
    /**
     * 楼栋ID
     */
    private Long buildId;
    /**
     * 分区ID
     */
    private Long zoneId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 用户ID（可能为空）
     */
    private Long roomUserId;
    /**
     * 数据状态(1使用，0禁用)
     */
    private Integer status;

}