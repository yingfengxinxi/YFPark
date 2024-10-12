package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.GetBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillCollectionAllListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillImportVO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/7/15 15:18
 */
public interface OrgBillService {


    /**
     *
     * @param billCollectionAllListVO
     * @return
     */
    public PageResult<BillCollectionAllListVO> getBillCollectionAllListPage(BillCollectionAllListVO billCollectionAllListVO);

    /**
     *
     * @param billCollectionAllListVO
     * @return
     */
    Map<String, Object> getBillCollectionAllTotalMoney(BillCollectionAllListVO billCollectionAllListVO);

    /**
     *
     * @param pageReqVO
     * @return
     */
    PageResult<BillCollectionAllListVO> getBillBeOverdueListPage(BillCollectionAllListVO pageReqVO);

    /**
     *
     * @param billCollectionAllListVO
     * @return
     */
    Map<String, Object> getBillBeOverdueTotalMoney(BillCollectionAllListVO billCollectionAllListVO);

    /**
     *
     * @param list
     * @param response
     * @return
     */
    Boolean importExcel(List<BillImportVO> list, HttpServletResponse response);
}
