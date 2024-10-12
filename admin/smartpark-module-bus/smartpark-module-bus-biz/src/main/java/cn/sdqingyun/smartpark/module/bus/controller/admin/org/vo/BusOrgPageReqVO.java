package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 机构分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusOrgPageReqVO extends PageParam {

    @Schema(description = "机构缩写名称", example = "张三")
    private String name;

    @Schema(description = "管理员用户id", example = "31240")
    private String adminUid;

    @Schema(description = "机构全名称")
    private String company;

    @Schema(description = "省份id", example = "17211")
    private Long provinceId;

    @Schema(description = "市id", example = "16535")
    private Long cityId;

    @Schema(description = "省市地址;英文逗号,拼接", example = "李四")
    private String districtName;

    @Schema(description = "机构介绍")
    private String info;

    @Schema(description = "机构logo")
    private String logo;

    @Schema(description = "正方形logo（建议透明的白色）")
    private String squareLogo;

    @Schema(description = "机构总人数")
    private Integer total;

    @Schema(description = "联系电话")
    private String tel;

    @Schema(description = "联系地址")
    private String address;

    @Schema(description = "机构类别", example = "2")
    private String type;

    @Schema(description = "机构专属域名前缀（若不填写则访问专属域名时展示平台信息，例如 admin-id.xxx.com）")
    private String domainPrefix;

    @Schema(description = "是否为OEM")
    private Integer isOem;

    @Schema(description = "公众号的微信号")
    private String wechatNumber;

    @Schema(description = "机构成员工号前缀(支持自定义)")
    private String orgPrefix;

    @Schema(description = "机构码(8位随机字符串)")
    private String joinCode;

    @Schema(description = "机构状态，1启动，0审核中，4禁用", example = "2")
    private Integer status;

    @Schema(description = "字典（替换页面上的字）")
    private String dictionary;

    @Schema(description = "支持开放的模块权限")
    private String module;

    @Schema(description = "允许的项目类型", example = "2")
    private String villageType;

    @Schema(description = "过期日期（当天23:59才过期）")
    private LocalDateTime overdueDay;

    @Schema(description = "招商电话")
    private String clueTel;

    @Schema(description = "招商时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] clueTime;

    @Schema(description = "注册来源(0自己注册，1企微，2后台添加，3钉钉...)")
    private Integer regFrom;

    @Schema(description = "最大可使用面积")
    private BigDecimal maxUseArea;

    @Schema(description = "最大可使用房间数")
    private Integer maxUseRoom;

    @Schema(description = "最后登录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] lastLoginTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}