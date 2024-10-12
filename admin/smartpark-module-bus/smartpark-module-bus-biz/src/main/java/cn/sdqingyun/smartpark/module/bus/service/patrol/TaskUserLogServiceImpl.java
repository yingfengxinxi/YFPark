package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.TaskUserLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.TaskUserLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.TaskUserLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.TaskUserLogMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 应用巡检任务变更执行人日志 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class TaskUserLogServiceImpl implements TaskUserLogService {

    @Resource
    private TaskUserLogMapper Mapper;

    @Override
    public Long create(TaskUserLogSaveReqVO createReqVO) {
        // 插入
        TaskUserLogDO taskUserLogDO = BeanUtils.toBean(createReqVO, TaskUserLogDO.class);
        Mapper.insert(taskUserLogDO);
        // 返回
        return taskUserLogDO.getId();
    }

    @Override
    public void update(TaskUserLogSaveReqVO updateReqVO) {

        // 更新
        TaskUserLogDO updateObj = BeanUtils.toBean(updateReqVO, TaskUserLogDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public TaskUserLogDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<TaskUserLogDO> getPage(TaskUserLogPageReqVO pageReqVO) {
        LambdaQueryWrapperX<TaskUserLogDO> queryWrapperX = new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

}