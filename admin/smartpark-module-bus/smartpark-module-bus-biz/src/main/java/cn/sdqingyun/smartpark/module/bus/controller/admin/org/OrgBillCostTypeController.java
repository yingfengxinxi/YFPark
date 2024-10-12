package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.CostTypeChildrenVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostTypePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostTypeRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostTypeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
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

import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillCostTypeService;

@Tag(name = "管理后台 - 账单费用类型")
@RestController
@RequestMapping("/bus/orgBillCostType")
@Validated
public class OrgBillCostTypeController {

    @Resource
    private OrgBillCostTypeService Service;

    /**
     * {
     * "name": "费用类型名称",
     * "categoryId": 875,
     * "isImportant": 1,
     * "sort": 0,
     * "status": "1"
     * }
     *
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建账单费用类型")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostType:create')")
    @Parameters({
            @Parameter(name = "name", description = "名称", required = true, example = "1024"),
            @Parameter(name = "categoryId", description = "费用分类id", required = true, example = "1"),
            @Parameter(name = "isImportant", description = "重要项目0=否1=是", required = true, example = "1"),
            @Parameter(name = "sort", description = "排序", required = true, example = "1"),
            @Parameter(name = "status", description = "状态0=停用1=启用", required = true, example = "1")
    })
    public CommonResult<Long> create(@Valid @RequestBody OrgBillCostTypeSaveReqVO createReqVO) {
        Boolean chackName = Service.isChackName(createReqVO.getName(), createReqVO.getCategoryId(), null);
        if (chackName) {
            throw new ServiceException(406, "当前类型下已存在费用类型名称");
        }
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新账单费用类型")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostType:update')")
    @Parameters({
            @Parameter(name = "name", description = "名称", required = true, example = "1024"),
            @Parameter(name = "categoryId", description = "费用分类id", required = true, example = "1"),
            @Parameter(name = "isImportant", description = "重要项目0=否1=是", required = true, example = "1"),
            @Parameter(name = "sort", description = "排序", required = true, example = "1"),
            @Parameter(name = "status", description = "状态0=停用1=启用", required = true, example = "1"),
            @Parameter(name = "id", description = "列表id", required = true, example = "1")
    })
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillCostTypeSaveReqVO updateReqVO) {
        Boolean chackName = Service.isChackName(updateReqVO.getName(), updateReqVO.getCategoryId(), updateReqVO.getId());
        if (chackName) {
            throw new ServiceException(406, "当前类型下已存在费用类型名称");
        }
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除账单费用类型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostType:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得账单费用类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostType:query')")
    public CommonResult<OrgBillCostTypeRespVO> get(@RequestParam("id") Long id) {
        OrgBillCostTypeDO orgBillCostTypeDO = Service.get(id);
        return success(BeanUtils.toBean(orgBillCostTypeDO, OrgBillCostTypeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得账单费用类型分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillCostType:query')")
    @Parameters({
            @Parameter(name = "name", description = "名称", required = false, example = "1024"),
            @Parameter(name = "categoryId", description = "费用分类id【查询所有传空值】", required = false, example = "1"),
            @Parameter(name = "pageSize", description = "显示条数", required = true, example = "1"),
            @Parameter(name = "pageNo", description = "页数", required = true, example = "1")
    })
    public CommonResult<PageResult<OrgBillCostTypeRespVO>> getPage(@Valid OrgBillCostTypePageReqVO pageReqVO) {
        PageResult<OrgBillCostTypeDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillCostTypeRespVO.class));
    }

    /**
     * @return
     */
    @GetMapping("/getCostTypeChildrenList")
    @Operation(summary = "费用类型下拉")
    public CommonResult<List<CostTypeChildrenVO>> getCostTypeChildrenList() {

        return success(Service.getCostTypeChildrenList());
    }


    /**
     * @return
     */
    @GetMapping("/getBondList")
    @Operation(summary = "保证金类型下拉")
    public CommonResult<List<OrgBillCostTypeDO>> getBondList() {

        return success(Service.getBondList());
    }
}