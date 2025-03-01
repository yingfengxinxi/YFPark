package cn.sdqingyun.smartpark.module.system.api.logger;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.system.api.logger.dto.OperateLogCreateReqDTO;
import cn.sdqingyun.smartpark.module.system.api.logger.dto.OperateLogPageReqDTO;
import cn.sdqingyun.smartpark.module.system.api.logger.dto.OperateLogRespDTO;
import cn.sdqingyun.smartpark.module.system.enums.ApiConstants;
import feign.QueryMap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 操作日志")
public interface OperateLogApi {

    String PREFIX = ApiConstants.PREFIX + "/operate-log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建操作日志")
    CommonResult<Boolean> createOperateLog(@Valid @RequestBody OperateLogCreateReqDTO createReqDTO);

    @PostMapping(PREFIX + "/page")
    @Operation(summary = "获取指定模块的指定数据的操作日志分页")
    CommonResult<PageResult<OperateLogRespDTO>> getOperateLogPage(@QueryMap OperateLogPageReqDTO pageReqVO);

}
