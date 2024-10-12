package cn.sdqingyun.smartpark.module.bus.service.contract;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.AnnexPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.AnnexSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.AnnexDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

/**
 * 机构合同附件 Service 接口
 *
 * @author 智慧园区
 */
public interface AnnexService {

    /**
     * 创建机构合同附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAnnex(@Valid AnnexSaveReqVO createReqVO);

    /**
     * 更新机构合同附件
     *
     * @param updateReqVO 更新信息
     */
    void updateAnnex(@Valid AnnexSaveReqVO updateReqVO);

    /**
     * 删除机构合同附件
     *
     * @param id 编号
     */
    void deleteAnnex(Long id);

    /**
     * 获得机构合同附件
     *
     * @param id 编号
     * @return 机构合同附件
     */
    AnnexDO getAnnex(Long id);

    /**
     * 获得机构合同附件分页
     *
     * @param pageReqVO 分页查询
     * @return 机构合同附件分页
     */
    PageResult<AnnexDO> getAnnexPage(AnnexPageReqVO pageReqVO);

    /**
     *
     * @param contractId
     */
    void deleteByContractId(Long contractId);

    /**
     *
     * @param contractId
     * @return
     */
    List<AnnexDO> getByContractIdAnnexList(Long contractId);
}