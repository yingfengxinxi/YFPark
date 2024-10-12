package cn.sdqingyun.smartpark.module.bus.service.bill;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillApprovalConfigPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo.BillApprovalConfigSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillApprovalConfigDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 账单业务审批配置 Service 接口
 *
 * @author 管理员
 */
public interface BillApprovalConfigService {

    /**
     * 创建账单业务审批配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long save(@Valid BillApprovalConfigSaveReqVO createReqVO);


    BillApprovalConfigDO get();

}