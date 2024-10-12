package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractOrderBillCollectionVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexSaveReqVO;
import lombok.Data;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/7/3 10:32
 */
@Data
public class BillStreamSaveVO extends BillStreamSaveReqVO {

    private Long energyId;

    //账单
    private List<ContractOrderBillCollectionVO> billCollectionList;

    /**
     * 附件
     */
    private List<OrgBillAnnexSaveReqVO> annexList;
}
