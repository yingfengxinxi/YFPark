package cn.sdqingyun.smartpark.module.infra.dal.dataobject.file;

import cn.hutool.core.util.StrUtil;
import cn.sdqingyun.smartpark.framework.common.util.json.JsonUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.FileClientConfig;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.db.DBFileClientConfig;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.ftp.FtpFileClientConfig;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.local.LocalFileClientConfig;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.s3.S3FileClientConfig;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.sftp.SftpFileClientConfig;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.enums.FileStorageEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.*;

/**
 * 文件配置表
 *
 * @author 智慧园区
 */
@TableName(value = "infra_file_config", autoResultMap = true)
@KeySequence("infra_file_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileConfigDO extends BaseDO {

    /**
     * 配置编号，数据库自增
     */
    private Long id;
    /**
     * 配置名
     */
    private String name;
    /**
     * 存储器
     *
     * 枚举 {@link FileStorageEnum}
     */
    private Integer storage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否为主配置
     *
     * 由于我们可以配置多个文件配置，默认情况下，使用主配置进行文件的上传
     */
    private Boolean master;

    /**
     * 支付渠道配置
     */
    @TableField(typeHandler = FileClientConfigTypeHandler.class)
    private FileClientConfig config;

    public static class FileClientConfigTypeHandler extends AbstractJsonTypeHandler<Object> {

        @Override
        protected Object parse(String json) {
            FileClientConfig config = JsonUtils.parseObjectQuietly(json, new TypeReference<>() {});
            if (config != null) {
                return config;
            }

            // 兼容老版本的包路径
            String className = JsonUtils.parseObject(json, "@class", String.class);
            className = StrUtil.subAfter(className, ".", true);
            switch (className) {
                case "DBFileClientConfig":
                    return JsonUtils.parseObject2(json, DBFileClientConfig.class);
                case "FtpFileClientConfig":
                    return JsonUtils.parseObject2(json, FtpFileClientConfig.class);
                case "LocalFileClientConfig":
                    return JsonUtils.parseObject2(json, LocalFileClientConfig.class);
                case "SftpFileClientConfig":
                    return JsonUtils.parseObject2(json, SftpFileClientConfig.class);
                case "S3FileClientConfig":
                    return JsonUtils.parseObject2(json, S3FileClientConfig.class);
                default:
                    throw new IllegalArgumentException("未知的 FileClientConfig 类型：" + json);
            }
        }

        @Override
        protected String toJson(Object obj) {
            return JsonUtils.toJsonString(obj);
        }

    }

}
