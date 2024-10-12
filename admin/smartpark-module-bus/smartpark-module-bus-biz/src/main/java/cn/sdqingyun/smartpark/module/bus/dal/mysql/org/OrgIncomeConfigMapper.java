package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构收支确认配置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgIncomeConfigMapper extends BaseMapperX<OrgIncomeConfigDO> {


    /**
     *
     * @param tenantId
     */
    void deleteByTenantId(@Param("tenantId") Long tenantId);
}