package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 机构楼宇售方信息(发票设置) DO
 *
 * @author 智慧园区
 */
@TableName("org_seller")
@KeySequence("org_seller_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgSellerDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 售方公司名称
     */
    private String companyName;
    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;
    /**
     * 开户行
     */
    private String bank;
    /**
     * 开户行账号
     */
    private String bankAccount;
    /**
     * 售方电话信息
     */
    private String phone;
    /**
     * 开票地址
     */
    private String address;

}