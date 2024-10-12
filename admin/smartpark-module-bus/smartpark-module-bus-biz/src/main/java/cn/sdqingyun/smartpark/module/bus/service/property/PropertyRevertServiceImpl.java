package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRevertDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyRevertMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产归还 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyRevertServiceImpl implements PropertyRevertService {

    @Resource
    private PropertyRevertMapper propertyRevertMapper;

    @Override
    public Long createPropertyRevert(PropertyRevertSaveReqVO createReqVO) {
        // 插入
        PropertyRevertDO propertyRevert = BeanUtils.toBean(createReqVO, PropertyRevertDO.class);
        propertyRevertMapper.insert(propertyRevert);
        // 返回
        return propertyRevert.getId();
    }

    @Override
    public void updatePropertyRevert(PropertyRevertSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyRevertExists(updateReqVO.getId());
        // 更新
        PropertyRevertDO updateObj = BeanUtils.toBean(updateReqVO, PropertyRevertDO.class);
        propertyRevertMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyRevert(Long id) {
        // 校验存在
        validatePropertyRevertExists(id);
        // 删除
        propertyRevertMapper.deleteById(id);
    }

    private void validatePropertyRevertExists(Long id) {
        if (propertyRevertMapper.selectById(id) == null) {
            throw exception(PROPERTY_REVERT_NOT_EXISTS);
        }
    }

    @Override
    public PropertyRevertDO getPropertyRevert(Long id) {
        return propertyRevertMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyRevertDO> getPropertyRevertPage(PropertyRevertPageReqVO pageReqVO) {
        return propertyRevertMapper.selectPage(pageReqVO);
    }

}