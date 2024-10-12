package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HydropowerOperateRecordPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HydropowerOperateRecordRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.HydropowerOperateRecordSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.HydropowerOperateRecordDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 水电表开关记录 Service 接口
 *
 * @author 管理员
 */
public interface HydropowerOperateRecordService {

    /**
     * 创建水电表开关记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid HydropowerOperateRecordSaveReqVO createReqVO);

    /**
     * 更新水电表开关记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid HydropowerOperateRecordSaveReqVO updateReqVO);

    /**
     * 删除水电表开关记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得水电表开关记录
     *
     * @param id 编号
     * @return 水电表开关记录
     */
    HydropowerOperateRecordDO get(Long id);

    /**
     * 获得水电表开关记录分页
     *
     * @param pageReqVO 分页查询
     * @return 水电表开关记录分页
     */
    PageResult<HydropowerOperateRecordRespVO> getPage(HydropowerOperateRecordPageReqVO pageReqVO);

}