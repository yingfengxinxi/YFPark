package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostCategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostCategoryDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillCostCategoryService;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;

import java.util.*;
import java.io.IOException;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 账单费用分类")
@RestController
@RequestMapping("/bus/orgBillCostCategory")
@Validated
public class OrgBillCostCategoryController {

    @Resource
    private OrgBillCostCategoryService Service;

    /**
     * {
     * "name":"费用分类名称",
     * "isBond":"0"
     * <p>
     * }
     *
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建账单费用分类")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostCategory:create')")
    @Parameters({
            @Parameter(name = "name", description = "费用分类名称", required = true, example = "1024"),
            @Parameter(name = "isBond", description = "是否设置为保证金类型0=否1=是", required = true, example = "1")
    })
    public CommonResult<Long> create(@Valid @RequestBody OrgBillCostCategorySaveReqVO createReqVO) {

        Boolean checkName = Service.isCheckName(createReqVO.getName(), null);
        if (checkName) {
            throw new ServiceException(406, "费用分类名称已存在,请检查");
        }

        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新账单费用分类")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostCategory:update')")
    @Parameters({
            @Parameter(name = "id", description = "费用分类id", required = true, example = "1"),
            @Parameter(name = "name", description = "费用分类名称", required = true, example = "1024"),
            @Parameter(name = "isBond", description = "是否设置为保证金类型0=否1=是", required = true, example = "1")
    })
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillCostCategorySaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除账单费用分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostCategory:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    /**
     * /admin-api/bus/orgBillCostCategory/get?id=875
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @Operation(summary = "获得账单费用分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostCategory:query')")
    public CommonResult<OrgBillCostCategoryRespVO> get(@RequestParam("id") Long id) {
        OrgBillCostCategoryDO billCostCategoryDO = Service.get(id);
        return success(BeanUtils.toBean(billCostCategoryDO, OrgBillCostCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得账单费用分类列表")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostCategory:query')")
    @Parameter(name = "name", description = "分类名称", required = true, example = "1024")
    public CommonResult<List<OrgBillCostCategoryDO>> list(@RequestParam(value = "name", required = false) String name) {
        List<OrgBillCostCategoryDO> pageResult = Service.getList(name);
        return success(pageResult);
    }

}