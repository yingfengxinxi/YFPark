package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeConfigRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgIncomeConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeConfigDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgIncomeConfigService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 机构收支确认配置")
@RestController
@RequestMapping("/bus/orgIncomeConfig")
@Validated
public class OrgIncomeConfigController {

    @Resource
    private OrgIncomeConfigService Service;

    /**
     * {
     * 	"lmtDay": "2"
     * }
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建机构收支确认配置")
    @PreAuthorize("@ss.hasPermission('bus:orgIncomeConfig:create')")
    @Parameter(name = "lmtDay", description = "确认天数,超过此天数不可取消确认不能为空", required = true, example = "2")
    public CommonResult<Long> create(@Valid @RequestBody OrgIncomeConfigSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    /**
     *
     * @return
     */
    @GetMapping("/getOneInfo")
    @Operation(summary = "获得机构收支确认配置分页")
    @PreAuthorize("@ss.hasPermission('bus:orgIncomeConfig:query')")
    public CommonResult<OrgIncomeConfigDO> getOneInfo() {
        return success(Service.getOneInfo());
    }

}