package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserSystemFieldExtendDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 用户扩展信息自定义系统设置 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface UserSystemFieldExtendService {

    /**
     * 创建用户扩展信息自定义系统设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserSystemFieldExtend(@Valid UserSystemFieldExtendSaveReqVO createReqVO);

    /**
     * 更新用户扩展信息自定义系统设置
     *
     * @param updateReqVO 更新信息
     */
    void updateUserSystemFieldExtend(@Valid UserSystemFieldExtendSaveReqVO updateReqVO);

    /**
     * 删除用户扩展信息自定义系统设置
     *
     * @param id 编号
     */
    void deleteUserSystemFieldExtend(Long id);

    /**
     * 获得用户扩展信息自定义系统设置
     *
     * @param id 编号
     * @return 用户扩展信息自定义系统设置
     */
    UserSystemFieldExtendDO getUserSystemFieldExtend(Long id);

    /**
     * 获得用户扩展信息自定义系统设置分页
     *
     * @param pageReqVO 分页查询
     * @return 用户扩展信息自定义系统设置分页
     */
    PageResult<UserSystemFieldExtendDO> getUserSystemFieldExtendPage(UserSystemFieldExtendPageReqVO pageReqVO);

}