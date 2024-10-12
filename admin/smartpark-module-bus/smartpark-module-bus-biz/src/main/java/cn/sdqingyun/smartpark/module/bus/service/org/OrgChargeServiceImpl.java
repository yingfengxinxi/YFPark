package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgChargePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgChargeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgChargeDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgChargeMapper;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.CHARGE_NOT_EXISTS;

/**
 * 收费标准 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgChargeServiceImpl implements OrgChargeService {

    @Resource
    private OrgChargeMapper Mapper;

    @Override
    public Long create(OrgChargeSaveReqVO createReqVO) {
        // 插入
        OrgChargeDO chargeDO = BeanUtils.toBean(createReqVO, OrgChargeDO.class);
        Mapper.insert(chargeDO);
        // 返回
        return chargeDO.getId();
    }

    @Override
    public void update(OrgChargeSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgChargeDO updateObj = BeanUtils.toBean(updateReqVO, OrgChargeDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
            throw exception(CHARGE_NOT_EXISTS);
        }
    }

    @Override
    public OrgChargeDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgChargeDO> getPage(OrgChargePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgChargeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(OrgChargeDO::getName, pageReqVO.getName());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getMode())) {
            queryWrapperX.eq(OrgChargeDO::getMode, pageReqVO.getMode());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getStatus())) {
            queryWrapperX.eq(OrgChargeDO::getStatus, pageReqVO.getStatus());
        }
        queryWrapperX.orderByDesc(OrgChargeDO::getCreateTime);
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

}