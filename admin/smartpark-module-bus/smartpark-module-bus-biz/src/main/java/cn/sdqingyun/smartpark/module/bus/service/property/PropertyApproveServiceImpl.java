package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.LoginUser;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyApprovePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyApproveSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyApproveDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyApproveMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_APPROVE_NOT_EXISTS;

/**
 * 资产单据审批记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyApproveServiceImpl implements PropertyApproveService {

    @Resource
    private PropertyApproveMapper propertyApproveMapper;

    @Override
    public Long createPropertyApprove(PropertyApproveSaveReqVO createReqVO) {
        // 插入
        PropertyApproveDO propertyApprove = BeanUtils.toBean(createReqVO, PropertyApproveDO.class);
        propertyApproveMapper.insert(propertyApprove);
        // 返回
        return propertyApprove.getId();
    }

    @Override
    public void updatePropertyApprove(PropertyApproveSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyApproveExists(updateReqVO.getId());
        // 更新
        PropertyApproveDO updateObj = BeanUtils.toBean(updateReqVO, PropertyApproveDO.class);
        propertyApproveMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyApprove(Long id) {
        // 校验存在
        validatePropertyApproveExists(id);
        // 删除
        propertyApproveMapper.deleteById(id);
    }

    private void validatePropertyApproveExists(Long id) {
        if (propertyApproveMapper.selectById(id) == null) {
            throw exception(PROPERTY_APPROVE_NOT_EXISTS);
        }
    }

    @Override
    public PropertyApproveDO getPropertyApprove(Long id) {
        return propertyApproveMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyApproveDO> getPropertyApprovePage(PropertyApprovePageReqVO pageReqVO) {
        return propertyApproveMapper.selectPage(pageReqVO);
    }

    @Override
    public Map<String, Object> getApproveCount() {
        Map<String, Object> hashMap = new HashMap<>();
        Long loginUser = SecurityFrameworkUtils.getLoginUserId();
        if(loginUser == null){
            throw new ServiceException(406,"当前登录已过期，请重新登陆");
        }
        LambdaQueryWrapperX<PropertyApproveDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(PropertyApproveDO::getStatus, 1)
                .eq(PropertyApproveDO::getCuserUid, loginUser);
        List<PropertyApproveDO> approveDOS = propertyApproveMapper.selectList( wrapperX );
        List<PropertyApproveDO> propertyApproveDOS = propertyApproveMapper.selectList( wrapperX );
        if(CollUtil.isNotEmpty( propertyApproveDOS )){
            // 按照 processType 分组统计数量
            Map<String, Long> processTypeCounts = propertyApproveDOS.stream()
                    .collect( Collectors.groupingBy(PropertyApproveDO::getProcessType, Collectors.counting()));

            // 将结果放入 hashMap
            hashMap.putAll(processTypeCounts);
        }

        return hashMap;
    }
}