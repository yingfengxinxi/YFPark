package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuSortDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.ControlMenuSortMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目租控管理菜单排序 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class ControlMenuSortServiceImpl implements ControlMenuSortService {

    @Resource
    private ControlMenuSortMapper controlMenuSortMapper;

    @Override
    public Long createControlMenuSort(ControlMenuSortSaveReqVO createReqVO) {
        // 插入
        ControlMenuSortDO controlMenuSort = BeanUtils.toBean(createReqVO, ControlMenuSortDO.class);
        controlMenuSortMapper.insert(controlMenuSort);
        // 返回
        return controlMenuSort.getId();
    }

    @Override
    public void updateControlMenuSort(ControlMenuSortSaveReqVO updateReqVO) {
        // 校验存在
        validateControlMenuSortExists(updateReqVO.getId());
        // 更新
        ControlMenuSortDO updateObj = BeanUtils.toBean(updateReqVO, ControlMenuSortDO.class);
        controlMenuSortMapper.updateById(updateObj);
    }

    @Override
    public void deleteControlMenuSort(Long id) {
        // 校验存在
        validateControlMenuSortExists(id);
        // 删除
        controlMenuSortMapper.deleteById(id);
    }

    private void validateControlMenuSortExists(Long id) {
        if (controlMenuSortMapper.selectById(id) == null) {
            throw exception(CONTROL_MENU_SORT_NOT_EXISTS);
        }
    }

    @Override
    public ControlMenuSortDO getControlMenuSort(Long id) {
        return controlMenuSortMapper.selectById(id);
    }

    @Override
    public PageResult<ControlMenuSortDO> getControlMenuSortPage(ControlMenuSortPageReqVO pageReqVO) {
        return controlMenuSortMapper.selectPage(pageReqVO);
    }

}