package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDictDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageTypeDictMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目类型字典 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class VillageTypeDictServiceImpl implements VillageTypeDictService {

    @Resource
    private VillageTypeDictMapper villageTypeDictMapper;

    @Override
    public Long createVillageTypeDict(VillageTypeDictSaveReqVO createReqVO) {
        // 插入
        VillageTypeDictDO villageTypeDict = BeanUtils.toBean(createReqVO, VillageTypeDictDO.class);
        villageTypeDictMapper.insert(villageTypeDict);
        // 返回
        return villageTypeDict.getId();
    }

    @Override
    public void updateVillageTypeDict(VillageTypeDictSaveReqVO updateReqVO) {
        // 校验存在
        validateVillageTypeDictExists(updateReqVO.getId());
        // 更新
        VillageTypeDictDO updateObj = BeanUtils.toBean(updateReqVO, VillageTypeDictDO.class);
        villageTypeDictMapper.updateById(updateObj);
    }

    @Override
    public void deleteVillageTypeDict(Long id) {
        // 校验存在
        validateVillageTypeDictExists(id);
        // 删除
        villageTypeDictMapper.deleteById(id);
    }

    private void validateVillageTypeDictExists(Long id) {
        if (villageTypeDictMapper.selectById(id) == null) {
            throw exception(VILLAGE_TYPE_DICT_NOT_EXISTS);
        }
    }

    @Override
    public VillageTypeDictDO getVillageTypeDict(Long id) {
        return villageTypeDictMapper.selectById(id);
    }

    @Override
    public PageResult<VillageTypeDictDO> getVillageTypeDictPage(VillageTypeDictPageReqVO pageReqVO) {
        return villageTypeDictMapper.selectPage(pageReqVO);
    }

}