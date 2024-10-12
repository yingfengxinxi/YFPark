package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplateRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillNoticeTemplateService;
import cn.sdqingyun.smartpark.module.bus.service.village.BuildService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import io.swagger.v3.oas.annotations.Parameters;
import org.jetbrains.annotations.Nullable;
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

@Tag(name = "管理后台 - 账单缴费通知单模板")
@RestController
@RequestMapping("/bus/orgBillNoticeTemplate/")
@Validated
public class OrgBillNoticeTemplateController {

    @Resource
    private OrgBillNoticeTemplateService Service;

    @Resource
    private BuildService buildService;

    @PostMapping("/create")
    @Operation(summary = "创建账单缴费通知单模板")
    @Parameters({
            @Parameter(name = "name", description = "模板名称", required = true),
            @Parameter(name = "templatePath", description = "模板上传地址", required = true),
            @Parameter(name = "buildBind", description = "应用楼宇(多个楼宇用逗号隔开(1,2,3))", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:orgBillNoticeTemplate:create')")
    public CommonResult<Boolean> create(@Valid @RequestBody OrgBillNoticeTemplateSaveReqVO createReqVO) {
        getBooleanCommonResult(createReqVO);
        Service.create(createReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新账单缴费通知单模板")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNoticeTemplate:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillNoticeTemplateSaveReqVO updateReqVO) {
        getBooleanCommonResult(updateReqVO);
        Service.update(updateReqVO);
        return success(true);
    }


    private void getBooleanCommonResult(OrgBillNoticeTemplateSaveReqVO updateReqVO) {
        //楼宇配置规则后不允许再次配置
        String[] buildBinds = updateReqVO.getBuildBind().split(",");
        for (String buildBind : buildBinds) {
            Boolean checkBuild = Service.isCheckBuild(buildBind, updateReqVO.getId());
            if (checkBuild) {
                BuildRespVO buildRespVO = buildService.getBuild(Long.valueOf(buildBind));
                String buildName = "";
                if (buildRespVO != null) {
                    buildName = buildRespVO.getBuildName();
                }
                throw new ServiceException(406, "当前楼宇" + buildName + "已存在缴费通知单模板,请勿重复配置");
            }
        }
        //模板名称不允许重复
        Boolean checkName = Service.isCheckName(updateReqVO.getName(), updateReqVO.getId());
        if (checkName) {
            throw new ServiceException(406, "模板名称已存在，请勿重复操作");
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除账单缴费通知单模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillNoticeTemplate:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得账单缴费通知单模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNoticeTemplate:query')")
    public CommonResult<OrgBillNoticeTemplateRespVO> get(@RequestParam("id") Long id) {
        OrgBillNoticeTemplateDO billNoticeTemplateDO = Service.get(id);
        return success(BeanUtils.toBean(billNoticeTemplateDO, OrgBillNoticeTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得账单缴费通知单模板分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNoticeTemplate:query')")
    public CommonResult<PageResult<OrgBillNoticeTemplateRespVO>> getPage(@Valid OrgBillNoticeTemplatePageReqVO pageReqVO) {
        PageResult<OrgBillNoticeTemplateDO> pageResult = Service.getPage(pageReqVO);
        List<OrgBillNoticeTemplateDO> list = pageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(orgBillReceiptTemplateDO -> {
                String buildBind = orgBillReceiptTemplateDO.getBuildBind();
                if (StringUtils.isNotEmpty(buildBind)) {
                    StringBuilder sb = new StringBuilder();
                    for (String buildBindId : buildBind.split(",")) {
                        BuildRespVO buildRespVO = buildService.getBuild(Long.valueOf(buildBindId));
                        if (buildRespVO != null) {
                            sb.append(buildRespVO.getBuildName()).append(",");
                        }
                    }
                    String buildBindName = sb.toString();
                    if (StringUtils.isNotEmpty(buildBindName)) {
                        buildBindName = buildBindName.substring(0, buildBindName.length() - 1);
                        orgBillReceiptTemplateDO.setBuildBind(buildBindName);
                    }

                }
            });
        }
        return success(BeanUtils.toBean(pageResult, OrgBillNoticeTemplateRespVO.class));
    }
}