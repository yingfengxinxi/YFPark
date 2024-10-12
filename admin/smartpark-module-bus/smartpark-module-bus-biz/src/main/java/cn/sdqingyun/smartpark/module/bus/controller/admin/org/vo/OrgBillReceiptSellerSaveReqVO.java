package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 收据收款方信息新增/修改 Request VO")
@Data
public class OrgBillReceiptSellerSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13227")
    private Long id;

    @Schema(description = "收款方单位名称", example = "李四")
    private String companyName;

    @Schema(description = "收款人名称", example = "张三")
    private String sellerName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    private String buildBind;

}