package cn.sdqingyun.smartpark.module.bus.framework.security.config;

import cn.sdqingyun.smartpark.framework.security.config.AuthorizeRequestsCustomizer;
import cn.sdqingyun.smartpark.module.bus.enums.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * Crm 模块的 Security 配置
 */
@Configuration("busSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("busAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
                // Swagger 接口文档
                registry.requestMatchers("/v3/api-docs/**").permitAll() // 元数据
                        .requestMatchers("/swagger-ui.html").permitAll(); // Swagger UI
                // Spring Boot Actuator 的安全配置
                registry.requestMatchers("/actuator").permitAll()
                        .requestMatchers("/actuator/**").permitAll();
                // Druid 监控
                registry.requestMatchers("/druid/**").permitAll();
                // RPC 服务的安全配置
                registry.requestMatchers( ApiConstants.PREFIX + "/**").permitAll();
            }

        };
    }

}
