package cn.sdqingyun.smartpark.module.bus.service.contract;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.common.util.pdf.MoneyConverter;
import cn.sdqingyun.smartpark.framework.common.util.pdf.WordToPdfUtil;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.BusOrgDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.expenseclause.ExpenseClauseMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerContacts.OwnerContactsMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepoove.poi.XWPFTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.annotation.Resource;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.crm.enums.ErrorCodeConstants.CONTRACT_NOT_EXISTS;

/**
 * 机构合同 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {

    @Resource
    private ContractMapper Mapper;

    @Resource
    private ExpenseClauseMapper expenseClauseMapper;

    @Autowired
    private ContractOrderBillMapper orderBillMapper;

    @Resource
    private ContractAnnexMapper annexMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private OwnerContactsMapper ownerContactsMapper;

    @Autowired
    private BusOrgMapper orgMapper;

    @Autowired
    private VillageMapper villageMapper;

    @Autowired
    private BuildMapper buildMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Resource
    private FileApi fileApi;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Resource
    private ContractTemplateMapper contractTemplateMapper;

    @Resource
    private ContractOperateLogMapper contractOperateLogMapper;

    @Resource
    private ContractSaveService contractSaveService;

    /**
     * @return
     */
    @Override
    public String contractPdf(ContractSaveVO contractSaveVO) throws IOException, DocumentException {
        String path = "/usr/uploads/contract";
        File filepath = new File(path);
        if (!filepath.exists()) {//如果文件夹不存在
            /*filepath.setWritable(true, false);*/    //设置写权限，windows下不用此语句
            filepath.mkdirs();//创建文件夹
        }
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        Date date1 = new Date();

        //出租人(甲方) contractSaveReqVO.getBuildId()
        Long tenantId = TenantContextHolder.getTenantId();
        BusOrgDO org = orgMapper.selectById(tenantId);
        if (org == null) {
            throw new ServiceException(406, "当前用户下未维护企业基本信息");
        }
        String companyName = org.getCompany();

        //承租人(乙方)
        OwnerDO ownerDO = getByOwnerIdInfo(contractSaveVO.getOwnerId());
        Long parkId = contractSaveVO.getParkId();
        if (parkId == null) {
            throw new ServiceException(406, "园区id字段【parkId】不能为空");
        }
        String parkName = villageMapper.selectById(parkId).getName();
        Long buildId = contractSaveVO.getBuildId();
        String buildName = buildMapper.selectById(buildId).getBuildName();

        //租赁面积
        String clauseTypes = contractSaveVO.getClauseTypes();

        String checkedBuild = contractSaveVO.getCheckedBuild();
        List<ContractSelectedPropertieSaveReqVO> contractSelectedPropertieList = JSONObject.parseArray(checkedBuild, ContractSelectedPropertieSaveReqVO.class);
        BigDecimal area = new BigDecimal("0.00");
        if (CollectionUtils.isNotEmpty(contractSelectedPropertieList)) {

            area = contractSelectedPropertieList.stream().
                    map(aa -> aa.getRentalArea()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }


        //租赁金额
        List<ClauseTypesVO> clauseTypesVos = JSONObject.parseArray(clauseTypes, ClauseTypesVO.class);
        ClauseTypesVO clauseTypesVo = clauseTypesVos.stream().filter(ClauseTypesVo -> ClauseTypesVo.getClauseType() == 1).collect(Collectors.toList()).get(0);
        ClauseTypesVO clauseTypesVo2 = clauseTypesVos.stream().filter(ClauseTypesVo -> ClauseTypesVo.getClauseType() == 2).collect(Collectors.toList()).get(0);
        String reportDetail = clauseTypesVo.getReportDetail();
        List<ContractOrderBillSaveReqVO> collect = Lists.newArrayList();
        List<ContractOrderBillSaveReqVO> contractOrderBillList = JSONObject.parseArray(reportDetail, ContractOrderBillSaveReqVO.class);
        for (ContractOrderBillSaveReqVO contractOrderBillSaveReqVO : contractOrderBillList) {
            OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(Long.valueOf(contractOrderBillSaveReqVO.getFeeType()));
            if (orgBillCostTypeDO != null) {
                if (orgBillCostTypeDO.getIsBond().equals("0")) {
                    collect.add(contractOrderBillSaveReqVO);
                }
            }
        }

        BigDecimal money = new BigDecimal("0.00");
        BigDecimal totalMoney = new BigDecimal("0.00");
        BigDecimal oneMoney = new BigDecimal(collect.get(0).getReceivable());
        for (ContractOrderBillSaveReqVO contractOrderBillSaveReqVO : collect) {
            BigDecimal receivable = new BigDecimal(contractOrderBillSaveReqVO.getReceivable());

            BigDecimal taxAmount = new BigDecimal(contractOrderBillSaveReqVO.getTaxAmount());
            money = money.add(receivable.subtract(taxAmount));

            totalMoney = totalMoney.add(receivable);
        }


        //甲方经办人
        Long operatorId = contractSaveVO.getOperatorId();
        String operatorName = systemUserMapper.getByOperatorIdUserName(operatorId);


        // 获取项目word转pdf文件夹目录
        //String filePath = "D:\\";
        String filePath = DictFrameworkUtils.getDictDataLabel("LEASE_AGREEMENT", "LEASE_AGREEMENT");
        if (StringUtils.isEmpty(filePath)) {
            throw new ServiceException(406, "租赁合同模板为空");
        }
        URL url = new URL(filePath); // 替换为你要读取的URL
        // 打开连接
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        String uuid = UuidUtils.generateUuid();
        String docPath = path + "/" + uuid + ".docx";
        BigDecimal finalArea = area;
        BigDecimal finalMoney = money;
        BigDecimal finalTotalMoney = totalMoney;
        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(
                new HashMap<String, Object>() {{
                    //服务合作协议编号
                    put("protocolNumber", contractSaveVO.getContractNumber());
                    put("landlordName", companyName);
                    put("tenantName", ownerDO.getName());
                    //承租人(乙方)身份证号
                    put("tenantIdCard", ownerDO.getCertificateNumber());
                    put("tenantPhone", ownerDO.getTel());
                    put("houseLocation", companyName + parkName + buildName);
                    put("roomName", contractSaveVO.getRoomNumber());
                    //户型
//                    put("unitType", "【三室两厅】");
                    put("area", finalArea);
                    //用途
//                    put("purpose", "【居住】");
                    //租赁开始时间

                    put("startYear", year.format(contractSaveVO.getContractStartTime()));
                    put("startMonth", month.format(contractSaveVO.getContractStartTime()));
                    put("startDay", day.format(contractSaveVO.getContractStartTime()));
                    //租赁结束时间
                    put("endYear", year.format(contractSaveVO.getContractEndTime()));
                    put("endMonth", month.format(contractSaveVO.getContractEndTime()));
                    put("endDay", day.format(contractSaveVO.getContractEndTime()));
                    put("money", finalMoney);
                    //租赁金额以及物业费金额
                    put("totalMoney", finalTotalMoney);
                    //租赁金额以及物业费金额大写
                    put("amountWords", MoneyConverter.convert(Double.valueOf(Tools.DecimalFormat(finalTotalMoney))));
                    //几个月押金
                    //parametersMap.put("howMany",contractSaveVO.getContractNumber());
                    //房屋保证金
                    String bondClause = clauseTypesVo.getBondClause();
                    String deposit = JSONObject.parseObject(bondClause).getString("bondPrice");
                    put("deposit", deposit);
                    //房屋押金大写
                    put("depositWords", MoneyConverter.convert(Double.valueOf(deposit)));

                    String wyMultipleClause = clauseTypesVo2.getMultipleClause();
                    JSONArray wyJsonArray = JSONArray.parseArray(wyMultipleClause);
                    String howLong = wyJsonArray.getJSONObject(0).getString("day");
                    //物业管理费多久一交
                    put("howLong", howLong);
                    //缴费金额
                    put("howMoney", finalTotalMoney);
                    //缴费金额大写
                    put("howMoneyWords", MoneyConverter.convert(Double.valueOf(finalTotalMoney.toString())));
                    //签订合同几日内支付首期租金
                    put("qdDay", oneMoney);
                    //开户名(甲方)
                    put("accountName", ownerDO.getName());
                    //开户行
                    put("openingBank", ownerDO.getBank());
                    //账号
                    put("account", ownerDO.getAccount());
//                    //电费
//                    put("electricityBill", "【20】");
//                    //冷水费
//                    put("coldWaterFee", "【21】");
//                    //热水费
//                    put("hotWaterFee", "【22】");
                    //签订日期
                    put("year", year.format(date1));
                    put("month", month.format(date1));
                    put("day", day.format(date1));
                    put("operatorName", operatorName);
                    //乙方签字代表
                    put("signedName", contractSaveVO.getSignedName());
                }});

        template.write(new FileOutputStream(docPath));
        String pdfPath = path + "contract/" + uuid + ".pdf";
        // 将word转换为pdf
        WordToPdfUtil.word2Pdf(docPath, pdfPath);// E:\\test\\test.docx为word文档路径


        FileInputStream is = new FileInputStream(pdfPath);
        String pdfUrl = fileApi.createFile(uuid + ".pdf", pdfPath, is.readAllBytes());
        WordToPdfUtil.deleteFile(docPath);
        WordToPdfUtil.deleteFile(pdfPath);
        inputStream.close();
        is.close();
        template.close();
        return pdfUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ContractSaveVO contractSaveVO) {
        return contractSaveService.create(contractSaveVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContractSaveVO contractSaveVO) {

        contractSaveService.update(contractSaveVO);
    }


    @Transactional(readOnly = false)
    @Override
    public void updateById(ContractDO contractDO) {

        Mapper.updateById(contractDO);
    }


    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByIdContract(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteByIdContract(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(CONTRACT_NOT_EXISTS);
        }
    }

    @Override
    public ContractSaveVO get(Long id) throws JsonProcessingException {
        ContractSaveVO contractSaveVO = null;
        ContractDO contractDO = Mapper.selectById(id);
        if (contractDO != null) {
            //经办人
            String userName = systemUserMapper.getByOperatorIdUserName(contractDO.getOperatorId());
            contractDO.setOperatorName(userName);
            //创建人
            String creatorUserName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(contractDO.getCreator()));
            contractDO.setCreator(creatorUserName);

            contractSaveVO = BeanUtils.toBean(contractDO, ContractSaveVO.class);

            LambdaQueryWrapper<AnnexDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AnnexDO::getContractId, contractSaveVO.getId());
            List<AnnexDO> annexList = annexMapper.selectList(queryWrapper);

            //附件
            ObjectMapper contractAnnex = new ObjectMapper();
            contractSaveVO.setContractAnnex(contractAnnex.writeValueAsString(annexList));

            LambdaQueryWrapper<ExpenseClauseDO> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ExpenseClauseDO::getContractId, contractSaveVO.getId());
            List<ExpenseClauseDO> expenseClauseList = expenseClauseMapper.selectList(queryWrapper1);

            ObjectMapper clauseTypes = new ObjectMapper();
            contractSaveVO.setClauseTypes(clauseTypes.writeValueAsString(expenseClauseList));

        }
        return contractSaveVO;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ContractDO selectById(Long id) {

        return Mapper.selectById(id);
    }

    @Override
    public List<ContractDO> getByBuildIdContractList(Long buildId) {
        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractDO::getBuildId, buildId);
        List<String> statusList = Lists.newArrayList();
        statusList.add("17");
        statusList.add("10");
        //已退租
        statusList.add("6");
        //已作废
        statusList.add("11");
        //已到期
        statusList.add("15");
        queryWrapperX.notIn(ContractDO::getStatus, statusList);
        List<ContractDO> contractDOS = Mapper.selectList(queryWrapperX);
        return contractDOS;
    }

    @Override
    public List<ContractDO> getExpireContractList() {
        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("DATE_FORMAT(contract_end_time,'%Y-%m-%d')< DATE_FORMAT(NOW(),'%Y-%m-%d') and `status` not in ('6','15','11')");
        List<ContractDO> contractDOS = Mapper.selectList(queryWrapperX);
        return contractDOS;
    }

    /**
     * @param ownerId
     * @return
     */
    public OwnerDO getByOwnerIdInfo(Long ownerId) {
        return ownerMapper.selectById(ownerId);
    }

    public Long getOwnerNameId(String ownerName) {
        LambdaQueryWrapperX<OwnerDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OwnerDO::getName, ownerName);
        List<OwnerDO> ownerDOS = ownerMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(ownerDOS)) {
            return ownerDOS.get(0).getId();
        }
        return null;
    }

    /**
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public PageResult<ContractDO> getPage(ContractPageReqVO pageReqVO) {


        IPage<ContractDO> listPage = Mapper.getListPage(MyBatisUtils.buildPage(pageReqVO), pageReqVO);
        List<ContractDO> list = listPage.getRecords();
        if (CollectionUtils.isNotEmpty(list)) {
            for (ContractDO contractDO : list) {

                String dataSource = contractDO.getDataSource();
                if (dataSource.equals("0")) {
                    contractDO.setDataSource("新建合同");
                } else {
                    contractDO.setDataSource("导入合同");
                }

                String status = contractDO.getStatus();
                String statusName = DictFrameworkUtils.getDictDataLabel("CONTRACT_STATUS", status);
                contractDO.setStatus(statusName);

                OwnerDO ownerDO = getByOwnerIdInfo(contractDO.getOwnerId());
                if (ownerDO != null) {
                    contractDO.setOwnerName(ownerDO.getName());
                }

                //合同到期计算到期天数
                Long dayCount = DateUtils.getDayCount(contractDO.getContractEndTime(), new Date());
                if (dayCount >= 1) {
                    contractDO.setExpirationDay(Integer.valueOf(String.valueOf(dayCount)));
                }
                LambdaQueryWrapper<ExpenseClauseDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ExpenseClauseDO::getContractId, contractDO.getId());
                List<ExpenseClauseDO> expenseClause = expenseClauseMapper.selectList(queryWrapper);
                ExpenseClauseDO expenseClauseDO1 = expenseClause.stream().filter(ExpenseClauseDO -> ExpenseClauseDO.getClauseType() == 1).collect(Collectors.toList()).get(0);
                String multipleClause = expenseClauseDO1.getMultipleClause();
                JSONArray jsonArray = JSONArray.parseArray(multipleClause);
                String zlDjMoney = jsonArray.getJSONObject(0).getString("dMoney");
                String contractUnitPrice = jsonArray.getJSONObject(0).getString("contractUnitPrice");
                System.out.println("contractDO>>>" + contractDO.getId());
                System.out.println("jsonArray>>>" + jsonArray.getJSONObject(0));
                System.out.println("contractUnitPrice>>>" + contractUnitPrice);
                String contractUnitPriceName = DictFrameworkUtils.getDictDataLabel("CONTRACT_UNIT_PRICE", contractUnitPrice);
                contractDO.setZlDjMoney(zlDjMoney + contractUnitPriceName);

                Long buildId = contractDO.getBuildId();
                BuildDO buildDO = buildMapper.selectById(buildId);
                if (buildDO != null) {
                    String buildName = buildDO.getBuildName();
                    contractDO.setBuildName(buildName);
                }


                //租赁保证金
                String zlBondClause = expenseClauseDO1.getBondClause();
                JSONObject jsonObject = JSONObject.parseObject(zlBondClause);
                contractDO.setZlBondClause(jsonObject.getString("bondPrice"));

                String[] split = contractDO.getRoomNumber().split(",");

                List<JSONObject> roomNumberJson = Lists.newArrayList();
                for (String roomId : split) {
                    RoomDO room = roomMapper.selectById(Long.valueOf(roomId));
                    if (room != null) {
                        String roomName = room.getRoomName();
                        String lc = roomName.substring(0, roomName.length() - 2); // 截取左
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("roomId", room.getId());
                        jsonObject1.put("roomName", lc + "-" + roomName);
                        roomNumberJson.add(jsonObject1);
                    } else {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("roomId", "");
                        jsonObject1.put("roomName", "");
                        roomNumberJson.add(jsonObject1);
                    }
                }

                if (CollectionUtils.isNotEmpty(roomNumberJson)) {
                    contractDO.setRoomNumber(new Gson().toJson(roomNumberJson));
                }


                ExpenseClauseDO expenseClauseDO2 = expenseClause.stream().filter(ExpenseClauseDO -> ExpenseClauseDO.getClauseType() == 2).collect(Collectors.toList()).get(0);
                //物业保证金
                String wyBondClause = expenseClauseDO2.getBondClause();
                JSONObject wyJsonObject = JSONObject.parseObject(wyBondClause);
                contractDO.setWyBondClause(wyJsonObject.getString("bondPrice"));


                String wyMultipleClause = expenseClauseDO2.getMultipleClause();
                JSONArray wyJsonArray = JSONArray.parseArray(wyMultipleClause);
                String wyDjMoney = wyJsonArray.getJSONObject(0).getString("dMoney");
                String wyContractUnitPrice = wyJsonArray.getJSONObject(0).getString("contractUnitPrice");
                String wyContractUnitPriceName = DictFrameworkUtils.getDictDataLabel("CONTRACT_UNIT_PRICE", wyContractUnitPrice);
                contractDO.setWyDjMoney(wyDjMoney + wyContractUnitPriceName);
            }
        }


        return new PageResult<>(listPage.getRecords(), listPage.getTotal());
    }

    /**
     * 批量导入合同
     *
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importContractList(List<ContractImportVO> list) {
        // 校验非空
        if (CollectionUtils.isNotEmpty(list)) {
            throw new RuntimeException("导入合同数据不能为空");
        }
        list.forEach(contractImportVo -> {
            ContractSaveVO contractSaveVO = new ContractSaveVO();
            LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(ContractDO::getContractNumber, contractImportVo.getContractNumber());
            int size = Mapper.selectList(queryWrapperX).size();
            if (size >= 1) {
                throw new RuntimeException("合同编号" + contractImportVo.getContractNumber() + "已存在");
            }
            //合同编号
            contractSaveVO.setContractNumber(contractImportVo.getContractNumber());

            String operatorName = contractImportVo.getOperatorName();
            String operatorId = systemUserMapper.getByOperatorNameUserId(operatorName);
            if (StringUtils.isNotEmpty(operatorId)) {
                contractSaveVO.setOperatorId(Long.valueOf(operatorId));
            } else {
                throw new RuntimeException("经办人" + operatorName + "不存在平台中");
            }
            //签订日期
            Date signingDate = contractImportVo.getSigningDate();
            contractSaveVO.setSigningDate(signingDate);
            //合同开始日期
            contractSaveVO.setContractStartTime(contractImportVo.getLeaseStart());
            //合同结束日期
            contractSaveVO.setContractEndTime(contractImportVo.getLeaseEnd());
            LambdaQueryWrapper<VillageDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(VillageDO::getName, contractImportVo.getParkName());
            List<VillageDO> villageDOS = villageMapper.selectList(queryWrapper);
            Long parkId = null;
            if (CollectionUtils.isNotEmpty(villageDOS)) {
                parkId = villageDOS.get(0).getId();
            }

            if (parkId != null) {
                contractSaveVO.setParkId(parkId);
            } else {
                throw new RuntimeException("园区" + contractImportVo.getParkName() + "不存在平台中");
            }
            String buildName = contractImportVo.getBuildName();
            LambdaQueryWrapperX<BuildDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(BuildDO::getBuildName, buildName);
            queryWrapperX1.eq(BuildDO::getVillageId, parkId);
            List<BuildDO> buildDOS = buildMapper.selectList(queryWrapperX1);
            Long buildId = null;
            if (CollectionUtils.isNotEmpty(buildDOS)) {
                buildId = buildDOS.get(0).getId();
            }

            if (buildId != null) {
                contractSaveVO.setBuildId(buildId);
            } else {
                throw new RuntimeException("园区" + contractImportVo.getParkName() + "下不存在名称为" + buildName + "的楼宇");
            }

            //楼层|房间号|租赁面积,多个以顿号相连 示例:1|101|100、1|102|100
            String buildName1 = contractImportVo.getBuildName1();
            String[] split1 = buildName1.split("、");
            StringBuilder roomIds = new StringBuilder();
            List<ContractSelectedPropertieSaveReqVO> checkedBuildList = Lists.newArrayList();
            for (String s : split1) {
                ContractSelectedPropertieSaveReqVO contractSelectedPropertieSaveReqVO = new ContractSelectedPropertieSaveReqVO();
                String[] split = s.split("|");
                //楼层
                String lc = split[0];
                //房号
                String roomNumber = split[1];
                //租赁面积
                String area = split[2];

                contractSelectedPropertieSaveReqVO.setParkId(parkId);
                contractSelectedPropertieSaveReqVO.setBuildId(buildId);
                Long roomId = getRoomNameId(parkId, buildId, roomNumber);
                if (roomId == null) {
                    contractSelectedPropertieSaveReqVO.setVillageRoomId(roomId);
                    roomIds.append(roomId).append(",");
                } else {
                    throw new RuntimeException("园区" + contractImportVo.getParkName() + ":楼宇" + buildName + "下不存在房间号为" + roomNumber + "的房间");
                }
                contractSelectedPropertieSaveReqVO.setRentalArea(new BigDecimal(area));
                checkedBuildList.add(contractSelectedPropertieSaveReqVO);
            }
            contractSaveVO.setUnitPricePoint(contractImportVo.getUnitPricePoint());
            contractSaveVO.setCalculationOrder(contractImportVo.getCalculationOrder());
            contractSaveVO.setIsReceivableInteger(contractImportVo.getIsReceivableInteger());
            contractSaveVO.setIsReceivableInteger(contractImportVo.getIsReceivableInteger());

            String ownerName = contractImportVo.getOwnerName();

            Long ownerId = getOwnerNameId(ownerName);
            if (ownerId != null) {
                contractSaveVO.setOwnerId(ownerId);
            } else {
                throw new RuntimeException("租客" + ownerName + "不存在平台中");
            }
            contractSaveVO.setLegalPerson(contractImportVo.getLegalPerson());
            contractSaveVO.setSignedName(contractImportVo.getSignedName());
            contractSaveVO.setStartingLateFeeDay(contractImportVo.getStartingLateFeeDay());
            contractSaveVO.setLateFeeRatio(contractImportVo.getLateFeeRatio());
            contractSaveVO.setUpperLimitLateFee(contractImportVo.getUpperLimitLateFee());

            //"[{\"id\":\"\",\"contractId\":\"\",\"parkId\":\"1\",\"buildId\":\"1\",\"villageRoomId\":\"103,102\",\"rentalArea\":\"300\"}]"
            String string = roomIds.toString();
            if (StringUtils.isNotEmpty(string)) {
                contractSaveVO.setRoomNumber(string.substring(0, string.length() - 1));
            }
            contractSaveVO.setLeaseArea(contractImportVo.getTotalArea());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                contractSaveVO.setCheckedBuild(objectMapper.writeValueAsString(checkedBuildList));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            //条款json
            List<JSONObject> jsonObjectList = Lists.newArrayList();

            //租赁
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("clauseType", "1");
            //租赁保证金
            JSONObject jsonBondClause = new JSONObject();
            jsonBondClause.put("bondType", "bond_2");
            jsonBondClause.put("bondPrice", contractImportVo.getZlBondMoney());
            try {
                jsonObject.put("bondClause", objectMapper.writeValueAsString(jsonBondClause));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


            //租金含税
            JSONObject jsonTaxClause = new JSONObject();
            jsonTaxClause.put("taxRate", contractImportVo.getTaxInclusiveRules());
            jsonTaxClause.put("taxRule", contractImportVo.getTaxInclusiveValue());
            try {
                jsonObject.put("taxClause", objectMapper.writeValueAsString(jsonTaxClause));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            //租赁条款
            JSONObject jsonMultipleClause = new JSONObject();
            jsonMultipleClause.put("startLeaseDate", contractImportVo.getLeaseStart());
            jsonMultipleClause.put("endLeaseDate", contractImportVo.getLeaseStart());
            jsonMultipleClause.put("dMoney", contractImportVo.getDMoney());
            jsonMultipleClause.put("contractUnitPrice", contractImportVo.getContractUnitPrice());
            jsonMultipleClause.put("payDay", contractImportVo.getPayDay());
            jsonMultipleClause.put("day", contractImportVo.getDay());
            try {
                jsonObject.put("multipleClause", objectMapper.writeValueAsString(jsonMultipleClause));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //租赁备注条款
            jsonObject.put("remarkClause", contractImportVo.getRemarkClause());
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            //账单明细

//                List<ContractOrderBillSaveReqVO> orderBillList = null;
//                try {
//                    orderBillList = contractOrderBillService.getOrderBillList(
//                            1,
//                            "1",
//                            contractImportVo.getFeeType(),
//                            sim.format(contractImportVo.getLeaseStart()),
//                            sim.format(contractImportVo.getLeaseStart()),
//                            contractImportVo.getDay(),
//                            contractImportVo.getZlBondMoney(),
//                            Integer.valueOf(contractImportVo.getPayDay()),
//                            contractImportVo.getUnitPricePoint(),
//                            contractImportVo.getCalculationOrder(),
//                            contractImportVo.getTaxInclusiveRules(),
//                            new BigDecimal(contractImportVo.getTaxInclusiveValue()),
//                            new BigDecimal(contractImportVo.getTotalArea()),
//                            contractImportVo.getContractUnitPrice(),
//                            new BigDecimal(contractImportVo.getDMoney()),
//                            contractImportVo.getIsReceivableInteger()
//                    );
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//
//                try {
//                    jsonObject.put("reportDetail", objectMapper.writeValueAsString(orderBillList));
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }


            jsonObjectList.add(jsonObject);


            //物业
            JSONObject wyJsonObject = new JSONObject();
            wyJsonObject.put("clauseType", "2");
            //租赁保证金
            JSONObject wyJsonBondClause = new JSONObject();
            wyJsonBondClause.put("bondType", "bond_2");
            wyJsonBondClause.put("bondPrice", contractImportVo.getWyBondMoney());
            try {
                wyJsonObject.put("bondClause", objectMapper.writeValueAsString(wyJsonBondClause));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


            //租金含税
            JSONObject wyJsonTaxClause = new JSONObject();
            wyJsonTaxClause.put("taxRate", contractImportVo.getWyTaxInclusiveRules());
            wyJsonTaxClause.put("taxRule", contractImportVo.getWyTaxInclusiveValue());
            try {
                wyJsonObject.put("taxClause", objectMapper.writeValueAsString(wyJsonTaxClause));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            //租赁条款
            JSONObject wyJsonMultipleClause = new JSONObject();
            wyJsonMultipleClause.put("startLeaseDate", contractImportVo.getLeaseStart());
            wyJsonMultipleClause.put("endLeaseDate", contractImportVo.getLeaseStart());
            wyJsonMultipleClause.put("dMoney", contractImportVo.getWyMoney());
            wyJsonMultipleClause.put("contractUnitPrice", contractImportVo.getWyContractUnitPrice());
            wyJsonMultipleClause.put("payDay", contractImportVo.getWyPayDay());
            wyJsonMultipleClause.put("day", contractImportVo.getWyDay());
            try {
                wyJsonObject.put("multipleClause", objectMapper.writeValueAsString(wyJsonMultipleClause));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //租赁备注条款
            wyJsonObject.put("remarkClause", contractImportVo.getWyRemarkClause());

            //账单明细

//                List<ContractOrderBillSaveReqVO> wyOrderBillList = null;
//                try {
//                    wyOrderBillList = contractOrderBillService.getOrderBillList(
//                            1,
//                            "2",
//                            sim.format(contractImportVo.getLeaseStart()),
//                            sim.format(contractImportVo.getLeaseStart()),
//                            contractImportVo.getWyDay(),
//                            contractImportVo.getWyBondMoney(),
//                            Integer.valueOf(contractImportVo.getWyPayDay()),
//                            contractImportVo.getUnitPricePoint(),
//                            contractImportVo.getCalculationOrder(),
//                            contractImportVo.getWyTaxInclusiveRules(),
//                            new BigDecimal(contractImportVo.getWyTaxInclusiveValue()),
//                            new BigDecimal(contractImportVo.getTotalArea()),
//                            contractImportVo.getWyContractUnitPrice(),
//                            new BigDecimal(contractImportVo.getWyMoney()),
//                            contractImportVo.getIsReceivableInteger()
//                    );
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//
//                try {
//                    wyJsonObject.put("reportDetail", objectMapper.writeValueAsString(wyOrderBillList));
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }


            jsonObjectList.add(wyJsonObject);


            try {
                contractSaveVO.setClauseTypes(objectMapper.writeValueAsString(jsonObjectList));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            contractSaveVO.setContractAnnex("");
            contractSaveVO.setDataSource("1");
            this.create(contractSaveVO);
        });

    }

    public Long getRoomNameId(Long villageId, Long buildId, String roomName) {
        LambdaQueryWrapperX<RoomDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(RoomDO::getVillageId, villageId);
        queryWrapper.eq(RoomDO::getBuildId, buildId);
        queryWrapper.eq(RoomDO::getRoomName, roomName);
        List<RoomDO> roomDOS = roomMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(roomDOS)) {
            return roomDOS.get(0).getId();
        }
        return null;
    }

    @Override
    public List<ContractTopVO> getContractTop5(ContractTopVO contractTopVO) {
        LambdaQueryWrapperX<ContractDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eqIfPresent(ContractDO::getBuildId, contractTopVO.getBuildId())
                .eqIfPresent(ContractDO::getParkId, contractTopVO.getParkId())
                .eq(ContractDO::getStatus, "1")
                .orderByDesc(ContractDO::getLeaseArea)
                .last("LIMIT 5");
        List<ContractDO> contractDOS = Mapper.selectList(wrapperX);
        List<ContractTopVO> arrayList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(contractDOS)) {
            contractDOS.forEach(contractDO -> {
                ContractTopVO contractTop = new ContractTopVO();
                OwnerDO ownerDO = getByOwnerIdInfo(contractDO.getOwnerId());
                if (ownerDO != null) {
                    contractTop.setIsPersonal(ownerDO.getIsPersonal());
                    contractTop.setOwnerName(ownerDO.getName());
                    contractTop.setOwnerId(contractDO.getOwnerId());
                    contractTop.setBuildId(contractDO.getBuildId());
                    contractTop.setLeaseArea(contractDO.getLeaseArea());
                }
                arrayList.add(contractTop);
            });
        }
        return arrayList;
    }

    @Override
    public List<ContractTopVO> getContractTop5Price(ContractTopVO contractTopVO) {
        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eqIfPresent(ContractDO::getBuildId, contractTopVO.getBuildId())
                .eqIfPresent(ContractDO::getParkId, contractTopVO.getParkId())
                .eq(ContractDO::getStatus, "1")
                .orderByDesc(ContractDO::getLeaseUnitPrice)
                .last("LIMIT 5");
        List<ContractDO> contractDOS = Mapper.selectList(queryWrapperX);
        List<ContractTopVO> arrayList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(contractDOS)) {
            contractDOS.forEach(contractDO -> {
                ContractTopVO contractTop = new ContractTopVO();
                contractTop.setOwnerId(contractDO.getOwnerId());
                OwnerDO ownerDO = getByOwnerIdInfo(contractDO.getOwnerId());
                if (ownerDO != null) {
                    contractTop.setIsPersonal(ownerDO.getIsPersonal());
                    contractTop.setOwnerName(ownerDO.getName());
                }
                contractTop.setLeaseArea(contractDO.getLeaseArea());
                contractTop.setLeaseUnitPrice(contractDO.getLeaseUnitPrice());
                contractTop.setBuildId(contractDO.getBuildId());
                if (contractDO.getContractStartTime() != null && contractDO.getContractEndTime() != null) {
                    contractTop.setLeaseDay(DateUtil.betweenDay(contractDO.getContractStartTime(), contractDO.getContractEndTime(), true));
                }
                contractTop.setName(contractDO.getBuildName() + "-" + contractDO.getRoomNumber());
                arrayList.add(contractTop);
            });
        }
        return arrayList;
    }


    public BigDecimal getByContractIdLateFee(Long contractId) {
        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractOrderBillDO::getContractId, contractId);
        queryWrapperX.eq(ContractOrderBillDO::getBillStatus, "2");
        List<ContractOrderBillDO> contractOrderBillDOS = orderBillMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(contractOrderBillDOS)) {
            return new BigDecimal(contractOrderBillDOS.stream().mapToDouble(ContractOrderBillDO -> Double.parseDouble(ContractOrderBillDO.getLateFee())).sum());
        }
        return new BigDecimal("0.00");
    }

    public Integer getByContractIdStatusCount(Long contractId) {
        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractOrderBillDO::getContractId, contractId);
        queryWrapperX.eq(ContractOrderBillDO::getBillStatus, "0");
        List<ContractOrderBillDO> contractOrderBillDOS = orderBillMapper.selectList(queryWrapperX);

        return contractOrderBillDOS.size();
    }

    @Override
    public Boolean rentTermination(Long contractId) {
        ContractDO contractDO = Mapper.selectById(contractId);

        BigDecimal lateFee = getByContractIdLateFee(contractId);
        if (lateFee.compareTo(BigDecimal.ZERO) > 0) {
            //存在滞纳金
            throw new ServiceException(406, "该租客的账单有滞纳金未结清，为防止滞纳金漏缴，请在退租前结算滞纳金");
        }
        LambdaQueryWrapperX<ContractTemplateDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.apply("REGEXP_LIKE(relation_builds, '(^|,)" + contractDO.getBuildId() + "($|,)')");
        int size = contractTemplateMapper.selectList(queryWrapperX).size();
        if (size <= 0) {
            //未存在模板
            throw new ServiceException(406, "当前楼宇下未维护退租协议,请维护");
        }
        return true;
    }

    /**
     * @param contractId
     * @return
     */
    @Override
    public void isCheckLateFee(Long contractId) {

        BigDecimal lateFee = getByContractIdLateFee(contractId);
        if (lateFee.compareTo(BigDecimal.ZERO) > 0) {
            //存在滞纳金
            throw new ServiceException(406, "该租客的账单有滞纳金未结清，为防止滞纳金漏缴，请在退租前结算滞纳金");
        }
    }

    @Override
    public Integer isCheckUpdateRoom(Long contractId) {

        return getByContractIdStatusCount(contractId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toVoid(ContractToVoidVO contractToVoidVO) {

        ContractOperateLogDO contractOperateLogDO = new ContractOperateLogDO();
        contractOperateLogDO.setContractId(contractToVoidVO.getContractId());
        contractOperateLogDO.setOperateType("void");
        contractOperateLogDO.setOperateContent(contractToVoidVO.getContent());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isToVoidBill", contractToVoidVO.getIsToVoidBill());
        jsonObject.put("isCloseFlow", contractToVoidVO.getISCloseFlow());
        String otherRemark = jsonObject.toString();
        contractOperateLogDO.setOtherRemark(otherRemark);
        contractOperateLogMapper.insert(contractOperateLogDO);

        Mapper.updateById(new ContractDO().setId(Long.valueOf(contractToVoidVO.getContractId())).setStatus("7"));
    }

    @Override
    public List<ContractDO> getPendingExecution() {
        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX();
        queryWrapperX.apply("`status`='13'");
        return Mapper.selectList(queryWrapperX);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ContractDO getByRenewalContractIdInfo(Long id) {
        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractDO::getRenewalContractId, id);
        return Mapper.selectOne(queryWrapperX);
    }


    /**
     * @param id
     * @return
     */
    @Override
    public Boolean isCheckContractNumber(String contractNumber, Long id) {
        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractDO::getContractNumber, contractNumber);
        queryWrapperX.apply("id !='" + id + "'");
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<OwnerNameContractVO> getOwnerNameContractList(String ownerName) {
        return Mapper.getOwnerNameContractList(ownerName);
    }
}