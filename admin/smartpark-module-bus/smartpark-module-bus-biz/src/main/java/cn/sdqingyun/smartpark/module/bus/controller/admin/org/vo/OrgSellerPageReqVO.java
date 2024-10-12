package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 机构楼宇售方信息(发票设置)分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgSellerPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}