package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyTaskLogMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 自定义抄表任务日志 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyTaskLogServiceImpl implements EnergyTaskLogService {

    @Resource
    private EnergyTaskLogMapper Mapper;

    @Override
    public Long create(EnergyTaskLogSaveReqVO createReqVO) {
        // 插入
        EnergyTaskLogDO energyTaskLogDO  = BeanUtils.toBean(createReqVO, EnergyTaskLogDO.class);
        Mapper.insert(energyTaskLogDO);
        // 返回
        return energyTaskLogDO.getId();
    }

    @Override
    public void update(EnergyTaskLogSaveReqVO updateReqVO) {

        // 更新
        EnergyTaskLogDO updateObj = BeanUtils.toBean(updateReqVO, EnergyTaskLogDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }

    @Override
    public EnergyTaskLogDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyTaskLogDO> getPage(EnergyTaskLogPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyTaskLogDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}