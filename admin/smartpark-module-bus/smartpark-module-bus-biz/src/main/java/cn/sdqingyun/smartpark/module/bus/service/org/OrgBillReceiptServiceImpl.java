package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.pdf.MoneyConverter;
import cn.sdqingyun.smartpark.framework.common.util.pdf.WordToPdfUtil;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.web.core.util.WebFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.LssueVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.ReceiptImportVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillStreamMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.deepoove.poi.XWPFTemplate;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_RECEIPT_NOT_EXISTS;

/**
 * 机构账单收据 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillReceiptServiceImpl implements OrgBillReceiptService {

    @Resource
    private OrgBillReceiptMapper Mapper;

    @Resource
    private OrgBillReceiptSellerMapper orgBillReceiptSellerMapper;

    @Resource
    private ContractOrderBillMapper contractOrderBillMapper;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private OwnerMapper ownerMapper;

    @Resource
    private BillStreamMapper billStreamMapper;

    @Resource
    private OrgBillStreamMiddleMapper orgBillStreamMiddleMapper;

    @Resource
    private OrgBillReceiptRuleMapper orgBillReceiptRuleMapper;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private NotifyMessageSendApi notifySendService;

    @Resource
    private FileApi fileApi;

    @Resource
    private OrgBillReceiptTemplateMapper orgBillReceiptTemplateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(LssueVO lssueVO) throws Exception {
        // 插入
        OrgBillReceiptDO billReceiptDO = new OrgBillReceiptDO();
        Long build = lssueVO.getBuild();
        BuildDO buildDO = buildMapper.selectById(build);
        if (buildDO != null) {
            billReceiptDO.setVillageId(buildDO.getVillageId());
        }
        billReceiptDO.setBuildId(build);
        billReceiptDO.setBillId(lssueVO.getBillId());
        if (lssueVO.getReceiptRuleId() != null) {
            billReceiptDO.setRuleId(lssueVO.getReceiptRuleId());
        }

        ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billReceiptDO.getBillId());
        if (contractOrderBillDO != null) {
            ContractDO contractDO = contractMapper.selectById(contractOrderBillDO.getContractId());
            if (contractDO != null) {
                billReceiptDO.setRoomNumber(contractDO.getRoomNumber());
            }
        }

        billReceiptDO.setPaymentCompanyId(lssueVO.getPaymentCompanyId());
        billReceiptDO.setPaymentCompany(lssueVO.getPaymentCompany());
        billReceiptDO.setPaymentUname(lssueVO.getPaymentUname());
        billReceiptDO.setPaymentAddress(lssueVO.getPaymentAddress());
        billReceiptDO.setPaymentPhone(lssueVO.getPaymentPhone());
        billReceiptDO.setReceiptNumber(lssueVO.getReceiptNumber());
        billReceiptDO.setNumberType(lssueVO.getNumberType());
        if (billReceiptDO.getNumberType().equals("1")) {
            //规则生成
            OrgBillReceiptRuleDO billReceiptRuleDO = orgBillReceiptRuleMapper.selectById(lssueVO.getReceiptRuleId());
            if (billReceiptRuleDO != null) {
                String prefix = billReceiptRuleDO.getPrefix();
                Integer receiptInt = Integer.valueOf(lssueVO.getReceiptNumber().replaceAll(prefix, ""));
                billReceiptDO.setReceiptInt(receiptInt);
            }
        }

        billReceiptDO.setPrice(lssueVO.getApplicationInvoicedAmount());
        billReceiptDO.setCanReceiptAmount(lssueVO.getInvoicedAmount());
        billReceiptDO.setApplyReceiptAmount(lssueVO.getApplicationInvoicedAmount());
        billReceiptDO.setCurrency("CNY");
        billReceiptDO.setStatus("6");
        billReceiptDO.setCollectionCompanyId(lssueVO.getCollectionCompanyId());
        OrgBillReceiptSellerDO orgBillReceiptSellerDO = orgBillReceiptSellerMapper.selectById(lssueVO.getCollectionCompanyId());
        if (orgBillReceiptSellerDO != null) {
            billReceiptDO.setCollectionCompany(orgBillReceiptSellerDO.getCompanyName());
        }

        billReceiptDO.setCollectionUname(lssueVO.getCollectionUname());
        billReceiptDO.setCollectionAddress(lssueVO.getCollectionAddress());
        billReceiptDO.setCollectionPhone(lssueVO.getCollectionPhone());
        billReceiptDO.setRemark(lssueVO.getRemark());
        billReceiptDO.setRemitType(lssueVO.getRemitType());
        billReceiptDO.setCostType(lssueVO.getCostType());
        billReceiptDO.setCostName(lssueVO.getCostName());
        Long userId = WebFrameworkUtils.getLoginUserId();
        billReceiptDO.setIssuerUid(String.valueOf(userId));
        Date issuerTime = lssueVO.getIssuerTime();
        if (issuerTime == null) {
            issuerTime = new Date();
        }
        billReceiptDO.setIssuerTime(issuerTime);
        billReceiptDO.setApplyStatus("0");

        Mapper.insert(billReceiptDO);

        updateStreamReceiptNo(billReceiptDO.getBillId(), billReceiptDO.getReceiptNumber());

        // 返回
        return billReceiptDO.getId();
    }

    /**
     * @param billId
     * @param receiptNumber
     */
    private void updateStreamReceiptNo(Long billId, String receiptNumber) {
        LambdaQueryWrapperX<OrgBillStreamMiddleDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(OrgBillStreamMiddleDO::getBillId, billId);
        List<OrgBillStreamMiddleDO> billStreamMiddleDOList = orgBillStreamMiddleMapper.selectList(queryWrapperX1);
        List<BillStreamDO> billStreamList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(billStreamMiddleDOList)) {
            List<Long> streamIdList = billStreamMiddleDOList.stream().map(aa -> aa.getStreamId()).distinct().collect(Collectors.toList());
            LambdaQueryWrapperX<BillStreamDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
            queryWrapperX2.in(BillStreamDO::getId, streamIdList);
            billStreamList = billStreamMapper.selectList(queryWrapperX2);
        }

        LambdaQueryWrapperX<BillStreamDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
        queryWrapperX2.eq(BillStreamDO::getBillId, billId);
        billStreamList.addAll(billStreamMapper.selectList(queryWrapperX2));

        if (CollectionUtils.isNotEmpty(billStreamList)) {
            billStreamList.forEach(billStreamDO -> {
                billStreamDO.setReceiptNo(receiptNumber);
                billStreamMapper.updateById(billStreamDO);
            });

        }
    }

    @Override
    public void update(OrgBillReceiptSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillReceiptDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillReceiptDO.class);
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
            throw exception(BILL_RECEIPT_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillReceiptDO get(Long id) {
        OrgBillReceiptDO billReceiptDO = Mapper.selectById(id);
        if (billReceiptDO != null) {
            extracted(billReceiptDO);

            ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billReceiptDO.getBillId());
            BillingStatementVO billingStatementVO = new BillingStatementVO();
            billingStatementVO.setCostName(billReceiptDO.getCostName());
            billingStatementVO.setBillId(contractOrderBillDO.getId());
            Long villageId = billReceiptDO.getVillageId();
            VillageDO villageDO = villageMapper.selectById(villageId);
            if (villageDO != null) {
                billingStatementVO.setVillageName(villageDO.getName());
            }
            BuildDO buildDO = buildMapper.selectById(billReceiptDO.getBuildId());
            if (buildDO != null) {
                billingStatementVO.setBuildName(buildDO.getBuildName());
            }
            billingStatementVO.setRoomNumber(getRoomName(billReceiptDO));
            Long paymentCompanyId = billReceiptDO.getPaymentCompanyId();
            OwnerDO ownerDO = ownerMapper.selectById(paymentCompanyId);
            if (ownerDO != null) {
                billingStatementVO.setOwnerName(ownerDO.getName());
            }
            BigDecimal receivable = new BigDecimal(contractOrderBillDO.getReceivable()).add(new BigDecimal(contractOrderBillDO.getLateFee()));
            billingStatementVO.setReceivable(receivable);
            billingStatementVO.setCanReceiptAmount(billReceiptDO.getCanReceiptAmount());
            billingStatementVO.setApplyReceiptAmount(billReceiptDO.getApplyReceiptAmount());
            billingStatementVO.setPayDate(contractOrderBillDO.getPayDate());

            billReceiptDO.setBillingStatement(billingStatementVO);
        }
        return billReceiptDO;
    }

    private String getRoomName(OrgBillReceiptDO orgBillReceiptDO) {
        String roomNumber = orgBillReceiptDO.getRoomNumber();
        if (StringUtils.isNotEmpty(roomNumber)) {
            String[] roomNumbers = roomNumber.split(",");
            StringBuilder sb = new StringBuilder();
            for (String roomId : roomNumbers) {
                RoomDO roomDO = roomMapper.selectById(roomId);
                if (roomDO != null) {
                    String roomName = roomDO.getRoomName();
                    String lc = roomName.substring(0, roomName.length() - 2); // 截取左边的数据
                    sb.append(lc + "-" + roomName).append(",");
                }

            }
            String roomName = sb.toString();
            if (StringUtils.isNotEmpty(roomName)) {
                roomName = roomName.substring(0, roomName.length() - 1);
                return roomName;
            }
        }
        return "";
    }

    @Override
    public PageResult<OrgBillReceiptDO> getPage(OrgBillReceiptPageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillReceiptDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getBillId() != null) {
            queryWrapperX.eq(OrgBillReceiptDO::getBillId, pageReqVO.getBillId());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getIssuerUid())) {
            queryWrapperX.eq(OrgBillReceiptDO::getIssuerUid, pageReqVO.getIssuerUid());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getPaymentCompany())) {
            queryWrapperX.like(OrgBillReceiptDO::getPaymentCompany, pageReqVO.getPaymentCompany());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getReceiptNumber())) {
            queryWrapperX.like(OrgBillReceiptDO::getReceiptNumber, pageReqVO.getReceiptNumber());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getCollectionCompany())) {
            queryWrapperX.like(OrgBillReceiptDO::getCollectionCompany, pageReqVO.getCollectionCompany());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStatus())) {
            queryWrapperX.eq(OrgBillReceiptDO::getStatus, pageReqVO.getStatus());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getCostName())) {
            queryWrapperX.like(OrgBillReceiptDO::getCostName, pageReqVO.getCostName());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStartCreateTime())) {
            queryWrapperX.apply("DATE_FORMAT(create_time,'%Y-%m-%d') between DATE_FORMAT(" + pageReqVO.getStartCreateTime() + ",'%Y-%m-%d') and DATE_FORMAT(" + pageReqVO.getEndCreateTime() + ",'%Y-%m-%d') ");
        }

        PageResult<OrgBillReceiptDO> orgBillReceiptDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<OrgBillReceiptDO> list = orgBillReceiptDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(orgBillReceiptDO -> {
                extracted(orgBillReceiptDO);
            });
        }
        return orgBillReceiptDOPageResult;
    }

    private void extracted(OrgBillReceiptDO orgBillReceiptDO) {
        String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(orgBillReceiptDO.getIssuerUid()));
        orgBillReceiptDO.setIssuerUid(userName);

        OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(orgBillReceiptDO.getCostType());
        if (orgBillCostTypeDO != null) {
            orgBillReceiptDO.setCostType(orgBillCostTypeDO.getName());
            orgBillReceiptDO.setCostName(orgBillCostTypeDO.getName());
        } else {
            orgBillReceiptDO.setCostType("--");
            orgBillReceiptDO.setCostName("--");
        }
        String dataLabel = DictFrameworkUtils.getDictDataLabel("RECEIPT_STATUS", orgBillReceiptDO.getStatus());
        orgBillReceiptDO.setStatusName(dataLabel);

        Long buildId = orgBillReceiptDO.getBuildId();
        BuildDO buildDO = buildMapper.selectById(buildId);
        if (buildDO != null) {
            String buildName = buildDO.getBuildName();
            orgBillReceiptDO.setBuildName(buildName);
        }


    }

    /**
     * @param billIds
     * @return
     */
    @Override
    public CommonResult<?> lssue(List<Long> billIds) {
        LssueVO lssueVO = new LssueVO();
        lssueVO.setBillId(billIds.get(0));
        //查询收款方账单
        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(ContractOrderBillDO::getId, billIds);
        queryWrapperX.eq(ContractOrderBillDO::getBillType, "1");
        List<ContractOrderBillDO> contractOrderBillDOS = contractOrderBillMapper.selectList(queryWrapperX);
        if (CollectionUtils.isEmpty(contractOrderBillDOS)) {
            return CommonResult.error(406, "当前无符合条件账单开据");
        }
        LambdaQueryWrapperX<OrgBillReceiptDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.in(OrgBillReceiptDO::getBillId, billIds);
        int size = Mapper.selectList(queryWrapperX1).size();
        if (size >= 1) {
            return CommonResult.error(406, "当前所选账单已开据完,无法进行开据操作");
        }


        List<Long> contractIdList = contractOrderBillDOS.stream().map(ContractOrderBillDO::getContractId).collect(Collectors.toList());
        LambdaQueryWrapperX<ContractDO> contractDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        contractDOLambdaQueryWrapperX.in(ContractDO::getId, contractIdList);
        List<ContractDO> contractDOS = contractMapper.selectList(contractDOLambdaQueryWrapperX);
        List<Long> buildsList = contractDOS.stream().map(ContractDO::getBuildId).distinct().collect(Collectors.toList());

        lssueVO.setBuild(buildsList.get(0));
        OrgBillReceiptRuleDO billReceiptRuleDO = orgBillReceiptRuleMapper.getByBuildsInfo(buildsList);
        if (billReceiptRuleDO == null) {
            return CommonResult.error(406, "当前勾选账单存在没有配置收据编号生成规则");
        }

        lssueVO.setReceiptRuleId(billReceiptRuleDO.getId());
        lssueVO.setReceiptRuleName(billReceiptRuleDO.getName());

        List<OrgBillReceiptSellerDO> billReceiptSellerList = orgBillReceiptSellerMapper.getByBuildsList(buildsList);
        lssueVO.setPayeeUnitList(billReceiptSellerList);


        Long ownerId = contractDOS.get(0).getOwnerId();
        OwnerDO ownerDO = ownerMapper.selectById(ownerId);
        if (ownerDO != null) {
            lssueVO.setPaymentCompanyId(ownerDO.getId());
            lssueVO.setPaymentCompany(ownerDO.getName());
        }
        //查询匹配金额
        LambdaQueryWrapperX<BillStreamDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
        queryWrapperX2.apply("match_status!='2'");
        queryWrapperX2.in(BillStreamDO::getBillId, billIds);
        List<BillStreamDO> billStreamDOS = billStreamMapper.selectList(queryWrapperX2);
        if (CollectionUtils.isEmpty(billStreamDOS)) {
            return CommonResult.error(406, "当前所选账单未匹配金额");
        }
        BigDecimal invoicedAmount = billStreamDOS.stream().
                map(aa -> aa.getMatchPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        lssueVO.setInvoicedAmount(invoicedAmount);
        lssueVO.setApplicationInvoicedAmount(invoicedAmount);
        lssueVO.setCostType(billStreamDOS.get(0).getCostType());
        OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(lssueVO.getCostType());
        if (orgBillCostTypeDO != null) {
            lssueVO.setCostTypeName(orgBillCostTypeDO.getName());
            lssueVO.setCostName(orgBillCostTypeDO.getName());
        }
        return CommonResult.success(lssueVO);
    }

    /**
     * 收据编号
     *
     * @param buildId
     * @return
     */
    @Override
    public CommonResult<?> getReceiptNumber(Long buildId) {
        List<Long> buildIdList = Lists.newArrayList();
        buildIdList.add(buildId);
        OrgBillReceiptRuleDO billReceiptRuleDO = orgBillReceiptRuleMapper.getByBuildsInfo(buildIdList);
        String prefix = billReceiptRuleDO.getPrefix();
        String redisKey = billReceiptRuleDO.getId() + ":" + prefix;
        Integer receiptNumber = (Integer) redisTemplate.opsForValue().get(redisKey);
        if (receiptNumber == null) {
            receiptNumber = Integer.valueOf(billReceiptRuleDO.getStartNumber()) + 1;
        } else {
            receiptNumber = receiptNumber + 1;
        }
        if (receiptNumber > Integer.valueOf(billReceiptRuleDO.getEndNumber())) {
            return CommonResult.error(406, "当前规则编号已生成至结束编号,请重新生成规则");
        }
        redisTemplate.opsForValue().set(redisKey, receiptNumber);
        return CommonResult.success(prefix + receiptNumber);
    }

    /**
     * @param id
     */
    @Override
    public void send(Long id) {

        OrgBillReceiptDO orgBillReceiptDO = Mapper.selectById(id);
        orgBillReceiptDO.setStatus("1");
        Mapper.updateById(orgBillReceiptDO);

        //站内消息
        Map<String, Object> map = new HashMap<>();
        map.put("receiptNumber", orgBillReceiptDO.getReceiptNumber());
        map.put("costName", orgBillReceiptDO.getCostName());
        map.put("applyReceiptAmount", orgBillReceiptDO.getApplyReceiptAmount());
        map.put("paymentCompany", orgBillReceiptDO.getPaymentCompany());
        map.put("collectionCompany", orgBillReceiptDO.getCollectionCompany());
        map.put("issuerTime", orgBillReceiptDO.getIssuerTime());
        map.put("remark", orgBillReceiptDO.getRemark());
        NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
        notifySendSingleToUserReqDTO.setTemplateCode("RECEIPT_SEND ");
        notifySendSingleToUserReqDTO.setUserId(orgBillReceiptDO.getPaymentCompanyId());
        notifySendSingleToUserReqDTO.setTemplateParams(map);
        notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
    }

    /**
     * @param id
     */
    @Override
    public void recovery(Long id) {

        OrgBillReceiptDO orgBillReceiptDO = Mapper.selectById(id);
        orgBillReceiptDO.setStatus("2");
        Mapper.updateById(orgBillReceiptDO);
    }

    /**
     * @param id
     */
    @Override
    public void toVoid(Long id) {
        OrgBillReceiptDO orgBillReceiptDO = Mapper.selectById(id);
        orgBillReceiptDO.setStatus("5");
        Mapper.updateById(orgBillReceiptDO);
        updateStreamReceiptNo(orgBillReceiptDO.getBillId(), orgBillReceiptDO.getReceiptNumber());
    }

    /**
     * @param id
     * @param applyTemplateId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generate(Long id, Long applyTemplateId) throws Exception {
        // 更新
        OrgBillReceiptDO updateObj = Mapper.selectById(id);
        updateObj.setApplyTemplateId(applyTemplateId);
        String url = generateReceoptUrl(updateObj);
        updateObj.setReceoptFileUrl(url);
        updateObj.setApplyStatus("2");
        //生成收据
        Mapper.updateById(updateObj);
        return url;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importReceiptList(List<ReceiptImportVO> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("导入账单收据数据不能为空");
        }
        list.forEach(ri -> {
            String billNumber = ri.getBillNumber();
            LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ContractOrderBillDO::getOrderNumber, billNumber);
            ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectOne(queryWrapperX);
            if (contractOrderBillDO == null) {
                throw new RuntimeException("账单编号" + billNumber + "不存在,请核对后重新输入");
            }
            Long contractId = contractOrderBillDO.getContractId();
            ContractDO contractDO = contractMapper.selectById(contractId);
            LssueVO lssueVO = new LssueVO();
            lssueVO.setBuild(contractDO.getBuildId());
            lssueVO.setBillId(contractOrderBillDO.getId());
            String receiptNumber = ri.getReceiptNumber();
            if (StringUtils.isEmpty(receiptNumber)) {
                throw new RuntimeException("收据编号不能为空");
            }
            lssueVO.setReceiptNumber(receiptNumber);
            Date issuerTime = ri.getIssuerTime();
            if (issuerTime != null) {
                throw new RuntimeException("开据日期不能为空");
            }
            lssueVO.setIssuerTime(issuerTime);
            if (StringUtils.isNotEmpty(ri.getRemitType())) {
                lssueVO.setRemitType(ri.getRemitType());
            }
            BigDecimal canReceiptAmount = ri.getCanReceiptAmount();
            if (canReceiptAmount != null) {
                throw new RuntimeException("开据金额不能为空");
            }
            lssueVO.setInvoicedAmount(canReceiptAmount);
            lssueVO.setApplicationInvoicedAmount(canReceiptAmount);
            lssueVO.setCostType(contractOrderBillDO.getFeeType());
            String costName = ri.getCostName();
            if (StringUtils.isEmpty(costName)) {
                OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(lssueVO.getCostType());
                if (orgBillCostTypeDO != null) {
                    costName = orgBillCostTypeDO.getName();
                }
            }
            lssueVO.setCostName(costName);
            String collectionCompany = ri.getCollectionCompany();
            if (StringUtils.isEmpty(collectionCompany)) {
                throw new RuntimeException("收款单位不能为空");
            }
            LambdaQueryWrapperX<OrgBillReceiptSellerDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(OrgBillReceiptSellerDO::getCompanyName, collectionCompany);
            List<OrgBillReceiptSellerDO> billReceiptSellerDOList = orgBillReceiptSellerMapper.selectList(queryWrapperX1);
            if (CollectionUtils.isEmpty(billReceiptSellerDOList)) {
                throw new RuntimeException("收款单位" + collectionCompany + "未配置在【收据设置->收款方信息】中,请核对");
            }
            Long id = billReceiptSellerDOList.get(0).getId();
            lssueVO.setCollectionCompanyId(id);

            String collectionUname = ri.getCollectionUname();
            if (StringUtils.isNotEmpty(collectionUname)) {
                lssueVO.setCollectionUname(collectionUname);
            }
            String collectionAddress = ri.getCollectionAddress();
            if (StringUtils.isNotEmpty(collectionAddress)) {
                lssueVO.setCollectionAddress(collectionAddress);
            }

            String collectionPhone = ri.getCollectionPhone();
            if (StringUtils.isNotEmpty(collectionPhone)) {
                lssueVO.setCollectionPhone(collectionPhone);
            }

            lssueVO.setNumberType("1");
            String remark = ri.getRemark();
            if (StringUtils.isNotEmpty(remark)) {
                lssueVO.setRemark(remark);
            }
            OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
            if (ownerDO != null) {
                lssueVO.setPaymentCompanyId(ownerDO.getId());
                lssueVO.setPaymentCompany(ownerDO.getName());
            }
            String paymentUname = ri.getPaymentUname();
            if (StringUtils.isNotEmpty(paymentUname)) {
                lssueVO.setPaymentUname(paymentUname);
            }
            String paymentAddress = ri.getPaymentAddress();
            if (StringUtils.isNotEmpty(paymentAddress)) {
                lssueVO.setPaymentAddress(paymentAddress);
            }

            String paymentPhone = ri.getPaymentPhone();
            if (StringUtils.isNotEmpty(paymentPhone)) {
                lssueVO.setPaymentPhone(paymentPhone);
            }
            try {
                this.create(lssueVO);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException(406, e.getMessage());
            }
        });
    }

    @Override
    public void isCheckReceipt(Long billId) {
        String billStatus = contractOrderBillMapper.selectById(billId).getBillStatus();
        if (!StringUtils.equals(billStatus, "1")) {
            throw new ServiceException(406, "当前账单未收款,无法进行开据操作");
        }
        LambdaQueryWrapperX<OrgBillReceiptDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillReceiptDO::getBillId, billId);
        queryWrapperX.in(OrgBillReceiptDO::getStatus, Arrays.asList("1,3,4,6".split(",")));
        List<OrgBillReceiptDO> orgBillReceiptDOS = Mapper.selectList(queryWrapperX);
        if (orgBillReceiptDOS.size() >= 1) {
            throw new ServiceException(406, "当前账单已开据完,无法进行开据操作");
        }

        //查询匹配金额
        LambdaQueryWrapperX<BillStreamDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
        queryWrapperX2.apply("match_status!='2'");
        queryWrapperX2.in(BillStreamDO::getBillId, billId);
        List<BillStreamDO> billStreamDOS = billStreamMapper.selectList(queryWrapperX2);
        if (CollectionUtils.isEmpty(billStreamDOS)) {

            throw new ServiceException(406, "当前所选账单未匹配金额");
        }
    }

    private String generateReceoptUrl(OrgBillReceiptDO billReceiptDO) throws Exception {
        String path = "/usr/uploads/receopt";
        File filepath = new File(path);
        if (!filepath.exists()) {//如果文件夹不存在
            /*filepath.setWritable(true, false);*/    //设置写权限，windows下不用此语句
            filepath.mkdirs();//创建文件夹
        }


        String uuid = UuidUtils.generateUuid();
        String docPath = path + "/" + uuid + ".docx";
        //开据人
        String issuerUid = systemUserMapper.getByOperatorIdUserName(Long.valueOf(billReceiptDO.getIssuerUid()));
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sim.format(billReceiptDO.getIssuerTime());
        sim = new SimpleDateFormat("yyyy-MM-dd");
        String issuerTime = sim.format(sim.parse(format));
        OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(billReceiptDO.getCostType());
        String costTypeName = "";
        if (orgBillCostTypeDO != null) {
            costTypeName = orgBillCostTypeDO.getName();
        }
        String finalCostTypeName = costTypeName;

        String remitType = DictFrameworkUtils.getDictDataLabel("REMIT_TYPE", billReceiptDO.getRemitType());

        BuildDO buildDO = buildMapper.selectById(billReceiptDO.getBuildId());
        if (buildDO != null) {
            billReceiptDO.setBuildName(buildDO.getBuildName());
        }


        ContractOrderBillDO contractOrderBillDO = contractOrderBillMapper.selectById(billReceiptDO.getBillId());

        // 获取项目word转pdf文件夹目录
        OrgBillReceiptTemplateDO receiptTemplateDO = orgBillReceiptTemplateMapper.selectById(billReceiptDO.getApplyTemplateId());
        String templatePath = receiptTemplateDO.getTemplatePath();
        // 创建URL对象
        URL url = new URL(templatePath); // 替换为你要读取的URL
        // 打开连接
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(
                new HashMap<String, Object>() {{
                    //收据信息
                    put("开据人", issuerUid);
                    put("开据时间", issuerTime);
                    put("开据金额D", MoneyConverter.convert(Double.valueOf(Tools.DecimalFormat(billReceiptDO.getApplyReceiptAmount()))));
                    put("开据金额X", billReceiptDO.getApplyReceiptAmount());
                    put("费用名称", billReceiptDO.getCostName());
                    put("费用类型", finalCostTypeName);
                    put("汇款方式", remitType);
                    put("收据编号", billReceiptDO.getReceiptNumber());

                    //交收款方
                    put("收款方地址", billReceiptDO.getCollectionAddress());
                    put("收款方电话", billReceiptDO.getCollectionPhone());
                    put("收款人", billReceiptDO.getCollectionUname());
                    put("收款单位", billReceiptDO.getCollectionCompany());
                    put("交款方地址", billReceiptDO.getPaymentAddress());
                    put("交款方电话", billReceiptDO.getPaymentPhone());
                    put("交款人", billReceiptDO.getPaymentUname());
                    put("交款单位", billReceiptDO.getPaymentCompany());
                    //房源信息
                    put("楼宇名称", billReceiptDO.getBuildName());
                    put("楼层房号", getRoomName(billReceiptDO));
                    //账单信息
                    put("账单编号", contractOrderBillDO.getOrderNumber());
                    put("楼宇名称", billReceiptDO.getBuildName());

                }});
        template.write(new FileOutputStream(docPath));
        String pdfPath = path + "/" + uuid + ".pdf";
        // 将word转换为pdf
        WordToPdfUtil.word2Pdf(docPath, pdfPath);// E:\\test\\test.docx为word文档路径


        FileInputStream is = new FileInputStream(pdfPath);
        String pdfUrl = fileApi.createFile(is.readAllBytes());
        WordToPdfUtil.deleteFile(docPath);
        WordToPdfUtil.deleteFile(pdfPath);
        is.close();
        inputStream.close();
        return pdfUrl;


    }

}