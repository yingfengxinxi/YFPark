package cn.sdqingyun.smartpark.module.bus.service.pay;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderBillPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderBillSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderBillDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 支付订单和账单表中间表	 Service 接口
 *
 * @author 智慧园区
 */
public interface PayOrderBillService {

    /**
     * 创建支付订单和账单表中间表	
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PayOrderBillSaveReqVO createReqVO);

    /**
     * 更新支付订单和账单表中间表	
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PayOrderBillSaveReqVO updateReqVO);

    /**
     * 删除支付订单和账单表中间表	
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得支付订单和账单表中间表	
     *
     * @param id 编号
     * @return 支付订单和账单表中间表	
     */
    PayOrderBillDO get(Long id);

    /**
     * 获得支付订单和账单表中间表	分页
     *
     * @param pageReqVO 分页查询
     * @return 支付订单和账单表中间表	分页
     */
    PageResult<PayOrderBillDO> getPage(PayOrderBillPageReqVO pageReqVO);

}