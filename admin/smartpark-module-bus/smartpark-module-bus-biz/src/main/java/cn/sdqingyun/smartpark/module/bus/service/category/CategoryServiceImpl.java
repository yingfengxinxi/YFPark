package cn.sdqingyun.smartpark.module.bus.service.category;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategorySubcatMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 工单分类配置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper Mapper;

    @Resource
    private CategorySubcatMapper categorySubcatMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private WorkOrderProposeMapper workOrderProposeMapper;

    @Override
    public Long create(CategorySaveReqVO createReqVO) {
        isCheckName(createReqVO);
        // 插入
        CategoryDO categoryDO = BeanUtils.toBean(createReqVO, CategoryDO.class);
        Mapper.insert(categoryDO);
        // 返回
        return categoryDO.getId();
    }

    /**
     * @param categorySaveReqVO
     */
    private void isCheckName(CategorySaveReqVO categorySaveReqVO) {
        LambdaQueryWrapperX<CategoryDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryDO::getName, categorySaveReqVO.getName());
        queryWrapperX.eq(CategoryDO::getApplication, categorySaveReqVO.getApplication());
        if (categorySaveReqVO.getId() != null) {
            queryWrapperX.apply("id !='" + categorySaveReqVO.getId() + "'");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "该大类名称已存在");
        }
    }

    @Override
    public void update(CategorySaveReqVO updateReqVO) {
        isCheckName(updateReqVO);
        // 更新
        CategoryDO updateObj = BeanUtils.toBean(updateReqVO, CategoryDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除
        Mapper.deleteById(id);
        LambdaQueryWrapperX<CategorySubcatDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategorySubcatDO::getCategoryId, id);
        categorySubcatMapper.delete(queryWrapperX);
    }

    @Override
    public CategoryDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<CategoryRespVO> getPage(CategoryPageReqVO pageReqVO) {
        LambdaQueryWrapperX<CategoryDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryDO::getApplication, pageReqVO.getApplication());
        queryWrapperX.orderByDesc(CategoryDO::getSort);
        PageResult<CategoryDO> categoryDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<CategoryRespVO> categoryRespVOPageResult = BeanUtils.toBean(categoryDOPageResult, CategoryRespVO.class);
        List<CategoryRespVO> list = categoryRespVOPageResult.getList();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(categoryRespVO -> {
                LambdaQueryWrapperX<CategorySubcatDO>queryWrapperX1=new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(CategorySubcatDO::getCategoryId,categoryRespVO.getId());
                Long count = categorySubcatMapper.selectCount(queryWrapperX1);
                categoryRespVO.setSubcatNumber(count);
                List<String>buildBindList = List.of(categoryRespVO.getBuildBind().split(","));
                StringBuilder sb = new StringBuilder();
                for (String buildBind : buildBindList) {
                    BuildDO buildDO = buildMapper.selectById(Long.valueOf(buildBind));
                    if (buildDO != null) {
                        sb.append(buildDO.getBuildName()).append(",");
                    }

                }
                String buildName = sb.toString();
                if (StringUtils.isNotEmpty(buildName)) {
                    buildName = buildName.substring(0, buildName.length() - 1);
                    categoryRespVO.setBuildBind(buildName);
                }
                //工单数量
                LambdaQueryWrapperX<WorkOrderProposeDO>queryWrapperX2=new LambdaQueryWrapperX<>();
                queryWrapperX2.eq(WorkOrderProposeDO::getCategoryId,categoryRespVO.getId());
                queryWrapperX2.eq(WorkOrderProposeDO::getApplication,categoryRespVO.getApplication());
                Long orderNumber = workOrderProposeMapper.selectCount(queryWrapperX2);
                categoryRespVO.setOrderNumber(orderNumber);
            });
        }
        return categoryRespVOPageResult;
    }

    @Override
    public List<CategoryRespVO> getList(String application,String buildBind) {
        LambdaQueryWrapperX<CategoryDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryDO::getApplication, application);
        queryWrapperX.apply("REGEXP_LIKE(build_bind, '(^|,)" + buildBind + "($|,)')");
        queryWrapperX.orderByDesc(CategoryDO::getSort);
        List<CategoryDO> categoryList = Mapper.selectList(queryWrapperX);
        List<CategoryRespVO> categoryRespVOPageResult = BeanUtils.toBean(categoryList, CategoryRespVO.class);
        if(CollectionUtils.isNotEmpty(categoryRespVOPageResult)){
            categoryRespVOPageResult.forEach(categoryRespVO -> {
                LambdaQueryWrapperX<CategorySubcatDO>queryWrapperX1=new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(CategorySubcatDO::getCategoryId,categoryRespVO.getId());
                queryWrapperX1.orderByDesc(CategorySubcatDO::getSort);
                List<CategorySubcatDO> categorySubcatList = categorySubcatMapper.selectList(queryWrapperX1);
                if(CollectionUtils.isNotEmpty(categorySubcatList)) {
                    categoryRespVO.setCategorySubcatList(categorySubcatList);
                }
            });
        }
        //只查询存在有子类的分类信息
        if(CollectionUtils.isNotEmpty(categoryRespVOPageResult)){
            return categoryRespVOPageResult.stream().filter(aa -> CollectionUtils.isNotEmpty(aa.getCategorySubcatList())).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

}