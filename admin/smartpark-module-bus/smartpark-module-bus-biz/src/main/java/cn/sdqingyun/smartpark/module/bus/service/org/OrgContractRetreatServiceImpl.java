package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.util.pdf.WordToPdfUtil;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgContractRetreatDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractTemplateMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillCostTypeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgContractRetreatMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgContractRetreatService;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.deepoove.poi.XWPFTemplate;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.CONTRACT_RETREAT_NOT_EXISTS;

/**
 * 机构合同退租 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgContractRetreatServiceImpl implements OrgContractRetreatService {

    @Resource
    private OrgContractRetreatMapper Mapper;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private ContractTemplateMapper contractTemplateMapper;

    @Resource
    private FileApi fileApi;

    @Resource
    private OwnerMapper ownerMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private ContractOrderBillMapper contractOrderBillMapper;

    @Resource
    private OrgBillCostTypeMapper orgBillCostTypeMapper;

    @Override
    public Long create(OrgContractRetreatSaveReqVO createReqVO) {
        ContractDO contractDO = contractMapper.selectById(createReqVO.getContractId());
        if (contractDO != null) {
            contractDO.setOwnerId(contractDO.getOwnerId());
        }
        // 插入
        OrgContractRetreatDO contractRetreatDO = BeanUtils.toBean(createReqVO, OrgContractRetreatDO.class);
        Mapper.insert(contractRetreatDO);
        // 返回
        return contractRetreatDO.getId();
    }

    @Override
    public void update(OrgContractRetreatSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgContractRetreatDO updateObj = BeanUtils.toBean(updateReqVO, OrgContractRetreatDO.class);
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
            throw exception(CONTRACT_RETREAT_NOT_EXISTS);
        }
    }

    @Override
    public OrgContractRetreatDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgContractRetreatDO> getPage(OrgContractRetreatPageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }

    /**
     * @param contractTemplateId
     * @param contractId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String downloadProtocol(Long contractTemplateId, Long contractId) throws Exception {
        ContractDO contractDO = contractMapper.selectById(contractId);
        if (contractDO == null) {
            throw new ServiceException(406, "当前合同不存在,请核对后重新下载");
        }
        if (contractDO.getStatus().equals("4")) {
            throw new ServiceException(406, "当前合同退租待审核中,不支持下载退租协议,请核对后重新下载");
        }
        LambdaQueryWrapperX<OrgContractRetreatDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(OrgContractRetreatDO::getContractId, contractId);
        OrgContractRetreatDO orgContractRetreatDO = Mapper.selectOne(queryWrapperX1);
        if (orgContractRetreatDO == null) {
            throw new ServiceException(406, "申请退租数据不存在,请联系系统管理");
        }
        orgContractRetreatDO.setApplyTemplateId(contractTemplateId);


        ContractTemplateDO contractTemplateDO = contractTemplateMapper.selectById(contractTemplateId);
        if (contractTemplateDO == null) {
            throw new ServiceException(406, "当前选择的退租合同模板不存在,请核对后重新下载");
        }
        // 生成文件
        //String path = "D:\\";
        String path = "/usr/uploads/contract/";
        File filepath = new File(path);
        if (!filepath.exists()) {//如果文件夹不存在
            filepath.mkdirs();//创建文件夹
        }
        // 获取项目word转pdf文件夹目录
        String filePath = contractTemplateDO.getTemplatePath();
        String uuid = UuidUtils.generateUuid();
        String docPath = filepath + "/" + uuid + ".docx";
        URL url = new URL(filePath); // 替换为你要读取的URL
        // 打开连接
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();


        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
        BuildDO buildDO = buildMapper.selectById(contractDO.getBuildId());
        String[] roomNumbers = contractDO.getRoomNumber().split(",");

        StringBuilder sb = new StringBuilder();
        for (String roomNumber : roomNumbers) {
            RoomDO roomDO = roomMapper.selectById(roomNumber);
            sb.append(roomDO.getRoomName()).append(",");
        }
        String roomName = sb.toString();
        if (StringUtils.isNotEmpty(roomName)) {

            roomName = roomName.substring(0, roomName.length() - 1);
        }

        LambdaQueryWrapperX<ContractOrderBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractOrderBillDO::getContractId, contractId);
        queryWrapperX.in(ContractOrderBillDO::getBillStatus, Arrays.asList("0,2".split(",")));
        List<ContractOrderBillDO> contractOrderBillDOS = contractOrderBillMapper.selectList(queryWrapperX);
        BigDecimal billSettlementAmount = new BigDecimal("0.00");
        BigDecimal depositSettlementAmount = new BigDecimal("0.00");
        List<BigDecimal> billSettlementAmountList = Lists.newArrayList();
        List<BigDecimal> depositSettlementAmountList = Lists.newArrayList();
        List<ContractOrderBillDO> billList = Lists.newArrayList();
        List<ContractOrderBillDO> depositList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(contractOrderBillDOS)) {
            contractOrderBillDOS.forEach(contractOrderBillDO -> {
                //保证金
                BigDecimal amountTotal = new BigDecimal(contractOrderBillDO.getReceivable()).add(new BigDecimal(contractOrderBillDO.getLateFee()));
                OrgBillCostTypeDO orgBillCostTypeDO = orgBillCostTypeMapper.selectById(contractOrderBillDO.getFeeType());
                if (orgBillCostTypeDO.getIsBond().equals("1")) {
                    depositSettlementAmountList.add(amountTotal);
                    depositList.add(contractOrderBillDO);
                } else {
                    amountTotal = amountTotal.subtract(new BigDecimal(contractOrderBillDO.getReceivablePayment()));
                    billSettlementAmountList.add(amountTotal);
                    billList.add(contractOrderBillDO);
                }
            });

            billSettlementAmount = billSettlementAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            depositSettlementAmount = depositSettlementAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        }


        String finalRoomName = roomName;
        BigDecimal finalBillSettlementAmount = billSettlementAmount;
        BigDecimal finalDepositSettlementAmount = depositSettlementAmount;
        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(
                new HashMap<String, Object>() {{
                    //租客信息
                    put("合同编号", contractDO.getContractNumber());
                    put("租赁面积", contractDO.getLeaseArea());
                    put("退租时间", sim.format(contractDO.getLeaseRetreatTime()));
                    put("租客名称", ownerDO.getName());
                    put("结束时间", sim.format(contractDO.getContractStartTime()));
                    put("计租时间", sim.format(contractDO.getContractEndTime()));
                    //房源信息
                    put("楼层房号", finalRoomName);
                    put("楼宇名称", buildDO.getBuildName());

                    //结算金额
                    put("账单结算金额", finalBillSettlementAmount);
                    put("保证金结算金额", finalDepositSettlementAmount);
                    put("总结算金额含保证金", finalBillSettlementAmount.add(finalDepositSettlementAmount));

                    //费用明细
//                    put("保证金明细", depositList);
//                    put("账单明细", billList);
                }});
        template.write(new FileOutputStream(docPath));
        String pdfPath = path + "contract/" + uuid + ".pdf";
        // 将word转换为pdf
        WordToPdfUtil.word2Pdf(docPath, pdfPath);// E:\\test\\test.docx为word文档路径


        FileInputStream is = new FileInputStream(pdfPath);
        String pdfUrl = fileApi.createFile(uuid + ".pdf", pdfPath, is.readAllBytes());
        WordToPdfUtil.deleteFile(docPath);
        is.close();
        inputStream.close();
        orgContractRetreatDO.setApplyContract(pdfUrl);
        Mapper.updateById(orgContractRetreatDO);
        return pdfUrl;
    }

}