package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyProcessDataDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyProcessDataMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 业务流程单据关联资产 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyProcessDataServiceImpl implements PropertyProcessDataService {

    @Resource
    private PropertyProcessDataMapper propertyProcessDataMapper;

    @Override
    public Long createPropertyProcessData(PropertyProcessDataSaveReqVO createReqVO) {
        // 插入
        PropertyProcessDataDO propertyProcessData = BeanUtils.toBean(createReqVO, PropertyProcessDataDO.class);
        propertyProcessDataMapper.insert(propertyProcessData);
        // 返回
        return propertyProcessData.getId();
    }

    @Override
    public void updatePropertyProcessData(PropertyProcessDataSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyProcessDataExists(updateReqVO.getId());
        // 更新
        PropertyProcessDataDO updateObj = BeanUtils.toBean(updateReqVO, PropertyProcessDataDO.class);
        propertyProcessDataMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyProcessData(Long id) {
        // 校验存在
        validatePropertyProcessDataExists(id);
        // 删除
        propertyProcessDataMapper.deleteById(id);
    }

    private void validatePropertyProcessDataExists(Long id) {
        if (propertyProcessDataMapper.selectById(id) == null) {
            throw exception(PROPERTY_PROCESS_DATA_NOT_EXISTS);
        }
    }

    @Override
    public PropertyProcessDataDO getPropertyProcessData(Long id) {
        return propertyProcessDataMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyProcessDataDO> getPropertyProcessDataPage(PropertyProcessDataPageReqVO pageReqVO) {
        return propertyProcessDataMapper.selectPage(pageReqVO);
    }

}