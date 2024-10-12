package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptLogDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;

/**
 * 账单开据记录 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillReceiptLogService {

    /**
     * 创建账单开据记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillReceiptLogSaveReqVO createReqVO);

    /**
     * 更新账单开据记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillReceiptLogSaveReqVO updateReqVO);

    /**
     * 删除账单开据记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得账单开据记录
     *
     * @param id 编号
     * @return 账单开据记录
     */
    OrgBillReceiptLogDO get(Long id);

    /**
     * 获得账单开据记录分页
     *
     * @param pageReqVO 分页查询
     * @return 账单开据记录分页
     */
    PageResult<OrgBillReceiptLogDO> getPage(OrgBillReceiptLogPageReqVO pageReqVO);

}