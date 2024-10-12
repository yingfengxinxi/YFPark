package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.common.util.qrcode.QRCodeUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.*;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.RESV_BOOKING_VERIFICATION_NOT_EXISTS;

/**
 * 预约核销 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ResvBookingVerificationServiceImpl implements ResvBookingVerificationService {

    @Resource
    private ResvBookingVerificationMapper resvBookingVerificationMapper;
    @Resource
    private ResvBookingMapper resvBookingMapper;
    @Resource
    private ResvBookingOrderMapper resvBookingOrderMapper;
    @Resource
    private ResvPlaceMapper resvPlaceMapper;
    @Resource
    private ResvPlaceCategoryMapper resvPlaceCategoryMapper;
    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createResvBookingVerification(ResvBookingVerificationSaveReqVO createReqVO) {
        //根据订单号查询
        // 插入
        createReqVO.setStatus( 0 );
        ResvBookingVerificationDO resvBookingVerification = BeanUtils.toBean(createReqVO, ResvBookingVerificationDO.class);
        resvBookingVerificationMapper.insert(resvBookingVerification);

        //生成核销码
        byte[] bytes = QRCodeUtils.generateQRCode( resvBookingVerification.getId().toString(), 350, 350 );
        String file = fileApi.createFile( bytes );

        //回更文件路径
        resvBookingVerification.setCode( file );
        resvBookingVerificationMapper.updateById( resvBookingVerification );

        return resvBookingVerification.getId();
    }

    @Override
    public void updateResvBookingVerification(Long id) {
        ResvBookingVerificationDO resvBookingVerificationDO = resvBookingVerificationMapper.selectById( id );
        // 校验存在
        if (resvBookingVerificationDO == null) {
            throw exception(RESV_BOOKING_VERIFICATION_NOT_EXISTS);
        }
        // 更新
        resvBookingVerificationDO.setStatus(1);
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        resvBookingVerificationDO.setUserId( loginUserId );
        resvBookingVerificationDO.setHandleTime( LocalDateTime.now() );
        resvBookingVerificationDO.setHandler( loginUserId );
        resvBookingVerificationDO.setHandlerName( SecurityFrameworkUtils.getLoginUserNickname() );
        resvBookingVerificationMapper.updateById(resvBookingVerificationDO);
        //更新预约单相关信息
        resvBookingMapper.updateById( new ResvBookingDO().setId( resvBookingVerificationDO.getBookingId() ).setStatus( 2 ) );
        resvBookingOrderMapper.update( new LambdaUpdateWrapper<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, resvBookingVerificationDO.getOrderNo() ).set( ResvBookingOrderDO::getOrderStatus, 2) );
    }

    @Override
    public void deleteResvBookingVerification(Long id) {
        // 校验存在
        validateResvBookingVerificationExists(id);
        // 删除
        resvBookingVerificationMapper.deleteById(id);
    }

    private void validateResvBookingVerificationExists(Long id) {
        if (resvBookingVerificationMapper.selectById(id) == null) {
            throw exception(RESV_BOOKING_VERIFICATION_NOT_EXISTS);
        }
    }

    @Override
    public ResvBookingVerificationDO getResvBookingVerification(Long id) {
        return resvBookingVerificationMapper.selectById(id);
    }

    @Override
    public PageResult<ResvBookingVerificationDO> getResvBookingVerificationPage(ResvBookingVerificationPageReqVO pageReqVO) {
        return resvBookingVerificationMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ResvBookingVerificationRespVO> getResvBookingVerificationPageVO(ResvBookingVerificationPageReqVO pageReqVO) {
        PageResult<ResvBookingVerificationDO> result = resvBookingVerificationMapper.selectPage( pageReqVO );
        PageResult<ResvBookingVerificationRespVO> voPageResult = BeanUtils.toBean( result, ResvBookingVerificationRespVO.class );
        if(CollUtil.isNotEmpty( voPageResult.getList() )){
            for (ResvBookingVerificationRespVO vo : voPageResult.getList()) {
                if(vo.getBookingId() != null){
                    ResvBookingDO resvBookingDO = resvBookingMapper.selectById( vo.getBookingId() );
                    if(resvBookingDO != null){
                        vo.setUserName( resvBookingDO.getUserName() );
                        vo.setUserMobile( resvBookingDO.getUserMobile() );
                        vo.setOwnerName( resvBookingDO.getOwnerName() );
                        if(resvBookingDO.getOrderNo() != null){
                            vo.setOrderTotal( resvBookingOrderMapper.selectOne( new LambdaQueryWrapperX<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, resvBookingDO.getOrderNo() ) ).getOrderTotal() );
                        }
                        if(resvBookingDO.getPlaceId() != null){
                            vo.setPlaceName( resvPlaceMapper.selectOne( new LambdaQueryWrapperX<ResvPlaceDO>().eq( ResvPlaceDO::getId, resvBookingDO.getPlaceId() ) ).getName() );
                        }
                        if(resvBookingDO.getCategoryId() != null){
                            vo.setCategoryName( resvPlaceCategoryMapper.selectOne( new LambdaQueryWrapperX<ResvPlaceCategoryDO>().eq( ResvPlaceCategoryDO::getId, resvBookingDO.getCategoryId())).getName() );
                        }
                        vo.setRest( resvBookingDO.getRest() );
                    }
                }
            }
        }
        return voPageResult;


    }

    @Override
    public Map<String, Object> getCountVerification(Long handler) {
        Map<String, Object> resultMap = new HashMap<>(4);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1).minusSeconds(1);
        LocalDateTime startOfMonth = now.toLocalDate().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atTime(23, 59, 59);

        Long thisDayCount = resvBookingVerificationMapper.selectCount(
                new LambdaQueryWrapperX<ResvBookingVerificationDO>()
                        .eq(ResvBookingVerificationDO::getHandler, handler)
                        .eq(ResvBookingVerificationDO::getStatus, 1)
                        .between(ResvBookingVerificationDO::getHandleTime, startOfToday, endOfToday)
        );

        Long thisMonthCount = resvBookingVerificationMapper.selectCount(
                new LambdaQueryWrapperX<ResvBookingVerificationDO>()
                        .eq(ResvBookingVerificationDO::getHandler, handler)
                        .eq(ResvBookingVerificationDO::getStatus, 1)
                        .between(ResvBookingVerificationDO::getHandleTime, startOfMonth, endOfMonth)
        );

        resultMap.put("thisDay", thisDayCount);
        resultMap.put("thisMonth", thisMonthCount);

        return resultMap;
    }
}