package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolRecordEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.TaskUserLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolRecordEquipmentMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolTaskEquipmentMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.TaskUserLogMapper;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.PostApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.PostRespDTO;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.mapstruct.ap.shaded.freemarker.ext.util.IdentityHashMap;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PATROL_TASK_EQUIPMENT_NOT_EXISTS;

/**
 * 应用巡检任务 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolTaskEquipmentServiceImpl implements PatrolTaskEquipmentService {

    @Resource
    private PatrolTaskEquipmentMapper Mapper;

    @Resource
    private PostApi postApi;

    @Resource
    private DeptApi deptApi;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private TaskUserLogMapper taskUserLogMapper;

    @Resource
    private PatrolRecordEquipmentMapper patrolRecordEquipmentMapper;


    @Override
    public Long create(PatrolTaskEquipmentSaveReqVO createReqVO) {
        // 插入
        PatrolTaskEquipmentDO patrolTaskEquipmentDO = BeanUtils.toBean(createReqVO, PatrolTaskEquipmentDO.class);
        Mapper.insert(patrolTaskEquipmentDO);
        // 返回
        return patrolTaskEquipmentDO.getId();
    }

    @Override
    public void update(PatrolTaskEquipmentSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        PatrolTaskEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, PatrolTaskEquipmentDO.class);
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
            throw exception(PATROL_TASK_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public PatrolTaskEquipmentDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<PatrolTaskEquipmentRespVO> getPage(PatrolTaskEquipmentPageReqVO pageReqVO) {

        LambdaQueryWrapperX<PatrolTaskEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolTaskEquipmentDO::getApplication,pageReqVO.getApplication());
        if (StringUtils.isNotEmpty(pageReqVO.getStatus())) {
            queryWrapperX.eq(PatrolTaskEquipmentDO::getStatus, pageReqVO.getStatus());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getTaskName())) {
            queryWrapperX.like(PatrolTaskEquipmentDO::getTaskName, pageReqVO.getTaskName());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getTaskNumber())) {
            queryWrapperX.like(PatrolTaskEquipmentDO::getTaskNumber, pageReqVO.getTaskNumber());
        }
        queryWrapperX.orderByDesc(PatrolTaskEquipmentDO::getCreateTime);
        PageResult<PatrolTaskEquipmentDO> patrolTaskEquipmentDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<PatrolTaskEquipmentRespVO> pageResult = BeanUtils.toBean(patrolTaskEquipmentDOPageResult, PatrolTaskEquipmentRespVO.class);
        List<PatrolTaskEquipmentRespVO> list = pageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(patrolTaskEquipmentRespVO -> {

                String dataLabel = DictFrameworkUtils.getDictDataLabel("PLAN_STATUS", patrolTaskEquipmentRespVO.getStatus());
                patrolTaskEquipmentRespVO.setStatusName(dataLabel);

                getStationName(patrolTaskEquipmentRespVO);

                getDeptName(patrolTaskEquipmentRespVO);

                LambdaQueryWrapperX<TaskUserLogDO> taskUserLogDOLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
                taskUserLogDOLambdaQueryWrapperX.eq(TaskUserLogDO::getTaskId, patrolTaskEquipmentRespVO.getId());
                taskUserLogDOLambdaQueryWrapperX.orderByDesc(TaskUserLogDO::getCreateTime);
                List<TaskUserLogDO> taskUserLogList = taskUserLogMapper.selectList(taskUserLogDOLambdaQueryWrapperX);
                if (CollectionUtils.isNotEmpty(taskUserLogList)) {
                    patrolTaskEquipmentRespVO.setChangePersonnelList(taskUserLogList);
                }
            });
        }
        return pageResult;
    }

    private void getDeptName(PatrolTaskEquipmentRespVO patrolTaskEquipmentRespVO) {
        Long departmentId = patrolTaskEquipmentRespVO.getDepartmentId();
        CommonResult<DeptRespDTO> deptResult = deptApi.getDept(departmentId);
        if (deptResult.getCode() == 0) {
            DeptRespDTO dept = deptResult.getData();
            if (dept != null) {
                patrolTaskEquipmentRespVO.setStationDepartmentName(patrolTaskEquipmentRespVO.getStationDepartmentName() + "-" + dept.getName());
            }
        }
    }

    private void getStationName(PatrolTaskEquipmentRespVO patrolTaskEquipmentRespVO) {
        Long stationId = patrolTaskEquipmentRespVO.getStationId();
        CommonResult<List<PostRespDTO>> postResult = postApi.getPostList(Arrays.asList(stationId));
        if (postResult.getCode() == 0) {
            List<PostRespDTO> postList = postResult.getData();
            if (CollectionUtils.isNotEmpty(postList)) {
                String name = postList.get(0).getName();
                patrolTaskEquipmentRespVO.setStationDepartmentName(name);
            }
        }
    }

    @Override
    public Map<String, Long> getTopStatistics(String application) {
        Map<String, Long> map = new HashMap<>();
        LambdaQueryWrapperX<PatrolTaskEquipmentDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolTaskEquipmentDO::getApplication,application);
        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = Mapper.selectList();
        //巡检中
        long havingTaskCount = 0L;
        //已完成
        long overTaskCount = 0L;
        //超期未执勤
        long timeoutTaskCount = 0L;
        //待开始
        long waitTaskCount = 0L;
        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {

            waitTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("1")).count();
            overTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("3")).count();
            havingTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("2")).count();
            timeoutTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("5")).count();
        }

        //巡检中
        map.put("havingTaskCount", havingTaskCount);
        //已完成
        map.put("overTaskCount", overTaskCount);
        //超期未执勤
        map.put("timeoutTaskCount", timeoutTaskCount);
        //待开始
        map.put("waitTaskCount", waitTaskCount);
        return map;
    }

    @Override
    public List<AdminUserRespDTO> getTaskPost(Long id) {
        List<AdminUserRespDTO> userList = Lists.newArrayList();
        PatrolTaskEquipmentDO patrolTaskEquipmentDO = Mapper.selectById(id);
        if (!StringUtils.equals(patrolTaskEquipmentDO.getStatus(), "1")) {
            throw new ServiceException(406, "请选择待开始的任务进行变更");
        }
        if (StringUtils.isNotEmpty(patrolTaskEquipmentDO.getPostUids())) {
            List<String> userIdList = List.of(patrolTaskEquipmentDO.getPostUids().split(","));
            for (String userId : userIdList) {
                CommonResult<AdminUserRespDTO> userApiUser = adminUserApi.getUser(Long.valueOf(userId));
                AdminUserRespDTO user = userApiUser.getData();
                userList.add(user);
            }
        }
        return userList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveTaskPost(TaskUserLogSaveReqVO createReqVO) {
        PatrolTaskEquipmentDO patrolTaskEquipmentDO = Mapper.selectById(createReqVO.getTaskId());
        TaskUserLogDO taskUserLogDO = BeanUtils.toBean(createReqVO, TaskUserLogDO.class);
        taskUserLogDO.setApplication(patrolTaskEquipmentDO.getApplication());
        taskUserLogMapper.insert(taskUserLogDO);

        String postUids = patrolTaskEquipmentDO.getPostUids();

        if (StringUtils.isNotEmpty(postUids)) {
            List<String>userIdList=Lists.newArrayList();
            userIdList.add(String.valueOf(createReqVO.getNewUid()));

            List<String> postUserIdList = List.of(postUids.split(","));
            for (String userId : postUserIdList) {
                if(!StringUtils.equals(userId,String.valueOf(createReqVO.getOldUid()))){
                    userIdList.add(userId);
                }
            }

            StringBuilder sb = new StringBuilder();
            postUserIdList.forEach(userId -> {
                sb.append(userId).append(",");
            });
            postUids = sb.toString();
            if (StringUtils.isNotEmpty(postUids)) {
                postUids = postUids.substring(0, postUids.length() - 1);
                patrolTaskEquipmentDO.setPostUids(postUids);
            }
        }

        Mapper.updateById(patrolTaskEquipmentDO);
        return true;
    }



    @Override
    public Map<String, Object> patrolTaskStaticTop(String application) {
        Map<String, Object> map = new HashMap();
        LambdaQueryWrapperX<PatrolTaskEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolTaskEquipmentDO::getApplication,application);
        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = Mapper.selectList(queryWrapperX);
        //任务完成数
        Integer overTaskCount = 0;
        //任务完成率
        Double overTaskRate = 0.00;

        //累计发现异常
        Integer abnormalCount = 0;
        //累计发现率
        Double abnormalRate = 0.00;

        //超时任务数
        Integer timeoutTaskCount = 0;
        //超时率
        Double timeoutTaskRate = 0.00;

        //整改数
        Integer rectificationCount = 0;
        //整改率
        Double rectificationRate = 0.00;


        //挂起
        Integer hangUpTaskCount = 0;
        //挂起率
        Double hangUpTaskRate = 0.00;
        int patrolTaskCount = patrolTaskEquipmentList.size();
        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {

            //任务状态已完成
            overTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("3")).collect(Collectors.toList()).size();
            if (overTaskCount > 0) {
                overTaskRate = (Double.valueOf(overTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
            }

            //超时任务数
            timeoutTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("4") || aa.getStatus().equals("5")).collect(Collectors.toList()).size();
            if (timeoutTaskCount > 0) {
                timeoutTaskRate = (Double.valueOf(timeoutTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
            }


            hangUpTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("6")).collect(Collectors.toList()).size();
            if (hangUpTaskCount > 0) {
                hangUpTaskRate = (Double.valueOf(hangUpTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
            }


        }

        LambdaQueryWrapperX<PatrolRecordEquipmentDO>queryWrapperX1=new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(PatrolRecordEquipmentDO::getApplication,application);
        List<PatrolRecordEquipmentDO> patrolRecordEquipmentList = patrolRecordEquipmentMapper.selectList(queryWrapperX1);
        if (CollectionUtils.isNotEmpty(patrolRecordEquipmentList)) {
            Integer computeTotal = patrolRecordEquipmentList.size();

            //异常
            abnormalCount = patrolRecordEquipmentList.stream().filter(aa -> aa.getStatus().equals("3") || aa.getStatus().equals("4")).collect(Collectors.toList()).size();
            if (abnormalCount > 0) {
                abnormalRate = (Double.valueOf(abnormalCount) / Double.valueOf(computeTotal)) * 100;
            }

            //整改
            rectificationCount = patrolRecordEquipmentList.stream().filter(aa -> aa.getStatus().equals("4")).collect(Collectors.toList()).size();
            if (rectificationCount > 0) {
                rectificationRate = (Double.valueOf(rectificationCount) / Double.valueOf(computeTotal)) * 100;
            }
        }

        //任务完成数
        map.put("overTaskCount", overTaskCount);
        //完成率
        map.put("overTaskRate", Tools.DecimalFormat(overTaskRate));

        //累计发现异常
        map.put("abnormalCount", abnormalCount);
        //异常率
        map.put("abnormalRate", Tools.DecimalFormat(abnormalRate));

        //超时任务数
        map.put("timeoutTaskCount", timeoutTaskCount);
        //超时率
        map.put("timeoutTaskRate", Tools.DecimalFormat(timeoutTaskRate));

        //整改完成率
        map.put("rectificationRate", Tools.DecimalFormat(rectificationRate));
        //跳过率
        map.put("timeoutBdTaskRate", Tools.DecimalFormat(hangUpTaskRate));


        return map;
    }


    /**
     *
     * @param startDate
     * @param endDate
     * @param application
     * @return
     */
    @Override
    public List<JSONObject> patrolTaskStatic(String startDate, String endDate,String application) {
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        LambdaQueryWrapperX<PatrolTaskEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolTaskEquipmentDO::getApplication,application);
        if (StringUtils.isNotEmpty(startDate)) {
            queryWrapperX.apply("DATE_FORMAT(should_time,'%Y-%m-%d') between DATE_FORMAT(" + startDate + ",'%Y-%m-%d') and DATE_FORMAT(" + endDate + ",'%Y-%m-%d')");
        }
        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = Mapper.selectList(queryWrapperX);
        //任务完成数
        Integer overTaskCount = 0;
        //任务完成率
        Double overTaskRate = 0.00;
        //超时任务数
        Integer timeoutTaskCount = 0;
        //超时率
        Double timeoutTaskRate = 0.00;
        //超时未处理任务数
        Integer timeoutBdTaskCount = 0;
        //超时未处理率
        Double timeoutBdTaskRate = 0.00;
        //待开始
        Integer waitTaskCount = 0;
        //待开始任务率
        Double waitTaskRate = 0.00;
        //挂起
        Integer hangUpTaskCount = 0;
        //挂起率
        Double hangUpTaskRate = 0.00;
        int patrolTaskCount = patrolTaskEquipmentList.size();
        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {

            //超期未处理
            timeoutBdTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("5")).collect(Collectors.toList()).size();
            if (timeoutBdTaskCount > 0) {
                timeoutBdTaskRate = (Double.valueOf(timeoutBdTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
            }

            overTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("3")).collect(Collectors.toList()).size();
            if (overTaskCount > 0) {
                overTaskRate = (Double.valueOf(overTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
            }


            timeoutTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("4")).collect(Collectors.toList()).size();
            if (timeoutTaskCount > 0) {
                timeoutTaskRate = (Double.valueOf(timeoutTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
            }


            waitTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("1")).collect(Collectors.toList()).size();
            if (waitTaskCount > 0) {
                waitTaskRate = (Double.valueOf(waitTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
            }

//            hangUpTaskCount = patrolTaskEquipmentList.stream().filter(aa -> aa.getStatus().equals("6")).collect(Collectors.toList()).size();
//            if (hangUpTaskCount > 0) {
//                hangUpTaskRate = (Double.valueOf(hangUpTaskCount) / Double.valueOf(patrolTaskCount)) * 100;
//            }


        }
        JSONObject jsonObject = new JSONObject();

        //超期未处理
        jsonObject.put("name", "超期未处理");
        jsonObject.put("total", timeoutBdTaskCount);
        jsonObject.put("percent", Tools.DecimalFormat(timeoutBdTaskRate));
        jsonObjectList.add(jsonObject);

        //待开始
        jsonObject = new JSONObject();
        jsonObject.put("name", "待开始");
        jsonObject.put("total", waitTaskCount);
        jsonObject.put("percent", Tools.DecimalFormat(waitTaskRate));
        jsonObjectList.add(jsonObject);

        //超期
        jsonObject = new JSONObject();
        jsonObject.put("name", "超期");
        jsonObject.put("total", timeoutTaskCount);
        jsonObject.put("percent", Tools.DecimalFormat(timeoutTaskRate));
        jsonObjectList.add(jsonObject);

        //已完成
        jsonObject = new JSONObject();
        jsonObject.put("name", "已完成");
        jsonObject.put("total", overTaskCount);
        jsonObject.put("percent", Tools.DecimalFormat(overTaskRate));
        jsonObjectList.add(jsonObject);
        return jsonObjectList;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param positionId
     * @param application
     * @return
     */
    @Override
    public JSONObject patrolPositionStatic(String startDate, String endDate, Long positionId,String application) {
        LambdaQueryWrapperX<PatrolRecordEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolRecordEquipmentDO::getApplication,application);
        if (StringUtils.isNotEmpty(startDate)) {
            queryWrapperX.apply("DATE_FORMAT(create_time,'%Y-%m-%d') between DATE_FORMAT(" + startDate + ",'%Y-%m-%d') and DATE_FORMAT(" + endDate + ",'%Y-%m-%d')");
        }

        if (positionId != null) {
            queryWrapperX.eq(PatrolRecordEquipmentDO::getPatrolPositionId, positionId);
        }
        //应用巡检次数
        Integer computeTotal = 0;
        //完成率
        Double successRate = 0.00;

        //正常
        Integer normalCount = 0;
        Double normalRate = 0.00;

        //异常
        Integer abnormalCount = 0;
        Double abnormalRate = 0.00;

        //跳过
        Integer skipCount = 0;
        Double skipRate = 0.00;

        List<PatrolRecordEquipmentDO> patrolRecordEquipmentList = patrolRecordEquipmentMapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(patrolRecordEquipmentList)) {
            computeTotal = patrolRecordEquipmentList.size();
            List<PatrolRecordEquipmentDO> collect = patrolRecordEquipmentList.stream().filter(aa -> StringUtils.equals(aa.getStatus(), "2") || StringUtils.equals(aa.getStatus(), "3") || StringUtils.equals(aa.getStatus(), "4") || StringUtils.equals(aa.getStatus(), "5")).collect(Collectors.toList());
            int size = collect.size();
            if (size > 0) {
                successRate = (Double.valueOf(size) / Double.valueOf(computeTotal)) * 100;
            }

            //正常
            normalCount = collect.stream().filter(aa -> aa.getStatus().equals("2")).collect(Collectors.toList()).size();
            if (normalCount > 0) {
                normalRate = (Double.valueOf(normalCount) / Double.valueOf(size)) * 100;
            }


            //异常
            abnormalCount = collect.stream().filter(aa -> aa.getStatus().equals("3") || aa.getStatus().equals("4")).collect(Collectors.toList()).size();
            if (abnormalCount > 0) {
                abnormalRate = (Double.valueOf(abnormalCount) / Double.valueOf(size)) * 100;
            }
            //跳过
            skipCount = collect.stream().filter(aa -> aa.getStatus().equals("5")).collect(Collectors.toList()).size();
            if (skipCount > 0) {
                skipRate = (Double.valueOf(skipCount) / Double.valueOf(size)) * 100;
            }
        }
        JSONObject jsonObject = new JSONObject();
        //应用巡检次数
        jsonObject.put("computeTotal", computeTotal);
        //完成率
        jsonObject.put("successRate", Tools.DecimalFormat(successRate));
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        JSONObject jsonObject1 = new JSONObject();
        //正常
        jsonObject1.put("name", "正常");
        jsonObject1.put("total", normalCount);
        jsonObject1.put("value", Tools.DecimalFormat(normalRate));
        jsonObjectList.add(jsonObject1);
        //异常
        jsonObject1 = new JSONObject();
        jsonObject1.put("name", "异常");
        jsonObject1.put("total", abnormalCount);
        jsonObject1.put("value", Tools.DecimalFormat(abnormalRate));
        jsonObjectList.add(jsonObject1);

        //跳过
        jsonObject1 = new JSONObject();
        jsonObject1.put("name", "跳过");
        jsonObject1.put("total", skipCount);
        jsonObject1.put("value", Tools.DecimalFormat(skipRate));
        jsonObjectList.add(jsonObject1);
        jsonObject.put("list", jsonObjectList);
        return jsonObject;
    }


}