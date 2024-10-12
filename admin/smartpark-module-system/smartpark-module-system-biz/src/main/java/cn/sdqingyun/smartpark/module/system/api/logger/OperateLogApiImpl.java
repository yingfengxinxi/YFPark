package cn.sdqingyun.smartpark.module.system.api.logger;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.module.system.api.logger.dto.OperateLogCreateReqDTO;
import cn.sdqingyun.smartpark.module.system.api.logger.dto.OperateLogPageReqDTO;
import cn.sdqingyun.smartpark.module.system.api.logger.dto.OperateLogRespDTO;
import cn.sdqingyun.smartpark.module.system.dal.dataobject.logger.OperateLogDO;
import cn.sdqingyun.smartpark.module.system.service.logger.OperateLogService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public CommonResult<Boolean> createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
        return success(true);
    }

    @Override
    public CommonResult<PageResult<OperateLogRespDTO>> getOperateLogPage(OperateLogPageReqDTO pageReqVO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqVO);
        return success(BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class));
    }

}
