package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyMaintainDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyMaintainMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产保养记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyMaintainServiceImpl implements PropertyMaintainService {

    @Resource
    private PropertyMaintainMapper propertyMaintainMapper;
    @Resource
    private CodeGetName codeGetName;
    @Override
    public Long createPropertyMaintain(PropertyMaintainSaveReqVO createReqVO) {
        // 插入
        PropertyMaintainDO propertyMaintain = BeanUtils.toBean(createReqVO, PropertyMaintainDO.class);
        propertyMaintainMapper.insert(propertyMaintain);
        // 返回
        return propertyMaintain.getId();
    }

    @Override
    public void updatePropertyMaintain(PropertyMaintainSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyMaintainExists(updateReqVO.getId());
        // 更新
        PropertyMaintainDO updateObj = BeanUtils.toBean(updateReqVO, PropertyMaintainDO.class);
        propertyMaintainMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyMaintain(Long id) {
        // 校验存在
        validatePropertyMaintainExists(id);
        // 删除
        propertyMaintainMapper.deleteById(id);
    }

    private void validatePropertyMaintainExists(Long id) {
        if (propertyMaintainMapper.selectById(id) == null) {
            throw exception(PROPERTY_MAINTAIN_NOT_EXISTS);
        }
    }

    @Override
    public PropertyMaintainDO getPropertyMaintain(Long id) {
        return propertyMaintainMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyMaintainDO> getPropertyMaintainPage(PropertyMaintainPageReqVO pageReqVO) {
        return propertyMaintainMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyMaintainRespVO> getPropertyMaintainPageVO(PropertyMaintainPageReqVO pageReqVO) {
        PageResult<PropertyMaintainDO> pageResult = propertyMaintainMapper.selectPage( pageReqVO );
        if(pageResult == null || CollUtil.isEmpty(pageResult.getList())){
            return PageResult.empty();
        }
        PageResult<PropertyMaintainRespVO> result = BeanUtils.toBean( pageResult, PropertyMaintainRespVO.class );
        for (PropertyMaintainRespVO respVO : result.getList()) {
            respVO.setMaintainName(codeGetName.getUserName(respVO.getMaintainUid()));
        }

        return result;
    }
}