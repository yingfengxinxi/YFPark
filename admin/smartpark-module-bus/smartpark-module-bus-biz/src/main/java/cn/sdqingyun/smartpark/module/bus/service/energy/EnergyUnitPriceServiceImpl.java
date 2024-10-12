package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyUnitPriceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyUnitPriceDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyUnitPriceMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 自定义价格标准表-阶梯单价 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyUnitPriceServiceImpl implements EnergyUnitPriceService {

    @Resource
    private EnergyUnitPriceMapper Mapper;

    @Override
    public Long create(EnergyUnitPriceSaveReqVO createReqVO) {
        // 插入
        EnergyUnitPriceDO energyUnitPriceDO = BeanUtils.toBean(createReqVO, EnergyUnitPriceDO.class);
        Mapper.insert(energyUnitPriceDO);
        // 返回
        return energyUnitPriceDO.getId();
    }

    @Override
    public void update(EnergyUnitPriceSaveReqVO updateReqVO) {

        // 更新
        EnergyUnitPriceDO updateObj = BeanUtils.toBean(updateReqVO, EnergyUnitPriceDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyUnitPriceDO get(Long id) {
        return Mapper.selectById(id);
    }



}