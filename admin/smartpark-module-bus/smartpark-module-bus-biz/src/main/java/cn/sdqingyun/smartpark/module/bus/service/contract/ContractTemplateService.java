package cn.sdqingyun.smartpark.module.bus.service.contract;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractTemplateDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构合同模板配置 Service 接口
 *
 * @author 智慧园区
 */
public interface ContractTemplateService {

    /**
     * 创建机构合同模板配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ContractTemplateSaveReqVO createReqVO);

    /**
     * 更新机构合同模板配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid ContractTemplateSaveReqVO updateReqVO);

    /**
     * 删除机构合同模板配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构合同模板配置
     *
     * @param id 编号
     * @return 机构合同模板配置
     */
    ContractTemplateDO get(Long id);

    /**
     * 获得机构合同模板配置分页
     *
     * @param pageReqVO 分页查询
     * @return 机构合同模板配置分页
     */
    PageResult<ContractTemplateDO> getPage(ContractTemplatePageReqVO pageReqVO);


    /**
     *
     * @param relationBuild
     * @return
     */
    List<ContractTemplateDO> getRelationBuildIdTemplateList(String relationBuild);
    /**
     *
     * @param name
     * @param id
     * @return
     */
    Boolean isCheckName(String name,Long id);

    /**
     *
     * @param buildBind
     * @param id
     * @return
     */
    Boolean isCheckBuild(String buildBind,Long id);
}