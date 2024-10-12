package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.LssueVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.ReceiptImportVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

/**
 * 机构账单收据 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillReceiptService {

    /**
     * 创建机构账单收据
     *
     * @param lssueVO 创建信息
     * @return 编号
     */
    Long create(@Valid LssueVO lssueVO) throws Exception;

    /**
     * 更新机构账单收据
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillReceiptSaveReqVO updateReqVO);

    /**
     * 删除机构账单收据
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构账单收据
     *
     * @param id 编号
     * @return 机构账单收据
     */
    OrgBillReceiptDO get(Long id);

    /**
     * 获得机构账单收据分页
     *
     * @param pageReqVO 分页查询
     * @return 机构账单收据分页
     */
    PageResult<OrgBillReceiptDO> getPage(OrgBillReceiptPageReqVO pageReqVO);

    /**
     *
     * @param billIds
     * @return
     */
    CommonResult<?> lssue(List<Long> billIds);

    /**
     *
     * @param buildId
     * @return
     */
    CommonResult<?> getReceiptNumber(Long buildId);

    /**
     *
     * @param id
     */
    void send(Long id);

    /**
     *
     * @param id
     */
    void recovery(Long id);

    /**
     *
     * @param id
     */
    void toVoid(Long id);

    /**
     *
     * @param id
     * @param applyTemplateId
     * @return
     */
    String generate(Long id,Long applyTemplateId) throws Exception;

    /**
     *
     * @param list
     */
    void importReceiptList(List<ReceiptImportVO> list) throws Exception;

    /**
     *
     * @param billId
     */
    void isCheckReceipt(Long billId);
}