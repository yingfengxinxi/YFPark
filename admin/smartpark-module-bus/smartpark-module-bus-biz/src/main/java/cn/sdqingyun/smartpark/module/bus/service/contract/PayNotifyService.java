package cn.sdqingyun.smartpark.module.bus.service.contract;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.sdqingyun.smartpark.module.pay.api.notify.dto.PayRefundNotifyReqDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;

/**
 * @Author lvzy
 * @Date 2024/8/19 11:13
 */
public interface PayNotifyService {


    /**
     * @param notifyReqDTO
     * @return
     */
    public CommonResult<Boolean> notifyOrder(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) throws Exception;

    /**
     *
     * @param notifyReqDTO
     * @return
     * @throws Exception
     */
    public CommonResult<Boolean> notifyOrderWorkOrder(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) throws Exception;

    /**
     *
     * @param notifyReqDTO
     * @return
     */
    public CommonResult<Boolean> notifyRefundOrderWorkOrder(PayRefundNotifyReqDTO notifyReqDTO);


}
