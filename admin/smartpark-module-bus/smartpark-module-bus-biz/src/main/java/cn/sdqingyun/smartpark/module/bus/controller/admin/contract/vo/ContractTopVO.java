package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 租赁面积、单价排行 VO")
@Data
public class ContractTopVO {

    @Schema(description = "租客id")
    @NotNull(message = "租客id不能为空")
    private Long ownerId;

    @Schema(description = "租客名称")
    private String ownerName;

    @Schema(description = "楼宇id")
    private Long buildId;

    @Schema(description = "项目id")
    private Long parkId;

    @Schema(description = "单价")
    private String leaseUnitPrice;

    @Schema(description = "租赁总面积")
    private String leaseArea;

    @Schema(description = "租赁天数")
    private Long leaseDay;

    @Schema(description = "资源")
    private String name;

    @Schema(description = "是否个人，1个人，0公司")
    private Integer isPersonal;
}