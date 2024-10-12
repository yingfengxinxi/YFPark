package cn.sdqingyun.smartpark.module.bus.service.pay;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderWorkOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderWorkOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderWorkOrderDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 支付订单和工单订单记录中间 Service 接口
 *
 * @author 管理员
 */
public interface PayOrderWorkOrderService {

    /**
     * 创建支付订单和工单订单记录中间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PayOrderWorkOrderSaveReqVO createReqVO);

    /**
     * 更新支付订单和工单订单记录中间
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PayOrderWorkOrderSaveReqVO updateReqVO);

    /**
     * 删除支付订单和工单订单记录中间
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得支付订单和工单订单记录中间
     *
     * @param id 编号
     * @return 支付订单和工单订单记录中间
     */
    PayOrderWorkOrderDO get(Long id);

}