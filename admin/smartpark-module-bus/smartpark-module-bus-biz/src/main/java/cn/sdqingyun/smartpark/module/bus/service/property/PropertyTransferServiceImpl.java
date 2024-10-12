package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLocationMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyTransferDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyTransferMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产调拨 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyTransferServiceImpl implements PropertyTransferService {

    @Resource
    private PropertyTransferMapper propertyTransferMapper;
    @Resource
    private PropertyLocationMapper propertyLocationMapper;

    @Override
    public Long createPropertyTransfer(PropertyTransferSaveReqVO createReqVO) {
        // 插入
        PropertyTransferDO propertyTransfer = BeanUtils.toBean(createReqVO, PropertyTransferDO.class);
        propertyTransferMapper.insert(propertyTransfer);
        // 返回
        return propertyTransfer.getId();
    }

    @Override
    public void updatePropertyTransfer(PropertyTransferSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyTransferExists(updateReqVO.getId());
        // 更新
        PropertyTransferDO updateObj = BeanUtils.toBean(updateReqVO, PropertyTransferDO.class);
        propertyTransferMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyTransfer(Long id) {
        // 校验存在
        validatePropertyTransferExists(id);
        // 删除
        propertyTransferMapper.deleteById(id);
    }

    private void validatePropertyTransferExists(Long id) {
        if (propertyTransferMapper.selectById(id) == null) {
            throw exception(PROPERTY_TRANSFER_NOT_EXISTS);
        }
    }

    @Override
    public PropertyTransferDO getPropertyTransfer(Long id) {
        return propertyTransferMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyTransferDO> getPropertyTransferPage(PropertyTransferPageReqVO pageReqVO) {
        return propertyTransferMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyTransferRespVO> getPropertyTransferPageVO(PropertyTransferPageReqVO pageReqVO) {
        PageResult<PropertyTransferDO> page = propertyTransferMapper.selectPage( pageReqVO );
        if( page == null || CollUtil.isEmpty( page.getList() )){
            return PageResult.empty();
        }

        PageResult<PropertyTransferRespVO> result = BeanUtils.toBean( page, PropertyTransferRespVO.class );
        for (PropertyTransferRespVO vo : result.getList()) {
            PropertyLocationDO propertyLocationDO = propertyLocationMapper.selectById( vo.getInLocationId() );
            if( propertyLocationDO != null ){
                vo.setInLocationName( propertyLocationDO.getName() );
            }
        }

        return result;
    }
}