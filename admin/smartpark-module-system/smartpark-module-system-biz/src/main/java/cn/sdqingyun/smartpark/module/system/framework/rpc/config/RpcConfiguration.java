package cn.sdqingyun.smartpark.module.system.framework.rpc.config;

import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import cn.sdqingyun.smartpark.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class})
public class RpcConfiguration {
}
