package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRecordDO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义抄表记录 Service 接口
 *
 * @author 管理员
 */
public interface EnergyRecordService {

    /**
     * 创建自定义抄表记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyRecordSaveReqVO createReqVO);

    /**
     * 更新自定义抄表记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyRecordSaveReqVO updateReqVO);

    /**
     * 删除自定义抄表记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义抄表记录
     *
     * @param id 编号
     * @return 自定义抄表记录
     */
    EnergyRecordDO get(Long id);

    /**
     * 获得自定义抄表记录分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义抄表记录分页
     */
    PageResult<EnergyRecordRespVO> getPage(EnergyRecordPageReqVO pageReqVO);

    /**
     * @param energyId
     * @return
     */
    EnergyRecordDO last(Long energyId);

    /**
     * @return
     */
    List<EnergyRecordImportExcelVO> importTemplate();

    /**
     * @param list
     * @param response
     * @return
     */
    Boolean importExcel(List<EnergyRecordImportExcelVO> list, HttpServletResponse response);

}