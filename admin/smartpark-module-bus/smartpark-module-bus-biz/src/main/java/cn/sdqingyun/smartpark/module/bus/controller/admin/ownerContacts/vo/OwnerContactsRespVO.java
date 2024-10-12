package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 租客联系人 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OwnerContactsRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13542")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "21504")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "联系人姓名", example = "王五")
    @ExcelProperty("联系人姓名")
    private String name;

    @Schema(description = "手机")
    @ExcelProperty("手机")
    private String phone;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;

    @Schema(description = "所属公司id", example = "4221")
    @ExcelProperty("所属公司id")
    private Long ownerId;

    @Schema(description = "通讯地址")
    @ExcelProperty("通讯地址")
    private String address;

    @Schema(description = "是否默认联系人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否默认联系人")
    private Integer isDefault;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}