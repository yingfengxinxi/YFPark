package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLocationMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.LayerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyConfigDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyConfigMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产配置信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyConfigServiceImpl implements PropertyConfigService {

    @Resource
    private PropertyConfigMapper propertyConfigMapper;
    @Resource
    private PropertyLocationService propertyLocationService;
    @Resource
    private VillageMapper villageMapper;
    @Resource
    private BuildMapper buildMapper;
    @Resource
    private LayerMapper layerMapper;
    @Resource
    private RoomMapper roomMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPropertyConfig(PropertyConfigSaveReqVO createReqVO) {
        String villageIdList = createReqVO.getBuildBind();
        PropertyConfigDO propertyConfig = propertyConfigMapper.selectOne(new LambdaQueryWrapperX<PropertyConfigDO>()
                .eq(PropertyConfigDO::getTenantId, TenantContextHolder.getTenantId()));

        if (propertyConfig != null) {
            // 原有的 buildBind
            String originalBuildBind = propertyConfig.getBuildBind();
            List<String> originalBuildBindList = Arrays.asList(originalBuildBind.split(","));

            // 从入参中去除原有的 buildBind 值
            List<String> newBuildBindList = new ArrayList<>(Arrays.asList(villageIdList.split(",")));
            newBuildBindList.removeAll(originalBuildBindList);

            // 将剩余的值转换回字符串形式，用于后续处理
            villageIdList = String.join(",", newBuildBindList);

            // 如果有新的需要处理的buildBind
            if (!villageIdList.isEmpty()) {
                executeSaveLogic(villageIdList, createReqVO, propertyConfig);
            }

            // 在最后，将原有的值与新值合并后，更新数据库
            Set<String> mergedSet = new LinkedHashSet<>(originalBuildBindList);
            mergedSet.addAll(newBuildBindList);
            String mergedBuildBind = String.join(",", mergedSet);
            createReqVO.setBuildBind(mergedBuildBind);

            PropertyConfigDO updatedConfigDO = BeanUtils.toBean(createReqVO, PropertyConfigDO.class);
            propertyConfigMapper.updateById(updatedConfigDO);
        } else {
            // 如果是新增操作
            executeSaveLogic(villageIdList, createReqVO, null);
            PropertyConfigDO newConfigDO = BeanUtils.toBean(createReqVO, PropertyConfigDO.class);
            propertyConfigMapper.insert(newConfigDO);
        }

        return 0L;
    }

    private void executeSaveLogic(String villageIdList, PropertyConfigSaveReqVO createReqVO, PropertyConfigDO propertyConfig) {
        String numberRule = createReqVO.getNumberRule();
        JSONObject entries = new JSONObject(numberRule);
        String conditionTxt = entries.getStr("condition_txt");
        int conditionNum = entries.getInt("condition_num");
        AtomicInteger conditionStart = new AtomicInteger(entries.getInt("condition_start"));

        String[] buildIds = villageIdList.split(",");

        int sort = 0;
        for (String buildIdStr : buildIds) {
            VillageDO villageDO = villageMapper.selectById(Long.parseLong(buildIdStr));
            if (villageDO != null) {
                Long villageId = villageDO.getId();
                String villageName = villageDO.getName();

                // 生成唯一编号
                String uniqueNumber = generateUniqueNumber(conditionTxt, conditionNum, conditionStart);

                PropertyLocationSaveReqVO locationDO = new PropertyLocationSaveReqVO();
                locationDO.setLevel("0");
                locationDO.setName(villageName);
                locationDO.setOrgId(createReqVO.getOrgId());
                locationDO.setParentId(0L);
                locationDO.setNumber(uniqueNumber);
                locationDO.setSort(sort++);
                locationDO.setStatus(1);
                Long propertyLocation = propertyLocationService.createPropertyLocation(locationDO);

                saveBuildsAndLocations(villageId, propertyLocation, conditionTxt, conditionNum, conditionStart);
            }
        }
    }

    private void saveBuildsAndLocations(Long villageId, Long parentLocationId, String conditionTxt, int conditionNum, AtomicInteger conditionStart) {
        List<BuildDO> buildDOS = buildMapper.selectList(new LambdaQueryWrapperX<BuildDO>()
                .eq(BuildDO::getVillageId, villageId));

        int buildSort = 0;
        for (BuildDO buildDO : buildDOS) {
            Long buildId = buildDO.getId();
            String buildName = buildDO.getBuildName();

            // 生成唯一编号
            String uniqueNumber = generateUniqueNumber(conditionTxt, conditionNum, conditionStart);

            PropertyLocationSaveReqVO locationDO = new PropertyLocationSaveReqVO();
            locationDO.setLevel(parentLocationId.toString());
            locationDO.setName(buildName);
            locationDO.setParentId(parentLocationId);
            locationDO.setNumber(uniqueNumber);
            locationDO.setSort(buildSort++);
            locationDO.setStatus(1);
            Long buildLocationId = propertyLocationService.createPropertyLocation(locationDO);

            saveLayersAndRooms(buildId, buildLocationId, conditionTxt, conditionNum, conditionStart);
        }
    }

    private void saveLayersAndRooms(Long buildId, Long buildLocationId, String conditionTxt, int conditionNum, AtomicInteger conditionStart) {
        List<LayerDO> layerDOS = layerMapper.selectList(new LambdaQueryWrapperX<LayerDO>()
                .eq(LayerDO::getBuildId, buildId));

        int layerSort = 0;
        for (LayerDO layerDO : layerDOS) {
            Long layerId = layerDO.getId();
            String layerName = layerDO.getLayerName();

            // 生成唯一编号
            String uniqueNumber = generateUniqueNumber(conditionTxt, conditionNum, conditionStart);

            PropertyLocationSaveReqVO locationDO = new PropertyLocationSaveReqVO();
            locationDO.setLevel(buildLocationId.toString());
            locationDO.setName(layerName);
            locationDO.setParentId(buildLocationId);
            locationDO.setNumber(uniqueNumber);
            locationDO.setSort(layerSort++);
            locationDO.setStatus(1);
            Long layerLocationId = propertyLocationService.createPropertyLocation(locationDO);

            saveRooms(layerId, layerLocationId, conditionTxt, conditionNum, conditionStart);
        }
    }

    private void saveRooms(Long layerId, Long layerLocationId, String conditionTxt, int conditionNum, AtomicInteger conditionStart) {
        List<RoomDO> roomDOS = roomMapper.selectList(new LambdaQueryWrapperX<RoomDO>()
                .eq(RoomDO::getLayerId, layerId));

        int roomSort = 0;
        for (RoomDO roomDO : roomDOS) {
            String roomName = roomDO.getRoomName();

            // 生成唯一编号
            String uniqueNumber = generateUniqueNumber(conditionTxt, conditionNum, conditionStart);

            PropertyLocationSaveReqVO locationDO = new PropertyLocationSaveReqVO();
            locationDO.setLevel(layerLocationId.toString());
            locationDO.setName(roomName);
            locationDO.setParentId(layerLocationId);
            locationDO.setNumber(uniqueNumber);
            locationDO.setSort(roomSort++);
            locationDO.setStatus(1);
            propertyLocationService.createPropertyLocation(locationDO);
        }
    }

    private String generateUniqueNumber(String conditionTxt, int conditionNum, AtomicInteger conditionStart) {
        String format = "%0" + conditionNum + "d";
        String number = String.format(format, conditionStart.getAndIncrement());

        return conditionTxt + number;
    }

    @Override
    public void updatePropertyConfig(PropertyConfigSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyConfigExists(updateReqVO.getId());
        // 更新
        PropertyConfigDO updateObj = BeanUtils.toBean(updateReqVO, PropertyConfigDO.class);
        propertyConfigMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyConfig(Long id) {
        // 校验存在
        validatePropertyConfigExists(id);
        // 删除
        propertyConfigMapper.deleteById(id);
    }

    private void validatePropertyConfigExists(Long id) {
        if (propertyConfigMapper.selectById(id) == null) {
            throw exception(PROPERTY_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public PropertyConfigDO getPropertyConfig(Long id) {
        return propertyConfigMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyConfigDO> getPropertyConfigPage(PropertyConfigPageReqVO pageReqVO) {
        return propertyConfigMapper.selectPage(pageReqVO);
    }

}