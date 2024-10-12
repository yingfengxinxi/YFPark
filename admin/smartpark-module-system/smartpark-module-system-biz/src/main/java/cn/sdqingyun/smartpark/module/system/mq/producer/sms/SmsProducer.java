package cn.sdqingyun.smartpark.module.system.mq.producer.sms;

import cn.sdqingyun.smartpark.framework.common.core.KeyValue;
import cn.sdqingyun.smartpark.module.system.mq.message.sms.SmsSendMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sms 短信相关消息的 Producer
 *
 * @author zzf
 * @since 2021/3/9 16:35
 */
@Slf4j
@Component
public class SmsProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送 {@link SmsSendMessage} 消息
     *
     * @param logId 短信日志编号
     * @param mobile 手机号
     * @param channelId 渠道编号
     * @param apiTemplateId 短信模板编号
     * @param templateParams 短信模板参数
     */
    public void sendSmsSendMessage(Long logId, String mobile,
                                   Long channelId, String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
        SmsSendMessage message = new SmsSendMessage().setLogId(logId).setMobile(mobile);
        message.setChannelId(channelId).setApiTemplateId(apiTemplateId).setTemplateParams(templateParams);
        rabbitTemplate.convertAndSend(SmsSendMessage.QUEUE, message);
    }

}
