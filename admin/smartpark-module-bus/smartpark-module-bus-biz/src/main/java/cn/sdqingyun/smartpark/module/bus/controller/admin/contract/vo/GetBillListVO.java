package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/7/5 13:15
 */
@Data
public class GetBillListVO extends PageParam {

    /**
     *
     */
    @Schema(description = "账单id")
    private Long billId;

    @Schema(description = "园区名称")
    private String villageName;

    @Schema(description = "楼宇名称")
    private String buildName;

    @Schema(description = "付款人姓名")
    private String ownerName;
    private Long ownerId;

    @Schema(description = "房号id")
    private String roomNumber;

    @Schema(description = "房号")
    private String roomName;

    @Schema(description = "账单类型")
    private String feeType;

    @Schema(description = "应收金额")
    private String receivable;

    @Schema(description = "匹配金额")
    private String matchPrice;

    @Schema(description = "应收日期")
    private Date payDate;

    @Schema(description = "缴费开始周期")
    private Date payStartDate;

    @Schema(description = "缴费结束周期")
    private Date payEndDate;

}
