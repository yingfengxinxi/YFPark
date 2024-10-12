package cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 租客联系人 DO
 *
 * @author 智慧园区
 */
@TableName("owner_contacts")
@KeySequence("owner_contacts_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerContactsDO extends BaseDO {

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
     * 联系人姓名
     */
    private String name;
    /**
     * 手机
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 所属公司id
     */
    private Long ownerId;
    /**
     * 通讯地址
     */
    private String address;
    /**
     * 是否默认联系人
     */
    private Integer isDefault;

}