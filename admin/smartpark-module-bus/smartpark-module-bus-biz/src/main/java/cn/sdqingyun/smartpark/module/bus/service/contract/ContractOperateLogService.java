package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOperateLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOperateLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOperateLogDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

/**
 * 合同操作日志 Service 接口
 *
 * @author 智慧园区
 */
public interface ContractOperateLogService {

    /**
     * 创建合同操作日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperateLog(@Valid ContractOperateLogSaveReqVO createReqVO);

    /**
     * 更新合同操作日志
     *
     * @param updateReqVO 更新信息
     */
    void updateOperateLog(@Valid ContractOperateLogSaveReqVO updateReqVO);

    /**
     * 删除合同操作日志
     *
     * @param id 编号
     */
    void deleteOperateLog(Long id);

    /**
     * 获得合同操作日志
     *
     * @param id 编号
     * @return 合同操作日志
     */
    ContractOperateLogDO getOperateLog(Long id);

    /**
     * 获得合同操作日志分页
     *
     * @param pageReqVO 分页查询
     * @return 合同操作日志分页
     */
    PageResult<ContractOperateLogDO> getOperateLogPage(ContractOperateLogPageReqVO pageReqVO);

}