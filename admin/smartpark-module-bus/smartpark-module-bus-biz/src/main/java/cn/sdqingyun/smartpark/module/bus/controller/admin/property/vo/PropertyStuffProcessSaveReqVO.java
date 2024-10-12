package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 耗材业务流程新增/修改 Request VO")
@Data
public class PropertyStuffProcessSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "流程单据编号")
    private String processNumber;

    @Schema(description = "业务流程类型", example = "2")
    private String processType;

    @Schema(description = "耗材物料id", example = "18478")
    private Long stuffId;

    @Schema(description = "所属分类id", example = "30565")
    private Long categoryId;

    @Schema(description = "所属仓库id/入库仓库/出库所属仓库id/处置仓库id/调整仓库id", example = "17783")
    private Long depositoryId;

    @Schema(description = "调入仓库id", example = "23099")
    private Long inDepositoryId;

    @Schema(description = "物料名称", example = "王五")
    private String name;

    @Schema(description = "物料编码")
    private String number;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格型号", example = "赵六")
    private String modelName;

    @Schema(description = "计量单位")
    private String meterUnit;

    @Schema(description = "入库单价/耗材定价", example = "453")
    private BigDecimal price;

    @Schema(description = "数量/可用库存、总库存/归还数量/调拨数量")
    private BigDecimal num;

    @Schema(description = "总价/入库合计金额/调拨合计金额/处置合计金额/归还总价/调拨总价", example = "31872")
    private BigDecimal totalPrice;

    @Schema(description = "物料照片;支持多张", example = "https://xxx")
    private String imageUrl;

    @Schema(description = "耗材物料信息状态", example = "2")
    private Integer status;

    @Schema(description = "调整前数量")
    private BigDecimal handoutNum;

    @Schema(description = "调整前单价")
    private BigDecimal retreatNum;

    @Schema(description = "其他/归还原因/入库备注/派发备注/调拨备注/处置原因/调整备注")
    private String extra;

    @Schema(description = "stockId 即时库存ID")
    private Long cuserUid;

    @Schema(description = "处理人uid")
    private Long muserUid;

    @Schema(description = "供应商")
    private String supplier;

    //派发退库字段
    @Schema(description = "领用人uid")
    private Long receiveUid;

    @Schema(description = "领用部门id/退库部门ID/处置发起部门id")
    private Long departmentId;

    @Schema(description = "领用部门名称/退库部门名称/处置发起部门id")
    private String departmentName;

    @Schema(description = "派发时间")
    private LocalDateTime handoutTime;

    //调拨
    @Schema(description = "调出管理员")
    private Long outAdminUid;

    @Schema(description = "调入管理员")
    private Long inAdminUid;

    @Schema(description = "调出仓库")
    private Long outDepositoryId;

    @Schema(description = "合计数量/调拨合计数量/处置合计")
    private BigDecimal totalNumAj;
    @Schema(description = "合并金额/处置合计")
    private BigDecimal totalPriceNum;
    @Schema(description = "调整时间")
    private LocalDateTime adjustTime;
    //处置
    @Schema(description = "发起时间")
    private LocalDateTime launchTime;

    //退库
    @Schema(description = "退库人uid")
    private Long retreatUid;
    @Schema(description = "退库时间")
    private LocalDateTime retreatDate;
    //入库
    @Schema(description = "入库处理人uid")
    private Long enterUid;
    @Schema(description = "入库时间")
    private LocalDateTime enterTime;
}