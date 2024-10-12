package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPaymentOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPaymentOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderPaymentOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderPaymentOrderMapper;
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
 * 付款单记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderPaymentOrderServiceImpl implements WorkOrderPaymentOrderService {

    @Resource
    private WorkOrderPaymentOrderMapper Mapper;

    @Override
    public Long create(WorkOrderPaymentOrderSaveReqVO createReqVO) {
        // 插入
        WorkOrderPaymentOrderDO workOrderPaymentOrderDO = BeanUtils.toBean(createReqVO, WorkOrderPaymentOrderDO.class);
        Mapper.insert(workOrderPaymentOrderDO);
        // 返回
        return workOrderPaymentOrderDO.getId();
    }

    @Override
    public void update(WorkOrderPaymentOrderSaveReqVO updateReqVO) {

        // 更新
        WorkOrderPaymentOrderDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderPaymentOrderDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }



    @Override
    public WorkOrderPaymentOrderDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<WorkOrderPaymentOrderDO> getPage(WorkOrderPaymentOrderPageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderPaymentOrderDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}