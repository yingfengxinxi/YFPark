package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyOperateLogDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyOperateLogMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产操作日志 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyOperateLogServiceImpl implements PropertyOperateLogService {

    @Resource
    private PropertyOperateLogMapper propertyOperateLogMapper;

    @Override
    public Long createPropertyOperateLog(PropertyOperateLogSaveReqVO createReqVO) {
        // 插入
        PropertyOperateLogDO propertyOperateLog = BeanUtils.toBean(createReqVO, PropertyOperateLogDO.class);
        propertyOperateLogMapper.insert(propertyOperateLog);
        // 返回
        return propertyOperateLog.getId();
    }

    @Override
    public void updatePropertyOperateLog(PropertyOperateLogSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyOperateLogExists(updateReqVO.getId());
        // 更新
        PropertyOperateLogDO updateObj = BeanUtils.toBean(updateReqVO, PropertyOperateLogDO.class);
        propertyOperateLogMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyOperateLog(Long id) {
        // 校验存在
        validatePropertyOperateLogExists(id);
        // 删除
        propertyOperateLogMapper.deleteById(id);
    }

    private void validatePropertyOperateLogExists(Long id) {
        if (propertyOperateLogMapper.selectById(id) == null) {
            throw exception(PROPERTY_OPERATE_LOG_NOT_EXISTS);
        }
    }

    @Override
    public PropertyOperateLogDO getPropertyOperateLog(Long id) {
        return propertyOperateLogMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyOperateLogDO> getPropertyOperateLogPage(PropertyOperateLogPageReqVO pageReqVO) {
        return propertyOperateLogMapper.selectPage(pageReqVO);
    }

}