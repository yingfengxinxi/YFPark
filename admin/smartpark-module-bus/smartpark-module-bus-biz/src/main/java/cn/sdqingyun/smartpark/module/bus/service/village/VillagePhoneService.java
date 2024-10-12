package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillagePhoneDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目电话 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface VillagePhoneService {

    /**
     * 创建项目电话
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVillagePhone(@Valid VillagePhoneSaveReqVO createReqVO);

    /**
     * 更新项目电话
     *
     * @param updateReqVO 更新信息
     */
    void updateVillagePhone(@Valid VillagePhoneSaveReqVO updateReqVO);

    /**
     * 删除项目电话
     *
     * @param id 编号
     */
    void deleteVillagePhone(Long id);

    /**
     * 获得项目电话
     *
     * @param id 编号
     * @return 项目电话
     */
    VillagePhoneDO getVillagePhone(Long id);

    /**
     * 获得项目电话分页
     *
     * @param pageReqVO 分页查询
     * @return 项目电话分页
     */
    PageResult<VillagePhoneDO> getVillagePhonePage(VillagePhonePageReqVO pageReqVO);

}