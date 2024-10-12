package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolFormPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolFormSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolFormDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 巡检表单设置 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolFormService {

    /**
     * 创建巡检表单设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolFormSaveReqVO createReqVO);

    /**
     * 更新巡检表单设置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolFormSaveReqVO updateReqVO);

    /**
     * 删除巡检表单设置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得巡检表单设置
     *
     * @param id 编号
     * @return 巡检表单设置
     */
    PatrolFormDO get(Long id);

    /**
     * 获得巡检表单设置分页
     *
     * @param pageReqVO 分页查询
     * @return 巡检表单设置分页
     */
    PageResult<PatrolFormDO> getPage(PatrolFormPageReqVO pageReqVO);

    /**
     *
     * @param updateReqVO
     */
    void updateIsDefault(PatrolFormSaveReqVO updateReqVO);
}