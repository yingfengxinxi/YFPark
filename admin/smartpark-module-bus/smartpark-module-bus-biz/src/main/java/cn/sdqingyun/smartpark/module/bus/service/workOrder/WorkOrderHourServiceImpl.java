package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderHourPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderHourRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderHourSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderHourDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderRuleDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderHourMapper;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.PostApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.PostRespDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工单工时配置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderHourServiceImpl implements WorkOrderHourService {

    @Resource
    private WorkOrderHourMapper Mapper;

    @Resource
    private PostApi postApi;

    @Resource
    private DeptApi deptApi;

    @Override
    public Long create(WorkOrderHourSaveReqVO createReqVO) {
        isCheckName(createReqVO);
        updateIsDefault(createReqVO);
        // 插入
        WorkOrderHourDO workOrderHourDO = BeanUtils.toBean(createReqVO, WorkOrderHourDO.class);
        workOrderHourDO.setIsDelete("1");
        Mapper.insert(workOrderHourDO);
        // 返回
        return workOrderHourDO.getId();
    }

    private void updateIsDefault(WorkOrderHourSaveReqVO createReqVO) {
        if (createReqVO.getIsDefault().equals("1")) {
            LambdaQueryWrapperX<WorkOrderHourDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(WorkOrderHourDO::getIsDefault, "1");
            queryWrapperX.eq(WorkOrderHourDO::getApplication, createReqVO.getApplication());
            if (createReqVO.getId() != null) {
                queryWrapperX.apply("id !='" + createReqVO.getId() + "'");
            }
            List<WorkOrderHourDO> workOrderHourList = Mapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(workOrderHourList)) {
                workOrderHourList.forEach(workOrderHourDO -> {
                    workOrderHourDO.setIsDefault("0");
                    Mapper.updateById(workOrderHourDO);
                });
            }
        }
    }

    private void isCheckName(WorkOrderHourSaveReqVO workOrderHourSaveReqVO) {
        LambdaQueryWrapperX<WorkOrderHourDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderHourDO::getName, workOrderHourSaveReqVO.getName());
        queryWrapperX.eq(WorkOrderHourDO::getApplication, workOrderHourSaveReqVO.getApplication());
        if (workOrderHourSaveReqVO.getId() != null) {
            queryWrapperX.apply("id !='" + workOrderHourSaveReqVO.getId() + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "工时名称已存在");
        }
    }

    @Override
    public void update(WorkOrderHourSaveReqVO updateReqVO) {
        isCheckName(updateReqVO);
        updateIsDefault(updateReqVO);
        // 更新
        WorkOrderHourDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderHourDO.class);
        updateObj.setIsDelete("1");
        Mapper.updateById(updateObj);
    }

    @Override
    public void updateStatus(Long id, String status) {

        WorkOrderHourDO workOrderHourDO=new WorkOrderHourDO();
        workOrderHourDO.setId(id);
        workOrderHourDO.setStatus(status);
        Mapper.updateById(workOrderHourDO);
    }

    @Override
    public void delete(Long id) {
        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public WorkOrderHourDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<WorkOrderHourRespVO> getPage(WorkOrderHourPageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderHourDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderHourDO::getApplication, pageReqVO.getApplication());
        queryWrapperX.orderByDesc(WorkOrderHourDO::getCreateTime);
        PageResult<WorkOrderHourDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<WorkOrderHourRespVO> hourRespVOPageResult = BeanUtils.toBean(pageResult, WorkOrderHourRespVO.class);
        List<WorkOrderHourRespVO> list = hourRespVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(workOrderHourRespVO -> {
                getDeptName(workOrderHourRespVO);
                getStationName(workOrderHourRespVO);
            });
        }


        return hourRespVOPageResult;
    }

    @Override
    public List<WorkOrderHourDO> workHourList(String application) {
        LambdaQueryWrapperX<WorkOrderHourDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderHourDO::getApplication,application);
        queryWrapperX.eq(WorkOrderHourDO::getIsDefault,"1");
        return Mapper.selectList(queryWrapperX);
    }


    private void getDeptName(WorkOrderHourRespVO workOrderHourRespVO) {
        Long departmentId = workOrderHourRespVO.getDepartmentId();
        if (departmentId != null) {
            CommonResult<DeptRespDTO> deptResult = deptApi.getDept(departmentId);
            if (deptResult.getCode() == 0) {
                DeptRespDTO dept = deptResult.getData();
                if (dept != null) {
                    workOrderHourRespVO.setDepartmentName(dept.getName());
                }
            }
        } else {
            workOrderHourRespVO.setDepartmentName("全部部门");
        }

    }

    private void getStationName(WorkOrderHourRespVO workOrderHourRespVO) {
        if (StringUtils.isNotEmpty(workOrderHourRespVO.getStationId())) {
            List<String> postIdStrList = List.of(workOrderHourRespVO.getStationId().split(","));
            List<Long> postIdList = Lists.newArrayList();
            postIdStrList.forEach(postId -> postIdList.add(Long.valueOf(postId)));
            StringBuilder sb = new StringBuilder();
            CommonResult<List<PostRespDTO>> postResult = postApi.getPostList(postIdList);
            if (postResult.getCode() == 0) {
                List<PostRespDTO> postList = postResult.getData();
                if (CollectionUtils.isNotEmpty(postList)) {
                    postList.forEach(postRespDTO -> {
                        String name = postRespDTO.getName();
                        sb.append(name).append(",");
                    });
                    String postName = sb.toString();
                    if (StringUtils.isNotEmpty(postName)) {
                        workOrderHourRespVO.setStationName(postName.substring(0, postName.length() - 1));
                    }

                }
            }
        } else {
            workOrderHourRespVO.setStationName("全部岗位");
        }


    }

}