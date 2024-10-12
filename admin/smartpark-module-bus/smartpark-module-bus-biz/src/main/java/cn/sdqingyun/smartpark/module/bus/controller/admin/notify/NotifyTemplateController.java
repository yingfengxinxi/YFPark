package cn.sdqingyun.smartpark.module.bus.controller.admin.notify;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 站内信模版")
@RestController
@RequestMapping("/bus/notify-template")
@Validated
public class NotifyTemplateController {

    @Resource
    private NotifyMessageSendApi notifySendService;


    @GetMapping("/send-notify")
    @Operation(summary = "发送站内信")
    @PreAuthorize("@ss.hasPermission('system:notify-template:send-notify')")
    public CommonResult<Long> sendNotify() {

        Map<String, Object> map = new HashMap<>();
        map.put("contractNumber", "202402030123");
        map.put("day", "80");
        map.put("link", "链接");

        NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO=new NotifySendSingleToUserReqDTO();
        notifySendSingleToUserReqDTO.setTemplateCode("SMS_468715011");
        notifySendSingleToUserReqDTO.setUserId(1L);
        notifySendSingleToUserReqDTO.setTemplateParams(map);
        notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
        return success(null);
    }
}
