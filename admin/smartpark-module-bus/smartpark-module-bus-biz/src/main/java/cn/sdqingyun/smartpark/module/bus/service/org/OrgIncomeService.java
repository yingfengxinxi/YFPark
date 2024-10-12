package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.GetBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillTypeIncomeListPageVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillConfirmIncomeVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构收入 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgIncomeService {

    /**
     * 创建机构收入
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgIncomeSaveReqVO createReqVO);

    /**
     * 更新机构收入
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgIncomeSaveReqVO updateReqVO);

    /**
     * 删除机构收入
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构收入
     *
     * @param id 编号
     * @return 机构收入
     */
    OrgIncomeDO get(Long id);

    /**
     * 获得机构收入分页
     *
     * @param pageReqVO 分页查询
     * @return 机构收入分页
     */
    PageResult<OrgIncomeDO> getPage(OrgIncomePageReqVO pageReqVO);

    /**
     *
     * @param billTypeIncomeListPageVO
     * @return
     */
    public PageResult<BillTypeIncomeListPageVO> getByBillTypeIncomeListPage(BillTypeIncomeListPageVO billTypeIncomeListPageVO);


    /**
     *
     * @return
     */
    public Map<String,Object>getAmountStatistics();
    /**
     *
     * @param orgBillConfirmIncomeVO
     */
    void confirmIncome(OrgBillConfirmIncomeVO orgBillConfirmIncomeVO);

    /**
     *
     * @param billTypeIncomeListPageVO
     * @return
     */
    Map<String,Object> getIncomeTypeExpensesPage(BillTypeIncomeListPageVO billTypeIncomeListPageVO);
}