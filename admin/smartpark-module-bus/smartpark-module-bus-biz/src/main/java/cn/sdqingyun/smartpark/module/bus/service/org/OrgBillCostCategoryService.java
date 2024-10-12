package cn.sdqingyun.smartpark.module.bus.service.org;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillCostCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostCategoryDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 账单费用分类 Service 接口
 *
 * @author 智慧园区
 */
public interface OrgBillCostCategoryService {

    /**
     * 创建账单费用分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid OrgBillCostCategorySaveReqVO createReqVO);

    /**
     * 更新账单费用分类
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid OrgBillCostCategorySaveReqVO updateReqVO);

    /**
     * 删除账单费用分类
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得账单费用分类
     *
     * @param id 编号
     * @return 账单费用分类
     */
    OrgBillCostCategoryDO get(Long id);

    /**
     * 获得账单费用分类分页
     *
     * @return 账单费用分类分页
     */
    List<OrgBillCostCategoryDO> getList(String name);


    /**
     *
     * @param name
     * @param id
     * @return
     */
    Boolean isCheckName(String name,Long id);
}