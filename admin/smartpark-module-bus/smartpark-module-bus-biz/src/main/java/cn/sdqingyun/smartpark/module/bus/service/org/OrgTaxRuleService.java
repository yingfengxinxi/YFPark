package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxRuleDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 税率规则配置 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgTaxRuleService {

    /**
     * 创建税率规则配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgTaxRuleSaveReqVO createReqVO);

    /**
     * 更新税率规则配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgTaxRuleSaveReqVO updateReqVO);

    /**
     * 删除税率规则配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得税率规则配置
     *
     * @param id 编号
     * @return 税率规则配置
     */
    OrgTaxRuleDO get(Long id);

    /**
     * 获得税率规则配置分页
     *
     * @param pageReqVO 分页查询
     * @return 税率规则配置分页
     */
    PageResult<OrgTaxRuleDO> getPage(OrgTaxRulePageReqVO pageReqVO);


    /**
     *
     * @param costType
     * @param id
     * @return
     */
    Boolean isCheckCostType(String costType,Long id);

    /**
     *
     * @return
     */
    List<OrgTaxRuleDO>getAllTaxRuleList();
}