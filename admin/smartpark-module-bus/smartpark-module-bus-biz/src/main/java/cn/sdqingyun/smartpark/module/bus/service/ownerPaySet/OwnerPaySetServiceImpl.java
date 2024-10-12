package cn.sdqingyun.smartpark.module.bus.service.ownerPaySet;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerPaySet.OwnerPaySetDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerPaySet.OwnerPaySetMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 企业自动缴费费用配置 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OwnerPaySetServiceImpl implements OwnerPaySetService {

    @Resource
    private OwnerPaySetMapper ownerPaySetMapper;

    @Override
    public Long createOwnerPaySet(OwnerPaySetSaveReqVO createReqVO) {
        // 插入
        OwnerPaySetDO ownerPaySet = BeanUtils.toBean(createReqVO, OwnerPaySetDO.class);
        ownerPaySetMapper.insert(ownerPaySet);
        // 返回
        return ownerPaySet.getId();
    }

    @Override
    public void updateOwnerPaySet(OwnerPaySetSaveReqVO updateReqVO) {
        // 校验存在
        validateOwnerPaySetExists(updateReqVO.getId());
        // 更新
        OwnerPaySetDO updateObj = BeanUtils.toBean(updateReqVO, OwnerPaySetDO.class);
        ownerPaySetMapper.updateById(updateObj);
    }

    @Override
    public void deleteOwnerPaySet(Long id) {
        // 校验存在
        validateOwnerPaySetExists(id);
        // 删除
        ownerPaySetMapper.deleteById(id);
    }

    private void validateOwnerPaySetExists(Long id) {
        if (ownerPaySetMapper.selectById(id) == null) {
            throw exception(OWNER_PAY_SET_NOT_EXISTS);
        }
    }

    @Override
    public OwnerPaySetDO getOwnerPaySet(Long id) {
        return ownerPaySetMapper.selectById(id);
    }

    @Override
    public PageResult<OwnerPaySetDO> getOwnerPaySetPage(OwnerPaySetPageReqVO pageReqVO) {
        return ownerPaySetMapper.selectPage(pageReqVO);
    }

}