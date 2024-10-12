package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.VillageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageInsertReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillagePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.CountDataVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.ProjectOverviewVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.BuildArrReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.BuildArrRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import jakarta.validation.Valid;

/**
 * 社区 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface VillageService {

    /**
     * 创建社区
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVillage(@Valid VillageSaveReqVO createReqVO);

    /**
     * 更新社区
     *
     * @param updateReqVO 更新信息
     */
    void updateVillage(@Valid VillageSaveReqVO updateReqVO);

    /**
     * 删除社区
     *
     * @param id 编号
     */
    void deleteVillage(Long id);

    /**
     * 获得社区
     *
     * @param id 编号
     * @return 社区
     */
    VillageDO getVillage(Long id);

    /**
     * 获得社区分页
     *
     * @param pageReqVO 分页查询
     * @return 社区分页
     */
    PageResult<VillageDO> getVillagePage(VillagePageReqVO pageReqVO);

    /**
     * 保存创建社区
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String saveVillage(@Valid VillageInsertReqVO createReqVO);

    /**
     * @return cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.BuildArrRespVO
     * @Author SUNk
     * @Description 获取项目和楼宇列表
     * @Date 17:13 2024/6/21
     * @Param [reqVO]
     **/
    BuildArrRespVO getVillageAndBuild(BuildArrReqVO reqVO);

    /**
     * 根据园区名称获取id
     *
     * @param parkName
     * @return
     */
    Long getParkNameId(String parkName);

    /**
     * 获取项目楼宇列表
     *
     * @param villageReqVO
     * @return
     */
    BuildArrRespVO villageAndBuildList(VillageReqVO villageReqVO);

    /**
     * 获取园区列表统计
     *
     * @param villageReqVO
     * @return
     */
    CountDataVO projectManage(VillageReqVO villageReqVO);

    /**
     * 获取项目概况统计
     *
     * @param id
     * @return
     */
    ProjectOverviewVO projectOverview(Long id);
}