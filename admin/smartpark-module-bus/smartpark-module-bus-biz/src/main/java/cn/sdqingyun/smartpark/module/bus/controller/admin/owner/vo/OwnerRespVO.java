package cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 租客信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OwnerRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11852")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "15270")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "租客名称", example = "芋艿")
    @ExcelProperty("租客名称")
    private String name;

    @Schema(description = "是否个人，1个人，0公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否个人，1个人，0公司")
    private Long isPersonal;

    @Schema(description = "是否推荐;0=不推荐;1=推荐;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否推荐;0=不推荐;1=推荐;")
    private Short isSuggest;

    @Schema(description = "目标对象;0=正常租客;1=收款对象;2=付款对象;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("目标对象;0=正常租客;1=收款对象;2=付款对象;")
    private Short type;

    @Schema(description = "虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;")
    private Short sham;

    @Schema(description = "联系人", example = "13864")
    @ExcelProperty("联系人")
    private Long contactId;

    @Schema(description = "租客合同签署人id", example = "8030")
    @ExcelProperty("租客合同签署人id")
    private Long contactSignId;

    @Schema(description = "缴费通知单联系人", example = "22846")
    @ExcelProperty("缴费通知单联系人")
    private Long contactNoticeId;

    @Schema(description = "审批联系人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26439")
    @ExcelProperty("审批联系人id")
    private Long approvalContactId;

    @Schema(description = "证件号码")
    @ExcelProperty("证件号码")
    private String certificateNumber;

    @Schema(description = "行业分类id", example = "4527")
    @ExcelProperty("行业分类id")
    private Long industryId;

    @Schema(description = "租客绑定的项目id", example = "9441")
    @ExcelProperty("租客绑定的项目id")
    private String villageIdList;

    @Schema(description = "租客绑定的项目楼宇房源信息")
    @ExcelProperty("租客绑定的项目楼宇房源信息")
    private String buildBind;

    @Schema(description = "联系方式")
    @ExcelProperty("联系方式")
    private String tel;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;

    @Schema(description = "租客编码")
    @ExcelProperty("租客编码")
    private String tenantNo;

    @Schema(description = "官网地址")
    @ExcelProperty("官网地址")
    private String websiteLink;

    @Schema(description = "企业logo")
    @ExcelProperty("企业logo")
    private String logo;

    @Schema(description = "企业简介")
    @ExcelProperty("企业简介")
    private String companyDesc;

    @Schema(description = "成立日期")
    @ExcelProperty("成立日期")
    private LocalDate businessInfoFoundingTime;

    @Schema(description = "营业期限")
    @ExcelProperty("营业期限")
    private LocalDate businessInfoBusinessTerm;

    @Schema(description = "注册资金")
    @ExcelProperty("注册资金")
    private BigDecimal registeredCapital;

    @Schema(description = "开票信息")
    @ExcelProperty("开票信息")
    private String invoiceInfo;

    @Schema(description = "工商信息")
    @ExcelProperty("工商信息")
    private String businessInfo;

    @Schema(description = "租客标签")
    @ExcelProperty("租客标签")
    private String tagInfo;

    @Schema(description = "自定义字段")
    @ExcelProperty("自定义字段")
    private String diyField;

    @Schema(description = "是否开启公司代付通知", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否开启公司代付通知")
    private Short isAdvanceNotice;

    @Schema(description = "是否归档", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否归档")
    private Short isArchive;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "开户行")
    private String bank;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "电话")
    private String bankPhone;

    @Schema(description = "纳税人识别号")
    private String taxpayerIdentificationNumber;


    @Schema(description = "发票类型0=增值税普通发票1=增值税专用发票2=增值税电子普通发票")
    private String invoiceType;

    @Schema(description = "开票地址")
    private String billingAddress;

    @Schema(description = "联系人姓名")
    private String contactName;

    @Schema(description = "租客绑定的项目名称")
    private String villageName;

    @Schema(description = "租客绑定的项目楼宇房源名称")
    private String buildBindName;

    /**
     * 合同id
     */
    @Schema(description = "合同id")
    @TableField(exist = false)
    private Long contractId;
}