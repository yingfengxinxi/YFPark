package cn.sdqingyun.smartpark.module.bus.service.property;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLendoutDepositoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLendoutDepositoryMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产借出仓库信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyLendoutDepositoryServiceImpl implements PropertyLendoutDepositoryService {

    @Resource
    private PropertyLendoutDepositoryMapper propertyLendoutDepositoryMapper;

    @Override
    public Long createPropertyLendoutDepository(PropertyLendoutDepositorySaveReqVO createReqVO) {
        // 插入
        PropertyLendoutDepositoryDO propertyLendoutDepository = BeanUtils.toBean(createReqVO, PropertyLendoutDepositoryDO.class);
        propertyLendoutDepositoryMapper.insert(propertyLendoutDepository);
        // 返回
        return propertyLendoutDepository.getId();
    }

    @Override
    public void updatePropertyLendoutDepository(PropertyLendoutDepositorySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyLendoutDepositoryExists(updateReqVO.getId());
        // 更新
        PropertyLendoutDepositoryDO updateObj = BeanUtils.toBean(updateReqVO, PropertyLendoutDepositoryDO.class);
        propertyLendoutDepositoryMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyLendoutDepository(Long id) {
        // 校验存在
        validatePropertyLendoutDepositoryExists(id);
        // 删除
        propertyLendoutDepositoryMapper.deleteById(id);
    }

    private void validatePropertyLendoutDepositoryExists(Long id) {
        if (propertyLendoutDepositoryMapper.selectById(id) == null) {
            throw exception(PROPERTY_LENDOUT_DEPOSITORY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyLendoutDepositoryDO getPropertyLendoutDepository(Long id) {
        return propertyLendoutDepositoryMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyLendoutDepositoryDO> getPropertyLendoutDepositoryPage(PropertyLendoutDepositoryPageReqVO pageReqVO) {
        return propertyLendoutDepositoryMapper.selectPage(pageReqVO);
    }

}