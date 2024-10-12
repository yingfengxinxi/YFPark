package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build.BuildLayerDataVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build.BuildLayerReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.CountBuildDataVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import jakarta.validation.Valid;

/**
 * 项目楼栋 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface BuildService {

    /**
     * 创建项目楼栋
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBuild(@Valid BuildSaveReqVO createReqVO);

    /**
     * 更新项目楼栋
     *
     * @param updateReqVO 更新信息
     */
    void updateBuild(@Valid BuildSaveReqVO updateReqVO);

    /**
     * 删除项目楼栋
     *
     * @param id 编号
     */
    void deleteBuild(Long id);

    /**
     * 获得项目楼栋
     *
     * @param id 编号
     * @return 项目楼栋
     */
    BuildRespVO getBuild(Long id);

    /**
     *
     * @param villageId
     * @param buildName
     * @return
     */
    Long getByBuildNameId(Long villageId,String buildName);

    /**
     * 获得项目楼栋分页
     *
     * @param pageReqVO 分页查询
     * @return 项目楼栋分页
     */
    PageResult<BuildDO> getBuildPage(BuildPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 建筑列表统计
    * @Date 9:35 2024/6/27
    * @Param [buildReqVO]
    * @return cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.CountBuildDataVO
    **/
    CountBuildDataVO projectBuild(BuildReqVO buildReqVO);

    /**
    * @Author SUNk
    * @Description 剖面图查询
    * @Date 18:13 2024/6/27
    * @Param [buildLayerReqVO]
    * @return cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build.BuildLayerDataVO
    **/
    BuildLayerDataVO projectProfile(BuildLayerReqVO buildLayerReqVO);

    /**
     * 获得项目楼栋分页(包含建筑名称)
     *
     * @param pageReqVO 分页查询
     * @return 项目楼栋分页
     */
    PageResult<BuildRespVO> getBuildVOPage(BuildPageReqVO pageReqVO);
}