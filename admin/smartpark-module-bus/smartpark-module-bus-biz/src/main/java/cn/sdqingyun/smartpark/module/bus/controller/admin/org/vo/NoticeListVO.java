package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/7/23 16:33
 */
@Data
public class NoticeListVO extends PageParam  {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "账单id")
    private String contractBillId;

    @Schema(description = "租客名称")
    private String ownerName;

    @Schema(description = "楼宇")
    private String buildName;

    @Schema(description = "楼号/房号")
    private String roomNumber;

    @Schema(description = "短信发送状态1=失败2=成功")
    private String smsStatus;

    @Schema(description = "邮件发送状态1=失败2=成功")
    private String emailStatus;

    @Schema(description = "站内信发送状态1=失败2=成功")
    private String mailStatus;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "创建开始时间")
    private String startCreateTime;

    @Schema(description = "创建结束时间")
    private String endCreateTime;


    @Schema(description = "楼宇查询条件")
    private List<Long> buildIdList;
}
