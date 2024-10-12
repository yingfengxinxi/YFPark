package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAnnexSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAnnexDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构账单收支流水附件 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillAnnexService {

    /**
     * 创建机构账单收支流水附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBillAnnex(@Valid OrgBillAnnexSaveReqVO createReqVO);

    /**
     * 更新机构账单收支流水附件
     *
     * @param updateReqVO 更新信息
     */
    void updateBillAnnex(@Valid OrgBillAnnexSaveReqVO updateReqVO);

    /**
     * 删除机构账单收支流水附件
     *
     * @param id 编号
     */
    void deleteBillAnnex(Long id);

    /**
     * 获得机构账单收支流水附件
     *
     * @param id 编号
     * @return 机构账单收支流水附件
     */
    OrgBillAnnexDO getBillAnnex(Long id);

    /**
     * 获得机构账单收支流水附件分页
     *
     * @param pageReqVO 分页查询
     * @return 机构账单收支流水附件分页
     */
    PageResult<OrgBillAnnexDO> getBillAnnexList(OrgBillAnnexPageReqVO pageReqVO);

}