package cn.sdqingyun.smartpark.module.bus.api.property;

import cn.sdqingyun.smartpark.module.bus.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - BUS服务property流程实例")
public interface PropertyApi {

    String PREFIX = ApiConstants.PREFIX;

    @PostMapping(PREFIX + "/property/updatePropertyStatus")
    @Operation(summary = "更新审批清单审批状态")
    @Parameter(name = "id", description = "流程ID", required = true, example = "1")
    void updatePropertyStatus(@RequestParam("id") String id,
                                                   @RequestParam("status") Integer status);

    @PostMapping(PREFIX + "/property/updatePropertyStuffStatus")
    @Operation(summary = "耗材-更新审批清单审批状态")
    @Parameter(name = "id", description = "流程ID", required = true, example = "1")
    void updatePropertyStuffStatus(@RequestParam("id") String id,
                              @RequestParam("status") Integer status);
}
