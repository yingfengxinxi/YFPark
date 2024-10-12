package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import java.math.BigDecimal;
import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillTypeIncomeListPageVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.IncomeTypeExpensesVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构收入 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgIncomeMapper extends BaseMapperX<OrgIncomeDO> {

    /**
     *
     * @param page
     * @param billTypeIncomeListPageVO
     * @return
     */
    IPage<BillTypeIncomeListPageVO> getByBillTypeIncomeListPage(Page<?> page, @Param("param") BillTypeIncomeListPageVO billTypeIncomeListPageVO);

    /**
     * @param page
     * @param billType
     * @param startMonth
     * @param endMonth
     * @param isConfirm
     * @param contractStatus
     * @return
     */
    IPage<IncomeTypeExpensesVO> getIncomeTypeExpensesPage(Page<?> page, @Param("billType") String billType, @Param("startMonth") String startMonth, @Param("endMonth") String endMonth, @Param("isConfirm") String isConfirm, @Param("contractStatus") String contractStatus);


    /**
     * 当月应收(含税)
     *
     * @return
     */
    BigDecimal getSameMonthReceivableTaxIncludedMoney();

    /**
     * 当月应收(不含税)
     *
     * @return
     */
    BigDecimal getSameMonthReceivableNoTaxIncludedMoney();

    /**
     * 当月实收(含税)
     *
     * @return
     */
    BigDecimal getSameMonthNetReceiptsTaxIncludedMoney();

    /**
     * 当月已确定收入
     *
     * @return
     */
    BigDecimal getSameMonthConfirmedTaxIncludedMoney();

    /**
     * 当月待确定收入
     *
     * @return
     */
    BigDecimal getSameMonthToBeConfirmedTaxIncludedMoney();
}