package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 第三方数据对接（目前用于智慧社区系统，全功能版） DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_third_butt")
@KeySequence("village_third_butt_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdButtDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 第三方业务类别
     */
    private String businessType;
    /**
     * 第三方业务ID
     */
    private String businessId;
    /**
     * 自有数据ID
     */
    private String selfId;
    /**
     * 数据原有返回格式
     */
    private String originalData;

}