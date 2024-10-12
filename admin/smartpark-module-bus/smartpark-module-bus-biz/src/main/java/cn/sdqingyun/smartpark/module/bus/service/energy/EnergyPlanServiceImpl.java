package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanEquipmentRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.LayerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.bus.service.patrol.PatrolComputingTime;
import cn.sdqingyun.smartpark.module.bus.service.patrol.PatrolTaskEquipmentLogData;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.PostApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.PostRespDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义抄表计划 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyPlanServiceImpl implements EnergyPlanService {

    @Resource
    private EnergyPlanMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private EnergyTaskMapper energyTaskMapper;

    @Resource
    private EnergyTaskLogMapper energyTaskLogMapper;

    @Resource
    private PostApi postApi;

    @Resource
    private DeptApi deptApi;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private LayerMapper layerMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(EnergyPlanSaveReqVO createReqVO) throws Exception {
        createReqVO.setPlanNumber("SDJH" + UuidUtils.generateUuid().replaceAll("-", ""));
        createReqVO.setPlanStatus("1");
        createReqVO.setStatus("1");

        String planRuleOld = createReqVO.getPlanRule();
        JSONObject planRuleOldJson = JSONObject.parseObject(planRuleOld);
        planRuleOldJson.put("key", UuidUtils.generateUuid().replace("-", ""));
        createReqVO.setPlanRule(new Gson().toJson(planRuleOldJson));
        // 插入
        EnergyPlanDO energyPlanDO = BeanUtils.toBean(createReqVO, EnergyPlanDO.class);
        saveRoomIds(energyPlanDO);
        //计算时间【当前执行时间】
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date nextTime = EnergyComputingTime.getNextTime(energyPlanDO.getPlanRule(), sim.parse(sim.format(new Date())), "0");
        energyPlanDO.setNextTime(nextTime);
        Mapper.insert(energyPlanDO);


        EnergyTaskDO energyTaskDO = new EnergyTaskDO();
        energyTaskDO.setOrgId(energyPlanDO.getOrgId());
        energyTaskDO.setPlanId(energyPlanDO.getId());
        energyTaskDO.setTaskCycle("");
        String planRule = energyPlanDO.getPlanRule();
        JSONObject jsonObject = JSON.parseObject(planRule);
        String taskKey = jsonObject.getString("key");
        energyTaskDO.setTaskKey(taskKey);
        energyTaskDO.setTaskName("");
        energyTaskDO.setTaskNumber(UuidUtils.generateUuid().replace("-", ""));
        //第一次执行时间
        energyTaskDO.setShouldTime(energyPlanDO.getNextTime());
        //计算任务发送时间
        Date remindTime = EnergyComputingTime.getRemindTime(energyPlanDO.getRemindJson(), energyPlanDO.getNextTime());
        energyTaskDO.setRemindTime(remindTime);
        energyTaskDO.setIsRemind("0");
        energyTaskDO.setDepartmentId(energyPlanDO.getDepartmentId());
        if (StringUtils.isNotEmpty(energyPlanDO.getDepartmentParentId())) {
            energyTaskDO.setDepartmentParentId(energyPlanDO.getDepartmentParentId());
        }

        List<String> deptIdList = Lists.newArrayList();
        deptIdList.add(String.valueOf(energyPlanDO.getDepartmentId()));
        if (StringUtils.isNotEmpty(energyPlanDO.getDepartmentParentId())) {
            deptIdList.addAll(List.of(energyPlanDO.getDepartmentParentId().split(",")));
        }
        List<String> postIdList = Lists.newArrayList();
        postIdList.add(String.valueOf(energyPlanDO.getStationIds()));
        if (StringUtils.isNotEmpty(energyPlanDO.getStationParentId())) {
            energyTaskDO.setStationParentId(energyPlanDO.getStationParentId());

            postIdList.addAll(List.of(energyPlanDO.getStationParentId().split(",")));
        }

        String postUids = systemUserMapper.getByDeptPostIdListId(deptIdList, postIdList);
        if (StringUtils.isEmpty(postUids)) {
            throw new ServiceException(406, "当前所选岗位和部门下暂无可抄表人员");
        }
        energyTaskDO.setPostUids(postUids);

        energyTaskDO.setStationIds(energyPlanDO.getStationIds());


        //计算超时时间
        getTimeoutTime(energyTaskDO, energyPlanDO);
        energyTaskDO.setIsTimeout("0");
        energyTaskDO.setIsRemind("0");
        energyTaskDO.setStatus(energyPlanDO.getPlanStatus());
        energyTaskDO.setHasLoop("0");
        energyTaskDO.setThirdTaskId(null);
        energyTaskDO.setIsStop("0");
        energyTaskMapper.insert(energyTaskDO);
        //保存巡检日志信息
        List<EnergyTaskLogDO> taskLogList = EnergyTaskLogData.getTaskLog(energyPlanDO, energyTaskDO, "0");
        energyTaskLogMapper.insertBatch(taskLogList);

        // 返回
        return energyPlanDO.getId();
    }

    private void saveRoomIds(EnergyPlanDO energyPlanDO) {
        if (StringUtils.isNotEmpty(energyPlanDO.getLayerIds())) {
            //楼层id
            List<String> layerIds = List.of(energyPlanDO.getLayerIds().split(","));
            LambdaQueryWrapperX<RoomDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.in(RoomDO::getLayerId, layerIds);
            queryWrapperX.in(RoomDO::getVillageId, energyPlanDO.getVillageId());
            queryWrapperX.in(RoomDO::getBuildId, energyPlanDO.getBuildId());
            List<RoomDO> roomList = roomMapper.selectList(queryWrapperX);
            StringBuilder sb = new StringBuilder();
            if (CollectionUtils.isNotEmpty(roomList)) {
                roomList.forEach(roomDO -> {
                    sb.append(roomDO.getId()).append(",");
                });

            }
            String roomIds = sb.toString();
            if (StringUtils.isNotEmpty(roomIds)) {
                energyPlanDO.setRoomIds(roomIds);
            }
        }
    }

    /**
     * 计算超时时间
     *
     * @param energyTaskDO
     * @param energyPlanDO
     */
    private static void getTimeoutTime(EnergyTaskDO energyTaskDO, EnergyPlanDO energyPlanDO) {
        Date nextTime = energyPlanDO.getNextTime();
        Integer timeLimit = energyPlanDO.getTimeLimit();
        energyTaskDO.setTimeoutTime(DateUtils.addHourMillis(nextTime, timeLimit));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EnergyPlanSaveReqVO updateReqVO) throws Exception {
        EnergyPlanDO energyPlanDO = Mapper.selectById(updateReqVO.getId());
        if (energyPlanDO.getStatus().equals("0")) {
            throw new ServiceException(406, "请开启计划后再进行编辑");
        }


        //判断执行周期是否修改
        //当执行周期发生改变后新增任务表和日志表数据
        //当执行周期不发生改变后更新基础数据

        //getPlanRule
        //true需要重新计算时间false不需要重新计算时间
        Boolean isCreate = EnergyComputingTime.getIsCreate(energyPlanDO.getPlanRule(), updateReqVO.getPlanRule());


        // 更新
        EnergyPlanDO updateObj = BeanUtils.toBean(updateReqVO, EnergyPlanDO.class);
        saveRoomIds(updateObj);
        if (isCreate) {
            //计算时间【当前执行时间】
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Date nextTime = PatrolComputingTime.getNextTime(updateObj.getPlanRule(), sim.parse(sim.format(new Date())), "0");
            updateObj.setNextTime(nextTime);
            updateObj.setLastTime(updateObj.getNextTime());
            String planRuleOld = updateObj.getPlanRule();
            JSONObject planRuleOldJson = JSONObject.parseObject(planRuleOld);
            planRuleOldJson.put("key", UuidUtils.generateUuid().replace("-", ""));
            updateObj.setPlanRule(new Gson().toJson(planRuleOldJson));
        } else {
            Date nextTime = Mapper.selectById(updateObj.getId()).getNextTime();
            updateObj.setNextTime(nextTime);
        }
        Mapper.updateById(updateObj);

        String planRule = updateObj.getPlanRule();
        JSONObject jsonObject = JSON.parseObject(planRule);
        String taskKey = jsonObject.getString("key");

        LambdaQueryWrapperX<EnergyTaskDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyTaskDO::getTaskKey, taskKey);
        EnergyTaskDO energyTaskDO = energyTaskMapper.selectOne(queryWrapperX);
        if (energyTaskDO != null) {
            //energyTaskDO.setTaskName(updateObj.getPlanName());
            if (isCreate) {
                //第一次执行时间
                energyTaskDO.setShouldTime(updateObj.getNextTime());
            }
            //计算任务发送时间
            Date remindTime = EnergyComputingTime.getRemindTime(updateObj.getRemindJson(), updateObj.getNextTime());
            energyTaskDO.setRemindTime(remindTime);
            energyTaskDO.setIsRemind("0");
            energyTaskDO.setDepartmentId(updateObj.getDepartmentId());
            if (StringUtils.isNotEmpty(updateObj.getDepartmentParentId())) {
                energyTaskDO.setDepartmentParentId(updateObj.getDepartmentParentId());
            }

            //计算超时时间
            getTimeoutTime(energyTaskDO, updateObj);
            List<String> deptIdList = Lists.newArrayList();
            deptIdList.add(String.valueOf(updateObj.getDepartmentId()));
            if (StringUtils.isNotEmpty(updateObj.getDepartmentParentId())) {
                deptIdList.addAll(List.of(updateObj.getDepartmentParentId().split(",")));
            }

            List<String> postIdList = Lists.newArrayList();
            postIdList.add(String.valueOf(updateObj.getStationIds()));
            if (StringUtils.isNotEmpty(updateObj.getStationParentId())) {
                energyTaskDO.setStationParentId(updateObj.getStationParentId());

                postIdList.addAll(List.of(updateObj.getStationParentId().split(",")));
            }

            String postUids = systemUserMapper.getByDeptPostIdListId(deptIdList, postIdList);
            if (StringUtils.isEmpty(postUids)) {
                throw new ServiceException(406, "当前所选岗位和部门下暂无可抄表人员");
            }
            energyTaskDO.setPostUids(postUids);
            energyTaskDO.setStationIds(updateObj.getStationIds());

            energyTaskMapper.updateById(energyTaskDO);
        } else {
            energyTaskDO = new EnergyTaskDO();
            //energyTaskDO.setTaskName(updateObj.getPlanName());
            //第一次执行时间
            energyTaskDO.setShouldTime(updateObj.getNextTime());
            //计算任务发送时间
            Date remindTime = EnergyComputingTime.getRemindTime(updateObj.getRemindJson(), updateObj.getNextTime());
            energyTaskDO.setRemindTime(remindTime);
            energyTaskDO.setIsRemind("0");
            energyTaskDO.setStationIds(updateObj.getStationIds());
            energyTaskDO.setDepartmentId(updateObj.getDepartmentId());
            //计算超时时间
            getTimeoutTime(energyTaskDO, updateObj);
            //patrolTaskEquipmentDO.setId(null);
            energyTaskMapper.insert(energyTaskDO);
        }


        if (isCreate) {
            //新增日志
            List<EnergyTaskLogDO> taskLogList = EnergyTaskLogData.getTaskLog(updateObj, energyTaskDO, "0");
            energyTaskLogMapper.insertBatch(taskLogList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception {

        // 删除
        Mapper.deleteById(id);

        LambdaQueryWrapperX<EnergyTaskDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyTaskDO::getPlanId, id);
        energyTaskMapper.delete(queryWrapperX);

        LambdaQueryWrapperX<EnergyTaskLogDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(EnergyTaskLogDO::getPlanId, id);
        energyTaskLogMapper.delete(queryWrapperX1);
    }


    @Override
    public EnergyPlanDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyPlanRespVO> getPage(EnergyPlanPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyPlanDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(EnergyPlanDO::getVillageId, pageReqVO.getVillageId());
        }

        if (pageReqVO.getBuildId() != null) {
            queryWrapperX.eq(EnergyPlanDO::getBuildId, pageReqVO.getBuildId());
        }

        if (pageReqVO.getDepartmentId() != null) {
            queryWrapperX.eq(EnergyPlanDO::getDepartmentId, pageReqVO.getDepartmentId());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStationIds())) {
            queryWrapperX.apply("REGEXP_LIKE(station_ids, '(^|,)" + pageReqVO.getStationIds() + "($|,)')");
        }

        if (StringUtils.isNotEmpty(pageReqVO.getPlanStatus())) {
            queryWrapperX.eq(EnergyPlanDO::getPlanStatus, pageReqVO.getPlanStatus());
        }

        PageResult<EnergyPlanDO> energyPlanDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<EnergyPlanRespVO> respVOPageResult = BeanUtils.toBean(energyPlanDOPageResult, EnergyPlanRespVO.class);
        List<EnergyPlanRespVO> list = respVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(energyPlanRespVO -> {
                getStationName(energyPlanRespVO);

                getDeptName(energyPlanRespVO);

                String planStatus = energyPlanRespVO.getPlanStatus();
                String dataLabel = DictFrameworkUtils.getDictDataLabel("ENERGY_PLAN_STATUS", planStatus);
                energyPlanRespVO.setPlanStatusName(dataLabel);

                energyPlanRespVO.setStatusName(energyPlanRespVO.getStatus().equals("1") ? "开启" : "关闭");

                VillageDO villageDO = villageMapper.selectById(energyPlanRespVO.getVillageId());
                if (villageDO != null) {
                    energyPlanRespVO.setVillageName(villageDO.getName());
                }

                BuildDO buildDO = buildMapper.selectById(energyPlanRespVO.getBuildId());
                if (villageDO != null) {
                    energyPlanRespVO.setBuildName(buildDO.getBuildName());
                }

                getLayerName(energyPlanRespVO);
            });
        }
        return respVOPageResult;
    }


    private void getDeptName(EnergyPlanRespVO energyPlanRespVO) {
        Long departmentId = energyPlanRespVO.getDepartmentId();
        CommonResult<DeptRespDTO> deptResult = deptApi.getDept(departmentId);
        if (deptResult.getCode() == 0) {
            DeptRespDTO dept = deptResult.getData();
            if (dept != null) {
                energyPlanRespVO.setStationDepartmentName(energyPlanRespVO.getStationDepartmentName() + "-" + dept.getName());
            }
        }
    }

    private void getStationName(EnergyPlanRespVO energyPlanRespVO) {
        String stationIds = energyPlanRespVO.getStationIds();
        List<String> list = Arrays.asList(stationIds.split(","));
        List<Long> stationIdList = Lists.newArrayList();
        list.forEach(stationId -> stationIdList.add(Long.valueOf(stationId)));
        CommonResult<List<PostRespDTO>> postResult = postApi.getPostList(stationIdList);
        if (postResult.getCode() == 0) {
            List<PostRespDTO> postList = postResult.getData();
            if (CollectionUtils.isNotEmpty(postList)) {
                StringBuilder sb = new StringBuilder();
                postList.forEach(aa -> {
                    sb.append(aa.getName()).append(",");
                });
                String stationDepartmentName = sb.toString();
                if (StringUtils.isNotEmpty(stationDepartmentName)) {
                    energyPlanRespVO.setStationDepartmentName(stationDepartmentName.substring(0, stationDepartmentName.length() - 1));
                }
            }
        }
    }


    private void getLayerName(EnergyPlanRespVO energyPlanRespVO) {
        String layerIds = energyPlanRespVO.getLayerIds();
        if(StringUtils.isNotEmpty(layerIds)){
            StringBuilder sb = new StringBuilder();
            List<String>layerIdList= List.of(layerIds.split(","));
            for (String layerId : layerIdList) {
                LayerDO layerDO = layerMapper.selectById(layerId);
                sb.append(layerDO.getLayerName()).append(",");
            }
            String layerName = sb.toString();
            if (StringUtils.isNotEmpty(layerName)) {
                layerName = layerName.substring(0, layerName.length() - 1);
                energyPlanRespVO.setLayerName(layerName);
            }
        }
    }
}