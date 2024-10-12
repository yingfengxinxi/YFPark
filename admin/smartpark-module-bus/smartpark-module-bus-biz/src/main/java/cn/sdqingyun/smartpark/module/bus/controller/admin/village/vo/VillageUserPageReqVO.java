package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目用户/租客分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VillageUserPageReqVO extends PageParam {

    @Schema(description = "人员针对房间的唯一编号", example = "17407")
    private String uniqidId;

    @Schema(description = "用户表ID，可能为空", example = "18379")
    private Long userId;

    @Schema(description = "归属租客ID", example = "4089")
    private Long ownerId;

    @Schema(description = "楼栋ID", example = "24774")
    private Long buildId;

    @Schema(description = "部门ID", example = "15817")
    private Long orgId;

    @Schema(description = "项目ID", example = "6578")
    private Long villageId;

    @Schema(description = "人员名称（128位加密）", example = "张三")
    private String name;

    @Schema(description = "人员手机号（128位加密）")
    private String phone;

    @Schema(description = "住户联系邮箱")
    private String email;

    @Schema(description = "通讯地址")
    private String address;

    @Schema(description = "是否为默认租客住户联系人")
    private Integer isDefault;

    @Schema(description = "员工身份（超级管理员、管理员、普通员工）", example = "0")
    private Short type;

    @Schema(description = "有效期开始（特别指访客）")
    private LocalDateTime effectiveTimeStart;

    @Schema(description = "有效期结束（特别指访客）")
    private LocalDateTime effectiveTimeEnd;

    @Schema(description = "证件类型（身份证、港澳台、护照等）", example = "2")
    private String idcardType;

    @Schema(description = "证件号（128位加密）")
    private String idcard;

    @Schema(description = "出生年（年月日尽量从身份证中获取）")
    private Short birthYear;

    @Schema(description = "出生月（年月日尽量从身份证中获取）")
    private Short birthMonth;

    @Schema(description = "出生日（年月日尽量从身份证中获取）")
    private Short birthDay;

    @Schema(description = "性别（1男，2女，0未知）（年月日尽量从身份证中获取）")
    private Integer sex;

    @Schema(description = "学历信息", example = "2")
    private Integer eduType;

    @Schema(description = "工作年限")
    private String workYear;

    @Schema(description = "毕业院校")
    private String gradSchool;

    @Schema(description = "技能证书")
    private String skCert;

    @Schema(description = "身份证照片(包含手持身份证图片){front_url:,back_url:,hand_url:}")
    private String idcardImg;

    @Schema(description = "照片网址（128加密）,后期若有多个用英文逗号分割")
    private String photo;

    @Schema(description = "照片数据状态（0+正常，10+审核中，20+失败）", example = "2")
    private Integer photoStatus;

    @Schema(description = "照片状态的描述文本（一般用于状态失败原因描述）")
    private Long photoStatusDesc;

    @Schema(description = "是否拥有代付权限")
    private Short inAdvance;

    @Schema(description = "是否代付通知")
    private Short inAdvanceNotice;

    @Schema(description = "数据状态（0审核中，1正常，4拒绝）", example = "1")
    private Integer status;

    @Schema(description = "租客端最后选择此身份的时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] lastChooseTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}