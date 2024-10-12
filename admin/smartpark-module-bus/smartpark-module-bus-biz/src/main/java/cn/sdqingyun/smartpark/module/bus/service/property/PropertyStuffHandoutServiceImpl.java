package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffProcessDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffProcessMapper;
import cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils.CodeGetName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffHandoutDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffHandoutMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 耗材业务派发 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffHandoutServiceImpl implements PropertyStuffHandoutService {

    @Resource
    private PropertyStuffHandoutMapper propertyStuffHandoutMapper;
    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;
    @Resource
    private CodeGetName codeGetName;
    @Resource
    private PropertyStuffProcessMapper processMapper;
    @Override
    public Long createPropertyStuffHandout(PropertyStuffHandoutSaveReqVO createReqVO) {
        // 插入
        PropertyStuffHandoutDO propertyStuffHandout = BeanUtils.toBean(createReqVO, PropertyStuffHandoutDO.class);
        propertyStuffHandoutMapper.insert(propertyStuffHandout);
        // 返回
        return propertyStuffHandout.getId();
    }

    @Override
    public void updatePropertyStuffHandout(PropertyStuffHandoutSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffHandoutExists(updateReqVO.getId());
        // 更新
        PropertyStuffHandoutDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffHandoutDO.class);
        propertyStuffHandoutMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffHandout(Long id) {
        // 校验存在
        validatePropertyStuffHandoutExists(id);
        // 删除
        propertyStuffHandoutMapper.deleteById(id);
    }

    private void validatePropertyStuffHandoutExists(Long id) {
        if (propertyStuffHandoutMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_HANDOUT_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffHandoutDO getPropertyStuffHandout(Long id) {
        return propertyStuffHandoutMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffHandoutDO> getPropertyStuffHandoutPage(PropertyStuffHandoutPageReqVO pageReqVO) {
        return propertyStuffHandoutMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffHandoutRespVO> getPropertyStuffHandoutPageVO(PropertyStuffHandoutPageReqVO pageReqVO) {
        PageResult<PropertyStuffHandoutDO> pageResult = propertyStuffHandoutMapper.selectPage(pageReqVO);
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList())){
            return PageResult.empty();
        }
        PageResult<PropertyStuffHandoutRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffHandoutRespVO.class );
        for (PropertyStuffHandoutRespVO respVO : result.getList()) {
            respVO.setReceiveName( codeGetName.getUserName( respVO.getReceiveUid()) );
            respVO.setDepositoryName( propertyStuffDepositoryMapper.getNameById( respVO.getDepositoryId() ) );
            LambdaQueryWrapperX<PropertyStuffProcessDO> wrapperX = new LambdaQueryWrapperX<>();
            wrapperX.eq(PropertyStuffProcessDO::getProcessCode, respVO.getProcessCode());
            respVO.setStuff(processMapper.selectList(wrapperX));
        }
        return result;
    }
}