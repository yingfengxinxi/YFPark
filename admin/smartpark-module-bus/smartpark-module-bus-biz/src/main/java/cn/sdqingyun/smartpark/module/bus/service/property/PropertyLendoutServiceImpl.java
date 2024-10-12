package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLendoutDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLendoutMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产借出 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyLendoutServiceImpl implements PropertyLendoutService {

    @Resource
    private PropertyLendoutMapper propertyLendoutMapper;
    @Resource
    private CodeGetName codeGetName;
    @Override
    public Long createPropertyLendout(PropertyLendoutSaveReqVO createReqVO) {
        // 插入
        PropertyLendoutDO propertyLendout = BeanUtils.toBean(createReqVO, PropertyLendoutDO.class);
        propertyLendoutMapper.insert(propertyLendout);
        // 返回
        return propertyLendout.getId();
    }

    @Override
    public void updatePropertyLendout(PropertyLendoutSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyLendoutExists(updateReqVO.getId());
        // 更新
        PropertyLendoutDO updateObj = BeanUtils.toBean(updateReqVO, PropertyLendoutDO.class);
        propertyLendoutMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyLendout(Long id) {
        // 校验存在
        validatePropertyLendoutExists(id);
        // 删除
        propertyLendoutMapper.deleteById(id);
    }

    private void validatePropertyLendoutExists(Long id) {
        if (propertyLendoutMapper.selectById(id) == null) {
            throw exception(PROPERTY_LENDOUT_NOT_EXISTS);
        }
    }

    @Override
    public PropertyLendoutDO getPropertyLendout(Long id) {
        return propertyLendoutMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyLendoutDO> getPropertyLendoutPage(PropertyLendoutPageReqVO pageReqVO) {
        return propertyLendoutMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyLendoutRespVO> getPropertyLendoutPageVO(PropertyLendoutPageReqVO pageReqVO) {
        PageResult<PropertyLendoutDO> result = propertyLendoutMapper.selectPage( pageReqVO );
        if(result == null || CollUtil.isEmpty( result.getList() )){
            return PageResult.empty();
        }

        PageResult<PropertyLendoutRespVO> pageResult = BeanUtils.toBean( result, PropertyLendoutRespVO.class );
        for (PropertyLendoutRespVO pair : pageResult.getList()) {
            if(pair.getDepartmentId() != null){
                pair.setDepartmentName( codeGetName.getDeptName(pair.getDepartmentId()) );
            }
            if(pair.getLendUid() != null){
                pair.setLendName( codeGetName.getUserName( pair.getLendUid() ));
            }
        }

        return pageResult;
    }
}