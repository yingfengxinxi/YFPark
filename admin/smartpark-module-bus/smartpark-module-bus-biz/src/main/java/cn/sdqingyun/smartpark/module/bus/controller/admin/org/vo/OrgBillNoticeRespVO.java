package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 收款通知记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillNoticeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18130")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "租客id", example = "15957")
    @ExcelProperty("租客id")
    private Long ownerId;

    @Schema(description = "合同id", example = "7089")
    @ExcelProperty("合同id")
    private Long contractId;

    @Schema(description = "楼宇id", example = "27698")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "合同账单明细id集合")
    private String contractBillId;

    @Schema(description = "生成方式;1=逐张生成;2=按租客合并;3=按合同合并;", example = "1")
    @ExcelProperty("生成方式;1=逐张生成;2=按租客合并;3=按合同合并;")
    private String buildType;

    @Schema(description = "通知方式;1=下载打印;2=短信通知;3=邮件通知;4=站内信", example = "1")
    @ExcelProperty("通知方式;1=下载打印;2=短信通知;3=邮件通知;4=站内信")
    private String noticeType;

    @Schema(description = "打包方式;1=逐张打包;2=合并zip", example = "2")
    @ExcelProperty("打包方式;1=逐张打包;2=合并zip")
    private String packType;

    @Schema(description = "短信发送状态;1=失败;2=成功", example = "1")
    @ExcelProperty("短信发送状态;1=失败;2=成功")
    private String smsStatus;

    @Schema(description = "邮箱发送状态;1=失败;2=成功", example = "1")
    @ExcelProperty("邮箱发送状态;1=失败;2=成功")
    private String emailStatus;

    @Schema(description = "微信发送状态;1=失败;2=成功", example = "2")
    @ExcelProperty("微信发送状态;1=失败;2=成功")
    private String wechatStatus;

    @Schema(description = "附收款二维码;1=是;0=否", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("附收款二维码;1=是;0=否")
    private String hasQrcode;

    @Schema(description = "通知单原始json")
    @ExcelProperty("通知单原始json")
    private String formData;

    @Schema(description = "通知单涉及源账单信息")
    @ExcelProperty("通知单涉及源账单信息")
    private String billData;

    @Schema(description = "通知单文件信息json")
    @ExcelProperty("通知单文件信息json")
    private String filePath;

    @Schema(description = "生成批次标识task_key")
    @ExcelProperty("生成批次标识task_key")
    private String taskKey;

    @Schema(description = "生成日期")
    @ExcelProperty("生成日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date buildDate;

    @Schema(description = "操作时间")
    @ExcelProperty("操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

}