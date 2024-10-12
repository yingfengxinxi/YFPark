package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 收据收款方信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillReceiptSellerRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13227")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "收款方单位名称", example = "李四")
    @ExcelProperty("收款方单位名称")
    private String companyName;

    @Schema(description = "收款人名称", example = "张三")
    @ExcelProperty("收款人名称")
    private String sellerName;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "电话")
    @ExcelProperty("电话")
    private String phone;

    @Schema(description = "应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    @ExcelProperty("应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    private String buildBind;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}