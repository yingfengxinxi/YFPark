package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.util.MyBatisUtils;
import cn.sdqingyun.smartpark.framework.web.core.util.WebFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.GetBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillInvoiceDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeConfigDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgIncomeConfigMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.org.OrgIncomeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.INCOME_NOT_EXISTS;

/**
 * 机构收入 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
public class OrgIncomeServiceImpl implements OrgIncomeService {

    @Resource
    private OrgIncomeMapper Mapper;

    @Resource
    private OrgIncomeConfigService orgIncomeConfigService;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Long create(OrgIncomeSaveReqVO createReqVO) {
        // 插入
        OrgIncomeDO orgIncomeDO = BeanUtils.toBean(createReqVO, OrgIncomeDO.class);
        Mapper.insert(orgIncomeDO);
        // 返回
        return orgIncomeDO.getId();
    }

    @Override
    public void update(OrgIncomeSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        OrgIncomeDO updateObj = BeanUtils.toBean(updateReqVO, OrgIncomeDO.class);
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
            throw exception(INCOME_NOT_EXISTS);
        }
    }

    @Override
    public OrgIncomeDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<OrgIncomeDO> getPage(OrgIncomePageReqVO pageReqVO) {
        LambdaQueryWrapperX<OrgIncomeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    /**
     * @param billTypeIncomeListPageVO
     * @return
     */
    @Override
    public PageResult<BillTypeIncomeListPageVO> getByBillTypeIncomeListPage(BillTypeIncomeListPageVO billTypeIncomeListPageVO) {
        IPage<BillTypeIncomeListPageVO> byBillTypeIncomeListPage = Mapper.getByBillTypeIncomeListPage(MyBatisUtils.buildPage(billTypeIncomeListPageVO), billTypeIncomeListPageVO);
        List<BillTypeIncomeListPageVO> incomeListPageVOList = byBillTypeIncomeListPage.getRecords();
        if (CollectionUtils.isNotEmpty(incomeListPageVOList)) {
            incomeListPageVOList.forEach(billTypeIncomeListPageVO1 -> {
                getRoomName(billTypeIncomeListPageVO1);

                String isConfirm = billTypeIncomeListPageVO1.getIsConfirm().equals("1") ? "待确认" : "已确认";
                billTypeIncomeListPageVO1.setIsConfirm(isConfirm);

                if (StringUtils.isNotEmpty(billTypeIncomeListPageVO1.getBillStatus())) {
                    String billStatusName = DictFrameworkUtils.getDictDataLabel("BILL_STATUS", billTypeIncomeListPageVO1.getBillStatus());
                    billTypeIncomeListPageVO1.setBillStatus(billStatusName);
                }


                if (StringUtils.isNotEmpty(billTypeIncomeListPageVO1.getMatchStatus())) {
                    String matchStatusName = DictFrameworkUtils.getDictDataLabel("MATCH_STATUS", billTypeIncomeListPageVO1.getMatchStatus());
                    billTypeIncomeListPageVO1.setMatchStatus(matchStatusName);
                }

                String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(billTypeIncomeListPageVO1.getConfirmUid()));
                billTypeIncomeListPageVO1.setConfirmUid(userName);
            });
        }
        return new PageResult<>(byBillTypeIncomeListPage.getRecords(), byBillTypeIncomeListPage.getTotal());
    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> getAmountStatistics() {
        Map<String, Object> map = new HashMap<>();
        //当月应收金额(含税)
        BigDecimal sameMonthReceivableTaxIncludedMoney = Mapper.getSameMonthReceivableTaxIncludedMoney();
        map.put("sameMonthReceivableTaxIncludedMoney", Tools.DecimalFormat(sameMonthReceivableTaxIncludedMoney));


        //当月应收金额(不含税)
        BigDecimal sameMonthReceivableNoTaxIncludedMoney = Mapper.getSameMonthReceivableNoTaxIncludedMoney();
        map.put("sameMonthReceivableNoTaxIncludedMoney", Tools.DecimalFormat(sameMonthReceivableNoTaxIncludedMoney));

        //当月实收(含税)
        BigDecimal sameMonthNetReceiptsTaxIncludedMoney = Mapper.getSameMonthNetReceiptsTaxIncludedMoney();
        map.put("sameMonthNetReceiptsTaxIncludedMoney", Tools.DecimalFormat(sameMonthNetReceiptsTaxIncludedMoney));

        //当月已确定收入
        BigDecimal sameMonthConfirmedTaxIncludedMoney = Mapper.getSameMonthConfirmedTaxIncludedMoney();
        map.put("sameMonthConfirmedTaxIncludedMoney", Tools.DecimalFormat(sameMonthConfirmedTaxIncludedMoney));

        //当月待确定收入
        BigDecimal sameMonthToBeConfirmedTaxIncludedMoney = Mapper.getSameMonthToBeConfirmedTaxIncludedMoney();
        map.put("sameMonthToBeConfirmedTaxIncludedMoney", Tools.DecimalFormat(sameMonthToBeConfirmedTaxIncludedMoney));
        return map;
    }

    private void getRoomName(BillTypeIncomeListPageVO billTypeIncomeListPageVO1) {
        String roomNumber = billTypeIncomeListPageVO1.getRoomNumber();
        String[] split = roomNumber.split(",");
        StringBuilder sb = new StringBuilder();
        for (String roomId : split) {
            RoomDO roomDO = roomMapper.selectById(roomId);
            if (roomDO != null) {
                String roomName = roomDO.getRoomName();
                String lc = roomName.substring(0, roomName.length() - 2);
                sb.append(lc + "-" + roomName).append(",");
            }
        }
        String string = sb.toString();
        if (StringUtils.isNotEmpty(string)) {
            billTypeIncomeListPageVO1.setBuildName(string.substring(0, string.length() - 1));
        }
    }


    @Override
    public void confirmIncome(OrgBillConfirmIncomeVO orgBillConfirmIncomeVO) {
        List<String> ids = List.of(orgBillConfirmIncomeVO.getIds().split(","));
        for (String id : ids) {
            OrgIncomeDO orgIncomeDO = Mapper.selectById(id);
            if (orgBillConfirmIncomeVO.getIsConfirm().equals("2")) {
                //确认
                if (orgIncomeDO.getIsConfirm().equals("1")) {
                    orgIncomeDO.setIsConfirm(orgBillConfirmIncomeVO.getIsConfirm());
                    orgIncomeDO.setConfirmTime(new Date());
                    Long userId = WebFrameworkUtils.getLoginUserId();
                    orgIncomeDO.setConfirmUid(String.valueOf(userId));
                    Mapper.updateById(orgIncomeDO);
                }
            } else {
                //取消
                //校验确认时间是否
                Date confirmTime = orgIncomeDO.getConfirmTime();
                Long day = DateUtils.getDayCount(confirmTime, new Date()) + 1;
                OrgIncomeConfigDO oneInfo = orgIncomeConfigService.getOneInfo();
                if (oneInfo != null) {
                    if (day > oneInfo.getLmtDay()) {
                        throw new RuntimeException("确认时间超过" + oneInfo.getLmtDay() + "天不可取消确认");
                    }
                }
                orgIncomeDO.setIsConfirm(orgBillConfirmIncomeVO.getIsConfirm());
                orgIncomeDO.setUnconfirmTime(new Date());
                Long userId = WebFrameworkUtils.getLoginUserId();
                orgIncomeDO.setUnconfirmUid(String.valueOf(userId));
                Mapper.updateById(orgIncomeDO);
            }

        }
    }

    @Override
    public Map<String, Object> getIncomeTypeExpensesPage(BillTypeIncomeListPageVO billTypeIncomeListPageVO) {
        Map<String, Object> map = new HashMap<>();
        List<String> titleList = Lists.newArrayList();
        titleList.add("费用类型");
        date(titleList, billTypeIncomeListPageVO.getMatchMonth());
        map.put("title", titleList);

        billTypeIncomeListPageVO.setBillType("1");
        billTypeIncomeListPageVO.setStartMonth(titleList.get(1));
        billTypeIncomeListPageVO.setEndMonth(titleList.get(titleList.size() - 1));
        IPage<IncomeTypeExpensesVO> byBillTypeIncomeListPage = Mapper.getIncomeTypeExpensesPage(MyBatisUtils.buildPage(billTypeIncomeListPageVO), billTypeIncomeListPageVO.getBillType(), billTypeIncomeListPageVO.getStartMonth(), billTypeIncomeListPageVO.getEndMonth(), billTypeIncomeListPageVO.getIsConfirm(), billTypeIncomeListPageVO.getContractStatus());
        List<IncomeTypeExpensesVO> incomeListPageVOList = byBillTypeIncomeListPage.getRecords();
        if (CollectionUtils.isNotEmpty(incomeListPageVOList)) {
            Map<String, List<IncomeTypeExpensesVO>> collect = incomeListPageVOList.stream()
                    .collect(Collectors.groupingBy(IncomeTypeExpensesVO::getCostType));
            for (String key : collect.keySet()) {
                List<IncomeTypeExpensesVO> incomeTypeExpensesVOS = collect.get(key);

                IncomeTypeExpensesVO incomeTypeExpensesVO = new IncomeTypeExpensesVO();
                //incomeTypeExpensesVO.setCostTypeName()

            }
        }
        return map;
    }


    /**
     * 获取两周日期
     *
     * @return
     */
    private static List<String> date(List<String> titleList, String matchMonth) {
        Integer year = Integer.valueOf(matchMonth.split("-")[0]);
        String month = matchMonth.split("-")[1];
        Integer year1 = year - 1;
        String startMonth = year1 + "-" + month;
        Integer year3 = year + 1;
        String endMonth = year3 + "-" + (Integer.valueOf(month) + 1);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
        try {
            List<Date> betweenDates = getBetweenDates(sim.parse(startMonth), sim.parse(endMonth));
            int i = 0;
            for (Date betweenDate : betweenDates) {
                i++;
                String dateNew = DateFormatUtils.format(betweenDate, "yyyy-MM");
                titleList.add(dateNew);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return titleList;
    }

    private static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 0);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
}