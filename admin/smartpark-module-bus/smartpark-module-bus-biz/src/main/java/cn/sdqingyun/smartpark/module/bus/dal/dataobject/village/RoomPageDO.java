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
 * 招商中心装修页 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_room_page")
@KeySequence("village_room_page_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomPageDO extends TenantBaseDO {

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
     * 内容信息json
     */
    private String content;
    /**
     * 最近修改的时间
     */
    private LocalDateTime lastTime;
    /**
     * 来源 0 pc端 1 移动端
     */
    private Integer isMobile;
    /**
     * 启用状态;0=关闭;1=开启
     */
    private Integer status;
    /**
     * 创建人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}