package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UserIcCardDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.UserIcCardMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 住户的IC卡 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class UserIcCardServiceImpl implements UserIcCardService {

    @Resource
    private UserIcCardMapper userIcCardMapper;

    @Override
    public Long createUserIcCard(UserIcCardSaveReqVO createReqVO) {
        // 插入
        UserIcCardDO userIcCard = BeanUtils.toBean(createReqVO, UserIcCardDO.class);
        userIcCardMapper.insert(userIcCard);
        // 返回
        return userIcCard.getId();
    }

    @Override
    public void updateUserIcCard(UserIcCardSaveReqVO updateReqVO) {
        // 校验存在
        validateUserIcCardExists(updateReqVO.getId());
        // 更新
        UserIcCardDO updateObj = BeanUtils.toBean(updateReqVO, UserIcCardDO.class);
        userIcCardMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserIcCard(Long id) {
        // 校验存在
        validateUserIcCardExists(id);
        // 删除
        userIcCardMapper.deleteById(id);
    }

    private void validateUserIcCardExists(Long id) {
        if (userIcCardMapper.selectById(id) == null) {
            throw exception(USER_IC_CARD_NOT_EXISTS);
        }
    }

    @Override
    public UserIcCardDO getUserIcCard(Long id) {
        return userIcCardMapper.selectById(id);
    }

    @Override
    public PageResult<UserIcCardDO> getUserIcCardPage(UserIcCardPageReqVO pageReqVO) {
        return userIcCardMapper.selectPage(pageReqVO);
    }

}