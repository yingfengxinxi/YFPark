package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyCallbackRecordRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyCallbackRecordMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyCallbackRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyCallbackRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyCallbackRecordDO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 终端数据推送记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyCallbackRecordServiceImpl implements EnergyCallbackRecordService {

    @Resource
    private EnergyCallbackRecordMapper Mapper;

    @Resource
    private EnergyMapper energyMapper;

    @Override
    public Long create(EnergyCallbackRecordSaveReqVO createReqVO) {
        // 插入
        EnergyCallbackRecordDO energyCallbackRecordDO = BeanUtils.toBean(createReqVO, EnergyCallbackRecordDO.class);
        Mapper.insert(energyCallbackRecordDO);
        // 返回
        return energyCallbackRecordDO.getId();
    }

    @Override
    public void update(EnergyCallbackRecordSaveReqVO updateReqVO) {

        // 更新
        EnergyCallbackRecordDO updateObj = BeanUtils.toBean(updateReqVO, EnergyCallbackRecordDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyCallbackRecordDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyCallbackRecordRespVO> getPage(EnergyCallbackRecordPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyCallbackRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyCallbackRecordDO::getEnergyId, pageReqVO.getEnergyId());
        queryWrapperX.eq(EnergyCallbackRecordDO::getIsCompleted, "1");
        queryWrapperX.orderByDesc(EnergyCallbackRecordDO::getSaveTime);
        PageResult<EnergyCallbackRecordDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        List<EnergyCallbackRecordDO> callbackRecordDOList = Mapper.selectList(queryWrapperX);
        PageResult<EnergyCallbackRecordRespVO> energyCallbackRecordRespVOPageResult = BeanUtils.toBean(pageResult, EnergyCallbackRecordRespVO.class);
        List<EnergyCallbackRecordRespVO> list = energyCallbackRecordRespVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(energyCallbackRecordRespVO -> {
                energyCallbackRecordRespVO.setIsOffName(
                        energyCallbackRecordRespVO.getIsOff().equals("0") ? "通" : "断");

                Long id = callbackRecordDOList.get(0).getId();
                energyCallbackRecordRespVO.setIsSq(false);
                if (energyCallbackRecordRespVO.getId() == id) {
                    energyCallbackRecordRespVO.setIsSq(true);
                }
            });
        }
        return energyCallbackRecordRespVOPageResult;
    }

    @Override
    public void read(Long energyId) {
        EnergyDO energyDO = energyMapper.selectById(energyId);
        if (StringUtils.isEmpty(energyDO.getDeviceSerial())) {
            throw new ServiceException(406, "设备不存在");
        }
        EnergyCallbackRecordDO energyCallbackRecordDO = new EnergyCallbackRecordDO();
        energyCallbackRecordDO.setEnergyId(energyId);
        energyCallbackRecordDO.setEquipNumber(energyDO.getDeviceSerial());
        energyCallbackRecordDO.setEquipCode(energyDO.getDeviceType());
        Random random = new Random();
        energyCallbackRecordDO.setReading(new BigDecimal(String.valueOf(random.nextDouble(200))));
        energyCallbackRecordDO.setRemaining(new BigDecimal(String.valueOf(random.nextDouble(200))));
        energyCallbackRecordDO.setBattery(new BigDecimal(String.valueOf(random.nextDouble(100))));
        energyCallbackRecordDO.setPercent(random.nextInt(100));
        energyCallbackRecordDO.setSaveTime(new Date());
        energyCallbackRecordDO.setSaveDate(DateUtils.getDayTime());
        energyCallbackRecordDO.setResult("");
        energyCallbackRecordDO.setIsOff("0");
        energyCallbackRecordDO.setAbnormalStatus("0");
        energyCallbackRecordDO.setIsCompleted("1");
        Mapper.insert(energyCallbackRecordDO);
    }

    @Override
    public void voidReadRecord(Long id) {

    }

}