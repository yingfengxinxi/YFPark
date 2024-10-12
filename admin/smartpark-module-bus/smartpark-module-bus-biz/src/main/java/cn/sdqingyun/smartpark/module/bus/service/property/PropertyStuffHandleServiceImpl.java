package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffHandlePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffHandleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffHandleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffHandleDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffHandleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_HANDLE_NOT_EXISTS;

/**
 * 耗材业务处置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffHandleServiceImpl implements PropertyStuffHandleService {

    @Resource
    private PropertyStuffHandleMapper propertyStuffHandleMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;
    @Override
    public Long createPropertyStuffHandle(PropertyStuffHandleSaveReqVO createReqVO) {
        // 插入
        PropertyStuffHandleDO propertyStuffHandle = BeanUtils.toBean(createReqVO, PropertyStuffHandleDO.class);
        propertyStuffHandleMapper.insert(propertyStuffHandle);
        // 返回
        return propertyStuffHandle.getId();
    }

    @Override
    public void updatePropertyStuffHandle(PropertyStuffHandleSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffHandleExists(updateReqVO.getId());
        // 更新
        PropertyStuffHandleDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffHandleDO.class);
        propertyStuffHandleMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffHandle(Long id) {
        // 校验存在
        validatePropertyStuffHandleExists(id);
        // 删除
        propertyStuffHandleMapper.deleteById(id);
    }

    private void validatePropertyStuffHandleExists(Long id) {
        if (propertyStuffHandleMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_HANDLE_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffHandleDO getPropertyStuffHandle(Long id) {
        return propertyStuffHandleMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffHandleDO> getPropertyStuffHandlePage(PropertyStuffHandlePageReqVO pageReqVO) {
        return propertyStuffHandleMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffHandleRespVO> getPropertyStuffHandlePageVO(PropertyStuffHandlePageReqVO pageReqVO) {
        PageResult<PropertyStuffHandleDO> pageResult = propertyStuffHandleMapper.selectPage(pageReqVO);
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList())){
            return PageResult.empty();
        }
        PageResult<PropertyStuffHandleRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffHandleRespVO.class );
        for (PropertyStuffHandleRespVO respVO : result.getList()) {
            respVO.setDepositoryName( propertyStuffDepositoryMapper.getNameById(respVO.getDepositoryId()) );
        }
        return result;
    }
}