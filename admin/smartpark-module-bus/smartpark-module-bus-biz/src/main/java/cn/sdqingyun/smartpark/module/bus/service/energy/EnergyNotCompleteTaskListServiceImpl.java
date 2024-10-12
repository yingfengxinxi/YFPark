package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyNotCompleteTaskListVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/10/8 8:43
 */
@Service
public class EnergyNotCompleteTaskListServiceImpl implements EnergyNotCompleteTaskListService {


    @Resource
    private EnergyPlanMapper energyPlanMapper;

    @Resource
    private EnergyTaskMapper energyTaskMapper;

    @Resource
    private EnergyMapper energyMapper;

    @Resource
    private EnergyTaskRecordMapper energyTaskRecordMapper;

    @Resource
    private EnergyRecordMapper energyRecordMapper;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private RoomMapper roomMapper;

    /**
     * @param planId
     * @return
     */
    @Override
    public List<EnergyNotCompleteTaskListVO> notCompleteTaskList(Long planId) {

        if (planId == null) {
            throw new ServiceException(406, "planId 必传");
        }

        EnergyPlanDO energyPlanDO = energyPlanMapper.selectById(planId);
        if (energyPlanDO == null) {
            throw new ServiceException(406, "抄表计划不存在");
        }
        List<EnergyNotCompleteTaskListVO> energyNotCompleteTaskListList = Lists.newArrayList();

        LambdaQueryWrapperX<EnergyTaskDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyTaskDO::getPlanId, energyPlanDO.getId());
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        queryWrapperX.apply("DATE_FORMAT(should_time,'%Y-%m-%d')='" + sim.format(new Date()) + "' and  status!='3'");
        queryWrapperX.orderByDesc(EnergyTaskDO::getId);
        List<EnergyTaskDO> energyTaskDOList = energyTaskMapper.selectList(queryWrapperX);
        if (CollectionUtils.isEmpty(energyTaskDOList)) {
            return Lists.newArrayList();
        }
        EnergyTaskDO energyTaskDO = energyTaskDOList.get(0);

        Long taskId = energyTaskDO.getId();


        String roomIds = energyPlanDO.getRoomIds();
        List<Long> energyIds = Lists.newArrayList();
        if (StringUtils.isNotEmpty(roomIds)) {
            List<String> roomIdList = List.of(roomIds.split(","));
            roomIdList.forEach(roomId -> {
                LambdaQueryWrapperX<EnergyDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(EnergyDO::getVillageId, energyPlanDO.getVillageId());
                queryWrapperX1.eq(EnergyDO::getBuildId, energyPlanDO.getBuildId());
                queryWrapperX1.in(EnergyDO::getType, Arrays.asList(energyPlanDO.getEnergyType().split(",")));
                queryWrapperX1.apply("REGEXP_LIKE(room_ids, '(^|,)" + roomId + "($|,)')");
                queryWrapperX1.eq(EnergyDO::getStatus, "1");
                //queryWrapperX1.notIn(EnergyDO::getId, "1");
                List<EnergyDO> energyList = energyMapper.selectList(queryWrapperX1);
                if (CollectionUtils.isNotEmpty(energyList)) {
                    energyIds.addAll(energyList.stream().map(energyDO -> energyDO.getId()).collect(Collectors.toList()));
                }
            });
        }
        List<Long> energyIdList = energyIds.stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(energyIdList)) {
            return Lists.newArrayList();
        }

        LambdaQueryWrapperX<EnergyTaskRecordDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(EnergyTaskRecordDO::getPlanId, planId);
        queryWrapperX1.apply("DATE_FORMAT(create_time,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d')");
        queryWrapperX1.orderByDesc(EnergyTaskRecordDO::getCreateTime);
        List<EnergyTaskRecordDO> energyTaskRecordList = energyTaskRecordMapper.selectList(queryWrapperX1);
        EnergyTaskRecordDO energyTaskRecordDO = new EnergyTaskRecordDO();
        if (CollectionUtils.isNotEmpty(energyTaskRecordList)) {
            energyTaskRecordDO = energyTaskRecordList.get(0);
        }


        LambdaQueryWrapperX<EnergyDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
        queryWrapperX2.in(EnergyDO::getId, energyIdList);
        if (energyTaskRecordDO != null) {
            queryWrapperX2.apply("id !='" + energyTaskRecordDO.getEnergyId() + "'");
        }
        //预付费不抄表
        queryWrapperX2.eq(EnergyDO::getPaymentType, "0");
        List<EnergyDO> energyList = energyMapper.selectList(queryWrapperX2);

        for (EnergyDO energyDO : energyList) {
            EnergyNotCompleteTaskListVO energyNotCompleteTaskListVO = new EnergyNotCompleteTaskListVO();
            energyNotCompleteTaskListVO.setEnergyId(energyDO.getId());
            energyNotCompleteTaskListVO.setEnergyTaskId(taskId);
            energyNotCompleteTaskListVO.setName(energyDO.getName());
            energyNotCompleteTaskListVO.setNumber(energyDO.getNumber());
            String purpose = DictFrameworkUtils.getDictDataLabel("PURPODE", energyDO.getPurpose());
            energyNotCompleteTaskListVO.setPurpose(purpose);
            VillageDO villageDO = villageMapper.selectById(energyDO.getVillageId());
            if (villageDO != null) {
                energyNotCompleteTaskListVO.setBindingLocation(villageDO.getName());
            }

            BuildDO buildDO = buildMapper.selectById(energyDO.getBuildId());
            if (buildDO != null) {
                energyNotCompleteTaskListVO.setBindingLocation(energyNotCompleteTaskListVO.getBindingLocation() + "/" + buildDO.getBuildName());
            }

            String roomName = getRoomName(energyDO);
            energyNotCompleteTaskListVO.setBindingLocation(energyNotCompleteTaskListVO.getBindingLocation() + "/" + roomName);


            LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
            queryWrapperX3.eq(EnergyRecordDO::getEnergyId, energyDO.getId());
            queryWrapperX3.eq(EnergyRecordDO::getVillageId, energyDO.getVillageId());
            queryWrapperX3.eq(EnergyRecordDO::getBuildId, energyDO.getBuildId());
            queryWrapperX3.orderByDesc(EnergyRecordDO::getCreateTime);
            List<EnergyRecordDO> energyRecordList = energyRecordMapper.selectList(queryWrapperX3);
            if (CollectionUtils.isNotEmpty(energyRecordList)) {
                EnergyRecordDO energyRecordDO = energyRecordList.get(0);
                energyNotCompleteTaskListVO.setThisTime(energyRecordDO.getThisTime());
                energyNotCompleteTaskListVO.setThisNumber(energyRecordDO.getThisNumber());
            } else {
                energyNotCompleteTaskListVO.setThisTime(energyDO.getMeterTime());
                energyNotCompleteTaskListVO.setThisNumber(energyDO.getOriginalReading());
            }
            energyNotCompleteTaskListList.add(energyNotCompleteTaskListVO);
        }
        return energyNotCompleteTaskListList;
    }

    private String getRoomName(EnergyDO energyDO) {
        String roomIds = energyDO.getRoomIds();
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
        return null;
    }
}
