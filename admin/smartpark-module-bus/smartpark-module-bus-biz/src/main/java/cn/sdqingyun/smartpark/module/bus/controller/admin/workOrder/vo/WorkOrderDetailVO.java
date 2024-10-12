package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeLogDO;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/9/10 14:23
 */
@Data
public class WorkOrderDetailVO {

    @Schema(description = "工单编号")
    private String number;

    @Schema(description = "工单分类类型【工单类型】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String orderType;

    @Schema(description = "报修类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String repairType;

    @Schema(description = "报修类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String repairTypeName;

    @Schema(description = "上报人名称", example = "张三")
    private String name;

    @Schema(description = "上报人手机")
    private String phone;

    @Schema(description = "楼宇id", example = "30763")
    private Long buildId;

    @Schema(description = "所属楼宇", example = "30763")
    private String buildName;

    @Schema(description = "对应位置")
    private String position;

    @Schema(description = "工单来源")
    private String from;

    @Schema(description = "上报时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date appearTime;

    @Schema(description = "上门时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date visitTime;


    private String dayOfWeek;


    @Schema(description = "完成时限")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;

    @Schema(description = "是否超时;0=否;1=是;")
    private String isTimeout;

    @Schema(description = "费用支付方", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paidPayer;
    @Schema(description = "费用支付方名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paidPayerName;

    @Schema(description = "描述标签")
    private String labelJson;

    @Schema(description = "补充内容", example = "你猜")
    private String remark;

    @Schema(description = "上报内容;支持多张")
    private String images;

    @Schema(description = "处理记录")
    private List<WorkOrderProposeLogDO> orderProposeLogList;

    @Schema(description = "工单收费情况")
    private String orderData;

    @Schema(description = "工单状态")
    private String workOrderStatusName;


}
