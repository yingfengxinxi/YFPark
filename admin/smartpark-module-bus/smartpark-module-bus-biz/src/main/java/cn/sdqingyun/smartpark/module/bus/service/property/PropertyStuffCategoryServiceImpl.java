package cn.sdqingyun.smartpark.module.bus.service.property;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffCategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyStuffCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyStuffCategoryMapper;
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
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PROPERTY_STUFF_CATEGORY_NOT_EXISTS;

/**
 * 资产耗材分类 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class PropertyStuffCategoryServiceImpl implements PropertyStuffCategoryService {

    @Resource
    private PropertyStuffCategoryMapper propertyStuffCategoryMapper;

    @Override
    public Long createPropertyStuffCategory(PropertyStuffCategorySaveReqVO createReqVO) {
        //校验编号
        validateCategoryNumberExists(createReqVO.getNumber(),null);
        // 插入
        PropertyStuffCategoryDO propertyStuffCategory = BeanUtils.toBean(createReqVO, PropertyStuffCategoryDO.class);
        propertyStuffCategoryMapper.insert(propertyStuffCategory);
        // 返回
        return propertyStuffCategory.getId();
    }

    public void validateCategoryNumberExists(String number, Long id) {
        LambdaQueryWrapperX<PropertyStuffCategoryDO> queryWrapper = new LambdaQueryWrapperX<PropertyStuffCategoryDO>()
                .eq(PropertyStuffCategoryDO::getNumber, number)
                .neIfPresent(PropertyStuffCategoryDO::getId, id);  // 编辑时排除当前记录

        if (propertyStuffCategoryMapper.selectCount(queryWrapper) > 0) {
            throw new ServiceException(406, "分类编码已存在");
        }
    }

    @Override
    public void updatePropertyStuffCategory(PropertyStuffCategorySaveReqVO updateReqVO) {
        // 校验存在
        validatePropertyStuffCategoryExists(updateReqVO.getId());
        //校验编号
        validateCategoryNumberExists(updateReqVO.getNumber(),updateReqVO.getId());
        // 更新
        PropertyStuffCategoryDO updateObj = BeanUtils.toBean(updateReqVO, PropertyStuffCategoryDO.class);
        //判断是否需要禁用
        if(updateReqVO.getFlag()){
            //便利获取当前分类下的所有子分类，以及子分类下面的子分类，全部修改为禁用状态
            disableSubCategories(updateObj.getId(),updateObj.getStatus(),false);
        }



        propertyStuffCategoryMapper.updateById(updateObj);
    }

    private void disableSubCategories(Long parentId,Integer status,Boolean delFlag) {
        // 获取当前分类的子分类列表
        List<PropertyStuffCategoryDO> subCategories = propertyStuffCategoryMapper.selectList(new LambdaQueryWrapper<PropertyStuffCategoryDO>()
                .eq(PropertyStuffCategoryDO::getParentId, parentId));

        // 遍历子分类列表，将状态修改为禁用，并递归禁用其子分类
        for (PropertyStuffCategoryDO subCategory : subCategories) {
            if(delFlag){
                propertyStuffCategoryMapper.deleteById(subCategory.getId());
            }else {
                subCategory.setStatus(status); // 设置为禁用状态
                propertyStuffCategoryMapper.updateById(subCategory); // 更新子分类状态
            }
            disableSubCategories(subCategory.getId(),status,delFlag); // 递归禁用子分类
        }
    }


    @Override
    public void deletePropertyStuffCategory(Long id) {
        // 校验存在
        validatePropertyStuffCategoryExists(id);
        //删除子集
        disableSubCategories(id,null,true);
        // 删除
        propertyStuffCategoryMapper.deleteById(id);
    }

    private void validatePropertyStuffCategoryExists(Long id) {
        if (propertyStuffCategoryMapper.selectById(id) == null) {
            throw exception(PROPERTY_STUFF_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public PropertyStuffCategoryDO getPropertyStuffCategory(Long id) {
        return propertyStuffCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyStuffCategoryRespVO> getPropertyStuffCategoryPage(PropertyStuffCategoryPageReqVO pageReqVO) {
        // 查询分页结果
        PageResult<PropertyStuffCategoryDO> propertyCategoryDOPageResult = propertyStuffCategoryMapper.selectPage(pageReqVO);

        // 转换为响应对象的分页结果
        PageResult<PropertyStuffCategoryRespVO> pageResult = BeanUtils.toBean(propertyCategoryDOPageResult, PropertyStuffCategoryRespVO.class);

        // 如果分页结果为空，直接返回
        if (CollUtil.isEmpty(pageResult.getList())) {
            return pageResult;
        }

        // 批量获取父节点名称，避免每次查询都去数据库查一次
        Set<Long> parentIds = pageResult.getList().stream()
                .map(PropertyStuffCategoryRespVO::getParentId)
                .filter(parentId -> parentId != 0)
                .collect( Collectors.toSet());

        if (!parentIds.isEmpty()) {
            Map<Long, String> parentNameMap = propertyStuffCategoryMapper.selectBatchIds(parentIds).stream()
                    .collect(Collectors.toMap(PropertyStuffCategoryDO::getId, PropertyStuffCategoryDO::getName));

            // 设置父节点名称
            for (PropertyStuffCategoryRespVO vo : pageResult.getList()) {
                if (vo.getParentId() != 0) {
                    vo.setParentName(parentNameMap.get(vo.getParentId()));
                }
            }
        }

        return pageResult;
    }

    @Override
    public List<PropertyStuffCategoryRespVO> getTree() {
        // 查询所有激活状态的分类
        List<PropertyStuffCategoryDO> allCategories = propertyStuffCategoryMapper.selectList(
                new LambdaQueryWrapperX<PropertyStuffCategoryDO>().eq(PropertyStuffCategoryDO::getStatus, 1)
        );

        // 将所有分类DO转换为VO，并将其映射到Map中，方便快速查找
        Map<Long, PropertyStuffCategoryRespVO> categoryMap = allCategories.stream()
                .map(category -> BeanUtils.toBean(category, PropertyStuffCategoryRespVO.class))
                .collect( Collectors.toMap(PropertyStuffCategoryRespVO::getId, Function.identity()));

        // 获取所有顶级分类，即parentId为0的分类
        List<PropertyStuffCategoryRespVO> rootCategories = categoryMap.values().stream()
                .filter(category -> category.getParentId() == 0)
                .collect(Collectors.toList());

        // 构建树结构
        for (PropertyStuffCategoryRespVO category : categoryMap.values()) {
            if (category.getParentId() != 0) {
                PropertyStuffCategoryRespVO parentCategory = categoryMap.get(category.getParentId());
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