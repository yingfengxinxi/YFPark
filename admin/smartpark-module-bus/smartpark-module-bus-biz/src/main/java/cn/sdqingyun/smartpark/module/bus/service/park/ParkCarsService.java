package cn.sdqingyun.smartpark.module.bus.service.park;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo.ParkCarsSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.park.ParkCarsDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 月租车白名单 Service 接口
 *
 * @author 智慧园区
 */
public interface ParkCarsService {

    /**
     * 创建月租车白名单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ParkCarsSaveReqVO createReqVO);

    /**
     * 更新月租车白名单
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid ParkCarsSaveReqVO updateReqVO);

    /**
     * 删除月租车白名单
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得月租车白名单
     *
     * @param id 编号
     * @return 月租车白名单
     */
    ParkCarsDO get(Long id);

    /**
     * 获得月租车白名单分页
     *
     * @param pageReqVO 分页查询
     * @return 月租车白名单分页
     */
    PageResult<ParkCarsDO> getPage(ParkCarsPageReqVO pageReqVO);

    /**
     * @param villageId
     * @param carNumber
     * @param id
     */
    Boolean isCheckCarNumber(Long villageId, String carNumber, Long id);
}