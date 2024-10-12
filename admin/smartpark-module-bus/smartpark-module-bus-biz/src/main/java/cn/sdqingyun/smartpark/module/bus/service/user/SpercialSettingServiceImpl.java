package cn.sdqingyun.smartpark.module.bus.service.user;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.user.SpercialSettingDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.user.SpercialSettingMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 机构用户自定义操作配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class SpercialSettingServiceImpl implements SpercialSettingService {

    @Resource
    private SpercialSettingMapper spercialSettingMapper;

    @Override
    public Long createSpercialSetting(SpercialSettingSaveReqVO createReqVO) {
        // 插入
        SpercialSettingDO spercialSetting = BeanUtils.toBean(createReqVO, SpercialSettingDO.class);
        spercialSettingMapper.insert(spercialSetting);
        // 返回
        return spercialSetting.getId();
    }

    @Override
    public void updateSpercialSetting(SpercialSettingSaveReqVO updateReqVO) {
        // 校验存在
        validateSpercialSettingExists(updateReqVO.getId());
        // 更新
        SpercialSettingDO updateObj = BeanUtils.toBean(updateReqVO, SpercialSettingDO.class);
        spercialSettingMapper.updateById(updateObj);
    }

    @Override
    public void deleteSpercialSetting(Long id) {
        // 校验存在
        validateSpercialSettingExists(id);
        // 删除
        spercialSettingMapper.deleteById(id);
    }

    private void validateSpercialSettingExists(Long id) {
        if (spercialSettingMapper.selectById(id) == null) {
            throw exception(SPERCIAL_SETTING_NOT_EXISTS);
        }
    }

    @Override
    public SpercialSettingDO getSpercialSetting(Long id) {
        return spercialSettingMapper.selectById(id);
    }

    @Override
    public PageResult<SpercialSettingDO> getSpercialSettingPage(SpercialSettingPageReqVO pageReqVO) {
        return spercialSettingMapper.selectPage(pageReqVO);
    }

}