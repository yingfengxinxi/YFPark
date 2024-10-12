package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 房间 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class OwnImportExcelVO {

    @Schema(description = "租客类型（公司/个人)", example = "公司")
    @ExcelProperty("租客类型公司/个人)")
    @ContentFontStyle(color = 10)
    private String isPersonal;

    @Schema(description = "租客名称", example = "中国信息科技有限公司")
    @ExcelProperty("租客名称")
    @ContentFontStyle(color = 10)
    private String name;

    @Schema(description = "租客联系人", example = "张三")
    @ExcelProperty("租客联系人")
    @ContentFontStyle(color = 10)
    private String contactName;

    @Schema(description = "员工身份（超级管理员、管理员、普通员工）", example = "张三")
    @ExcelProperty("员工身份（超级管理员、管理员、普通员工）")
    @ContentFontStyle(color = 10)
    private String VillageType;

    @Schema(description = "租客电话", example = "13111111111")
    @ExcelProperty("租客电话")
    @ContentFontStyle(color = 10)
    private String tel;

    @Schema(description = "证件类型", example = "大陆身份证")
    @ExcelProperty("证件类型")
    @ContentFontStyle(color = 10)
    private String idcardType;

    @Schema(description = "证件号码")
    @ExcelProperty("证件号码")
    @ContentFontStyle(color = 10)
    private String certificateNumber;

    @Schema(description = "租客编码")
    @ExcelProperty("租客编码")
    @ContentFontStyle(color = 10)
    private String tenantNo;

    @Schema(description = "联系人通讯地址")
    @ExcelProperty("联系人通讯地址")
    @ContentFontStyle(color = 10)
    private String address;

    @Schema(description = "导入结果说明")
    @ExcelProperty("导入结果说明")
    @ContentFontStyle(color = 10)
    private String importResult;

}
