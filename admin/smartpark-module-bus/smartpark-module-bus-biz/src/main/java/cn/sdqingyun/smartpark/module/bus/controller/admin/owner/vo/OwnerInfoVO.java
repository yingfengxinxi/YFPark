package cn.sdqingyun.smartpark.module.bus.controller.admin.owner.vo;

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.RoomOwnerListVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/8/1 16:09
 */
@Data
public class OwnerInfoVO {

    @Schema(description = "租客id")
    private Long id;

    @Schema(description = "租客")
    private String ownerName;

    @Schema(description = "是否个人，1个人，0公司")
    private String isPersonal;


    @Schema(description = "联系人")
    private String contactName;


    @Schema(description = "合同有效期")
    private String contractValidityPeriod;

    @Schema(description = "合同id")
    private Long contractId;

    @Schema(description = "合同状态")
    private String status;

    @Schema(description = "合同状态")
    private String statusName;

    @Schema(description = "经办人")
    private String operatorName;

    @Schema(description = "房屋")
    private String roomNumber;

    @Schema(description = "关联房屋")
    List<RoomOwnerListVO> roomOwnerListVOList;
}
