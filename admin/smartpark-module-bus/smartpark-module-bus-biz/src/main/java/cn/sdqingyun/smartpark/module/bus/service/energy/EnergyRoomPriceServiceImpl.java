package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyRoomPriceMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomPricePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyRoomPriceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRoomPriceDO;

/**
 * 房间对应自定义价格 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyRoomPriceServiceImpl implements EnergyRoomPriceService {

    @Resource
    private EnergyRoomPriceMapper Mapper;

    @Override
    public Long create(EnergyRoomPriceSaveReqVO createReqVO) {
        // 插入
        EnergyRoomPriceDO energyRoomPriceDO = BeanUtils.toBean(createReqVO, EnergyRoomPriceDO.class);
        Mapper.insert(energyRoomPriceDO);
        // 返回
        return energyRoomPriceDO.getId();
    }

    @Override
    public void update(EnergyRoomPriceSaveReqVO updateReqVO) {

        // 更新
        EnergyRoomPriceDO updateObj = BeanUtils.toBean(updateReqVO, EnergyRoomPriceDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }

    @Override
    public EnergyRoomPriceDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyRoomPriceDO> getPage(EnergyRoomPricePageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyRoomPriceDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}