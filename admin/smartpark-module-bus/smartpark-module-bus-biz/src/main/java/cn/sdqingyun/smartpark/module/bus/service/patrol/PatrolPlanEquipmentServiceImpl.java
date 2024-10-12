package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.hutool.core.lang.UUID;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolPlanEquipmentMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolPositionMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolTaskEquipmentLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolTaskEquipmentMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLocationMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.PostApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.PostRespDTO;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PATROL_PLAN_EQUIPMENT_NOT_EXISTS;

/**
 * 应用巡检计划 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolPlanEquipmentServiceImpl implements PatrolPlanEquipmentService {

    @Resource
    private PatrolPlanEquipmentMapper Mapper;

    @Resource
    private PatrolPlanPositionService patrolPlanPositionService;

    @Resource
    private PatrolPositionService patrolPositionService;

    @Resource
    private PropertyLocationMapper propertyLocationMapper;

    @Resource
    private PostApi postApi;

    @Resource
    private DeptApi deptApi;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private PatrolTaskEquipmentLogMapper patrolTaskEquipmentLogMapper;

    @Resource
    private PatrolTaskEquipmentMapper patrolTaskEquipmentMapper;

    @Resource
    private AdminUserApi adminUserApi;


    /**
     * @param createReqVO 创建信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(PatrolPlanEquipmentSaveReqVO createReqVO) throws Exception {
        isCheckPlanName(createReqVO.getApplication(),createReqVO.getPlanName(), createReqVO.getId());
        createReqVO.setPlanNumber("XJJH" + UuidUtils.generateUuid().replace("-", ""));
        createReqVO.setStatus("1");
        createReqVO.setPlanStatus("1");
        String planRuleOld = createReqVO.getPlanRule();
        JSONObject planRuleOldJson = JSONObject.parseObject(planRuleOld);
        planRuleOldJson.put("key", UuidUtils.generateUuid().replace("-", ""));
        createReqVO.setPlanRule(new Gson().toJson(planRuleOldJson));
        // 插入
        PatrolPlanEquipmentDO patrolPlanEquipmentDO = BeanUtils.toBean(createReqVO, PatrolPlanEquipmentDO.class);
        //计算时间【当前执行时间】
        Date nextTime = PatrolComputingTime.getNextTime(patrolPlanEquipmentDO.getPlanRule(), patrolPlanEquipmentDO.getStartDate(), "0");
        patrolPlanEquipmentDO.setNextTime(nextTime);
        Mapper.insert(patrolPlanEquipmentDO);

        savePatrolPlan(createReqVO, patrolPlanEquipmentDO.getId());

        PatrolTaskEquipmentDO patrolTaskEquipmentDO = new PatrolTaskEquipmentDO();
        patrolTaskEquipmentDO.setOrgId(patrolPlanEquipmentDO.getOrgId());
        patrolTaskEquipmentDO.setApplication(patrolPlanEquipmentDO.getApplication());
        patrolTaskEquipmentDO.setPlanId(patrolPlanEquipmentDO.getId());
        patrolTaskEquipmentDO.setPatrolOrder(patrolPlanEquipmentDO.getPatrolOrder());
        patrolTaskEquipmentDO.setTaskCycle("");
        String planRule = patrolPlanEquipmentDO.getPlanRule();
        JSONObject jsonObject = JSON.parseObject(planRule);
        String taskKey = jsonObject.getString("key");
        patrolTaskEquipmentDO.setTaskKey(taskKey);
        patrolTaskEquipmentDO.setTaskName(patrolPlanEquipmentDO.getPlanName());
        patrolTaskEquipmentDO.setTaskNumber(UuidUtils.generateUuid().replace("-", ""));
        //第一次执行时间
        patrolTaskEquipmentDO.setShouldTime(patrolPlanEquipmentDO.getNextTime());
        //计算任务发送时间
        Date remindTime = PatrolComputingTime.getRemindTime(patrolPlanEquipmentDO.getRemindJson(), patrolPlanEquipmentDO.getNextTime());
        patrolTaskEquipmentDO.setRemindTime(remindTime);
        patrolTaskEquipmentDO.setIsRemind("0");


        patrolTaskEquipmentDO.setDepartmentId(patrolPlanEquipmentDO.getDepartmentId());
        if (StringUtils.isNotEmpty(patrolPlanEquipmentDO.getDepartmentParentId())) {
            patrolTaskEquipmentDO.setDepartmentParentId(patrolPlanEquipmentDO.getDepartmentParentId());
        }

        List<String> deptIdList = Lists.newArrayList();
        deptIdList.add(String.valueOf(patrolPlanEquipmentDO.getDepartmentId()));
        if (StringUtils.isNotEmpty(patrolPlanEquipmentDO.getDepartmentParentId())) {
            deptIdList.addAll(List.of(patrolPlanEquipmentDO.getDepartmentParentId().split(",")));
        }

        List<String> postIdList = Lists.newArrayList();
        postIdList.add(String.valueOf(patrolPlanEquipmentDO.getStationId()));
        if (StringUtils.isNotEmpty(patrolPlanEquipmentDO.getStationParentId())) {
            patrolTaskEquipmentDO.setStationParentId(patrolPlanEquipmentDO.getStationParentId());

            postIdList.addAll(List.of(patrolPlanEquipmentDO.getStationParentId().split(",")));
        }

        String postUids = systemUserMapper.getByDeptPostIdListId(deptIdList, postIdList);
        if (StringUtils.isEmpty(postUids)) {
            throw new ServiceException(406, "当前所选岗位和部门下暂无可巡检人员");
        }


        patrolTaskEquipmentDO.setStationId(patrolPlanEquipmentDO.getStationId());

        patrolTaskEquipmentDO.setPostUids(postUids);

        //计算超时时间
        getTimeoutTime(patrolTaskEquipmentDO, patrolPlanEquipmentDO);
        patrolTaskEquipmentDO.setIsTimeout("0");
        patrolTaskEquipmentDO.setIsRemind("0");
        patrolTaskEquipmentDO.setStatus(patrolPlanEquipmentDO.getPlanStatus());
        patrolTaskEquipmentDO.setHasLoop("0");
        patrolTaskEquipmentDO.setThirdTaskId("");
        patrolTaskEquipmentDO.setIsStop(0);
        patrolTaskEquipmentMapper.insert(patrolTaskEquipmentDO);
        //保存巡检日志信息
        List<PatrolTaskEquipmentLogDO> taskEquipmentLog = PatrolTaskEquipmentLogData.getTaskEquipmentLog(patrolPlanEquipmentDO, patrolTaskEquipmentDO, "0");
        patrolTaskEquipmentLogMapper.insertBatch(taskEquipmentLog);


        // 返回
        return patrolPlanEquipmentDO.getId();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PatrolPlanEquipmentSaveReqVO updateReqVO) throws Exception {
        PatrolPlanEquipmentDO patrolPlanEquipmentDO = Mapper.selectById(updateReqVO.getId());
        if (patrolPlanEquipmentDO.getStatus().equals("0")) {
            throw new ServiceException(406, "请开启计划后再进行编辑");
        }
        isCheckPlanName(updateReqVO.getApplication(),updateReqVO.getPlanName(), updateReqVO.getId());

        //判断执行周期是否修改
        //当执行周期发生改变后新增任务表和日志表数据
        //当执行周期不发生改变后更新基础数据

        //getPlanRule
        //true需要重新计算时间false不需要重新计算时间
        Boolean isCreate = PatrolComputingTime.getIsCreate(patrolPlanEquipmentDO.getPlanRule(), updateReqVO.getPlanRule());


        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        PatrolPlanEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, PatrolPlanEquipmentDO.class);

        if (isCreate) {
            //计算时间【当前执行时间】
            Date nextTime = PatrolComputingTime.getNextTime(updateObj.getPlanRule(), updateObj.getStartDate(), "0");
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

        savePatrolPlan(updateReqVO, updateObj.getId());

        String planRule = updateObj.getPlanRule();
        JSONObject jsonObject = JSON.parseObject(planRule);
        String taskKey = jsonObject.getString("key");

        LambdaQueryWrapperX<PatrolTaskEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolTaskEquipmentDO::getTaskKey, taskKey);
        PatrolTaskEquipmentDO patrolTaskEquipmentDO = patrolTaskEquipmentMapper.selectOne(queryWrapperX);
        if (patrolTaskEquipmentDO != null) {
            patrolTaskEquipmentDO.setTaskName(updateObj.getPlanName());
            if (isCreate) {
                //第一次执行时间
                patrolTaskEquipmentDO.setShouldTime(updateObj.getNextTime());
            }
            //计算任务发送时间
            Date remindTime = PatrolComputingTime.getRemindTime(updateObj.getRemindJson(), updateObj.getNextTime());
            patrolTaskEquipmentDO.setRemindTime(remindTime);
            patrolTaskEquipmentDO.setApplication(updateReqVO.getApplication());
            patrolTaskEquipmentDO.setIsRemind("0");
            patrolTaskEquipmentDO.setDepartmentId(updateObj.getDepartmentId());
            if (StringUtils.isNotEmpty(updateObj.getDepartmentParentId())) {
                patrolTaskEquipmentDO.setDepartmentParentId(updateObj.getDepartmentParentId());
            }

            //计算超时时间
            getTimeoutTime(patrolTaskEquipmentDO, updateObj);
            List<String> deptIdList = Lists.newArrayList();
            deptIdList.add(String.valueOf(patrolPlanEquipmentDO.getDepartmentId()));
            if (StringUtils.isNotEmpty(patrolPlanEquipmentDO.getDepartmentParentId())) {
                deptIdList.addAll(List.of(patrolPlanEquipmentDO.getDepartmentParentId().split(",")));
            }

            List<String> postIdList = Lists.newArrayList();
            postIdList.add(String.valueOf(patrolPlanEquipmentDO.getStationId()));
            if (StringUtils.isNotEmpty(updateObj.getStationParentId())) {
                patrolTaskEquipmentDO.setStationParentId(updateObj.getStationParentId());

                postIdList.addAll(List.of(patrolPlanEquipmentDO.getStationParentId().split(",")));
            }


            String postUids = systemUserMapper.getByDeptPostIdListId(deptIdList, postIdList);
            if (StringUtils.isEmpty(postUids)) {
                throw new ServiceException(406, "当前所选岗位和部门下暂无可巡检人员");
            }
            patrolTaskEquipmentDO.setStationId(updateObj.getStationId());

            patrolTaskEquipmentDO.setPostUids(postUids);

            patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
        } else {
            patrolTaskEquipmentDO = new PatrolTaskEquipmentDO();
            patrolTaskEquipmentDO.setApplication(updateReqVO.getApplication());
            patrolTaskEquipmentDO.setTaskName(updateObj.getPlanName());
            //第一次执行时间
            patrolTaskEquipmentDO.setShouldTime(updateObj.getNextTime());
            //计算任务发送时间
            Date remindTime = PatrolComputingTime.getRemindTime(updateObj.getRemindJson(), updateObj.getNextTime());
            patrolTaskEquipmentDO.setRemindTime(remindTime);
            patrolTaskEquipmentDO.setIsRemind("0");
            patrolTaskEquipmentDO.setStationId(updateObj.getStationId());
            patrolTaskEquipmentDO.setDepartmentId(updateObj.getDepartmentId());
            //计算超时时间
            getTimeoutTime(patrolTaskEquipmentDO, updateObj);
            //patrolTaskEquipmentDO.setId(null);
            patrolTaskEquipmentMapper.insert(patrolTaskEquipmentDO);
        }


        if (isCreate) {
            //新增日志
            List<PatrolTaskEquipmentLogDO> taskEquipmentLog = PatrolTaskEquipmentLogData.getTaskEquipmentLog(updateObj, patrolTaskEquipmentDO, "0");
            patrolTaskEquipmentLogMapper.insertBatch(taskEquipmentLog);
        }
    }


    /**
     * 计算超时时间
     *
     * @param patrolTaskEquipmentDO
     * @param patrolPlanEquipmentDO
     */
    private static void getTimeoutTime(PatrolTaskEquipmentDO patrolTaskEquipmentDO, PatrolPlanEquipmentDO patrolPlanEquipmentDO) {
        Date nextTime = patrolPlanEquipmentDO.getNextTime();
        Integer timeLimit = patrolPlanEquipmentDO.getTimeLimit();
        patrolTaskEquipmentDO.setTimeoutTime(DateUtils.addHourMillis(nextTime, timeLimit));
    }


    private void savePatrolPlan(PatrolPlanEquipmentSaveReqVO createReqVO, Long id) {
        List<PatrolPlanPositionSaveReqVO> planPositionList = createReqVO.getPlanPositionList();
        patrolPlanPositionService.delete(id);
        if (CollectionUtils.isNotEmpty(planPositionList)) {
            planPositionList.forEach(patrolPlanPositionSaveReqVO -> {
                if (patrolPlanPositionSaveReqVO.getPositionId() == null) {
                    throw new ServiceException(406, "巡检点id不为空");
                }
                patrolPlanPositionSaveReqVO.setPlanId(id);
                patrolPlanPositionService.create(patrolPlanPositionSaveReqVO);
            });
        }
    }

    private void isCheckPlanName(String application,String planName, Long id) {
        LambdaQueryWrapperX<PatrolPlanEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolPlanEquipmentDO::getPlanName, planName);
        queryWrapperX.eq(PatrolPlanEquipmentDO::getApplication, application);
        queryWrapperX.apply("id !='" + id + "'");
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "当前计划名称已存在,请勿重复添加");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除
        Mapper.deleteById(id);

        LambdaQueryWrapperX<PatrolTaskEquipmentDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolTaskEquipmentDO::getPlanId,id);
        patrolTaskEquipmentMapper.delete(queryWrapperX);

        LambdaQueryWrapperX<PatrolTaskEquipmentLogDO>queryWrapperX1=new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(PatrolTaskEquipmentLogDO::getPlanId,id);
        patrolTaskEquipmentLogMapper.delete(queryWrapperX1);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(PATROL_PLAN_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public PatrolPlanEquipmentRespVO get(Long id) {
        PatrolPlanEquipmentDO patrolPlanEquipmentDO = Mapper.selectById(id);
        PatrolPlanEquipmentRespVO planEquipmentRespVO = BeanUtils.toBean(patrolPlanEquipmentDO, PatrolPlanEquipmentRespVO.class);
        List<PatrolPlanPositionSaveReqVO> list = patrolPlanPositionService.getList(new PatrolPlanPositionPageReqVO().setPlanId(id));
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(patrolPlanPositionSaveReqVO -> {
                PatrolPositionDO patrolPositionDO = patrolPositionService.get(patrolPlanPositionSaveReqVO.getPositionId());
                if (patrolPositionDO != null) {
                    patrolPlanPositionSaveReqVO.setNumber(patrolPositionDO.getNumber());
                    patrolPlanPositionSaveReqVO.setName(patrolPositionDO.getName());
                    PropertyLocationDO propertyLocationDO = propertyLocationMapper.selectById(patrolPositionDO.getPositionId());
                    if (propertyLocationDO != null) {
                        patrolPlanPositionSaveReqVO.setPositionName(propertyLocationDO.getName());
                    }
                }
            });
            planEquipmentRespVO.setPlanPositionList(list);
        }


        List<String> deptIdList = Lists.newArrayList();
        deptIdList.add(String.valueOf(planEquipmentRespVO.getDepartmentId()));
        if (StringUtils.isNotEmpty(planEquipmentRespVO.getDepartmentParentId())) {
            deptIdList.addAll(List.of(planEquipmentRespVO.getDepartmentParentId().split(",")));
        }

        List<String> postIdList = Lists.newArrayList();
        postIdList.add(String.valueOf(planEquipmentRespVO.getStationId()));
        if (StringUtils.isNotEmpty(planEquipmentRespVO.getStationParentId())) {
            postIdList.addAll(List.of(planEquipmentRespVO.getStationParentId().split(",")));
        }
        List<DeptPostIdUserListVO> postIdUserList = systemUserMapper.getDeptPostIdUserList(null, postIdList);
        if (CollectionUtils.isNotEmpty(postIdUserList)) {
            planEquipmentRespVO.setUserList(postIdUserList);
        }
        List<DeptPostIdUserListVO> deptIdUserList = systemUserMapper.getDeptPostIdUserList(deptIdList, null);
        if (CollectionUtils.isNotEmpty(deptIdUserList)) {
            if (CollectionUtils.isNotEmpty(planEquipmentRespVO.getUserList())) {
                List<DeptPostIdUserListVO> userList = planEquipmentRespVO.getUserList();
                userList.addAll(deptIdUserList);
                planEquipmentRespVO.setUserList(userList);
            } else {
                planEquipmentRespVO.setUserList(deptIdUserList);
            }

        }

        return planEquipmentRespVO;
    }

    @Override
    public PageResult<PatrolPlanEquipmentRespVO> getPage(PatrolPlanEquipmentPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PatrolPlanEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolPlanEquipmentDO::getApplication, pageReqVO.getApplication());
        if (StringUtils.isNotEmpty(pageReqVO.getPlanName())) {
            queryWrapperX.like(PatrolPlanEquipmentDO::getPlanName, pageReqVO.getPlanName());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getCreator())) {
            queryWrapperX.eq(PatrolPlanEquipmentDO::getCreator, pageReqVO.getCreator());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getPlanStatus())) {
            queryWrapperX.eq(PatrolPlanEquipmentDO::getPlanStatus, pageReqVO.getPlanStatus());
        }

        if (pageReqVO.getLastTime() != null) {
            queryWrapperX.apply("DATE_FORMAT(last_time,'%Y-%m-%d')='DATE_FORMAT('" + pageReqVO.getLastTime() + "','%Y-%m-%d')");
        }

        PageResult<PatrolPlanEquipmentDO> patrolPlanEquipmentDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<PatrolPlanEquipmentRespVO> respVOPageResult = BeanUtils.toBean(patrolPlanEquipmentDOPageResult, PatrolPlanEquipmentRespVO.class);
        List<PatrolPlanEquipmentRespVO> list = respVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(patrolPlanEquipmentRespVO -> {
                getStationName(patrolPlanEquipmentRespVO);

                getDeptName(patrolPlanEquipmentRespVO);

                String dateFlag = patrolPlanEquipmentRespVO.getDateFlag();
                if (StringUtils.equals(dateFlag, "1")) {
                    patrolPlanEquipmentRespVO.setDateFlag("永久");
                } else {
                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                    patrolPlanEquipmentRespVO.setDateFlag(sim.format(patrolPlanEquipmentRespVO.getEndDate()));
                }

                String creator = patrolPlanEquipmentRespVO.getCreator();
                String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                patrolPlanEquipmentRespVO.setCreator(userName);

                String planRule = patrolPlanEquipmentRespVO.getPlanRule();
                JSONObject jsonObject = JSON.parseObject(planRule);
                patrolPlanEquipmentRespVO.setPlanRule(jsonObject.getString("cycleTxt"));

                String planStatus = patrolPlanEquipmentRespVO.getPlanStatus();
                String dataLabel = DictFrameworkUtils.getDictDataLabel("PLAN_STATUS", planStatus);
                patrolPlanEquipmentRespVO.setPlanStatus(dataLabel);

                patrolPlanEquipmentRespVO.setStatusName(patrolPlanEquipmentRespVO.getStatus().equals("1") ? "开启" : "关闭");
            });
        }
        return respVOPageResult;
    }

    private void getDeptName(PatrolPlanEquipmentRespVO patrolPlanEquipmentRespVO) {
        Long departmentId = patrolPlanEquipmentRespVO.getDepartmentId();
        CommonResult<DeptRespDTO> deptResult = deptApi.getDept(departmentId);
        if (deptResult.getCode() == 0) {
            DeptRespDTO dept = deptResult.getData();
            if (dept != null) {
                patrolPlanEquipmentRespVO.setStationDepartmentName(patrolPlanEquipmentRespVO.getStationDepartmentName() + "-" + dept.getName());
            }
        }
    }

    private void getStationName(PatrolPlanEquipmentRespVO patrolPlanEquipmentRespVO) {
        Long stationId = patrolPlanEquipmentRespVO.getStationId();
        CommonResult<List<PostRespDTO>> postResult = postApi.getPostList(Arrays.asList(stationId));
        if (postResult.getCode() == 0) {
            List<PostRespDTO> postList = postResult.getData();
            if (CollectionUtils.isNotEmpty(postList)) {
                String name = postList.get(0).getName();
                patrolPlanEquipmentRespVO.setStationDepartmentName(name);
            }
        }
    }

    @Override
    public void updateStatus(Long id, String status) {
        Mapper.updateById(new PatrolPlanEquipmentDO().setId(id).setStatus(status));
        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = patrolTaskEquipmentMapper.selectList(new LambdaQueryWrapperX<PatrolTaskEquipmentDO>().eq(PatrolTaskEquipmentDO::getPlanId, id));
        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {
            patrolTaskEquipmentList.forEach(patrolTaskEquipmentDO -> {
                patrolTaskEquipmentDO.setHasLoop("1");
                patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
            });
        }

        if (StringUtils.equals(status, "1")) {
            patrolTaskEquipmentList.forEach(patrolTaskEquipmentDO -> {
                PatrolPlanEquipmentDO patrolPlanEquipmentDO = Mapper.selectById(patrolTaskEquipmentDO.getPlanId());
                //启用
                //判断任务是否执行到最后一个
                LambdaQueryWrapperX<PatrolTaskEquipmentLogDO> patrolTaskLogEquipmentWrapper = new LambdaQueryWrapperX<>();
                patrolTaskLogEquipmentWrapper.eq(PatrolTaskEquipmentLogDO::getPlanKey, patrolTaskEquipmentDO);
                patrolTaskLogEquipmentWrapper.eq(PatrolTaskEquipmentLogDO::getStatus, "0");
                patrolTaskLogEquipmentWrapper.orderByAsc(PatrolTaskEquipmentLogDO::getSort);
                List<PatrolTaskEquipmentLogDO> patrolTaskEquipmentLogList = patrolTaskEquipmentLogMapper.selectList(patrolTaskLogEquipmentWrapper);
                if (CollectionUtils.isNotEmpty(patrolTaskEquipmentLogList)) {
                    patrolTaskEquipmentLogList.forEach(patrolTaskEquipmentLogDO -> {
                        Date taskTime = patrolTaskEquipmentLogDO.getTaskTime();
                        int i = taskTime.compareTo(new Date());
                        if (i < 0) {
                            patrolTaskEquipmentLogDO.setStatus("1");
                            patrolTaskEquipmentLogMapper.updateById(patrolTaskEquipmentLogDO);
                        }
                    });
                }


                List<PatrolTaskEquipmentLogDO> patrolTaskEquipmentLogList1 = patrolTaskEquipmentLogMapper.selectList(new LambdaQueryWrapperX<PatrolTaskEquipmentLogDO>().eq(PatrolTaskEquipmentLogDO::getPlanKey, patrolTaskEquipmentDO.getTaskKey()).orderByAsc(PatrolTaskEquipmentLogDO::getSort));
                if (CollectionUtils.isNotEmpty(patrolTaskEquipmentLogList1)) {
                    //更新巡检计划的最近执行时间和下次执行时间
                    PatrolTaskEquipmentLogDO patrolTaskEquipmentLogDO = patrolTaskEquipmentLogList1.get(patrolTaskEquipmentLogList1.size() - 1);
                    Date taskTime = patrolTaskEquipmentLogDO.getTaskTime();
                    int i = taskTime.compareTo(new Date());
                    if (i > 0) {
                        try {
                            List<PatrolTaskEquipmentLogDO> taskEquipmentLog = PatrolTaskEquipmentLogData.getTaskEquipmentLog(patrolPlanEquipmentDO, patrolTaskEquipmentDO, "1");
                            patrolTaskEquipmentLogMapper.insertBatch(taskEquipmentLog);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }

    }

    /**
     *
     * @param application
     * @return
     */
    @Override
    public Map<String, Object> getTopCount(String application) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        LambdaQueryWrapperX<PatrolPlanEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolPlanEquipmentDO::getApplication, application);
        List<PatrolPlanEquipmentDO> patrolPlanEquipmentDOS = Mapper.selectList(queryWrapperX);
        //失效计划
        Integer planLoseNums = 0;
        //生效计划
        Integer planingNums = 0;
        //本周完成数量
        Integer planCompleteNums = 0;
        if (CollectionUtils.isNotEmpty(patrolPlanEquipmentDOS)) {
            //生效计划
            planingNums = patrolPlanEquipmentDOS.stream().filter(aa -> {
                try {
                    String dateFlag = aa.getDateFlag();
                    if (dateFlag.equals("1")) {
                        Date endDate = sim.parse("2100-01-01");
                        aa.setEndDate(endDate);
                    }
                    return aa.getPlanStatus().equals("1") && sim.parse(sim.format(aa.getEndDate())).compareTo(sim.parse(sim.format(new Date()))) >= 0;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()).size();


            //失效计划
            planLoseNums = patrolPlanEquipmentDOS.stream().filter(aa -> {
                try {
                    String dateFlag = aa.getDateFlag();
                    if (dateFlag.equals("1")) {
                        Date endDate = sim.parse("2100-01-01");
                        aa.setEndDate(endDate);
                    }
                    return aa.getPlanStatus().equals("1") && sim.parse(sim.format(aa.getEndDate())).compareTo(sim.parse(sim.format(new Date()))) < 0;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()).size();


            //本周完整计划数量
            planCompleteNums = patrolPlanEquipmentDOS.stream().filter(aa -> {
                try {
                    String dateFlag = aa.getDateFlag();
                    if (dateFlag.equals("1")) {
                        Date endDate = sim.parse("2100-01-01");
                        aa.setEndDate(endDate);
                    }
                    return aa.getPlanStatus().equals("3") && DateUtils.isThisWeek(sim.parse(sim.format(aa.getEndDate())));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()).size();
        }
        //生效计划
        map.put("planingNums", planingNums);
        //失效计划
        map.put("planLoseNums", planLoseNums);
        //本周完成任务数量
        map.put("planCompleteNums", planCompleteNums);

        return map;
    }


}