package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffRetreatPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffRetreatRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffRetreatSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffRetreatDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffRetreatMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_RETREAT_NOT_EXISTS;

/**
 * 耗材业务退库 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffRetreatServiceImpl implements PropertyStuffRetreatService {

    @Resource
    private PropertyStuffRetreatMapper propertyStuffRetreatMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;
    @Resource
    private CodeGetName codeGetName;
    @Override
    public Long createPropertyStuffRetreat(PropertyStuffRetreatSaveReqVO createReqVO) {
        // 插入
        PropertyStuffRetreatDO propertyStuffRetreat = BeanUtils.toBean(createReqVO, PropertyStuffRetreatDO.class);
        propertyStuffRetreatMapper.insert(propertyStuffRetreat);
        // 返回
        return propertyStuffRetreat.getId();
    }

    @Override
    public void updatePropertyStuffRetreat(PropertyStuffRetreatSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffRetreatExists(updateReqVO.getId());
        // 更新
        PropertyStuffRetreatDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffRetreatDO.class);
        propertyStuffRetreatMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffRetreat(Long id) {
        // 校验存在
        validatePropertyStuffRetreatExists(id);
        // 删除
        propertyStuffRetreatMapper.deleteById(id);
    }

    private void validatePropertyStuffRetreatExists(Long id) {
        if (propertyStuffRetreatMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_RETREAT_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffRetreatDO getPropertyStuffRetreat(Long id) {
        return propertyStuffRetreatMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffRetreatDO> getPropertyStuffRetreatPage(PropertyStuffRetreatPageReqVO pageReqVO) {
        return propertyStuffRetreatMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffRetreatRespVO> getPropertyStuffRetreatPageVO(PropertyStuffRetreatPageReqVO pageReqVO) {
        PageResult<PropertyStuffRetreatDO> pageResult = propertyStuffRetreatMapper.selectPage(pageReqVO);
       if(pageResult == null || CollUtil.isEmpty( pageResult.getList())){
           return PageResult.empty();
       }
       PageResult<PropertyStuffRetreatRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffRetreatRespVO.class );
       for (PropertyStuffRetreatRespVO respVO : result.getList()) {
           respVO.setRetreatName( codeGetName.getUserName( respVO.getRetreatUid()) );
           respVO.setDepositoryName( propertyStuffDepositoryMapper.getNameById( respVO.getDepositoryId() ) );
       }
        return result;
    }
}