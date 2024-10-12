package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryListDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyInventoryListMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产盘点清单 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyInventoryListServiceImpl implements PropertyInventoryListService {

    @Resource
    private PropertyInventoryListMapper propertyInventoryListMapper;

    @Override
    public Long createPropertyInventoryList(PropertyInventoryListSaveReqVO createReqVO) {
        // 插入
        PropertyInventoryListDO propertyInventoryList = BeanUtils.toBean(createReqVO, PropertyInventoryListDO.class);
        propertyInventoryListMapper.insert(propertyInventoryList);
        // 返回
        return propertyInventoryList.getId();
    }

    @Override
    public void updatePropertyInventoryList(PropertyInventoryListSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyInventoryListExists(updateReqVO.getId());
        // 更新
        PropertyInventoryListDO updateObj = BeanUtils.toBean(updateReqVO, PropertyInventoryListDO.class);
        propertyInventoryListMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyInventoryList(Long id) {
        // 校验存在
        validatePropertyInventoryListExists(id);
        // 删除
        propertyInventoryListMapper.deleteById(id);
    }

    private void validatePropertyInventoryListExists(Long id) {
        if (propertyInventoryListMapper.selectById(id) == null) {
            throw exception(PROPERTY_INVENTORY_LIST_NOT_EXISTS);
        }
    }

    @Override
    public PropertyInventoryListDO getPropertyInventoryList(Long id) {
        return propertyInventoryListMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyInventoryListDO> getPropertyInventoryListPage(PropertyInventoryListPageReqVO pageReqVO) {
        return propertyInventoryListMapper.selectPage(pageReqVO);
    }

}