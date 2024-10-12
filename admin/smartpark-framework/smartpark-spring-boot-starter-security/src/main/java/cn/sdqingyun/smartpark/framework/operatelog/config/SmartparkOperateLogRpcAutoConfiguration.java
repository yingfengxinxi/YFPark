package cn.sdqingyun.smartpark.framework.operatelog.config;

import cn.sdqingyun.smartpark.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * OperateLog 使用到 Feign 的配置项
 *
 * @author 智慧园区
 */
@AutoConfiguration
@EnableFeignClients(clients = {OperateLogApi.class}) // 主要是引入相关的 API 服务
public class SmartparkOperateLogRpcAutoConfiguration {
}
