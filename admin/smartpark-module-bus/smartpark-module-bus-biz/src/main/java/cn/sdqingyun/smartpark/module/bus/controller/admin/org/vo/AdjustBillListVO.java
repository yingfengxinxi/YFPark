package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAdjustDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/7/26 16:22
 */
@Data
public class AdjustBillListVO extends PageParam {

    @Schema(description = "调整账单id")
    private Long id;

    @Schema(description = "账单id")
    private Long billId;

    @Schema(description = "租客名称")
    private String ownerName;

    @Schema(description = "租客Id")
    private String ownerId;

    @Schema(description = "是否个人，1个人，0公司")
    private Integer isPersonal;

    @Schema(description = "楼宇名称")
    private String buildName;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "合同Id")
    private String contractId;

    @Schema(description = "财务编号")
    private String orderNumber;

    @Schema(description = "账单状态")
    private String billStatus;

    @Schema(description = "调整账单")
    private List<OrgBillAdjustDO> adjustList;

    @Schema(description = "项目名称集合")
    private List<Long> parkIdList;

    @Schema(description = "楼宇名称集合")
    private List<Long> billIdList;

    @Schema(description = "调整类型")
    private String adjustType;

    @Schema(description = "调整方式0=按金额调整1=按比例调整")
    private String adjustMode;

    @Schema(description = "调整金额")
    private BigDecimal adjustPrice;

    private List<String>adjustTypeList;
}
