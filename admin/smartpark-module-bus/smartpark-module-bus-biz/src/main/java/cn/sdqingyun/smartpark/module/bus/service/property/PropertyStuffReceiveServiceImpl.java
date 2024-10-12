package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffReceiveDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffReceiveMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 耗材业务领用 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffReceiveServiceImpl implements PropertyStuffReceiveService {

    @Resource
    private PropertyStuffReceiveMapper propertyStuffReceiveMapper;

    @Override
    public Long createPropertyStuffReceive(PropertyStuffReceiveSaveReqVO createReqVO) {
        // 插入
        PropertyStuffReceiveDO propertyStuffReceive = BeanUtils.toBean(createReqVO, PropertyStuffReceiveDO.class);
        propertyStuffReceiveMapper.insert(propertyStuffReceive);
        // 返回
        return propertyStuffReceive.getId();
    }

    @Override
    public void updatePropertyStuffReceive(PropertyStuffReceiveSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffReceiveExists(updateReqVO.getId());
        // 更新
        PropertyStuffReceiveDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffReceiveDO.class);
        propertyStuffReceiveMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffReceive(Long id) {
        // 校验存在
        validatePropertyStuffReceiveExists(id);
        // 删除
        propertyStuffReceiveMapper.deleteById(id);
    }

    private void validatePropertyStuffReceiveExists(Long id) {
        if (propertyStuffReceiveMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_RECEIVE_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffReceiveDO getPropertyStuffReceive(Long id) {
        return propertyStuffReceiveMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffReceiveDO> getPropertyStuffReceivePage(PropertyStuffReceivePageReqVO pageReqVO) {
        return propertyStuffReceiveMapper.selectPage(pageReqVO);
    }

}