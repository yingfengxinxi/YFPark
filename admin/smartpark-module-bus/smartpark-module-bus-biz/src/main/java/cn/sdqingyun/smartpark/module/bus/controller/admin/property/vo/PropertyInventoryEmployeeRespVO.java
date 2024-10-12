package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产盘点员工记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyInventoryEmployeeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22856")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24926")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "盘点清单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8453")
    @ExcelProperty("盘点清单id")
    private Long inventoryId;

    @Schema(description = "盘点员工id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8609")
    @ExcelProperty("盘点员工id")
    private Long userId;

    @Schema(description = "资产分类id")
    @ExcelProperty("资产分类id")
    private String typeIds;

    @Schema(description = "部门id")
    @ExcelProperty("部门id")
    private String departmentIds;

    @Schema(description = "位置id")
    @ExcelProperty("位置id")
    private String positionIds;

    @Schema(description = "支付状态", example = "2")
    @ExcelProperty("支付状态")
    private String propertyStatus;

    @Schema(description = "管理员id", example = "13174")
    @ExcelProperty("管理员id")
    private String adminId;

    @Schema(description = "购置方式", example = "1")
    @ExcelProperty("购置方式")
    private String purchaseType;

    @Schema(description = "完成状态 1是0否", example = "2")
    @ExcelProperty("完成状态 1是0否")
    private Integer status;

    @Schema(description = "是否是全部权限", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否是全部权限")
    private Integer isAll;

    @Schema(description = "每个用户的范围回显冗余字段")
    @ExcelProperty("每个用户的范围回显冗余字段")
    private String exterData;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}