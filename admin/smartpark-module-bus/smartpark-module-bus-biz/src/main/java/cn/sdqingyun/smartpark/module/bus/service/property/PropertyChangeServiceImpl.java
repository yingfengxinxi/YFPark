package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyChangeDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyChangeMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产变更领用人 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyChangeServiceImpl implements PropertyChangeService {

    @Resource
    private PropertyChangeMapper propertyChangeMapper;
    @Resource
    private CodeGetName codeGetName;

    @Override
    public Long createPropertyChange(PropertyChangeSaveReqVO createReqVO) {
        // 插入
        PropertyChangeDO propertyChange = BeanUtils.toBean(createReqVO, PropertyChangeDO.class);
        propertyChangeMapper.insert(propertyChange);
        // 返回
        return propertyChange.getId();
    }

    @Override
    public void updatePropertyChange(PropertyChangeSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyChangeExists(updateReqVO.getId());
        // 更新
        PropertyChangeDO updateObj = BeanUtils.toBean(updateReqVO, PropertyChangeDO.class);
        propertyChangeMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyChange(Long id) {
        // 校验存在
        validatePropertyChangeExists(id);
        // 删除
        propertyChangeMapper.deleteById(id);
    }

    private void validatePropertyChangeExists(Long id) {
        if (propertyChangeMapper.selectById(id) == null) {
            throw exception(PROPERTY_CHANGE_NOT_EXISTS);
        }
    }

    @Override
    public PropertyChangeDO getPropertyChange(Long id) {
        return propertyChangeMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyChangeDO> getPropertyChangePage(PropertyChangePageReqVO pageReqVO) {
        return propertyChangeMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyChangeRespVO> getPropertyChangePageVO(PropertyChangePageReqVO pageReqVO) {
        PageResult<PropertyChangeDO> pageResult = propertyChangeMapper.selectPage( pageReqVO );
        if(pageResult == null || CollUtil.isEmpty(pageResult.getList())){
            return PageResult.empty();
        }
        PageResult<PropertyChangeRespVO> result = BeanUtils.toBean( pageResult, PropertyChangeRespVO.class );
        for (PropertyChangeRespVO propertyChangeRespVO : result.getList()) {
            propertyChangeRespVO.setAfterUseName(codeGetName.getUserName( propertyChangeRespVO.getAfterUseUid() ));
            propertyChangeRespVO.setAfterUseDepartmentName(codeGetName.getUserName( propertyChangeRespVO.getAfterUseDepartmentId() ));
        }
        return result;
    }
}