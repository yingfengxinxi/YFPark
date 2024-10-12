package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryRecordDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyInventoryRecordMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产盘点记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyInventoryRecordServiceImpl implements PropertyInventoryRecordService {

    @Resource
    private PropertyInventoryRecordMapper propertyInventoryRecordMapper;

    @Override
    public Long createPropertyInventoryRecord(PropertyInventoryRecordSaveReqVO createReqVO) {
        // 插入
        PropertyInventoryRecordDO propertyInventoryRecord = BeanUtils.toBean(createReqVO, PropertyInventoryRecordDO.class);
        propertyInventoryRecordMapper.insert(propertyInventoryRecord);
        // 返回
        return propertyInventoryRecord.getId();
    }

    @Override
    public void updatePropertyInventoryRecord(PropertyInventoryRecordSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyInventoryRecordExists(updateReqVO.getId());
        // 更新
        PropertyInventoryRecordDO updateObj = BeanUtils.toBean(updateReqVO, PropertyInventoryRecordDO.class);
        propertyInventoryRecordMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyInventoryRecord(Long id) {
        // 校验存在
        validatePropertyInventoryRecordExists(id);
        // 删除
        propertyInventoryRecordMapper.deleteById(id);
    }

    private void validatePropertyInventoryRecordExists(Long id) {
        if (propertyInventoryRecordMapper.selectById(id) == null) {
            throw exception(PROPERTY_INVENTORY_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public PropertyInventoryRecordDO getPropertyInventoryRecord(Long id) {
        return propertyInventoryRecordMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyInventoryRecordDO> getPropertyInventoryRecordPage(PropertyInventoryRecordPageReqVO pageReqVO) {
        return propertyInventoryRecordMapper.selectPage(pageReqVO);
    }

}