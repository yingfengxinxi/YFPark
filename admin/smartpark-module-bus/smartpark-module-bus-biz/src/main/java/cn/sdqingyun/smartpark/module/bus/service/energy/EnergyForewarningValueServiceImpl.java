package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyForewarningValueSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyForewarningValueDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyForewarningValueMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 误抄表预警 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyForewarningValueServiceImpl implements EnergyForewarningValueService {

    @Resource
    private EnergyForewarningValueMapper Mapper;

    /**
     * @param energyForewarningValueSaveReqVO 创建信息
     * @return
     */
    @Override
    public Long save(EnergyForewarningValueSaveReqVO energyForewarningValueSaveReqVO) {
        LambdaQueryWrapperX<EnergyForewarningValueDO> queryWrapperX = new LambdaQueryWrapperX<>();
        EnergyForewarningValueDO energyForewarningValueOld = Mapper.selectOne(queryWrapperX);
        if (energyForewarningValueOld != null) {
            energyForewarningValueOld.setValue(energyForewarningValueSaveReqVO.getValue());
            energyForewarningValueSaveReqVO.setId(energyForewarningValueOld.getId());
            Mapper.updateById(energyForewarningValueOld);
        } else {
            // 插入
            EnergyForewarningValueDO energyForewarningValueDO = BeanUtils.toBean(energyForewarningValueSaveReqVO, EnergyForewarningValueDO.class);
            Mapper.insert(energyForewarningValueDO);
        }

        // 返回
        return energyForewarningValueSaveReqVO.getId();
    }


    @Override
    public EnergyForewarningValueDO get() {
        LambdaQueryWrapperX<EnergyForewarningValueDO> queryWrapperX = new LambdaQueryWrapperX<>();

        return Mapper.selectOne(queryWrapperX);
    }


}