package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 机构楼宇售方信息(发票设置) Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgSellerRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31291")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "售方公司名称", example = "芋艿")
    @ExcelProperty("售方公司名称")
    private String companyName;

    @Schema(description = "纳税人识别号")
    @ExcelProperty("纳税人识别号")
    private String taxpayerNumber;

    @Schema(description = "开户行")
    @ExcelProperty("开户行")
    private String bank;

    @Schema(description = "开户行账号", example = "13872")
    @ExcelProperty("开户行账号")
    private String bankAccount;

    @Schema(description = "售方电话信息")
    @ExcelProperty("售方电话信息")
    private String phone;

    @Schema(description = "开票地址")
    @ExcelProperty("开票地址")
    private String address;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}