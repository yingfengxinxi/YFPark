package cn.sdqingyun.smartpark.module.bus.service.ownerRemarks;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerRemarks.OwnerRemarksDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerRemarks.OwnerRemarksMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 租客备注信息 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OwnerRemarksServiceImpl implements OwnerRemarksService {

    @Resource
    private OwnerRemarksMapper ownerRemarksMapper;

    @Override
    public Long createOwnerRemarks(OwnerRemarksSaveReqVO createReqVO) {
        // 插入
        OwnerRemarksDO ownerRemarks = BeanUtils.toBean(createReqVO, OwnerRemarksDO.class);
        ownerRemarksMapper.insert(ownerRemarks);
        // 返回
        return ownerRemarks.getId();
    }

    @Override
    public void updateOwnerRemarks(OwnerRemarksSaveReqVO updateReqVO) {
        // 校验存在
        validateOwnerRemarksExists(updateReqVO.getId());
        // 更新
        OwnerRemarksDO updateObj = BeanUtils.toBean(updateReqVO, OwnerRemarksDO.class);
        ownerRemarksMapper.updateById(updateObj);
    }

    @Override
    public void deleteOwnerRemarks(Long id) {
        // 校验存在
        validateOwnerRemarksExists(id);
        // 删除
        ownerRemarksMapper.deleteById(id);
    }

    private void validateOwnerRemarksExists(Long id) {
        if (ownerRemarksMapper.selectById(id) == null) {
            throw exception(OWNER_REMARKS_NOT_EXISTS);
        }
    }

    @Override
    public OwnerRemarksDO getOwnerRemarks(Long id) {
        return ownerRemarksMapper.selectById(id);
    }

    @Override
    public PageResult<OwnerRemarksDO> getOwnerRemarksPage(OwnerRemarksPageReqVO pageReqVO) {
        return ownerRemarksMapper.selectPage(pageReqVO);
    }

}