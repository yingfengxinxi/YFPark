package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandoutDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyHandoutMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产派发/退库 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyHandoutServiceImpl implements PropertyHandoutService {

    @Resource
    private PropertyHandoutMapper propertyHandoutMapper;
    @Resource
    private CodeGetName codeGetName;
    @Override
    public Long createPropertyHandout(PropertyHandoutSaveReqVO createReqVO) {
        // 插入
        PropertyHandoutDO propertyHandout = BeanUtils.toBean(createReqVO, PropertyHandoutDO.class);
        propertyHandoutMapper.insert(propertyHandout);
        // 返回
        return propertyHandout.getId();
    }

    @Override
    public void updatePropertyHandout(PropertyHandoutSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyHandoutExists(updateReqVO.getId());
        // 更新
        PropertyHandoutDO updateObj = BeanUtils.toBean(updateReqVO, PropertyHandoutDO.class);
        propertyHandoutMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyHandout(Long id) {
        // 校验存在
        validatePropertyHandoutExists(id);
        // 删除
        propertyHandoutMapper.deleteById(id);
    }

    private void validatePropertyHandoutExists(Long id) {
        if (propertyHandoutMapper.selectById(id) == null) {
            throw exception(PROPERTY_HANDOUT_NOT_EXISTS);
        }
    }

    @Override
    public PropertyHandoutDO getPropertyHandout(Long id) {
        return propertyHandoutMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyHandoutDO> getPropertyHandoutPage(PropertyHandoutPageReqVO pageReqVO) {
        return propertyHandoutMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyHandoutRespVO> getPropertyHandoutPageVO(PropertyHandoutPageReqVO pageReqVO) {
        PageResult<PropertyHandoutDO> result = propertyHandoutMapper.selectPage( pageReqVO );
        if(result == null || CollUtil.isEmpty( result.getList() )){
            return PageResult.empty();
        }

        PageResult<PropertyHandoutRespVO> pageResult = BeanUtils.toBean( result, PropertyHandoutRespVO.class );
        for (PropertyHandoutRespVO handoutRespVO : pageResult.getList()) {
            if(handoutRespVO.getDepartmentId() != null){
                handoutRespVO.setDepartmentName( codeGetName.getDeptName(handoutRespVO.getDepartmentId()) );
            }
            if(handoutRespVO.getReceiveUid() != null){
                handoutRespVO.setReceiveName( codeGetName.getUserName( handoutRespVO.getReceiveUid() ));
            }
        }

        return pageResult;
    }
}