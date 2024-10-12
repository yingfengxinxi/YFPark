package cn.sdqingyun.smartpark.module.bus.service.user;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.user.SpercialSettingDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构用户自定义操作配置 Service 接口
 *
 * @author 智慧园区
 */
public interface SpercialSettingService {

    /**
     * 创建机构用户自定义操作配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSpercialSetting(@Valid SpercialSettingSaveReqVO createReqVO);

    /**
     * 更新机构用户自定义操作配置
     *
     * @param updateReqVO 更新信息
     */
    void updateSpercialSetting(@Valid SpercialSettingSaveReqVO updateReqVO);

    /**
     * 删除机构用户自定义操作配置
     *
     * @param id 编号
     */
    void deleteSpercialSetting(Long id);

    /**
     * 获得机构用户自定义操作配置
     *
     * @param id 编号
     * @return 机构用户自定义操作配置
     */
    SpercialSettingDO getSpercialSetting(Long id);

    /**
     * 获得机构用户自定义操作配置分页
     *
     * @param pageReqVO 分页查询
     * @return 机构用户自定义操作配置分页
     */
    PageResult<SpercialSettingDO> getSpercialSettingPage(SpercialSettingPageReqVO pageReqVO);

}