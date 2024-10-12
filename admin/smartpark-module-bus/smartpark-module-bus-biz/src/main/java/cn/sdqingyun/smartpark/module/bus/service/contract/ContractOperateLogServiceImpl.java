package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOperateLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOperateLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOperateLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOperateLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.OPERATE_LOG_NOT_EXISTS;

/**
 * 合同操作日志 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ContractOperateLogServiceImpl implements ContractOperateLogService {

    @Resource
    private ContractOperateLogMapper operateLogMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public Long createOperateLog(ContractOperateLogSaveReqVO createReqVO) {
        // 插入
        ContractOperateLogDO operateLog = BeanUtils.toBean(createReqVO, ContractOperateLogDO.class);
        operateLogMapper.insert(operateLog);
        // 返回
        return operateLog.getId();
    }

    @Override
    public void updateOperateLog(ContractOperateLogSaveReqVO updateReqVO) {
        // 校验存在
        validateOperateLogExists(updateReqVO.getId());
        // 更新
        ContractOperateLogDO updateObj = BeanUtils.toBean(updateReqVO, ContractOperateLogDO.class);
        operateLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperateLog(Long id) {
        // 校验存在
        validateOperateLogExists(id);
        // 删除
        operateLogMapper.deleteById(id);
    }

    private void validateOperateLogExists(Long id) {
        if (operateLogMapper.selectById(id) == null) {
            throw exception(OPERATE_LOG_NOT_EXISTS);
        }
    }

    @Override
    public ContractOperateLogDO getOperateLog(Long id) {
        return operateLogMapper.selectById(id);
    }

    @Override
    public PageResult<ContractOperateLogDO> getOperateLogPage(ContractOperateLogPageReqVO pageReqVO) {

        LambdaQueryWrapperX<ContractOperateLogDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractOperateLogDO::getContractId, pageReqVO.getContractId());
        queryWrapperX.eq(ContractOperateLogDO::getOperateType, "remark");
        PageResult<ContractOperateLogDO> contractOperateLogDOPageResult = operateLogMapper.selectPage(pageReqVO,queryWrapperX);
        List<ContractOperateLogDO> list = contractOperateLogDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(annexDO -> {
                String creator = annexDO.getCreator();
                String nickname = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                if (StringUtils.isNotEmpty(nickname)) {
                    annexDO.setCreator(nickname);
                }
            });
        }
        return contractOperateLogDOPageResult;
    }

}