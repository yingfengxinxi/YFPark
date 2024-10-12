package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingVerificationDO;
import jakarta.validation.Valid;

import java.util.Map;

/**
 * 预约核销 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvBookingVerificationService {

    /**
     * 创建预约核销
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvBookingVerification(@Valid ResvBookingVerificationSaveReqVO createReqVO);

    /**
     * 更新预约核销
     *
     * @param id 更新信息
     */
    void updateResvBookingVerification(Long id);

    /**
     * 删除预约核销
     *
     * @param id 编号
     */
    void deleteResvBookingVerification(Long id);

    /**
     * 获得预约核销
     *
     * @param id 编号
     * @return 预约核销
     */
    ResvBookingVerificationDO getResvBookingVerification(Long id);

    /**
     * 获得预约核销分页
     *
     * @param pageReqVO 分页查询
     * @return 预约核销分页
     */
    PageResult<ResvBookingVerificationDO> getResvBookingVerificationPage(ResvBookingVerificationPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获得预约核销分页VO
    * @Date 10:53 2024/7/30
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingVerificationRespVO>
    **/
    PageResult<ResvBookingVerificationRespVO> getResvBookingVerificationPageVO(ResvBookingVerificationPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获取核销统计
    * @Date 9:53 2024/8/10
    * @Param [handler]
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String, Object> getCountVerification(Long handler);
}