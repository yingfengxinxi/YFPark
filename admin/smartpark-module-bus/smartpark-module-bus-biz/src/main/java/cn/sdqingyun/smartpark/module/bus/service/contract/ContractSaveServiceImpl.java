package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClauseSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.AnnexDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractSelectedPropertieDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractAnnexMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractOrderBillMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractSelectedPropertieMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.expenseclause.ExpenseClauseMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.ContractAuditStatusUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.CONTRACT_NUMBER_NOT_EXISTS;

/**
 * @Author lvzy
 * @Date 2024/8/12 15:20
 */
@Service
public class ContractSaveServiceImpl implements ContractSaveService {

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private ContractSelectedPropertieMapper contractSelectedPropertieMapper;

    @Resource
    private ExpenseClauseMapper expenseClauseMapper;

    @Resource
    private ContractOrderBillMapper contractOrderBillMapper;

    @Resource
    private ContractAnnexMapper contractAnnexMapper;

    @Resource
    private OwnerMapper ownerMapper;


    /**
     * @param contractSaveVO 创建信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long create(ContractSaveVO contractSaveVO) {
        // 插入
        ContractDO contractDO = BeanUtils.toBean(contractSaveVO, ContractDO.class);

        LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractDO::getContractNumber, contractDO.getContractNumber());
        int size = contractMapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw exception(CONTRACT_NUMBER_NOT_EXISTS);
        }
        String[] roomNames = contractDO.getRoomNumber().split(",");
        StringBuilder sb = new StringBuilder();
        for (String roomName : roomNames) {

            LambdaQueryWrapperX<RoomDO> queryWrapper1 = new LambdaQueryWrapperX<>();
            queryWrapper1.eq(RoomDO::getBuildId, contractDO.getBuildId());
            queryWrapper1.eq(RoomDO::getRoomName, roomName);
            RoomDO roomDO = roomMapper.selectOne(queryWrapper1);
            if (roomDO != null) {
                sb.append(roomDO.getId()).append(",");
            } else {
                throw new ServiceException(406, "房间号" + roomName + "不存在,请重新选择");
            }
            roomDO.setRoomStatus(20);
            roomMapper.updateById(roomDO);
        }
        String roomNumber = sb.toString();
        if (StringUtils.isNotEmpty(roomNumber)) {
            roomNumber = roomNumber.substring(0, roomNumber.length() - 1);
            contractDO.setRoomNumber(roomNumber);
        }
        contractMapper.insert(contractDO);


        getCheckedBuild(contractDO, null);

        extracted(contractSaveVO, contractDO);

        // 返回
        return contractDO.getId();
    }


    /**
     * @param contractSaveVO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ContractSaveVO contractSaveVO) {

        ContractDO contractOld = contractMapper.selectById(contractSaveVO.getId());
        // 更新
        ContractDO updateObj = BeanUtils.toBean(contractSaveVO, ContractDO.class);

        contractMapper.updateById(updateObj);

        getCheckedBuild(updateObj, contractOld);

        extracted(contractSaveVO, updateObj);
    }


    /**
     * 处理已选房源
     *
     * @param contractDO
     */
    private void getCheckedBuild(ContractDO contractDO, ContractDO contractOld) {
        //查询已经新增的房间进行释放
        LambdaQueryWrapperX<ContractSelectedPropertieDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ContractSelectedPropertieDO::getContractId, contractDO.getId());
        List<ContractSelectedPropertieDO> contractSelectedPropertieDOS = contractSelectedPropertieMapper.selectList(queryWrapperX);
        for (ContractSelectedPropertieDO contractSelectedPropertieDO : contractSelectedPropertieDOS) {
            //修改房租状态
            RoomDO roomDO = roomMapper.selectById(contractSelectedPropertieDO.getVillageRoomId());
            roomDO.setRoomStatus(10);
            roomMapper.updateById(roomDO);
        }
        updateOwnerInfo(contractDO, contractOld);


