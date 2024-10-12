package cn.sdqingyun.smartpark.module.system.mq.consumer.sms;

import cn.sdqingyun.smartpark.module.system.mq.message.sms.SmsSendMessage;
import cn.sdqingyun.smartpark.module.system.service.sms.SmsSendService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 针对 {@link SmsSendMessage} 的消费者
 *
 * @author zzf
 */
//@Component
//@RabbitListener(queues = SmsSendMessage.QUEUE)// 重点：添加 @RabbitListener 注解，声明消费的 queue
@Slf4j
public class SmsSendConsumer {

    @Resource
    private SmsSendService smsSendService;


    @RabbitHandler
    public void onMessage(SmsSendMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        smsSendService.doSendSms(message);
    }

}
