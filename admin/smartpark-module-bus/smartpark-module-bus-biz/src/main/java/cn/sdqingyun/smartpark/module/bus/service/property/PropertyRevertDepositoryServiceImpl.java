package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRevertDepositoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyRevertDepositoryMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产归还仓库信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyRevertDepositoryServiceImpl implements PropertyRevertDepositoryService {

    @Resource
    private PropertyRevertDepositoryMapper propertyRevertDepositoryMapper;

    @Override
    public Long createPropertyRevertDepository(PropertyRevertDepositorySaveReqVO createReqVO) {
        // 插入
        PropertyRevertDepositoryDO propertyRevertDepository = BeanUtils.toBean(createReqVO, PropertyRevertDepositoryDO.class);
        propertyRevertDepositoryMapper.insert(propertyRevertDepository);
        // 返回
        return propertyRevertDepository.getId();
    }

    @Override
    public void updatePropertyRevertDepository(PropertyRevertDepositorySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyRevertDepositoryExists(updateReqVO.getId());
        // 更新
        PropertyRevertDepositoryDO updateObj = BeanUtils.toBean(updateReqVO, PropertyRevertDepositoryDO.class);
        propertyRevertDepositoryMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyRevertDepository(Long id) {
        // 校验存在
        validatePropertyRevertDepositoryExists(id);
        // 删除
        propertyRevertDepositoryMapper.deleteById(id);
    }

    private void validatePropertyRevertDepositoryExists(Long id) {
        if (propertyRevertDepositoryMapper.selectById(id) == null) {
            throw exception(PROPERTY_REVERT_DEPOSITORY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyRevertDepositoryDO getPropertyRevertDepository(Long id) {
        return propertyRevertDepositoryMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyRevertDepositoryDO> getPropertyRevertDepositoryPage(PropertyRevertDepositoryPageReqVO pageReqVO) {
        return propertyRevertDepositoryMapper.selectPage(pageReqVO);
    }

}