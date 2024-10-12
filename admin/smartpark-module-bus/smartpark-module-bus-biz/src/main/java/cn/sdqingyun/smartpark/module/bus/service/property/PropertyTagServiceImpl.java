package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyTagDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyTagMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产标签模板 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyTagServiceImpl implements PropertyTagService {

    @Resource
    private PropertyTagMapper propertyTagMapper;

    @Override
    public Long createPropertyTag(PropertyTagSaveReqVO createReqVO) {
        // 插入
        PropertyTagDO propertyTag = BeanUtils.toBean(createReqVO, PropertyTagDO.class);

        if(propertyTag.getId() == null){
            propertyTagMapper.insert(propertyTag);
        }else {
            propertyTagMapper.updateById(propertyTag);
        }

        // 返回
        return propertyTag.getId();
    }

    @Override
    public void updatePropertyTag(PropertyTagSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyTagExists(updateReqVO.getId());
        // 更新
        PropertyTagDO updateObj = BeanUtils.toBean(updateReqVO, PropertyTagDO.class);
        propertyTagMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyTag(Long id) {
        // 校验存在
        validatePropertyTagExists(id);
        // 删除
        propertyTagMapper.deleteById(id);
    }

    private void validatePropertyTagExists(Long id) {
        if (propertyTagMapper.selectById(id) == null) {
            throw exception(PROPERTY_TAG_NOT_EXISTS);
        }
    }

    @Override
    public PropertyTagDO getPropertyTag(Long id) {
        return propertyTagMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyTagDO> getPropertyTagPage(PropertyTagPageReqVO pageReqVO) {
        return propertyTagMapper.selectPage(pageReqVO);
    }

}