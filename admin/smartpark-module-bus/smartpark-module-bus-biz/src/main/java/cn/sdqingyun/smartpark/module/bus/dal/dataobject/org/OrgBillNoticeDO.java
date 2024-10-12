package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 收款通知记录 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_notice")
@KeySequence("org_bill_notice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillNoticeDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 楼宇id
     */
    private Long buildId;


    @Schema(description = "收支账户id", example = "27698")
    private String accountId;

    /**
     * 合同账单明细id集合
     */
    private String contractBillId;
    /**
     * 生成方式;1=逐张生成;2=按租客合并;3=按合同合并;
     */
    private String buildType;
    /**
     * 通知方式;1=下载打印;2=短信通知;3=邮件通知;4=站内信
     */
    private String noticeType;
    /**
     * 打包方式;1=逐张打包;2=合并zip
     */
    private String packType;
    /**
     * 短信发送状态;1=失败;2=成功
     */
    private String smsStatus;
    /**
     * 邮箱发送状态;1=失败;2=成功
     */
    private String emailStatus;
    /**
     * 微信发送状态;1=失败;2=成功
     */
    private String wechatStatus;
    /**
     * 站内信发送状态;1=失败;2=成功
     */
    private String mailStatus;
    /**
     * 附收款二维码;1=是;0=否
     */
    private String hasQrcode;
    /**
     * 通知单原始json
     */
    private String formData;
    /**
     * 通知单涉及源账单信息
     */
    private String billData;
    /**
     * 通知单文件信息json
     */
    private String filePath;
    /**
     * 生成批次标识task_key
     */
    private String taskKey;
    /**
     * 生成日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date buildDate;

}