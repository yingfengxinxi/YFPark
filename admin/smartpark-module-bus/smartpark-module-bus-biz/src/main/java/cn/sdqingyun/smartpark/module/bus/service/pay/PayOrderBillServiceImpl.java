package cn.sdqingyun.smartpark.module.bus.service.pay;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderBillPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderBillSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.pay.PayOrderBillMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PAY_ORDER_BILL_NOT_EXISTS;

/**
 * 支付订单和账单表中间表	 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PayOrderBillServiceImpl implements PayOrderBillService {

    @Resource
    private PayOrderBillMapper Mapper;

    @Override
    public Long create(PayOrderBillSaveReqVO createReqVO) {
        // 插入
        PayOrderBillDO payOrderBillDO = BeanUtils.toBean(createReqVO, PayOrderBillDO.class);
        Mapper.insert(payOrderBillDO);
        // 返回
        return payOrderBillDO.getId();
    }

    @Override
    public void update(PayOrderBillSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        PayOrderBillDO updateObj = BeanUtils.toBean(updateReqVO, PayOrderBillDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(PAY_ORDER_BILL_NOT_EXISTS);
        }
    }

    @Override
    public PayOrderBillDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<PayOrderBillDO> getPage(PayOrderBillPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PayOrderBillDO>queryWrapperX=new LambdaQueryWrapperX<>();

        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}