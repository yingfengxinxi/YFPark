package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.NoticeListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillNoticeService;
import com.alibaba.nacos.common.utils.UuidUtils;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
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

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;
import static cn.sdqingyun.smartpark.framework.common.util.file.FileTypeUtils.writeAttachment;

@Tag(name = "管理后台 - 收款通知记录")
@RestController
@RequestMapping("/bus/orgBillNotice")
@Validated
public class OrgBillNoticeController {

    @Resource
    private OrgBillNoticeService billNoticeService;

    @PostMapping("/batchCreate")
    @Operation(summary = "批量创建收款通知记录")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNotice:create')")
    @Parameters({
            @Parameter(name = "ownerId", description = "租客id", required = true),
            @Parameter(name = "contractId", description = "合同id", required = true),
            @Parameter(name = "buildId", description = "楼宇id", required = true),
            @Parameter(name = "accountId", description = "收支账户id", required = true),
            @Parameter(name = "contractBillId", description = "账单明细id集合（多个使用英文逗号隔开）", required = true),
            @Parameter(name = "buildType", description = "生成方式;1=逐张生成;2=按租客合并;3=按合同合并;", required = true),
            @Parameter(name = "noticeType", description = "通知方式;1=下载打印;2=短信通知;3=邮件通知;4=站内信", required = true),
            @Parameter(name = "hasQrcode", description = "附收款二维码;1=是;0=否", required = true),
            @Parameter(name = "buildDate", description = "生成日期", required = true)
    })
    @TenantIgnore
    public CommonResult batchCreate(@Valid @RequestBody OrgBillNoticeSaveReqVO createReqVOs, HttpServletResponse response) {

        try {
            return CommonResult.success(billNoticeService.batchCreate(createReqVOs, response));
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "No valid entries or contents found, this is not a valid OOXML (Office Open XML) file";
            if (e.getMessage().equals(msg)) {
                throw new ServiceException(406,"当前楼宇缴费通知单模板异常,请检查模板再生成");
            } else {
                throw new ServiceException(406, e.getMessage());
            }

        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新收款通知记录")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNotice:update')")
    public CommonResult<Boolean> updateBillNotice(@Valid @RequestBody OrgBillNoticeSaveReqVO updateReqVO) {
        billNoticeService.updateBillNotice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收款通知记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillNotice:delete')")
    public CommonResult<Boolean> deleteBillNotice(@RequestParam("id") Long id) {
        billNoticeService.deleteBillNotice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收款通知记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNotice:query')")
    public CommonResult<OrgBillNoticeRespVO> getBillNotice(@RequestParam("id") Long id) {
        OrgBillNoticeDO billNotice = billNoticeService.getBillNotice(id);
        return success(BeanUtils.toBean(billNotice, OrgBillNoticeRespVO.class));
    }

    @GetMapping("/resend")
    @Operation(summary = "重新发送")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNotice:resend')")
    @TenantIgnore
    public CommonResult resend(@RequestParam("id") Long id, HttpServletResponse response) {
        try {
            OrgBillNoticeDO billNotice = billNoticeService.getBillNotice(id);
            OrgBillNoticeSaveReqVO createReqVOs = BeanUtils.toBean(billNotice, OrgBillNoticeSaveReqVO.class);
            return CommonResult.success(billNoticeService.batchCreate(createReqVOs, response));
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "No valid entries or contents found, this is not a valid OOXML (Office Open XML) file";
            if (e.getMessage().equals(msg)) {
                throw new ServiceException(406,"当前楼宇缴费通知单模板异常,请检查模板再生成");
            } else {
                throw new ServiceException(406, e.getMessage());
            }
        }
    }
//    @GetMapping("/getFileDownloadContent/{id}")
//    @PermitAll
//    @Operation(summary = "下载文件")
//    @Parameter(name = "id", description = "id", required = true)
//    public void getFileDownloadContent(HttpServletRequest request,
//                               HttpServletResponse response,
//                               @PathVariable("id") Long id) throws Exception {
//        OrgBillNoticeDO billNotice = billNoticeService.getBillNotice(id);
//        // 获取请求的路径
//        String path =billNotice.getFilePath();
//        if (StrUtil.isEmpty(path)) {
//            throw new IllegalArgumentException("结尾的 path 路径必须传递");
//        }
//        // 解码，解决中文路径的问题 https://gitee.com/zhijiantianya/ruoyi-vue-pro/pulls/807/
//        path = URLUtil.decode(path);
//
//        // 读取内容
//        byte[] content = fileService.getFileContent(configId, path);
//        if (content == null) {
////            log.warn("[getFileContent][configId({}) path({}) 文件不存在]", configId, path);
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            return;
//        }
//        writeAttachment(response, path, content);
//    }

    @GetMapping("/page")
    @Operation(summary = "获得收款通知记录分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillNotice:query')")
    @Parameters({
            @Parameter(name = "pageSize", description = "显示条数", required = true),
            @Parameter(name = "pageNo", description = "第几页", required = true),
            @Parameter(name = "ownerName", description = "租客名称", required = false),
            @Parameter(name = "buildIdList", description = "楼宇id集合", required = false),
            @Parameter(name = "smsStatus", description = "短信发送状态【1=失败2=成功】", required = false),
            @Parameter(name = "emailStatus", description = "邮件发送状态【1=失败2=成功】", required = false),
            @Parameter(name = "mailStatus", description = "站内信发送状态【1=失败2=成功】", required = false),
            @Parameter(name = "startCreateTime", description = "创建开始时间", required = false),
            @Parameter(name = "endCreateTime", description = "创建结束时间", required = false)
    })
    public CommonResult<PageResult<NoticeListVO>> getBillNoticePage(@Valid NoticeListVO noticeListVO) {

        return success(billNoticeService.getBillNoticePage(noticeListVO));
    }


    @GetMapping("/getTopNumCount")
    @Operation(summary = "获得收款通知记录顶部统计")
    @Parameters({
            @Parameter(name = "ownerName", description = "租客名称", required = false),
            @Parameter(name = "buildIdList", description = "楼宇id集合", required = false),
            @Parameter(name = "smsStatus", description = "短信发送状态【1=失败2=成功】", required = false),
            @Parameter(name = "emailStatus", description = "邮件发送状态【1=失败2=成功】", required = false),
            @Parameter(name = "mailStatus", description = "站内信发送状态【1=失败2=成功】", required = false),
            @Parameter(name = "startCreateTime", description = "创建开始时间", required = false),
            @Parameter(name = "endCreateTime", description = "创建结束时间", required = false)
    })
    public CommonResult<?> getTopNumCount(@Valid NoticeListVO noticeListVO) {
        noticeListVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return success(billNoticeService.getTopNumCount(noticeListVO));
    }
}