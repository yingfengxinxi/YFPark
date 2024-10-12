package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 租客在租/绑定房间 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_room_rent")
@KeySequence("village_room_rent_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRentDO extends TenantBaseDO {

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
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 楼层id
     */
    private Long layerId;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 租赁到期时间
     */
    private LocalDateTime expireTime;
    /**
     * 来源标识;1=合同;2=物业绑定
     */
    private Integer source;
    /**
     * 房间状态;0=在租;
     */
    private Integer status;
    /**
     * 其他
     */
    private String extra;

}