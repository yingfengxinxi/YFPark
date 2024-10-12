package cn.sdqingyun.smartpark.module.bus.dal.mysql.contract;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractExpireRuleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合同到期提醒规则 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ContractExpireRuleMapper extends BaseMapperX<ContractExpireRuleDO> {


    /**
     * 校验名称或者楼宇是否已经存在
     *
     * @param tenantId
     * @param ruleName
     * @param relationBuild
     * @param id
     * @return
     */
    Integer getTenantIdExpireRuleCount(@Param("tenantId") Long tenantId, @Param("ruleName") String ruleName, @Param("relationBuild") String relationBuild, @Param("id") Long id);

}