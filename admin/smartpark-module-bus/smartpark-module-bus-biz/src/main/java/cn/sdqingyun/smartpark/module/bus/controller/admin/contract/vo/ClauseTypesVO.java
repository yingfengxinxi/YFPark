package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/6/21 11:35
 */
@Data
public class ClauseTypesVO {

    @Schema(description = "条款类型(属性);1=租赁条款;2=物业条款", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "条款类型(属性);1=租赁条款;2=物业条款不能为空")
    private Integer clauseType;

    @Schema(description = "保证金条款json")
    private String bondClause;

    @Schema(description = "税率规则条款json")
    private String taxClause;

    @Schema(description = "租赁条款json")
    private String multipleClause;

    @Schema(description = "备注条款")
    private String remarkClause;

    @Schema(description = "租金明细报表json")
    private String reportDetail;
}
