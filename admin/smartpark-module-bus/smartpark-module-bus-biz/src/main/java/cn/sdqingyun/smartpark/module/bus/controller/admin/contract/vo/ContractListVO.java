package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/6/21 19:04
 */
@Data
public class ContractListVO {

    private Long id;

    /**
     * 租客名称
     */
    @TableField(exist = false)
    @ExcelProperty("租客名称")
    private String ownerName;
    @TableField(exist = false)
    private Long ownerId;


    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @ExcelProperty("开始日")
    private Date contractStartTime;


    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @ExcelProperty("结束日")
    private Date contractEndTime;

    /**
     * 到期天数
     */
    @TableField(exist = false)
    private Integer expirationDay;

    /**
     * 租赁单价
     */
    @ExcelProperty("租赁单价")
    private String zlDjMoney;


    /**
     * 租赁面积
     */
    @ExcelProperty("租赁面积")
    private String leaseArea;

    /**
     * 签订日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @ExcelProperty("签订日")
    private Date signingDate;

    /**
     * 楼宇名称
     */
    @ExcelProperty("楼宇名称")
    private String buildName;

    /**
     * 合同编号
     */
    @ExcelProperty("合同编号")
    private String contractNumber;


    /**
     * 合同来源0=新建1=导入
     */
    @ExcelProperty("合同来源")
    private String dataSource;


    /**
     * 合同状态0=新建待审核1=正常执行中2=变更待审核3=变更待修改4=退租待审批5=退租待执行6=已退租7=作废待审批8=作废待修改9=到期未处理10=已驳回11=已作废12=已撤回13=待执行14=续租待审批15=已到期
     */
    @ExcelProperty("合同状态")
    private String status;

    /**
     * 租赁保证金
     */
    @ExcelProperty("合同状态")
    private String zlBondClause;

    /**
     * 房号,多个用英文逗号拼接;例:1-101,1-103
     */
    @ExcelProperty("房号")
    private String roomNumber;

    /**
     * 退租时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @ExcelProperty("退租日")
    private Date leaseRetreatTime;

    /**
     * 物业保证金
     */
    @ExcelProperty("物业保证金")
    private String wyBondClause;


    /**
     * 物业单价
     */
    @ExcelProperty("物业单价")
    private String wyDjMoney;

    private String pdfFileUrl;
}
