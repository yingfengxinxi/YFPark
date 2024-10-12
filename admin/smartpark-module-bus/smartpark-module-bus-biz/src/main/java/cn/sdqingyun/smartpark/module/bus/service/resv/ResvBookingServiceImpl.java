package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageUserDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageUserMapper;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 预约 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Slf4j
public class ResvBookingServiceImpl implements ResvBookingService {

    @Resource
    private ResvBookingMapper resvBookingMapper;
    @Resource
    private ResvBookingOrderMapper resvBookingOrderMapper;
    @Resource
    private ResvPlaceMapper resvPlaceMapper;
    @Resource
    private ResvPlaceCategoryMapper resvPlaceCategoryMapper;
    @Resource
    private ResvBillRuleMapper resvBillRuleMapper;
    @Resource
    private VillageUserMapper villageUserMapper;
    @Resource
    private OwnerMapper ownerMapper;
    @Resource
    private ResvBookingVerificationMapper resvBookingVerificationMapper;
    @Resource
    private ResvPayOrderMapper resvPayOrderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResvBookingSaveReqVO createResvBooking(ResvBookingSaveReqVO createReqVO) {

        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        if(loginUserId == null){
            throw new ServiceException(401,"用户未登录");
        }

        //查询用户相关联信息
        createReqVO.setUserId( loginUserId );

        //查询租客相关信息
        VillageUserDO villageUserDO = villageUserMapper.selectOne( new LambdaQueryWrapperX<VillageUserDO>().eq( VillageUserDO::getUserId, loginUserId ) );
        if(villageUserDO == null){
            throw new ServiceException(406,"用户未关联租客信息");
        }
        createReqVO.setUserName( villageUserDO.getName() );
        createReqVO.setUserMobile( villageUserDO.getPhone() );

        OwnerDO ownerDO = ownerMapper.selectById( villageUserDO.getOwnerId() );
        if(ownerDO == null){
            throw new ServiceException(406,"用户未关联业主信息");
        }
        createReqVO.setOwnerId( ownerDO.getId() );
        createReqVO.setOwnerName( ownerDO.getName() );

        //查询分类
        ResvPlaceCategoryDO resvPlaceCategoryDO = resvPlaceCategoryMapper.selectById( createReqVO.getCategoryId() );
        if(resvPlaceCategoryDO == null){
            throw exception(RESV_PLACE_CATEGORY_NOT_EXISTS);
        }

        createReqVO.setCapacity( resvPlaceCategoryDO.getCapacity() );
        //查询场地及rule
        ResvPlaceDO resvPlaceDO = resvPlaceMapper.selectById( createReqVO.getPlaceId() );
        if(resvPlaceDO == null){
            throw exception(RESV_PLACE_NOT_EXISTS);
        }

        ResvBillRuleDO resvBillRuleDO = resvBillRuleMapper.selectById( resvPlaceDO.getBillRuleId() );
        if(resvBillRuleDO == null){
            throw exception(RESV_BILL_RULE_NOT_EXISTS);
        }

        // 预约限制
        JSONObject jsonObject = new JSONObject(resvPlaceCategoryDO.getReservationRule());
        String startTime = jsonObject.getJSONObject("timeframe").getStr("start_time");
        String endTime = jsonObject.getJSONObject("timeframe").getStr("end_time");
        int earliestTime = jsonObject.getInt("earliest_time");

        //预约时间段
        JSONArray timeSlotsArray = new JSONArray(createReqVO.getTimeSlots());
        List<Map<String, String>> timeSlots = new ArrayList<>();
        for (int i = 0; i < timeSlotsArray.size(); i++) {
            JSONObject slot = timeSlotsArray.getJSONObject(i);
            Map<String, String> timeSlotMap = new HashMap<>();
            timeSlotMap.put("start_time", slot.getStr("start_time"));
            timeSlotMap.put("end_time", slot.getStr("end_time"));
            timeSlots.add(timeSlotMap);
        }

        // 检查预约时间
        LocalDate now = LocalDate.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        BigDecimal allTimeSlots = new BigDecimal( "0" );

        for (Map<String, String> timeSlot : timeSlots) {
            LocalDateTime dateTime = LocalDateTime.now().with(LocalTime.parse(timeSlot.get("start_time"), timeFormatter));
            if (now.plusDays(earliestTime).isBefore(dateTime.toLocalDate())) {
                throw new ServiceException(406,"当前预约时间已超过最大可提前预约时间，请重新选择预约时间");
            }
            LocalTime slotStartTime = LocalTime.parse(timeSlot.get("start_time"), timeFormatter);
            LocalTime slotEndTime = LocalTime.parse(timeSlot.get("end_time"), timeFormatter);
            LocalTime ruleStartTime = LocalTime.parse(startTime, timeFormatter);
            LocalTime ruleEndTime = LocalTime.parse(endTime, timeFormatter);

            if (slotStartTime.isBefore(ruleStartTime) || slotEndTime.isAfter(ruleEndTime)) {
                throw new ServiceException(406,"当前预约时间 " + slotStartTime + "~" + slotEndTime + " 不在每日可预定时间范围内，请重新选择预约时间");
            }

            // 将时间差转换为分钟
            long minutes = ChronoUnit.MINUTES.between(slotStartTime, slotEndTime);
            allTimeSlots = allTimeSlots.add(new BigDecimal(minutes));
        }

        //校验预约时间是否冲突
        LambdaQueryWrapperX<ResvBookingDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(ResvBookingDO::getPlaceId, createReqVO.getPlaceId())
                .eq(ResvBookingDO::getCategoryId, createReqVO.getCategoryId())
                .eq(ResvBookingDO::getAppSign, createReqVO.getAppSign())
                .eq(ResvBookingDO::getDate,DateUtil.format( createReqVO.getDate(), DatePattern.NORM_DATE_PATTERN ))
                .in(ResvBookingDO::getStatus, Arrays.asList(0, 1));
        List<ResvBookingDO> resvBookingDOList = resvBookingMapper.selectList(wrapperX);
        if(CollUtil.isNotEmpty( resvBookingDOList )){
            for (ResvBookingDO resvBookingDO : resvBookingDOList) {
                checkCommonTimeSlot( resvBookingDO.getTimeSlots(), createReqVO.getTimeSlots());
            }
        }

        //计算价格
        BigDecimal calculatePrice = calculatePrice( resvBillRuleDO, createReqVO, allTimeSlots );
        if(calculatePrice.compareTo( createReqVO.getOrderTotal() ) != 0){
            throw new ServiceException(406,"订单金额与计算金额不一致，请重新选择");
        }

        createReqVO.setRest( String.valueOf( allTimeSlots ) );
        //生成订单号
        String orderNo = buildOrderNo();
        createReqVO.setOrderNo( orderNo );

        // 插入booking
        ResvBookingDO resvBooking = BeanUtils.toBean(createReqVO, ResvBookingDO.class);
        resvBookingMapper.insert(resvBooking);

        ResvBookingOrderDO saveReqVO = new ResvBookingOrderDO();
        saveReqVO.setAppSign( createReqVO.getAppSign() );
        saveReqVO.setVillageId( createReqVO.getVillageId() );
        saveReqVO.setOrderNo( orderNo );
        saveReqVO.setOrderTotal( createReqVO.getOrderTotal() );
        saveReqVO.setDiscountAmount( BigDecimal.ZERO );
        saveReqVO.setOrderStatus( 0 );
        saveReqVO.setUserId( createReqVO.getUserId() );
        saveReqVO.setOwnerId( createReqVO.getOwnerId() );
        resvBookingOrderMapper.insert(saveReqVO);

        // 返回
        return createReqVO;
    }

