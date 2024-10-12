package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyTaskRecordMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskRecordDO;

/**
 * 自定义抄表任务记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyTaskRecordServiceImpl implements EnergyTaskRecordService {

    @Resource
    private EnergyTaskRecordMapper Mapper;

    @Override
    public Long create(EnergyTaskRecordSaveReqVO createReqVO) {
        // 插入
        EnergyTaskRecordDO energyTaskRecordDO = BeanUtils.toBean(createReqVO, EnergyTaskRecordDO.class);
        Mapper.insert(energyTaskRecordDO);
        // 返回
        return energyTaskRecordDO.getId();
    }

    @Override
    public void update(EnergyTaskRecordSaveReqVO updateReqVO) {

        // 更新
        EnergyTaskRecordDO updateObj = BeanUtils.toBean(updateReqVO, EnergyTaskRecordDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyTaskRecordDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyTaskRecordDO> getPage(EnergyTaskRecordPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyTaskRecordDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}