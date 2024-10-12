package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoicePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAnnexDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillInvoiceDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillAnnexMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_ANNEX_NOT_EXISTS;

/**
 * 机构账单收支流水附件 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillAnnexServiceImpl implements OrgBillAnnexService {

    @Resource
    private OrgBillAnnexMapper billAnnexMapper;

    @Override
    public Long createBillAnnex(OrgBillAnnexSaveReqVO createReqVO) {
        // 插入
        OrgBillAnnexDO billAnnex = BeanUtils.toBean(createReqVO, OrgBillAnnexDO.class);
        billAnnexMapper.insert(billAnnex);
        // 返回
        return billAnnex.getId();
    }

    @Override
    public void updateBillAnnex(OrgBillAnnexSaveReqVO updateReqVO) {
        // 校验存在
        validateBillAnnexExists(updateReqVO.getId());
        // 更新
        OrgBillAnnexDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillAnnexDO.class);
        billAnnexMapper.updateById(updateObj);
    }

    @Override
    public void deleteBillAnnex(Long id) {
        // 校验存在
        validateBillAnnexExists(id);
        // 删除
        billAnnexMapper.deleteById(id);
    }

    private void validateBillAnnexExists(Long id) {
        if (billAnnexMapper.selectById(id) == null) {
            throw exception(BILL_ANNEX_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillAnnexDO getBillAnnex(Long id) {
        return billAnnexMapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillAnnexDO> getBillAnnexList(OrgBillAnnexPageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillAnnexDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OrgBillAnnexDO::getSourceId,pageReqVO.getSourceId());
        queryWrapperX.eq(OrgBillAnnexDO::getType, pageReqVO.getType());
        return billAnnexMapper.selectPage(pageReqVO,queryWrapperX);
    }

}