package cn.sdqingyun.smartpark.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档地址
 *
 * @author 智慧园区
 */
@Getter
@AllArgsConstructor
public enum DocumentEnum {

    REDIS_INSTALL("https://wiki.sdqingyun.cn/", "Redis 安装文档"),
    TENANT("https://wiki.sdqingyun.cn/", "SaaS 多租户文档");
    
    private final String url;
    private final String memo;

}
