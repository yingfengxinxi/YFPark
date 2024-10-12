package cn.sdqingyun.smartpark.module.bus.service.pay;

import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderWorkOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderWorkOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.pay.PayOrderWorkOrderMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 支付订单和工单订单记录中间 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PayOrderWorkOrderServiceImpl implements PayOrderWorkOrderService {

    @Resource
    private PayOrderWorkOrderMapper Mapper;

    @Override
    public Long create(PayOrderWorkOrderSaveReqVO createReqVO) {
        // 插入
        PayOrderWorkOrderDO payOrderWorkOrderDO = BeanUtils.toBean(createReqVO, PayOrderWorkOrderDO.class);
        Mapper.insert(payOrderWorkOrderDO);
        // 返回
        return payOrderWorkOrderDO.getId();
    }

    @Override
    public void update(PayOrderWorkOrderSaveReqVO updateReqVO) {

        // 更新
        PayOrderWorkOrderDO updateObj = BeanUtils.toBean(updateReqVO, PayOrderWorkOrderDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public PayOrderWorkOrderDO get(Long id) {
        return Mapper.selectById(id);
    }


}