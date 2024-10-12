package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 收款通知记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgBillNoticePageReqVO extends PageParam {

    @Schema(description = "租客id", example = "15957")
    private Long ownerId;

    @Schema(description = "合同id", example = "7089")
    private Long contractId;

    @Schema(description = "楼宇id", example = "27698")
    private Long buildId;

    @Schema(description = "合同账单明细id集合")
    private String contractBillId;

    @Schema(description = "生成方式;1=逐张生成;2=按租客合并;3=按合同合并;", example = "1")
    private String buildType;

    @Schema(description = "通知方式;1=下载打印;2=短信通知;3=邮件通知;4=站内信", example = "1")
    private String noticeType;

    @Schema(description = "打包方式;1=逐张打包;2=合并zip", example = "2")
    private String packType;

    @Schema(description = "短信发送状态;1=失败;2=成功", example = "1")
    private String smsStatus;

    @Schema(description = "邮箱发送状态;1=失败;2=成功", example = "1")
    private String emailStatus;

    @Schema(description = "微信发送状态;1=失败;2=成功", example = "2")
    private String wechatStatus;

    @Schema(description = "附收款二维码;1=是;0=否")
    private String hasQrcode;

    @Schema(description = "通知单原始json")
    private String formData;

    @Schema(description = "通知单涉及源账单信息")
    private String billData;

    @Schema(description = "通知单文件信息json")
    private String filePath;

    @Schema(description = "生成批次标识task_key")
    private String taskKey;

    @Schema(description = "生成日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] buildDate;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}