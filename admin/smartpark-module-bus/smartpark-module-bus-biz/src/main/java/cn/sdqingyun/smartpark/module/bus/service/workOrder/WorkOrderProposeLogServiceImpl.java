package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeLogMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工单操作日志 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderProposeLogServiceImpl implements WorkOrderProposeLogService {

    @Resource
    private WorkOrderProposeLogMapper Mapper;

    @Override
    public Long create(WorkOrderProposeLogSaveReqVO createReqVO) {
        // 插入
        WorkOrderProposeLogDO workOrderProposeLogDO = BeanUtils.toBean(createReqVO, WorkOrderProposeLogDO.class);
        Mapper.insert(workOrderProposeLogDO);
        // 返回
        return workOrderProposeLogDO.getId();
    }

    @Override
    public void update(WorkOrderProposeLogSaveReqVO updateReqVO) {

        // 更新
        WorkOrderProposeLogDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderProposeLogDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public WorkOrderProposeLogDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<WorkOrderProposeLogDO> getPage(WorkOrderProposeLogPageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderProposeLogDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}