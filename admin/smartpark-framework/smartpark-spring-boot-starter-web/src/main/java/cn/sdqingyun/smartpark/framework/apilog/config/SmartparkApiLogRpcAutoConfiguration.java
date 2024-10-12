package cn.sdqingyun.smartpark.framework.apilog.config;

import cn.sdqingyun.smartpark.module.infra.api.logger.ApiAccessLogApi;
import cn.sdqingyun.smartpark.module.infra.api.logger.ApiErrorLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * API 日志使用到 Feign 的配置项
 *
 * @author 智慧园区
 */
@AutoConfiguration
@EnableFeignClients(clients = {ApiAccessLogApi.class, // 主要是引入相关的 API 服务
        ApiErrorLogApi.class})
public class SmartparkApiLogRpcAutoConfiguration {
}
