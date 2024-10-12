package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlacePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPlaceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPlaceDO;
import jakarta.validation.Valid;

/**
 * 预约场地 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvPlaceService {

    /**
     * 创建预约场地
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvPlace(@Valid ResvPlaceSaveReqVO createReqVO);

    /**
     * 更新预约场地
     *
     * @param updateReqVO 更新信息
     */
    void updateResvPlace(@Valid ResvPlaceSaveReqVO updateReqVO);

    /**
     * 删除预约场地
     *
     * @param id 编号
     */
    void deleteResvPlace(Long id);

    /**
     * 获得预约场地
     *
     * @param id 编号
     * @return 预约场地
     */
    ResvPlaceDO getResvPlace(Long id);

    /**
     * 获得预约场地分页
     *
     * @param pageReqVO 分页查询
     * @return 预约场地分页
     */
    PageResult<ResvPlaceDO> getResvPlacePage(ResvPlacePageReqVO pageReqVO);

    /**
     * 获得预约场地VO
     *
     * @param id 编号
     * @return 预约场地
     */
    ResvPlaceRespVO getResvPlaceVO(Long id);

}