package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPayorderApprovalPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPayorderApprovalSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderPaymentOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderPayorderApprovalDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderPayorderApprovalMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工单付款单审批记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderPayorderApprovalServiceImpl implements WorkOrderPayorderApprovalService {

    @Resource
    private WorkOrderPayorderApprovalMapper Mapper;

    @Override
    public Long create(WorkOrderPayorderApprovalSaveReqVO createReqVO) {
        // 插入
        WorkOrderPayorderApprovalDO workOrderPayorderApprovalDO  = BeanUtils.toBean(createReqVO, WorkOrderPayorderApprovalDO.class);
        Mapper.insert(workOrderPayorderApprovalDO);
        // 返回
        return workOrderPayorderApprovalDO.getId();
    }

    @Override
    public void update(WorkOrderPayorderApprovalSaveReqVO updateReqVO) {

        // 更新
        WorkOrderPayorderApprovalDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderPayorderApprovalDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public WorkOrderPayorderApprovalDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<WorkOrderPayorderApprovalDO> getPage(WorkOrderPayorderApprovalPageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderPayorderApprovalDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}