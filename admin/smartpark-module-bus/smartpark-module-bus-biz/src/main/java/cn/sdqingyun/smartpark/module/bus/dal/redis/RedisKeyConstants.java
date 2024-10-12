package cn.sdqingyun.smartpark.module.bus.dal.redis;

/**
 * BUS Redis Key 枚举类
 *
 * @author 智慧园区
 */
public interface RedisKeyConstants {

    /**
     * 合同续租
     */
    String CONTRACT_RENEWAL_LEASE = "bus:contract:renewalLease:";

    /**
     * 变更
     */
    String CONTRACT_CHANGE = "bus:contract:change:";

}
