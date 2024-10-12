package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户信息扩展 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserExtendsRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25046")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19532")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6608")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "user service user 表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9044")
    @ExcelProperty("user service user 表id")
    private Long userId;

    @Schema(description = "village service village_user 表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6083")
    @ExcelProperty("village service village_user 表id")
    private Long villageUserId;

    @Schema(description = "民族", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("民族")
    private Integer nation;

    @Schema(description = "学历", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("学历")
    private Integer education;

    @Schema(description = "专业", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("专业")
    private String major;

    @Schema(description = "职称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("职称")
    private String jobTitle;

    @Schema(description = "政治面貌", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("政治面貌")
    private Integer politicalStatus;

    @Schema(description = "婚姻状况", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("婚姻状况")
    private Integer maritalStatus;

    @Schema(description = "照护等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("照护等级")
    private Integer careLevel;

    @Schema(description = "居住情况", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("居住情况")
    private Integer livingConditions;

    @Schema(description = "服务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("服务类型")
    private Integer serviceType;

    @Schema(description = "失能情况", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("失能情况")
    private Integer failure;

    @Schema(description = "慢性病[多选]")
    @ExcelProperty("慢性病[多选]")
    private String chronic;

    @Schema(description = "血型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("血型")
    private Integer bloodType;

    @Schema(description = "残疾情况", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("残疾情况")
    private Integer disabled;

    @Schema(description = "是否托管", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否托管")
    private Integer custody;

    @Schema(description = "联系人座机", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("联系人座机")
    private String contacTel;

    @Schema(description = "职务", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("职务")
    private String position;

    @Schema(description = "称呼", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("称呼")
    private String callName;

    @Schema(description = "公司传真", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("公司传真")
    private String companyFax;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "QQ", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("QQ")
    private String qq;

    @Schema(description = "阿里旺旺", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("阿里旺旺")
    private String aliwangwang;

    @Schema(description = "微信", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("微信")
    private String weixin;

    @Schema(description = "其它联系方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("其它联系方式")
    private String otherContacMethod;

    @Schema(description = "家庭住址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("家庭住址")
    private String homeAddress;

    @Schema(description = "家庭电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("家庭电话")
    private String homeTel;

    @Schema(description = "户籍", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("户籍")
    private String houseRegistration;

    @Schema(description = "国籍", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("国籍")
    private String countryRegistration;

    @Schema(description = "户口所在地", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("户口所在地")
    private String placeResidence;

    @Schema(description = "紧急联系人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("紧急联系人")
    private String emergeContact;

    @Schema(description = "工作单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("工作单位")
    private String employer;

    @Schema(description = "工作年限", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("工作年限")
    private Integer workingYears;

    @Schema(description = "单位性质", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("单位性质")
    private String companyType;

    @Schema(description = "工作单位地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("工作单位地址")
    private String workAddress;

    @Schema(description = "住房性质", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("住房性质")
    private Integer houseType;

    @Schema(description = "入党时间")
    @ExcelProperty("入党时间")
    private LocalDateTime joinPartyTime;

    @Schema(description = "月收入(元)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("月收入(元)")
    private String monthIncome;

    @Schema(description = "离退休人员", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("离退休人员")
    private Integer retiree;

    @Schema(description = "用工性质", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("用工性质")
    private Integer natureEmployment;

    @Schema(description = "社区重点人群", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("社区重点人群")
    private Integer communityGroup;

    @Schema(description = "是否重点儿童", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否重点儿童")
    private Integer foucusChildren;

    @Schema(description = "是否低保户", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否低保户")
    private Integer isDibaohu;

    @Schema(description = "参保类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("参保类型")
    private Integer insuranceType;

    @Schema(description = "信仰宗教", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("信仰宗教")
    private Integer religiousBelief;

    @Schema(description = "技能证书")
    @ExcelProperty("技能证书")
    private String skillCertificate;

    @Schema(description = "毕业院校", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("毕业院校")
    private String graduatedShcool;

    @Schema(description = "房间唯一编号id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12594")
    @ExcelProperty("房间唯一编号id")
    private String uniqueId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}