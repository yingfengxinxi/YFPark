package cn.sdqingyun.smartpark.module.bus.service.bpm;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeavePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeaveRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeaveSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bpm.ContractLeaveDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import jakarta.validation.Valid;

import java.text.ParseException;
import java.util.List;

/**
 * 合同审批 Service 接口
 *
 * @author 智慧园区
 */
public interface ContractLeaveService {

    /**
     * 创建合同审批
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContractLeave(@Valid ContractLeaveSaveReqVO createReqVO);

    /**
     * 更新合同审批
     *
     * @param updateReqVO 更新信息
     */
    void updateContractLeave(@Valid ContractLeaveSaveReqVO updateReqVO);

    /**
     * 删除合同审批
     *
     * @param id 编号
     */
    void deleteContractLeave(Long id);

    /**
     * 获得合同审批
     *
     * @param id 编号
     * @return 合同审批
     */
    ContractLeaveDO getContractLeave(Long id);

    /**
     * 获得合同审批分页
     *
     * @param pageReqVO 分页查询
     * @return 合同审批分页
     */
    PageResult<ContractLeaveDO> getContractLeavePage(ContractLeavePageReqVO pageReqVO);

    /**
     * 更新合同审批审批状态-合同审批回调
     *
     * @param id 编号
     * @param status 审批状态
     */
    void updateContractAuditStatus(Long id,Integer status) throws ParseException;


    /**
     *
     * @param contractDO
     */
    public void updateOwnerAndRoomsForTerminatedOrCancelledContract(ContractDO contractDO);

    /**
    * @Author SUNk
    * @Description 查询审批列表
    * @Date 10:25 2024/7/18
    * @Param [contractLeaveSaveReqVO]
    * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.ContractLeaveRespVO>
    **/
    List<ContractLeaveRespVO> getList(ContractLeaveSaveReqVO contractLeaveSaveReqVO);

    /**
     * 获得合同审批
     *
     * @param contractLeaveSaveReqVO
     * @return 合同审批
     */
    ContractLeaveRespVO getOneByContractId(ContractLeaveSaveReqVO contractLeaveSaveReqVO);
}