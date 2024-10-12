package cn.sdqingyun.smartpark.module.bus.service.resv;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingOrderDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.ResvBookingOrderMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 预约订单 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ResvBookingOrderServiceImpl implements ResvBookingOrderService {

    @Resource
    private ResvBookingOrderMapper resvBookingOrderMapper;

    @Override
    public Long createResvBookingOrder(ResvBookingOrderSaveReqVO createReqVO) {
        // 插入
        ResvBookingOrderDO resvBookingOrder = BeanUtils.toBean(createReqVO, ResvBookingOrderDO.class);
        resvBookingOrderMapper.insert(resvBookingOrder);
        // 返回
        return resvBookingOrder.getId();
    }

    @Override
    public void updateResvBookingOrder(ResvBookingOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateResvBookingOrderExists(updateReqVO.getId());
        // 更新
        ResvBookingOrderDO updateObj = BeanUtils.toBean(updateReqVO, ResvBookingOrderDO.class);
        resvBookingOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvBookingOrder(Long id) {
        // 校验存在
        validateResvBookingOrderExists(id);
        // 删除
        resvBookingOrderMapper.deleteById(id);
    }

    private void validateResvBookingOrderExists(Long id) {
        if (resvBookingOrderMapper.selectById(id) == null) {
            throw exception(RESV_BOOKING_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ResvBookingOrderDO getResvBookingOrder(Long id) {
        return resvBookingOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ResvBookingOrderDO> getResvBookingOrderPage(ResvBookingOrderPageReqVO pageReqVO) {
        return resvBookingOrderMapper.selectPage(pageReqVO);
    }

}