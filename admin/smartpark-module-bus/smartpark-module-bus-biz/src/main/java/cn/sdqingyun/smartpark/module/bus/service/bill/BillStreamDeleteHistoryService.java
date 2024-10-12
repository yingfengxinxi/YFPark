package cn.sdqingyun.smartpark.module.bus.service.bill;

import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillStreamDeleteHistoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillStreamDeleteHistorySaveReqVO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDeleteHistoryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

/**
 * 流水删除历史 Service 接口
 *
 * @author 智慧园区
 */
public interface BillStreamDeleteHistoryService {

    /**
     * 创建流水删除历史
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBillStreamDeleteHistory(@Valid BillStreamDeleteHistorySaveReqVO createReqVO);

    /**
     * 更新流水删除历史
     *
     * @param updateReqVO 更新信息
     */
    void updateBillStreamDeleteHistory(@Valid BillStreamDeleteHistorySaveReqVO updateReqVO);

    /**
     * 删除流水删除历史
     *
     * @param id 编号
     */
    void deleteBillStreamDeleteHistory(Long id);

    /**
     * 获得流水删除历史
     *
     * @param id 编号
     * @return 流水删除历史
     */
    BillStreamDeleteHistoryDO getBillStreamDeleteHistory(Long id);

    /**
     * 获得流水删除历史分页
     *
     * @param pageReqVO 分页查询
     * @return 流水删除历史分页
     */
    PageResult<BillStreamDeleteHistoryDO> getBillStreamDeleteHistoryPage(BillStreamDeleteHistoryPageReqVO pageReqVO);

}