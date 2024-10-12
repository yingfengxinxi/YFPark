package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyDO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义 Service 接口
 *
 * @author 管理员
 */
public interface EnergyService {

    /**
     * 创建自定义
     *
     * @param createReqVO   创建信息
     * @param updateSupport 是否同步资产
     * @return 编号
     */
    Long create(@Valid EnergySaveReqVO createReqVO, Boolean updateSupport) throws Exception;

    /**
     * @return
     */
    List<EnergyImportExcelVO> importTemplate();

    /**
     * @param list
     * @param updateSupport
     * @param response
     * @return
     */
    Boolean importExcel(List<EnergyImportExcelVO> list, Boolean updateSupport, HttpServletResponse response);

    /**
     * 更新自定义
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergySaveReqVO updateReqVO, Boolean updateSupport) throws Exception;

    /**
     * 删除自定义
     *
     * @param id 编号
     */
    void delete(Long id) throws Exception;

    /**
     * 获得自定义
     *
     * @param id 编号
     * @return 自定义
     */
    EnergyRespVO get(Long id);

    /**
     * 获得自定义分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义分页
     */
    PageResult<EnergyRespVO> smartEnergyList(EnergyPageReqVO pageReqVO);


    /**
     * 获得自定义抄表记录分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义抄表记录分页
     */
    PageResult<EnergyRespVO> getPage(EnergyPageReqVO pageReqVO);


    /**
     * @param id
     */
    void cancel(Long id) throws Exception;

    /**
     *
     * @param id
     */
    void close(Long id);

    /**
     *
     * @param pageReqVO
     * @return
     */
    List<EnergyRespVO> getList(EnergyPageReqVO pageReqVO);
}