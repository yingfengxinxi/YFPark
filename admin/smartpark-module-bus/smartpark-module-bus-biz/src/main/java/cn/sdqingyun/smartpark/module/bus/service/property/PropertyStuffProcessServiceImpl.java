package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.LoginUser;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffProcessPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffProcessRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffProcessSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyApproveDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffProcessDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_PROCESS_NOT_EXISTS;

/**
 * 耗材业务流程 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffProcessServiceImpl implements PropertyStuffProcessService {

    @Resource
    private PropertyStuffProcessMapper propertyStuffProcessMapper;
    @Resource
    private PropertyStuffMapper propertyStuffMapper;
    @Resource
    private PropertyDepositoryMapper propertyDepositoryMapper;
    @Resource
    private PropertyStuffStockMapper propertyStuffStockMapper;
    @Resource
    private PropertyStuffCategoryMapper propertyStuffCategoryMapper;
    @Override
    public Long createPropertyStuffProcess(PropertyStuffProcessSaveReqVO createReqVO) {
        // 插入
        PropertyStuffProcessDO propertyStuffProcess = BeanUtils.toBean(createReqVO, PropertyStuffProcessDO.class);
        propertyStuffProcessMapper.insert(propertyStuffProcess);
        // 返回
        return propertyStuffProcess.getId();
    }

    @Override
    public void updatePropertyStuffProcess(PropertyStuffProcessSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffProcessExists(updateReqVO.getId());
        // 更新
        PropertyStuffProcessDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffProcessDO.class);
        propertyStuffProcessMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffProcess(Long id) {
        // 校验存在
        validatePropertyStuffProcessExists(id);
        // 删除
        propertyStuffProcessMapper.deleteById(id);
    }

    private void validatePropertyStuffProcessExists(Long id) {
        if (propertyStuffProcessMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_PROCESS_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffProcessDO getPropertyStuffProcess(Long id) {
        return propertyStuffProcessMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffProcessDO> getPropertyStuffProcessPage(PropertyStuffProcessPageReqVO pageReqVO) {
        return propertyStuffProcessMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PropertyStuffProcessRespVO> getPropertyStuffProcessPageVO(PropertyStuffProcessPageReqVO pageReqVO) {
        PageResult<PropertyStuffProcessDO> pageResult = propertyStuffProcessMapper.selectPage( pageReqVO );
        if(pageResult == null || CollUtil.isEmpty( pageResult.getList() )){
            return PageResult.empty();
        }
        PageResult<PropertyStuffProcessRespVO> result = BeanUtils.toBean( pageResult, PropertyStuffProcessRespVO.class );
        for (PropertyStuffProcessRespVO vo : result.getList()) {
            PropertyStuffDO propertyStuffDO = propertyStuffMapper.selectById( vo.getStuffId() );
            if(propertyStuffDO != null){
                vo.setStuff(propertyStuffDO);
                vo.setCategoryName(propertyStuffCategoryMapper.selectById(propertyStuffDO.getCategoryId()).getName());
            }
            vo.setDepository( propertyDepositoryMapper.selectById( vo.getDepositoryId()) );
            vo.setStock( propertyStuffStockMapper.selectById( vo.getCuserUid()) );
        }
        return result;
    }

    @Override
    public Map<String, Object> getProcessCount() {
        Map<String, Object> hashMap = new HashMap<>();
        Long loginUser = SecurityFrameworkUtils.getLoginUserId();
        if(loginUser == null){
            throw new ServiceException(406,"当前登录已过期，请重新登陆");
        }
        LambdaQueryWrapperX<PropertyStuffProcessDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(PropertyStuffProcessDO::getStatus, 1)
                .eq(PropertyStuffProcessDO::getCuserUid, loginUser);
        List<PropertyStuffProcessDO> propertyApproveDOS = propertyStuffProcessMapper.selectList( wrapperX );
        if(CollUtil.isNotEmpty( propertyApproveDOS )){
            // 按照 processType 分组统计数量
            Map<String, Long> processTypeCounts = propertyApproveDOS.stream()
                    .collect( Collectors.groupingBy(PropertyStuffProcessDO::getProcessType, Collectors.counting()));

            // 将结果放入 hashMap
            hashMap.putAll(processTypeCounts);
        }

        return hashMap;
    }
}