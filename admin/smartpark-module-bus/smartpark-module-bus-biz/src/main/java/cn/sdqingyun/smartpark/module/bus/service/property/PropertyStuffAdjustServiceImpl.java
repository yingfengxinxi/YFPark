package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffAdjustPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffAdjustRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffAdjustSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffAdjustDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffAdjustMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_ADJUST_NOT_EXISTS;

/**
 * 耗材业务调整 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffAdjustServiceImpl implements PropertyStuffAdjustService {

    @Resource
    private PropertyStuffAdjustMapper propertyStuffAdjustMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;
    @Override
    public Long createPropertyStuffAdjust(PropertyStuffAdjustSaveReqVO createReqVO) {
        // 插入
        PropertyStuffAdjustDO propertyStuffAdjust = BeanUtils.toBean(createReqVO, PropertyStuffAdjustDO.class);
        propertyStuffAdjustMapper.insert(propertyStuffAdjust);
        // 返回
        return propertyStuffAdjust.getId();
    }

    @Override
    public void updatePropertyStuffAdjust(PropertyStuffAdjustSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffAdjustExists(updateReqVO.getId());
        // 更新
        PropertyStuffAdjustDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffAdjustDO.class);
        propertyStuffAdjustMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffAdjust(Long id) {
        // 校验存在
        validatePropertyStuffAdjustExists(id);
        // 删除
        propertyStuffAdjustMapper.deleteById(id);
    }

    private void validatePropertyStuffAdjustExists(Long id) {
        if (propertyStuffAdjustMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_ADJUST_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffAdjustDO getPropertyStuffAdjust(Long id) {
        return propertyStuffAdjustMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffAdjustDO> getPropertyStuffAdjustPage(PropertyStuffAdjustPageReqVO pageReqVO) {
        return propertyStuffAdjustMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffAdjustRespVO> getPropertyStuffAdjustPageVO(PropertyStuffAdjustPageReqVO pageReqVO) {
        PageResult<PropertyStuffAdjustDO> pageResult = propertyStuffAdjustMapper.selectPage(pageReqVO);
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList())){
            return PageResult.empty();
        }
        PageResult<PropertyStuffAdjustRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffAdjustRespVO.class );
        for (PropertyStuffAdjustRespVO respVO : result.getList()) {
            respVO.setDepositoryName( propertyStuffDepositoryMapper.selectById(respVO.getDepositoryId()).getName() );
        }
        return result;
    }
}