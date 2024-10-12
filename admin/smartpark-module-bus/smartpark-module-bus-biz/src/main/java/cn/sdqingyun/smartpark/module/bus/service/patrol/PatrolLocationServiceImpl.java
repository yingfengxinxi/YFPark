package cn.sdqingyun.smartpark.module.bus.service.patrol;//package cn.sdqingyun.smartpark.module.bus.service.patrol;
//
//import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
//import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
//import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolLocationPageReqVO;
//import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolLocationRespVO;
//import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolLocationSaveReqVO;
//import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolLocationDO;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyCategoryDO;
//import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.PatrolLocationMapper;
//import com.alibaba.nacos.common.utils.CollectionUtils;
//import com.alibaba.nacos.common.utils.StringUtils;
//import org.springframework.stereotype.Service;
//import jakarta.annotation.Resource;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;
//import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
//import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
//import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
//import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PATROL_LOCATION_NOT_EXISTS;
//
///**
// * 位置 Service 实现类
// *
// * @author 智慧园区
// */
//@Service
//@Validated
//public class PatrolLocationServiceImpl implements PatrolLocationService {
//
//    @Resource
//    private PatrolLocationMapper Mapper;
//
//    @Override
//    public Long create(PatrolLocationSaveReqVO createReqVO) {
//        //校验编码是否存在
//        isCheckNumber(createReqVO.getNumber(), createReqVO.getId());
//        // 插入
//        PatrolLocationDO patrolLocationDO = BeanUtils.toBean(createReqVO, PatrolLocationDO.class);
//        patrolLocationDO.setStatus("1");
//        extracted(patrolLocationDO);
//        Mapper.insert(patrolLocationDO);
//        // 返回
//        return patrolLocationDO.getId();
//    }
//
//    /**
//     * @param number
//     * @param id
//     */
//    private void isCheckNumber(String number, Long id) {
//        LambdaQueryWrapperX<PatrolLocationDO> queryWrapperX = new LambdaQueryWrapperX<>();
//        queryWrapperX.eq(PatrolLocationDO::getNumber, number);
//        if (id != null) {
//            queryWrapperX.apply("id !='" + id + "'");
//        }
//
//        int size = Mapper.selectList(queryWrapperX).size();
//        if (size >= 1) {
//            throw new ServiceException(406, "位置编码已存在请勿重复添加");
//        }
//    }
//
//    @Override
//    public void update(PatrolLocationSaveReqVO updateReqVO) {
//        //校验编码是否存在
//        isCheckNumber(updateReqVO.getNumber(), updateReqVO.getId());
//        // 校验存在
//        validateExists(updateReqVO.getId());
//        // 更新
//        PatrolLocationDO updateObj = BeanUtils.toBean(updateReqVO, PatrolLocationDO.class);
//        updateObj.setStatus("1");
//        extracted(updateObj);
//        Mapper.updateById(updateObj);
//    }
//
//    private void extracted(PatrolLocationDO patrolLocationDO) {
//        if (patrolLocationDO.getParentId() != 0) {
//            PatrolLocationDO patrolLocationParent = Mapper.selectById(patrolLocationDO.getParentId());
//            if (StringUtils.isNotEmpty(patrolLocationParent.getLevel())) {
//                patrolLocationDO.setLevel(patrolLocationParent.getLevel() + "," + patrolLocationDO.getParentId());
//                patrolLocationDO.setLevelName(patrolLocationParent.getLevelName() + "," + patrolLocationParent.getName());
//            } else {
//                patrolLocationDO.setLevel(String.valueOf(patrolLocationDO.getParentId()));
//                patrolLocationDO.setLevelName(String.valueOf(patrolLocationParent.getName()));
//            }
//
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void delete(Long id) {
//        PatrolLocationDO patrolLocationDO = Mapper.selectById(id);
//        LambdaQueryWrapperX<PatrolLocationDO> queryWrapperX = new LambdaQueryWrapperX<>();
//        queryWrapperX.apply("REGEXP_LIKE(level, '(^|,)" + patrolLocationDO.getId() + "($|,)')");
//        List<PatrolLocationDO> patrolLocationDOS = Mapper.selectList(queryWrapperX);
//        if (CollectionUtils.isNotEmpty(patrolLocationDOS)) {
//            List<Long> ids = patrolLocationDOS.stream().map(PatrolLocationDO::getId).collect(Collectors.toList());
//            Mapper.deleteBatchIds(ids);
//        }
//
//        // 校验存在
//        validateExists(id);
//        // 删除
//        Mapper.deleteById(id);
//    }
//
//    private void validateExists(Long id) {
//        if (Mapper.selectById(id) == null) {
//            throw exception(PATROL_LOCATION_NOT_EXISTS);
//        }
//    }
//
//    @Override
//    public PatrolLocationDO get(Long id) {
//        return Mapper.selectById(id);
//    }
//
//    @Override
//    public PageResult<PatrolLocationDO> getPage(PatrolLocationPageReqVO pageReqVO) {
//        LambdaQueryWrapperX<PatrolLocationDO> queryWrapperX = new LambdaQueryWrapperX<>();
//        if (StringUtils.isNotEmpty(pageReqVO.getNumber())) {
//            queryWrapperX.like(PatrolLocationDO::getNumber, pageReqVO.getNumber());
//        }
//        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
//            queryWrapperX.like(PatrolLocationDO::getName, pageReqVO.getName());
//        }
//        if (pageReqVO.getId() != null) {
//            queryWrapperX.apply("(REGEXP_LIKE(level, '(^|,)" + pageReqVO.getId() + "($|,)') or id ='" + pageReqVO.getId() + "')");
//        }
//        queryWrapperX.orderByDesc(PatrolLocationDO::getCreateTime);
//        PageResult<PatrolLocationDO> patrolLocationDOPageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
//        List<PatrolLocationDO> list = patrolLocationDOPageResult.getList();
//        if (CollectionUtils.isNotEmpty(list)) {
//            list.forEach(patrolLocationDO -> {
//                if (patrolLocationDO.getParentId() != null && patrolLocationDO.getParentId() != 0) {
//                    PatrolLocationDO patrolLocationParent = Mapper.selectById(patrolLocationDO.getParentId());
//                    patrolLocationDO.setParentName(patrolLocationParent.getName());
//                } else {
//                    patrolLocationDO.setParentName("--");
//                }
//            });
//        }
//        return patrolLocationDOPageResult;
//    }
//
//    /**
//     * 启用
//     *
//     * @param id
//     */
//    @Override
//    public void enable(Long id) {
//        PatrolLocationDO patrolLocationDO = Mapper.selectById(id);
//        LambdaQueryWrapperX<PatrolLocationDO> queryWrapperX = new LambdaQueryWrapperX<>();
//        queryWrapperX.apply("REGEXP_LIKE(level, '(^|,)" + patrolLocationDO.getId() + "($|,)')");
//        List<PatrolLocationDO> patrolLocationDOS = Mapper.selectList(queryWrapperX);
//        if (CollectionUtils.isNotEmpty(patrolLocationDOS)) {
//            patrolLocationDOS.forEach(aa -> aa.setStatus("1"));
//            Mapper.updateBatch(patrolLocationDOS);
//        }
//        patrolLocationDO.setStatus("1");
//        Mapper.updateById(patrolLocationDO);
//    }
//
//    @Override
//    public void disable(Long id) {
//        PatrolLocationDO patrolLocationDO = Mapper.selectById(id);
//        LambdaQueryWrapperX<PatrolLocationDO> queryWrapperX = new LambdaQueryWrapperX<>();
//        queryWrapperX.apply("REGEXP_LIKE(level, '(^|,)" + patrolLocationDO.getId() + "($|,)')");
//        List<PatrolLocationDO> patrolLocationDOS = Mapper.selectList(queryWrapperX);
//        if (CollectionUtils.isNotEmpty(patrolLocationDOS)) {
//            patrolLocationDOS.forEach(aa -> aa.setStatus("2"));
//            Mapper.updateBatch(patrolLocationDOS);
//        }
//        patrolLocationDO.setStatus("2");
//        Mapper.updateById(patrolLocationDO);
//    }
//
//
//    @Override
//    public List<PatrolLocationRespVO> getTree() {
//        // 查询所有激活状态的分类
//        List<PatrolLocationDO> allCategories = Mapper.selectList(
//                new LambdaQueryWrapperX<PatrolLocationDO>().eq(PatrolLocationDO::getStatus, "1")
//        );
//
//        // 将所有分类DO转换为VO，并将其映射到Map中，方便快速查找
//        Map<Long, PatrolLocationRespVO> categoryMap = allCategories.stream()
//                .map(category -> BeanUtils.toBean(category, PatrolLocationRespVO.class))
//                .collect(Collectors.toMap(PatrolLocationRespVO::getId, Function.identity()));
//
//        // 获取所有顶级分类，即parentId为0的分类
//        List<PatrolLocationRespVO> rootCategories = categoryMap.values().stream()
//                .filter(category -> category.getParentId() == 0)
//                .collect(Collectors.toList());
//
//        // 构建树结构
//        for (PatrolLocationRespVO category : categoryMap.values()) {
//            if (category.getParentId() != 0) {
//                PatrolLocationRespVO parentCategory = categoryMap.get(category.getParentId());
//                if (parentCategory != null) {
//                    // 设置父级名称
//                    category.setParentName(parentCategory.getName());
//                    // 将当前分类加入父级的子分类列表
//                    if (parentCategory.getChildren() == null) {
//                        parentCategory.setChildren(new ArrayList<>());
//                    }
//                    parentCategory.getChildren().add(category);
//                }
//            }
//        }
//
//        // 返回顶级分类（树根节点）
//        return rootCategories;
//    }
//
//    @Override
//    public void batchCreate(List<PatrolLocationSaveReqVO> createReqVOList) {
//        for (PatrolLocationSaveReqVO patrolLocationSaveReqVO : createReqVOList) {
//            create(patrolLocationSaveReqVO);
//        }
//
//    }
//}