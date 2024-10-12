package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanEquipmentRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanPositionPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanPositionSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanPositionDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolPlanPositionMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 应用巡检计划绑定巡检点 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PatrolPlanPositionServiceImpl implements PatrolPlanPositionService {

    @Resource
    private PatrolPlanPositionMapper Mapper;

    @Override
    public Long create(PatrolPlanPositionSaveReqVO createReqVO) {
        // 插入
        PatrolPlanPositionDO patrolPlanPositionDO = BeanUtils.toBean(createReqVO, PatrolPlanPositionDO.class);
        Mapper.insert(patrolPlanPositionDO);
        // 返回
        return patrolPlanPositionDO.getId();
    }

    @Override
    public void update(PatrolPlanPositionSaveReqVO updateReqVO) {

        // 更新
        PatrolPlanPositionDO updateObj = BeanUtils.toBean(updateReqVO, PatrolPlanPositionDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long planId) {
        // 删除
        LambdaQueryWrapperX<PatrolPlanPositionDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolPlanPositionDO::getPlanId, planId);
        Mapper.delete(queryWrapperX);
    }


    @Override
    public PatrolPlanPositionDO get(Long id) {
        return Mapper.selectById(id);
    }

    /**
     * @param pageReqVO 分页查询
     * @return
     */
    @Override
    public List<PatrolPlanPositionSaveReqVO> getList(PatrolPlanPositionPageReqVO pageReqVO) {
        LambdaQueryWrapperX<PatrolPlanPositionDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(PatrolPlanPositionDO::getPlanId, pageReqVO.getPlanId());
        queryWrapperX.orderByAsc(PatrolPlanPositionDO::getSort);
        List<PatrolPlanPositionDO> patrolPlanPositionDOS = Mapper.selectList(queryWrapperX);
        return BeanUtils.toBean(patrolPlanPositionDOS, PatrolPlanPositionSaveReqVO.class);
    }

}