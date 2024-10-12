package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.hutool.http.HttpUtil;
import cn.sdqingyun.smartpark.framework.common.enums.order.PayOrderDisplayModeEnum;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.currency.CurrencyConverter;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddlePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAdjustDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillStreamMiddleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.expenseclause.ExpenseClauseMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillStreamMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillAdjustMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillNoticeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.pay.PayOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillStreamMiddleService;
import cn.sdqingyun.smartpark.module.pay.api.client.ClientApi;
import cn.sdqingyun.smartpark.module.pay.api.order.PayOrderApi;
import cn.sdqingyun.smartpark.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.sdqingyun.smartpark.module.pay.api.order.vo.PayOrderSubmitReqVO;
import cn.sdqingyun.smartpark.module.pay.api.payapp.PayAppApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.ORDER_BILL_NOT_EXISTS;

/**
 * 机构合同账单明细 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class ContractOrderBillServiceImpl implements ContractOrderBillService {

    @Resource
    private ContractOrderBillMapper orderBillMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ExpenseClauseMapper expenseClauseMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    @Lazy // 延迟加载，解决循环依赖的问题
    private OrgBillNoticeMapper orgBillNoticeMapper;

    @Autowired
    private BillStreamMapper billStreamMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Resource
    private OrgBillAdjustMapper orgBillAdjustMapper;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private BuildMapper buildMapper;


    @Autowired
    private PayOrderApi payOrderApi;

    @Autowired
    private ClientApi clientApi;

    @Autowired
    private PayAppApi payAppApi;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PayOrderBillMapper payOrderBillMapper;

    @Resource
    private OrgBillStreamMiddleService orgBillStreamMiddleService;

    @Override
    public Long create(ContractOrderBillSaveReqVO createReqVO) {

        createReqVO.setOrderNumber(UuidUtils.generateUuid().replaceAll("-", ""));
        // 插入
        ContractOrderBillDO orderBill = BeanUtils.toBean(createReqVO, ContractOrderBillDO.class);
        orderBillMapper.insert(orderBill);
        // 返回
        return orderBill.getId();
    }

    /**
     * @param createBillVO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBill(CreateBillVO createBillVO) throws Exception {
        //账单
        ContractOrderBillDO contractOrderBillDO = BeanUtils.toBean(createBillVO, ContractOrderBillDO.class);
        contractOrderBillDO.setOrderNumber(UuidUtils.generateUuid().replaceAll("-", ""));
        contractOrderBillDO.setDataSource("1");
        contractOrderBillDO.setBillSource("5");
        contractOrderBillDO.setBillStatus("0");
        contractOrderBillDO.setClauseType("1");
        orderBillMapper.insert(contractOrderBillDO);

        //创建收支流水
        if (StringUtils.equals(createBillVO.getIsCreateStream(), "1")) {
            BillStreamDO billStream = BeanUtils.toBean(createBillVO, BillStreamDO.class);
            billStream.setStreamNumber(UuidUtils.generateUuid().replace("-", ""));
            billStream.setStreamSource("1");
            billStream.setDataSource("1");
            billStream.setBillType(contractOrderBillDO.getBillType());
            billStream.setIsClose("1");
            billStream.setIsConfirm("1");
            billStream.setMatchDate(new Date());
            billStream.setNomatchPrice(billStream.getAmount());
            billStream.setMatchStatus("2");
            billStream.setCompanyId(billStream.getOwnerId());
            billStream.setCompanyName(createBillVO.getOwnerName());
            if (StringUtils.isNotEmpty(createBillVO.getStreamRemark())) {
                billStream.setRemark(createBillVO.getStreamRemark());
            }

            billStreamMapper.insert(billStream);

            List<Long> streamIds = Lists.newArrayList();
            streamIds.add(billStream.getId());
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            String matchDate = sim.format(new Date());
            orgBillStreamMiddleService.addCollectionMiddle(contractOrderBillDO.getId(), streamIds, billStream.getAmount(), matchDate);
        }

        // 返回
        return contractOrderBillDO.getId();
    }

    @Override
    public void updateOrderBill(ContractOrderBillSaveReqVO updateReqVO) {
        // 校验存在
        validateOrderBillExists(updateReqVO.getId());
        // 更新
        ContractOrderBillDO updateObj = BeanUtils.toBean(updateReqVO, ContractOrderBillDO.class);
        orderBillMapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateOrderBillExists(id);
        // 删除
        orderBillMapper.deleteById(id);
    }

    private void validateOrderBillExists(Long id) {
        if (orderBillMapper.selectById(id) == null) {
            throw exception(ORDER_BILL_NOT_EXISTS);
        }
    }

    @Override
    public ContractOrderBillDO getOrderBill(Long id) {
        return orderBillMapper.selectById(id);
    }

    @Override
    public List<Long> getOrderBillContractIdList(List<String> billIdList) {
        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(ContractOrderBillDO::getId, billIdList);
        List<ContractOrderBillDO> contractOrderBillDOS = orderBillMapper.selectList(queryWrapperX);

        return contractOrderBillDOS.stream().map(ContractOrderBillDO::getContractId).distinct().collect(Collectors.toList());
    }

    /**
     * 根据合同编号查询未付款账单
     *
     * @param contractId
     * @return
     */
    @Override
    public List<ContractOrderBillDO> getByContractIdOrderBillList(Long contractId) {
        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractOrderBillDO::getContractId, contractId);
        queryWrapperX.in(ContractOrderBillDO::getBillStatus, Arrays.asList("0,2".split(",")));
        queryWrapperX.eq(ContractOrderBillDO::getDataSource, "1");
        return orderBillMapper.selectList(queryWrapperX);
    }

    /**
     * @param orderNumber
     * @return
     */
    @Override
    public ContractOrderBillDO getByOrderNumberInfo(String orderNumber) {
        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractOrderBillDO::getOrderNumber, orderNumber);
        return orderBillMapper.selectOne(queryWrapperX);
    }

    @Override
    public PageResult<ContractOrderBillDO> getOrderBillPage(ContractOrderBillPageReqVO pageReqVO) {
        ContractDO contractDO = contractMapper.selectById(pageReqVO.getContractId());
        if (StringUtils.equals(contractDO.getStatus(), "0")) {

            return new PageResult<ContractOrderBillDO>();
        }
        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractOrderBillDO::getContractId, pageReqVO.getContractId());
        if (StringUtils.isNotEmpty(pageReqVO.getStartPayDate())) {
            queryWrapperX.apply("DATE_FORMAT(pay_date,'%Y-%m-%d') between DATE_FORMAT(" + pageReqVO.getStartPayDate() + ",'%Y-%m-%d') and DATE_FORMAT(" + pageReqVO.getEndPayDate() + ",'%Y-%m-%d') ");
        }
        PageResult<ContractOrderBillDO> contractOrderBillDOPageResult = orderBillMapper.selectPage(pageReqVO, queryWrapperX);
        return contractOrderBillDOPageResult;
    }

    @Override
    public List<ContractOrderBillDO> getOrderBillList(LambdaQueryWrapper<ContractOrderBillDO> queryWrapper) {

        return orderBillMapper.selectList(queryWrapper);
    }

    /**
     * 查询逾期账单
     *
     * @return
     */
    @Override
    public List<ContractOrderBillDO> getOrderBillBeOverdueList() {

        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        List<String> billStatusList = Lists.newArrayList();
        billStatusList.add("0");
        billStatusList.add("2");
        queryWrapperX.in(ContractOrderBillDO::getBillStatus, billStatusList);
        queryWrapperX.apply("DATE_FORMAT(pay_date,'%Y-%m-%d') < DATE_FORMAT(" + new Date() + ",'%Y-%m-%d') ");

        List<ContractOrderBillDO> contractOrderBillDOPageResult = orderBillMapper.selectList(queryWrapperX);
        return contractOrderBillDOPageResult;
    }

    /**
     * @param contractId
     * @param startPayDate
     * @param endPayDate
     * @return
     */
    @Override
    public List<ContractIdCompyOrderBillListVO> getByContractIdCompyOrderBillList(Long contractId, String startPayDate, String endPayDate) {
        List<ContractIdCompyOrderBillListVO> orderBillList = orderBillMapper.getByContractIdCompyOrderBillList(contractId, startPayDate, endPayDate);
        if (CollectionUtils.isNotEmpty(orderBillList)) {
            orderBillList.forEach(contractIdCompyOrderBillListVo -> {
                if (StringUtils.isNotEmpty(contractIdCompyOrderBillListVo.getRoomNumber())) {
                    JSONObject jsonObject = getRoomName(contractIdCompyOrderBillListVo.getRoomNumber());
                    String roomName = jsonObject.getString("roomName");
                    if (StringUtils.isNotEmpty(roomName)) {
                        contractIdCompyOrderBillListVo.setRoomNumber(roomName);
                    }
                }

            });
        }
        return orderBillList;
    }

    private JSONObject getRoomName(String roomNumber) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roomName", "");
        jsonObject.put("roomInfo", "");

        if (StringUtils.isNotEmpty(roomNumber)) {
            List<JSONObject> roomList = Lists.newArrayList();
            String[] roomNumbers = roomNumber.split(",");
            StringBuilder sb = new StringBuilder();
            for (String roomId : roomNumbers) {
                RoomDO roomDO = roomMapper.selectById(roomId);
                if (roomDO != null) {
                    String roomName = roomDO.getRoomName();
                    String lc = roomName.substring(0, roomName.length() - 2); // 截取左边的数据
                    roomName = lc + "-" + roomName;
                    sb.append(roomName).append(",");
                    JSONObject roomInfo = new JSONObject();
                    roomInfo.put("roomId", roomDO.getId());
                    roomInfo.put("roomName", roomName);
                    roomList.add(roomInfo);
                }

            }
            String roomName = sb.toString();
            if (StringUtils.isNotEmpty(roomName)) {
                jsonObject.put("roomName", roomName.substring(0, roomName.length() - 1));
            }
            jsonObject.put("roomInfo", new Gson().toJson(roomList));
        }
        return jsonObject;
    }

    /**
     * @param contractId
     */
    @Override
    public void deleteByContractId(Long contractId) {

        LambdaQueryWrapper<ContractOrderBillDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContractOrderBillDO::getContractId, contractId);
        orderBillMapper.delete(queryWrapper);
    }

    @Override
    public List<ContractOrderBillDO> getByContractIdRentingTerminationBondBillList(Long contractId) {

        return orderBillMapper.getByContractIdRentingTerminationBondBillList(contractId);
    }

    @Override
    public List<ContractOrderBillDO> getByContractIdRentingTerminationBillList(Long contractId) {
        List<ContractOrderBillDO> terminationBillList = orderBillMapper.getByContractIdRentingTerminationBillList(contractId);
        if (CollectionUtils.isNotEmpty(terminationBillList)) {

            terminationBillList.forEach(contractOrderBillDO -> {
                //手动添加的账单不做处理
                if ("0".equals(contractOrderBillDO.getDataSource())) {
                    //非逾期账单进行处理
                    if (contractOrderBillDO.getFlag().equals("0")) {
                        ContractDO contractDO = contractMapper.selectById(contractId);
                        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                        String format = sim.format(new Date());
                        try {
                            long time = sim.parse(format).getTime();
                            long time1 = sim.parse(contractOrderBillDO.getPayStartDateStr()).getTime();
                            if (time >= time1) {
                                LambdaQueryWrapper<ExpenseClauseDO> queryWrapper = new LambdaQueryWrapper<>();
                                queryWrapper.eq(ExpenseClauseDO::getContractId, contractId);
                                queryWrapper.eq(ExpenseClauseDO::getClauseType, contractOrderBillDO.getClauseType());
                                ExpenseClauseDO expenseClauseDO = expenseClauseMapper.selectOne(queryWrapper);

                                String multipleClause = expenseClauseDO.getMultipleClause();
                                JSONArray jsonArray = JSONArray.parseArray(multipleClause);
                                String contractUnitPrice = jsonArray.getJSONObject(contractOrderBillDO.getCount() - 1).getString("contractUnitPrice");
                                //计算每天的退租金额
                                if ("0".equals(contractUnitPrice)) {
                                    contractOrderBillDO.setFlag("4");
                                    contractOrderBillDO.setBillType("2");
                                    //计算退租金额
                                    Long dayCount = DateUtils.getDayCount(sim.parse(contractOrderBillDO.getPayStartDateStr()), new Date()) + 1;
                                    String finalUnitPrice = contractOrderBillDO.getFinalUnitPrice();
                                    BigDecimal bigDecimal = new BigDecimal(dayCount).multiply(new BigDecimal(finalUnitPrice));

                                    if ("1".equals(contractDO.getIsReceivableInteger())) {
                                        //四舍五入
                                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal)), 2);
                                        bigDecimal = new BigDecimal(roundedTwoDecimalPlaces);
                                    } else {
                                        bigDecimal = new BigDecimal(Tools.DecimalFormat(bigDecimal));
                                    }
                                    contractOrderBillDO.setReceivablePayableAmount(bigDecimal.toString());
                                }
                            } else {
                                //全额退租
                                //需要展示在页面中的
                                contractOrderBillDO.setFlag("4");
                                //机构付款
                                contractOrderBillDO.setBillType("2");
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        //需要展示在页面中的
                        contractOrderBillDO.setFlag("4");
                    }
                } else {
                    //需要展示在页面中的
                    contractOrderBillDO.setFlag("4");
                }
            });

            terminationBillList = terminationBillList.stream().filter(contractOrderBillDO -> contractOrderBillDO.getFlag().equals("4")).collect(Collectors.toList());
        }
        return terminationBillList;
    }


    @Override
    public List<ContractOrderBillDO> getPaymentNoticeList() {
        return orderBillMapper.getPaymentNoticeList();
    }

    /**
     * @return
     */
    @Override
    public List<ContractOrderBillDO> getBeOverdueList() {
        return orderBillMapper.getBeOverdueList();
    }

    @Override
    public void updateByIdBillLateFeeOverdue(Long id, String billStatus, String lateFee, Integer overdueDay) {
        orderBillMapper.updateByIdBillLateFeeOverdue(id, billStatus, lateFee, overdueDay);
    }

    /**
     * @param ownerId
     * @param roomId
     * @param isDisplayReceived    已显示已收账单0=隐藏1=显示
     * @param isAccountsReceivable 已显示未到应收期账单0=隐藏1=显示
     * @return
     */
    @Override
    public List<Map<String, Object>> getByOwnerIdRoomIdBillList(Long ownerId, Long roomId, String isDisplayReceived, String isAccountsReceivable) {
        List<String> billStatusList = Lists.newArrayList();
        if ("0".equals(isDisplayReceived)) {
            billStatusList.add("0");
            billStatusList.add("2");
        }
        if ("1".equals(isAccountsReceivable)) {
            isAccountsReceivable = "";
        }
        List<ContractOrderBillDO> ownerIdRoomIdBillList = orderBillMapper.getByOwnerIdRoomIdBillList(ownerId, roomId, billStatusList, isAccountsReceivable);
        List<Map<String, Object>> mapList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ownerIdRoomIdBillList)) {
            for (ContractOrderBillDO contractOrderBillDO : ownerIdRoomIdBillList) {
                String receivablePayableAmount = contractOrderBillDO.getReceivablePayableAmount();
                contractOrderBillDO.setReceivablePayableAmount(Tools.DecimalFormat(receivablePayableAmount));
                LambdaQueryWrapperX<OrgBillNoticeDO> queryWrapper = new LambdaQueryWrapperX<>();
                queryWrapper.apply("REGEXP_LIKE(contract_bill_id, '(^|,)" + contractOrderBillDO.getId() + "($|,)')");
                int size = orgBillNoticeMapper.selectList(queryWrapper).size();
                contractOrderBillDO.setIsCs(0);
                if (size >= 1) {
                    contractOrderBillDO.setIsCs(1);
                }

            }

            //根据付款日期进行分组
            Map<String, List<ContractOrderBillDO>> groupedItems = ownerIdRoomIdBillList.stream()
                    .collect(Collectors.groupingBy(ContractOrderBillDO::getPayDateStr));

            int i = 0;
            for (String payDate : groupedItems.keySet()) {
                Map<String, Object> map = new HashMap<>();
                i++;
                List<ContractOrderBillDO> contractOrderBillDOS = groupedItems.get(payDate);
                contractOrderBillDOS.forEach(contractOrderBillDO -> {
                    String billStatus = contractOrderBillDO.getBillStatus();
                    String dataLabel = DictFrameworkUtils.getDictDataLabel("BILL_STATUS", billStatus);
                    contractOrderBillDO.setBillStatusName(dataLabel);
                });

                //第几次
                map.put("num", i);
                //付款时间
                map.put("payDate", payDate);
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                //有效期
                map.put("termValidity", sim.format(contractOrderBillDOS.get(0).getPayStartDate()) + "~" + sim.format(contractOrderBillDOS.get(contractOrderBillDOS.size() - 1).getPayEndDate()));
                //几项
                map.put("count", contractOrderBillDOS.size());
                //应收租金
                double receivable = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivable())).sum();
                map.put("receivable", receivable);
                //滞纳金
                double lateFee = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getLateFee())).sum();
                map.put("lateFee", lateFee);
                //应收金额
                double receivablePayableAmount = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayableAmount())).sum();
                map.put("receivablePayableAmount", receivablePayableAmount);

                //实收金额
                double receivablePayment = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayment())).sum();
                map.put("receivablePayment", receivablePayment);

                //状态
                int size = contractOrderBillDOS.stream().filter(contractOrderBillDO -> contractOrderBillDO.getBillStatus().equals("0") || contractOrderBillDO.getBillStatus().equals("2")).collect(Collectors.toList()).size();


                if (receivablePayment > 0) {
                    map.put("billStatus", "部分已收");
                } else {
                    map.put("billStatus", size >= 1 ? "待收" : "已付款");
                }

                int isCs = contractOrderBillDOS.stream().filter(contractOrderBillDO -> contractOrderBillDO.getIsCs() >= 1).collect(Collectors.toList()).size();
                if (isCs == contractOrderBillDOS.size()) {
                    map.put("isCs", "1");
                } else {
                    map.put("isCs", "0");
                }

                //实收金额
                double ssMoney = contractOrderBillDOS.
                        stream().
                        filter(contractOrderBillDO -> contractOrderBillDO.getBillStatus().equals("1")).
                        collect(Collectors.toList()).
                        stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayableAmount())).sum();
                map.put("ssMoney", ssMoney);

                map.put("data", contractOrderBillDOS);

                mapList.add(map);
            }
        }

        return mapList;
    }

    /**
     * @param ids
     * @param buildType
     * @return
     */
    @Override
    public Map<String, Object> getNotificationNumber(List<String> ids, String buildType) {
        Map<String, Object> map = new HashMap<>();
        //生成方式;1=逐张生成;2=按租客合并;3=按合同合并
        Integer count = 0;
        if ("1".equals(buildType)) {
            // 逐张生成
            count = ids.size();
        }
        if ("2".equals(buildType)) {
            //按租客合并
            List<BillListVO> byIdBillList = orderBillMapper.getByIdBillList(ids);
            Map<String, List<BillListVO>> collect = byIdBillList.stream()
                    .collect(Collectors.groupingBy(BillListVO::getOwnerId));


            count = collect.size();
        }

        if ("3".equals(buildType)) {
            //按合同合并
            List<BillListVO> byIdBillList = orderBillMapper.getByIdBillList(ids);
            Map<String, List<BillListVO>> collect = byIdBillList.stream()
                    .collect(Collectors.groupingBy(BillListVO::getContractId));
            count = collect.size();
        }

        map.put("count", count);

        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(ContractOrderBillDO::getId, ids);
        List<ContractOrderBillDO> contractOrderBillDOS = orderBillMapper.selectList(queryWrapperX);
        //应交金额
        BigDecimal receivable = new BigDecimal("0.00");
        //滞纳金
        BigDecimal lateFee = new BigDecimal("0.00");
        //实缴金额
        BigDecimal receivablePayment = new BigDecimal("0.00");
        if (CollectionUtils.isNotEmpty(contractOrderBillDOS)) {
            for (ContractOrderBillDO contractOrderBillDO : contractOrderBillDOS) {
                receivable = receivable.add(new BigDecimal(contractOrderBillDO.getReceivable()));
                lateFee = lateFee.add(new BigDecimal(contractOrderBillDO.getLateFee()));
                receivablePayment = receivablePayment.add(new BigDecimal(contractOrderBillDO.getReceivablePayment()));
            }
        }
        receivable = receivable.add(lateFee);
        map.put("money", Tools.DecimalFormat(receivable.subtract(receivablePayment)));
        return map;
    }

    /**
     * @param idList
     * @return
     */
    @Override
    public List<BillListVO> getByIdBillList(List<String> idList) {
        return orderBillMapper.getByIdBillList(idList);
    }

    @Override
    public Map<String, Object> getCollectionBillList(List<String> ids) {
        Map<String, Object> map = new HashMap<>();

        List<ContractOrderBillCollectionVO> collectionBillList = orderBillMapper.getCollectionBillList(ids);
        if (CollectionUtils.isNotEmpty(collectionBillList)) {
            double receivable = collectionBillList.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayableAmount())).sum();
            BigDecimal receivablePayableAmount = new BigDecimal(receivable);


            //实际应收总金额
            map.put("receivablePayableAmount", Tools.DecimalFormat(receivablePayableAmount));

            //已收总金额
            BigDecimal receivablePayment = new BigDecimal(collectionBillList.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayment())).sum());
            map.put("receivablePayment", receivablePayment);

            //待收金额
            BigDecimal toBeCollected = receivablePayableAmount.subtract(receivablePayment);
            map.put("toBeCollected", Tools.DecimalFormat(toBeCollected));

            if (receivablePayment.compareTo(BigDecimal.ZERO) > 0) {
                map.put("billStatus", "部分已收");
            }
            map.put("billStatus", "待收");

            map.put("data", collectionBillList);
        }
        return map;
    }

    @Override
    public void updateOrderBillReceivablePayment(String thisPayment, Long id) {
        orderBillMapper.updateOrderBillReceivablePayment(thisPayment, id);
    }


    /**
     * @param pageVo
     * @return
     */
    @Override
    public PageResult<GetBillListVO> getBillList(GetBillListVO pageVo) {
        //      List<Long> roomNumberList = Lists.newArrayList();
//        if (StringUtils.isNotEmpty(pageVo.getRoomNumber())) {
//            LambdaQueryWrapperX<RoomDO> queryWrapperX = new LambdaQueryWrapperX<>();
//            queryWrapperX.like(RoomDO::getRoomName, pageVo.getRoomNumber());
//            List<RoomDO> roomDOS = roomMapper.selectList(queryWrapperX);
//            roomNumberList = roomDOS.stream().map(RoomDO::getId).collect(Collectors.toList());
//        }
        IPage<GetBillListVO> pageResult = orderBillMapper.getBillList(MyBatisUtils.buildPage(pageVo), pageVo.getOwnerId(), "1");
        List<GetBillListVO> getBillListList = pageResult.getRecords();
        if (CollectionUtils.isNotEmpty(getBillListList)) {
            getBillListList.stream().forEach(getBillListVO -> {
                StringBuilder sb = new StringBuilder();
                String[] roomNumbers = getBillListVO.getRoomNumber().split(",");
                for (String roomId : roomNumbers) {
                    RoomDO roomDO = roomMapper.selectById(roomId);
                    if (roomDO != null) {
                        String roomName = roomDO.getRoomName();
                        String lc = roomName.substring(0, roomName.length() - 2);
                        sb.append(lc + "-" + roomName).append(",");
                    }
                }

                String roomName = sb.toString();
                if (StringUtils.isNotEmpty(roomName)) {
                    roomName = roomName.substring(0, roomName.length() - 1);
                    getBillListVO.setRoomName(roomName);
                }

            });
        }

        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }

    /**
     * @param ownerId
     * @param roomId
     * @return
     */
    @Override
    public Map<String, Object> getOwnerIdOverdueIds(Long ownerId, Long roomId) {

        Map<String, Object> map = new HashMap<>();

        //逾期账单
        List<Long> ownerIdOverdueIds = orderBillMapper.getOwnerIdOverdueIds(ownerId, roomId);
        map.put("beOverdueCount", ownerIdOverdueIds);

        //累计流水
        BigDecimal amount = billStreamMapper.getOwnerIdAmountTotal(ownerId, roomId);
        map.put("amount", Tools.DecimalFormat(amount));

        //未匹配金额
        BigDecimal ownerIdNomatchPriceTotal = billStreamMapper.getOwnerIdNomatchPriceTotal(ownerId, roomId);
        map.put("nomatchPrice", Tools.DecimalFormat(ownerIdNomatchPriceTotal));


        //需收金额
        BigDecimal amountCollected = orderBillMapper.getOwnerIdAmountCollectedTotal(ownerId, roomId);
        map.put("amountCollected", Tools.DecimalFormat(amountCollected));

        //应收金额
        BigDecimal receivable = orderBillMapper.getOwnerIdReceivableTotal(ownerId, roomId);
        map.put("receivable", Tools.DecimalFormat(receivable));
        return map;
    }

    @Override
    public PageResult<OwnerFinanceListVO> ownerFinanceList(OwnerFinanceListVO pageVo) {
        String month = pageVo.getMonth();
        if (month.length() == 1) {
            pageVo.setMonth("0" + month);
        }
        IPage<OwnerFinanceListVO> ownerFinanceListVOIPage = orderBillMapper.ownerFinanceList(MyBatisUtils.buildPage(pageVo), pageVo);
        List<OwnerFinanceListVO> records = ownerFinanceListVOIPage.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(ownerFinanceListVO -> {
                if (!StringUtils.equals(ownerFinanceListVO.getBillStatus(), "1")) {
                    //未付款
                    BigDecimal receivablePayment = ownerFinanceListVO.getReceivablePayment();
                    if (receivablePayment.compareTo(BigDecimal.ZERO) > 0) {
                        if (StringUtils.equals(ownerFinanceListVO.getBillStatus(), "2")) {
                            ownerFinanceListVO.setBillStatus("逾期部分结清");
                        } else {
                            ownerFinanceListVO.setBillStatus("部分结清");
                        }
                    } else {
                        if (StringUtils.equals(ownerFinanceListVO.getBillStatus(), "0")) {
                            ownerFinanceListVO.setBillStatus("待付款");
                        }

                        if (StringUtils.equals(ownerFinanceListVO.getBillStatus(), "2")) {
                            ownerFinanceListVO.setBillStatus("逾期");
                        }
                    }
                } else {
                    ownerFinanceListVO.setBillStatus("已付款");
                }

            });
        }

        return new PageResult<>(ownerFinanceListVOIPage.getRecords(), ownerFinanceListVOIPage.getTotal());
    }

    @Override
    public ContractOrderBillDO detail(Long billId) {
        ContractOrderBillDO contractOrderBillDO = orderBillMapper.selectById(billId);
        if (contractOrderBillDO != null) {
            ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
            if (contractDO != null) {
                contractOrderBillDO.setContractNumber(contractDO.getContractNumber());
            }

            Long ownerId = contractDO.getOwnerId();
            OwnerDO ownerDO = ownerMapper.selectById(ownerId);
            if (ownerDO != null) {
                String name = ownerDO.getName();
                contractOrderBillDO.setPayName(name);
            }
            String userName = systemUserMapper.getByOperatorIdUserName(contractDO.getOperatorId());
            contractOrderBillDO.setOperatorName(userName);
        }
        return contractOrderBillDO;
    }

    /**
     * @param billId
     * @return
     */
    @Override
    public IncomeDetailVO incomeDetail(Long billId) {
        IncomeDetailVO incomeDetailVO = new IncomeDetailVO();

        ContractOrderBillDO contractOrderBillDO = orderBillMapper.selectById(billId);
        if (contractOrderBillDO != null) {
            incomeDetailVO.setBillStatus(contractOrderBillDO.getBillStatus());
            incomeDetailVO.setReceivable(contractOrderBillDO.getReceivable());
            BigDecimal amountToBeCollected = new BigDecimal(contractOrderBillDO.getReceivable()).add(new BigDecimal(contractOrderBillDO.getLateFee())).subtract(new BigDecimal(contractOrderBillDO.getReceivablePayment()));
            incomeDetailVO.setAmountToBeCollected(Tools.DecimalFormat(amountToBeCollected));
            incomeDetailVO.setPayDate(contractOrderBillDO.getPayDate());
            incomeDetailVO.setLateFee(contractOrderBillDO.getLateFee());
            incomeDetailVO.setGenerateLateFee(contractOrderBillDO.getGenerateLateFee());
            incomeDetailVO.setFeeType(contractOrderBillDO.getFeeType());
            OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(contractOrderBillDO.getFeeType());
            if (orgBillCostTypeDO != null) {
                incomeDetailVO.setFeeTypeName(orgBillCostTypeDO.getName());
            }

            ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
            if (contractDO != null) {
                incomeDetailVO.setContractNumber(contractDO.getContractNumber());
                incomeDetailVO.setContractId(contractDO.getId());
            }
            incomeDetailVO.setStartingLateFeeDay(contractOrderBillDO.getStartingLateFeeDay());
            incomeDetailVO.setLateFeeRatio(contractOrderBillDO.getLateFeeRatio());
            incomeDetailVO.setUpperLimitLateFee(contractOrderBillDO.getUpperLimitLateFee());

            Long ownerId = contractDO.getOwnerId();
            OwnerDO ownerDO = ownerMapper.selectById(ownerId);
            if (ownerDO != null) {
                String name = ownerDO.getName();
                incomeDetailVO.setPayName(name);
                incomeDetailVO.setOwnerId(ownerId);
                incomeDetailVO.setIsPersonal(ownerDO.getIsPersonal());
            }
            String userName = systemUserMapper.getByOperatorIdUserName(contractDO.getOperatorId());
            incomeDetailVO.setOperatorName(userName);
            incomeDetailVO.setOrderNumber(contractOrderBillDO.getOrderNumber());
            incomeDetailVO.setRemark(contractOrderBillDO.getRemark());
            incomeDetailVO.setPayStartDate(contractOrderBillDO.getPayStartDate());
            incomeDetailVO.setPayEndDate(contractOrderBillDO.getPayEndDate());
            incomeDetailVO.setPayTime(contractOrderBillDO.getPayTime());

            OrgBillStreamMiddlePageReqVO orgBillStreamMiddlePageReqVO = new OrgBillStreamMiddlePageReqVO();
            orgBillStreamMiddlePageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
            orgBillStreamMiddlePageReqVO.setBillId(billId);
            orgBillStreamMiddlePageReqVO.setMatchStatus("1");
            List<OrgBillStreamMiddleDO> list = orgBillStreamMiddleService.getBillStreamListPage(orgBillStreamMiddlePageReqVO).getList();
            if (CollectionUtils.isNotEmpty(list)) {
                Date matchDate = list.get(0).getMatchDate();
                if (matchDate != null) {
                    incomeDetailVO.setMatchDate(matchDate);
                }
            }
        }
        return incomeDetailVO;
    }

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    @Override
    public PageResult<AccountsReceivableReportVO> getAccountsReceivableReportPage(AccountsReceivableReportVO accountsReceivableReportVO) {
        List<Long> roomNumberList = Lists.newArrayList();
        if (StringUtils.isNotEmpty(accountsReceivableReportVO.getRoomNumber())) {
            LambdaQueryWrapperX<RoomDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.like(RoomDO::getRoomName, accountsReceivableReportVO.getRoomNumber());
            List<RoomDO> roomDOS = roomMapper.selectList(queryWrapperX);
            roomNumberList = roomDOS.stream().map(RoomDO::getId).collect(Collectors.toList());
        }
        accountsReceivableReportVO.setTenantId(TenantContextHolder.getTenantId());
        accountsReceivableReportVO.setRoomNumberList(roomNumberList);
        IPage<AccountsReceivableReportVO> pageResult = orderBillMapper.getAccountsReceivableReportList(MyBatisUtils.buildPage(accountsReceivableReportVO), accountsReceivableReportVO);
        List<AccountsReceivableReportVO> records = pageResult.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(accountsReceivableReportVO1 -> {
                if (StringUtils.isNotEmpty(accountsReceivableReportVO1.getRoomNumber())) {
                    JSONObject jsonObject = getRoomName(accountsReceivableReportVO1.getRoomNumber());
                    String roomName = jsonObject.getString("roomName");
                    String roomInfo = jsonObject.getString("roomInfo");
                    if (StringUtils.isNotEmpty(roomName)) {
                        accountsReceivableReportVO1.setRoomNumberName(roomName);
                    }
                    if (StringUtils.isNotEmpty(roomInfo)) {
                        accountsReceivableReportVO1.setRoomNumber(roomInfo);
                    }
                }


                if (StringUtils.isNotEmpty(accountsReceivableReportVO1.getBillStatus())) {
                    String billStatusName = DictFrameworkUtils.getDictDataLabel("BILL_STATUS", accountsReceivableReportVO1.getBillStatus());
                    accountsReceivableReportVO1.setBillStatus(billStatusName);
                }

                if (StringUtils.isNotEmpty(accountsReceivableReportVO1.getRemitType())) {
                    String remitTypeName = DictFrameworkUtils.getDictDataLabel("REMIT_TYPE", accountsReceivableReportVO1.getRemitType());
                    accountsReceivableReportVO1.setRemitType(remitTypeName);
                } else {
                    accountsReceivableReportVO1.setRemitType("--");
                }
            });
        }
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }

    /**
     * @param billType
     * @param isBond
     * @param contractId
     * @return
     */
    @Override
    public List<BillTypeContractIdBillVO> getByBillTypeContractIdBillInfoList(String billType, String isBond, Long contractId, String startPayDate, String endPayDate) {
        List<BillTypeContractIdBillVO> contractIdBillInfoList = orderBillMapper.getByBillTypeContractIdBillInfoList(billType, isBond, contractId, startPayDate, endPayDate);
        if (CollectionUtils.isNotEmpty(contractIdBillInfoList)) {
            contractIdBillInfoList.forEach(billTypeContractIdBillVO -> {

                billTypeContractIdBillVO.setIsLateFee("否");
                if (billTypeContractIdBillVO.getLateFee().compareTo(BigDecimal.ZERO) > 0) {
                    billTypeContractIdBillVO.setIsLateFee("是");
                }

                LambdaQueryWrapperX<OrgBillAdjustDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(OrgBillAdjustDO::getBillId, billTypeContractIdBillVO.getBillId());
                List<String> adjustStatusList = Lists.newArrayList();
                adjustStatusList.add("2");
                adjustStatusList.add("3");
                queryWrapperX.in(OrgBillAdjustDO::getAdjustStatus, adjustStatusList);
                List<OrgBillAdjustDO> billAdjustDOList = orgBillAdjustMapper.selectList(queryWrapperX);
                if (CollectionUtils.isNotEmpty(billAdjustDOList)) {
                    //调整金额（加）
                    billTypeContractIdBillVO.setJiaAdjustPrice(new BigDecimal("0.00"));
                    List<OrgBillAdjustDO> jiaList = billAdjustDOList.stream().filter(OrgBillAdjustDO -> OrgBillAdjustDO.getAdjustType().equals("1")).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(jiaList)) {
                        billTypeContractIdBillVO.setJiaAdjustPrice(jiaList.stream().
                                map(aa -> aa.getAdjustPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                    //调整金额（减）
                    billTypeContractIdBillVO.setJianAdjustPrice(new BigDecimal("0.00"));
                    List<OrgBillAdjustDO> jianList = billAdjustDOList.stream().filter(OrgBillAdjustDO -> OrgBillAdjustDO.getAdjustType().equals("2")).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(jianList)) {
                        billTypeContractIdBillVO.setJianAdjustPrice(jianList.stream().
                                map(aa -> aa.getAdjustPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                }
            });
        }
        return contractIdBillInfoList;
    }

    @Override
    public Map<String, Object> getByBillTypeContractIdBillMoneyTotal(Long contractId) {
        Map<String, Object> map = new HashMap<>();
        //收款账单总金额
        BigDecimal receivableTotalMoney1 = orderBillMapper.getReceivableTotalMoney(contractId, "1");
        map.put("receivableTotalMoney1", Tools.DecimalFormat(receivableTotalMoney1));

        //付款账单总金额
        BigDecimal receivablePaymentTotalMoney1 = orderBillMapper.getReceivablePaymentTotalMoney(contractId, "1");
        map.put("receivablePaymentTotalMoney1", Tools.DecimalFormat(receivablePaymentTotalMoney1));

        //收款账单总实收
        BigDecimal receivableTotalMoney2 = orderBillMapper.getReceivableTotalMoney(contractId, "2");
        map.put("receivableTotalMoney2", Tools.DecimalFormat(receivableTotalMoney2));

        //付款账单总实收
        BigDecimal receivablePaymentTotalMoney2 = orderBillMapper.getReceivablePaymentTotalMoney(contractId, "2");
        map.put("receivablePaymentTotalMoney2", Tools.DecimalFormat(receivablePaymentTotalMoney2));

        return map;
    }

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    @Override
    public Map<String, Object> getAccountsReceivableSummary(AccountsReceivableReportVO accountsReceivableReportVO) {
        Map<String, Object> map = new HashMap<>();
        accountsReceivableReportVO.setTenantId(TenantContextHolder.getTenantId());
        accountsReceivableReportVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        IPage<AccountsReceivableReportVO> pageResult = orderBillMapper.getAccountsReceivableReportList(MyBatisUtils.buildPage(accountsReceivableReportVO), accountsReceivableReportVO);
        List<AccountsReceivableReportVO> records = pageResult.getRecords();
        BigDecimal receivable = new BigDecimal("0.00");
        BigDecimal zlReceivable = new BigDecimal("0.00");
        BigDecimal wyReceivable = new BigDecimal("0.00");
        BigDecimal receivablePayment = new BigDecimal("0.00");
        if (CollectionUtils.isNotEmpty(records)) {
            receivable = records.stream().
                    map(aa -> aa.getReceivable()).reduce(BigDecimal.ZERO, BigDecimal::add);

            zlReceivable = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getClauseType()) && aa.getClauseType().equals("1")).collect(Collectors.toList()).stream().
                    map(aa -> aa.getReceivable()).reduce(BigDecimal.ZERO, BigDecimal::add);

            wyReceivable = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getClauseType()) && aa.getClauseType().equals("2")).collect(Collectors.toList()).stream().
                    map(aa -> aa.getReceivable()).reduce(BigDecimal.ZERO, BigDecimal::add);

            receivablePayment = records.stream().map(aa -> aa.getReceivablePayment()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //总应收入
        map.put("receivable", Tools.DecimalFormat(receivable));
        //租赁费收入
        map.put("zlReceivable", Tools.DecimalFormat(zlReceivable));
        //物业费收入
        map.put("wyReceivable", Tools.DecimalFormat(wyReceivable));
        //未收金额
        map.put("wxReceivablePayment", Tools.DecimalFormat(receivable.subtract(receivablePayment)));
        //已收金额
        map.put("receivablePayment", Tools.DecimalFormat(receivablePayment));
        return map;
    }

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    @Override
    public Map<String, Object> getProportionReceivedAmountMap(AccountsReceivableReportVO accountsReceivableReportVO) {
        Map<String, Object> map = new HashMap<>();
        accountsReceivableReportVO.setTenantId(TenantContextHolder.getTenantId());
        accountsReceivableReportVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        IPage<AccountsReceivableReportVO> pageResult = orderBillMapper.getAccountsReceivableReportList(MyBatisUtils.buildPage(accountsReceivableReportVO), accountsReceivableReportVO);
        List<AccountsReceivableReportVO> records = pageResult.getRecords();
        //线上支付
        Long xs = 0L;
        //线下支付
        Long xx = 0L;
        if (CollectionUtils.isNotEmpty(records)) {
            xx = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getStreamSource()) && aa.getStreamSource().equals("1")).count();
            xs = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getStreamSource()) && aa.getStreamSource().equals("2")).count();
        }
        Long totalNum = xx + xs;
        Double xsR = 0.00;
        if (xs > 0) {
            xsR = Double.valueOf(xs) / Double.valueOf(totalNum) * 100;
        }

        List<JSONObject> jsonObjectList = Lists.newArrayList();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "线上");
        jsonObject1.put("value", xsR);
        jsonObjectList.add(jsonObject1);

        Double xxR = 0.00;
        if (xx > 0) {
            xxR = Double.valueOf(xx) / Double.valueOf(totalNum) * 100;
        }

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "线下");
        jsonObject2.put("value", xxR);
        jsonObjectList.add(jsonObject2);

        map.put("dataList", jsonObjectList);
        map.put("totalNum", totalNum);
        return map;
    }

    /**
     * @param accountsReceivableReportVO
     * @return
     */
    @Override
    public Map<String, Object> getTrendCostTypeMap(AccountsReceivableReportVO accountsReceivableReportVO) {
        Map<String, Object> map = new HashMap<>();
        accountsReceivableReportVO.setTenantId(TenantContextHolder.getTenantId());
        accountsReceivableReportVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        IPage<AccountsReceivableReportVO> pageResult = orderBillMapper.getAccountsReceivableReportList(MyBatisUtils.buildPage(accountsReceivableReportVO), accountsReceivableReportVO);
        List<AccountsReceivableReportVO> records = pageResult.getRecords();
        List<String> costNameList = Lists.newArrayList();
        List<BigDecimal> receivablePaymentList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(records)) {
            Map<String, List<AccountsReceivableReportVO>> collect = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getCostType()))
                    .collect(Collectors.groupingBy(AccountsReceivableReportVO::getCostType));
            for (String key : collect.keySet()) {
                OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(key);
                if (orgBillCostTypeDO != null) {
                    String name = orgBillCostTypeDO.getName();
                    costNameList.add(name);
                }
                List<AccountsReceivableReportVO> accountsReceivableReportVOS = collect.get(key);
                BigDecimal receivablePayment = accountsReceivableReportVOS.stream().
                        map(aa -> aa.getReceivablePayment()).reduce(BigDecimal.ZERO, BigDecimal::add);
                receivablePaymentList.add(receivablePayment);
            }
        }
        map.put("xAxisData", costNameList);
        map.put("seriesData", receivablePaymentList);
        return map;
    }

    /**
     * @param dCount              第几次生成账单
     * @param termType            条款类型0=租赁条款1=物业条款
     * @param feeType             费用类型
     * @param leaseStartDate      租赁开始时间
     * @param leaseEndDate        租赁结束时间
     * @param day                 付款周期（几月一付）
     * @param bondMoney           保证金金额
     * @param payDay              付款时间(提前几天)
     * @param unitPricePoint      单价小数点保留几位
     * @param calculationOrder    计算顺序0=单价×面积×时间1=单价×时间×面积
     * @param taxInclusiveRules   含税规则0=单价不含税1=单价含税
     * @param taxInclusiveValue   税率
     * @param totalArea           面积
     * @param contractUnitPrice   合同单价单位
     * @param dMoney              单价
     * @param isReceivableInteger 是否四舍五入
     * @return
     * @throws ParseException
     */
    @Override
    public List<ContractOrderBillSaveReqVO> getOrderBillList(
            Integer dCount,
            String termType,
            String feeType,
            String leaseStartDate,
            String leaseEndDate,
            int day,
            String bondMoney,
            int payDay,
            int unitPricePoint,
            String calculationOrder,
            String taxInclusiveRules,
            BigDecimal taxInclusiveValue,
            BigDecimal totalArea,
            String contractUnitPrice,
            BigDecimal dMoney,
            String isReceivableInteger
    ) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        ContractOrderBillSaveReqVO contractOrderBillSaveReqVO = new ContractOrderBillSaveReqVO();
        contractOrderBillSaveReqVO.setCount(dCount);
        contractOrderBillSaveReqVO.setFeeType(feeType);
        Date startDate = sim.parse(leaseStartDate);
        Date endDate = sim.parse(leaseEndDate);

        contractOrderBillSaveReqVO.setNumber(0);
        contractOrderBillSaveReqVO.setPayStartDate(startDate);
        contractOrderBillSaveReqVO.setPayEndDate(endDate);
        contractOrderBillSaveReqVO.setPayDate(startDate);
        contractOrderBillSaveReqVO.setBillStatus("0");
        contractOrderBillSaveReqVO.setFinalUnitPrice("0.00");
        contractOrderBillSaveReqVO.setReceivable(bondMoney);
        contractOrderBillSaveReqVO.setTaxAmount("0.00");
        contractOrderBillSaveReqVO.setTaxRate(String.valueOf(taxInclusiveValue));

        List<ContractOrderBillSaveReqVO> orderBillList = Lists.newArrayList();
        orderBillList.add(contractOrderBillSaveReqVO);
        Long billCount = DateUtils.differenceMonthCount(startDate, endDate) + 1;
        Double count = Double.valueOf(billCount / day);
        for (int i = 0; i < count; i++) {
            //JSONObject jsonObject = new JSONObject();
            ContractOrderBillSaveReqVO billSaveReqVO = new ContractOrderBillSaveReqVO();
            billSaveReqVO.setBillStatus("0");
            String monthDate = DateUtils.getMonthDate(startDate, day);
            int num = (i + 1);
            if (num == 1) {
                String start = sim.format(startDate);
                System.out.println("monthDate>>" + monthDate);
                String end = DateUtils.getDateJDay(sim.parse(monthDate), 1);
                int number = (i + 1);
                System.out.println("第" + number + "期,缴费周期:" + start + "~" + end);
                //期数
                billSaveReqVO.setNumber(number);
                //还款周期开始时间
                Date payStartDate = sim.parse(start);
                billSaveReqVO.setPayStartDate(payStartDate);
                //还款周期结束时间
                Date payEndDate = sim.parse(end);
                billSaveReqVO.setPayEndDate(payEndDate);
                billSaveReqVO.setPayDate(payStartDate);
                LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.apply(" is_bond='0'");
                queryWrapperX.like(OrgBillCostTypeDO::getName, "租金");
                List<OrgBillCostTypeDO> orgBillCostTypeDOS = orgBillCostTypeMapper.selectList(queryWrapperX);
                getFee(termType, String.valueOf(orgBillCostTypeDOS.get(0).getId()), payStartDate, payEndDate, unitPricePoint, calculationOrder, taxInclusiveRules, taxInclusiveValue, totalArea, contractUnitPrice, dMoney, day, isReceivableInteger, billSaveReqVO);

            } else {
                //期数
                int number = (i + 1);
                billSaveReqVO.setNumber(number);
                //还款周期开始时间
                startDate = sim.parse(monthDate);
                String start = sim.format(startDate);

                Date payStartDate = sim.parse(start);
                billSaveReqVO.setPayStartDate(payStartDate);
                //还款周期结束时间
                String end = DateUtils.getDateJDay(sim.parse(DateUtils.getMonthDate(startDate, day)), 1);
                if (sim.parse(end).getTime() >= endDate.getTime()) {
                    end = sim.format(endDate);
                }
                Date payEndDate = sim.parse(end);
                billSaveReqVO.setPayEndDate(payEndDate);

                String payTime = DateUtils.getDateJDay(sim.parse(start), payDay);
                billSaveReqVO.setPayDate(sim.parse(payTime));
                LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.apply(" is_bond='0'");
                queryWrapperX.eq(OrgBillCostTypeDO::getName, "物业费");
                List<OrgBillCostTypeDO> orgBillCostTypeDOS = orgBillCostTypeMapper.selectList(queryWrapperX);
                getFee(termType, String.valueOf(orgBillCostTypeDOS.get(0).getId()), payStartDate, payEndDate, unitPricePoint, calculationOrder, taxInclusiveRules, taxInclusiveValue, totalArea, contractUnitPrice, dMoney, day, isReceivableInteger, billSaveReqVO);
                System.out.println("第" + number + "期,缴费周期:" + start + "~" + end);

//                //自然日
//                if (isZrDay == 1) {
//                    //工作日
//                    payTime = DateUtils.getDateJDay(sim.parse(getDateString(start)), payDay);
//                    jsonObject.put("payDate", payTime);
//                }
            }

            orderBillList.add(billSaveReqVO);
        }


        System.out.println(orderBillList.toString());

        return orderBillList;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sim.parse("2024-08-22");
        Date endDate = sim.parse("2025-08-21");
        Integer day = 3;
        Integer payDay = 15;
        Long count = DateUtils.differenceMonthCount(startDate, endDate) + 1;
        Double billCount = Double.valueOf(count / day);
        for (int i = 0; i < billCount; i++) {
            //JSONObject jsonObject = new JSONObject();
            ContractOrderBillSaveReqVO billSaveReqVO = new ContractOrderBillSaveReqVO();
            billSaveReqVO.setBillStatus("0");
            String monthDate = DateUtils.getMonthDate(startDate, day);
            int num = (i + 1);
            if (num == 1) {
                String start = sim.format(startDate);
                System.out.println("monthDate>>" + monthDate);
                String end = DateUtils.getDateJDay(sim.parse(monthDate), 1);
                int number = (i + 1);
                System.out.println("第" + number + "期,缴费周期:" + start + "~" + end);
                //期数
                billSaveReqVO.setNumber(number);
                //还款周期开始时间
                Date payStartDate = sim.parse(start);
                billSaveReqVO.setPayStartDate(payStartDate);
                //还款周期结束时间
                Date payEndDate = sim.parse(end);
                //billSaveReqVO.setPayEndDate(payEndDate);
                //billSaveReqVO.setPayDate(payStartDate);
                //LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
                //queryWrapperX.apply(" is_bond='0'");
                //queryWrapperX.like(OrgBillCostTypeDO::getName, "租金");
                // List<OrgBillCostTypeDO> orgBillCostTypeDOS = orgBillCostTypeMapper.selectList(queryWrapperX);
                //getFee(termType, String.valueOf(orgBillCostTypeDOS.get(0).getId()), payStartDate, payEndDate, unitPricePoint, calculationOrder, taxInclusiveRules, taxInclusiveValue, totalArea, contractUnitPrice, dMoney, day, isReceivableInteger, billSaveReqVO);

            } else {
                //期数
                int number = (i + 1);
                billSaveReqVO.setNumber(number);
                //还款周期开始时间
                startDate = sim.parse(monthDate);
                String start = sim.format(startDate);

                Date payStartDate = sim.parse(start);
                billSaveReqVO.setPayStartDate(payStartDate);
                //还款周期结束时间
                String end = DateUtils.getDateJDay(sim.parse(DateUtils.getMonthDate(startDate, day)), 1);
                if (sim.parse(end).getTime() >= endDate.getTime()) {
                    end = sim.format(endDate);
                }
                Date payEndDate = sim.parse(end);
                billSaveReqVO.setPayEndDate(payEndDate);
                String payTime = DateUtils.getDateJDay(sim.parse(start), payDay);
                billSaveReqVO.setPayDate(sim.parse(payTime));
                //LambdaQueryWrapperX<OrgBillCostTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
                //queryWrapperX.apply(" is_bond='0'");
                //queryWrapperX.eq(OrgBillCostTypeDO::getName, "物业费");
                //List<OrgBillCostTypeDO> orgBillCostTypeDOS = orgBillCostTypeMapper.selectList(queryWrapperX);
                //getFee("", String.valueOf(orgBillCostTypeDOS.get(0).getId()), payStartDate, payEndDate, unitPricePoint, calculationOrder, taxInclusiveRules, taxInclusiveValue, totalArea, contractUnitPrice, dMoney, day, isReceivableInteger, billSaveReqVO);
                System.out.println("第" + number + "期,缴费周期:" + start + "~" + end);
            }
        }
    }

    /**
     * 租赁
     *
     * @param termType
     * @param startDate
     * @param endDate
     * @param unitPricePoint
     * @param calculationOrder
     * @param taxInclusiveRules
     * @param taxInclusiveValue
     * @param totalArea
     * @param contractUnitPrice
     * @param dMoney
     * @param day
     * @param isReceivableInteger
     * @param contractOrderBillSaveReqVO
     * @throws ParseException
     */
    public static void getFee(
            String termType,
            String feeType,
            Date startDate,
            Date endDate,
            int unitPricePoint,
            String calculationOrder,
            String taxInclusiveRules,
            BigDecimal taxInclusiveValue,
            BigDecimal totalArea,
            String contractUnitPrice,
            BigDecimal dMoney,
            int day,
            String isReceivableInteger,
            ContractOrderBillSaveReqVO contractOrderBillSaveReqVO
    ) throws ParseException {


        //租赁费用
        contractOrderBillSaveReqVO.setFeeType(feeType);
        //时间(天)
        Long dayCount = DateUtils.getDayCount(startDate, endDate) + 1;
        System.out.println("====" + startDate + "~" + endDate + "计算相差" + dayCount + "天====");
        taxInclusiveValue = taxInclusiveValue.divide(new BigDecimal("100"));
        //计算顺序
        if ("0".equals(calculationOrder)) {
            //单价×面积×时间
            if ("0".equals(taxInclusiveRules)) {
                BigDecimal zDMoney = dMoney.add(dMoney.multiply(taxInclusiveValue));
                if ("1".equals(isReceivableInteger)) {
                    //四舍五入保留两位小数
                    double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(zDMoney)), unitPricePoint);
                    zDMoney = new BigDecimal(roundedTwoDecimalPlaces);
                } else {
                    zDMoney = new BigDecimal(Tools.DecimalFormat(zDMoney, unitPricePoint));
                }
                contractOrderBillSaveReqVO.setFinalUnitPrice(String.valueOf(zDMoney));
                System.out.println("最终单价（含税）：" + zDMoney);
                //单价不含税
                //每天每平方计算
                if ("0".equals(contractUnitPrice)) {
                    BigDecimal bigDecimal1 = zDMoney.multiply(totalArea);
                    bigDecimal1 = bigDecimal1.multiply(new BigDecimal(dayCount));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）1：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));

                    BigDecimal bigDecimal2 = dMoney.multiply(totalArea).multiply(taxInclusiveValue);
                    bigDecimal2 = bigDecimal2.multiply(new BigDecimal(dayCount));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal2)), 2);
                        bigDecimal2 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal2 = new BigDecimal(Tools.DecimalFormat(bigDecimal2));
                    }
                    System.out.println("税额：" + bigDecimal2);
                    contractOrderBillSaveReqVO.setTaxAmount(String.valueOf(bigDecimal2));

                }
                //元/m²·月
                if ("1".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);
                    BigDecimal bigDecimal1 = zDMoney.multiply(totalArea);
                    bigDecimal1 = bigDecimal1.multiply(new BigDecimal(day));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）2：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));

                    BigDecimal bigDecimal2 = dMoney.multiply(totalArea).multiply(taxInclusiveValue);
                    bigDecimal2 = bigDecimal2.multiply(new BigDecimal(day));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal2)), 2);
                        bigDecimal2 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal2 = new BigDecimal(Tools.DecimalFormat(bigDecimal2));
                    }
                    System.out.println("税额：" + bigDecimal2);
                    contractOrderBillSaveReqVO.setTaxAmount(String.valueOf(bigDecimal2));
                }

                //元/月
                if ("2".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);

                    BigDecimal bigDecimal1 = zDMoney.multiply(new BigDecimal(day));

                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）3：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));

                    BigDecimal bigDecimal2 = dMoney.multiply(taxInclusiveValue);
                    bigDecimal2 = bigDecimal2.multiply(new BigDecimal(day));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal2)), 2);
                        bigDecimal2 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal2 = new BigDecimal(Tools.DecimalFormat(bigDecimal2));
                    }
                    System.out.println("税额：" + bigDecimal2);
                    contractOrderBillSaveReqVO.setTaxAmount(String.valueOf(bigDecimal2));
                }

            } else {
                //单价含税(不需要乘以税)
                BigDecimal zDMoney = dMoney;

                if ("1".equals(isReceivableInteger)) {
                    //四舍五入
                    double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(zDMoney)), unitPricePoint);
                    zDMoney = new BigDecimal(roundedTwoDecimalPlaces);
                } else {
                    zDMoney = new BigDecimal(Tools.DecimalFormat(zDMoney, unitPricePoint));
                }
                System.out.println("最终单价（含税）：" + zDMoney);
                contractOrderBillSaveReqVO.setFinalUnitPrice(String.valueOf(zDMoney));
                //每天每平方计算
                if ("0".equals(contractUnitPrice)) {
                    System.out.println("天数:" + dayCount);
                    BigDecimal bigDecimal1 = zDMoney.multiply(totalArea);
                    bigDecimal1 = bigDecimal1.multiply(new BigDecimal(dayCount));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）4：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    System.out.println("税额：0.00");
                    contractOrderBillSaveReqVO.setTaxAmount("0.00");
                    contractOrderBillSaveReqVO.setTaxRate("0.00");
                }
                //元/m²·月
                if ("1".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);
                    BigDecimal bigDecimal1 = zDMoney.multiply(totalArea);
                    bigDecimal1 = bigDecimal1.multiply(new BigDecimal(day));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）5：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    System.out.println("税额：0.00");
                    contractOrderBillSaveReqVO.setTaxAmount("0.00");
                    contractOrderBillSaveReqVO.setTaxRate("0.00");
                }
                //元/月
                if ("2".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);

                    BigDecimal bigDecimal1 = zDMoney.multiply(new BigDecimal(day));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）6：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    contractOrderBillSaveReqVO.setTaxAmount("0.00");
                    contractOrderBillSaveReqVO.setTaxRate("0.00");
                    System.out.println("税额：0.00");
                }
            }
        } else {
            //单价×时间×面积
            System.out.println("开始计算====单价×时间×面积====");
            if ("0".equals(taxInclusiveRules)) {
                //单价不含税
                System.out.println("====单价不含税====");
                BigDecimal zDMoney = dMoney.add(dMoney.multiply(taxInclusiveValue));
                System.out.println("租赁单价+(租赁单价*税)=" + zDMoney);
                if ("1".equals(isReceivableInteger)) {
                    //四舍五入
                    zDMoney = new BigDecimal(Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(zDMoney)), unitPricePoint));
                    System.out.println("====四舍五入====" + zDMoney + "元");
                } else {
                    System.out.println("====不四舍五入====");
                    zDMoney = new BigDecimal(Tools.DecimalFormat(zDMoney, unitPricePoint));
                    System.out.println("====不四舍五入====" + zDMoney + "元");
                }
                System.out.println("最终单价（含税）：" + zDMoney);
                contractOrderBillSaveReqVO.setFinalUnitPrice(String.valueOf(zDMoney));

                //单价不含税
                //每天每平方计算
                if ("0".equals(contractUnitPrice)) {
                    System.out.println("====每天每平方计算====");
                    BigDecimal bigDecimal1 = zDMoney.multiply(new BigDecimal(dayCount));
                    System.out.println("房屋单价(含税)" + zDMoney + "*天数" + dayCount + "=" + bigDecimal1);
                    bigDecimal1 = bigDecimal1.multiply(totalArea);
                    System.out.println("房屋单价(含税)" + zDMoney + "*天数" + dayCount + "*面积" + totalArea + "=" + bigDecimal1);
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）7：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    BigDecimal bigDecimal2 = dMoney.multiply(new BigDecimal(dayCount)).multiply(totalArea).multiply(taxInclusiveValue);
                    System.out.println("税额：单价" + dMoney + "*天数" + dayCount + "*面积" + totalArea + "*税率" + taxInclusiveValue + "=" + bigDecimal2);
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal2)), 2);
                        bigDecimal2 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal2 = new BigDecimal(Tools.DecimalFormat(bigDecimal2));
                    }

                    System.out.println("税额：" + bigDecimal2);
                    contractOrderBillSaveReqVO.setTaxAmount(String.valueOf(bigDecimal2));
                }
                //元/m²·月
                if ("1".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);
                    BigDecimal bigDecimal1 = zDMoney.multiply(new BigDecimal(day));
                    bigDecimal1 = bigDecimal1.multiply(totalArea);
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）8：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    BigDecimal bigDecimal2 = dMoney.multiply(new BigDecimal(day)).multiply(totalArea).multiply(taxInclusiveValue);
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal2)), 2);
                        bigDecimal2 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal2 = new BigDecimal(Tools.DecimalFormat(bigDecimal2));
                    }
                    contractOrderBillSaveReqVO.setTaxAmount(String.valueOf(bigDecimal2));
                    System.out.println("税额：" + bigDecimal2);
                }

                //元/月
                if ("2".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);

                    BigDecimal bigDecimal1 = zDMoney.multiply(new BigDecimal(day));

                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）9：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));

                    BigDecimal bigDecimal2 = dMoney.multiply(new BigDecimal(day)).multiply(taxInclusiveValue);
                    System.out.println("税额：" + bigDecimal2);
                    contractOrderBillSaveReqVO.setTaxAmount(String.valueOf(bigDecimal2));

                }
            } else {
                //单价含税(不需要乘以税)
                //单价不含税

                if ("1".equals(isReceivableInteger)) {
                    //四舍五入
                    double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(dMoney)), unitPricePoint);
                    dMoney = new BigDecimal(roundedTwoDecimalPlaces);
                } else {
                    dMoney = new BigDecimal(Tools.DecimalFormat(dMoney, unitPricePoint));
                }
                contractOrderBillSaveReqVO.setFinalUnitPrice(String.valueOf(dMoney));
                System.out.println("最终单价（含税）：" + dMoney);
                //单价不含税
                //每天每平方计算
                if ("0".equals(contractUnitPrice)) {
                    BigDecimal bigDecimal1 = dMoney.multiply(new BigDecimal(dayCount));
                    bigDecimal1 = bigDecimal1.multiply(totalArea);

                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）10：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    System.out.println("税额：0.00");
                    contractOrderBillSaveReqVO.setTaxAmount("0.00");
                    contractOrderBillSaveReqVO.setTaxRate("0.00");
                }
                //元/m²·月
                if ("1".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);
                    BigDecimal bigDecimal1 = dMoney.multiply(new BigDecimal(day));
                    bigDecimal1 = bigDecimal1.multiply(totalArea);
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）11：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    System.out.println("税额：0.00");
                    contractOrderBillSaveReqVO.setTaxAmount("0.00");
                    contractOrderBillSaveReqVO.setTaxRate("0.00");
                }

                //元/月
                if ("2".equals(contractUnitPrice)) {
                    System.out.println("月数:" + day);

                    BigDecimal bigDecimal1 = dMoney.multiply(new BigDecimal(day));
                    if ("1".equals(isReceivableInteger)) {
                        //四舍五入
                        double roundedTwoDecimalPlaces = Tools.roundToDecimalPlaces(Double.valueOf(String.valueOf(bigDecimal1)), 2);
                        bigDecimal1 = new BigDecimal(roundedTwoDecimalPlaces);
                    } else {
                        bigDecimal1 = new BigDecimal(Tools.DecimalFormat(bigDecimal1));
                    }
                    System.out.println("应收（含税）12：" + bigDecimal1);
                    contractOrderBillSaveReqVO.setReceivable(String.valueOf(bigDecimal1));
                    System.out.println("税额：0.00");
                    contractOrderBillSaveReqVO.setTaxAmount("0.00");
                    contractOrderBillSaveReqVO.setTaxRate("0.00");
                }
            }
        }
    }


    public static String getDateString(String date) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        int i = 0;
        while (i == 0) {
            String url = "https://timor.tech/api/holiday/info/" + date;
            String responseDate = HttpUtil.get(url);
            System.out.println(responseDate);
            JSONObject parsed = JSON.parseObject(responseDate);
            String code = String.valueOf(parsed.get("code"));
            if (code.equals("0")) {
                String type = String.valueOf(parsed.get("type"));
                JSONObject typeObject = JSON.parseObject(type);
                String t = String.valueOf(typeObject.get("type"));
                if ("0".equals(t) || "3".equals(t)) {
                    i = 1;
                    System.out.println("工作日日期:" + date);
                    break;
                } else {
                    date = DateUtils.getDateJDay(sim.parse(date), 1);
                }
            }
        }
        return date;
    }

    /**
     * @param ownerId
     * @param isDisplayReceived    已显示已收账单0=隐藏1=显示
     * @param isAccountsReceivable 已显示未到应收期账单0=隐藏1=显示
     * @return
     */
    @Override
    public List<Map<String, Object>> getByOwnerIdRoomIdCashierBillList(Long ownerId, String isDisplayReceived, String isAccountsReceivable) {
        List<String> billStatusList = Lists.newArrayList();
        if ("0".equals(isDisplayReceived)) {
            billStatusList.add("0");
            billStatusList.add("2");
        }
        if ("1".equals(isAccountsReceivable)) {
            isAccountsReceivable = "";
        }
        List<ContractOrderBillDO> ownerIdRoomIdBillList = orderBillMapper.getByOwnerIdRoomIdCashierBillList(ownerId, billStatusList, isAccountsReceivable);
        List<Map<String, Object>> mapList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ownerIdRoomIdBillList)) {
            for (ContractOrderBillDO contractOrderBillDO : ownerIdRoomIdBillList) {
                LambdaQueryWrapperX<OrgBillNoticeDO> queryWrapper = new LambdaQueryWrapperX<>();
                queryWrapper.apply("REGEXP_LIKE(contract_bill_id, '(^|,)" + contractOrderBillDO.getId() + "($|,)')");
                int size = orgBillNoticeMapper.selectList(queryWrapper).size();
                contractOrderBillDO.setIsCs(0);
                if (size >= 1) {
                    contractOrderBillDO.setIsCs(1);
                }

            }

            //根据付款日期进行分组
            Map<String, List<ContractOrderBillDO>> groupedItems = ownerIdRoomIdBillList.stream()
                    .collect(Collectors.groupingBy(ContractOrderBillDO::getPayDateStr));

            int i = 0;
            for (String payDate : groupedItems.keySet()) {
                Map<String, Object> map = new HashMap<>();
                i++;
                List<ContractOrderBillDO> contractOrderBillDOS = groupedItems.get(payDate);
                contractOrderBillDOS.forEach(contractOrderBillDO -> {
                    String billStatus = contractOrderBillDO.getBillStatus();
                    String dataLabel = DictFrameworkUtils.getDictDataLabel("BILL_STATUS", billStatus);
                    contractOrderBillDO.setBillStatusName(dataLabel);
                });

                //缴费次数
                map.put("num", i);
                //付款时间
                map.put("payDate", payDate);
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                //有效期
                map.put("termValidity", sim.format(contractOrderBillDOS.get(0).getPayStartDate()) + "~" + sim.format(contractOrderBillDOS.get(contractOrderBillDOS.size() - 1).getPayEndDate()));
                //几项
                map.put("count", contractOrderBillDOS.size());
                //应收金额
                double receivable = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivable())).sum();
                map.put("receivable", receivable);
                //滞纳金
                double lateFee = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getLateFee())).sum();
                map.put("lateFee", lateFee);
                //应收金额
                double receivablePayableAmount = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayableAmount())).sum();
                map.put("receivablePayableAmount", receivablePayableAmount);

                //实收金额
                double receivablePayment = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayment())).sum();
                map.put("receivablePayment", receivablePayment);

                //状态
                int size = contractOrderBillDOS.stream().filter(contractOrderBillDO -> contractOrderBillDO.getBillStatus().equals("0") || contractOrderBillDO.getBillStatus().equals("2")).collect(Collectors.toList()).size();
                if (receivablePayment > 0) {
                    map.put("billStatus", "部分已收");
                } else {
                    map.put("billStatus", size >= 1 ? "待收" : "已付款");
                }

                int isCs = contractOrderBillDOS.stream().filter(contractOrderBillDO -> contractOrderBillDO.getIsCs() >= 1).collect(Collectors.toList()).size();
                map.put("isCs", isCs >= 1 ? "1" : "0");

                //根据合同id进行分组
                Map<Long, List<ContractOrderBillDO>> contractIdMap = contractOrderBillDOS.stream()
                        .collect(Collectors.groupingBy(ContractOrderBillDO::getContractId));

                List<JSONObject> contractJsonList = Lists.newArrayList();
                for (Long contractId : contractIdMap.keySet()) {
                    ContractDO contractDO = contractMapper.selectById(contractId);
                    JSONObject jsonObject = new JSONObject();
                    //合同id
                    jsonObject.put("contractId", contractDO.getId());
                    //合同编号
                    jsonObject.put("contractNumber", contractDO.getContractNumber());
                    //合同有效期
                    jsonObject.put("contractTermValidity", sim.format(contractDO.getContractStartTime()) + "~" + sim.format(contractDO.getContractEndTime()));
                    //合同状态
                    jsonObject.put("contractStatus", DictFrameworkUtils.getDictDataLabel("CONTRACT_STATUS", contractDO.getStatus()));
                    String glRoom = "";
                    VillageDO villageDO = villageMapper.selectById(contractDO.getParkId());
                    if (villageDO != null) {
                        glRoom = villageDO.getName();
                        BuildDO buildDO = buildMapper.selectById(contractDO.getBuildId());
                        if (buildDO != null) {
                            glRoom = glRoom + "/" + buildDO.getBuildName();
                        }
                    }
                    jsonObject.put("glRoom", glRoom);
                    String[] roomList = contractDO.getRoomNumber().split(",");
                    List<JSONObject> roomJsonList = Lists.newArrayList();
                    for (String roomId : roomList) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("roomId", roomId);
                        RoomDO roomDO = roomMapper.selectById(roomId);
                        String roomName = roomId;
                        if (roomDO != null) {
                            roomName = roomDO.getRoomName();
                        }

                        String lc = roomName.substring(0, roomName.length() - 2);
                        jsonObject1.put("roomName", lc + "-" + roomName);
                        roomJsonList.add(jsonObject1);
                    }

                    jsonObject.put("room", new Gson().toJson(roomJsonList));
                    List<ContractOrderBillDO> contractOrderBillDOS1 = contractIdMap.get(contractId);
                    jsonObject.put("data", contractOrderBillDOS1);
                    contractJsonList.add(jsonObject);
                }
                map.put("contractInfoDataList", contractJsonList);


                mapList.add(map);
            }
        }

        return mapList;
    }

    /**
     * 扫码支付
     *
     * @param billId
     * @param payType
     * @return
     */
    @Override
    @TenantIgnore
    public String scanCodeBillPay(String billId, String payType) {
        try {
            List<String> billIds = List.of(billId.split(","));
            String merchantOrderIdNew = UuidUtils.generateUuid().replace("-", "");

            LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.in(ContractOrderBillDO::getId, billIds);
            List<ContractOrderBillDO> contractOrderBillDOS = orderBillMapper.selectList(queryWrapperX);
            contractOrderBillDOS.forEach(contractOrderBillDO -> {

                String billStatus = contractOrderBillDO.getBillStatus();
                if (StringUtils.equals(billStatus, "1")) {
                    throw new ServiceException(406, "当前支付订单中存在已支付订单,请核对后重新支付");
                }

                PayOrderBillDO payOrderBillDO = new PayOrderBillDO();
                payOrderBillDO.setMerchantOrderId(merchantOrderIdNew);
                payOrderBillDO.setBillId(Long.valueOf(contractOrderBillDO.getId()));
                BigDecimal receivable = new BigDecimal(contractOrderBillDO.getReceivable()).add(new BigDecimal(contractOrderBillDO.getLateFee()));
                receivable = receivable.subtract(new BigDecimal(contractOrderBillDO.getReceivablePayment()));
                int price = CurrencyConverter.convertYuanToFen(receivable);
                payOrderBillDO.setPrice(price);

                //校验支付订单中是否存在已支付或者已支付正在处理的订单
                LambdaQueryWrapperX<PayOrderBillDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(PayOrderBillDO::getBillId, contractOrderBillDO.getId());
                PayOrderBillDO payOrderBillOld = payOrderBillMapper.selectOne(queryWrapperX1);
                if (payOrderBillOld != null) {
                    System.out.println("PayOrderBillDO中存在数据:" + payOrderBillOld.toString());
                    //0=等待通知10=通知成功20=通知失败21=请求成功，但是结果失败22=请求失败
                    Integer status = payOrderBillMapper.getByMerchantOrderIdStatus(payOrderBillOld.getMerchantOrderId());
                    System.out.println("回调接口中存在数据:" + status);
                    if (status != null) {
                        if (status == 0) {
                            throw new ServiceException(406, "当前支付订单中存在已支付或者支付成功正在处理订单,请核对后重新支付");
                        }
                        if (status == 10 || status == 20 || status == 21 || status == 22) {
                            throw new ServiceException(406, "当前订单中存在已支付订单,请核对后重新支付");
                        }
                    }
                    payOrderBillDO.setId(payOrderBillOld.getId());
                    payOrderBillMapper.updateById(payOrderBillDO);
                    System.out.println("更新payOrderBill表数据:" + payOrderBillDO.toString());
                } else {
                    payOrderBillMapper.insert(payOrderBillDO);
                    System.out.println("插入payOrderBill表数据:" + payOrderBillDO.toString());
                }
            });
            String channelCode = "wx_native";
            Long appId=9L;
            CommonResult<Long> channelCodeResult = clientApi.getByChannelCodeId(appId,channelCode);
            PayOrderCreateReqDTO payOrderCreateReqDTO = new PayOrderCreateReqDTO();
            payOrderCreateReqDTO.setAppId(appId);
            payOrderCreateReqDTO.setUserIp(getClientIP());
            payOrderCreateReqDTO.setMerchantOrderId(merchantOrderIdNew);
            payOrderCreateReqDTO.setChannelId(channelCodeResult.getData());
            //计算金额；(应交金额+滞纳金)-实缴金额
            //应收金额
            Double receivableD = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivable())).sum();
            //违约金
            Double lateFeeD = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getLateFee())).sum();
            //实缴金额
            Double receivablePaymentD = contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getReceivablePayment())).sum();
            BigDecimal receivable = new BigDecimal(String.valueOf(receivableD)).add(new BigDecimal(String.valueOf(lateFeeD)));
            BigDecimal receivablePayment = receivable.subtract(new BigDecimal(String.valueOf(receivablePaymentD)));
            String msg = "支付费用" + receivablePayment + "元";
            payOrderCreateReqDTO.setSubject(msg);
            payOrderCreateReqDTO.setBody(msg);
            int price = CurrencyConverter.convertYuanToFen(receivablePayment);
            payOrderCreateReqDTO.setPrice(price);
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDateTime expireTime = localDateTime.plusMinutes(10);
            payOrderCreateReqDTO.setExpireTime(expireTime);
            CommonResult<Long> order = payOrderApi.createOrder(payOrderCreateReqDTO);
            if (order.getCode() != 0) {
                throw new ServiceException(406, order.getMsg());
            }
            Long payOrderId = order.getData();
            PayOrderSubmitReqVO payOrderSubmitReqVO = new PayOrderSubmitReqVO();
            payOrderSubmitReqVO.setId(payOrderId);
            payOrderSubmitReqVO.setChannelCode(channelCode);
            Map<String, String> channelExtras = new HashMap<>();
            payOrderSubmitReqVO.setChannelExtras(channelExtras);
            payOrderSubmitReqVO.setDisplayMode(PayOrderDisplayModeEnum.QR_CODE_URL.getMode());

            CommonResult<?> app = payAppApi.getApp(payOrderCreateReqDTO.getAppId());
            if (app.getData() == null) {
                throw new RuntimeException("当前渠道APPID无效");
            }
            //JSONObject appJsonObject = new JSONObject((Map<String, Object>) app.getData());
            //String orderNotifyUrl = appJsonObject.getString("orderNotifyUrl");
            //CommonResult<Long> channelCodeResult = clientApi.getByChannelCodeId(payOrderSubmitReqVO.getChannelCode());
            //payOrderSubmitReqVO.setReturnUrl(orderNotifyUrl + channelCodeResult.getData());
            CommonResult<?> commonResult = payOrderApi.submitOrder(payOrderSubmitReqVO);
            if (commonResult.getCode().equals(0)) {
                redisTemplate.opsForValue().set(payOrderCreateReqDTO.getMerchantOrderId(), billId, 60, TimeUnit.MINUTES);
                Object data = commonResult.getData();
                JSONObject jsonObject = new JSONObject((Map<String, Object>) data);
                System.out.println("支付地址:" + jsonObject.toString());
                String displayContent = jsonObject.getString("displayContent");
                return displayContent;
            }
            throw new ServiceException(406, commonResult.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(406, e.getMessage());
        }
    }

    @Override
    public void close(ContractOrderBillSaveReqVO contractOrderBillSaveReqVO) {

        LambdaQueryWrapperX<BillStreamDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(BillStreamDO::getBillId, contractOrderBillSaveReqVO.getId());
        int size = billStreamMapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "当前账单存在已确认流水,不支持关闭账单");
        }
        ContractOrderBillDO updateObj = BeanUtils.toBean(contractOrderBillSaveReqVO, ContractOrderBillDO.class);
        updateObj.setCloseStatus("1");
        orderBillMapper.updateById(updateObj);
    }

    @Override
    public BillInformationVO billInformation(Long billId) {
        BillInformationVO billInformationVO = new BillInformationVO();
        ContractOrderBillDO contractOrderBillDO = orderBillMapper.selectById(billId);
        if (contractOrderBillDO != null) {
            billInformationVO.setBillId(contractOrderBillDO.getId());
            ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
            billInformationVO.setBuildId(contractDO.getBuildId());
            billInformationVO.setRoomNumber(contractDO.getRoomNumber());
            OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
            if (ownerDO != null) {
                billInformationVO.setOwnerId(ownerDO.getId());
                billInformationVO.setOwnerName(ownerDO.getName());
                billInformationVO.setIsPersonal(ownerDO.getIsPersonal());
            }
            billInformationVO.setCloseStatus(contractOrderBillDO.getCloseStatus());

            BigDecimal receivablePayment = new BigDecimal(contractOrderBillDO.getReceivablePayment());
            billInformationVO.setBillStatus(billStatusName(contractOrderBillDO.getBillStatus(), receivablePayment));


            BigDecimal receivable = new BigDecimal(contractOrderBillDO.getReceivable());
            billInformationVO.setReceivable(receivable);
            billInformationVO.setPayDate(contractOrderBillDO.getPayDate());

            String amountToBeCollected = Tools.DecimalFormat(receivable.subtract(receivablePayment));
            billInformationVO.setAmountToBeCollected(new BigDecimal(amountToBeCollected));

            billInformationVO.setGenerateLateFee(new BigDecimal(contractOrderBillDO.getGenerateLateFee()));

            billInformationVO.setLateFee(new BigDecimal(contractOrderBillDO.getLateFee()));

            OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(contractOrderBillDO.getFeeType());
            if (orgBillCostTypeDO != null) {
                billInformationVO.setFeeType(orgBillCostTypeDO.getName());
            }

            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            String payStartDate = sim.format(contractOrderBillDO.getPayStartDate());
            String payEndDate = sim.format(contractOrderBillDO.getPayEndDate());
            billInformationVO.setBillingCycle(payStartDate + "~" + payEndDate);

            billInformationVO.setCreateTime(contractOrderBillDO.getCreateTime());

            billInformationVO.setContractId(contractDO.getId());

            billInformationVO.setContractNumber(contractDO.getContractNumber());

            billInformationVO.setOrderNumber(contractOrderBillDO.getOrderNumber());

            billInformationVO.setStartingLateFeeDay(contractOrderBillDO.getStartingLateFeeDay());

            billInformationVO.setLateFeeRatio(contractOrderBillDO.getLateFeeRatio());

            billInformationVO.setUpperLimitLateFee(contractOrderBillDO.getUpperLimitLateFee());

            String userName = systemUserMapper.getByOperatorIdUserName(contractDO.getOperatorId());
            billInformationVO.setOperatorName(userName);

            billInformationVO.setRemark(contractOrderBillDO.getRemark());

            billInformationVO.setVillageId(contractDO.getParkId());

            billInformationVO.setLateFeePayStatus(contractOrderBillDO.getLateFeePayStatus());

        }
        return billInformationVO;
    }

    @Override
    public Map<String, Object> billDetails(Long billId) {
        Map<String, Object> map = new HashMap<>();
        List<ContractOrderBillRespVO> contractOrderBillRespVOList = Lists.newArrayList();
        ContractOrderBillDO contractOrderBillDO = orderBillMapper.selectById(billId);

        ContractOrderBillRespVO contractOrderBillRespVO1 = new ContractOrderBillRespVO();
        OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(contractOrderBillDO.getFeeType());
        if (orgBillCostTypeDO != null) {
            contractOrderBillRespVO1.setFeeType(orgBillCostTypeDO.getName());
            contractOrderBillRespVO1.setRemark(StringUtils.isBlank(contractOrderBillDO.getRemark()) ? orgBillCostTypeDO.getName() : contractOrderBillDO.getRemark());
        }

        contractOrderBillRespVO1.setReceivable(contractOrderBillDO.getReceivable());
        contractOrderBillRespVO1.setPayStartDate(contractOrderBillDO.getPayStartDate());
        contractOrderBillRespVO1.setPayEndDate(contractOrderBillDO.getPayEndDate());
        contractOrderBillRespVO1.setTaxAmount(contractOrderBillDO.getTaxAmount());
        contractOrderBillRespVO1.setTaxRate(contractOrderBillDO.getTaxRate());

        contractOrderBillRespVOList.add(contractOrderBillRespVO1);

        if (new BigDecimal(contractOrderBillDO.getLateFee()).compareTo(BigDecimal.ZERO) > 0) {
            ContractOrderBillRespVO contractOrderBillRespVO2 = new ContractOrderBillRespVO();
            contractOrderBillRespVO2.setFeeType("违约金");
            contractOrderBillRespVO2.setReceivable(contractOrderBillDO.getLateFee());
            contractOrderBillRespVO2.setPayStartDate(contractOrderBillDO.getPayStartDate());
            contractOrderBillRespVO2.setPayEndDate(contractOrderBillDO.getPayEndDate());
            contractOrderBillRespVO2.setRemark(StringUtils.isBlank(contractOrderBillDO.getRemark()) ? "滞纳金" : contractOrderBillDO.getRemark());
            contractOrderBillRespVO1.setTaxAmount("0");
            contractOrderBillRespVO1.setTaxRate("0");
            contractOrderBillRespVOList.add(contractOrderBillRespVO2);
        }

        map.put("dataList", contractOrderBillRespVOList);
        //结清金额
        map.put("settlementAmount", contractOrderBillDO.getReceivablePayment());
        //调整金额
        LambdaQueryWrapperX<OrgBillAdjustDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillAdjustDO::getBillId, billId);
        List<String> adjustStatusList = List.of("2,3".split(","));
        queryWrapperX.eq(OrgBillAdjustDO::getAdjustStatus, adjustStatusList);
        List<OrgBillAdjustDO> billAdjustDOList = orgBillAdjustMapper.selectList(queryWrapperX);
        BigDecimal adjustAmountJia = new BigDecimal("0.00");
        BigDecimal adjustAmountJian = new BigDecimal("0.00");
        if (CollectionUtils.isNotEmpty(billAdjustDOList)) {
            adjustAmountJia = billAdjustDOList.stream().filter(aa -> aa.getAdjustType().equals("1")).collect(Collectors.toList()).stream().
                    map(aa -> aa.getAdjustPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);

            adjustAmountJian = billAdjustDOList.stream().filter(aa -> aa.getAdjustType().equals("2")).collect(Collectors.toList()).stream().
                    map(aa -> aa.getAdjustPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        map.put("adjustAmountJia", adjustAmountJia);
        map.put("adjustAmountJian", adjustAmountJian);
        return map;
    }

    /**
     * @param billId
     * @param lateFee
     */
    @Override
    public void collectLateFee(Long billId, BigDecimal lateFee) {

        ContractOrderBillDO contractOrderBillDO = orderBillMapper.selectById(billId);
        //BigDecimal generateLateFee = new BigDecimal(contractOrderBillDO.getGenerateLateFee());
        // lateFee = generateLateFee.subtract(lateFee);
        contractOrderBillDO.setLateFee(Tools.DecimalFormat(lateFee));
        orderBillMapper.updateById(contractOrderBillDO);
    }

    private String billStatusName(String billStatus, BigDecimal receivablePayment) {
        if (!StringUtils.equals(billStatus, "1")) {
            //未付款

            if (receivablePayment.compareTo(BigDecimal.ZERO) > 0) {
                if (StringUtils.equals(billStatus, "2")) {
                    return "逾期部分结清";
                } else {
                    return "部分结清";
                }
            } else {
                if (StringUtils.equals(billStatus, "0")) {
                    return "待付款";
                }

                if (StringUtils.equals(billStatus, "2")) {
                    return "逾期";
                }
            }
        } else {
            return "已付款";
        }
        return "";
    }
}