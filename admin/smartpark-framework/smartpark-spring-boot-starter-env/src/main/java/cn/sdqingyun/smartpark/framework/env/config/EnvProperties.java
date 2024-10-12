package cn.sdqingyun.smartpark.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author 智慧园区
 */
@ConfigurationProperties(prefix = "smartpark.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "smartpark.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
