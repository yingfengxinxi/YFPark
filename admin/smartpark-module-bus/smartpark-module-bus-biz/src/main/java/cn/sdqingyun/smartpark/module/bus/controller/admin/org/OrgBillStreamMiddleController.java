package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddlePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillStreamMiddleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.StreamIdMatchingListPageVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillStreamMiddleDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillStreamMiddleService;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import io.swagger.v3.oas.annotations.Parameters;
import org.apache.commons.compress.utils.Lists;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.io.IOException;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 机构流水账单中间表【匹配】")
@RestController
@RequestMapping("/bus/orgBillStreamMiddle")
@Validated
public class OrgBillStreamMiddleController {

    @Resource
    private OrgBillStreamMiddleService billStreamMiddleService;


    @PutMapping("/update")
    @Operation(summary = "更新机构流水账单中间表【匹配】")
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:update')")
    public CommonResult<Boolean> updateBillStreamMiddle(@Valid @RequestBody OrgBillStreamMiddleSaveReqVO updateReqVO) {
        billStreamMiddleService.updateBillStreamMiddle(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构流水账单中间表【匹配】")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:delete')")
    public CommonResult<Boolean> deleteBillStreamMiddle(@RequestParam("id") Long id) {
        billStreamMiddleService.deleteBillStreamMiddle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构流水账单中间表【匹配】")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:query')")
    public CommonResult<OrgBillStreamMiddleRespVO> getBillStreamMiddle(@RequestParam("id") Long id) {
        OrgBillStreamMiddleDO billStreamMiddle = billStreamMiddleService.getBillStreamMiddle(id);
        return success(BeanUtils.toBean(billStreamMiddle, OrgBillStreamMiddleRespVO.class));
    }


    /**
     * @param pageReqVO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "收支流水详情匹配列表【获得已经匹配或者取消匹配的账单分页】")
    @Parameters({
            @Parameter(name = "streamId", description = "流水id", required = true),
            @Parameter(name = "matchStatus", description = "显示取消匹配账单默认传值1(不显示),传空值是查询全部", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:query')")
    public CommonResult<PageResult<StreamIdMatchingListPageVO>> getBillStreamMiddlePage(@Valid OrgBillStreamMiddlePageReqVO pageReqVO) {
        PageResult<StreamIdMatchingListPageVO> pageResult = billStreamMiddleService.getBillStreamMiddlePage(pageReqVO);
        return success(pageResult);
    }


    /**
     * /bus/orgBillStreamMiddle/getBillStreamListPage?pageSize=10&pageNo=1&billId=21&matchStatus=
     *
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getBillStreamListPage")
    @Operation(summary = "财务管理->账单详情->流水信息已经匹配账单分页")
    @Parameters({
            @Parameter(name = "billId", description = "账单id", required = true),
            @Parameter(name = "matchStatus", description = "显示取消匹配账单默认传值1(不显示),传空值是查询全部", required = true)
    })
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:query')")
    public CommonResult<PageResult<OrgBillStreamMiddleRespVO>> getBillStreamListPage(@Valid OrgBillStreamMiddlePageReqVO pageReqVO) {
        PageResult<OrgBillStreamMiddleDO> pageResult = billStreamMiddleService.getBillStreamListPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillStreamMiddleRespVO.class));
    }


    /**
     * @param billId
     * @param streamIds
     * @param matchPrice
     * @param matchDate
     * @return
     */
    @GetMapping("/matchBill")
    @Operation(summary = "收支流水详情->匹配->账单列表->匹配确认按钮")
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:addMiddle')")
    @Parameters({
            @Parameter(name = "billIds", description = "选中的账单id集合", required = true),
            @Parameter(name = "streamId", description = "流水id", required = true),
            @Parameter(name = "matchPrice", description = "匹配金额", required = true),
            @Parameter(name = "matchDate", description = "匹配日期", required = true)
    })
    public CommonResult<?> matchBill(
            @RequestParam("billIds") List<Long> billIds,
            @RequestParam("streamId") Long streamId,
            @RequestParam("matchPrice") BigDecimal matchPrice,
            @RequestParam("matchDate") String matchDate
    ) {

        try {
            billStreamMiddleService.addMiddle(billIds, streamId, matchPrice, matchDate);
        } catch (Exception e) {
            e.getMessage();
            throw new ServiceException(406, e.getMessage());
        }
        return success(true);
    }

    /**
     * @param billId
     * @param streamIds
     * @param matchPrice
     * @param matchDate
     * @return
     */
    @GetMapping("/addCollectionMiddle")
    @Operation(summary = "租客账单详情->匹配->账单列表->匹配确认按钮")
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:addMiddle')")
    @Parameters({
            @Parameter(name = "billId", description = "选中的账单id【id值从上个页面获取】", required = true),
            @Parameter(name = "streamIds", description = "流水id【收支流水列表Id集合】", required = true),
            @Parameter(name = "matchPrice", description = "匹配金额", required = true),
            @Parameter(name = "matchDate", description = "匹配日期", required = true)
    })
    public CommonResult<?> addCollectionMiddle(
            @RequestParam("billId") Long billId,
            @RequestParam("streamIds") List<Long> streamIds,
            @RequestParam("matchPrice") BigDecimal matchPrice,
            @RequestParam("matchDate") String matchDate
    ) {

        try {
            billStreamMiddleService.addCollectionMiddle(billId, streamIds, matchPrice, matchDate);
        } catch (Exception e) {
            e.getMessage();
            throw new ServiceException(406, e.getMessage());
        }
        return success(true);
    }

    /**
     * @param id
     * @param cancelMatchDate
     * @return
     */
    @GetMapping("/cancelMatch")
    @Operation(summary = "取消匹配确认按钮")
    @PreAuthorize("@ss.hasPermission('bus:orgBillStreamMiddle:addMiddle')")
    @Parameters({
            @Parameter(name = "id", description = "列表id", required = true),
            @Parameter(name = "cancelMatchDate", description = "取消匹配日期", required = true)
    })
    public CommonResult<?> cancelMatch(
            @RequestParam("id") Long id,
            @RequestParam("cancelMatchDate") String cancelMatchDate
    ) {

        try {
            billStreamMiddleService.cancelMatch(id, cancelMatchDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return success(null);
    }
}