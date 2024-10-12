package cn.sdqingyun.smartpark.module.bus.dal.mysql.bpm;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bpm.ContractLeaveDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo.*;

/**
 * 合同审批 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ContractLeaveMapper extends BaseMapperX<ContractLeaveDO> {

    default PageResult<ContractLeaveDO> selectPage(ContractLeavePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ContractLeaveDO>()
                .eqIfPresent(ContractLeaveDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(ContractLeaveDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(ContractLeaveDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(ContractLeaveDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ContractLeaveDO::getContractId, reqVO.getContractId())
                .eqIfPresent(ContractLeaveDO::getContractNumber, reqVO.getContractNumber())
                .eqIfPresent(ContractLeaveDO::getProcessInstanceId, reqVO.getProcessInstanceId())
                .betweenIfPresent(ContractLeaveDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ContractLeaveDO::getId));
    }

}