package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRepairDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyRepairMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 资产维修 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyRepairServiceImpl implements PropertyRepairService {

    @Resource
    private PropertyRepairMapper propertyRepairMapper;
    @Resource
    private CodeGetName codeGetName;

    @Override
    public Long createPropertyRepair(PropertyRepairSaveReqVO createReqVO) {
        // 插入
        PropertyRepairDO propertyRepair = BeanUtils.toBean(createReqVO, PropertyRepairDO.class);
        propertyRepairMapper.insert(propertyRepair);
        // 返回
        return propertyRepair.getId();
    }

    @Override
    public void updatePropertyRepair(PropertyRepairSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyRepairExists(updateReqVO.getId());
        // 更新
        PropertyRepairDO updateObj = BeanUtils.toBean(updateReqVO, PropertyRepairDO.class);
        propertyRepairMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyRepair(Long id) {
        // 校验存在
        validatePropertyRepairExists(id);
        // 删除
        propertyRepairMapper.deleteById(id);
    }

    private void validatePropertyRepairExists(Long id) {
        if (propertyRepairMapper.selectById(id) == null) {
            throw exception(PROPERTY_REPAIR_NOT_EXISTS);
        }
    }

    @Override
    public PropertyRepairDO getPropertyRepair(Long id) {
        return propertyRepairMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyRepairDO> getPropertyRepairPage(PropertyRepairPageReqVO pageReqVO) {
        return propertyRepairMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyRepairRespVO> getPropertyRepairPageVO(PropertyRepairPageReqVO pageReqVO) {
        PageResult<PropertyRepairDO> pageResult = propertyRepairMapper.selectPage( pageReqVO );
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList() )){
            return PageResult.empty();
        }
        PageResult<PropertyRepairRespVO> result = BeanUtils.toBean( pageResult, PropertyRepairRespVO.class );
        for (PropertyRepairRespVO vo : result.getList()) {
            vo.setRepairDepartmentName( codeGetName.getDeptName( vo.getRepairDepartmentId() ) );
        }
        return result;
    }
}