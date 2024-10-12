package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RESV_BOOKING_ORDER_REFUND_NOT_EXISTS;

/**
 * 预约订单退款 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ResvBookingOrderRefundServiceImpl implements ResvBookingOrderRefundService {

    @Resource
    private ResvBookingOrderRefundMapper resvBookingOrderRefundMapper;
    @Resource
    private ResvBookingMapper resvBookingMapper;
    @Resource
    private ResvBookingOrderMapper resvBookingOrderMapper;
    @Resource
    private ResvPlaceMapper resvPlaceMapper;
    @Resource
    private ResvPlaceCategoryMapper resvPlaceCategoryMapper;
    @Override
    public Long createResvBookingOrderRefund(ResvBookingOrderRefundSaveReqVO createReqVO) {
        // 插入
        ResvBookingOrderRefundDO resvBookingOrderRefund = BeanUtils.toBean(createReqVO, ResvBookingOrderRefundDO.class);
        resvBookingOrderRefundMapper.insert(resvBookingOrderRefund);
        // 返回
        return resvBookingOrderRefund.getId();
    }

    @Override
    public void updateResvBookingOrderRefund(ResvBookingOrderRefundSaveReqVO updateReqVO) {
        // 校验存在
        validateResvBookingOrderRefundExists(updateReqVO.getId());
        // 更新
        ResvBookingOrderRefundDO updateObj = BeanUtils.toBean(updateReqVO, ResvBookingOrderRefundDO.class);
        resvBookingOrderRefundMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvBookingOrderRefund(Long id) {
        // 校验存在
        validateResvBookingOrderRefundExists(id);
        // 删除
        resvBookingOrderRefundMapper.deleteById(id);
    }

    private void validateResvBookingOrderRefundExists(Long id) {
        if (resvBookingOrderRefundMapper.selectById(id) == null) {
            throw exception(RESV_BOOKING_ORDER_REFUND_NOT_EXISTS);
        }
    }

    @Override
    public ResvBookingOrderRefundDO getResvBookingOrderRefund(Long id) {
        return resvBookingOrderRefundMapper.selectById(id);
    }

    @Override
    public PageResult<ResvBookingOrderRefundDO> getResvBookingOrderRefundPage(ResvBookingOrderRefundPageReqVO pageReqVO) {
        return resvBookingOrderRefundMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ResvBookingOrderRefundRespVO> getResvBookingOrderRefundPageVO(ResvBookingOrderRefundPageReqVO pageReqVO) {
        PageResult<ResvBookingOrderRefundDO> result = resvBookingOrderRefundMapper.selectPage( pageReqVO );
        PageResult<ResvBookingOrderRefundRespVO> resultPage = BeanUtils.toBean( result, ResvBookingOrderRefundRespVO.class );
        if(CollUtil.isNotEmpty( resultPage.getList() )){
            for (ResvBookingOrderRefundRespVO vo : resultPage.getList()) {
                if(vo.getOrderId() != null){
                    if(vo.getBookingId() != null){
                        ResvBookingDO resvBookingDO = resvBookingMapper.selectById( vo.getBookingId() );
                        if(resvBookingDO != null){
                            vo.setUserName( resvBookingDO.getUserName() );
                            vo.setUserMobile( resvBookingDO.getUserMobile() );
                            vo.setOwnerName( resvBookingDO.getOwnerName() );
                            if(resvBookingDO.getOrderNo() != null){
                                ResvBookingOrderDO resvBookingOrderDO = resvBookingOrderMapper.selectOne( new LambdaQueryWrapperX<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, resvBookingDO.getOrderNo() ) );
                                if(resvBookingOrderDO != null && resvBookingOrderDO.getOrderTotal() != null){
                                    vo.setOrderTotal( resvBookingOrderDO.getOrderTotal() );
                                }
                            }
                            if(resvBookingDO.getPlaceId() != null){
                                ResvPlaceDO resvPlaceDO = resvPlaceMapper.selectOne( new LambdaQueryWrapperX<ResvPlaceDO>().eq( ResvPlaceDO::getId, resvBookingDO.getPlaceId() ) );
                                if(resvPlaceDO != null && resvPlaceDO.getName() != null){
                                    vo.setPlaceName( resvPlaceDO.getName() );
                                }
                            }
                            if(resvBookingDO.getCategoryId() != null){
                                ResvPlaceCategoryDO resvPlaceCategoryDO = resvPlaceCategoryMapper.selectOne( new LambdaQueryWrapperX<ResvPlaceCategoryDO>().eq( ResvPlaceCategoryDO::getId, resvBookingDO.getCategoryId() ) );
                                if(resvPlaceCategoryDO != null && resvPlaceCategoryDO.getName() != null){
                                    vo.setCategoryName( resvPlaceCategoryDO.getName() );
                                }
                            }

                        }
                    }
                }
            }
        }
        return resultPage;
    }
}