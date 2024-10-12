package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 租客联系人分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OwnerContactsPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "21504")
    private Long orgId;

    @Schema(description = "联系人姓名", example = "王五")
    private String name;

    @Schema(description = "手机")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "所属公司id", example = "4221")
    private Long ownerId;

    @Schema(description = "通讯地址")
    private String address;

    @Schema(description = "是否默认联系人")
    private Integer isDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}