package cn.sdqingyun.smartpark.module.bus.service.park;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarsDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarsOperatorLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkCarsMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkCarsOperatorLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.park.ParkMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PARK_CARS_NOT_EXISTS;

/**
 * 月租车白名单 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class ParkCarsServiceImpl implements ParkCarsService {

    @Resource
    private ParkCarsMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private ParkMapper parkMapper;

    @Resource
    private ParkCarsOperatorLogMapper parkCarsOperatorLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ParkCarsSaveReqVO createReqVO) {

        String userId = getUserId(createReqVO);
        createReqVO.setUserId(Long.valueOf(userId));
        // 插入
        ParkCarsDO parkCarsDO = BeanUtils.toBean(createReqVO, ParkCarsDO.class);
        Mapper.insert(parkCarsDO);

        ParkCarsOperatorLogDO parkCarsOperatorLogDO = new ParkCarsOperatorLogDO();
        parkCarsOperatorLogDO.setType("0");
        parkCarsOperatorLogDO.setCarsId(parkCarsDO.getId());
        parkCarsOperatorLogDO.setBefore("");
        JSONObject jsonObject = getJsonObject(parkCarsDO);
        parkCarsOperatorLogDO.setAfter(jsonObject.toString());
        parkCarsOperatorLogMapper.insert(parkCarsOperatorLogDO);
        // 返回
        return parkCarsDO.getId();
    }

    @NotNull
    private static JSONObject getJsonObject(ParkCarsDO parkCarsDO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", parkCarsDO.getId());
        jsonObject.put("villageId", parkCarsDO.getVillageId());
        jsonObject.put("parkId", parkCarsDO.getParkId());
        jsonObject.put("userId", parkCarsDO.getUserId());
        jsonObject.put("userName", parkCarsDO.getUserName());
        jsonObject.put("userPhone", parkCarsDO.getUserPhone());
        jsonObject.put("carNumber", parkCarsDO.getCarNumber());
        jsonObject.put("carType", parkCarsDO.getCarType());
        jsonObject.put("chargeType", parkCarsDO.getChargeType());
        jsonObject.put("balance", parkCarsDO.getBalance());
        jsonObject.put("carRemark", parkCarsDO.getCarRemark());
        jsonObject.put("parkSpace", parkCarsDO.getParkSpace());
        jsonObject.put("carData", parkCarsDO.getCarData());
        jsonObject.put("addFrom", parkCarsDO.getAddFrom());
        jsonObject.put("enable", parkCarsDO.getEnable());
        jsonObject.put("needAlarm", parkCarsDO.getNeedAlarm());
        jsonObject.put("enableTime", parkCarsDO.getEnableTime());
        jsonObject.put("overdueTime", parkCarsDO.getOverdueTime());
        jsonObject.put("isPush", parkCarsDO.getIsPush());
        jsonObject.put("pushResult", parkCarsDO.getPushResult());
        jsonObject.put("feeChargeId", parkCarsDO.getFeeChargeId());
        jsonObject.put("spaceNum", parkCarsDO.getSpaceNum());
        jsonObject.put("parentId", parkCarsDO.getParentId());
        jsonObject.put("subCarNum", parkCarsDO.getSubCarNum());
        jsonObject.put("vehicleBrand", parkCarsDO.getVehicleBrand());
        jsonObject.put("vehicleEquipmentNumber", parkCarsDO.getVehicleEquipmentNumber());
        jsonObject.put("drivingLicenseNumber", parkCarsDO.getDrivingLicenseNumber());
        jsonObject.put("vehicleColor", parkCarsDO.getVehicleColor());
        return jsonObject;
    }

    private String getUserId(ParkCarsSaveReqVO createReqVO) {
        String userId = systemUserMapper.getByMobileUserId(createReqVO.getUserPhone());
        if (StringUtils.isEmpty(userId)) {
            AdminUserRespDTO adminUserRespDTO = new AdminUserRespDTO();
            adminUserRespDTO.setUsername(createReqVO.getUserPhone());
            adminUserRespDTO.setMobile(createReqVO.getUserPhone());
            adminUserRespDTO.setPassword(createReqVO.getUserPhone());
            adminUserRespDTO.setNickname(createReqVO.getUserName());
            adminUserRespDTO.setStatus(0);
            CommonResult<Long> longCommonResult = adminUserApi.create(adminUserRespDTO);
            if (longCommonResult.isError()) {
                throw new ServiceException(406, "创建用户失败," + longCommonResult.getMsg());
            }
            //注册用户
            userId = String.valueOf(longCommonResult.getData());
        }
        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ParkCarsSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());

        String userId = getUserId(updateReqVO);
        updateReqVO.setUserId(Long.valueOf(userId));
        // 更新
        ParkCarsDO updateObj = BeanUtils.toBean(updateReqVO, ParkCarsDO.class);
        ParkCarsDO parkCarsDO = Mapper.selectById(updateObj.getId());
        Mapper.updateById(updateObj);

        ParkCarsOperatorLogDO parkCarsOperatorLogDO = new ParkCarsOperatorLogDO();
        parkCarsOperatorLogDO.setType("1");
        parkCarsOperatorLogDO.setCarsId(updateObj.getId());
        JSONObject jsonObject = getJsonObject(parkCarsDO);
        parkCarsOperatorLogDO.setBefore(jsonObject.toString());
        JSONObject updateJsonObj = getJsonObject(updateObj);
        parkCarsOperatorLogDO.setAfter(updateJsonObj.toString());
        parkCarsOperatorLogMapper.insert(parkCarsOperatorLogDO);
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
            throw exception(PARK_CARS_NOT_EXISTS);
        }
    }

    @Override
    public ParkCarsDO get(Long id) {
        ParkCarsDO parkCarsDO = Mapper.selectById(id);
        if (parkCarsDO != null) {
            Long villageId = parkCarsDO.getVillageId();
            VillageDO villageDO = villageMapper.selectById(villageId);
            if (villageDO != null) {
                parkCarsDO.setVillageName(villageDO.getName());
            }
            ParkDO parkDO = parkMapper.selectById(parkCarsDO.getParkId());
            if (parkDO != null) {
                parkCarsDO.setParkName(parkDO.getParkName());
            }

        }
        return parkCarsDO;
    }

    @Override
    public PageResult<ParkCarsDO> getPage(ParkCarsPageReqVO pageReqVO) {
        LambdaQueryWrapperX<ParkCarsDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getCarNumber())) {
            queryWrapperX.like(ParkCarsDO::getCarNumber, pageReqVO.getCarNumber());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getUserName())) {
            queryWrapperX.like(ParkCarsDO::getUserName, pageReqVO.getUserName());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getUserPhone())) {
            queryWrapperX.like(ParkCarsDO::getUserPhone, pageReqVO.getUserPhone());
        }
        PageResult<ParkCarsDO> parkCarsDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<ParkCarsDO> list = parkCarsDOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(parkCarsDO -> {
                Long villageId = parkCarsDO.getVillageId();
                VillageDO villageDO = villageMapper.selectById(villageId);
                if (villageDO != null) {
                    String name = villageDO.getName();
                    parkCarsDO.setVillageName(name);
                }
            });
        }
        return parkCarsDOPageResult;
    }

    @Override
    public Boolean isCheckCarNumber(Long villageId, String carNumber, Long id) {

        LambdaQueryWrapperX<ParkCarsDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ParkCarsDO::getVillageId, villageId);
        queryWrapperX.eq(ParkCarsDO::getCarNumber, carNumber);
        if (id != null) {
            queryWrapperX.apply("id !='" + id + "'");
        }

        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            return true;
        }
        return false;
    }

}