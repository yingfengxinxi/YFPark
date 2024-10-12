package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
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
public class VillageUserImportExcelVO {
    @Schema(description = "租客名称", example = "中国信息科技有限公司")
    @ExcelProperty("租客名称(必填）")
    @HeadFontStyle(color = 10)
    private String ownerName;

    @Schema(description = "员工姓名", example = "张三")
    @ExcelProperty("员工姓名(必填）")
    @HeadFontStyle(color = 10)
    private String name;

    @Schema(description = "员工电话", example = "13111111111")
    @ExcelProperty("员工电话(必填）")
    @HeadFontStyle(color = 10)
    private String phone;

    @Schema(description = "员工身份（超级管理员、管理员、普通员工）", example = "张三")
    @ExcelProperty("员工身份（超级管理员、管理员、普通员工）(必填）")
    @HeadFontStyle(color = 10)
    private String type;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;


    @Schema(description = "证件类型（身份证、港澳台、护照等）", example = "身份证")
    @ExcelProperty("证件类型（身份证、港澳台、护照等）")
    private String idcardType;

    @Schema(description = "证件号码")
    @ExcelProperty("证件号码")
    private String idcard;

    @Schema(description = "性别（男，女，未知）")
    @ExcelProperty("性别（男，女，未知）")
    private String sex;

    @Schema(description = "导入结果说明")
    @ExcelProperty("导入结果说明（不需要填写）")
    private String importResult;

}
