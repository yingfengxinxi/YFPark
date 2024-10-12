package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserIcCardDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 住户的IC卡 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface UserIcCardService {

    /**
     * 创建住户的IC卡
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserIcCard(@Valid UserIcCardSaveReqVO createReqVO);

    /**
     * 更新住户的IC卡
     *
     * @param updateReqVO 更新信息
     */
    void updateUserIcCard(@Valid UserIcCardSaveReqVO updateReqVO);

    /**
     * 删除住户的IC卡
     *
     * @param id 编号
     */
    void deleteUserIcCard(Long id);

    /**
     * 获得住户的IC卡
     *
     * @param id 编号
     * @return 住户的IC卡
     */
    UserIcCardDO getUserIcCard(Long id);

    /**
     * 获得住户的IC卡分页
     *
     * @param pageReqVO 分页查询
     * @return 住户的IC卡分页
     */
    PageResult<UserIcCardDO> getUserIcCardPage(UserIcCardPageReqVO pageReqVO);

}