package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyRoomTaskMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomTaskPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomTaskSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRoomTaskDO;

/**
 * 房间对应抄表任务 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyRoomTaskServiceImpl implements EnergyRoomTaskService {

    @Resource
    private EnergyRoomTaskMapper Mapper;

    @Override
    public Long create(EnergyRoomTaskSaveReqVO createReqVO) {
        // 插入
        EnergyRoomTaskDO energyRoomTaskDO = BeanUtils.toBean(createReqVO, EnergyRoomTaskDO.class);
        Mapper.insert(energyRoomTaskDO);
        // 返回
        return energyRoomTaskDO.getId();
    }

    @Override
    public void update(EnergyRoomTaskSaveReqVO updateReqVO) {

        // 更新
        EnergyRoomTaskDO updateObj = BeanUtils.toBean(updateReqVO, EnergyRoomTaskDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyRoomTaskDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyRoomTaskDO> getPage(EnergyRoomTaskPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyRoomTaskDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}