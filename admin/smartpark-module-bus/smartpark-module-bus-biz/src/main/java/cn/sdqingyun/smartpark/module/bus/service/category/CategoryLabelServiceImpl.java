package cn.sdqingyun.smartpark.module.bus.service.category;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryLabelPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryLabelRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryLabelSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryLabelDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryLabelMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工单分类标签配置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CategoryLabelServiceImpl implements CategoryLabelService {

    @Resource
    private CategoryLabelMapper Mapper;

    @Override
    public Long create(CategoryLabelSaveReqVO createReqVO) {
        isCheckName(createReqVO);
        // 插入
        CategoryLabelDO categoryLabelDO = BeanUtils.toBean(createReqVO, CategoryLabelDO.class);
        Mapper.insert(categoryLabelDO);
        // 返回
        return categoryLabelDO.getId();
    }

    private void isCheckName(CategoryLabelSaveReqVO categoryLabelSaveReqVO) {
        LambdaQueryWrapperX<CategoryLabelDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryLabelDO::getApplication, categoryLabelSaveReqVO.getApplication());
        queryWrapperX.eq(CategoryLabelDO::getSubcatId, categoryLabelSaveReqVO.getSubcatId());
        queryWrapperX.eq(CategoryLabelDO::getName, categoryLabelSaveReqVO.getName());
        queryWrapperX.apply("id !='" + categoryLabelSaveReqVO.getId() + "'");
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "标签名称已存在");
        }
    }
    @Override
    public void update(CategoryLabelSaveReqVO updateReqVO) {
        isCheckName(updateReqVO);
        // 更新
        CategoryLabelDO updateObj = BeanUtils.toBean(updateReqVO, CategoryLabelDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public CategoryLabelDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<CategoryLabelRespVO> getPage(CategoryLabelPageReqVO pageReqVO) {
        LambdaQueryWrapperX<CategoryLabelDO>queryWrapperX=new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryLabelDO::getSubcatId,pageReqVO.getSubcatId());
        queryWrapperX.eq(CategoryLabelDO::getApplication,pageReqVO.getApplication());
        queryWrapperX.orderByDesc(CategoryLabelDO::getSort);
        PageResult<CategoryLabelDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<CategoryLabelRespVO> categoryLabelRespVOPageResult = BeanUtils.toBean(pageResult, CategoryLabelRespVO.class);
        List<CategoryLabelRespVO> list = categoryLabelRespVOPageResult.getList();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(categoryLabelRespVO -> {
                //统计使用频率
                categoryLabelRespVO.setUsageFrequency(0);
            });
        }
        return categoryLabelRespVOPageResult;
    }

}