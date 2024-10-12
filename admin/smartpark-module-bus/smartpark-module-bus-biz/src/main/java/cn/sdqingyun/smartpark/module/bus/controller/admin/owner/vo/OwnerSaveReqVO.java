package cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 租客信息新增/修改 Request VO")
@Data
public class OwnerSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", example = "11852")
    private Long id;

    @Schema(description = "机构ID", example = "15270")
    private Long orgId;

    @Schema(description = "租客名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "中国信息科技")
    private String name;

    @Schema(description = "是否个人，1个人，0公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否个人，1个人，0公司不能为空")
    private Integer isPersonal;

    @Schema(description = "是否推荐;0=不推荐;1=推荐;")
    @NotNull(message = "是否推荐;0=不推荐;1=推荐;不能为空")
    private Short isSuggest;

    @Schema(description = "目标对象;0=正常租客;1=收款对象;2=付款对象;", example = "2")
    private Short type;

    @Schema(description = "虚拟身份注册;0=无;1=虚拟个人注册;2=虚拟企业注册;")
    private Short sham;

    @Schema(description = "联系人", example = "13864")
    private Long contactId;

    @Schema(description = "租客合同签署人id", example = "8030")
    private Long contactSignId;

    @Schema(description = "缴费通知单联系人", example = "22846")
    private Long contactNoticeId;

    @Schema(description = "审批联系人id", example = "26439")
    private Long approvalContactId;

    @Schema(description = "证件号码")
    private String certificateNumber;

    @Schema(description = "行业分类id", example = "4527")
    private Long industryId;

    @Schema(description = "租客绑定的项目id", example = "9441")
    private String villageIdList;

    @Schema(description = "租客绑定的项目楼宇房源信息")
    private String buildBind;

    @Schema(description = "联系方式", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tel;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "租客编码")
    private String tenantNo;

    @Schema(description = "官网地址")
    private String websiteLink;

    @Schema(description = "企业logo")
    private String logo;

    @Schema(description = "企业简介")
    private String companyDesc;

    @Schema(description = "成立日期")
    private LocalDate businessInfoFoundingTime;

    @Schema(description = "营业期限")
    private LocalDate businessInfoBusinessTerm;

    @Schema(description = "注册资金")
    private BigDecimal registeredCapital;

    @Schema(description = "开票信息")
    private String invoiceInfo;

    @Schema(description = "工商信息")
    private String businessInfo;

    @Schema(description = "租客标签")
    private String tagInfo;

    @Schema(description = "自定义字段")
    private String diyField;

    @Schema(description = "是否开启公司代付通知")
    private Short isAdvanceNotice;

    @Schema(description = "是否归档")
    private Short isArchive;

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


    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String contactName;

    @Schema(description = "0房主，10家属，20租户，30物业人员，40服务人员，50访客", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Short VillageType;

    @Schema(description = "证件类型", example = "大陆身份证")
    private String idcardType;

    @ExcelProperty("联系人通讯地址")
    private String address;

    @Schema(description = "租客绑定的项目id,传参环形图使用")
    private String villageId;
}