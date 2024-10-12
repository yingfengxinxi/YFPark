package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserExtendsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.UserExtendsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserExtendsDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;

import java.util.Map;

/**
 * 用户信息扩展 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface UserExtendsService {

    /**
     * 创建用户信息扩展
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserExtends(@Valid UserExtendsSaveReqVO createReqVO);

    /**
     * 更新用户信息扩展
     *
     * @param updateReqVO 更新信息
     */
    void updateUserExtends(@Valid UserExtendsSaveReqVO updateReqVO);

    /**
     * 删除用户信息扩展
     *
     * @param id 编号
     */
    void deleteUserExtends(Long id);

    /**
     * 获得用户信息扩展
     *
     * @param id 编号
     * @return 用户信息扩展
     */
    UserExtendsDO getUserExtends(Long id);

    /**
     * 获得用户信息扩展分页
     *
     * @param pageReqVO 分页查询
     * @return 用户信息扩展分页
     */
    PageResult<UserExtendsDO> getUserExtendsPage(UserExtendsPageReqVO pageReqVO);

    /**
     * @Author SUNk
     * @Description 项目用户扩展汇总
     * @Date 11:14 2024/7/8
     * @Param [createReqVO]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> getCountUserExtends(UserExtendsSaveReqVO createReqVO) throws JsonProcessingException;

    /**
    * @Author SUNk
    * @Description 通过villageUserId获得用户信息扩展
    * @Date 15:45 2024/7/15
    * @Param [villageUserId]
    * @return cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserExtendsDO
    **/
    UserExtendsDO getUserExtendsByUser(Long villageUserId);
}