package cn.sdqingyun.smartpark.module.bus.service.bill;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillApprovalConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillApprovalConfigDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillApprovalConfigMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 账单业务审批配置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BillApprovalConfigServiceImpl implements BillApprovalConfigService {

    @Resource
    private BillApprovalConfigMapper Mapper;

    @Override
    public Long save(BillApprovalConfigSaveReqVO createReqVO) {
        Long tenantId = TenantContextHolder.getTenantId();
        createReqVO.setTenantId(tenantId);
        createReqVO.setType("adjust_approve");
        BillApprovalConfigDO billApprovalConfigDO = get();
        if (billApprovalConfigDO != null) {
            billApprovalConfigDO.setIsUse(createReqVO.getIsUse());
            Mapper.updateById(billApprovalConfigDO);
        } else {
            billApprovalConfigDO = BeanUtils.toBean(createReqVO, BillApprovalConfigDO.class);
            Mapper.insert(billApprovalConfigDO);
        }
        // 返回
        return billApprovalConfigDO.getId();
    }

    @Override
    public BillApprovalConfigDO get() {
        Long tenantId = TenantContextHolder.getTenantId();
        LambdaQueryWrapperX<BillApprovalConfigDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(BillApprovalConfigDO::getTenantId, tenantId);
        return Mapper.selectOne(queryWrapperX);
    }


}