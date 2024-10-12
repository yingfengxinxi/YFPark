package cn.sdqingyun.smartpark.module.bus.framework.rpc.config;

import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import cn.sdqingyun.smartpark.module.pay.api.client.ClientApi;
import cn.sdqingyun.smartpark.module.pay.api.order.PayOrderApi;
import cn.sdqingyun.smartpark.module.pay.api.payapp.PayAppApi;
import cn.sdqingyun.smartpark.module.pay.api.refund.PayRefundApi;
import cn.sdqingyun.smartpark.module.system.api.dept.DeptApi;
import cn.sdqingyun.smartpark.module.system.api.dept.PostApi;
import cn.sdqingyun.smartpark.module.system.api.dict.DictDataApi;
import cn.sdqingyun.smartpark.module.system.api.mail.MailSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.permission.PermissionApi;
import cn.sdqingyun.smartpark.module.system.api.permission.RoleApi;
import cn.sdqingyun.smartpark.module.system.api.sms.SmsSendApi;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {
        RoleApi.class,
        DeptApi.class,
        PostApi.class,
        AdminUserApi.class,
        SmsSendApi.class,
        DictDataApi.class,
        NotifyMessageSendApi.class,
        FileApi.class,
        SmsSendApi.class,
        MailSendApi.class,
        PayOrderApi.class,
        ClientApi.class,
        PayAppApi.class,
        PayRefundApi.class,
        PermissionApi.class
})
public class RpcConfiguration {
}
