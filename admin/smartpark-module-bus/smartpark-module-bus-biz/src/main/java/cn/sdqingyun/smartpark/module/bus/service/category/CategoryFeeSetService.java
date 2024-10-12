package cn.sdqingyun.smartpark.module.bus.service.category;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryFeeSetDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单子类费用设置 Service 接口
 *
 * @author 管理员
 */
public interface CategoryFeeSetService {

    /**
     * 创建工单子类费用设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long save(@Valid CategoryFeeSetSaveReqVO createReqVO) throws Exception;

    /**
     * 删除工单子类费用设置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单子类费用设置
     *
     * @param id 编号
     * @return 工单子类费用设置
     */
    CategoryFeeSetDO get(Long id);

    /**
     * 获得工单子类费用设置分页
     *
     * @param pageReqVO 分页查询
     * @return 工单子类费用设置分页
     */
    PageResult<CategoryFeeSetDO> getPage(CategoryFeeSetPageReqVO pageReqVO);

    /**
     *
     * @param subcatId
     * @return
     */
    CategoryFeeSetRespVO getFeeSet(Long subcatId, String date);
}