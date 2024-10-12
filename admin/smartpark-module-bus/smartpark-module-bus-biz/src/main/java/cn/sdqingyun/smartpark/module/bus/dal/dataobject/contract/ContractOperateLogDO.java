package cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 合同操作日志 DO
 *
 * @author 智慧园区
 */
@TableName("contract_operate_log")
@KeySequence("contract_operate_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractOperateLogDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 基础合同,合同主表id
     */
    private Integer contractId;
    /**
     * 操作内容
     */
    private String operateContent;

    /**
     * 合同类型
     */
    private String operateType;

    /**
     * 其他备注JSON
     */
    private String otherRemark;

}