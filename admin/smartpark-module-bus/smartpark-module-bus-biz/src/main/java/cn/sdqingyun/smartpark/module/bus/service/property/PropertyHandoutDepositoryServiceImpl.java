package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandoutDepositoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyHandoutDepositoryMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产派发仓库信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyHandoutDepositoryServiceImpl implements PropertyHandoutDepositoryService {

    @Resource
    private PropertyHandoutDepositoryMapper propertyHandoutDepositoryMapper;

    @Override
    public Long createPropertyHandoutDepository(PropertyHandoutDepositorySaveReqVO createReqVO) {
        // 插入
        PropertyHandoutDepositoryDO propertyHandoutDepository = BeanUtils.toBean(createReqVO, PropertyHandoutDepositoryDO.class);
        propertyHandoutDepositoryMapper.insert(propertyHandoutDepository);
        // 返回
        return propertyHandoutDepository.getId();
    }

    @Override
    public void updatePropertyHandoutDepository(PropertyHandoutDepositorySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyHandoutDepositoryExists(updateReqVO.getId());
        // 更新
        PropertyHandoutDepositoryDO updateObj = BeanUtils.toBean(updateReqVO, PropertyHandoutDepositoryDO.class);
        propertyHandoutDepositoryMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyHandoutDepository(Long id) {
        // 校验存在
        validatePropertyHandoutDepositoryExists(id);
        // 删除
        propertyHandoutDepositoryMapper.deleteById(id);
    }

    private void validatePropertyHandoutDepositoryExists(Long id) {
        if (propertyHandoutDepositoryMapper.selectById(id) == null) {
            throw exception(PROPERTY_HANDOUT_DEPOSITORY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyHandoutDepositoryDO getPropertyHandoutDepository(Long id) {
        return propertyHandoutDepositoryMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyHandoutDepositoryDO> getPropertyHandoutDepositoryPage(PropertyHandoutDepositoryPageReqVO pageReqVO) {
        return propertyHandoutDepositoryMapper.selectPage(pageReqVO);
    }

}