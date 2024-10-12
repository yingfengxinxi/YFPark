package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyHandlePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyHandleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyHandleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandleDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyHandleMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_HANDLE_NOT_EXISTS;

/**
 * 资产处置单据记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyHandleServiceImpl implements PropertyHandleService {

    @Resource
    private PropertyHandleMapper propertyHandleMapper;
    @Resource
    private CodeGetName codeGetName;

    @Override
    public Long createPropertyHandle(PropertyHandleSaveReqVO createReqVO) {
        // 插入
        PropertyHandleDO propertyHandle = BeanUtils.toBean(createReqVO, PropertyHandleDO.class);
        propertyHandleMapper.insert(propertyHandle);
        // 返回
        return propertyHandle.getId();
    }

    @Override
    public void updatePropertyHandle(PropertyHandleSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyHandleExists(updateReqVO.getId());
        // 更新
        PropertyHandleDO updateObj = BeanUtils.toBean(updateReqVO, PropertyHandleDO.class);
        propertyHandleMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyHandle(Long id) {
        // 校验存在
        validatePropertyHandleExists(id);
        // 删除
        propertyHandleMapper.deleteById(id);
    }

    private void validatePropertyHandleExists(Long id) {
        if (propertyHandleMapper.selectById(id) == null) {
            throw exception(PROPERTY_HANDLE_NOT_EXISTS);
        }
    }

    @Override
    public PropertyHandleDO getPropertyHandle(Long id) {
        return propertyHandleMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyHandleDO> getPropertyHandlePage(PropertyHandlePageReqVO pageReqVO) {
        return propertyHandleMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyHandleRespVO> getPropertyHandlePageVO(PropertyHandlePageReqVO pageReqVO) {
        PageResult<PropertyHandleDO> pageResult = propertyHandleMapper.selectPage( pageReqVO );
        if( pageResult == null || CollUtil.isEmpty( pageResult.getList() )){
            return PageResult.empty();
        }

        PageResult<PropertyHandleRespVO> result = BeanUtils.toBean( pageResult, PropertyHandleRespVO.class );
        for (PropertyHandleRespVO vo : result.getList()) {
            vo.setDepartmentName( codeGetName.getDeptName( vo.getDepartmentId() ) );
            vo.setUserName( codeGetName.getUserName( Long.valueOf( vo.getCreator() )) );
        }

        return result;
    }
}