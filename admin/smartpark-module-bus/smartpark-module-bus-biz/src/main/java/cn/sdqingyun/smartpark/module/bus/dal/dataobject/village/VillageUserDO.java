package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目用户/租客 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_user")
@KeySequence("village_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillageUserDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 人员针对房间的唯一编号
     */
    private String uniqidId;
    /**
     * 用户表ID，可能为空
     */
    private Long userId;
    /**
     * 归属租客ID
     */
    private Long ownerId;
    /**
     * 楼栋ID
     */
    private Long buildId;
    /**
     * 部门ID
     */
    private Long orgId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 人员名称（128位加密）
     */
    private String name;
    /**
     * 人员手机号（128位加密）
     */
    private String phone;
    /**
     * 住户联系邮箱
     */
    private String email;
    /**
     * 通讯地址
     */
    private String address;
    /**
     * 是否为默认租客住户联系人
     */
    private Integer isDefault;
    /**
     * 0+房主，10+家属，20+租户，30+物业人员，40+服务人员，50+访客
     */
    private Short type;
    /**
     * 有效期开始（特别指访客）
     */
    private LocalDateTime effectiveTimeStart;
    /**
     * 有效期结束（特别指访客）
     */
    private LocalDateTime effectiveTimeEnd;
    /**
     * 证件类型（大陆身份证、港澳台、护照等）
     */
    private String idcardType;
    /**
     * 证件号（128位加密）
     */
    private String idcard;
    /**
     * 出生年（年月日尽量从身份证中获取）
     */
    private Short birthYear;
    /**
     * 出生月（年月日尽量从身份证中获取）
     */
    private Short birthMonth;
    /**
     * 出生日（年月日尽量从身份证中获取）
     */
    private Short birthDay;
    /**
     * 性别（1男，2女，0未知）（年月日尽量从身份证中获取）
     */
    private Integer sex;
    /**
     * 学历信息
     */
    private Integer eduType;
    /**
     * 工作年限
     */
    private String workYear;
    /**
     * 毕业院校
     */
    private String gradSchool;
    /**
     * 技能证书
     */
    private String skCert;
    /**
     * 身份证照片(包含手持身份证图片){"front_url":"","back_url":"","hand_url":""}
     */
    private String idcardImg;
    /**
     * 照片网址（128加密）,后期若有多个用英文逗号分割
     */
    private String photo;
    /**
     * 照片数据状态（0+正常，10+审核中，20+失败）
     */
    private Integer photoStatus;
    /**
     * 照片状态的描述文本（一般用于状态失败原因描述）
     */
    private Integer photoStatusDesc;
    /**
     * 是否拥有代付权限
     */
    private Short inAdvance;
    /**
     * 是否代付通知
     */
    private Short inAdvanceNotice;
    /**
     * 数据状态（0审核中，1正常，4拒绝）
     */
    private Integer status;
    /**
     * 租客端最后选择此身份的时间
     */
    private LocalDateTime lastChooseTime;

}