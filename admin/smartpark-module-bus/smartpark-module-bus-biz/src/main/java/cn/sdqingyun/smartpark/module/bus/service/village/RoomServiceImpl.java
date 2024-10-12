package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.RoomImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagHouseDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.contract.ContractMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagHouseMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.LayerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import jakarta.annotation.Resource;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.ROOM_NOT_EXISTS;

/**
 * 项目房间 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;
    @Resource
    private VillageMapper villageMapper;
    @Resource
    private BuildMapper buildMapper;
    @Resource
    private LayerMapper layerMapper;
    @Resource
    private TagHouseMapper tagHouseMapper;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private OwnerMapper ownerMapper;

    @Override
    public Long createRoom(RoomSaveReqVO createReqVO) {
        // 插入
        RoomDO room = BeanUtils.toBean(createReqVO, RoomDO.class);
        room.setRoomStatus(10);
        validateRoomNumber(room);
        roomMapper.insert(room);
        // 返回
        return room.getId();
    }

    @Override
    public void updateByIdRoomStatus(Long roomId, Integer roomStatus) {
        RoomDO roomDO = roomMapper.selectById(roomId);
        roomDO.setRoomStatus(roomStatus);
        roomMapper.updateById(roomDO);
    }

    private void validateRoomNumber(RoomDO room) {
        if (room.getRoomNumber() != null) {
            validateRoomAttribute(room, "RoomNumber", "项目房间编号已经存在");
        }

        if (StringUtils.isNotEmpty(room.getRoomAliasId())) {
            validateRoomAttribute(room, "RoomAliasId", "项目房间管理编号已经存在");
        }

        if (StringUtils.isNotEmpty(room.getRoomName())) {
            validateRoomAttribute(room, "RoomName", "项目房间房号已经存在");
        }

    }

    private <T> void validateRoomAttribute(RoomDO createReqVO, String attributeGetter, String errorMessage) {
        LambdaQueryWrapperX<RoomDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eqIfPresent(RoomDO::getVillageId, createReqVO.getVillageId())
                .eqIfPresent(RoomDO::getBuildId, createReqVO.getBuildId())
                .neIfPresent(RoomDO::getId, createReqVO.getId());
        switch (attributeGetter) {
            case "RoomNumber":
                wrapper.eq(RoomDO::getRoomNumber, createReqVO.getRoomNumber());
                break;
            case "RoomAliasId":
                wrapper.eq(RoomDO::getRoomAliasId, createReqVO.getRoomAliasId());
                break;
            case "RoomName":
                wrapper.eq(RoomDO::getRoomName, createReqVO.getRoomName());
                break;
        }
        if (roomMapper.selectCount(wrapper) > 0) {
            throw new ServiceException(406, errorMessage);
        }
    }

    @Override
    public void updateRoom(RoomSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomExists(updateReqVO.getId());
        // 更新
        RoomDO updateObj = BeanUtils.toBean(updateReqVO, RoomDO.class);

        validateRoomNumber(updateObj);
        roomMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoom(Long id) {
        // 校验存在
        validateRoomExists(id);
        // 删除
        roomMapper.deleteById(id);
    }

    private void validateRoomExists(Long id) {
        if (roomMapper.selectById(id) == null) {
            throw exception(ROOM_NOT_EXISTS);
        }
    }

    @Override
    public RoomDO getRoom(Long id) {
        return roomMapper.selectById(id);
    }

    @Override
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
    public PageResult<RoomDO> getRoomPage(RoomPageReqVO pageReqVO) {
        return roomMapper.selectPage(pageReqVO);
    }

    @Override
    public Boolean importExcel(List<RoomImportExcelVO> roomImportExcelVOS) {
        if (CollectionUtils.isEmpty(roomImportExcelVOS)) return false;
        roomImportExcelVOS.remove(0);
        if (CollectionUtils.isEmpty(roomImportExcelVOS)) {
            throw new ServiceException(406, "导入数据为空");
        }
        //校验参数
        for (RoomImportExcelVO roomImportExcelVO : roomImportExcelVOS) {
            try {
                validateAndSaveRoom(roomImportExcelVO);

                String tmpVillageName = roomImportExcelVO.getName().trim();
                String tmpBuildName = roomImportExcelVO.getBuildName().trim();
                String tmpLayerName = roomImportExcelVO.getLayerName().trim();
                String tmpRoomName = roomImportExcelVO.getRoomName().trim();
                int tmpRoomNumber = Integer.parseInt(roomImportExcelVO.getRoomNumber().trim());

                VillageDO villageDO = villageMapper.selectOne(new LambdaQueryWrapperX<VillageDO>()
                        .eq(VillageDO::getName, tmpVillageName));

                if (villageDO == null || villageDO.getId() == null) {
                    throw new Exception(tmpVillageName + "项目不存在，请检查后重新导入");
                }

                BuildDO buildDO = buildMapper.selectOne(new LambdaQueryWrapperX<BuildDO>()
                        .eq(BuildDO::getBuildName, tmpBuildName)
                        .eq(BuildDO::getVillageId, villageDO.getId()));
                if (buildDO == null || buildDO.getId() == null) {
                    throw new Exception(tmpBuildName + "楼宇不存在，请检查后重新导入");
                }

                LayerDO layerDO = layerMapper.selectOne(new LambdaQueryWrapperX<LayerDO>().eq(LayerDO::getLayerName, tmpLayerName).eq(LayerDO::getBuildId, buildDO.getId()));
                if (layerDO == null || layerDO.getId() == null) {
                    throw new Exception(tmpLayerName + "楼层不存在，请检查后重新导入");
                }

                List<RoomDO> roomDOS = roomMapper.selectList(new LambdaQueryWrapperX<RoomDO>().eq(RoomDO::getRoomName, tmpRoomName).eq(RoomDO::getBuildId, buildDO.getId()).eq(RoomDO::getLayerId, layerDO.getId()));
                if (CollectionUtils.isNotEmpty(roomDOS)) {
                    throw new Exception(tmpRoomName + "房间名称已存在，请检查后重新导入");
                }
                List<RoomDO> room = roomMapper.selectList(new LambdaQueryWrapperX<RoomDO>().eq(RoomDO::getRoomNumber, tmpRoomNumber).eq(RoomDO::getBuildId, buildDO.getId()).eq(RoomDO::getLayerId, layerDO.getId()));
                if (CollectionUtils.isNotEmpty(room)) {
                    throw new Exception(tmpRoomNumber + "房间号已存在，请检查后重新导入");
                }


                BigDecimal tmpChargingArea = new BigDecimal(roomImportExcelVO.getChargingArea() == null ? "0" : roomImportExcelVO.getChargingArea().trim());
                BigDecimal tmpBuildArea = new BigDecimal(roomImportExcelVO.getBuildArea() == null ? "0" : roomImportExcelVO.getBuildArea().trim());
                BigDecimal tmpFloorHeight = new BigDecimal(roomImportExcelVO.getFloorHeight() == null ? "0" : roomImportExcelVO.getFloorHeight().trim());
                BigDecimal tmpPreUnitPrice = new BigDecimal(roomImportExcelVO.getPreUnitPrice() == null ? "0" : roomImportExcelVO.getPreUnitPrice().trim());
                BigDecimal tmpPreUnitPriceMin = new BigDecimal(roomImportExcelVO.getPreUnitPriceMin() == null ? "0" : roomImportExcelVO.getPreUnitPriceMin().trim());
                String tmpPriceUnit = roomImportExcelVO.getPriceUnit() == null ? null : roomImportExcelVO.getPriceUnit().trim();
                String tmpPriceUnitMin = roomImportExcelVO.getPriceUnitMin() == null ? null : roomImportExcelVO.getPriceUnitMin().trim();
                String tmpRecordNo = roomImportExcelVO.getRecordNo() == null ? null : roomImportExcelVO.getRecordNo().trim();
                String tmpRoomTag = roomImportExcelVO.getDecoration() == null ? null : roomImportExcelVO.getDecoration().trim();

                RoomDO roomDO = new RoomDO();
                roomDO.setVillageId(villageDO.getId());
                roomDO.setRoomName(tmpRoomName);
                roomDO.setBuildId(buildDO.getId());
                roomDO.setLayerId(layerDO.getId());
                roomDO.setRoomNumber(tmpRoomNumber);
                roomDO.setChargingArea(tmpChargingArea);
                roomDO.setBuildArea(tmpBuildArea);
                roomDO.setFloorHeight(tmpFloorHeight);
                roomDO.setPreUnitPrice(tmpPreUnitPrice);
                roomDO.setPreUnitPriceMin(tmpPreUnitPriceMin);
                roomDO.setPriceUnit(coverPriceUnit(tmpPriceUnit));
                roomDO.setPriceUnitMin(coverPriceUnit(tmpPriceUnitMin));
                roomDO.setRecordNo(tmpRecordNo);
                roomDO.setDecoration(tmpRoomTag);
                roomDO.setStatus(1);
                validateRoomNumber(roomDO);
                roomMapper.insert(roomDO);
            } catch (Exception e) {
                throw new ServiceException(406, e.getMessage());
            }
        }


        return true;
    }

    private String coverPriceUnit(String priceUnit) {
        if (StringUtils.isEmpty(priceUnit)) {
            return "square_month";
        }
        switch (priceUnit) {
            case "元/㎡/月":
                return "square_month";
            case "元/㎡/天":
                return "square_day";
            case "元/月":
                return "month";
            case "元/天":
                return "day";
            case "元/年":
                return "year";
            default:
                return "day";
        }
    }

    private void validateAndSaveRoom(RoomImportExcelVO vo) throws Exception {
        if (StringUtils.isEmpty(vo.getName())) throw new Exception("项目名称必须填写");

        if (StringUtils.isEmpty(vo.getBuildName())) throw new Exception("楼栋名称必须填写");

        if (StringUtils.isEmpty(vo.getLayerName())) throw new Exception("楼层名称必须填写");

        if (StringUtils.isEmpty(vo.getRoomName())) throw new Exception("房间名称必须填写");

        if (StringUtils.isEmpty(vo.getRoomNumber())) throw new Exception("房间编号必须填写");

        if (StringUtils.isEmpty(vo.getRentalArea())) throw new Exception("计租面积必须填写");
        BigDecimal tmpRentalArea = new BigDecimal(vo.getRentalArea() == null ? "0" : vo.getRentalArea().trim());
        if (tmpRentalArea.compareTo(BigDecimal.ZERO) < 0) throw new Exception("计租面积必须大于0");

        if (StringUtils.isEmpty(vo.getPriceUnit())) throw new Exception("价格单位必须填写");
    }

    @Override
    public RoomRespVO getOneRoom(Long id) {
        RoomDO roomDO = roomMapper.selectById(id);
        RoomRespVO roomRespResult = BeanUtils.toBean(roomDO, RoomRespVO.class);
        if (roomRespResult != null) {
            VillageDO villageDO = villageMapper.selectById(roomDO.getVillageId());
            if (villageDO != null) {
                roomRespResult.setVillageName(villageDO.getName());
            }
            BuildDO buildDO = buildMapper.selectById(roomDO.getBuildId());
            if (buildDO != null) {
                roomRespResult.setBuildName(buildDO.getBuildName());
            }
            LayerDO layerDO = layerMapper.selectById(roomDO.getLayerId());
            if (layerDO != null) {
                roomRespResult.setLayerName(layerDO.getLayerName());
            }

            //查询房源标签
            if (StringUtils.isNotEmpty(roomDO.getTagIdArr())) {
                JSONArray jsonArray = JSON.parseArray(roomDO.getTagIdArr());
                List<TagHouseDO> tagHouseDOS = new ArrayList<>();
                for (Object o : jsonArray) {
                    TagHouseDO tagHouseDO = tagHouseMapper.selectById(Long.parseLong(o.toString()));
                    tagHouseDOS.add(tagHouseDO);
                }
                roomRespResult.setTagHouseList(tagHouseDOS);
            }
        }
        return roomRespResult;
    }

    @Override
    public Long getByRoomNameId(Long buildId, String roomNumber) {
        LambdaQueryWrapperX<RoomDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(RoomDO::getBuildId, buildId);
        queryWrapperX.eq(RoomDO::getRoomName, roomNumber);
        RoomDO roomDO = roomMapper.selectOne(queryWrapperX);
        if (roomDO != null) {
            return roomDO.getId();
        }
        return null;
    }

    @Override
    public List<JSONObject> resourceList(RoomPageReqVO pageReqVO) {
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        LambdaQueryWrapperX<RoomDO> queryWrapper = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getRoomName())) {
            queryWrapper.eq(RoomDO::getRoomName, pageReqVO.getRoomName());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getRoomAliasId())) {
            queryWrapper.like(RoomDO::getRoomAliasId, pageReqVO.getRoomAliasId());
        }
        queryWrapper.orderByDesc(RoomDO::getCreateTime);
        List<RoomDO> roomDOS = roomMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(roomDOS)) {
            roomDOS.forEach(roomDO -> {
                LambdaQueryWrapperX<ContractDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.apply("REGEXP_LIKE(room_number, '(^|,)" + roomDO.getId() + "($|,)')");
                List<ContractDO> contractDOS = contractMapper.selectList(queryWrapperX1);
                if (CollectionUtils.isNotEmpty(contractDOS)) {
                    contractDOS.forEach(contractDO -> {
                        JSONObject jsonObject = new JSONObject();
                        String msg = "";
                        VillageDO villageDO = villageMapper.selectById(contractDO.getParkId());
                        if (villageDO != null) {
                            msg = villageDO.getName();
                        }
                        BuildDO buildDO = buildMapper.selectById(contractDO.getBuildId());
                        if (buildDO != null) {
                            msg = msg + "/" + buildDO.getBuildName();
                        }
                        String roomName = roomDO.getRoomName();
                        String lc = roomName.substring(0, roomName.length() - 2); //
                        msg = msg + "/" + lc + "-" + roomName;
                        jsonObject.put("room", msg);
                        OwnerDO ownerDO = ownerMapper.selectById(contractDO.getOwnerId());
                        jsonObject.put("owner", ownerDO);
                        jsonObjectList.add(jsonObject);
                    });
                }
            });

        }
        return jsonObjectList;
    }
}