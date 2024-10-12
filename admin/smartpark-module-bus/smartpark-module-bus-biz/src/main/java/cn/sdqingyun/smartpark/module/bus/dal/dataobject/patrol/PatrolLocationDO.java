package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;//package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;
//
//import com.alibaba.excel.annotation.ExcelProperty;
//import lombok.*;
//import java.util.*;
//import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
//import java.time.LocalDateTime;
//import java.time.LocalDateTime;
//import com.baomidou.mybatisplus.annotation.*;
//import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
//
///**
// * 位置 DO
// *
// * @author 智慧园区
// */
//@TableName("patrol_location")
//@KeySequence("patrol_location_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
//@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class PatrolLocationDO extends TenantBaseDO {
//
//    /**
//     * 编号
//     */
//    @TableId
//    private Long id;
//    /**
//     * 机构ID
//     */
//    private Long orgId;
//    /**
//     * 位置编码
//     */
//    private String number;
//    /**
//     * 位置名称
//     */
//    private String name;
//    /**
//     * 位置级别字符串，逗号拼接上级id
//     */
//    private String level;
//
//    /**
//     *
//     */
//    private String levelName;
//    /**
//     * 父级id
//     */
//    private Long parentId;
//
//    /**
//     *
//     */
//    @TableField(exist = false)
//    private String parentName;
//    /**
//     * 参数
//     */
//    private String param;
//    /**
//     * 备注
//     */
//    private String remark;
//    /**
//     * 排序
//     */
//    private Integer sort;
//    /**
//     * 显示状态，2禁用 1显示，0隐藏
//     */
//    private String status;
//
//}