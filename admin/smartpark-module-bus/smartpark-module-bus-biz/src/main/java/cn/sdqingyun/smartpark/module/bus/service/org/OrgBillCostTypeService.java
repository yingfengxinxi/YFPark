package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.CostTypeChildrenVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostTypePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostTypeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 账单费用类型 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillCostTypeService {

    /**
     * 创建账单费用类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillCostTypeSaveReqVO createReqVO);

    /**
     * 更新账单费用类型
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillCostTypeSaveReqVO updateReqVO);

    /**
     * 删除账单费用类型
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得账单费用类型
     *
     * @param id 编号
     * @return 账单费用类型
     */
    OrgBillCostTypeDO get(Long id);

    /**
     * 获得账单费用类型分页
     *
     * @param pageReqVO 分页查询
     * @return 账单费用类型分页
     */
    PageResult<OrgBillCostTypeDO> getPage(OrgBillCostTypePageReqVO pageReqVO);


    /**
     * @param name
     * @param id
     * @param categoryId
     * @return
     */
    Boolean isChackName(String name, Long id, Long categoryId);


    /**
     *
     * @return
     */
    List<CostTypeChildrenVO> getCostTypeChildrenList();

    /**
     *
     * @return
     */
    List<OrgBillCostTypeDO>getBondList();
}