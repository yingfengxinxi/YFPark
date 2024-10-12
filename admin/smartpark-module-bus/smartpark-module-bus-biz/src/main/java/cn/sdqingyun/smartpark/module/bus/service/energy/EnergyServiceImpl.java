package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyCategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.LayerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.bus.enums.ProptryStatusEnum;
import cn.sdqingyun.smartpark.module.bus.service.property.PropertyService;
import cn.sdqingyun.smartpark.module.system.api.dict.DictDataApi;
import cn.sdqingyun.smartpark.module.system.api.dict.dto.DictDataRespDTO;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 自定义 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyServiceImpl implements EnergyService {

    @Resource
    private EnergyMapper Mapper;

    @Resource
    private EnergyBindMapper energyBindMapper;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private PropertyService propertyService;

    @Resource
    private PropertyCategoryMapper propertyCategoryMapper;

    @Resource
    private EnergyTypeMapper energyTypeMapper;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private LayerMapper layerMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private DictDataApi dictDataApi;

    @Resource
    private EnergyRecordMapper energyRecordMapper;

    @Resource
    private PropertyMapper propertyMapper;

    @Resource
    private EnergyCallbackRecordMapper energyCallbackRecordMapper;

    @Resource
    private HydropowerOperateRecordMapper hydropowerOperateRecordMapper;

    @Resource
    private NotifyMessageSendApi notifySendService;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(EnergySaveReqVO createReqVO, Boolean updateSupport) throws Exception {
        isCheck(createReqVO);
        // 插入
        EnergyDO energyDO = BeanUtils.toBean(createReqVO, EnergyDO.class);
        energyDO.setStatus("1");

        List<String> roomIdList = List.of(createReqVO.getRoomIds().split(","));
        StringBuilder sb = new StringBuilder();
        roomIdList.forEach(roomId -> {
            LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.apply("REGEXP_LIKE(room_number, '(^|,)" + roomId + "($|,)')");
            List<String> statusList = Lists.newArrayList();
            statusList.add("2");
            statusList.add("5");
            statusList.add("6");
            statusList.add("8");
            statusList.add("10");
            //queryWrapperX.in(ContractDO::getStatus, statusList);
            List<ContractDO> contractList = contractMapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(contractList)) {
                contractList.forEach(aa -> {
                    sb.append(aa.getId()).append(",");
                });

            }
        });
        String contractIds = sb.toString();
        if (StringUtils.isNotEmpty(contractIds)) {
            contractIds = contractIds.substring(0, contractIds.length() - 1);
        }
        energyDO.setContractIds(contractIds);

        energyDO.setCurrentRemaining(new BigDecimal("0.00"));
        energyDO.setDeviceStatus("0");
        energyDO.setDeviceOff("0");
        energyDO.setRemoteValveControl("0");
        if (StringUtils.isEmpty(energyDO.getPaymentType())) {
            energyDO.setPaymentType("0");
        }
        Mapper.insert(energyDO);

        //保存关联分表
        saveEnergyBind(createReqVO, energyDO);

        if (updateSupport) {
            //添加资产
            saveProperty(energyDO);
        }


        // 返回
        return energyDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EnergySaveReqVO updateReqVO, Boolean updateSupport) throws Exception {
        isCheck(updateReqVO);
        // 更新
        EnergyDO updateObj = BeanUtils.toBean(updateReqVO, EnergyDO.class);

        List<String> roomIdList = List.of(updateObj.getRoomIds().split(","));
        StringBuilder sb = new StringBuilder();
        roomIdList.forEach(roomId -> {
            LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.apply("REGEXP_LIKE(room_number, '(^|,)" + roomId + "($|,)')");
            List<String> statusList = Lists.newArrayList();
            statusList.add("2");
            statusList.add("5");
            statusList.add("6");
            statusList.add("8");
            statusList.add("10");
            //queryWrapperX.in(ContractDO::getStatus, statusList);
            List<ContractDO> contractList = contractMapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(contractList)) {
                contractList.forEach(aa -> {
                    sb.append(aa.getId()).append(",");
                });

            }
        });
        String contractIds = sb.toString();
        if (StringUtils.isNotEmpty(contractIds)) {
            contractIds = contractIds.substring(0, contractIds.length() - 1);
        }
        updateObj.setContractIds(contractIds);

        Mapper.updateById(updateObj);

        //保存关联分表
        saveEnergyBind(updateReqVO, updateObj);

        if (updateSupport) {
            //添加资产
            saveProperty(updateObj);
        }
    }

    @Override
    public List<EnergyImportExcelVO> importTemplate() {
        List<EnergyTypeDO> energyTypeList = energyTypeMapper.selectList();
        if (CollectionUtils.isEmpty(energyTypeList)) {
            throw new ServiceException(406, "表类型不存在，请添加表类型后下载模板!");
        }
        StringBuilder sb = new StringBuilder();
        energyTypeList.forEach(energyTypeDO -> {
            sb.append(energyTypeDO.getName()).append("/");
        });
        String typeName = sb.toString();
        if (StringUtils.isNotEmpty(typeName)) {
            typeName = typeName.substring(0, typeName.length() - 1);
        }

        StringBuilder sb1 = new StringBuilder();
        List<String> purpodeNameList = dictDataApi.getDictDataLabelList("PURPODE");
        purpodeNameList.forEach(purpodeName -> {
            sb1.append(purpodeName).append("/");
        });
        String purpodeName = sb1.toString();
        if (StringUtils.isNotEmpty(purpodeName)) {
            purpodeName = purpodeName.substring(0, purpodeName.length() - 1);
        }

        StringBuilder sb2 = new StringBuilder();
        List<String> publicTypeNameList = dictDataApi.getDictDataLabelList("PUBLIC_TYPE");
        publicTypeNameList.forEach(publicType -> {
            sb2.append(publicType).append("/");
        });
        String publicTypeName = sb2.toString();
        if (StringUtils.isNotEmpty(publicTypeName)) {
            publicTypeName = publicTypeName.substring(0, publicTypeName.length() - 1);
        }

        StringBuilder sb3 = new StringBuilder();
        List<String> deviceTypeNameList = dictDataApi.getDictDataLabelList("BAILING_ORG_CONFIG_DEVICE_TYPE");
        deviceTypeNameList.forEach(deviceType -> {
            sb3.append(deviceType).append("/");
        });
        String deviceTypeName = sb3.toString();
        if (StringUtils.isNotEmpty(deviceTypeName)) {
            deviceTypeName = deviceTypeName.substring(0, deviceTypeName.length() - 1);
        }

        StringBuilder sb4 = new StringBuilder();
        List<String> paymentTypeNameList = dictDataApi.getDictDataLabelList("ENERGY_PAYMENT_TYPE");
        paymentTypeNameList.forEach(paymentType -> {
            sb4.append(paymentType).append("/");
        });
        String paymentTypeName = sb4.toString();
        if (StringUtils.isNotEmpty(paymentTypeName)) {
            paymentTypeName = paymentTypeName.substring(0, paymentTypeName.length() - 1);
        }


        // 手动创建导出 demo
        return Arrays.asList(
                EnergyImportExcelVO.builder()
                        .villageName("项目名称（必填）")
                        .buildName("楼宇名称（必填）")
                        .roomName("房号名称（必填），例如:202,203,304")
                        .type(typeName)
                        .name("表名称（必填）同类型不可重复")
                        .number("表具编号（必填）同类型不可重复")
                        .magnification("整数（必填）")
                        .maxReading("表的最大可读数（必填）")
                        .meterTime("抄录时间（必填）格式yyyy-mm-dd，例:2024-01-01")
                        .originalReading("填入读数数字")
                        .purpose(purpodeName + "（必填）")
                        .publicType(publicTypeName + "（用途是【总表/公摊表】时必填)")
                        .deviceType(deviceTypeName)
                        .deviceSerial("智能设备序列号")
                        .paymentType(paymentTypeName + "（默认后付费）")
                        .importResult("非填项，如上传失败请下载反馈文档，该列会予以问题说明").build()
        );
    }

    /**
     * @param list
     * @param updateSupport
     * @param response
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importExcel(List<EnergyImportExcelVO> list, Boolean updateSupport, HttpServletResponse response) {
        list.remove(0);
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        list.forEach(energyImportExcelVO -> {
            StringBuilder builder = new StringBuilder();
            if (StringUtils.isEmpty(energyImportExcelVO.getVillageName())) {
                flag.set(false);
                builder.append("项目名称不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            LambdaQueryWrapperX<VillageDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(VillageDO::getName, energyImportExcelVO.getVillageName());
            VillageDO villageDO = villageMapper.selectOne(queryWrapperX);
            if (villageDO == null) {
                flag.set(false);
                builder.append("项目名称输入不正确，请核对后重新导入/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(energyImportExcelVO.getBuildName())) {
                flag.set(false);
                builder.append("楼宇名称不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            LambdaQueryWrapperX<BuildDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(BuildDO::getBuildName, energyImportExcelVO.getBuildName());
            BuildDO buildDO = buildMapper.selectOne(queryWrapperX1);
            if (buildDO == null) {
                flag.set(false);
                builder.append("楼宇名称输入不正确，请核对后重新导入/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(energyImportExcelVO.getRoomName())) {
                flag.set(false);
                builder.append("房间名称不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            for (String roomName : energyImportExcelVO.getRoomName().split(",")) {
                LambdaQueryWrapperX<RoomDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                queryWrapperX2.eq(RoomDO::getVillageId, villageDO.getId());
                queryWrapperX2.eq(RoomDO::getBuildId, buildDO.getId());
                queryWrapperX2.eq(RoomDO::getRoomName, roomName);
                RoomDO roomDO = roomMapper.selectOne(queryWrapperX2);
                if (roomDO == null) {
                    flag.set(false);
                    builder.append("房间号" + roomName + "不存在项目" + energyImportExcelVO.getVillageName() + "中,请核对后重新导入/");
                    throw new ServiceException(406, builder.toString());
                }
            }


            if (StringUtils.isEmpty(energyImportExcelVO.getType())) {
                flag.set(false);
                builder.append("表种类不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            LambdaQueryWrapperX<EnergyTypeDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
            queryWrapperX3.eq(EnergyTypeDO::getName, energyImportExcelVO.getType());
            EnergyTypeDO energyTypeDO = energyTypeMapper.selectOne(queryWrapperX3);
            if (energyTypeDO == null) {
                flag.set(false);
                builder.append("表种类输入错误,请核对后重新导入/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(energyImportExcelVO.getName())) {
                flag.set(false);
                builder.append("表名称不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            if (StringUtils.isEmpty(energyImportExcelVO.getNumber())) {
                flag.set(false);
                builder.append("表具编号不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            EnergySaveReqVO energySaveReqVO = new EnergySaveReqVO();
            energySaveReqVO.setType(energyTypeDO.getId());
            energySaveReqVO.setName(energyImportExcelVO.getName());
            energySaveReqVO.setNumber(energyImportExcelVO.getNumber());
            energySaveReqVO.setBuildId(buildDO.getId());

            try {
                isCheck(energySaveReqVO);
            } catch (Exception e) {
                flag.set(false);
                builder.append(e.getMessage() + "/");
                throw new ServiceException(406, builder.toString());
            }


            if (StringUtils.isEmpty(energyImportExcelVO.getMagnification())) {
                flag.set(false);
                builder.append("倍率不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(energyImportExcelVO.getMaxReading())) {
                flag.set(false);
                builder.append("最大读数不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(energyImportExcelVO.getMeterTime())) {
                flag.set(false);
                builder.append("抄录时间不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            if (!energyImportExcelVO.getMeterTime().contains("-")) {
                flag.set(false);
                builder.append("抄录时间格式不正确/");
                throw new ServiceException(406, builder.toString());
            }

            if (StringUtils.isEmpty(energyImportExcelVO.getOriginalReading())) {
                flag.set(false);
                builder.append("原始读数不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            if (StringUtils.isEmpty(energyImportExcelVO.getPurpose())) {
                flag.set(false);
                builder.append("用途不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            List<String> purpodeNameList = DictFrameworkUtils.getDictDataLabelList("PURPODE");
            if (!purpodeNameList.contains(energyImportExcelVO.getPurpose())) {
                flag.set(false);
                builder.append("用途输入错误,请核对后重新导入/");
                throw new ServiceException(406, builder.toString());
            }

            if (!StringUtils.equals(energyImportExcelVO.getPurpose(), "1")) {
                flag.set(false);
                builder.append("公摊方式不能为空/");
                throw new ServiceException(406, builder.toString());
            } else {
                //2-总表 3-公摊表
                if (StringUtils.equals(energyImportExcelVO.getPurpose(), "2")) {
                    List<String> publicTypeList = DictFrameworkUtils.getDictDataLabelList("PUBLIC_TYPE");
                    if (!publicTypeList.contains(energyImportExcelVO.getPublicType())) {
                        flag.set(false);
                        builder.append("总表-公摊方式输入错误,请核对后重新导入/");
                        throw new ServiceException(406, builder.toString());
                    }
                }

                if (StringUtils.equals(energyImportExcelVO.getPurpose(), "3")) {
                    List<String> publicTypeList = DictFrameworkUtils.getDictDataLabelList("PUBLIC_TYPE1");
                    if (!publicTypeList.contains(energyImportExcelVO.getPublicType())) {
                        flag.set(false);
                        builder.append("公摊表-公摊方式输入错误,请核对后重新导入/");
                        throw new ServiceException(406, builder.toString());
                    }
                }

            }

            if (StringUtils.isNotEmpty(energyImportExcelVO.getDeviceType())) {
                List<String> deviceTypeNameList = DictFrameworkUtils.getDictDataLabelList("BAILING_ORG_CONFIG_DEVICE_TYPE");
                if (!deviceTypeNameList.contains(energyImportExcelVO.getDeviceType())) {
                    flag.set(false);
                    builder.append("智能设备类型输入错误,请核对后重新导入/");
                    throw new ServiceException(406, builder.toString());
                }
            }

            if (StringUtils.isNotEmpty(energyImportExcelVO.getPaymentType())) {
                List<String> paymentTypeNameList = DictFrameworkUtils.getDictDataLabelList("ENERGY_PAYMENT_TYPE");
                if (!paymentTypeNameList.contains(energyImportExcelVO.getPaymentType())) {
                    flag.set(false);
                    builder.append("付费类型输入错误,请核对后重新导入/");
                    throw new ServiceException(406, builder.toString());
                }
            }


            energyImportExcelVO.setImportResult(String.valueOf(builder));
        });
        if (flag.get()) {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            list.forEach(energyImportExcelVO -> {
                EnergySaveReqVO energySaveReqVO = new EnergySaveReqVO();


                LambdaQueryWrapperX<EnergyTypeDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
                queryWrapperX3.eq(EnergyTypeDO::getName, energyImportExcelVO.getType());
                EnergyTypeDO energyTypeDO = energyTypeMapper.selectOne(queryWrapperX3);
                energySaveReqVO.setType(energyTypeDO.getId());

                LambdaQueryWrapperX<VillageDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(VillageDO::getName, energyImportExcelVO.getVillageName());
                VillageDO villageDO = villageMapper.selectOne(queryWrapperX);
                energySaveReqVO.setVillageId(villageDO.getId());

                LambdaQueryWrapperX<BuildDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(BuildDO::getBuildName, energyImportExcelVO.getBuildName());
                BuildDO buildDO = buildMapper.selectOne(queryWrapperX1);
                energySaveReqVO.setBuildId(buildDO.getId());
                StringBuilder sb = new StringBuilder();
                List<String> layerIdList = Lists.newArrayList();
                List<JSONObject> builds = Lists.newArrayList();
                for (String roomName : energyImportExcelVO.getRoomName().split(",")) {
                    LambdaQueryWrapperX<RoomDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                    queryWrapperX2.eq(RoomDO::getVillageId, villageDO.getId());
                    queryWrapperX2.eq(RoomDO::getBuildId, buildDO.getId());
                    queryWrapperX2.eq(RoomDO::getRoomName, roomName);
                    RoomDO roomDO = roomMapper.selectOne(queryWrapperX2);
                    sb.append(roomDO.getId()).append(",");
                    layerIdList.add(String.valueOf(roomDO.getLayerId()));
                    String layerName = layerMapper.selectById(roomDO.getLayerId()).getLayerName();

                    //拼凑数据
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", roomDO.getId());
                    jsonObject.put("roomNumber", roomDO.getRoomNumber());
                    jsonObject.put("roomName", roomDO.getRoomName());
                    jsonObject.put("roomAliasId", roomDO.getRoomAliasId());
                    jsonObject.put("parentRoomId", roomDO.getParentRoomId());
                    jsonObject.put("subRoomCount", roomDO.getSubRoomCount());
                    jsonObject.put("subRoomRentCount", roomDO.getSubRoomRentCount());
                    jsonObject.put("buildArea", roomDO.getBuildArea());
                    jsonObject.put("insideArea", roomDO.getInsideArea());
                    jsonObject.put("rentalArea", roomDO.getRentalArea());
                    jsonObject.put("rentalAreaIn", roomDO.getRentalAreaIn());
                    jsonObject.put("chargingArea", roomDO.getChargingArea());
                    jsonObject.put("chargingAreaIn", roomDO.getChargingAreaIn());
                    jsonObject.put("leaseStart", roomDO.getLeaseStart());
                    jsonObject.put("leaseEnd", roomDO.getLeaseEnd());
                    jsonObject.put("deliverTime", roomDO.getDeliverTime());
                    jsonObject.put("invitationStatus", roomDO.getInvitationStatus());
                    jsonObject.put("investmentPolicy", roomDO.getInvestmentPolicy());
                    jsonObject.put("images", roomDO.getImages());
                    jsonObject.put("priceUnit", roomDO.getPriceUnit());
                    jsonObject.put("priceUnitMin", roomDO.getPriceUnitMin());
                    jsonObject.put("preUnitPrice", roomDO.getPreUnitPrice());
                    jsonObject.put("preUnitPriceMin", roomDO.getPreUnitPriceMin());
                    jsonObject.put("tagIdArr", roomDO.getTagIdArr());
                    jsonObject.put("contractInfo", roomDO.getContractInfo());
                    jsonObject.put("contractCount", roomDO.getContractCount());
                    jsonObject.put("decoration", roomDO.getDecoration());
                    jsonObject.put("propertyRight", roomDO.getPropertyRight());
                    jsonObject.put("floorHeight", roomDO.getFloorHeight());
                    jsonObject.put("loadMax", roomDO.getLoadMax());
                    jsonObject.put("layerId", roomDO.getLayerId());
                    jsonObject.put("unitId", roomDO.getUnitId());
                    jsonObject.put("buildId", roomDO.getBuildId());
                    jsonObject.put("zoneId", roomDO.getZoneId());
                    jsonObject.put("villageId", roomDO.getVillageId());
                    jsonObject.put("sort", roomDO.getSort());
                    jsonObject.put("status", roomDO.getStatus());
                    jsonObject.put("threeDimensionalId", roomDO.getThreeDimensionalId());
                    jsonObject.put("roomStatus", roomDO.getRoomStatus());
                    jsonObject.put("houseType", roomDO.getHouseType());
                    jsonObject.put("recordNo", roomDO.getRecordNo());
                    jsonObject.put("promoterMoney", roomDO.getPromoterMoney());
                    jsonObject.put("promoterMoneyUnit", roomDO.getPromoterMoneyUnit());
                    jsonObject.put("extraConfig", roomDO.getExtraConfig());
                    jsonObject.put("vrLink", roomDO.getVrLink());
                    jsonObject.put("video", roomDO.getVideo());
                    jsonObject.put("vrVideoSort", roomDO.getVrVideoSort());
                    jsonObject.put("monthHits", roomDO.getMonthHits());
                    jsonObject.put("splitParentArea", roomDO.getSplitParentArea());
                    jsonObject.put("isLock", roomDO.getIsLock());
                    jsonObject.put("isUnreal", roomDO.getIsUnreal());
                    jsonObject.put("extraLock", roomDO.getExtraLock());
                    jsonObject.put("createTime", roomDO.getCreateTime());
                    jsonObject.put("layerName", layerName);
                    jsonObject.put("buildName", energyImportExcelVO.getBuildName());
                    jsonObject.put("villageName", energyImportExcelVO.getVillageName());
                    jsonObject.put("tagHouseList", "");
                    jsonObject.put("showStatus", roomDO.getStatus());
                    jsonObject.put("ownerName", "");
                    jsonObject.put("name", roomDO.getRoomName());
                    jsonObject.put("level", "3");
                    jsonObject.put("level_key", "3-" + roomDO.getId());
                    jsonObject.put("level1_key", "1-" + roomDO.getVillageId());
                    jsonObject.put("level2_key", "2-" + roomDO.getBuildId());

                    builds.add(jsonObject);
                }
                String roomIds = sb.toString();
                if (StringUtils.isNotEmpty(roomIds)) {
                    roomIds = roomIds.substring(0, roomIds.length() - 1);
                }
                energySaveReqVO.setRoomIds(roomIds);
                StringBuilder sb1 = new StringBuilder();
                layerIdList.forEach(layerId -> {
                    sb1.append(layerId).append(",");
                });
                String layerIds = sb1.toString();
                if (StringUtils.isNotEmpty(layerIds)) {
                    layerIds = layerIds.substring(0, layerIds.length() - 1);
                }

                energySaveReqVO.setLayerIds(layerIds);
                energySaveReqVO.setBuilds(new Gson().toJson(builds));

                CommonResult<DictDataRespDTO> purpode = dictDataApi.parseDictData("PURPODE", energyImportExcelVO.getPurpose());
                energySaveReqVO.setPurpose(purpode.getData().getValue());

                energySaveReqVO.setName(energyImportExcelVO.getName());

                energySaveReqVO.setNumber(energyImportExcelVO.getNumber());

                energySaveReqVO.setMagnification(new BigDecimal(energyImportExcelVO.getMagnification()));

                energySaveReqVO.setOriginalReading(new BigDecimal(energyImportExcelVO.getOriginalReading()));

                energySaveReqVO.setMaxReading(new BigDecimal(energyImportExcelVO.getMaxReading()));

                try {
                    Date meterTime = sim.parse(energyImportExcelVO.getMeterTime());
                    energySaveReqVO.setMeterTime(meterTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new ServiceException(406, e.getMessage());
                }
                if (!StringUtils.equals(energyImportExcelVO.getPurpose(), "1")) {
                    CommonResult<DictDataRespDTO> publicType = dictDataApi.parseDictData("PUBLIC_TYPE", energyImportExcelVO.getPublicType());
                    energySaveReqVO.setPublicType(publicType.getData().getValue());
                }
                if (StringUtils.isNotEmpty(energyImportExcelVO.getDeviceType())) {
                    CommonResult<DictDataRespDTO> deviceType = dictDataApi.parseDictData("BAILING_ORG_CONFIG_DEVICE_TYPE", energyImportExcelVO.getDeviceType());
                    energySaveReqVO.setDeviceType(deviceType.getData().getValue());
                    energySaveReqVO.setDeviceId("1");
                    energySaveReqVO.setDeviceSerial(energyImportExcelVO.getDeviceSerial());
                }

                if (StringUtils.isEmpty(energyImportExcelVO.getPaymentType())) {
                    energySaveReqVO.setPaymentType("0");
                }
                try {
                    create(energySaveReqVO, updateSupport);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException(406, "水电分表导入错误" + e.getMessage());
                }
            });
            return true;
        } else {
            List<EnergyImportExcelVO> energyImportExcelList = importTemplate();
            energyImportExcelList.addAll(list);
            // 输出
            try {
                ExcelUtils.write(response, "水电分表导入错误.xls", "水电分表列表", EnergyImportExcelVO.class, energyImportExcelList);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException(406, "水电分表导入错误" + e.getMessage());
            }
            return false;
        }

    }

    /**
     * @param energyDO
     */
    private void saveProperty(EnergyDO energyDO) {
        if (StringUtils.isNotEmpty(energyDO.getDeviceType())) {
            PropertyPageReqVO pageReqVO = new PropertyPageReqVO();
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
            pageReqVO.setDeviceCode(energyDO.getDeviceSerial());
            List<PropertyDO> list = propertyService.getPropertyPage(pageReqVO).getList();
            if (CollectionUtils.isEmpty(list)) {
                EnergyTypeDO energyTypeDO = energyTypeMapper.selectById(energyDO.getType());
                LambdaQueryWrapperX<PropertyCategoryDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(PropertyCategoryDO::getName, energyTypeDO.getName());
                List<PropertyCategoryDO> propertyCategoryDOS = propertyCategoryMapper.selectList(queryWrapperX);
                Long propertyCategoryId = null;
                if (CollectionUtils.isNotEmpty(propertyCategoryDOS)) {
                    propertyCategoryId = propertyCategoryDOS.get(0).getId();
                } else {
                    PropertyCategoryDO propertyCategoryDO = new PropertyCategoryDO();
                    propertyCategoryDO.setName(energyTypeDO.getName());
                    propertyCategoryDO.setNumber(UuidUtils.generateUuid().replaceAll("-", ""));
                    propertyCategoryDO.setSort(0);
                    propertyCategoryDO.setStatus(1);
                    propertyCategoryMapper.insert(propertyCategoryDO);
                    propertyCategoryId = propertyCategoryDO.getId();
                }
                PropertySaveReqVO propertySaveReqVO = new PropertySaveReqVO();
                propertySaveReqVO.setType(propertyCategoryId);
                propertySaveReqVO.setName(energyTypeDO.getName());
                String dataLabel = DictFrameworkUtils.getDictDataLabel("BAILING_ORG_CONFIG", energyDO.getDeviceId());
                propertySaveReqVO.setBrand(dataLabel);

                String modelName = DictFrameworkUtils.getDictDataLabel("BAILING_ORG_CONFIG_DEVICE_TYPE", energyDO.getDeviceType());
                propertySaveReqVO.setModelName(modelName);
                propertySaveReqVO.setDeviceCode(energyDO.getDeviceSerial());
                Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
                propertySaveReqVO.setAdminId(loginUserId);
                propertySaveReqVO.setVillageId(energyDO.getVillageId());
                propertySaveReqVO.setBuildId(energyDO.getBuildId());
                propertySaveReqVO.setRoomId(energyDO.getRoomIds());
                propertySaveReqVO.setPositionId(0L);
                propertyService.createProperty(propertySaveReqVO);
                energyDO.setPropertyId(propertySaveReqVO.getId());
            } else {
                energyDO.setPropertyId(list.get(0).getId());
            }
            //更新资产id
            Mapper.updateById(energyDO);
        }
    }

    /**
     * @param createReqVO
     * @param energyDO
     */
    private void saveEnergyBind(EnergySaveReqVO createReqVO, EnergyDO energyDO) {
        if (!createReqVO.getPurpose().equals("1")) {
            if (StringUtils.isNotEmpty(createReqVO.getPartIds())) {
                List<String> partIds = List.of(createReqVO.getPartIds().split(","));
                partIds.forEach(energyId -> {
                    EnergyBindDO energyBindDO = new EnergyBindDO();
                    EnergyDO energyOld = Mapper.selectById(energyId);
                    if (energyOld.getPurpose().equals("1")) {
                        energyBindDO.setType("1");
                    } else {
                        energyBindDO.setType("2");
                    }

                    energyBindDO.setEnergyId(Long.valueOf(energyId));
                    energyBindDO.setParentEnergyId(energyDO.getId());

                    energyBindDO.setMeterType(energyDO.getType());
                    energyBindDO.setPurposeType(energyDO.getPurpose());
                    energyBindDO.setVillageId(energyOld.getVillageId());
                    energyBindDO.setBuildId(energyOld.getBuildId());
                    energyBindDO.setName(energyOld.getName());
                    energyBindDO.setStatus("1");
                    energyBindMapper.insert(energyBindDO);
                });
            }

        }
    }

    private void isCheck(EnergySaveReqVO energySaveReqVO) throws Exception {

        LambdaQueryWrapperX<EnergyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyDO::getType, energySaveReqVO.getType());
        queryWrapperX.apply("REGEXP_LIKE(build_id, '(^|,)" + energySaveReqVO.getBuildId() + "($|,)') and status !='2'");
        queryWrapperX.eq(EnergyDO::getName, energySaveReqVO.getName());
        if (energySaveReqVO.getId() != null) {
            queryWrapperX.apply("id !='" + energySaveReqVO.getId() + "'");
        }
        Long count = Mapper.selectCount(queryWrapperX);
        if (count >= 1) {
            throw new ServiceException(406, "名称已存在");
        }


        LambdaQueryWrapperX<EnergyDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(EnergyDO::getType, energySaveReqVO.getType());
        queryWrapperX.apply("REGEXP_LIKE(build_id, '(^|,)" + energySaveReqVO.getBuildId() + "($|,)') and status !='2'");
        queryWrapperX1.eq(EnergyDO::getNumber, energySaveReqVO.getNumber());
        if (energySaveReqVO.getId() != null) {
            queryWrapperX1.apply("id !='" + energySaveReqVO.getId() + "'");
        }
        Long count1 = Mapper.selectCount(queryWrapperX1);
        if (count1 >= 1) {
            throw new ServiceException(406, "表具编号已存在");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception {
        // 查询是否有抄表记录, 有抄表记录无法删除
        LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyRecordDO::getEnergyId, id);
        Long count = energyRecordMapper.selectCount(queryWrapperX);
        if (count >= 1) {
            throw new ServiceException(406, "当前表中存在抄表记录,无法删除操作!");
        }
        // 删除
        Mapper.deleteById(id);

        EnergyDO energyDO = Mapper.selectById(id);
        LambdaQueryWrapperX<EnergyBindDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        if (energyDO.getPurpose().equals("1")) {
            //分表
            queryWrapperX1.eq(EnergyBindDO::getEnergyId, energyDO.getId());
        } else {
            queryWrapperX1.eq(EnergyBindDO::getParentEnergyId, energyDO.getId());

        }
        energyBindMapper.delete(queryWrapperX1);

        //解绑设备
        unbindDevice(energyDO);
    }


    @Override
    public EnergyRespVO get(Long id) {
        EnergyDO energyDO = Mapper.selectById(id);
        EnergyRespVO energyRespVO = BeanUtils.toBean(energyDO, EnergyRespVO.class);
        EnergyTypeDO energyTypeDO = energyTypeMapper.selectById(energyRespVO.getType());
        energyRespVO.setTypeName("--");
        if (energyTypeDO != null) {
            energyRespVO.setTypeName(energyTypeDO.getName());
        }

        String purpose = DictFrameworkUtils.getDictDataLabel("PURPODE", energyRespVO.getPurpose());
        energyRespVO.setPurpose(purpose);
        energyRespVO.setDeviceName("--");
        if (energyRespVO.getPropertyId() != null && energyRespVO.getPropertyId() > 0) {
            PropertyDO propertyDO = propertyMapper.selectById(energyRespVO.getPropertyId());
            energyRespVO.setDeviceName(propertyDO.getName());
        }

        String statusName = DictFrameworkUtils.getDictDataLabel("ENERGY_STATUS", energyRespVO.getStatus());
        energyRespVO.setStatusName(statusName);

        energyRespVO.setBindRoomCount(energyRespVO.getRoomIds().split(",").length);

        AtomicReference<Integer> rentRoomCount = new AtomicReference<>(0);
        List<String> contractIds = List.of(energyRespVO.getContractIds().split(","));
        if (CollectionUtils.isNotEmpty(contractIds)) {
            LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.in(ContractDO::getId, contractIds);
            List<ContractDO> contractList = contractMapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(contractList)) {
                List<String> roomIdList = contractList.stream().map(contractDO -> contractDO.getRoomNumber()).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(roomIdList)) {
                    roomIdList.forEach(roomIds -> {
                        String[] split = roomIds.split(",");
                        rentRoomCount.set(rentRoomCount.get() + split.length);
                    });
                }
            }
        }

        energyRespVO.setRentRoomCount(rentRoomCount.get());
        if (StringUtils.isNotEmpty(energyRespVO.getPublicType())) {
            energyRespVO.setPublicType(DictFrameworkUtils.getDictDataLabel("PUBLIC_TYPE", energyRespVO.getPublicType()));
        }


        VillageDO villageDO = villageMapper.selectById(energyRespVO.getVillageId());
        energyRespVO.setVillageName(villageDO.getName());

        BuildDO buildDO = buildMapper.selectById(energyRespVO.getBuildId());
        energyRespVO.setBuildName(buildDO.getBuildName());
        String roomName = getRoomName(energyRespVO.getRoomIds());
        energyRespVO.setRoomName(roomName);

        energyRespVO.setPaymentTypeName(DictFrameworkUtils.getDictDataLabel("ENERGY_PAYMENT_TYPE", energyRespVO.getPaymentType()));

        LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyRecordDO::getEnergyId, id);
        Long count = energyRecordMapper.selectCount(queryWrapperX);
        energyRespVO.setIsRecord(false);
        if (count >= 1) {
            energyRespVO.setIsRecord(true);
        }
        LambdaQueryWrapperX<EnergyBindDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(EnergyBindDO::getParentEnergyId, energyRespVO.getId());
        List<EnergyBindDO> energyBindList = energyBindMapper.selectList(queryWrapperX1);
        if (CollectionUtils.isNotEmpty(energyBindList)) {
            List<Long> energyBindEnergyIdList = energyBindList.stream().map(energyBindDO -> energyBindDO.getEnergyId()).collect(Collectors.toList());
            energyRespVO.setEnergyBindEnergyIdList(energyBindEnergyIdList);
        }
        return energyRespVO;
    }

    @Override
    public PageResult<EnergyRespVO> smartEnergyList(EnergyPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyDO> queryWrapperX = getEnergyDOLambdaQueryWrapperX(pageReqVO);
        PageResult<EnergyDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<EnergyRespVO> respVOPageResult = BeanUtils.toBean(pageResult, EnergyRespVO.class);
        List<EnergyRespVO> list = respVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(energyRespVO -> {
                String purposeName = DictFrameworkUtils.getDictDataLabel("PURPODE", energyRespVO.getPurpose());
                energyRespVO.setPurposeName(purposeName);

                VillageDO villageDO = villageMapper.selectById(energyRespVO.getVillageId());
                if (villageDO != null) {
                    energyRespVO.setBindingLocation(villageDO.getName());
                }

                BuildDO buildDO = buildMapper.selectById(energyRespVO.getBuildId());
                if (buildDO != null) {
                    energyRespVO.setBindingLocation(energyRespVO.getBindingLocation() + "/" + buildDO.getBuildName());
                }

                String roomName = getRoomName(energyRespVO.getRoomIds());
                if (StringUtils.isNotEmpty(roomName)) {
                    energyRespVO.setBindingLocation(energyRespVO.getBindingLocation() + "/" + roomName);
                }

                String paymentTypeName = DictFrameworkUtils.getDictDataLabel("ENERGY_PAYMENT_TYPE", energyRespVO.getPaymentType());
                energyRespVO.setPaymentTypeName(paymentTypeName);

                String statusName = DictFrameworkUtils.getDictDataLabel("ENERGY_STATUS", energyRespVO.getStatus());
                energyRespVO.setStatusName(statusName);

                //当前读数
                LambdaQueryWrapperX<EnergyCallbackRecordDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(EnergyCallbackRecordDO::getEquipCode, energyRespVO.getDeviceId());
                queryWrapperX1.eq(EnergyCallbackRecordDO::getAbnormalStatus, "0");
                queryWrapperX1.eq(EnergyCallbackRecordDO::getIsCompleted, "1");
                queryWrapperX1.apply("reading>0");
                queryWrapperX1.orderByDesc(EnergyCallbackRecordDO::getSaveTime);
                List<EnergyCallbackRecordDO> energyCallbackRecordList = energyCallbackRecordMapper.selectList(queryWrapperX1);
                if (CollectionUtils.isNotEmpty(energyCallbackRecordList)) {
                    EnergyCallbackRecordDO energyCallbackRecordDO = energyCallbackRecordList.get(0);
                    if (energyCallbackRecordDO != null) {
                        energyRespVO.setOriginalReading(energyCallbackRecordDO.getReading());
                    }
                }

                //通信状态
                if (
                        energyRespVO.getDeviceId() != null &&
                                energyRespVO.getLastHeartTime() != null &&
                                DateUtils.isOutTime(energyRespVO.getLastHeartTime())
                ) {
                    if (CollectionUtils.isNotEmpty(energyCallbackRecordList)) {
                        EnergyCallbackRecordDO energyCallbackRecordDO = energyCallbackRecordList.get(0);
                        if (energyCallbackRecordDO != null) {
                            DateUtils.isOutTime(energyCallbackRecordDO.getSaveTime());
                            EnergyDO energyDO = BeanUtils.toBean(energyRespVO, EnergyDO.class);
                            energyDO.setDeviceOff("1");
                            Mapper.updateById(energyDO);
                            energyRespVO.setDeviceOff("1");
                        }

                    }

                }

                String deviceOffName = DictFrameworkUtils.getDictDataLabel("DEVICE_OFF", energyRespVO.getDeviceOff());
                energyRespVO.setDeviceOffName(deviceOffName);


                String deviceStatusName = DictFrameworkUtils.getDictDataLabel("DEVICE_STATUS", energyRespVO.getDeviceStatus());
                energyRespVO.setDeviceStatusName(deviceStatusName);

            });
        }
        return respVOPageResult;
    }

    @Override
    public PageResult<EnergyRespVO> getPage(EnergyPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(EnergyDO::getVillageId, pageReqVO.getVillageId());
        }
        if (pageReqVO.getBuildId() != null) {
            queryWrapperX.eq(EnergyDO::getBuildId, pageReqVO.getBuildId());
        }
        if (pageReqVO.getType() != null) {
            queryWrapperX.eq(EnergyDO::getType, pageReqVO.getType());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getPurpose())) {
            queryWrapperX.eq(EnergyDO::getPurpose, pageReqVO.getPurpose());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(EnergyDO::getName, pageReqVO.getName());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getNumber())) {
            queryWrapperX.like(EnergyDO::getNumber, pageReqVO.getNumber());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getStatus())) {
            queryWrapperX.in(EnergyDO::getStatus, Arrays.asList(pageReqVO.getStatus().split(",")));
        }
        queryWrapperX.orderByDesc(EnergyDO::getCreateTime);
        PageResult<EnergyDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<EnergyRespVO> respVOPageResult = BeanUtils.toBean(pageResult, EnergyRespVO.class);
        List<EnergyRespVO> records = respVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(energyRespVO -> {

                //用途
                String purpose = energyRespVO.getPurpose();
                String dataLabel = DictFrameworkUtils.getDictDataLabel("PURPODE", purpose);
                energyRespVO.setPurpose(dataLabel);


                VillageDO villageDO = villageMapper.selectById(energyRespVO.getVillageId());
                if (villageDO != null) {
                    energyRespVO.setBindingLocation(villageDO.getName());
                }

                BuildDO buildDO = buildMapper.selectById(energyRespVO.getBuildId());
                if (buildDO != null) {
                    energyRespVO.setBindingLocation(energyRespVO.getBindingLocation() + "/" + buildDO.getBuildName());
                }

                String roomName = getRoomName(energyRespVO.getRoomIds());
                energyRespVO.setBindingLocation(energyRespVO.getBindingLocation() + "/" + roomName);

                String paymentType = energyRespVO.getPaymentType();
                String paymentTypeName = DictFrameworkUtils.getDictDataLabel("ENERGY_PAYMENT_TYPE", paymentType);
                energyRespVO.setPaymentTypeName(paymentTypeName);

                LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(EnergyRecordDO::getEnergyId, energyRespVO.getId());
                queryWrapperX1.orderByDesc(EnergyRecordDO::getCreateTime);
                List<EnergyRecordDO> energyRecordDOList = energyRecordMapper.selectList(queryWrapperX1);
                if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                    EnergyRecordDO energyRecordDO = energyRecordDOList.get(0);
                    Long leadUid = energyRecordDO.getLeadUid();
                    String userName = systemUserMapper.getByOperatorIdUserName(leadUid);
                    energyRespVO.setLeadUid(leadUid);
                    energyRespVO.setLeadName(userName);
                    energyRespVO.setThisTime(energyRecordDO.getThisTime());
                    energyRespVO.setThisNumber(energyRecordDO.getThisNumber());
                    energyRespVO.setLastNumber(energyRecordDO.getLastNumber());
                    energyRespVO.setThisUse(energyRecordDO.getThisUse());
                }
                energyRespVO.setPropertyName("未绑定");
                PropertyDO propertyDO = propertyMapper.selectById(energyRespVO.getPropertyId());
                if (propertyDO != null) {
                    energyRespVO.setPropertyName(propertyDO.getName());
                }

                String status = energyRespVO.getStatus();
                String statusName = DictFrameworkUtils.getDictDataLabel("ENERGY_STATUS", status);
                energyRespVO.setStatusName(statusName);

            });
        }
        return respVOPageResult;
    }

    @NotNull
    private static LambdaQueryWrapperX<EnergyDO> getEnergyDOLambdaQueryWrapperX(EnergyPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(EnergyDO::getVillageId, pageReqVO.getVillageId());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getDeviceSerial())) {
            queryWrapperX.apply("device_serial !='' and device_serial is not null");
        }


        if (pageReqVO.getBuildId() != null) {
            queryWrapperX.eq(EnergyDO::getBuildId, pageReqVO.getBuildId());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getLayerIds())) {
            queryWrapperX.apply("REGEXP_LIKE(layer_ids, '(^|,)" + pageReqVO.getLayerIds() + "($|,)')");
        }
        if (StringUtils.isNotEmpty(pageReqVO.getRoomIds())) {
            queryWrapperX.apply("REGEXP_LIKE(room_ids, '(^|,)" + pageReqVO.getRoomIds() + "($|,)')");
        }


        if (pageReqVO.getType() != null) {
            queryWrapperX.eq(EnergyDO::getType, pageReqVO.getType());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getPurpose())) {
            queryWrapperX.eq(EnergyDO::getPurpose, pageReqVO.getPurpose());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(EnergyDO::getName, pageReqVO.getName());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getNumber())) {
            queryWrapperX.like(EnergyDO::getNumber, pageReqVO.getNumber());
        }


        if (StringUtils.isNotEmpty(pageReqVO.getStatus())) {
            queryWrapperX.eq(EnergyDO::getStatus, pageReqVO.getStatus());
        }
        queryWrapperX.orderByDesc(EnergyDO::getMeterTime);
        return queryWrapperX;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long id) throws Exception {
        // 查询是否有抄表记录, 有抄表记录才能作废
        LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyRecordDO::getEnergyId, id);
        Long count = energyRecordMapper.selectCount(queryWrapperX);
        if (count <= 0) {
            throw new ServiceException(406, "当前表中不存在抄表记录,无法作废操作!");
        }

        EnergyDO energyDO = Mapper.selectById(id);

        //解绑设备
        unbindDevice(energyDO);


        energyDO.setDeviceType("");
        energyDO.setDeviceSerial("");
        energyDO.setPropertyId(null);
        energyDO.setDeviceId("");
        energyDO.setDeviceOff("0");
        energyDO.setDeviceStatus("0");
        Mapper.updateById(energyDO);
    }

    @Override
    public void close(Long id) {
        EnergyDO energyDO = Mapper.selectById(id);
        EnergyRespVO energyRespVO = BeanUtils.toBean(energyDO, EnergyRespVO.class);
        if (energyRespVO == null) {
            throw new ServiceException(406, "智能设备不存在");
        }

        //EnergyTypeDO energyTypeDO = energyTypeMapper.selectById(energyRespVO.getType());

        HydropowerOperateRecordDO hydropowerOperateRecordDO = new HydropowerOperateRecordDO();
        hydropowerOperateRecordDO.setEnergyId(energyRespVO.getId());
        hydropowerOperateRecordDO.setOperateUid(SecurityFrameworkUtils.getLoginUserId());
        hydropowerOperateRecordDO.setStatus("0");
        hydropowerOperateRecordDO.setReason("手动关闸");
        hydropowerOperateRecordDO.setDeviceType(energyRespVO.getDeviceType());
        hydropowerOperateRecordMapper.insert(hydropowerOperateRecordDO);

        String roomIds = energyRespVO.getRoomIds();
        if (StringUtils.isNotEmpty(roomIds)) {
            List<String> roomIdList = List.of(roomIds.split(","));
            roomIdList.forEach(roomId -> {
                LambdaQueryWrapperX<ContractDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.in(ContractDO::getStatus, Arrays.asList("2,3,5,6,8,10".split(",")));
                queryWrapperX.apply("REGEXP_LIKE(room_number, '(^|,)" + roomId + "($|,)')");
                List<ContractDO> contractList = contractMapper.selectList(queryWrapperX);
                if (CollectionUtils.isNotEmpty(contractList)) {
                    contractList.forEach(contractDO -> {
                        //发送站内信
                        //站内消息

                        //拉闸位置:{address} 拉闸原因:{reason} 拉闸类型:{closeType} 拉闸时间:{closeTime}

                        VillageDO villageDO = villageMapper.selectById(energyRespVO.getVillageId());
                        if (villageDO != null) {
                            energyRespVO.setBindingLocation(villageDO.getName());
                        }

                        BuildDO buildDO = buildMapper.selectById(energyRespVO.getBuildId());
                        if (buildDO != null) {
                            energyRespVO.setBindingLocation(energyRespVO.getBindingLocation() + "/" + buildDO.getBuildName());
                        }

                        String roomName = getRoomName(energyRespVO.getRoomIds());
                        if (StringUtils.isNotEmpty(roomName)) {
                            energyRespVO.setBindingLocation(energyRespVO.getBindingLocation() + "/" + roomName);
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put("address", energyRespVO.getBindingLocation());
                        map.put("reason", hydropowerOperateRecordDO.getReason());
                        map.put("closeType", "手动关闸");
                        SimpleDateFormat sim = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
                        map.put("closeTime", sim.format(new Date()));
                        NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                        notifySendSingleToUserReqDTO.setTemplateCode("ENERGY_CLOSE");
                        notifySendSingleToUserReqDTO.setUserId(contractDO.getOwnerId());
                        notifySendSingleToUserReqDTO.setTemplateParams(map);
                        notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
                    });
                }
            });
        }
    }

    @Override
    public List<EnergyRespVO> getList(EnergyPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(EnergyDO::getVillageId, pageReqVO.getVillageId());
        }

        if (pageReqVO.getBuildId() != null) {
            queryWrapperX.eq(EnergyDO::getBuildId, pageReqVO.getBuildId());
        }

        if (pageReqVO.getType() != null) {
            queryWrapperX.eq(EnergyDO::getType, pageReqVO.getType());
        }

        queryWrapperX.eq(EnergyDO::getPurpose, "1");


        List<EnergyDO> pageResult = Mapper.selectList(queryWrapperX);
        List<EnergyRespVO> list = BeanUtils.toBean(pageResult, EnergyRespVO.class);
        return list;
    }

    private void unbindDevice(EnergyDO energyDO) {
        Long propertyId = energyDO.getPropertyId();
        if (propertyId != null && propertyId > 0) {
            // 资产解绑
            // 解绑资产后，在资产应用下变更资产的状态为未用
            PropertyDO propertyDO = new PropertyDO();
            propertyDO.setId(propertyId);
            propertyDO.setStatus(ProptryStatusEnum.STATUS_1.getCode());
            propertyDO.setUserId(null);
            propertyDO.setDepartmentId(null);
            propertyDO.setBuildBind(null);
            propertyDO.setReceiveTime(null);
            propertyMapper.updateCancelById(propertyDO);
        }
    }

    private String getRoomName(String roomIds) {
        if (StringUtils.isNotEmpty(roomIds)) {
            StringBuilder sb = new StringBuilder();
            String[] split = roomIds.split(",");
            for (String roomId : split) {
                RoomDO roomDO = roomMapper.selectById(roomId);
                if (roomDO != null) {
                    String roomName = roomDO.getRoomName();
                    String lc = roomName.substring(0, roomName.length() - 2);
                    sb.append(lc + "-" + roomName).append(",");
                }
            }

            String roomName = sb.toString();
            if (StringUtils.isNotEmpty(roomName)) {
                return roomName.substring(0, roomName.length() - 1);
            }
        }
        return "";
    }
}