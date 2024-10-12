package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageUserSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel.VillageUserImportExcelVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageUserDO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 项目用户/租客 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface VillageUserService {

    /**
     * 创建项目用户/租客
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVillageUser(@Valid VillageUserSaveReqVO createReqVO);

    /**
     * 更新项目用户/租客
     *
     * @param updateReqVO 更新信息
     */
    void updateVillageUser(@Valid VillageUserSaveReqVO updateReqVO);

    /**
     * 删除项目用户/租客
     *
     * @param id 编号
     */
    void deleteVillageUser(Long id);

    /**
     * 获得项目用户/租客
     *
     * @param id 编号
     * @return 项目用户/租客
     */
    VillageUserDO getVillageUser(Long id);

    /**
     * 获得项目用户/租客分页
     *
     * @param pageReqVO 分页查询
     * @return 项目用户/租客分页
     */
    PageResult<VillageUserDO> getVillageUserPage(VillageUserPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 导入员工列表
    * @Date 15:53 2024/7/9
    * @Param [list, response]
    * @return void
    **/
    Boolean importExcel(List<VillageUserImportExcelVO> list, HttpServletResponse response);

    /**
     * 获得项目用户/租客分页
     *
     * @param pageReqVO 分页查询
     * @return 项目用户/租客分页
     */
    PageResult<VillageUserRespVO> getVillageUserPageVO(VillageUserPageReqVO pageReqVO);

    /**
     *
     * @param phone
     * @return
     */
    List<VillageUserDO> getByPhoneOwnerList(String phone);
}