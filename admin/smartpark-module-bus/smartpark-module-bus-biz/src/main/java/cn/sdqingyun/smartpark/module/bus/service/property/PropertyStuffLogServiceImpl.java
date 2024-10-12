package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffLogDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffLogMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产耗材业务记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffLogServiceImpl implements PropertyStuffLogService {

    @Resource
    private PropertyStuffLogMapper propertyStuffLogMapper;

    @Override
    public Long createPropertyStuffLog(PropertyStuffLogSaveReqVO createReqVO) {
        // 插入
        PropertyStuffLogDO propertyStuffLog = BeanUtils.toBean(createReqVO, PropertyStuffLogDO.class);
        propertyStuffLogMapper.insert(propertyStuffLog);
        // 返回
        return propertyStuffLog.getId();
    }

    @Override
    public void updatePropertyStuffLog(PropertyStuffLogSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffLogExists(updateReqVO.getId());
        // 更新
        PropertyStuffLogDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffLogDO.class);
        propertyStuffLogMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffLog(Long id) {
        // 校验存在
        validatePropertyStuffLogExists(id);
        // 删除
        propertyStuffLogMapper.deleteById(id);
    }

    private void validatePropertyStuffLogExists(Long id) {
        if (propertyStuffLogMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_LOG_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffLogDO getPropertyStuffLog(Long id) {
        return propertyStuffLogMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffLogDO> getPropertyStuffLogPage(PropertyStuffLogPageReqVO pageReqVO) {
        return propertyStuffLogMapper.selectPage(pageReqVO);
    }

}