        //已选中房源添加数据
        contractSelectedPropertieMapper.deleteByContractId(contractDO.getId());
        List<ContractSelectedPropertieSaveReqVO> contractSelectedPropertieList = JSONObject.parseArray(contractDO.getCheckedBuild(), ContractSelectedPropertieSaveReqVO.class);
        BigDecimal leaseArea = new BigDecimal("0.00");
        for (ContractSelectedPropertieSaveReqVO contractSelectedPropertieSaveReqVO : contractSelectedPropertieList) {
            ContractSelectedPropertieSaveReqVO selectedPropertieSaveReqVO = BeanUtils.toBean(contractSelectedPropertieSaveReqVO, ContractSelectedPropertieSaveReqVO.class);
            selectedPropertieSaveReqVO.setVillageRoomId(contractSelectedPropertieSaveReqVO.getId());
            selectedPropertieSaveReqVO.setContractId(contractDO.getId());
            selectedPropertieSaveReqVO.setTenantId(contractDO.getTenantId());
            selectedPropertieSaveReqVO.setId(null);
            selectedPropertieSaveReqVO.setCreateTime(LocalDateTime.now());
            ContractSelectedPropertieDO selectedPropertie = BeanUtils.toBean(selectedPropertieSaveReqVO, ContractSelectedPropertieDO.class);
            contractSelectedPropertieMapper.insert(selectedPropertie);
            leaseArea = leaseArea.add(selectedPropertieSaveReqVO.getRentalArea());
            //修改房租状态
            RoomDO roomDO = roomMapper.selectById(selectedPropertieSaveReqVO.getVillageRoomId());
            ContractDO contractDO1 = contractMapper.selectById(contractDO.getId());
            if (contractDO1.getStatus().equals("1")) {
                roomDO.setRoomStatus(30);
            } else {
                roomDO.setRoomStatus(20);
            }
            roomMapper.updateById(roomDO);

        }
        contractDO.setLeaseArea(leaseArea.toString());
        contractMapper.updateById(contractDO);
    }

    /**
     * @param contractDO
     * @param contractOld
     */
    private void updateOwnerInfo(ContractDO contractDO, ContractDO contractOld) {
        if (contractOld != null) {
            if (contractOld.getOwnerId() == contractDO.getOwnerId()) {
                OwnerDO ownerDO = new OwnerDO();
                ownerDO.setId(contractDO.getOwnerId());
                //减掉
                ownerDO.setBuildBind(ContractAuditStatusUtils.subtractBuildBinder(ownerDO.getBuildBind(), contractOld.getCheckedBuild()));
                //增加
                ownerDO.setBuildBind(ContractAuditStatusUtils.convertBuildBingder(ownerDO.getBuildBind(), contractDO.getCheckedBuild()));
                //减掉
                ownerDO.setVillageIdList(ContractAuditStatusUtils.subtractVillageId(ownerDO.getVillageIdList(), contractOld.getParkId()));
                //增加
                ownerDO.setVillageIdList(ContractAuditStatusUtils.convertVillageList(ownerDO.getVillageIdList(), contractDO.getParkId()));
                ownerMapper.updateById(ownerDO);
            } else {
                //减掉
                OwnerDO ownerOld = ownerMapper.selectById(contractOld.getOwnerId());
                ownerOld.setBuildBind(ContractAuditStatusUtils.subtractBuildBinder(ownerOld.getBuildBind(), contractOld.getCheckedBuild()));
                ownerOld.setVillageIdList(ContractAuditStatusUtils.subtractVillageId(ownerOld.getVillageIdList(), contractOld.getParkId()));
                ownerMapper.updateById(ownerOld);
                //增加
                OwnerDO ownerNew = ownerMapper.selectById(contractDO.getOwnerId());
                ownerNew.setBuildBind(ContractAuditStatusUtils.convertBuildBingder(ownerNew.getBuildBind(), contractDO.getCheckedBuild()));
                ownerNew.setVillageIdList(ContractAuditStatusUtils.convertVillageList(ownerNew.getVillageIdList(), contractDO.getParkId()));
                ownerMapper.updateById(ownerNew);
            }
        }
    }

    private void extracted(ContractSaveVO contractSaveVO, ContractDO contractDO) {
        //删除条款
        expenseClauseMapper.deleteByContractId(contractDO.getId());
        //账单明细
        contractOrderBillMapper.deleteByContractId(contractDO.getId());

        String clauseTypes = contractSaveVO.getClauseTypes();
        List<ClauseTypesVO> clauseTypesVos = JSONObject.parseArray(clauseTypes, ClauseTypesVO.class);
        for (ClauseTypesVO clauseTypesVo : clauseTypesVos) {
            System.out.println("clauseTypesVo>>>" + clauseTypesVo);
            ExpenseClauseSaveReqVO expenseClauseSaveReqVO = new ExpenseClauseSaveReqVO();
            String orgId = String.valueOf(contractDO.getOrgId()).replaceAll("null", "");
            if (StringUtils.isNotEmpty(orgId)) {
                expenseClauseSaveReqVO.setOrgId(Integer.valueOf(orgId));
            }
            if (StringUtils.isNotEmpty(String.valueOf(contractDO.getId()))) {
                expenseClauseSaveReqVO.setContractId(Integer.valueOf(String.valueOf(contractDO.getId())));
            }

            expenseClauseSaveReqVO.setClauseType(clauseTypesVo.getClauseType());
            // String checkedBuild = clauseTypesVo.getCheckedBuild();
            expenseClauseSaveReqVO.setBondClause(clauseTypesVo.getBondClause());
            expenseClauseSaveReqVO.setTaxClause(clauseTypesVo.getTaxClause());
            expenseClauseSaveReqVO.setMultipleClause(clauseTypesVo.getMultipleClause());
            expenseClauseSaveReqVO.setRemarkClause(clauseTypesVo.getRemarkClause());
            String reportDetail = clauseTypesVo.getReportDetail();
            expenseClauseSaveReqVO.setReportDetail(reportDetail);
            expenseClauseSaveReqVO.setTenantId(contractDO.getTenantId());
            expenseClauseSaveReqVO.setDeleted(Boolean.FALSE);
            expenseClauseSaveReqVO.setCreateTime(LocalDateTime.now());
            // 插入
            ExpenseClauseDO expenseClause = BeanUtils.toBean(expenseClauseSaveReqVO, ExpenseClauseDO.class);
            expenseClauseMapper.insert(expenseClause);


            List<ContractOrderBillSaveReqVO> contractOrderBillList = JSONObject.parseArray(reportDetail, ContractOrderBillSaveReqVO.class);
            for (ContractOrderBillSaveReqVO contractOrderBillSaveReqVO : contractOrderBillList) {
                contractOrderBillSaveReqVO.setContractId(contractDO.getId());
                contractOrderBillSaveReqVO.setTenantId(contractDO.getTenantId());
                contractOrderBillSaveReqVO.setBillType("1");
                contractOrderBillSaveReqVO.setDataSource("0");
                contractOrderBillSaveReqVO.setStartingLateFeeDay(contractSaveVO.getStartingLateFeeDay());
                contractOrderBillSaveReqVO.setLateFeeRatio(contractSaveVO.getLateFeeRatio());
                contractOrderBillSaveReqVO.setUpperLimitLateFee(contractSaveVO.getUpperLimitLateFee());
                contractOrderBillSaveReqVO.setClauseType(String.valueOf(expenseClauseSaveReqVO.getClauseType()));
                //contractOrderBillSaveReqVO.setTaxRate();
                contractOrderBillSaveReqVO.setBillSource("1");
                contractOrderBillSaveReqVO.setOrderNumber(UuidUtils.generateUuid().replaceAll("-", ""));
                if (StringUtils.isEmpty(contractOrderBillSaveReqVO.getBillStatus())) {
                    contractOrderBillSaveReqVO.setBillStatus("0");
                }
                // 插入
                ContractOrderBillDO orderBill = BeanUtils.toBean(contractOrderBillSaveReqVO, ContractOrderBillDO.class);
                System.out.println("orderBill>>" + orderBill.getOrderNumber() + ">>>" + orderBill);
                contractOrderBillMapper.insert(orderBill);
            }
        }


        String contractAnnex = contractSaveVO.getContractAnnex();
        contractAnnexMapper.deleteByContractId(contractDO.getId());
        if (StringUtils.isNotEmpty(contractAnnex.replaceAll("\\[]", ""))) {
            List<AnnexSaveReqVO> annexList = JSONObject.parseArray(contractAnnex, AnnexSaveReqVO.class);
            if (CollectionUtils.isNotEmpty(annexList)) {
                for (AnnexSaveReqVO annexSaveReqVO : annexList) {
                    annexSaveReqVO.setContractId(contractDO.getId());
                    annexSaveReqVO.setCreateTime(LocalDateTime.now());
                    annexSaveReqVO.setDeleted(false);
                    // 插入
                    AnnexDO annex = BeanUtils.toBean(annexSaveReqVO, AnnexDO.class);
                    contractAnnexMapper.insert(annex);
                }
            }
        }

    }
}
