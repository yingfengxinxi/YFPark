package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.AdjustBillListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAdjustPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillAdjustSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillAdjustDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构账单调整 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillAdjustService {

    /**
     * 创建机构账单调整
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillAdjustSaveReqVO createReqVO) throws Exception;

    /**
     * @param id
     */
    public void approved(Long id);


    /**
     * @param id
     */
    public void applicationToVoid(Long id) throws Exception;

    /**
     * @param id
     * @throws Exception
     */
    public void voidAdjustmentApproval(Long id) throws Exception;

    /**
     * 更新机构账单调整
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillAdjustSaveReqVO updateReqVO);

    /**
     * 删除机构账单调整
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构账单调整
     *
     * @param id 编号
     * @return 机构账单调整
     */
    OrgBillAdjustDO get(Long id);

    /**
     * 获得机构账单调整分页
     *
     * @param pageReqVO 分页查询
     * @return 机构账单调整分页
     */
    PageResult<OrgBillAdjustDO> getPage(OrgBillAdjustPageReqVO pageReqVO);

    /**
     * 调整账单
     *
     * @param adjustBillListVO
     * @return
     */
    PageResult<AdjustBillListVO> getAdjustBillList(AdjustBillListVO adjustBillListVO);

    /**
     * @param adjustBillListVO
     * @return
     */
    Map<String, Object> getAdjustBillTopStatistic(AdjustBillListVO adjustBillListVO);
}