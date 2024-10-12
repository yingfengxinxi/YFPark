package cn.sdqingyun.smartpark.module.bus.service.park;

import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.impl.UUIDUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PARK_CAR_ORDER_NOT_EXISTS;

/**
 * 车的收费订单 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ParkCarOrderServiceImpl implements ParkCarOrderService {

    @Resource
    private ParkCarOrderMapper Mapper;

    @Resource
    private ParkCarsMapper parkCarsMapper;

    @Resource
    private ParkChargeMapper parkChargeMapper;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private ParkMapper parkMapper;

    @Resource
    private ParkCarsOperatorLogMapper parkCarsOperatorLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ParkCarOrderSaveReqVO createReqVO) throws JsonProcessingException, ParseException {

        createReqVO.setOrderId(UuidUtils.generateUuid().replaceAll("-", ""));
        String msg = "";
        String orderInfo = "";
        //1月租车，2储值车，3临时车
        if (createReqVO.getOrderType().equals("1")) {
            msg = "月租缴费";
            orderInfo = "月租车";
        }
        if (createReqVO.getOrderType().equals("2")) {
            msg = "余额储值";
            orderInfo = "储值车";
        }
        if (createReqVO.getOrderType().equals("3")) {
            msg = "临时停车";
            orderInfo = "临时车";
        }
        createReqVO.setOrderName(createReqVO.getCarNumber() + msg);
        if (createReqVO.getOrderType().equals("2")) {
            orderInfo = orderInfo + "充值" + createReqVO.getOrderMoney() + "元";

        } else {
            JSONObject jsonObject = JSONObject.parseObject(createReqVO.getOrderInfo());
            Integer month = Integer.valueOf(jsonObject.getString("month"));
            String startDay = jsonObject.getString("startDay");
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            String endDay = DateUtils.getMonthDate(sim.parse(startDay), month);
            orderInfo = orderInfo + "缴费" + month + "个月从" + startDay + "~" + endDay;
        }
        createReqVO.setOrderInfo(orderInfo);
        createReqVO.setOrderStatus("1");
        createReqVO.setIsDirect("0");
        createReqVO.setPayMoney(createReqVO.getOrderMoney());
        createReqVO.setPayTime(new Date());
        createReqVO.setPayMethod("0");
        createReqVO.setPayMethodTxt("现金收款");
        LambdaQueryWrapperX<ParkChargeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ParkChargeDO::getVillageId, createReqVO.getVillageId());
        queryWrapperX.eq(ParkChargeDO::getParkId, createReqVO.getParkId());
        queryWrapperX.orderByAsc(ParkChargeDO::getCreateTime);
        List<ParkChargeDO> parkChargeDOS = parkChargeMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(parkChargeDOS)) {
            createReqVO.setChargeId(parkChargeDOS.get(0).getId());
        }
        createReqVO.setInvoiceStatus("0");
        createReqVO.setRefundMoney(createReqVO.getOrderMoney());
        LambdaQueryWrapperX<ParkCarsDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
        queryWrapperX2.eq(ParkCarsDO::getCarNumber, createReqVO.getCarNumber());
        ParkCarsDO parkCarsDO = parkCarsMapper.selectOne(queryWrapperX2);
        createReqVO.setUid(parkCarsDO.getUserId());

        // 插入
        ParkCarOrderDO parkCarOrderDO = BeanUtils.toBean(createReqVO, ParkCarOrderDO.class);
        Mapper.insert(parkCarOrderDO);
        if (createReqVO.getOrderType().equals("2")) {
            //累加余额
            BigDecimal balance = parkCarsDO.getBalance().add(createReqVO.getOrderMoney());
            parkCarsDO.setBalance(balance);
            parkCarsMapper.updateById(parkCarsDO);
        }

        ParkCarsOperatorLogDO parkCarsOperatorLogDO = new ParkCarsOperatorLogDO();
        parkCarsOperatorLogDO.setType("3");
        parkCarsOperatorLogDO.setOrderId(createReqVO.getOrderId());
        parkCarsOperatorLogDO.setCarsId(parkCarsDO.getId());
        parkCarsOperatorLogDO.setBefore("");
        parkCarsOperatorLogDO.setAfter("");
        parkCarsOperatorLogMapper.insert(parkCarsOperatorLogDO);

        // 返回
        return parkCarOrderDO.getId();
    }

    @Override
    public void update(ParkCarOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        ParkCarOrderDO updateObj = BeanUtils.toBean(updateReqVO, ParkCarOrderDO.class);
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
            throw exception(PARK_CAR_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ParkCarOrderDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<ParkCarOrderDO> getPage(ParkCarOrderPageReqVO pageReqVO) {
        LambdaQueryWrapperX<ParkCarOrderDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getCarNumber())) {
            queryWrapperX.eq(ParkCarOrderDO::getCarNumber, pageReqVO.getCarNumber());
        }
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(ParkCarOrderDO::getVillageId, pageReqVO.getVillageId());
        }
        queryWrapperX.orderByDesc(ParkCarOrderDO::getPayTime);
        PageResult<ParkCarOrderDO> parkCarOrderDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<ParkCarOrderDO> list = parkCarOrderDOPageResult.getList();

        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(parkCarOrderDO -> {
                VillageDO villageDO = villageMapper.selectById(parkCarOrderDO.getVillageId());
                parkCarOrderDO.setVillageName(villageDO.getName());

                ParkDO parkDO = parkMapper.selectById(parkCarOrderDO.getParkId());
                parkCarOrderDO.setParkName(parkDO.getParkName());
            });
        }
        return parkCarOrderDOPageResult;
    }

}