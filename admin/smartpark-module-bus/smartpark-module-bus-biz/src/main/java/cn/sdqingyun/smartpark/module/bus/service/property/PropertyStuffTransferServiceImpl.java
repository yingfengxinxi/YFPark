package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffTransferPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffTransferRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffTransferSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffTransferDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffTransferMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_TRANSFER_NOT_EXISTS;

/**
 * 耗材业务调拨 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffTransferServiceImpl implements PropertyStuffTransferService {

    @Resource
    private PropertyStuffTransferMapper propertyStuffTransferMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;
    @Resource
    private CodeGetName codeGetName;
    @Override
    public Long createPropertyStuffTransfer(PropertyStuffTransferSaveReqVO createReqVO) {
        // 插入
        PropertyStuffTransferDO propertyStuffTransfer = BeanUtils.toBean(createReqVO, PropertyStuffTransferDO.class);
        propertyStuffTransferMapper.insert(propertyStuffTransfer);
        // 返回
        return propertyStuffTransfer.getId();
    }

    @Override
    public void updatePropertyStuffTransfer(PropertyStuffTransferSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffTransferExists(updateReqVO.getId());
        // 更新
        PropertyStuffTransferDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffTransferDO.class);
        propertyStuffTransferMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffTransfer(Long id) {
        // 校验存在
        validatePropertyStuffTransferExists(id);
        // 删除
        propertyStuffTransferMapper.deleteById(id);
    }

    private void validatePropertyStuffTransferExists(Long id) {
        if (propertyStuffTransferMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_TRANSFER_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffTransferDO getPropertyStuffTransfer(Long id) {
        return propertyStuffTransferMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffTransferDO> getPropertyStuffTransferPage(PropertyStuffTransferPageReqVO pageReqVO) {
        return propertyStuffTransferMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffTransferRespVO> getPropertyStuffTransferPageVO(PropertyStuffTransferPageReqVO pageReqVO) {
        PageResult<PropertyStuffTransferDO> pageResult = propertyStuffTransferMapper.selectPage(pageReqVO);
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList() )){
            return PageResult.empty();
        }
        PageResult<PropertyStuffTransferRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffTransferRespVO.class );
        for (PropertyStuffTransferRespVO respVO : result.getList()) {
            respVO.setInAdminName( codeGetName.getUserName( respVO.getInAdminUid()) );
            respVO.setOutAdminName( codeGetName.getUserName( respVO.getOutAdminUid()) );
            respVO.setInDepositoryName( propertyStuffDepositoryMapper.getNameById( respVO.getInDepositoryId() ) );
            respVO.setOutDepositorName( propertyStuffDepositoryMapper.getNameById( respVO.getOutDepositoryId() ) );
        }

        return result;
    }
}