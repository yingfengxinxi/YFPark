package cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerFiles;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 租客附件 DO
 *
 * @author 智慧园区
 */
@TableName("owner_files")
@KeySequence("owner_files_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerFilesDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 租客ID
     */
    private Long ownerId;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 用户服务ID
     */
    private Long uid;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件网址
     */
    private String url;

}