    public BigDecimal calculatePrice(ResvBillRuleDO resvBillRuleDO, ResvBookingSaveReqVO createReqVO, BigDecimal allTimeSlots) {
        BigDecimal priceEnd = BigDecimal.ZERO;

        if (resvBillRuleDO.getIsMultiTimeCharge() == 1) {
            if (StrUtil.isNotEmpty(resvBillRuleDO.getMultiTimeChargeStandard())) {
                JSONArray multiTimeChargeStandardArray = new JSONArray(resvBillRuleDO.getMultiTimeChargeStandard());
                if (CollUtil.isNotEmpty(multiTimeChargeStandardArray)) {
                    JSONObject entry = multiTimeChargeStandardArray.getJSONObject(0);
                    if (entry.getJSONArray("week").contains(createReqVO.getWeekText())) {
                        priceEnd = priceEnd.add( calculateCharge(entry.getJSONObject("charge_standard"), allTimeSlots));
                    }else {
                        priceEnd = priceEnd.add( calculateCharge( new JSONObject( resvBillRuleDO.getChargeStandard() ), allTimeSlots) );
                    }
                }
            }
        } else {
            priceEnd = priceEnd.add( calculateCharge( new JSONObject( resvBillRuleDO.getChargeStandard() ), allTimeSlots) );
        }
        log.info( "计算价格最终：" + priceEnd );
        return priceEnd;
    }

