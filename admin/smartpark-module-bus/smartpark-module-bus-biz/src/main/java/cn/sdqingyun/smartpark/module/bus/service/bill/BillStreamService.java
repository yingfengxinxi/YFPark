package cn.sdqingyun.smartpark.module.bus.service.bill;

import java.text.ParseException;
import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

/**
 * 机构账单收支流水 Service 接口
 *
 * @author 智慧园区
 */
public interface BillStreamService {

    /**
     * 创建机构账单收支流水
     *
     * @param billStreamSaveVO 创建信息
     * @return 编号
     */
    void create(@Valid BillStreamSaveVO billStreamSaveVO);


    /**
     *
     * @param billStreamSaveVO
     */
    void createBillStream(@Valid BillStreamSaveVO billStreamSaveVO);

    /**
     *
     * @param billStreamSaveVO
     */
    void lateSettle(@Valid BillStreamSaveVO billStreamSaveVO) throws ParseException;


    /**
     *
     * @param billStreamSaveVO
     */
    void rechargeCreateBillStream(BillStreamSaveVO billStreamSaveVO);

    /**
     *
     * @param billStreamSaveReqVO
     */
    Long save(@Valid BillStreamSaveReqVO billStreamSaveReqVO);

    /**
     * 更新机构账单收支流水
     *
     * @param updateReqVO 更新信息
     */
    void updateBillStream(@Valid BillStreamSaveReqVO updateReqVO);

    /**
     * 删除机构账单收支流水
     *
     * @param deleteReqVO 编号
     */
    void deleteBillStream(BillStreamSaveReqVO deleteReqVO);

    /**
     * 获得机构账单收支流水
     *
     * @param id 编号
     * @return 机构账单收支流水
     */
    BillStreamDO getBillStream(Long id);

    /**
     * 获得机构账单收支流水分页
     *
     * @param pageReqVO 分页查询
     * @return 机构账单收支流水分页
     */
    PageResult<BillStreamRespVO> getBillStreamPage(BillStreamPageReqVO pageReqVO);

    /**
     *
     * @param pageReqVO
     * @return
     */
    PageResult<BillStreamDO> getOwnerStreamPage(BillStreamPageReqVO pageReqVO);

    /**
     *
     * @param ownerId
     * @return
     */
    Map<String,Object> getOwnerIdStreamTotalMoney(Long ownerId);

    /**
     * @param ownerId
     * @param loanType
     * @return
     */
    List<OwnerIdLoanTypeInfoListVO> getOwnerIdLoanTypeInfoList(Long ownerId,String loanType);

    /**
     *
     * @param list
     */
    void importStreamList(List<BillStreamImportVO> list);

    /**
     *
     * @param streamMiddleId
     * @return
     */
    BillStreamDO incomeStreamDetail(Long streamMiddleId);

}