package cn.sdqingyun.smartpark.module.bus.service.village;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.PhoneCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.PhoneCategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.PhoneCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillagePhoneRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.PhoneCategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillagePhoneDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.PhoneCategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillagePhoneMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.PHONE_CATEGORY_NOT_EXISTS;

/**
 * 项目电话类型 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class PhoneCategoryServiceImpl implements PhoneCategoryService {

    @Resource
    private PhoneCategoryMapper phoneCategoryMapper;
    @Resource
    private VillagePhoneMapper villagePhoneMapper;

    @Override
    public Long createPhoneCategory(PhoneCategorySaveReqVO createReqVO) {
        // 插入
        PhoneCategoryDO phoneCategory = BeanUtils.toBean(createReqVO, PhoneCategoryDO.class);
        phoneCategoryMapper.insert(phoneCategory);
        // 返回
        return phoneCategory.getId();
    }

    @Override
    public void updatePhoneCategory(PhoneCategorySaveReqVO updateReqVO) {
        // 校验存在
        validatePhoneCategoryExists(updateReqVO.getId());
        // 更新
        PhoneCategoryDO updateObj = BeanUtils.toBean(updateReqVO, PhoneCategoryDO.class);
        phoneCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deletePhoneCategory(Long id) {
        // 校验存在
        validatePhoneCategoryExists(id);
        // 删除
        phoneCategoryMapper.deleteById(id);
    }

    private void validatePhoneCategoryExists(Long id) {
        if (phoneCategoryMapper.selectById(id) == null) {
            throw exception(PHONE_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public PhoneCategoryDO getPhoneCategory(Long id) {
        return phoneCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<PhoneCategoryDO> getPhoneCategoryPage(PhoneCategoryPageReqVO pageReqVO) {
        return phoneCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<PhoneCategoryRespVO> getPhoneCategoryVOPage(PhoneCategoryPageReqVO pageReqVO) {
        PageResult<PhoneCategoryDO> result = phoneCategoryMapper.selectPage( pageReqVO );
        List<PhoneCategoryDO> list = result.getList();
        if(CollUtil.isNotEmpty(list)){
            PageResult<PhoneCategoryRespVO> pageResult = BeanUtils.toBean( result, PhoneCategoryRespVO.class );
            for (PhoneCategoryRespVO respVO : pageResult.getList()) {
                List<VillagePhoneDO> villagePhoneDOS = villagePhoneMapper.selectList( new LambdaQueryWrapperX<VillagePhoneDO>().eq( VillagePhoneDO::getCatId, respVO.getId() ) );
                if(CollUtil.isNotEmpty(villagePhoneDOS)){
                    List<VillagePhoneRespVO> villagePhoneRespVOS = BeanUtils.toBean( villagePhoneDOS, VillagePhoneRespVO.class );
                    villagePhoneRespVOS.forEach( villagePhoneRespVO -> {villagePhoneRespVO.setPid( villagePhoneRespVO.getId() );villagePhoneRespVO.setId( null );} );
                    respVO.setVillagePhoneDOS( villagePhoneRespVOS );
                }
            }
            return pageResult;
        }
        return null;
    }
}