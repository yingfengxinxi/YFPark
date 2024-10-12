package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 用户信息扩展新增/修改 Request VO")
@Data
public class UserExtendsSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", example = "25046")
    private Long id;

    @Schema(description = "机构id", example = "19532")
    private Long orgId;

    @Schema(description = "项目id", example = "6608")
    private Long villageId;

    @Schema(description = "user service user 表id", example = "9044")
    private Long userId;

    @Schema(description = "village service village_user 表id", example = "6083")
    private Long villageUserId;

    @Schema(description = "民族")
    private Integer nation;

    @Schema(description = "学历")
    private Integer education;

    @Schema(description = "专业")
    private String major;

    @Schema(description = "职称")
    private String jobTitle;

    @Schema(description = "政治面貌", example = "1")
    private Integer politicalStatus;

    @Schema(description = "婚姻状况", example = "1")
    private Integer maritalStatus;

    @Schema(description = "照护等级")
    private Integer careLevel;

    @Schema(description = "居住情况")
    private Integer livingConditions;

    @Schema(description = "服务类型", example = "2")
    private Integer serviceType;

    @Schema(description = "失能情况")
    private Integer failure;

    @Schema(description = "慢性病[多选]")
    private String chronic;

    @Schema(description = "血型", example = "1")
    private Integer bloodType;

    @Schema(description = "残疾情况")
    private Integer disabled;

    @Schema(description = "是否托管")
    private Integer custody;

    @Schema(description = "联系人座机")
    private String contacTel;

    @Schema(description = "职务")
    private String position;

    @Schema(description = "称呼", example = "芋艿")
    private String callName;

    @Schema(description = "公司传真")
    private String companyFax;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "阿里旺旺")
    private String aliwangwang;

    @Schema(description = "微信")
    private String weixin;

    @Schema(description = "其它联系方式")
    private String otherContacMethod;

    @Schema(description = "家庭住址")
    private String homeAddress;

    @Schema(description = "家庭电话")
    private String homeTel;

    @Schema(description = "户籍")
    private String houseRegistration;

    @Schema(description = "国籍")
    private String countryRegistration;

    @Schema(description = "户口所在地")
    private String placeResidence;

    @Schema(description = "紧急联系人")
    private String emergeContact;

    @Schema(description = "工作单位")
    private String employer;

    @Schema(description = "工作年限")
    private Integer workingYears;

    @Schema(description = "单位性质")
    private String companyType;

    @Schema(description = "工作单位地址")
    private String workAddress;

    @Schema(description = "住房性质", example = "1")
    private Integer houseType;

    @Schema(description = "入党时间")
    private LocalDateTime joinPartyTime;

    @Schema(description = "月收入(元)")
    private String monthIncome;

    @Schema(description = "离退休人员")
    private Integer retiree;

    @Schema(description = "用工性质")
    private Integer natureEmployment;

    @Schema(description = "社区重点人群")
    private Integer communityGroup;

    @Schema(description = "是否重点儿童")
    private Integer foucusChildren;

    @Schema(description = "是否低保户")
    private Integer isDibaohu;

    @Schema(description = "参保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "信仰宗教")
    private Integer religiousBelief;

    @Schema(description = "技能证书")
    private String skillCertificate;

    @Schema(description = "毕业院校")
    private String graduatedShcool;

    @Schema(description = "房间唯一编号id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12594")
    private String uniqueId;

}