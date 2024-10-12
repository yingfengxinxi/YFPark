package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryLogDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyInventoryLogMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产盘点操作日志 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyInventoryLogServiceImpl implements PropertyInventoryLogService {

    @Resource
    private PropertyInventoryLogMapper propertyInventoryLogMapper;

    @Override
    public Long createPropertyInventoryLog(PropertyInventoryLogSaveReqVO createReqVO) {
        // 插入
        PropertyInventoryLogDO propertyInventoryLog = BeanUtils.toBean(createReqVO, PropertyInventoryLogDO.class);
        propertyInventoryLogMapper.insert(propertyInventoryLog);
        // 返回
        return propertyInventoryLog.getId();
    }

    @Override
    public void updatePropertyInventoryLog(PropertyInventoryLogSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyInventoryLogExists(updateReqVO.getId());
        // 更新
        PropertyInventoryLogDO updateObj = BeanUtils.toBean(updateReqVO, PropertyInventoryLogDO.class);
        propertyInventoryLogMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyInventoryLog(Long id) {
        // 校验存在
        validatePropertyInventoryLogExists(id);
        // 删除
        propertyInventoryLogMapper.deleteById(id);
    }

    private void validatePropertyInventoryLogExists(Long id) {
        if (propertyInventoryLogMapper.selectById(id) == null) {
            throw exception(PROPERTY_INVENTORY_LOG_NOT_EXISTS);
        }
    }

    @Override
    public PropertyInventoryLogDO getPropertyInventoryLog(Long id) {
        return propertyInventoryLogMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyInventoryLogDO> getPropertyInventoryLogPage(PropertyInventoryLogPageReqVO pageReqVO) {
        return propertyInventoryLogMapper.selectPage(pageReqVO);
    }

}