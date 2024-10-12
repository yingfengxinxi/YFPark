package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyMaintainSetDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyMaintainSetMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产保养设置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyMaintainSetServiceImpl implements PropertyMaintainSetService {

    @Resource
    private PropertyMaintainSetMapper propertyMaintainSetMapper;

    @Override
    public Long createPropertyMaintainSet(PropertyMaintainSetSaveReqVO createReqVO) {
        // 插入
        PropertyMaintainSetDO propertyMaintainSet = BeanUtils.toBean(createReqVO, PropertyMaintainSetDO.class);
        propertyMaintainSet.setCreatorName( SecurityFrameworkUtils.getLoginUserNickname() );
        propertyMaintainSetMapper.insert(propertyMaintainSet);
        // 返回
        return propertyMaintainSet.getId();
    }

    @Override
    public void updatePropertyMaintainSet(PropertyMaintainSetSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyMaintainSetExists(updateReqVO.getId());
        // 更新
        PropertyMaintainSetDO updateObj = BeanUtils.toBean(updateReqVO, PropertyMaintainSetDO.class);
        propertyMaintainSetMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyMaintainSet(Long id) {
        // 校验存在
        validatePropertyMaintainSetExists(id);
        // 删除
        propertyMaintainSetMapper.deleteById(id);
    }

    private void validatePropertyMaintainSetExists(Long id) {
        if (propertyMaintainSetMapper.selectById(id) == null) {
            throw exception(PROPERTY_MAINTAIN_SET_NOT_EXISTS);
        }
    }

    @Override
    public PropertyMaintainSetDO getPropertyMaintainSet(Long id) {
        return propertyMaintainSetMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyMaintainSetDO> getPropertyMaintainSetPage(PropertyMaintainSetPageReqVO pageReqVO) {
        return propertyMaintainSetMapper.selectPage(pageReqVO);
    }

}