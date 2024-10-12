package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 巡检点位数据 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_position")
@KeySequence("patrol_position_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolPositionDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构Id
     */
    private Long orgId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 巡检点编码
     */
    private String number;
    /**
     * 巡检点名称
     */
    private String name;
    /**
     * 资产位置id
     */
    private Long positionId;

    @Schema(description = "资产位置父级id集合（多个值使用逗号(,)隔开）", example = "1,2,3,4")
    private String positionParentIds;

    /**
     * 资产位置名称
     */
    @TableField(exist = false)
    private String positionName;
    /**
     * nfc卡号ID
     */
    private String nfcCardId;
    /**
     * 巡检表单id
     */
    private Long formId;
    /**
     * 巡检表单名称
     */
    @TableField(exist = false)
    private String formTitle;
    /**
     * 巡检点图片
     */
    private String images;
    /**
     * 绑定的资产Id
     */
    private String propertyId;
    /**
     * 启用状态;0=禁用,1=启用
     */
    private String status;

    //启用状态
    @TableField(exist = false)
    private String statusName;

}