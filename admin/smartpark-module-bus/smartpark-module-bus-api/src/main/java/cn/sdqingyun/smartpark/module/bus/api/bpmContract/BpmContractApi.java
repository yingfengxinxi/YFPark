package cn.sdqingyun.smartpark.module.bus.api.bpmContract;

import cn.sdqingyun.smartpark.module.bus.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - BUS流程实例")
public interface BpmContractApi {

    String PREFIX = ApiConstants.PREFIX + "/contract-leave";

    @PostMapping(PREFIX + "/updateContractAuditStatus")
    @Operation(summary = "更新合同审批审批状态")
    @Parameter(name = "id", description = "合同ID", required = true, example = "1")
    void updateContractAuditStatus(@RequestParam("id") Long id,
                                                   @RequestParam("status") Integer status) throws ParseException;

}
