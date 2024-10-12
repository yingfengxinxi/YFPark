package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyPublicRecordMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPublicRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPublicRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPublicRecordDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 分摊记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyPublicRecordServiceImpl implements EnergyPublicRecordService {

    @Resource
    private EnergyPublicRecordMapper Mapper;

    @Override
    public Long create(EnergyPublicRecordSaveReqVO createReqVO) {
        // 插入
        EnergyPublicRecordDO energyPublicRecordDO = BeanUtils.toBean(createReqVO, EnergyPublicRecordDO.class);
        Mapper.insert(energyPublicRecordDO);
        // 返回
        return energyPublicRecordDO.getId();
    }

    @Override
    public void update(EnergyPublicRecordSaveReqVO updateReqVO) {

        // 更新
        EnergyPublicRecordDO updateObj = BeanUtils.toBean(updateReqVO, EnergyPublicRecordDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyPublicRecordDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyPublicRecordDO> getPage(EnergyPublicRecordPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyPublicRecordDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}