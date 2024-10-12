package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageTypeMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目类型 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class VillageTypeServiceImpl implements VillageTypeService {

    @Resource
    private VillageTypeMapper villageTypeMapper;

    @Override
    public Long createVillageType(VillageTypeSaveReqVO createReqVO) {
        // 插入
        VillageTypeDO villageType = BeanUtils.toBean(createReqVO, VillageTypeDO.class);
        villageTypeMapper.insert(villageType);
        // 返回
        return villageType.getId();
    }

    @Override
    public void updateVillageType(VillageTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateVillageTypeExists(updateReqVO.getId());
        // 更新
        VillageTypeDO updateObj = BeanUtils.toBean(updateReqVO, VillageTypeDO.class);
        villageTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteVillageType(Long id) {
        // 校验存在
        validateVillageTypeExists(id);
        // 删除
        villageTypeMapper.deleteById(id);
    }

    private void validateVillageTypeExists(Long id) {
        if (villageTypeMapper.selectById(id) == null) {
            throw exception(VILLAGE_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public VillageTypeDO getVillageType(Long id) {
        return villageTypeMapper.selectById(id);
    }

    @Override
    public PageResult<VillageTypeDO> getVillageTypePage(VillageTypePageReqVO pageReqVO) {
        return villageTypeMapper.selectPage(pageReqVO);
    }

}