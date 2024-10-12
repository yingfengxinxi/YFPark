package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户扩展信息自定义系统设置 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_user_field_extend")
@KeySequence("village_user_field_extend_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFieldExtendDO extends TenantBaseDO {

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
     * 关联 village_user_system_field_extend id
     */
    private Long sysFieldId;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 表单名称
     */
    private String inputName;
    /**
     * 表单名称自定义
     */
    private String inputNameCus;
    /**
     * 输入框类型
     */
    private String inputType;
    /**
     * 表单默认值 
     */
    private String defaultInputValue;
    /**
     * 表单默认值 
     */
    private String defaultFieldValue;
    /**
     * 是否启用，1-启用,0-禁用
     */
    private Integer isEnable;
    /**
     * 是否必填 1-必填
     */
    private Integer isRequired;
    /**
     * 是否允许修改表单名称，1允许，0不允许
     */
    private Integer isAllowModifie;
    /**
     * JSON 字段集合
     */
    private String jsonFields;
    /**
     * 排序
     */
    private Integer sort;

}