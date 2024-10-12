package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.framework.web.core.util.WebFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOrderBillSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillStreamSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderPaymentOrderMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeOrderMapper;
import cn.sdqingyun.smartpark.module.bus.service.bpm.ContractLeaveService;
import cn.sdqingyun.smartpark.module.bus.service.bill.BillStreamService;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillStreamMiddleService;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgIncomeService;
import cn.sdqingyun.smartpark.module.bus.service.owner.OwnerService;
import cn.sdqingyun.smartpark.module.pay.api.client.ClientApi;
import cn.sdqingyun.smartpark.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.sdqingyun.smartpark.module.pay.api.notify.dto.PayRefundNotifyReqDTO;
import cn.sdqingyun.smartpark.module.pay.api.order.PayOrderApi;
import cn.sdqingyun.smartpark.module.pay.api.order.dto.PayOrderRespDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

/**
 * @Author lvzy
 * @Date 2024/8/19 11:13
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class PayNotifyServiceImpl implements PayNotifyService {


    @Autowired
    private PayOrderApi payOrderApi;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ContractOrderBillService contractOrderBillService;

    @Autowired
    private BillStreamService billStreamService;

    @Autowired
    private OrgBillStreamMiddleService billStreamMiddleService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ClientApi clientApi;

    @Autowired
    private ContractLeaveService contractLeaveService;

    @Autowired
    private WorkOrderProposeOrderMapper workOrderProposeOrderMapper;

    @Autowired
    private WorkOrderPaymentOrderMapper workOrderPaymentOrderMapper;

    @Autowired
    private WorkOrderProposeLogMapper workOrderProposeLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> notifyOrder(PayOrderNotifyReqDTO notifyReqDTO) throws Exception {
        log.info("支付渠道的统一【支付】回调,{}", notifyReqDTO);
        // 2.1 校验支付单是否存在
        PayOrderRespDTO payOrder = payOrderApi.getMerchantOrderId(notifyReqDTO.getMerchantOrderId()).getCheckedData();
        if (payOrder == null) {
            return success(false);
        }
        String merchantOrderId = payOrder.getMerchantOrderId();
        //修改支付订单
        String billIds = redisTemplate.opsForValue().get(merchantOrderId);
        if (StringUtils.isNotEmpty(billIds)) {

            List<String> billIdList = List.of(billIds.split(","));

            for (String billId : billIdList) {
                CommonResult<String> channelCode = clientApi.getByIdChannelCode(payOrder.getChannelId());
                String data = channelCode.getData();

                //修改账单状态
                ContractOrderBillDO contractOrderBillDO = contractOrderBillService.getOrderBill(Long.valueOf(billId));

                BigDecimal receivable = new BigDecimal(contractOrderBillDO.getReceivable()).add(new BigDecimal(contractOrderBillDO.getLateFee()));
                BigDecimal receivablePayment = receivable.subtract(new BigDecimal(contractOrderBillDO.getReceivablePayment()));

                contractOrderBillDO.setBillStatus("1");
                contractOrderBillDO.setPayTime(new Date());
                BigDecimal billReceivablePayment = new BigDecimal(contractOrderBillDO.getReceivable()).add(new BigDecimal(contractOrderBillDO.getLateFee()));
                contractOrderBillDO.setReceivablePayment(Tools.DecimalFormat(billReceivablePayment));
                ContractOrderBillSaveReqVO updateObj = BeanUtils.toBean(contractOrderBillDO, ContractOrderBillSaveReqVO.class);
                contractOrderBillService.updateOrderBill(updateObj);
                ContractDO contractDO = contractService.selectById(contractOrderBillDO.getContractId());
                //记录账单流水
                BillStreamSaveReqVO billStreamSaveReqVO = new BillStreamSaveReqVO();
                billStreamSaveReqVO.setVillageId(contractDO.getParkId());
                billStreamSaveReqVO.setCompanyId(contractDO.getOwnerId());
                billStreamSaveReqVO.setBuildId(contractDO.getBuildId());
                billStreamSaveReqVO.setRoomNumber(contractDO.getRoomNumber());
                billStreamSaveReqVO.setBillId(contractOrderBillDO.getId());
                billStreamSaveReqVO.setStreamNumber(merchantOrderId);
                billStreamSaveReqVO.setBillType(contractOrderBillDO.getBillType());
                billStreamSaveReqVO.setAmount(receivablePayment);
                billStreamSaveReqVO.setIncomeDate(new Date());
                OwnerDO ownerDO = ownerService.get(billStreamSaveReqVO.getCompanyId());
                if (ownerDO != null) {
                    billStreamSaveReqVO.setCompanyName(ownerDO.getName());
                    billStreamSaveReqVO.setStreamAccount(ownerDO.getBank());
                    billStreamSaveReqVO.setOtherAccount(ownerDO.getAccount());
                }
                billStreamSaveReqVO.setCostType(contractOrderBillDO.getFeeType());
                billStreamSaveReqVO.setDataSource("2");

                if (data.contains("wx")) {
                    //微信
                    billStreamSaveReqVO.setRemitType("4");
                } else {
                    //支付宝
                    billStreamSaveReqVO.setRemitType("3");
                }

                Long billStreamId = billStreamService.save(billStreamSaveReqVO);

                List<Long> streamIds = Lists.newArrayList();
                streamIds.add(billStreamId);
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                String matchDate = sim.format(billStreamSaveReqVO.getMatchDate());
                billStreamMiddleService.addCollectionMiddle(contractOrderBillDO.getId(), streamIds, billStreamSaveReqVO.getMatchPrice(), matchDate);
            }
            List<Long> contractIdList = contractOrderBillService.getOrderBillContractIdList(billIdList);
            for (Long contractId : contractIdList) {
                String status = contractService.selectById(contractId).getStatus();
                if (StringUtils.equals(status, "5")) {
                    int size = contractOrderBillService.getByContractIdOrderBillList(contractId).size();
                    if (size <= 0) {
                        ContractDO contractDO = contractService.selectById(contractId);
                        //结清
                        contractService.updateById(new ContractDO().setId(contractId).setStatus("6"));

                        contractLeaveService.updateOwnerAndRoomsForTerminatedOrCancelledContract(contractDO);
                    }
                }

            }

        }
        redisTemplate.delete(merchantOrderId);
        return success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> notifyOrderWorkOrder(PayOrderNotifyReqDTO notifyReqDTO) throws Exception {
        log.info("支付渠道的统一【工单支付】回调,{}", notifyReqDTO);
        if(StringUtils.isEmpty(notifyReqDTO.getMerchantOrderId())){
            return CommonResult.success(false);
        }
        // 2.1 校验支付单是否存在
        PayOrderRespDTO payOrder = payOrderApi.getMerchantOrderId(notifyReqDTO.getMerchantOrderId()).getCheckedData();
        if (payOrder == null) {
            return CommonResult.success(false);
        }
        String merchantOrderId = payOrder.getMerchantOrderId();
        //修改支付订单
        String ids = redisTemplate.opsForValue().get(merchantOrderId);
        if (StringUtils.isNotEmpty(ids)) {

            List<String> idList = List.of(ids.split(","));

            for (String id : idList) {
                CommonResult<String> channelCode = clientApi.getByIdChannelCode(payOrder.getChannelId());
                String data = channelCode.getData();

                //修改账单状态
                WorkOrderProposeOrderDO workOrderProposeOrderDO = workOrderProposeOrderMapper.selectById(id);

                workOrderProposeOrderDO.setOrderStatus("2");
                workOrderProposeOrderDO.setPaymentTime(new Date());
                workOrderProposeOrderDO.setPaymentAmount(workOrderProposeOrderDO.getNeedAmount());
                workOrderProposeOrderDO.setThirdOrderNo(notifyReqDTO.getMerchantOrderId());
                if (data.contains("wx")) {
                    //微信
                    workOrderProposeOrderDO.setPayMethod("4");
                } else {
                    //支付宝
                    workOrderProposeOrderDO.setPayMethod("3");
                }
                workOrderProposeOrderMapper.updateById(workOrderProposeOrderDO);


                WorkOrderProposeLogDO workOrderProposeLogDO=new WorkOrderProposeLogDO();
                workOrderProposeLogDO.setWorkorderId(workOrderProposeOrderDO.getWorkorderId());
                workOrderProposeLogDO.setVillageId(workOrderProposeOrderDO.getVillageId());
                workOrderProposeLogDO.setBuildId(workOrderProposeOrderDO.getBuildId());
                workOrderProposeLogDO.setApplication(workOrderProposeOrderDO.getApplication());
                workOrderProposeLogDO.setFrom(workOrderProposeOrderDO.getFrom());
                workOrderProposeLogDO.setSource("1");
                workOrderProposeLogDO.setOperateType("11");
                workOrderProposeLogDO.setStatus("1");
                workOrderProposeLogDO.setOperateTime(new Date());
                String orderTypeName = DictFrameworkUtils.getDictDataLabel("WORL_ORDER_TYPE", workOrderProposeOrderDO.getOrderType());
                String otherContent=orderTypeName+workOrderProposeOrderDO.getNeedAmount()+"元";
                workOrderProposeLogDO.setOtherContent(otherContent);
                Long userId = WebFrameworkUtils.getLoginUserId();
                workOrderProposeLogDO.setUid(userId);
                workOrderProposeLogDO.setRemark("");
                workOrderProposeLogMapper.insert(workOrderProposeLogDO);


            }
        }
        redisTemplate.delete(merchantOrderId);
        return success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> notifyRefundOrderWorkOrder(PayRefundNotifyReqDTO notifyReqDTO) {
        log.info("支付渠道的统一【工单退款支付】回调,{}", notifyReqDTO);
        if(StringUtils.isEmpty(notifyReqDTO.getMerchantOrderId())){
            return CommonResult.success(false);
        }
        // 2.1 校验支付单是否存在
        PayOrderRespDTO payOrder = payOrderApi.getMerchantOrderId(notifyReqDTO.getMerchantOrderId()).getCheckedData();
        if (payOrder == null) {
            return CommonResult.success(false);
        }
        String merchantOrderId = payOrder.getMerchantOrderId();
        //修改支付订单
        String refundInfo = redisTemplate.opsForValue().get(merchantOrderId);
        JSONObject jsonObject= JSON.parseObject(refundInfo);

        //修改账单状态
        WorkOrderProposeOrderDO workOrderProposeOrderDO = workOrderProposeOrderMapper.selectById(jsonObject.getLong("id"));

        workOrderProposeOrderDO.setOrderStatus("3");
        workOrderProposeOrderDO.setRefundTime(new Date());
        workOrderProposeOrderDO.setRefundAmount(new BigDecimal(jsonObject.getString("refundAmount")));
        workOrderProposeOrderDO.setRefundStatus(jsonObject.getString("refundStatus"));
        workOrderProposeOrderDO.setThirdOrderNo(notifyReqDTO.getMerchantOrderId());

        workOrderProposeOrderMapper.updateById(workOrderProposeOrderDO);

        WorkOrderProposeLogDO workOrderProposeLogDO=new WorkOrderProposeLogDO();
        workOrderProposeLogDO.setWorkorderId(workOrderProposeOrderDO.getWorkorderId());
        workOrderProposeLogDO.setVillageId(workOrderProposeOrderDO.getVillageId());
        workOrderProposeLogDO.setBuildId(workOrderProposeOrderDO.getBuildId());
        workOrderProposeLogDO.setApplication(workOrderProposeOrderDO.getApplication());
        workOrderProposeLogDO.setFrom(workOrderProposeOrderDO.getFrom());
        workOrderProposeLogDO.setSource("2");
        workOrderProposeLogDO.setOperateType("15");
        workOrderProposeLogDO.setStatus("1");
        workOrderProposeLogDO.setOperateTime(new Date());
        Long userId = WebFrameworkUtils.getLoginUserId();
        workOrderProposeLogDO.setUid(userId);
        workOrderProposeLogDO.setRemark("退款费用: "+workOrderProposeOrderDO.getRefundAmount()+"元");
        workOrderProposeLogMapper.insert(workOrderProposeLogDO);
        return success(true);
    }
}
