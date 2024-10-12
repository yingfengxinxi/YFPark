package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillConfirmIncomeVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoicePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoiceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillInvoiceDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构账单开票记录 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillInvoiceService {

    /**
     * 创建机构账单开票记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillInvoiceSaveReqVO createReqVO);

    /**
     * 更新机构账单开票记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillInvoiceSaveReqVO updateReqVO);

    /**
     * 删除机构账单开票记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构账单开票记录
     *
     * @param id 编号
     * @return 机构账单开票记录
     */
    OrgBillInvoiceDO get(Long id);

    /**
     * 获得机构账单开票记录分页
     *
     * @param pageReqVO 分页查询
     * @return 机构账单开票记录分页
     */
    PageResult<OrgBillInvoiceDO> getPage(OrgBillInvoicePageReqVO pageReqVO);


}