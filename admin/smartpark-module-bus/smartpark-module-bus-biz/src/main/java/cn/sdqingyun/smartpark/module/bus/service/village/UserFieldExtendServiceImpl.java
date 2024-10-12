package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserFieldExtendDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.UserFieldExtendMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 用户扩展信息自定义系统设置 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class UserFieldExtendServiceImpl implements UserFieldExtendService {

    @Resource
    private UserFieldExtendMapper userFieldExtendMapper;

    @Override
    public Long createUserFieldExtend(UserFieldExtendSaveReqVO createReqVO) {
        // 插入
        UserFieldExtendDO userFieldExtend = BeanUtils.toBean(createReqVO, UserFieldExtendDO.class);
        userFieldExtendMapper.insert(userFieldExtend);
        // 返回
        return userFieldExtend.getId();
    }

    @Override
    public void updateUserFieldExtend(UserFieldExtendSaveReqVO updateReqVO) {
        // 校验存在
        validateUserFieldExtendExists(updateReqVO.getId());
        // 更新
        UserFieldExtendDO updateObj = BeanUtils.toBean(updateReqVO, UserFieldExtendDO.class);
        userFieldExtendMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserFieldExtend(Long id) {
        // 校验存在
        validateUserFieldExtendExists(id);
        // 删除
        userFieldExtendMapper.deleteById(id);
    }

    private void validateUserFieldExtendExists(Long id) {
        if (userFieldExtendMapper.selectById(id) == null) {
            throw exception(USER_FIELD_EXTEND_NOT_EXISTS);
        }
    }

    @Override
    public UserFieldExtendDO getUserFieldExtend(Long id) {
        return userFieldExtendMapper.selectById(id);
    }

    @Override
    public PageResult<UserFieldExtendDO> getUserFieldExtendPage(UserFieldExtendPageReqVO pageReqVO) {
        return userFieldExtendMapper.selectPage(pageReqVO);
    }

}