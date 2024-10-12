package cn.sdqingyun.smartpark.module.bus.service.category;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetReservationDatePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo.CategoryFeeSetReservationDateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryFeeSetReservationDateDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单子类费用预约时段设置 Service 接口
 *
 * @author 管理员
 */
public interface CategoryFeeSetReservationDateService {

    /**
     * 创建工单子类费用预约时段设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid CategoryFeeSetReservationDateSaveReqVO createReqVO);

    /**
     * 更新工单子类费用预约时段设置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid CategoryFeeSetReservationDateSaveReqVO updateReqVO);

    /**
     * 删除工单子类费用预约时段设置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单子类费用预约时段设置
     *
     * @param id 编号
     * @return 工单子类费用预约时段设置
     */
    CategoryFeeSetReservationDateDO get(Long id);

}