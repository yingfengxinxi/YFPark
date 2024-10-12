package cn.sdqingyun.smartpark.module.bus.service.bill;

import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillStreamDeleteHistoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillStreamDeleteHistorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.bill.BillStreamDeleteHistoryMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDeleteHistoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.BILL_STREAM_DELETE_HISTORY_NOT_EXISTS;

/**
 * 流水删除历史 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class BillStreamDeleteHistoryServiceImpl implements BillStreamDeleteHistoryService {

    @Resource
    private BillStreamDeleteHistoryMapper billStreamDeleteHistoryMapper;

    @Override
    public Long createBillStreamDeleteHistory(BillStreamDeleteHistorySaveReqVO createReqVO) {
        // 插入
        BillStreamDeleteHistoryDO billStreamDeleteHistory = BeanUtils.toBean(createReqVO, BillStreamDeleteHistoryDO.class);
        billStreamDeleteHistoryMapper.insert(billStreamDeleteHistory);
        // 返回
        return billStreamDeleteHistory.getId();
    }

    @Override
    public void updateBillStreamDeleteHistory(BillStreamDeleteHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateBillStreamDeleteHistoryExists(updateReqVO.getId());
        // 更新
        BillStreamDeleteHistoryDO updateObj = BeanUtils.toBean(updateReqVO, BillStreamDeleteHistoryDO.class);
        billStreamDeleteHistoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteBillStreamDeleteHistory(Long id) {
        // 校验存在
        validateBillStreamDeleteHistoryExists(id);
        // 删除
        billStreamDeleteHistoryMapper.deleteById(id);
    }

    private void validateBillStreamDeleteHistoryExists(Long id) {
        if (billStreamDeleteHistoryMapper.selectById(id) == null) {
            throw exception(BILL_STREAM_DELETE_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public BillStreamDeleteHistoryDO getBillStreamDeleteHistory(Long id) {
        return billStreamDeleteHistoryMapper.selectById(id);
    }

    @Override
    public PageResult<BillStreamDeleteHistoryDO> getBillStreamDeleteHistoryPage(BillStreamDeleteHistoryPageReqVO pageReqVO) {
        return billStreamDeleteHistoryMapper.selectPage(pageReqVO);
    }

}