package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import cn.sdqingyun.smartpark.framework.excel.core.annotations.DictFormat;
import cn.sdqingyun.smartpark.framework.excel.core.annotations.ExcelColumnSelect;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;
import static cn.sdqingyun.smartpark.module.bus.enums.DictTypeConstants.*;

/**
 * @Author lvzy
 * @Date 2024/6/21 16:24
 */
@Data
public class ContractImportVO {

    @NotNull(message = "Excel 文件不能为空")
    private MultipartFile file;

    @NotNull(message = "是否生成账单不能为空0=否1=是")
    private Boolean updateSupport;


    @ExcelProperty(value = "合同编号")
    @NotNull(message = "合同编号不能为空")
    private String contractNumber;

    @ExcelProperty(value = "经办人")
    @NotNull(message = "经办人不能为空")
    private String operatorName;

    @ExcelProperty(value = "签订日")
    @NotNull(message = "签订日不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date signingDate;

    @ExcelProperty("租赁开始日期")
    @NotNull(message = "租赁开始日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date leaseStart;

    @ExcelProperty("租赁结束日期")
    @NotNull(message = "租赁结束日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date leaseEnd;

    @ExcelProperty("所属项目")
    @NotNull(message = "所属项目不能为空")
    private String parkName;

    @ExcelProperty("楼宇名称")
    @NotNull(message = "楼宇名称不能为空")
    private String buildName;

    @ExcelProperty("所属房源(楼层|房间号|租赁面积,多个以顿号相连 示例:1|101|100、1|102|100)")
    @NotNull(message = "所属房源不能为空")
    private String buildName1;

    @ExcelProperty("租赁面积(m²,注:此为所属房源总租赁面积)")
    @NotNull(message = "租赁面积不能为空")
    private String totalArea;

    @ExcelProperty("单价保留小数点几位")
    @NotNull(message = "单价保留小数点几位不能为空")
    private Integer unitPricePoint;

    //0=单价*面积*时间1=单价*时间*面积
    @ExcelProperty("计算顺序")
    @NotNull(message = "计算顺序不能为空")
    @DictFormat(CALCULATION_ORDER)
    @ExcelColumnSelect(dictType = CALCULATION_ORDER)
    private String calculationOrder;

    //0=否1=是
    @ExcelProperty("应收整数是否四舍五入")
    @NotNull(message = "应收整数是否四舍五入不能为空")
    @DictFormat(IS_RECEIVABLE_INTEGER)
    @ExcelColumnSelect(dictType = IS_RECEIVABLE_INTEGER)
    private String isReceivableInteger;


    @ExcelProperty("乙方")
    @NotNull(message = "乙方不能为空")
    private String ownerName;

    @ExcelProperty("法人姓名")
    @NotNull(message = "法人姓名不能为空")
    private String legalPerson;

    @ExcelProperty("签订人姓名")
    @NotNull(message = "签订人姓名不能为空")
    private String signedName;


    @ExcelProperty("滞纳金起算天数")
    @NotNull(message = "滞纳金起算天数不能为空")
    private Integer startingLateFeeDay;

    @ExcelProperty("滞纳金比例%")
    @NotNull(message = "滞纳金比例不能为空")
    private String lateFeeRatio;

    @ExcelProperty("滞纳金上限%")
    @NotNull(message = "滞纳金上限不能为空")
    private String upperLimitLateFee;

    @ExcelProperty("租赁保证金")
    @NotNull(message = "租赁保证金不能为空")
    private String zlBondMoney;

    //0=单价不含税1=单价含税
    @ExcelProperty("含税规则")
    @NotNull(message = "含税规则不能为空")
    @DictFormat(TAX_INCLUSIVE_RULES)
    @ExcelColumnSelect(dictType = TAX_INCLUSIVE_RULES)
    private String taxInclusiveRules;

    @ExcelProperty("税率")
    private String taxInclusiveValue;

    @ExcelProperty("租赁单价")
    @NotNull(message = "租赁单价不能为空")
    private String dMoney;

    @ExcelProperty("租赁单价计费单位（元/㎡·天、元/㎡·月、元/月）")
    @NotNull(message = "租赁单价计费单位不能为空")
    @DictFormat(CONTRACT_UNIT_PRICE)
    @ExcelColumnSelect(dictType = CONTRACT_UNIT_PRICE)
    private String contractUnitPrice;

    @ExcelProperty("付款时间(提前几天)")
    @NotNull(message = "付款时间(提前几天)不能为空")
    private String payDay;

    @ExcelProperty("付款周期（几月一付）")
    @NotNull(message = "付款周期（几月一付）不能为空")
    private int day;

    @ExcelProperty("租赁备注条款")
    private String remarkClause;

    @ExcelProperty("物业保证金")
    @NotNull(message = "物业保证金不能为空")
    private String wyBondMoney;


    //0=单价不含税1=单价含税
    @ExcelProperty("物业含税规则")
    @NotNull(message = "物业含税规则不能为空")
    @DictFormat(TAX_INCLUSIVE_RULES)
    @ExcelColumnSelect(dictType = TAX_INCLUSIVE_RULES)
    private String wyTaxInclusiveRules;

    @ExcelProperty("物业含税税率(选择单价含税可不填)")
    private String wyTaxInclusiveValue;

    @ExcelProperty("物业合同单价")
    @NotNull(message = "物业合同单价不能为空")
    private String wyMoney;


    @ExcelProperty("物业管理费单价计费单位(元/㎡·天、元/㎡·月、元/月)")
    @NotNull(message = "物业管理费单价计费单位不能为空")
    @DictFormat(CONTRACT_UNIT_PRICE)
    @ExcelColumnSelect(dictType = CONTRACT_UNIT_PRICE)
    private String wyContractUnitPrice;


    @ExcelProperty("物业付款时间(提前几天)")
    @NotNull(message = "物业付款时间(提前几天)不能为空")
    private String wyPayDay;

    @ExcelProperty("物业付款周期（几月一付）")
    @NotNull(message = "物业付款周期（几月一付）不能为空")
    private int wyDay;


    @ExcelProperty("物业备注条款")
    private String wyRemarkClause;


}
