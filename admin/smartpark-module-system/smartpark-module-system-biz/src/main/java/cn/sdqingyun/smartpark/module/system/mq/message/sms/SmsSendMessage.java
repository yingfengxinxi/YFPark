package cn.sdqingyun.smartpark.module.system.mq.message.sms;

import cn.sdqingyun.smartpark.framework.common.core.KeyValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 短信发送消息
 *
 * @author 智慧园区
 */
@Data
public class SmsSendMessage implements Serializable {// 重点：需要添加 Serializable 接口
    public static final String QUEUE = "SEND_MESSAGE_QUEUE"; // 重点：需要增加消息对应的 Queue
    /**
     * 短信日志编号
     */
    @NotNull(message = "短信日志编号不能为空")
    private Long logId;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String mobile;
    /**
     * 短信渠道编号
     */
    @NotNull(message = "短信渠道编号不能为空")
    private Long channelId;
    /**
     * 短信 API 的模板编号
     */
    @NotNull(message = "短信 API 的模板编号不能为空")
    private String apiTemplateId;
    /**
     * 短信模板参数
     */
    private List<KeyValue<String, Object>> templateParams;

}
