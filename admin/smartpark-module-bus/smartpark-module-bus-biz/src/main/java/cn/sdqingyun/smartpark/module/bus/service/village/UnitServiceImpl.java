package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UnitDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.UnitMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目单元 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class UnitServiceImpl implements UnitService {

    @Resource
    private UnitMapper unitMapper;

    @Override
    public Long createUnit(UnitSaveReqVO createReqVO) {
        // 插入
        UnitDO unit = BeanUtils.toBean(createReqVO, UnitDO.class);
        unitMapper.insert(unit);
        // 返回
        return unit.getId();
    }

    @Override
    public void updateUnit(UnitSaveReqVO updateReqVO) {
        // 校验存在
        validateUnitExists(updateReqVO.getId());
        // 更新
        UnitDO updateObj = BeanUtils.toBean(updateReqVO, UnitDO.class);
        unitMapper.updateById(updateObj);
    }

    @Override
    public void deleteUnit(Long id) {
        // 校验存在
        validateUnitExists(id);
        // 删除
        unitMapper.deleteById(id);
    }

    private void validateUnitExists(Long id) {
        if (unitMapper.selectById(id) == null) {
            throw exception(UNIT_NOT_EXISTS);
        }
    }

    @Override
    public UnitDO getUnit(Long id) {
        return unitMapper.selectById(id);
    }

    @Override
    public PageResult<UnitDO> getUnitPage(UnitPageReqVO pageReqVO) {
        return unitMapper.selectPage(pageReqVO);
    }

}