package cn.sdqingyun.smartpark.module.bus.service.org;


import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.pdf.WordToPdfUtil;
import cn.sdqingyun.smartpark.framework.common.util.qrcode.QRCodeUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.BillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractIdCompyOrderBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.NoticeListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractExpireRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgAccountDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerContacts.OwnerContactsDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractExpireRuleMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractSelectedPropertieMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.expenseclause.ExpenseClauseMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgAccountMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillNoticeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillNoticeTemplateMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerContacts.OwnerContactsMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.service.contract.ContractOrderBillService;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import cn.sdqingyun.smartpark.module.system.api.mail.MailSendApi;
import cn.sdqingyun.smartpark.module.system.api.mail.dto.MailSendSingleToUserReqDTO;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.sdqingyun.smartpark.module.system.api.sms.SmsSendApi;
import cn.sdqingyun.smartpark.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_NOTICE_NOT_EXISTS;

/**
 * 收款通知记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Transactional(readOnly = true)
public class OrgBillNoticeServiceImpl implements OrgBillNoticeService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private OwnerContactsMapper ownerContactsMapper;

    @Autowired
    private ContractOrderBillService contractOrderBillService;

    @Resource
    private OrgBillNoticeMapper billNoticeMapper;

    @Autowired
    private SmsSendApi smsSendApi;

    @Autowired
    private MailSendApi mailSendApi;

    @Resource
    private NotifyMessageSendApi notifySendService;

    @Autowired
    private FileApi fileApi;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private OrgBillNoticeTemplateMapper orgBillNoticeTemplateMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private ExpenseClauseMapper expenseClauseMapper;

    @Resource
    private OrgAccountMapper orgAccountMapper;


    /**
     * @param createReqVO
     * @param response
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String batchCreate(OrgBillNoticeSaveReqVO createReqVO, HttpServletResponse response) throws Exception {

        List<String> pdfPathList = Lists.newArrayList();
        // 插入
        OrgBillNoticeDO billNotice = BeanUtils.toBean(createReqVO, OrgBillNoticeDO.class);
        //1=逐张生成;2=按租客合并;3=按合同合并;
        String buildType = billNotice.getBuildType();
        if (StringUtils.equals(buildType, "1")) {
            //逐张生成
            if (StringUtils.isNotEmpty(billNotice.getNoticeType())) {
                //通知
                pdfPathList = noticeType1(billNotice);
            }
        }
        if (StringUtils.equals(buildType, "2")) {
            //按租客合并
            if (StringUtils.isNotEmpty(billNotice.getNoticeType())) {
                //通知
                pdfPathList = noticeType2(billNotice);
            }
        }
        if (StringUtils.equals(buildType, "3")) {
            //按合同合并
            if (StringUtils.isNotEmpty(billNotice.getNoticeType())) {
                //通知
                pdfPathList = noticeType3(billNotice);
            }
        }

        if (CollectionUtils.isNotEmpty(pdfPathList)) {
            String zipFileName = UuidUtils.generateUuid() + ".zip";
            String zipPath = "/usr/uploads/contract/zip/";
            File filepath = new File(zipPath);
            if (!filepath.exists()) {//如果文件夹不存在
                filepath.mkdirs();//创建文件夹
            }
            zipPath = zipPath + zipFileName;
            //打包成压缩文件
            getZipUtil(pdfPathList, zipPath);
            FileInputStream is = new FileInputStream(zipPath);
            zipPath = fileApi.createFile(is.readAllBytes());
            //删除zip文件
            for (String pdfPath : pdfPathList) {
                WordToPdfUtil.deleteFile(pdfPath);
            }
            is.close();

            return zipPath;
        }
        return "";
    }


    /**
     * 压缩文件
     *
     * @param path
     * @param outPath
     */
    public static void getZipUtil(List<String> path, String outPath) {
//        List<String>path=Lists.newArrayList();
//        path.add("d:/usr/58a7dbe99e67b7d15f50f987134ee357510f1be9e1818944fa538c0f6be81c39.pdf");
//        path.add("d:/usr/b395a9478553ad2f119f98409f475c7bba67a8739385a06f87fbfa68fcf88a3e.pdf");
        List<File> files = Lists.newArrayList();
        for (String s : path) {
            files.add(new File(s));
        }

        File zipFile = new File(outPath);

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos)) {

            for (File file : files) {
                ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
                zos.putArchiveEntry(entry);

                // 写入文件内容
                byte[] buffer = new byte[1024];
                int length;
                try (FileInputStream fis = new FileInputStream(file)) {
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                }

                zos.closeArchiveEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按合同合并生成账单
     *
     * @param billNoticeOld
     */
    private List<String> noticeType3(OrgBillNoticeDO billNoticeOld) throws Exception {
        List<String> pdfPathList = Lists.newArrayList();
        String[] split = billNoticeOld.getContractBillId().split(",");
        List<BillListVO> byIdBillList = contractOrderBillService.getByIdBillList(Arrays.asList(split));
        Map<String, List<BillListVO>> ownerMap = byIdBillList.stream()
                .collect(Collectors.groupingBy(BillListVO::getContractId));
        for (String contractId : ownerMap.keySet()) {

            OrgBillNoticeDO billNotice = BeanUtils.toBean(billNoticeOld, OrgBillNoticeDO.class);


            List<BillListVO> billListVos = ownerMap.get(contractId);
            List<String> billids = billListVos.stream().map(BillListVO::getId).collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for (String billid : billids) {
                sb.append(billid).append(",");
            }
            String billid = sb.toString();
            if (StringUtils.isNotEmpty(billid)) {
                billid = billid.substring(0, billid.length() - 1);
            }
            billNotice.setContractBillId(billid);
            LambdaQueryWrapper<ContractOrderBillDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(ContractOrderBillDO::getId, billids);
            queryWrapper.orderByAsc(ContractOrderBillDO::getPayStartDate);
            List<ContractOrderBillDO> orderBillList = contractOrderBillService.getOrderBillList(queryWrapper);
            BigDecimal receivable = new BigDecimal("0.00");
            BigDecimal receivablePayment = new BigDecimal("0.00");
            BigDecimal xReceivable = new BigDecimal("0.00");
            BigDecimal lateFee = new BigDecimal("0.00");
            BigDecimal taxAmount = new BigDecimal("0.00");
            for (ContractOrderBillDO contractOrderBillDO : orderBillList) {
                receivable = receivable.add(new BigDecimal(contractOrderBillDO.getReceivable())).add(new BigDecimal(contractOrderBillDO.getLateFee()));
                receivablePayment = receivablePayment.add(new BigDecimal(contractOrderBillDO.getReceivablePayment()));
                lateFee = lateFee.add(new BigDecimal(contractOrderBillDO.getLateFee()));
                taxAmount = taxAmount.add(new BigDecimal(contractOrderBillDO.getTaxAmount()));
            }
            //计算账单
            if (lateFee.compareTo(BigDecimal.ZERO) > 0) {
                receivable = receivable.add(lateFee);
            }
            xReceivable = receivable.subtract(receivablePayment);
            SimpleDateFormat sim = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);
            String[] noticeTypes = billNotice.getNoticeType().split(",");

            ContractDO contractDO = contractMapper.selectById(contractId);
            OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
            String name = "";
            String phone = "";
            String email = "";
            if (ownerDO != null) {
                Long contactId = ownerDO.getContactId();
                OwnerContactsDO contacts = ownerContactsMapper.selectById(contactId);
                if (contacts != null) {
                    name = contacts.getName();
                    phone = contacts.getPhone();
                }

                email = ownerDO.getEmail();
            }


            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("需收金额", xReceivable);
            templateParams.put("应收金额", receivable);
            templateParams.put("账单编号", orderBillList.get(0).getOrderNumber());
            templateParams.put("账单金额", receivable);
            templateParams.put("应收日期", sim.format(orderBillList.get(0).getPayDate()));
            OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(orderBillList.get(0).getFeeType());

            templateParams.put("费用类型", orgBillCostTypeDO.getName());
            templateParams.put("计费周期", sim.format(orderBillList.get(0).getPayStartDate()) + "~" + sim.format(orderBillList.get(orderBillList.size() - 1).getPayEndDate()));
            templateParams.put("合同编号", contractDO.getContractNumber());
            templateParams.put("需收滞纳金", lateFee);
            templateParams.put("产生滞纳金", lateFee);
            templateParams.put("滞纳金起算天数", contractDO.getStartingLateFeeDay());
            templateParams.put("滞纳金比例", contractDO.getLateFeeRatio());
            templateParams.put("滞纳金上限", contractDO.getUpperLimitLateFee());
            LambdaQueryWrapperX<ExpenseClauseDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ExpenseClauseDO::getClauseType, orderBillList.get(0).getClauseType());
            queryWrapperX.eq(ExpenseClauseDO::getContractId, contractDO.getId());
            ExpenseClauseDO expenseClauseDO = expenseClauseMapper.selectOne(queryWrapperX);
            String taxClause = expenseClauseDO.getTaxClause();
            JSONObject jsonObject = JSONObject.parseObject(taxClause);
            templateParams.put("税率", jsonObject.getString("taxRate"));
            templateParams.put("税额", taxAmount);

            //租客信息
            templateParams.put("通知单联系人", name);
            templateParams.put("租客联系人", name);
            templateParams.put("法人", contractDO.getLegalPerson());
            templateParams.put("租客名称", ownerDO.getName());
            //房源信息
            templateParams.put("租赁面积", contractDO.getLeaseArea());
            BuildDO buildDO = buildMapper.selectById(contractDO.getBuildId());
            String buildName = "";
            if (buildDO != null) {
                buildName = buildDO.getBuildName();
            }
            templateParams.put("楼宇名称", buildName);
            templateParams.put("楼层房号", roomNames(contractDO.getRoomNumber()));
            //账户信息
            OrgAccountDO orgAccountDO = orgAccountMapper.selectById(billNoticeOld.getAccountId());
            templateParams.put("开户银行", orgAccountDO.getBank());
            templateParams.put("收款公司", orgAccountDO.getCompany());
            templateParams.put("收款账号", orgAccountDO.getBankAccount());
            templateParams.put("租客账号", ownerDO.getAccount());
            //其他
            String codeImage = "";
            String payUrl = DictFrameworkUtils.getDictDataLabel("NOTIFICATION_PAYMENT_ADDRESS", "scanCodeBillPay") + "?billId=" + billNotice.getContractBillId();
            if (StringUtils.equals(billNotice.getHasQrcode(), "1")) {
                codeImage = QRCodeUtils.generateQRCodeBase64Image(payUrl, 115, 115);
            }
            templateParams.put("收款二维码", codeImage);
            templateParams.put("通知单发送日期", sim.format(new Date()));
            templateParams.put("今日日期", sim.format(new Date()));
            templateParams.put("账单链接", payUrl);


            for (String noticeType : noticeTypes) {
                //1=下载打印;2=短信通知;3=邮件通知;4=站内信
                if (StringUtils.equals(noticeType, "1")) {
                    //下载打印
                    Map<String, String> map = generateBillsOneByOne(templateParams, String.valueOf(contractDO.getBuildId()));
                    if (map != null) {
                        pdfPathList.add(String.valueOf(map.get("pdfPath")));
                        billNotice.setFilePath(String.valueOf(map.get("savePdfUrl")));
                    }
                }
                if (StringUtils.equals(noticeType, "2")) {
                    //短信通知
                    SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO();
                    smsSendSingleToUserReqDTO.setMobile(phone);
                    smsSendSingleToUserReqDTO.setUserId(contractDO.getOwnerId());
                    smsSendSingleToUserReqDTO.setTemplateCode("SMS_467510404");
                    smsSendSingleToUserReqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = smsSendApi.sendSingleSmsToOwner(smsSendSingleToUserReqDTO);
                    if (longCommonResult.getCode() == 0) {
                        billNotice.setSmsStatus("2");
                    } else {
                        billNotice.setSmsStatus("1");
                    }
                }
                if (StringUtils.equals(noticeType, "3")) {
                    //邮件通知
                    MailSendSingleToUserReqDTO reqDTO = new MailSendSingleToUserReqDTO();
                    reqDTO.setMail(email);
                    reqDTO.setTemplateCode("BILL_NOTICE");
                    reqDTO.setUserId(Long.valueOf(contractDO.getOwnerId()));
                    reqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = mailSendApi.sendSingleMailToOwner(reqDTO);
                    if (longCommonResult.getCode() == 0) {
                        billNotice.setEmailStatus("2");
                    } else {
                        billNotice.setEmailStatus("1");
                    }
                }
                if (StringUtils.equals(noticeType, "4")) {
                    //站内信
                    NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                    notifySendSingleToUserReqDTO.setTemplateCode("BILL_NOTICE");
                    notifySendSingleToUserReqDTO.setUserId(Long.valueOf(contractDO.getOwnerId()));
                    notifySendSingleToUserReqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
                    if (longCommonResult.getCode() == 0) {
                        billNotice.setMailStatus("2");
                    } else {
                        billNotice.setMailStatus("1");
                    }
                }
            }

            billNotice.setOwnerId(contractDO.getOwnerId());
            billNotice.setContractId(Long.valueOf(contractId));
            billNotice.setBuildId(contractDO.getBuildId());
            StringBuilder stringBuilder = new StringBuilder();
            for (String billId : billids) {
                stringBuilder.append(billId).append(",");
            }
            billNotice.setContractBillId(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
            billNotice.setTaskKey(UuidUtils.generateUuid().replace("-", ""));
            billNotice.setTenantId(TenantContextHolder.getTenantId());
            if (billNotice.getId() != null) {
                billNoticeMapper.updateById(billNotice);
            } else {
                billNoticeMapper.insert(billNotice);
            }
        }

        return pdfPathList;
    }


    /**
     * 按租客合并生成账单
     *
     * @param billNoticeOld
     */
    private List<String> noticeType2(OrgBillNoticeDO billNoticeOld) throws Exception {
        List<String> pdfPathList = Lists.newArrayList();
        String[] split = billNoticeOld.getContractBillId().split(",");
        List<BillListVO> byIdBillList = contractOrderBillService.getByIdBillList(Arrays.asList(split));
        Map<String, List<BillListVO>> ownerMap = byIdBillList.stream()
                .collect(Collectors.groupingBy(BillListVO::getOwnerId));
        for (String ownerId : ownerMap.keySet()) {

            OrgBillNoticeDO billNotice = BeanUtils.toBean(billNoticeOld, OrgBillNoticeDO.class);

            List<BillListVO> billListVos = ownerMap.get(ownerId);
            List<String> billids = billListVos.stream().map(BillListVO::getId).collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for (String billid : billids) {
                sb.append(billid).append(",");
            }
            String billid = sb.toString();
            if (StringUtils.isNotEmpty(billid)) {
                billid = billid.substring(0, billid.length() - 1);
            }
            billNotice.setContractBillId(billid);
            LambdaQueryWrapper<ContractOrderBillDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(ContractOrderBillDO::getId, billids);
            queryWrapper.orderByAsc(ContractOrderBillDO::getPayStartDate);
            List<ContractOrderBillDO> orderBillList = contractOrderBillService.getOrderBillList(queryWrapper);
            BigDecimal receivable = new BigDecimal("0.00");
            BigDecimal lateFee = new BigDecimal("0.00");
            BigDecimal xReceivable = new BigDecimal("0.00");
            BigDecimal receivablePayment = new BigDecimal("0.00");
            for (ContractOrderBillDO contractOrderBillDO : orderBillList) {
                receivable = receivable.add(new BigDecimal(contractOrderBillDO.getReceivable()));
                receivablePayment = receivablePayment.add(new BigDecimal(contractOrderBillDO.getReceivablePayment()));
                lateFee = lateFee.add(new BigDecimal(contractOrderBillDO.getLateFee()));
            }
            //计算账单
            if (lateFee.compareTo(BigDecimal.ZERO) > 0) {
                receivable = receivable.add(lateFee);
            }
            xReceivable = receivable.subtract(receivablePayment);

            SimpleDateFormat sim = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);
            String[] noticeTypes = billNotice.getNoticeType().split(",");


            OwnerDO ownerDO = ownerMapper.selectById(ownerId);
            String name = "";
            String phone = "";
            String email = "";
            if (ownerDO != null) {
                Long contactId = ownerDO.getContactId();
                OwnerContactsDO contacts = ownerContactsMapper.selectById(contactId);
                if (contacts != null) {
                    name = contacts.getName();
                    phone = contacts.getPhone();
                }

                email = ownerDO.getEmail();
            }
            List<Long> contractIdList = orderBillList.stream().map(ContractOrderBillDO::getContractId).collect(Collectors.toList());

            LambdaQueryWrapper<ContractDO> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ContractDO::getOwnerId, ownerId);
            queryWrapper1.in(ContractDO::getId, contractIdList);
            List<ContractDO> contractDOS = contractMapper.selectList(queryWrapper1);

            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("需收金额", xReceivable);
            templateParams.put("应收金额", receivable);
            templateParams.put("账单编号", orderBillList.get(0).getOrderNumber());
            templateParams.put("账单金额", receivable);
            templateParams.put("应收日期", sim.format(orderBillList.get(0).getPayDate()));
            OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(orderBillList.get(0).getFeeType());

            templateParams.put("费用类型", orgBillCostTypeDO.getName());
            templateParams.put("计费周期", sim.format(orderBillList.get(0).getPayStartDate()) + "~" + sim.format(orderBillList.get(orderBillList.size() - 1).getPayEndDate()));
            templateParams.put("合同编号", contractDOS.get(0).getContractNumber());
            templateParams.put("需收滞纳金", lateFee);
            templateParams.put("产生滞纳金", lateFee);
            templateParams.put("滞纳金起算天数", orderBillList.get(0).getStartingLateFeeDay());
            templateParams.put("滞纳金比例", orderBillList.get(0).getLateFeeRatio());
            templateParams.put("滞纳金上限", orderBillList.get(0).getUpperLimitLateFee());
            LambdaQueryWrapperX<ExpenseClauseDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ExpenseClauseDO::getClauseType, orderBillList.get(0).getClauseType());
            queryWrapperX.eq(ExpenseClauseDO::getContractId, contractDOS.get(0).getId());
            ExpenseClauseDO expenseClauseDO = expenseClauseMapper.selectOne(queryWrapperX);
            String taxClause = expenseClauseDO.getTaxClause();
            JSONObject jsonObject = JSONObject.parseObject(taxClause);
            templateParams.put("税率", jsonObject.getString("taxRate"));
            templateParams.put("税额", orderBillList.get(0).getTaxAmount());

            //租客信息
            templateParams.put("通知单联系人", name);
            templateParams.put("租客联系人", name);
            templateParams.put("法人", contractDOS.get(0).getLegalPerson());
            templateParams.put("租客名称", ownerDO.getName());
            //房源信息
            templateParams.put("租赁面积", contractDOS.get(0).getLeaseArea());
            BuildDO buildDO = buildMapper.selectById(contractDOS.get(0).getBuildId());
            String buildName = "";
            if (buildDO != null) {
                buildName = buildDO.getBuildName();
            }
            templateParams.put("楼宇名称", buildName);
            templateParams.put("楼层房号", roomNames(contractDOS.get(0).getRoomNumber()));
            //账户信息
            OrgAccountDO orgAccountDO = orgAccountMapper.selectById(billNoticeOld.getAccountId());
            templateParams.put("开户银行", orgAccountDO.getBank());
            templateParams.put("收款公司", orgAccountDO.getCompany());
            templateParams.put("收款账号", orgAccountDO.getBankAccount());
            templateParams.put("租客账号", ownerDO.getAccount());
            //其他
            String codeImage = "";
            String payUrl = DictFrameworkUtils.getDictDataLabel("NOTIFICATION_PAYMENT_ADDRESS", "scanCodeBillPay") + "?billId=" + billNotice.getContractBillId();
            if (StringUtils.equals(billNotice.getHasQrcode(), "1")) {
                codeImage = QRCodeUtils.generateQRCodeBase64Image(payUrl, 115, 115);
            }
            templateParams.put("收款二维码", codeImage);
            templateParams.put("通知单发送日期", sim.format(new Date()));
            templateParams.put("今日日期", sim.format(new Date()));
            templateParams.put("账单链接", payUrl);


            for (String noticeType : noticeTypes) {
                //1=下载打印;2=短信通知;3=邮件通知;4=站内信
                if (StringUtils.equals(noticeType, "1")) {
                    //下载打印

                    Map<String, String> map = generateBillsOneByOne(templateParams, String.valueOf(contractDOS.get(0).getBuildId()));
                    if (map != null) {
                        pdfPathList.add(String.valueOf(map.get("pdfPath")));
                        billNotice.setFilePath(String.valueOf(map.get("savePdfUrl")));
                    }
                }
                if (StringUtils.equals(noticeType, "2")) {
                    //短信通知
                    SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO();
                    smsSendSingleToUserReqDTO.setMobile(phone);
                    smsSendSingleToUserReqDTO.setUserId(Long.valueOf(ownerId));
                    smsSendSingleToUserReqDTO.setTemplateCode("SMS_467510404");
                    smsSendSingleToUserReqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = smsSendApi.sendSingleSmsToOwner(smsSendSingleToUserReqDTO);
                    if (longCommonResult.getCode() == 0) {
                        billNotice.setSmsStatus("2");
                    } else {
                        billNotice.setSmsStatus("1");
                    }
                }
                if (StringUtils.equals(noticeType, "3")) {
                    //邮件通知
                    MailSendSingleToUserReqDTO reqDTO = new MailSendSingleToUserReqDTO();
                    reqDTO.setMail(email);
                    reqDTO.setTemplateCode("BILL_NOTICE");
                    reqDTO.setUserId(Long.valueOf(ownerId));
                    reqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = mailSendApi.sendSingleMailToOwner(reqDTO);
                    if (longCommonResult.getCode() == 0) {
                        billNotice.setEmailStatus("2");
                    } else {
                        billNotice.setEmailStatus("1");
                    }
                }
                if (StringUtils.equals(noticeType, "4")) {
                    //站内信
                    NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                    notifySendSingleToUserReqDTO.setTemplateCode("BILL_NOTICE");
                    notifySendSingleToUserReqDTO.setUserId(Long.valueOf(ownerId));
                    notifySendSingleToUserReqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
                    if (longCommonResult.getCode() == 0) {
                        billNotice.setMailStatus("2");
                    } else {
                        billNotice.setMailStatus("1");
                    }
                }
            }

            billNotice.setOwnerId(Long.valueOf(ownerId));
            billNotice.setContractId(contractDOS.get(0).getId());
            billNotice.setBuildId(contractDOS.get(0).getBuildId());
            StringBuilder stringBuilder = new StringBuilder();
            for (String billId : billids) {
                stringBuilder.append(billId).append(",");
            }
            billNotice.setContractBillId(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
            billNotice.setTaskKey(UuidUtils.generateUuid().replace("-", ""));
            billNotice.setTenantId(TenantContextHolder.getTenantId());
            if (billNotice.getId() != null) {
                billNoticeMapper.updateById(billNotice);
            } else {
                billNoticeMapper.insert(billNotice);
            }
        }

        return pdfPathList;
    }

    /**
     * 逐张生成账单
     *
     * @param billNoticeOld
     */
    private List<String> noticeType1(OrgBillNoticeDO billNoticeOld) throws Exception {
        List<String> pdfPathList = Lists.newArrayList();
        String[] billIds = billNoticeOld.getContractBillId().split(",");
        for (String billId : billIds) {

            //计算账单
            ContractOrderBillDO orderBill = contractOrderBillService.getOrderBill(Long.valueOf(billId));
            if (orderBill == null) {
                throw new ServiceException(406, "账单id" + billId + ",已被删除或者不存在,请核对后重试");
            }
            String feeType = orderBill.getFeeType();
            OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(Long.valueOf(feeType));
            BigDecimal xReceivable = new BigDecimal(orderBill.getReceivable());
            BigDecimal receivable = new BigDecimal("0.00");
            if (orderBill.getOverdueDay() >= 1) {
                xReceivable = xReceivable.add(new BigDecimal(orderBill.getLateFee()));
                receivable = xReceivable.subtract(new BigDecimal(orderBill.getReceivablePayment()));

            }


            SimpleDateFormat sim = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);
            String[] noticeTypes = billNoticeOld.getNoticeType().split(",");
            ContractDO contractDO = contractMapper.selectById(orderBill.getContractId());
            Long ownerId = contractDO.getOwnerId();
            OwnerDO ownerDO = ownerMapper.selectById(ownerId);
            String name = "";
            String phone = "";
            String email = "";
            if (ownerDO != null) {
                Long contactId = ownerDO.getContactId();
                OwnerContactsDO contacts = ownerContactsMapper.selectById(contactId);
                if (contacts != null) {
                    name = contacts.getName();
                    phone = contacts.getPhone();
                }
                email = ownerDO.getEmail();
            }


            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("需收金额", xReceivable);
            templateParams.put("应收金额", receivable);
            templateParams.put("账单编号", orderBill.getOrderNumber());
            templateParams.put("账单金额", receivable);
            templateParams.put("应收日期", sim.format(orderBill.getPayDate()));
            templateParams.put("费用类型", orgBillCostTypeDO.getName());
            templateParams.put("计费周期", sim.format(orderBill.getPayStartDate()) + "~" + sim.format(orderBill.getPayEndDate()));
            templateParams.put("合同编号", contractDO.getContractNumber());
            templateParams.put("需收滞纳金", orderBill.getLateFee());
            templateParams.put("产生滞纳金", orderBill.getLateFee());
            templateParams.put("滞纳金起算天数", orderBill.getStartingLateFeeDay());
            templateParams.put("滞纳金比例", orderBill.getLateFeeRatio());
            templateParams.put("滞纳金上限", orderBill.getUpperLimitLateFee());
            LambdaQueryWrapperX<ExpenseClauseDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ExpenseClauseDO::getClauseType, orderBill.getClauseType());
            queryWrapperX.eq(ExpenseClauseDO::getContractId, contractDO.getId());
            ExpenseClauseDO expenseClauseDO = expenseClauseMapper.selectOne(queryWrapperX);
            String taxClause = expenseClauseDO.getTaxClause();
            JSONObject jsonObject = JSONObject.parseObject(taxClause);
            templateParams.put("税率", jsonObject.getString("taxRate"));
            templateParams.put("税额", orderBill.getTaxAmount());

            //租客信息
            templateParams.put("通知单联系人", name);
            templateParams.put("租客联系人", name);
            templateParams.put("法人", contractDO.getLegalPerson());
            templateParams.put("租客名称", ownerDO.getName());
            //房源信息
            templateParams.put("租赁面积", contractDO.getLeaseArea());
            BuildDO buildDO = buildMapper.selectById(contractDO.getBuildId());
            String buildName = "";
            if (buildDO != null) {
                buildName = buildDO.getBuildName();
            }
            templateParams.put("楼宇名称", buildName);
            templateParams.put("楼层房号", roomNames(contractDO.getRoomNumber()));
            //账户信息
            OrgAccountDO orgAccountDO = orgAccountMapper.selectById(billNoticeOld.getAccountId());
            templateParams.put("开户银行", orgAccountDO.getBank());
            templateParams.put("收款公司", orgAccountDO.getCompany());
            templateParams.put("收款账号", orgAccountDO.getBankAccount());
            templateParams.put("租客账号", ownerDO.getAccount());
            //其他
            String codeImage = "";
            String payUrl = DictFrameworkUtils.getDictDataLabel("NOTIFICATION_PAYMENT_ADDRESS", "scanCodeBillPay") + "?billId=" + billNoticeOld.getContractBillId();
            if (StringUtils.equals(billNoticeOld.getHasQrcode(), "1")) {
                codeImage = QRCodeUtils.generateQRCodeBase64Image(payUrl, 115, 115);
            }
            templateParams.put("收款二维码", codeImage);
            templateParams.put("通知单发送日期", sim.format(new Date()));
            templateParams.put("今日日期", sim.format(new Date()));
            templateParams.put("账单链接", payUrl);
            OrgBillNoticeDO billNotice = BeanUtils.toBean(billNoticeOld, OrgBillNoticeDO.class);
            billNotice.setContractBillId(billId);
            String filePath = "";
            String smsStatus = "";
            String emailStatus = "";
            String mailStatus = "";
            for (String noticeType : noticeTypes) {
                //1=下载打印;2=短信通知;3=邮件通知;4=站内信
                if (StringUtils.equals(noticeType, "1")) {
                    //下载打印
                    Map<String, String> map = generateBillsOneByOne(templateParams, String.valueOf(contractDO.getBuildId()));
                    if (map != null) {
                        pdfPathList.add(String.valueOf(map.get("pdfPath")));
                        System.out.println("pdfPath>>" + String.valueOf(map.get("pdfPath")));
                        filePath = String.valueOf(map.get("savePdfUrl"));
                        System.out.println("savePdfUrl>>" + String.valueOf(map.get("savePdfUrl")));
                    }
                }
                if (StringUtils.equals(noticeType, "2")) {
                    //短信通知
                    SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO();
                    smsSendSingleToUserReqDTO.setMobile(phone);
                    smsSendSingleToUserReqDTO.setUserId(ownerId);
                    smsSendSingleToUserReqDTO.setTemplateCode("SMS_467510404");
                    smsSendSingleToUserReqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = smsSendApi.sendSingleSmsToOwner(smsSendSingleToUserReqDTO);
                    if (longCommonResult.getCode() == 0) {
                        smsStatus = "2";
                    } else {
                        smsStatus = "1";
                    }
                }
                if (StringUtils.equals(noticeType, "3")) {
                    //邮件通知
                    MailSendSingleToUserReqDTO reqDTO = new MailSendSingleToUserReqDTO();
                    reqDTO.setMail(email);
                    reqDTO.setTemplateCode("BILL_NOTICE");
                    reqDTO.setUserId(ownerId);
                    reqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = mailSendApi.sendSingleMailToOwner(reqDTO);
                    if (longCommonResult.getCode() == 0) {
                        emailStatus = "2";
                    } else {
                        emailStatus = "1";
                    }
                }
                if (StringUtils.equals(noticeType, "4")) {
                    //站内信
                    NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                    notifySendSingleToUserReqDTO.setTemplateCode("BILL_NOTICE");
                    notifySendSingleToUserReqDTO.setUserId(ownerId);
                    notifySendSingleToUserReqDTO.setTemplateParams(templateParams);
                    CommonResult<Long> longCommonResult = notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
                    if (longCommonResult.getCode() == 0) {
                        mailStatus = "2";
                    } else {
                        mailStatus = "1";
                    }

                }
            }
            billNotice.setMailStatus(mailStatus);
            billNotice.setEmailStatus(emailStatus);
            billNotice.setSmsStatus(smsStatus);
            billNotice.setFilePath(filePath);
            billNotice.setOwnerId(contractDO.getOwnerId());
            billNotice.setContractId(orderBill.getContractId());
            billNotice.setBuildId(contractDO.getBuildId());
            billNotice.setTaskKey(UuidUtils.generateUuid().replace("-", ""));
            billNotice.setTenantId(TenantContextHolder.getTenantId());
            if (billNotice.getId() != null) {
                billNoticeMapper.updateById(billNotice);
            } else {
                billNoticeMapper.insert(billNotice);
            }
        }

        return pdfPathList;
    }

    /**
     * @param templateParams
     * @param buildBind
     * @return
     * @throws Exception
     */
    public Map<String, String> generateBillsOneByOne(Map<String, Object> templateParams, String buildBind) throws Exception {
        Map<String, String> map = new HashMap<>();


        // 生成文件
        //String path = "D:\\";
        String path = "/usr/uploads/contract/";
        File filepath = new File(path);
        if (!filepath.exists()) {//如果文件夹不存在
            filepath.mkdirs();//创建文件夹
        }

        // 获取项目word转pdf文件夹目录
        //查询模板地址
        LambdaQueryWrapperX<OrgBillNoticeTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("REGEXP_LIKE(build_bind, '(^|,)" + buildBind + "($|,)')");
        OrgBillNoticeTemplateDO orgBillNoticeTemplateDO = orgBillNoticeTemplateMapper.selectOne(queryWrapperX);
        String filePath = "";
        Integer noticeTemplateCode = 0;
        if (orgBillNoticeTemplateDO == null) {
            noticeTemplateCode = 1;
        } else {
            // "C:\\Users\\Administrator\\Desktop\\智慧社区\\模板\\缴费通知单.docx"
            filePath = orgBillNoticeTemplateDO.getTemplatePath();
            if (StringUtils.isEmpty(filePath)) {
                noticeTemplateCode = 1;
            }
        }

        if (noticeTemplateCode == 1) {
            BuildDO buildDO = buildMapper.selectById(buildBind);
            String buildName = "";
            if (buildDO != null) {
                buildName = buildDO.getBuildName();
            }
            throw new RuntimeException("当前楼宇" + buildName + "未配置缴费通知单,请配置完后再生成");
        }

        String uuid = UuidUtils.generateUuid();
        String docPath = filepath + "/" + uuid + ".docx";
        URL url = new URL(filePath); // 替换为你要读取的URL
        // 打开连接
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(
                new HashMap<String, Object>() {{
                    //
                    put("需收金额", String.valueOf(templateParams.get("需收金额")));
                    put("应收金额", String.valueOf(templateParams.get("应收金额")));
                    put("账单编号", String.valueOf(templateParams.get("账单编号")));
                    put("账单金额", String.valueOf(templateParams.get("账单金额")));
                    put("应收日期", String.valueOf(templateParams.get("应收日期")));
                    put("费用类型", String.valueOf(templateParams.get("费用类型")));
                    put("计费周期", String.valueOf(templateParams.get("计费周期")));
                    put("合同编号", String.valueOf(templateParams.get("合同编号")));
                    put("需收滞纳金", String.valueOf(templateParams.get("需收滞纳金")));
                    put("产生滞纳金", String.valueOf(templateParams.get("产生滞纳金")));
                    put("滞纳金起算天数", String.valueOf(templateParams.get("滞纳金起算天数")));
                    put("滞纳金比例", String.valueOf(templateParams.get("滞纳金比例")));
                    put("滞纳金上限", String.valueOf(templateParams.get("滞纳金上限")));
                    put("税率", String.valueOf(templateParams.get("税率")));
                    put("税额", String.valueOf(templateParams.get("税额")));

                    //租客信息
                    put("通知单联系人", String.valueOf(templateParams.get("通知单联系人")));
                    put("租客联系人", String.valueOf(templateParams.get("租客联系人")));
                    put("法人", String.valueOf(templateParams.get("法人")));
                    put("租客名称", String.valueOf(templateParams.get("租客名称")));
                    //房源信息
                    put("租赁面积", String.valueOf(templateParams.get("租赁面积")));
                    put("楼宇名称", String.valueOf(templateParams.get("楼宇名称")));
                    put("楼层房号", String.valueOf(templateParams.get("楼层房号")));
                    //账户信息
                    put("开户银行", String.valueOf(templateParams.get("开户银行")));
                    put("收款公司", String.valueOf(templateParams.get("收款公司")));
                    put("收款账号", String.valueOf(templateParams.get("收款账号")));
                    put("租客账号", String.valueOf(templateParams.get("租客账号")));
                    //其他
                    put("收款二维码", String.valueOf(templateParams.get("收款二维码")));
                    put("通知单发送日期", String.valueOf(templateParams.get("通知单发送日期")));
                    put("今日日期", String.valueOf(templateParams.get("今日日期")));
                }});
        template.write(new FileOutputStream(docPath));
        String pdfPath = path + "contract/" + uuid + ".pdf";
        // 将word转换为pdf
        WordToPdfUtil.word2Pdf(docPath, pdfPath);// E:\\test\\test.docx为word文档路径


        FileInputStream is = new FileInputStream(pdfPath);
        String pdfUrl = fileApi.createFile(uuid + ".pdf", pdfPath, is.readAllBytes());
        WordToPdfUtil.deleteFile(docPath);
        map.put("savePdfUrl", pdfUrl);
        map.put("pdfPath", pdfPath);
        is.close();
        inputStream.close();

        return map;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBillNotice(OrgBillNoticeSaveReqVO updateReqVO) {
        // 校验存在
        validateBillNoticeExists(updateReqVO.getId());
        // 更新
        OrgBillNoticeDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillNoticeDO.class);
        billNoticeMapper.updateById(updateObj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBillNotice(Long id) {
        // 校验存在
        validateBillNoticeExists(id);
        // 删除
        billNoticeMapper.deleteById(id);
    }

    private void validateBillNoticeExists(Long id) {
        if (billNoticeMapper.selectById(id) == null) {
            throw exception(BILL_NOTICE_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillNoticeDO getBillNotice(Long id) {
        return billNoticeMapper.selectById(id);
    }

    @Override
    public PageResult<NoticeListVO> getBillNoticePage(NoticeListVO noticeListVO) {
        IPage<NoticeListVO> noticeListPage = billNoticeMapper.getNoticeListPage(MyBatisUtils.buildPage(noticeListVO), noticeListVO);
        List<NoticeListVO> records = noticeListPage.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(noticeListVO1 -> {
                getRoomName(noticeListVO1);
            });
        }
        return new PageResult<>(noticeListPage.getRecords(), noticeListPage.getTotal());
    }

    /**
     * @param noticeListVO
     * @return
     */
    @Override
    public Map<String, Object> getTopNumCount(NoticeListVO noticeListVO) {
        Map<String, Object> map = new HashMap<>();
        IPage<NoticeListVO> noticeListPage = billNoticeMapper.getNoticeListPage(MyBatisUtils.buildPage(noticeListVO), noticeListVO);
        List<NoticeListVO> records = noticeListPage.getRecords();
        //已生成
        map.put("total", records.size());
        //短信发送成功
        int smsSuccess = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getSmsStatus()) && aa.getSmsStatus().equals("2")).collect(Collectors.toList()).size();
        map.put("smsSuccess", smsSuccess);
        //邮件发送成功
        int emailStatus = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getEmailStatus()) && aa.getEmailStatus().equals("2")).collect(Collectors.toList()).size();
        map.put("emailStatus", emailStatus);
        //站内信发送成功
        int mailStatus = records.stream().filter(aa -> StringUtils.isNotEmpty(aa.getMailStatus()) && aa.getMailStatus().equals("2")).collect(Collectors.toList()).size();
        map.put("mailStatus", mailStatus);
        return map;
    }

    private void getRoomName(NoticeListVO noticeListVO) {
        String roomNumber = noticeListVO.getRoomNumber();
        if (StringUtils.isNotEmpty(roomNumber)) {
            noticeListVO.setRoomNumber(roomNames(roomNumber));


        }
    }

    private String roomNames(String roomNumber) {
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
                return roomName = roomName.substring(0, roomName.length() - 1);
            }
        }
        return "";
    }
}