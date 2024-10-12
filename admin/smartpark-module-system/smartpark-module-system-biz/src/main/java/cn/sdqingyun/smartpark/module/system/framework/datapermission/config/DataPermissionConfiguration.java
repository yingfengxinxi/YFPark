package cn.sdqingyun.smartpark.module.system.framework.datapermission.config;

import cn.sdqingyun.smartpark.module.system.dal.dataobject.dept.DeptDO;
import cn.sdqingyun.smartpark.module.system.dal.dataobject.user.AdminUserDO;
import cn.sdqingyun.smartpark.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author 智慧园区
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(AdminUserDO.class);
            rule.addDeptColumn(DeptDO.class, "id");
            // user
            rule.addUserColumn(AdminUserDO.class, "id");
        };
    }

}
