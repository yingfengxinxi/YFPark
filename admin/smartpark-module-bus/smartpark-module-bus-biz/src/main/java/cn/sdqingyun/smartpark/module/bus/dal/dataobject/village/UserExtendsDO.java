package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户信息扩展 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_user_extends")
@KeySequence("village_user_extends_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExtendsDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 项目id
     */
    private Long villageId;
    /**
     * user service user 表id
     */
    private Long userId;
    /**
     * village service village_user 表id
     */
    private Long villageUserId;
    /**
     * 民族
     */
    private Integer nation;
    /**
     * 学历
     */
    private Integer education;
    /**
     * 专业
     */
    private String major;
    /**
     * 职称
     */
    private String jobTitle;
    /**
     * 政治面貌
     */
    private Integer politicalStatus;
    /**
     * 婚姻状况
     */
    private Integer maritalStatus;
    /**
     * 照护等级
     */
    private Integer careLevel;
    /**
     * 居住情况
     */
    private Integer livingConditions;
    /**
     * 服务类型
     */
    private Integer serviceType;
    /**
     * 失能情况
     */
    private Integer failure;
    /**
     * 慢性病[多选]
     */
    private String chronic;
    /**
     * 血型
     */
    private Integer bloodType;
    /**
     * 残疾情况
     */
    private Integer disabled;
    /**
     * 是否托管
     */
    private Integer custody;
    /**
     * 联系人座机
     */
    private String contacTel;
    /**
     * 职务
     */
    private String position;
    /**
     * 称呼
     */
    private String callName;
    /**
     * 公司传真
     */
    private String companyFax;
    /**
     * 备注
     */
    private String remark;
    /**
     * QQ
     */
    private String qq;
    /**
     * 阿里旺旺
     */
    private String aliwangwang;
    /**
     * 微信
     */
    private String weixin;
    /**
     * 其它联系方式
     */
    private String otherContacMethod;
    /**
     * 家庭住址
     */
    private String homeAddress;
    /**
     * 家庭电话
     */
    private String homeTel;
    /**
     * 户籍
     */
    private String houseRegistration;
    /**
     * 国籍
     */
    private String countryRegistration;
    /**
     * 户口所在地
     */
    private String placeResidence;
    /**
     * 紧急联系人
     */
    private String emergeContact;
    /**
     * 工作单位
     */
    private String employer;
    /**
     * 工作年限
     */
    private Integer workingYears;
    /**
     * 单位性质
     */
    private String companyType;
    /**
     * 工作单位地址
     */
    private String workAddress;
    /**
     * 住房性质
     */
    private Integer houseType;
    /**
     * 入党时间
     */
    private LocalDateTime joinPartyTime;
    /**
     * 月收入(元)
     */
    private String monthIncome;
    /**
     * 离退休人员
     */
    private Integer retiree;
    /**
     * 用工性质
     */
    private Integer natureEmployment;
    /**
     * 社区重点人群
     */
    private Integer communityGroup;
    /**
     * 是否重点儿童
     */
    private Integer foucusChildren;
    /**
     * 是否低保户
     */
    private Integer isDibaohu;
    /**
     * 参保类型
     */
    private Integer insuranceType;
    /**
     * 信仰宗教
     */
    private Integer religiousBelief;
    /**
     * 技能证书
     */
    private String skillCertificate;
    /**
     * 毕业院校
     */
    private String graduatedShcool;
    /**
     * 房间唯一编号id
     */
    private String uniqueId;

}