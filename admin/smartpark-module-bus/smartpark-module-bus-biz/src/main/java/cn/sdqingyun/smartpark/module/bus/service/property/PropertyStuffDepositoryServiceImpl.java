package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffDepositoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffDepositoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffDepositorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDepositoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffDepositoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_DEPOSITORY_NOT_EXISTS;

/**
 * 耗材档案信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffDepositoryServiceImpl implements PropertyStuffDepositoryService {

    @Resource
    private PropertyStuffDepositoryMapper propertyStuffDepositoryMapper;

    @Override
    public Long createPropertyStuffDepository(PropertyStuffDepositorySaveReqVO createReqVO) {
        // 插入
        PropertyStuffDepositoryDO propertyStuffDepository = BeanUtils.toBean(createReqVO, PropertyStuffDepositoryDO.class);
        propertyStuffDepositoryMapper.insert(propertyStuffDepository);
        // 返回
        return propertyStuffDepository.getId();
    }

    @Override
    public void updatePropertyStuffDepository(PropertyStuffDepositorySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffDepositoryExists(updateReqVO.getId());
        // 更新
        PropertyStuffDepositoryDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffDepositoryDO.class);
        propertyStuffDepositoryMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyStuffDepository(Long id) {
        // 校验存在
        validatePropertyStuffDepositoryExists(id);
        // 删除
        propertyStuffDepositoryMapper.deleteById(id);
    }

    private void validatePropertyStuffDepositoryExists(Long id) {
        if (propertyStuffDepositoryMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_DEPOSITORY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffDepositoryDO getPropertyStuffDepository(Long id) {
        return propertyStuffDepositoryMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffDepositoryRespVO> getPropertyStuffDepositoryPage(PropertyStuffDepositoryPageReqVO pageReqVO) {

        // 查询分页结果
        PageResult<PropertyStuffDepositoryDO> propertyCategoryDOPageResult = propertyStuffDepositoryMapper.selectPage(pageReqVO);

        // 转换为响应对象的分页结果
        PageResult<PropertyStuffDepositoryRespVO> pageResult = BeanUtils.toBean(propertyCategoryDOPageResult, PropertyStuffDepositoryRespVO.class);

        // 如果分页结果为空，直接返回
        if (CollUtil.isEmpty(pageResult.getList())) {
            return pageResult;
        }

        // 批量获取父节点名称，避免每次查询都去数据库查一次
        Set<Long> parentIds = pageResult.getList().stream()
                .map(PropertyStuffDepositoryRespVO::getParentId)
                .filter(parentId -> parentId != 0)
                .collect( Collectors.toSet());

        if (!parentIds.isEmpty()) {
            Map<Long, String> parentNameMap = propertyStuffDepositoryMapper.selectBatchIds(parentIds).stream()
                    .collect(Collectors.toMap(PropertyStuffDepositoryDO::getId, PropertyStuffDepositoryDO::getName));

            // 设置父节点名称
            for (PropertyStuffDepositoryRespVO vo : pageResult.getList()) {
                if (vo.getParentId() != 0) {
                    vo.setParentName(parentNameMap.get(vo.getParentId()));
                }
            }
        }

        return pageResult;
    }

    @Override
    public List<PropertyStuffDepositoryRespVO> getTree() {
        // 查询所有激活状态的分类
        List<PropertyStuffDepositoryDO> allCategories = propertyStuffDepositoryMapper.selectList(
                new LambdaQueryWrapperX<PropertyStuffDepositoryDO>().eq(PropertyStuffDepositoryDO::getStatus, 1)
        );

        // 将所有分类DO转换为VO，并将其映射到Map中，方便快速查找
        Map<Long, PropertyStuffDepositoryRespVO> categoryMap = allCategories.stream()
                .map(category -> BeanUtils.toBean(category, PropertyStuffDepositoryRespVO.class))
                .collect( Collectors.toMap(PropertyStuffDepositoryRespVO::getId, Function.identity()));

        // 获取所有顶级分类，即parentId为0的分类
        List<PropertyStuffDepositoryRespVO> rootCategories = categoryMap.values().stream()
                .filter(category -> category.getParentId() == 0)
                .collect(Collectors.toList());

        // 构建树结构
        for (PropertyStuffDepositoryRespVO category : categoryMap.values()) {
            if (category.getParentId() != 0) {
                PropertyStuffDepositoryRespVO parentCategory = categoryMap.get(category.getParentId());
                if (parentCategory != null) {
                    // 设置父级名称
                    category.setParentName(parentCategory.getName());
                    // 将当前分类加入父级的子分类列表
                    if (parentCategory.getChildren() == null) {
                        parentCategory.setChildren(new ArrayList<>());
                    }
                    parentCategory.getChildren().add(category);
                }
            }
        }

        // 返回顶级分类（树根节点）
        return rootCategories;
    }
}