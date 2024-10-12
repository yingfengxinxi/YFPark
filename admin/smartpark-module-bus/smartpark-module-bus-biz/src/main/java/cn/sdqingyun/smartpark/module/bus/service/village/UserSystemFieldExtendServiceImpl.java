package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserSystemFieldExtendDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.UserSystemFieldExtendMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 用户扩展信息自定义系统设置 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class UserSystemFieldExtendServiceImpl implements UserSystemFieldExtendService {

    @Resource
    private UserSystemFieldExtendMapper userSystemFieldExtendMapper;

    @Override
    public Long createUserSystemFieldExtend(UserSystemFieldExtendSaveReqVO createReqVO) {
        // 插入
        UserSystemFieldExtendDO userSystemFieldExtend = BeanUtils.toBean(createReqVO, UserSystemFieldExtendDO.class);
        userSystemFieldExtendMapper.insert(userSystemFieldExtend);
        // 返回
        return userSystemFieldExtend.getId();
    }

    @Override
    public void updateUserSystemFieldExtend(UserSystemFieldExtendSaveReqVO updateReqVO) {
        // 校验存在
        validateUserSystemFieldExtendExists(updateReqVO.getId());
        // 更新
        UserSystemFieldExtendDO updateObj = BeanUtils.toBean(updateReqVO, UserSystemFieldExtendDO.class);
        userSystemFieldExtendMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserSystemFieldExtend(Long id) {
        // 校验存在
        validateUserSystemFieldExtendExists(id);
        // 删除
        userSystemFieldExtendMapper.deleteById(id);
    }

    private void validateUserSystemFieldExtendExists(Long id) {
        if (userSystemFieldExtendMapper.selectById(id) == null) {
            throw exception(USER_SYSTEM_FIELD_EXTEND_NOT_EXISTS);
        }
    }

    @Override
    public UserSystemFieldExtendDO getUserSystemFieldExtend(Long id) {
        return userSystemFieldExtendMapper.selectById(id);
    }

    @Override
    public PageResult<UserSystemFieldExtendDO> getUserSystemFieldExtendPage(UserSystemFieldExtendPageReqVO pageReqVO) {
        return userSystemFieldExtendMapper.selectPage(pageReqVO);
    }

}