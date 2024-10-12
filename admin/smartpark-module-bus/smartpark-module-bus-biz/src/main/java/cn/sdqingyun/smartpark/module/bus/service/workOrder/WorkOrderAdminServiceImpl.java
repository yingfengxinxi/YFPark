package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import ch.qos.logback.core.util.StringCollectionUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderAdminPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderAdminRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderAdminSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderAdminMapper;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.PostApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.PostRespDTO;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderAdminDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.Arrays;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工单应用管理员信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderAdminServiceImpl implements WorkOrderAdminService {

    @Resource
    private WorkOrderAdminMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private PostApi postApi;

    @Resource
    private DeptApi deptApi;

    @Override
    public Long create(WorkOrderAdminSaveReqVO createReqVO) {
        if (StringUtils.equals(createReqVO.getRole(), "1")) {
            if (createReqVO.getUid() == null) {
                throw new ServiceException(406, "请选择用户信息");
            }
            //校验用户id是否存在
            LambdaQueryWrapperX<WorkOrderAdminDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(WorkOrderAdminDO::getApplication, createReqVO.getApplication());
            queryWrapperX.eq(WorkOrderAdminDO::getUid, createReqVO.getUid());
            int size = Mapper.selectList(queryWrapperX).size();
            if (size >= 1) {
                throw new ServiceException(406, "当前用户已存在，请勿重复添加");
            }
        }

        if (StringUtils.equals(createReqVO.getRole(), "2")) {
            if (StringUtils.isEmpty(createReqVO.getPostId())) {
                throw new ServiceException(406, "请选择岗位");
            }
            String[] postIds = createReqVO.getPostId().split(",");
            for (String postId : postIds) {
                //校验用户id是否存在
                LambdaQueryWrapperX<WorkOrderAdminDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(WorkOrderAdminDO::getApplication, createReqVO.getApplication());
                queryWrapperX.apply("REGEXP_LIKE(post_id, '(^|,)" + postId + "($|,)')");
                int size = Mapper.selectList(queryWrapperX).size();
                if (size >= 1) {
                    throw new ServiceException(406, "当前岗位已存在，请勿重复添加");
                }
            }

        }
        // 插入
        WorkOrderAdminDO workOrderAdminDO = BeanUtils.toBean(createReqVO, WorkOrderAdminDO.class);
        Mapper.insert(workOrderAdminDO);
        // 返回
        return workOrderAdminDO.getId();
    }

    @Override
    public void update(WorkOrderAdminSaveReqVO updateReqVO) {

        // 更新
        WorkOrderAdminDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderAdminDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public WorkOrderAdminDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<WorkOrderAdminRespVO> getPage(WorkOrderAdminPageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderAdminDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderAdminDO::getApplication, pageReqVO.getApplication());
        if (StringUtils.isNotEmpty(pageReqVO.getRole())) {
            queryWrapperX.eq(WorkOrderAdminDO::getRole, pageReqVO.getRole());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(WorkOrderAdminDO::getName, pageReqVO.getName());
        }
        queryWrapperX.orderByDesc(WorkOrderAdminDO::getCreateTime);
        PageResult<WorkOrderAdminDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<WorkOrderAdminRespVO> adminRespVOPageResult = BeanUtils.toBean(pageResult, WorkOrderAdminRespVO.class);
        List<WorkOrderAdminRespVO> list = adminRespVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(workOrderAdminRespVO -> {
                workOrderAdminRespVO.setNumber(0);
                if (workOrderAdminRespVO.getUid() != null) {
                    String userName = systemUserMapper.getByOperatorIdUserName(workOrderAdminRespVO.getUid());
                    workOrderAdminRespVO.setName(userName);
                }

                if (StringUtils.isNotEmpty(workOrderAdminRespVO.getPostId())) {
                    List<Long> postIdList = Lists.newArrayList();
                    postIdList.add(Long.valueOf(workOrderAdminRespVO.getPostId()));
                    CommonResult<List<PostRespDTO>> postList = postApi.getPostList(postIdList);
                    if (postList.getCode() == 0) {
                        List<PostRespDTO> data = postList.getData();
                        if (CollectionUtils.isNotEmpty(data)) {
                            String name = data.get(0).getName();
                            workOrderAdminRespVO.setPostName(name);
                            int size = systemUserMapper.getDeptPostIdUserList(Lists.newArrayList(), Arrays.asList(String.valueOf(data.get(0).getId()))).size();
                            workOrderAdminRespVO.setNumber(size);
                        }

                    }
                }

            });
        }
        return adminRespVOPageResult;
    }

}