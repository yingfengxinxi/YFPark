package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyDepositoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyDepositorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDepositoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyDepositoryMapper;
import cn.sdqingyun.smartpark.module.bus.enums.ProptryAuditStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_DEPOSITORY_NOT_EXISTS;

/**
 * 资产仓库信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyDepositoryServiceImpl implements PropertyDepositoryService {

    @Resource
    private PropertyDepositoryMapper propertyDepositoryMapper;

    @Override
    public Long createPropertyDepository(PropertyDepositorySaveReqVO createReqVO) {
        // 插入
        PropertyDepositoryDO propertyDepository = BeanUtils.toBean(createReqVO, PropertyDepositoryDO.class);
        propertyDepositoryMapper.insert(propertyDepository);
        // 返回
        return propertyDepository.getId();
    }

    @Override
    public void updatePropertyDepository(PropertyDepositorySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyDepositoryExists(updateReqVO.getId());
        // 更新
        PropertyDepositoryDO updateObj = BeanUtils.toBean(updateReqVO, PropertyDepositoryDO.class);
        propertyDepositoryMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyDepository(Long id) {
        // 校验存在
        PropertyDepositoryDO propertyDepositoryDO = propertyDepositoryMapper.selectById( id );
        if (propertyDepositoryDO == null) {
            throw exception(PROPERTY_DEPOSITORY_NOT_EXISTS);
        }
        if(propertyDepositoryDO.getStatus() != ProptryAuditStatusEnum.STATUS_3.getCode()){
            throw new ServiceException(406, "仅支持删除审批不通过的单据");
        }
        // 删除
        propertyDepositoryMapper.deleteById(id);
    }

    private void validatePropertyDepositoryExists(Long id) {
        if (propertyDepositoryMapper.selectById(id) == null) {
            throw exception(PROPERTY_DEPOSITORY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyDepositoryDO getPropertyDepository(Long id) {
        return propertyDepositoryMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyDepositoryDO> getPropertyDepositoryPage(PropertyDepositoryPageReqVO pageReqVO) {
        return propertyDepositoryMapper.selectPage(pageReqVO);
    }

}