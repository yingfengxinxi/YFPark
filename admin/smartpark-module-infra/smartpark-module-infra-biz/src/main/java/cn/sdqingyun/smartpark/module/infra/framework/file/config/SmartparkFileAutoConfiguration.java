package cn.sdqingyun.smartpark.module.infra.framework.file.config;

import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.FileClientFactory;
import cn.sdqingyun.smartpark.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 智慧园区
 */
@Configuration(proxyBeanMethods = false)
public class SmartparkFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
