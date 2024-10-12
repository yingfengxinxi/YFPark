package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffReturnPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffReturnRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffReturnSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffReturnDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffReturnMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_RETURN_NOT_EXISTS;

/**
 * 耗材业务耗材退还 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffReturnServiceImpl implements PropertyStuffReturnService {

    @Resource
    private PropertyStuffReturnMapper propertyStuffReturnMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;

    @Override
    public Long createPropertyStuffReturn(PropertyStuffReturnSaveReqVO createReqVO) {
        // 插入
        PropertyStuffReturnDO propertyStuffReturn = BeanUtils.toBean(createReqVO, PropertyStuffReturnDO.class);
        propertyStuffReturnMapper.insert(propertyStuffReturn);
        // 返回
        return propertyStuffReturn.getId();
    }

    @Override
    public void updatePropertyStuffReturn(PropertyStuffReturnSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffReturnExists(updateReqVO.getId());
        // 更新
        PropertyStuffReturnDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffReturnDO.class);
        propertyStuffReturnMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffReturn(Long id) {
        // 校验存在
        validatePropertyStuffReturnExists(id);
        // 删除
        propertyStuffReturnMapper.deleteById(id);
    }

    private void validatePropertyStuffReturnExists(Long id) {
        if (propertyStuffReturnMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_RETURN_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffReturnDO getPropertyStuffReturn(Long id) {
        return propertyStuffReturnMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffReturnDO> getPropertyStuffReturnPage(PropertyStuffReturnPageReqVO pageReqVO) {
        return propertyStuffReturnMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffReturnRespVO> getPropertyStuffReturnPageVO(PropertyStuffReturnPageReqVO pageReqVO) {
        PageResult<PropertyStuffReturnDO> propertyStuffReturnDOPageResult = propertyStuffReturnMapper.selectPage( pageReqVO );
        if(propertyStuffReturnDOPageResult == null || CollUtil.isEmpty(propertyStuffReturnDOPageResult.getList())){
            return PageResult.empty();
        }

        PageResult<PropertyStuffReturnRespVO> result = BeanUtils.toBean( propertyStuffReturnDOPageResult, PropertyStuffReturnRespVO.class );
        for (PropertyStuffReturnRespVO vo : result.getList()) {
            vo.setDepositoryName( propertyStuffDepositoryMapper.getNameById( vo.getDepositoryId() ) );
        }
        return result;
    }
}