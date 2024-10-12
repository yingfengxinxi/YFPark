package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryEmployeeDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyInventoryEmployeeMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产盘点员工记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyInventoryEmployeeServiceImpl implements PropertyInventoryEmployeeService {

    @Resource
    private PropertyInventoryEmployeeMapper propertyInventoryEmployeeMapper;

    @Override
    public Long createPropertyInventoryEmployee(PropertyInventoryEmployeeSaveReqVO createReqVO) {
        // 插入
        PropertyInventoryEmployeeDO propertyInventoryEmployee = BeanUtils.toBean(createReqVO, PropertyInventoryEmployeeDO.class);
        propertyInventoryEmployeeMapper.insert(propertyInventoryEmployee);
        // 返回
        return propertyInventoryEmployee.getId();
    }

    @Override
    public void updatePropertyInventoryEmployee(PropertyInventoryEmployeeSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyInventoryEmployeeExists(updateReqVO.getId());
        // 更新
        PropertyInventoryEmployeeDO updateObj = BeanUtils.toBean(updateReqVO, PropertyInventoryEmployeeDO.class);
        propertyInventoryEmployeeMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyInventoryEmployee(Long id) {
        // 校验存在
        validatePropertyInventoryEmployeeExists(id);
        // 删除
        propertyInventoryEmployeeMapper.deleteById(id);
    }

    private void validatePropertyInventoryEmployeeExists(Long id) {
        if (propertyInventoryEmployeeMapper.selectById(id) == null) {
            throw exception(PROPERTY_INVENTORY_EMPLOYEE_NOT_EXISTS);
        }
    }

    @Override
    public PropertyInventoryEmployeeDO getPropertyInventoryEmployee(Long id) {
        return propertyInventoryEmployeeMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyInventoryEmployeeDO> getPropertyInventoryEmployeePage(PropertyInventoryEmployeePageReqVO pageReqVO) {
        return propertyInventoryEmployeeMapper.selectPage(pageReqVO);
    }

}