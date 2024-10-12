package cn.sdqingyun.smartpark.module.bus.api.bpmContract;

import cn.sdqingyun.smartpark.module.bus.service.bpm.ContractLeaveService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * BUS 流程实例 Api 实现类
 *
 * @author 智慧园区
 * @author jason
 */
@RestController
@Validated
public class BpmContractApiImpl implements BpmContractApi {

    @Resource
    private ContractLeaveService contractLeaveService;

    @Override
    public void updateContractAuditStatus(Long userId,Integer status) throws ParseException {
        contractLeaveService.updateContractAuditStatus(userId, status);
    }

}
