package cn.sdqingyun.smartpark.module.bus.service.resv;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 预约 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvBookingService {

    /**
     * 创建预约
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    ResvBookingSaveReqVO createResvBooking(@Valid ResvBookingSaveReqVO createReqVO);

    /**
     * 更新预约
     *
     * @param updateReqVO 更新信息
     */
    void updateResvBooking(@Valid ResvBookingSaveReqVO updateReqVO);

    /**
     * 删除预约
     *
     * @param id 编号
     */
    void deleteResvBooking(Long id);

    /**
     * 获得预约
     *
     * @param id 编号
     * @return 预约
     */
    ResvBookingDO getResvBooking(Long id);

    /**
     * 获得预约分页
     *
     * @param pageReqVO 分页查询
     * @return 预约分页
     */
    PageResult<ResvBookingDO> getResvBookingPage(ResvBookingPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获得预约记录分页VO
    * @Date 9:03 2024/7/30
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingRespVO>
    **/
    PageResult<ResvBookingRespVO> getResvBookingPageVO(ResvBookingPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获取时间段
    * @Date 11:54 2024/8/9
    * @Param [createReqVO]
    * @return java.lang.String
    **/
    String getTimeSlots(ResvBookingSaveReqVO createReqVO);

}