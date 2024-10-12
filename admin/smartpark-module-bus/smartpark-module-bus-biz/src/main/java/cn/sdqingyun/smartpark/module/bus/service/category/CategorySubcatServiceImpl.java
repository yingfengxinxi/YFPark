package cn.sdqingyun.smartpark.module.bus.service.category;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategorySubcatSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanEquipmentRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryLabelDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryLabelMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategorySubcatMapper;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.PostApi;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.DeptRespDTO;
import cn.sdqingyun.smartpark.module.system.api.dept.dto.PostRespDTO;
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

import java.util.Arrays;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工单分类子类信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CategorySubcatServiceImpl implements CategorySubcatService {

    @Resource
    private CategorySubcatMapper Mapper;

    @Resource
    private CategoryLabelMapper categoryLabelMapper;

    @Resource
    private PostApi postApi;

    @Resource
    private DeptApi deptApi;

    @Override
    public Long create(CategorySubcatSaveReqVO createReqVO) {
        isCheckName(createReqVO);
        // 插入
        CategorySubcatDO categorySubcatDO = BeanUtils.toBean(createReqVO, CategorySubcatDO.class);
        Mapper.insert(categorySubcatDO);
        // 返回
        return categorySubcatDO.getId();
    }

    private void isCheckName(CategorySubcatSaveReqVO categorySubcatSaveReqVO) {
        LambdaQueryWrapperX<CategorySubcatDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategorySubcatDO::getApplication, categorySubcatSaveReqVO.getApplication());
        queryWrapperX.eq(CategorySubcatDO::getCategoryId, categorySubcatSaveReqVO.getCategoryId());
        queryWrapperX.eq(CategorySubcatDO::getName, categorySubcatSaveReqVO.getName());
        queryWrapperX.apply("id !='" + categorySubcatSaveReqVO.getId() + "'");
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "该子类名称已存在,勿重复添加");
        }
    }

    @Override
    public void update(CategorySubcatSaveReqVO updateReqVO) {
        isCheckName(updateReqVO);
        // 更新
        CategorySubcatDO updateObj = BeanUtils.toBean(updateReqVO, CategorySubcatDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除
        Mapper.deleteById(id);
        LambdaQueryWrapperX<CategoryLabelDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryLabelDO::getSubcatId, id);
        categoryLabelMapper.delete(queryWrapperX);
    }


    @Override
    public CategorySubcatDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<CategorySubcatRespVO> getPage(CategorySubcatPageReqVO pageReqVO) {
        LambdaQueryWrapperX<CategorySubcatDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategorySubcatDO::getCategoryId, pageReqVO.getCategoryId());
        queryWrapperX.eq(CategorySubcatDO::getApplication, pageReqVO.getApplication());
        queryWrapperX.orderByDesc(CategorySubcatDO::getSort);
        PageResult<CategorySubcatDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<CategorySubcatRespVO> subcatRespVOPageResult = BeanUtils.toBean(pageResult, CategorySubcatRespVO.class);
        List<CategorySubcatRespVO> list = subcatRespVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(categorySubcatRespVO -> {

                getStationName(categorySubcatRespVO);

                getDeptName(categorySubcatRespVO);

                LambdaQueryWrapperX<CategoryLabelDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(CategoryLabelDO::getSubcatId, categorySubcatRespVO.getId());
                Long count = categoryLabelMapper.selectCount(queryWrapperX1);
                categorySubcatRespVO.setTagNumber(count);

                //统计工单数量
                categorySubcatRespVO.setOrderNumber(0);
                categorySubcatRespVO.setSatisfactionLevel("0.00%");
            });
        }

        return subcatRespVOPageResult;
    }


    private void getDeptName(CategorySubcatRespVO categorySubcatRespVO) {
        Long departmentId = categorySubcatRespVO.getDepartmentId();
        CommonResult<DeptRespDTO> deptResult = deptApi.getDept(departmentId);
        if (deptResult.getCode() == 0) {
            DeptRespDTO dept = deptResult.getData();
            if (dept != null) {
                categorySubcatRespVO.setDepartmentName(dept.getName());
            }
        }
    }

    private void getStationName(CategorySubcatRespVO categorySubcatRespVO) {
        if (StringUtils.isNotEmpty(categorySubcatRespVO.getStationJson())) {
            List<String> postIdStrList = List.of(categorySubcatRespVO.getStationJson().split(","));
            List<Long> postIdList = Lists.newArrayList();
            postIdStrList.forEach(postId -> postIdList.add(Long.valueOf(postId)));
            StringBuilder sb = new StringBuilder();
            CommonResult<List<PostRespDTO>> postResult = postApi.getPostList(postIdList);
            if (postResult.getCode() == 0) {
                List<PostRespDTO> postList = postResult.getData();
                if (CollectionUtils.isNotEmpty(postList)) {
                    postList.forEach(postRespDTO -> {
                        String name = postRespDTO.getName();
                        sb.append(name).append(",");
                    });
                    String postName = sb.toString();
                    if (StringUtils.isNotEmpty(postName)) {
                        categorySubcatRespVO.setStationName(postName.substring(0, postName.length() - 1));
                    }

                }
            }
        }


    }


}