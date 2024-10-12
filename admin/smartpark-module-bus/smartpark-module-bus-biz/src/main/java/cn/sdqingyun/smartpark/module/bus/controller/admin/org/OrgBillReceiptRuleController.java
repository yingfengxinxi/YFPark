package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRuleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptRuleDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillReceiptRuleService;
import cn.sdqingyun.smartpark.module.bus.service.village.BuildService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
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

@Tag(name = "管理后台 - 收据设置->收据编号")
@RestController
@RequestMapping("/bus/orgBillReceiptRule")
@Validated
public class OrgBillReceiptRuleController {

    @Resource
    private OrgBillReceiptRuleService Service;

    @Resource
    private BuildService buildService;


    @PostMapping("/create")
    @Operation(summary = "创建账单收据编号规则")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptRule:create')")
    public CommonResult<?> create(@Valid @RequestBody OrgBillReceiptRuleSaveReqVO createReqVO) {
        getLongCommonResult(createReqVO);
        return success(Service.create(createReqVO));
    }

    private void getLongCommonResult(OrgBillReceiptRuleSaveReqVO createReqVO) {
        //楼宇配置规则后不允许再次配置
        String[] buildBinds = createReqVO.getBuildBind().split(",");
        for (String buildBind : buildBinds) {
            Boolean checkBuild = Service.isCheckBuild(buildBind, createReqVO.getId());
            if (checkBuild) {
                BuildRespVO buildRespVO = buildService.getBuild(Long.valueOf(buildBind));
                throw new ServiceException(406, "当前楼宇" + buildRespVO.getBuildName() + "已存在配置规则,请勿重复配置");
            }
        }
        //收款编号名称不允许重复
        Boolean checkName = Service.isCheckName(createReqVO.getName(), createReqVO.getId());
        if (checkName) {
            throw new ServiceException(406, "收款编号名称已存在，请勿重复操作");
        }
        //起始编号位数需和结束编号位数要相同
        int startNumberLength = createReqVO.getStartNumber().length();
        int endNumberLength = createReqVO.getEndNumber().length();
        if (startNumberLength != endNumberLength) {
            throw new ServiceException(406, "起始编号位数需和结束编号位数不相同");
        }

        //结束编号数需大于起始编号数
        String startNumber = createReqVO.getStartNumber();
        String endNumber = createReqVO.getEndNumber();
        if (Integer.valueOf(endNumber) <= Integer.valueOf(startNumber)) {
            throw new ServiceException(406, "结束编号数需大于起始编号数");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新账单收据编号规则")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptRule:update')")
    public CommonResult<?> update(@Valid @RequestBody OrgBillReceiptRuleSaveReqVO updateReqVO) {
        getLongCommonResult(updateReqVO);

        Service.update(updateReqVO);
        return success(updateReqVO.getId());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除账单收据编号规则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptRule:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得账单收据编号规则")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptRule:query')")
    public CommonResult<OrgBillReceiptRuleRespVO> get(@RequestParam("id") Long id) {
        OrgBillReceiptRuleDO billReceiptRuleDO = Service.get(id);
        return success(BeanUtils.toBean(billReceiptRuleDO, OrgBillReceiptRuleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得账单收据编号规则分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptRule:query')")
    public CommonResult<PageResult<OrgBillReceiptRuleRespVO>> getPage(@Valid OrgBillReceiptRulePageReqVO pageReqVO) {
        PageResult<OrgBillReceiptRuleDO> pageResult = Service.getPage(pageReqVO);
        List<OrgBillReceiptRuleDO> list = pageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(orgBillReceiptRuleDO -> {
                String[] split = orgBillReceiptRuleDO.getBuildBind().split(",");
                StringBuilder sb = new StringBuilder();
                for (String buildBind : split) {
                    BuildRespVO buildRespVO = buildService.getBuild(Long.valueOf(buildBind));
                    if (buildRespVO != null) {
                        sb.append(buildRespVO.getBuildName()).append(",");
                    }

                }
                String buildName = sb.toString();
                if (StringUtils.isNotEmpty(buildName)) {
                    buildName = buildName.substring(0, buildName.length() - 1);
                    orgBillReceiptRuleDO.setBuildBind(buildName);
                }
            });
        }
        return success(BeanUtils.toBean(pageResult, OrgBillReceiptRuleRespVO.class));
    }


}