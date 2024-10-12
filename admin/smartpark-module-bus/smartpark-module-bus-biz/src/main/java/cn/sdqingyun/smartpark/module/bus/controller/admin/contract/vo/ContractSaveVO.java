package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构合同新增/修改 Request VO")
@Data
public class ContractSaveVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32481")
    private Long id;


    @Schema(description = "合同编号")
    @NotNull(message = "合同编号不能为空")
    private String contractNumber;

    @Schema(description = "经办人id", example = "32679")
    private Long operatorId;

    @Schema(description = "签订日")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date signingDate;

    @Schema(description = "合同起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date contractStartTime;

    @Schema(description = "合同结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date contractEndTime;

    @Schema(description = "单价保留小数点几位")
    private Integer unitPricePoint;

    @Schema(description = "计算顺序0=单价*面积*时间1=单价*时间*面积")
    private String calculationOrder;

    @Schema(description = "应收整数是否四舍五入0=否1=是")
    private String isReceivableInteger;

    @Schema(description = "租客id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27502")
    @NotNull(message = "租客id不能为空")
    private Long ownerId;

    @Schema(description = "行业id")
    private String industry;

    @Schema(description = "法人姓名")
    private String legalPerson;

    @Schema(description = "签订人姓名", example = "张三")
    private String signedName;

    @Schema(description = "滞纳金起算天数")
    private Integer startingLateFeeDay;

    @Schema(description = "滞纳金比例%")
    private String lateFeeRatio;

    @Schema(description = "滞纳金上限%")
    private String upperLimitLateFee;

    @Schema(description = "机构ID", example = "29323")
    private Long orgId;

    @Schema(description = "楼宇id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7452")
    @NotNull(message = "楼宇id不能为空")
    private Long buildId;

    @Schema(description = "房号id,多个用英文逗号拼接;例:101,103")
    private String roomNumber;

    @Schema(description = "已选中楼宇资源")
    private String checkedBuild;

    @Schema(description = "园区id")
    private Long parkId;

    @Schema(description = "生成的pdf合同路径")
    private String pdfFileUrl;

    @Schema(description = "租赁条款和物业条款集合JSON")
    private String clauseTypes;

    @Schema(description = "附件集合")
    private String contractAnnex;

    @Schema(description = "租赁总面积")
    private String leaseArea;

    /**
     * 合同来源0=新建1=导入
     */
    @Schema(description = "合同来源0=新建1=导入")
    private String dataSource;
    /**
     * 是否续租0=否1=是
     */
    @Schema(description = "是否续租0=否1=是")
    private String isWhetherLease;

    /**
     * 合同状态
     */
    @Schema(description = "合同状态")
    private String status;
}