    /**
    * @Author SUNk
    * @Description 计算价格
    * @Date 15:38 2024/7/31
    * @Param [chargeStandard, allTimeSlots, priceEnd]
    * @return java.math.BigDecimal
    **/
    private BigDecimal calculateCharge(JSONObject chargeStandard, BigDecimal allTimeSlots) {
        long firstBillingDuration = chargeStandard.getLong("first_billing_duration"); // 初次计费时长
        long firstBillingPrice = chargeStandard.getLong("first_billing_price");       // 初次计费价格
        long billingPrice = chargeStandard.getLong("billing_price");                 // 后续计费单价
        long billingDuration = chargeStandard.getLong("billing_duration");           // 后续计费时长

        // 如果在初次计费时长内，则只收取初次计费价格
        if (allTimeSlots.compareTo(new BigDecimal(firstBillingDuration)) <= 0) {
            return new BigDecimal(firstBillingPrice);
        } else {
            // 超过初次计费时长的剩余时间
            BigDecimal remainingTime = allTimeSlots.subtract(new BigDecimal(firstBillingDuration));

            // 计算后续计费部分的总费用，按后续计费时长分段计算
            BigDecimal additionalCharge = remainingTime.divide(new BigDecimal(billingDuration), 0, RoundingMode.UP)
                    .multiply(new BigDecimal(billingPrice));

            // 返回初次计费价格 + 后续计费费用
            return new BigDecimal(firstBillingPrice).add(additionalCharge);
        }
    }

    /**
    * @Author SUNk
    * @Description 获取订单编号
    * @Date 15:37 2024/7/31
    * @Param []
    * @return java.lang.String
    **/
    private String buildOrderNo() {
        // 获取当前时间戳，格式为yyyyMMddHHmmss，共14位
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 生成一个随机数，作为订单编号的后缀部分
        Random random = new Random();
        int randomNumber = random.nextInt(1000); // 生成0到999的随机数

        // 将时间戳和随机数拼接，并确保随机数为4位数，不足的前面补0
        String paddedRandomNumber = String.format("%04d", randomNumber);

        // 拼接最终的订单编号，确保总长度为28位
        return timestamp + paddedRandomNumber;
    }

    private void checkCommonTimeSlot(String timeSlots1, String timeSlots2){
        String[] split = timeSlots1.substring( 1, timeSlots1.length() - 1 ).split( "," );
        String[] split2 = timeSlots2.substring( 1, timeSlots2.length() - 1 ).split( "," );
        for (String s : split) {
            for (String s1 : split2) {
                if(s.equals(s1)){
                    throw new ServiceException(406,"当前预约时间 " + s + " 与已预约时间冲突，请重新选择预约时间");
                }
            }
        }
    }

    @Override
    public void updateResvBooking(ResvBookingSaveReqVO updateReqVO) {
        // 校验存在
        validateResvBookingExists(updateReqVO.getId());
        // 更新
        ResvBookingDO updateObj = BeanUtils.toBean(updateReqVO, ResvBookingDO.class);
        resvBookingMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvBooking(Long id) {
        // 校验存在
        validateResvBookingExists(id);
        // 删除
        resvBookingMapper.deleteById(id);
    }

    private void validateResvBookingExists(Long id) {
        if (resvBookingMapper.selectById(id) == null) {
            throw exception(RESV_BOOKING_NOT_EXISTS);
        }
    }

    @Override
    public ResvBookingDO getResvBooking(Long id) {
        return resvBookingMapper.selectById(id);
    }

    @Override
    public PageResult<ResvBookingDO> getResvBookingPage(ResvBookingPageReqVO pageReqVO) {
        return resvBookingMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ResvBookingRespVO> getResvBookingPageVO(ResvBookingPageReqVO pageReqVO) {
        PageResult<ResvBookingDO> result = resvBookingMapper.selectPage( pageReqVO );
        PageResult<ResvBookingRespVO> pageResult = BeanUtils.toBean( result, ResvBookingRespVO.class );
        if(CollUtil.isNotEmpty( pageResult.getList() )){
            for (ResvBookingRespVO vo : pageResult.getList()) {
                if(vo.getOrderNo() != null){
                    ResvBookingOrderDO resvBookingOrderDO = resvBookingOrderMapper.selectOne( new LambdaQueryWrapperX<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, vo.getOrderNo() ) );
                    if(resvBookingOrderDO != null && resvBookingOrderDO.getOrderTotal() != null){
                        vo.setOrderTotal( resvBookingOrderDO.getOrderTotal() );
                    }
                    //查询订单
                    ResvBookingOrderDO resvBookingOrder = resvBookingOrderMapper.selectOne( new LambdaQueryWrapperX<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, vo.getOrderNo() ) );
                    if(resvBookingOrder != null){
                        vo.setResvPayOrderId( resvBookingOrder.getId() );
                    }
                    //查询支付订单
                    ResvPayOrderDO payOrderDO = resvPayOrderMapper.selectOne( new LambdaQueryWrapperX<ResvPayOrderDO>().eq( ResvPayOrderDO::getOrderNo, vo.getOrderNo() ) );
                    if(payOrderDO != null){
                        vo.setPayId( payOrderDO.getId() );
                    }
                    //查询预约码
                    ResvBookingVerificationDO verificationDO = resvBookingVerificationMapper.selectOne( new LambdaQueryWrapperX<ResvBookingVerificationDO>().eq( ResvBookingVerificationDO::getOrderNo, vo.getOrderNo() ) );
                    if(verificationDO != null){
                        vo.setVerificationCode( verificationDO.getCode() );
                    }

                }
                if(vo.getPlaceId() != null){
                    ResvPlaceDO resvPlaceDO = resvPlaceMapper.selectOne( new LambdaQueryWrapperX<ResvPlaceDO>().eq( ResvPlaceDO::getId, vo.getPlaceId() ) );
                    if(resvPlaceDO != null && resvPlaceDO.getName() != null){
                        vo.setPlaceName( resvPlaceDO.getName() );
                    }
                }
                if(vo.getCategoryId() != null){
                    ResvPlaceCategoryDO resvPlaceCategoryDO = resvPlaceCategoryMapper.selectOne( new LambdaQueryWrapperX<ResvPlaceCategoryDO>().eq( ResvPlaceCategoryDO::getId, vo.getCategoryId() ) );
                    if(resvPlaceCategoryDO != null && resvPlaceCategoryDO.getName() != null){
                        vo.setCategoryName( resvPlaceCategoryDO.getName() );
                    }
                }
            }
        }

        return pageResult;

    }

