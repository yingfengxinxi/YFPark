package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffStockReceiveDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffStockReceiveMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 耗材业务库存领用 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffStockReceiveServiceImpl implements PropertyStuffStockReceiveService {

    @Resource
    private PropertyStuffStockReceiveMapper propertyStuffStockReceiveMapper;

    @Override
    public Long createPropertyStuffStockReceive(PropertyStuffStockReceiveSaveReqVO createReqVO) {
        // 插入
        PropertyStuffStockReceiveDO propertyStuffStockReceive = BeanUtils.toBean(createReqVO, PropertyStuffStockReceiveDO.class);
        propertyStuffStockReceiveMapper.insert(propertyStuffStockReceive);
        // 返回
        return propertyStuffStockReceive.getId();
    }

    @Override
    public void updatePropertyStuffStockReceive(PropertyStuffStockReceiveSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffStockReceiveExists(updateReqVO.getId());
        // 更新
        PropertyStuffStockReceiveDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffStockReceiveDO.class);
        propertyStuffStockReceiveMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffStockReceive(Long id) {
        // 校验存在
        validatePropertyStuffStockReceiveExists(id);
        // 删除
        propertyStuffStockReceiveMapper.deleteById(id);
    }

    private void validatePropertyStuffStockReceiveExists(Long id) {
        if (propertyStuffStockReceiveMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_STOCK_RECEIVE_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffStockReceiveDO getPropertyStuffStockReceive(Long id) {
        return propertyStuffStockReceiveMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffStockReceiveDO> getPropertyStuffStockReceivePage(PropertyStuffStockReceivePageReqVO pageReqVO) {
        return propertyStuffStockReceiveMapper.selectPage(pageReqVO);
    }

}