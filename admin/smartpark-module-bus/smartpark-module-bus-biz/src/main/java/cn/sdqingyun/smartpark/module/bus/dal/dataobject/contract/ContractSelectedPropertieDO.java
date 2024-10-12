package cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 合同中选中房源 DO
 *
 * @author 智慧园区
 */
@TableName("contract_selected_propertie")
@KeySequence("contract_selected_propertie_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractSelectedPropertieDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 园区id
     */
    private Long parkId;
    /**
     * 楼宇id
     */
    private Long buildId;
    @TableField(exist = false)
    private String buildName;
    /**
     * 房间id
     */
    private Long villageRoomId;

    @TableField(exist = false)
    private String roomName;
    @TableField(exist = false)
    private Integer floor;
    /**
     * 已选房源租赁面积
     */
    private BigDecimal rentalArea;

    /**
     * 租赁状态0=已租赁1=退租
     */
    private String status;

}