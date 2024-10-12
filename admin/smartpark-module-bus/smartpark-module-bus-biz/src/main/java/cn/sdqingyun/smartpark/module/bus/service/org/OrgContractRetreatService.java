package cn.sdqingyun.smartpark.module.bus.service.org;

import java.net.MalformedURLException;
import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgContractRetreatDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构合同退租 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgContractRetreatService {

    /**
     * 创建机构合同退租
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgContractRetreatSaveReqVO createReqVO);

    /**
     * 更新机构合同退租
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgContractRetreatSaveReqVO updateReqVO);

    /**
     * 删除机构合同退租
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构合同退租
     *
     * @param id 编号
     * @return 机构合同退租
     */
    OrgContractRetreatDO get(Long id);

    /**
     * 获得机构合同退租分页
     *
     * @param pageReqVO 分页查询
     * @return 机构合同退租分页
     */
    PageResult<OrgContractRetreatDO> getPage(OrgContractRetreatPageReqVO pageReqVO);

    /**
     *
     * @param contractTemplateId
     * @param contractId
     * @return
     */
    String downloadProtocol(Long contractTemplateId,Long contractId) throws Exception;
}