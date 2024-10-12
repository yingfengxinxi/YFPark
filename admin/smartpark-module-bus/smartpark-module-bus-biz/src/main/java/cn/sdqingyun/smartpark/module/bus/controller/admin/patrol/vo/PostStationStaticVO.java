package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/9/2 10:16
 */
@Data
public class PostStationStaticVO {

    @Schema(description = "岗位id", example = "1167")
    private Long stationId;

    @ExcelProperty("责任岗位")
    private String stationName;

    @ExcelProperty("应巡检次数")
    private Integer shouldTotal;

    @ExcelProperty("实际巡检次数")
    private Integer actualTotal;

    @ExcelProperty("正常")
    private Integer normalTotal;

    @ExcelProperty("异常")
    private Integer abnormalTotal;

    @ExcelProperty("跳过")
    private Integer skipTotal;


    private String status;

    private Integer total;

    private String startDate;

    private String endDate;

    private Long tenantId;
}
