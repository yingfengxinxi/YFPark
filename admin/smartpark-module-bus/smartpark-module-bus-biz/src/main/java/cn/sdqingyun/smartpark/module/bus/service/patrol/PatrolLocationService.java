package cn.sdqingyun.smartpark.module.bus.service.patrol;//package cn.sdqingyun.smartpark.module.bus.service.patrol;
//
//import java.util.*;
//
//import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolLocationPageReqVO;
//import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolLocationRespVO;
//import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolLocationSaveReqVO;
//import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.PropertyCategoryRespVO;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolLocationDO;
//import jakarta.validation.*;
//import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
//import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
//
///**
// * 位置 Service 接口
// *
// * @author 智慧园区
// */
//public interface PatrolLocationService {
//
//    /**
//     * 创建位置
//     *
//     * @param createReqVO 创建信息
//     * @return 编号
//     */
//    Long create(@Valid PatrolLocationSaveReqVO createReqVO);
//
//    /**
//     * 更新位置
//     *
//     * @param updateReqVO 更新信息
//     */
//    void update(@Valid PatrolLocationSaveReqVO updateReqVO);
//
//    /**
//     * 删除位置
//     *
//     * @param id 编号
//     */
//    void delete(Long id);
//
//    /**
//     * 获得位置
//     *
//     * @param id 编号
//     * @return 位置
//     */
//    PatrolLocationDO get(Long id);
//
//    /**
//     * 获得位置分页
//     *
//     * @param pageReqVO 分页查询
//     * @return 位置分页
//     */
//    PageResult<PatrolLocationDO> getPage(PatrolLocationPageReqVO pageReqVO);
//
//    /**
//     * @param id
//     */
//    void enable(Long id);
//
//    /**
//     * @param id
//     */
//    void disable(Long id);
//
//    /**
//     * 获得位置树
//     *
//     * @return
//     */
//    List<PatrolLocationRespVO> getTree();
//
//    /**
//     *
//     * @param createReqVOList
//     */
//    void batchCreate(List<PatrolLocationSaveReqVO> createReqVOList);
//}