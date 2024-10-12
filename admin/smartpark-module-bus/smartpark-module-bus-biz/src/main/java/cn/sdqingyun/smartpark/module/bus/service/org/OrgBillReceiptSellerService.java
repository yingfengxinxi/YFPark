package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptSellerDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 收据收款方信息 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillReceiptSellerService {

    /**
     * 创建收据收款方信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillReceiptSellerSaveReqVO createReqVO);

    /**
     * 更新收据收款方信息
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillReceiptSellerSaveReqVO updateReqVO);

    /**
     * 删除收据收款方信息
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得收据收款方信息
     *
     * @param id 编号
     * @return 收据收款方信息
     */
    OrgBillReceiptSellerDO get(Long id);

    /**
     * 获得收据收款方信息分页
     *
     * @param pageReqVO 分页查询
     * @return 收据收款方信息分页
     */
    PageResult<OrgBillReceiptSellerDO> getPage(OrgBillReceiptSellerPageReqVO pageReqVO);

    /**
     *
     * @param buildBindList
     * @return
     */
    List<OrgBillReceiptSellerDO> getByBuildsList(List<Long> buildBindList);
}