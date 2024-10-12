package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 机构楼宇售方信息(发票设置)新增/修改 Request VO")
@Data
public class OrgSellerSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31291")
    private Long id;

    @Schema(description = "售方公司名称", example = "芋艿")
    private String companyName;

    @Schema(description = "纳税人识别号")
    private String taxpayerNumber;

    @Schema(description = "开户行")
    private String bank;

    @Schema(description = "开户行账号", example = "13872")
    private String bankAccount;

    @Schema(description = "售方电话信息")
    private String phone;

    @Schema(description = "开票地址")
    private String address;

}