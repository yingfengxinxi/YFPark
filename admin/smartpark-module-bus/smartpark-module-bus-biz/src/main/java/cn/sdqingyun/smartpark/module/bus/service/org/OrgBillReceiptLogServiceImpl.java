package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgBillReceiptLogMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_RECEIPT_LOG_NOT_EXISTS;

/**
 * 账单开据记录 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgBillReceiptLogServiceImpl implements OrgBillReceiptLogService {

    @Resource
    private OrgBillReceiptLogMapper Mapper;

    @Override
    public Long create(OrgBillReceiptLogSaveReqVO createReqVO) {
        // 插入
        OrgBillReceiptLogDO billReceiptLogDO = BeanUtils.toBean(createReqVO, OrgBillReceiptLogDO.class);
        Mapper.insert(billReceiptLogDO);
        // 返回
        return billReceiptLogDO.getId();
    }

    @Override
    public void update(OrgBillReceiptLogSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgBillReceiptLogDO updateObj = BeanUtils.toBean(updateReqVO, OrgBillReceiptLogDO.class);
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
            throw exception(BILL_RECEIPT_LOG_NOT_EXISTS);
        }
    }

    @Override
    public OrgBillReceiptLogDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgBillReceiptLogDO> getPage(OrgBillReceiptLogPageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgBillReceiptLogDO>queryWrapperX=new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO,queryWrapperX);
    }

}