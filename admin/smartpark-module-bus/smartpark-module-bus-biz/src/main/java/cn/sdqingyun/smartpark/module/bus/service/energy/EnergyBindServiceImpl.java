package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyBindMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBindPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBindSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyBindDO;

/**
 * 自定义关联分总 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyBindServiceImpl implements EnergyBindService {

    @Resource
    private EnergyBindMapper Mapper;

    @Override
    public Long create(EnergyBindSaveReqVO createReqVO) {
        // 插入
        EnergyBindDO energyBindDO = BeanUtils.toBean(createReqVO, EnergyBindDO.class);
        Mapper.insert(energyBindDO);
        // 返回
        return energyBindDO.getId();
    }

    @Override
    public void update(EnergyBindSaveReqVO updateReqVO) {

        // 更新
        EnergyBindDO updateObj = BeanUtils.toBean(updateReqVO, EnergyBindDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyBindDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyBindDO> getPage(EnergyBindPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyBindDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyBindDO::getEnergyId, pageReqVO.getEnergyId());
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

}