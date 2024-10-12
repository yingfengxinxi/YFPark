package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffEnterDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffEnterMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 耗材入库记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffEnterServiceImpl implements PropertyStuffEnterService {

    @Resource
    private PropertyStuffEnterMapper propertyStuffEnterMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;
    @Resource
    private CodeGetName codeGetName;
    @Override
    public Long createPropertyStuffEnter(PropertyStuffEnterSaveReqVO createReqVO) {
        // 插入
        PropertyStuffEnterDO propertyStuffEnter = BeanUtils.toBean(createReqVO, PropertyStuffEnterDO.class);
        propertyStuffEnterMapper.insert(propertyStuffEnter);
        // 返回
        return propertyStuffEnter.getId();
    }

    @Override
    public void updatePropertyStuffEnter(PropertyStuffEnterSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffEnterExists(updateReqVO.getId());
        // 更新
        PropertyStuffEnterDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffEnterDO.class);
        propertyStuffEnterMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffEnter(Long id) {
        // 校验存在
        validatePropertyStuffEnterExists(id);
        // 删除
        propertyStuffEnterMapper.deleteById(id);
    }

    private void validatePropertyStuffEnterExists(Long id) {
        if (propertyStuffEnterMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_ENTER_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffEnterDO getPropertyStuffEnter(Long id) {
        return propertyStuffEnterMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffEnterDO> getPropertyStuffEnterPage(PropertyStuffEnterPageReqVO pageReqVO) {
        return propertyStuffEnterMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffEnterRespVO> getPropertyStuffEnterPageVO(PropertyStuffEnterPageReqVO pageReqVO) {
        PageResult<PropertyStuffEnterDO> pageResult = propertyStuffEnterMapper.selectPage(pageReqVO);
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList() )){
            return PageResult.empty();
        }
        PageResult<PropertyStuffEnterRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffEnterRespVO.class );
        for (PropertyStuffEnterRespVO respVO : result.getList()) {
            respVO.setDepositoryName(propertyStuffDepositoryMapper.getNameById( respVO.getDepositoryId() ));
            respVO.setEnterName( codeGetName.getUserName( respVO.getEnterUid() ) );
        }
        return result;
    }
}