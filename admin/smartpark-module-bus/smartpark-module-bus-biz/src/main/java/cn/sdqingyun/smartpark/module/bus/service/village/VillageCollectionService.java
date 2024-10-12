package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageCollectionDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 项目集合表，方便快速选择 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface VillageCollectionService {

    /**
     * 创建项目集合表，方便快速选择
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVillageCollection(@Valid VillageCollectionSaveReqVO createReqVO);

    /**
     * 更新项目集合表，方便快速选择
     *
     * @param updateReqVO 更新信息
     */
    void updateVillageCollection(@Valid VillageCollectionSaveReqVO updateReqVO);

    /**
     * 删除项目集合表，方便快速选择
     *
     * @param id 编号
     */
    void deleteVillageCollection(Long id);

    /**
     * 获得项目集合表，方便快速选择
     *
     * @param id 编号
     * @return 项目集合表，方便快速选择
     */
    VillageCollectionDO getVillageCollection(Long id);

    /**
     * 获得项目集合表，方便快速选择分页
     *
     * @param pageReqVO 分页查询
     * @return 项目集合表，方便快速选择分页
     */
    PageResult<VillageCollectionDO> getVillageCollectionPage(VillageCollectionPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获得项目结合列表
    * @Date 17:40 2024/6/21
    * @Param [reqVO]
    * @return java.util.List<cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionRespVO>
    **/
    List<VillageCollectionRespVO> getVillageCollectionList(VillageCollectionRespVO reqVO);

    /**
     * 获得项目集合表(包含楼宇名称）
     *
     * @param pageReqVO 分页查询
     * @return 项目集合表，方便快速选择分页
     */
    PageResult<VillageCollectionRespVO> getVillageCollectionRespVOPage(VillageCollectionPageReqVO pageReqVO);

}