    @Override
    public String getTimeSlots(ResvBookingSaveReqVO createReqVO) {
        //查询分类
        ResvPlaceCategoryDO resvPlaceCategoryDO = resvPlaceCategoryMapper.selectById( createReqVO.getCategoryId() );
        if(resvPlaceCategoryDO == null){
            throw exception(RESV_PLACE_CATEGORY_NOT_EXISTS);
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        JSONObject jsonObject = new JSONObject(resvPlaceCategoryDO.getReservationRule());
        LocalTime startTime = LocalTime.parse(jsonObject.getJSONObject("timeframe").getStr("start_time"), timeFormatter);
        LocalTime endTime = LocalTime.parse(jsonObject.getJSONObject("timeframe").getStr("end_time"), timeFormatter);
        String intervalUnit = jsonObject.getStr("interval_unit");
        int interval = jsonObject.getInt("interval");
        if(StringUtils.equals( intervalUnit, "hour" )){
            interval = interval * 60;
        }


        LambdaQueryWrapperX<ResvBookingDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(ResvBookingDO::getPlaceId, createReqVO.getPlaceId())
                .eq(ResvBookingDO::getCategoryId, createReqVO.getCategoryId())
                .eq(ResvBookingDO::getAppSign, createReqVO.getAppSign())
                .eq(ResvBookingDO::getDate, DateUtil.format( createReqVO.getDate(), DatePattern.NORM_DATE_PATTERN ))
                .in(ResvBookingDO::getStatus, Arrays.asList(0, 1));
        List<ResvBookingDO> resvBookingDOList = resvBookingMapper.selectList(wrapperX);

        // 生成时间段列表
        List<JSONObject> timeSlotsList = new ArrayList<>();
        while (startTime.isBefore(endTime)) {
            LocalTime slotEndTime = startTime.plusMinutes(interval);
            if (slotEndTime.isAfter(endTime)) {
                slotEndTime = endTime;
            }
            JSONObject timeSlot = new JSONObject();
            timeSlot.put("start_time", startTime.format(timeFormatter));
            timeSlot.put("end_time", slotEndTime.format(timeFormatter));
            timeSlot.put("status", 0); // 默认状态为0，表示未预定
            timeSlotsList.add(timeSlot);
            startTime = slotEndTime;
        }

        // 检查预定时间段
        for (ResvBookingDO resvBookingDO : resvBookingDOList) {
            if (resvBookingDO.getTimeSlots() != null) {
                JSONArray timeSlotsArray = new JSONArray(resvBookingDO.getTimeSlots());
                for (int i = 0; i < timeSlotsArray.size(); i++) {
                    JSONObject slot = timeSlotsArray.getJSONObject(i);
                    String bookedStartTime = slot.getStr("start_time");
                    String bookedEndTime = slot.getStr("end_time");

                    for (JSONObject timeSlot : timeSlotsList) {
                        if (timeSlot.getStr("start_time").equals(bookedStartTime) &&
                                timeSlot.getStr("end_time").equals(bookedEndTime)) {
                            timeSlot.put("status", 1); // 标记为已预定
                        }
                    }
                }
            }
        }

        return JSON.toJSONString(timeSlotsList);
    }
}