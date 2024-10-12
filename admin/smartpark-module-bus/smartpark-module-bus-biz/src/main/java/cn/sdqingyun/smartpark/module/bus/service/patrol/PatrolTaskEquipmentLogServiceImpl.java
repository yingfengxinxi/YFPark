package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTaskEquipmentLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTaskEquipmentLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolTaskEquipmentLogMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PATROL_TASK_EQUIPMENT_LOG_NOT_EXISTS;

/**
 * 应用巡检任务日志 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolTaskEquipmentLogServiceImpl implements PatrolTaskEquipmentLogService {

    @Resource
    private PatrolTaskEquipmentLogMapper Mapper;

    @Override
    public Long create(PatrolTaskEquipmentLogSaveReqVO createReqVO) {
        // 插入
        PatrolTaskEquipmentLogDO patrolTaskEquipmentLogDO = BeanUtils.toBean(createReqVO, PatrolTaskEquipmentLogDO.class);
        Mapper.insert(patrolTaskEquipmentLogDO);
        // 返回
        return patrolTaskEquipmentLogDO.getId();
    }

    @Override
    public void update(PatrolTaskEquipmentLogSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        PatrolTaskEquipmentLogDO updateObj = BeanUtils.toBean(updateReqVO, PatrolTaskEquipmentLogDO.class);
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
            throw exception(PATROL_TASK_EQUIPMENT_LOG_NOT_EXISTS);
        }
    }

    @Override
    public PatrolTaskEquipmentLogDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<PatrolTaskEquipmentLogDO> getPage(PatrolTaskEquipmentLogPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PatrolTaskEquipmentLogDO> queryWrapperX = new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

}