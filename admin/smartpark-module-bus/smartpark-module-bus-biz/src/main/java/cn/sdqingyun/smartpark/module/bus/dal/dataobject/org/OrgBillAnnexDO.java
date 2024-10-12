package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 机构账单收支流水附件 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_annex")
@KeySequence("org_bill_annex_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillAnnexDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 附件唯一标识
     */
    private String objectId;
    /**
     * 流水id
     */
    private Long sourceId;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 置顶排序
     */
    private Long sort;
    /**
     * 文件名/文件夹
     */
    private String name;
    /**
     * 账单操作类型;1=账单附件;2=流水附件
     */
    private String type;
    /**
     * 1=pc;2=phone
     */
    private String annexSource;
    /**
     * 文件路径
     */
    private String filePath;

}