package cn.sdqingyun.smartpark.module.bus.service.village;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillagePhoneDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillagePhoneMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目电话 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class VillagePhoneServiceImpl implements VillagePhoneService {

    @Resource
    private VillagePhoneMapper villagePhoneMapper;

    @Override
    public Long createVillagePhone(VillagePhoneSaveReqVO createReqVO) {
        // 插入
        VillagePhoneDO villagePhone = BeanUtils.toBean(createReqVO, VillagePhoneDO.class);
        villagePhoneMapper.insert(villagePhone);
        // 返回
        return villagePhone.getId();
    }

    @Override
    public void updateVillagePhone(VillagePhoneSaveReqVO updateReqVO) {
        // 校验存在
        validateVillagePhoneExists(updateReqVO.getId());
        // 更新
        VillagePhoneDO updateObj = BeanUtils.toBean(updateReqVO, VillagePhoneDO.class);
        villagePhoneMapper.updateById(updateObj);
    }

    @Override
    public void deleteVillagePhone(Long id) {
        // 校验存在
        validateVillagePhoneExists(id);
        // 删除
        villagePhoneMapper.deleteById(id);
    }

    private void validateVillagePhoneExists(Long id) {
        if (villagePhoneMapper.selectById(id) == null) {
            throw exception(VILLAGE_PHONE_NOT_EXISTS);
        }
    }

    @Override
    public VillagePhoneDO getVillagePhone(Long id) {
        return villagePhoneMapper.selectById(id);
    }

    @Override
    public PageResult<VillagePhoneDO> getVillagePhonePage(VillagePhonePageReqVO pageReqVO) {
        return villagePhoneMapper.selectPage(pageReqVO);
    }

}