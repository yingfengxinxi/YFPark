package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.ControlMenuMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目租控管理菜单 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class ControlMenuServiceImpl implements ControlMenuService {

    @Resource
    private ControlMenuMapper controlMenuMapper;

    @Override
    public Long createControlMenu(ControlMenuSaveReqVO createReqVO) {
        // 插入
        ControlMenuDO controlMenu = BeanUtils.toBean(createReqVO, ControlMenuDO.class);
        controlMenuMapper.insert(controlMenu);
        // 返回
        return controlMenu.getId();
    }

    @Override
    public void updateControlMenu(ControlMenuSaveReqVO updateReqVO) {
        // 校验存在
        validateControlMenuExists(updateReqVO.getId());
        // 更新
        ControlMenuDO updateObj = BeanUtils.toBean(updateReqVO, ControlMenuDO.class);
        controlMenuMapper.updateById(updateObj);
    }

    @Override
    public void deleteControlMenu(Long id) {
        // 校验存在
        validateControlMenuExists(id);
        // 删除
        controlMenuMapper.deleteById(id);
    }

    private void validateControlMenuExists(Long id) {
        if (controlMenuMapper.selectById(id) == null) {
            throw exception(CONTROL_MENU_NOT_EXISTS);
        }
    }

    @Override
    public ControlMenuDO getControlMenu(Long id) {
        return controlMenuMapper.selectById(id);
    }

    @Override
    public PageResult<ControlMenuDO> getControlMenuPage(ControlMenuPageReqVO pageReqVO) {
        return controlMenuMapper.selectPage(pageReqVO);
    }

}