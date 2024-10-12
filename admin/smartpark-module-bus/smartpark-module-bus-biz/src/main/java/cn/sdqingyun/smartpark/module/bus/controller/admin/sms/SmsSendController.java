package cn.sdqingyun.smartpark.module.bus.controller.admin.sms;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.module.system.api.sms.SmsSendApi;
import cn.sdqingyun.smartpark.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/6/25 14:29
 */
@RequestMapping("bus/smsSend")
@RestController
public class SmsSendController {

    @Resource
    private SmsSendApi smsSendApi;

    @GetMapping("smsSend")
    public CommonResult smsSend() {
        //站内消息

        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("name", "张三");
        try {
            SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO=new SmsSendSingleToUserReqDTO();
            smsSendSingleToUserReqDTO.setMobile("18606341938");
            smsSendSingleToUserReqDTO.setUserId(1L);
            smsSendSingleToUserReqDTO.setTemplateCode("SMS_467510404");
            smsSendSingleToUserReqDTO.setTemplateParams(templateParams);
            smsSendApi.sendSingleSmsToOwner(smsSendSingleToUserReqDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return CommonResult.success(null);
    }
}
