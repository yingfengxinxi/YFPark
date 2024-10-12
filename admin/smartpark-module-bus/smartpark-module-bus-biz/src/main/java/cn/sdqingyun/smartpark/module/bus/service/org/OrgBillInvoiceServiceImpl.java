package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillConfirmIncomeVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoicePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoiceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillInvoiceDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillInvoiceMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_INVOICE_NOT_EXISTS;

/**
 * 机构账单开票记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillInvoiceServiceImpl implements OrgBillInvoiceService {

    @Resource
    private OrgBillInvoiceMapper Mapper;

    @Override
    public Long create(OrgBillInvoiceSaveReqVO createReqVO) {
        // 插入
        OrgBillInvoiceDO billInvoiceDO = BeanUtils.toBean(createReqVO, OrgBillInvoiceDO.class);
        Mapper.insert(billInvoiceDO);
        // 返回
        return billInvoiceDO.getId();
    }

    @Override
    public void update(OrgBillInvoiceSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillInvoiceDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillInvoiceDO.class);
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
            throw exception(BILL_INVOICE_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillInvoiceDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillInvoiceDO> getPage(OrgBillInvoicePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillInvoiceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }


}