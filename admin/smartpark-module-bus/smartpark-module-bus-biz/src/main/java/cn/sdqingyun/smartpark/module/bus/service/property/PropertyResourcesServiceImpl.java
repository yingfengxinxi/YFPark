package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyResourcesDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyResourcesMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产静态资源管理 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyResourcesServiceImpl implements PropertyResourcesService {

    @Resource
    private PropertyResourcesMapper propertyResourcesMapper;

    @Override
    public Long createPropertyResources(PropertyResourcesSaveReqVO createReqVO) {
        // 插入
        PropertyResourcesDO propertyResources = BeanUtils.toBean(createReqVO, PropertyResourcesDO.class);
        propertyResourcesMapper.insert(propertyResources);
        // 返回
        return propertyResources.getId();
    }

    @Override
    public void updatePropertyResources(PropertyResourcesSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyResourcesExists(updateReqVO.getId());
        // 更新
        PropertyResourcesDO updateObj = BeanUtils.toBean(updateReqVO, PropertyResourcesDO.class);
        propertyResourcesMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyResources(Long id) {
        // 校验存在
        validatePropertyResourcesExists(id);
        // 删除
        propertyResourcesMapper.deleteById(id);
    }

    private void validatePropertyResourcesExists(Long id) {
        if (propertyResourcesMapper.selectById(id) == null) {
            throw exception(PROPERTY_RESOURCES_NOT_EXISTS);
        }
    }

    @Override
    public PropertyResourcesDO getPropertyResources(Long id) {
        return propertyResourcesMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyResourcesDO> getPropertyResourcesPage(PropertyResourcesPageReqVO pageReqVO) {
        return propertyResourcesMapper.selectPage(pageReqVO);
    }

}