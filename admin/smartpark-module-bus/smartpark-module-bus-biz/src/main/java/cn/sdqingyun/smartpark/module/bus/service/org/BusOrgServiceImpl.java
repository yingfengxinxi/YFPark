package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BusOrgPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BusOrgSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.BusOrgDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.BusOrgMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.ORG_NOT_EXISTS;

/**
 * 机构 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class BusOrgServiceImpl implements BusOrgService {

    @Resource
    private BusOrgMapper orgMapper;

    @Override
    public Long createOrg(BusOrgSaveReqVO createReqVO) {
        // 插入
        BusOrgDO org = BeanUtils.toBean(createReqVO, BusOrgDO.class);
        orgMapper.insert(org);
        // 返回
        return org.getId();
    }

    @Override
    public void updateOrg(BusOrgSaveReqVO updateReqVO) {
        // 校验存在
        validateOrgExists(updateReqVO.getId());
        // 更新
        BusOrgDO updateObj = BeanUtils.toBean(updateReqVO, BusOrgDO.class);
        orgMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrg(Long id) {
        // 校验存在
        validateOrgExists(id);
        // 删除
        orgMapper.deleteById(id);
    }

    private void validateOrgExists(Long id) {
        if (orgMapper.selectById(id) == null) {
            throw exception(ORG_NOT_EXISTS);
        }
    }

    @Override
    public BusOrgDO getOrg(Long id) {
        return orgMapper.selectById(id);
    }

    @Override
    public PageResult<BusOrgDO> getOrgPage(BusOrgPageReqVO pageReqVO) {
        return orgMapper.selectPage(pageReqVO);
    }

    @Override
    public BusOrgDO getOrgByCode() {
        //租户默认拼接，所以不用增加租户code
        LambdaQueryWrapper<BusOrgDO> busOrgDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        busOrgDOLambdaQueryWrapper.eq( BusOrgDO::getDeleted, Boolean.FALSE );
        return orgMapper.selectOne( busOrgDOLambdaQueryWrapper );
    }
}