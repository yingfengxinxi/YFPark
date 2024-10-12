package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ThirdButtDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 第三方数据对接（目前用于智慧社区系统，全功能版） Service 接口
 *
 * @author 智慧园区管理员
 */
public interface ThirdButtService {

    /**
     * 创建第三方数据对接（目前用于智慧社区系统，全功能版）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createThirdButt(@Valid ThirdButtSaveReqVO createReqVO);

    /**
     * 更新第三方数据对接（目前用于智慧社区系统，全功能版）
     *
     * @param updateReqVO 更新信息
     */
    void updateThirdButt(@Valid ThirdButtSaveReqVO updateReqVO);

    /**
     * 删除第三方数据对接（目前用于智慧社区系统，全功能版）
     *
     * @param id 编号
     */
    void deleteThirdButt(Long id);

    /**
     * 获得第三方数据对接（目前用于智慧社区系统，全功能版）
     *
     * @param id 编号
     * @return 第三方数据对接（目前用于智慧社区系统，全功能版）
     */
    ThirdButtDO getThirdButt(Long id);

    /**
     * 获得第三方数据对接（目前用于智慧社区系统，全功能版）分页
     *
     * @param pageReqVO 分页查询
     * @return 第三方数据对接（目前用于智慧社区系统，全功能版）分页
     */
    PageResult<ThirdButtDO> getThirdButtPage(ThirdButtPageReqVO pageReqVO);

}