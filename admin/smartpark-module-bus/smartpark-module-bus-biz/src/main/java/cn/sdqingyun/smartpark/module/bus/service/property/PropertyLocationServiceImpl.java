package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLocationPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLocationRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyLocationSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLocationMapper;
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
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_LOCATION_NOT_EXISTS;

/**
 * 位置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyLocationServiceImpl implements PropertyLocationService {

    @Resource
    private PropertyLocationMapper propertyLocationMapper;

    @Override
    public Long createPropertyLocation(PropertyLocationSaveReqVO createReqVO) {

        validateLocationNumberExists(createReqVO.getNumber(), null);
        // 插入
        PropertyLocationDO propertyLocation = BeanUtils.toBean(createReqVO, PropertyLocationDO.class);
        propertyLocationMapper.insert(propertyLocation);
        // 返回
        return propertyLocation.getId();
    }

    public void validateLocationNumberExists(String number, Long id) {
        LambdaQueryWrapperX<PropertyLocationDO> queryWrapper = new LambdaQueryWrapperX<PropertyLocationDO>()
                .eq(PropertyLocationDO::getNumber, number)
                .neIfPresent(PropertyLocationDO::getId, id);  // 编辑时排除当前记录

        if (propertyLocationMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException(406, "分类编码已存在");
        }
    }

    @Override
    public void updatePropertyLocation(PropertyLocationSaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyLocationExists(updateReqVO.getId());

        validateLocationNumberExists(updateReqVO.getNumber(), updateReqVO.getId());
        // 更新
        PropertyLocationDO updateObj = BeanUtils.toBean(updateReqVO, PropertyLocationDO.class);
        propertyLocationMapper.updateById(updateObj);
    }

    @Override
    public void deletePropertyLocation(Long id) {
        // 校验存在
        validatePropertyLocationExists(id);
        // 删除
        propertyLocationMapper.deleteById(id);
    }

    private void validatePropertyLocationExists(Long id) {
        if (propertyLocationMapper.selectById(id) == null) {
            throw exception(PROPERTY_LOCATION_NOT_EXISTS);
        }
    }

    @Override
    public PropertyLocationDO getPropertyLocation(Long id) {
        return propertyLocationMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyLocationDO> getPropertyLocationPage(PropertyLocationPageReqVO pageReqVO) {
        return propertyLocationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PropertyLocationRespVO> getTree() {
        // 查询所有激活状态的分类
        List<PropertyLocationDO> allCategories = propertyLocationMapper.selectList(
                new LambdaQueryWrapperX<PropertyLocationDO>().eq(PropertyLocationDO::getStatus, 1)
        );

        // 将所有分类DO转换为VO，并将其映射到Map中，方便快速查找
        Map<Long, PropertyLocationRespVO> categoryMap = allCategories.stream()
                .map(category -> BeanUtils.toBean(category, PropertyLocationRespVO.class))
                .collect( Collectors.toMap(PropertyLocationRespVO::getId, Function.identity()));

        // 获取所有顶级分类，即parentId为0的分类
        List<PropertyLocationRespVO> rootCategories = categoryMap.values().stream()
                .filter(category -> category.getParentId() == 0)
                .collect(Collectors.toList());

        // 构建树结构
        for (PropertyLocationRespVO category : categoryMap.values()) {
            if (category.getParentId() != 0) {
                PropertyLocationRespVO parentCategory = categoryMap.get(category.getParentId());
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

    @Override
    public PageResult<PropertyLocationRespVO> getPropertyLocationPageVO(PropertyLocationPageReqVO pageReqVO) {
        // 查询分页结果
        PageResult<PropertyLocationDO> propertyCategoryDOPageResult = propertyLocationMapper.selectPage(pageReqVO);

        // 转换为响应对象的分页结果
        PageResult<PropertyLocationRespVO> pageResult = BeanUtils.toBean(propertyCategoryDOPageResult, PropertyLocationRespVO.class);

        // 如果分页结果为空，直接返回
        if (CollUtil.isEmpty(pageResult.getList())) {
            return pageResult;
        }

        // 批量获取父节点名称，避免每次查询都去数据库查一次
        Set<Long> parentIds = pageResult.getList().stream()
                .map(PropertyLocationRespVO::getParentId)
                .filter(parentId -> parentId != 0)
                .collect(Collectors.toSet());

        if (!parentIds.isEmpty()) {
            Map<Long, String> parentNameMap = propertyLocationMapper.selectBatchIds(parentIds).stream()
                    .collect(Collectors.toMap(PropertyLocationDO::getId, PropertyLocationDO::getName));

            // 设置父节点名称
            for (PropertyLocationRespVO vo : pageResult.getList()) {
                if (vo.getParentId() != 0) {
                    vo.setParentName(parentNameMap.get(vo.getParentId()));
                }
            }
        }

        return pageResult;
    }
}