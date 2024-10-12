package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyCategoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_CATEGORY_NOT_EXISTS;

/**
 * 资产分类 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyCategoryServiceImpl implements PropertyCategoryService {

    @Resource
    private PropertyCategoryMapper propertyCategoryMapper;

    @Override
    public Long createPropertyCategory(PropertyCategorySaveReqVO createReqVO) {
        validateCategoryNumberExists(createReqVO.getNumber(),null);
        // 插入
        PropertyCategoryDO propertyCategory = BeanUtils.toBean(createReqVO, PropertyCategoryDO.class);
        propertyCategoryMapper.insert(propertyCategory);
        // 返回
        return propertyCategory.getId();
    }

    public void validateCategoryNumberExists(String number, Long id) {
        LambdaQueryWrapperX<PropertyCategoryDO> queryWrapper = new LambdaQueryWrapperX<PropertyCategoryDO>()
                .eq(PropertyCategoryDO::getNumber, number)
                .neIfPresent(PropertyCategoryDO::getId, id);  // 编辑时排除当前记录

        if (propertyCategoryMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException(406, "分类编码已存在");
        }
    }

    @Override
    public void updatePropertyCategory(PropertyCategorySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyCategoryExists(updateReqVO.getId());
        validateCategoryNumberExists(updateReqVO.getNumber(),updateReqVO.getId());
        // 更新
        PropertyCategoryDO updateObj = BeanUtils.toBean(updateReqVO, PropertyCategoryDO.class);
        //如果是禁用，则需要将所有下面的一块进行禁用
        if(updateReqVO.getFlag()){
            //便利获取当前分类下的所有子分类，以及子分类下面的子分类，全部修改为禁用状态
            disableSubCategories(updateObj.getId(),updateObj.getStatus(),false);
        }

        propertyCategoryMapper.updateById(updateObj);
    }

    private void disableSubCategories(Long parentId,Integer status,Boolean delFlag) {
        // 获取当前分类的子分类列表
        List<PropertyCategoryDO> subCategories = propertyCategoryMapper.selectList(new LambdaQueryWrapper<PropertyCategoryDO>()
                .eq(PropertyCategoryDO::getParentId, parentId));

        // 遍历子分类列表，将状态修改为禁用，并递归禁用其子分类
        for (PropertyCategoryDO subCategory : subCategories) {
            if(delFlag){
                propertyCategoryMapper.deleteById(subCategory.getId());
            }else {
                subCategory.setStatus(status); // 设置为禁用状态
                propertyCategoryMapper.updateById(subCategory); // 更新子分类状态
            }
            disableSubCategories(subCategory.getId(),status,delFlag); // 递归禁用子分类
        }
    }

    @Override
    public void deletePropertyCategory(Long id) {
        // 校验存在
        validatePropertyCategoryExists(id);

        //删除子集
        disableSubCategories(id,null,true);
        // 删除
        propertyCategoryMapper.deleteById(id);
    }

    private void validatePropertyCategoryExists(Long id) {
        if (propertyCategoryMapper.selectById(id) == null) {
            throw exception(PROPERTY_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyCategoryDO getPropertyCategory(Long id) {
        return propertyCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyCategoryRespVO> getPropertyCategoryPage(PropertyCategoryPageReqVO pageReqVO) {
        // 查询分页结果
        PageResult<PropertyCategoryDO> propertyCategoryDOPageResult = propertyCategoryMapper.selectPage(pageReqVO);

        // 转换为响应对象的分页结果
        PageResult<PropertyCategoryRespVO> pageResult = BeanUtils.toBean(propertyCategoryDOPageResult, PropertyCategoryRespVO.class);

        // 如果分页结果为空，直接返回
        if (CollUtil.isEmpty(pageResult.getList())) {
            return pageResult;
        }

        // 批量获取父节点名称，避免每次查询都去数据库查一次
        Set<Long> parentIds = pageResult.getList().stream()
                .map(PropertyCategoryRespVO::getParentId)
                .filter(parentId -> parentId != 0)
                .collect(Collectors.toSet());

        if (!parentIds.isEmpty()) {
            Map<Long, String> parentNameMap = propertyCategoryMapper.selectBatchIds(parentIds).stream()
                    .collect(Collectors.toMap(PropertyCategoryDO::getId, PropertyCategoryDO::getName));

            // 设置父节点名称
            for (PropertyCategoryRespVO vo : pageResult.getList()) {
                if (vo.getParentId() != 0) {
                    vo.setParentName(parentNameMap.get(vo.getParentId()));
                }
            }
        }

        return pageResult;
    }

    @Override
    public List<PropertyCategoryRespVO> getTree() {
        // 查询所有激活状态的分类
        List<PropertyCategoryDO> allCategories = propertyCategoryMapper.selectList(
                new LambdaQueryWrapperX<PropertyCategoryDO>().eq(PropertyCategoryDO::getStatus, 1)
        );

        // 将所有分类DO转换为VO，并将其映射到Map中，方便快速查找
        Map<Long, PropertyCategoryRespVO> categoryMap = allCategories.stream()
                .map(category -> BeanUtils.toBean(category, PropertyCategoryRespVO.class))
                .collect( Collectors.toMap(PropertyCategoryRespVO::getId, Function.identity()));

        // 获取所有顶级分类，即parentId为0的分类
        List<PropertyCategoryRespVO> rootCategories = categoryMap.values().stream()
                .filter(category -> category.getParentId() == 0)
                .collect(Collectors.toList());

        // 构建树结构
        for (PropertyCategoryRespVO category : categoryMap.values()) {
            if (category.getParentId() != 0) {
                PropertyCategoryRespVO parentCategory = categoryMap.get(category.getParentId());
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