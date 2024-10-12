package cn.sdqingyun.smartpark.module.bus.service.resv;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBillRuleDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 预约收费规则 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvBillRuleService {

    /**
     * 创建预约收费规则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvBillRule(@Valid ResvBillRuleSaveReqVO createReqVO);

    /**
     * 更新预约收费规则
     *
     * @param updateReqVO 更新信息
     */
    void updateResvBillRule(@Valid ResvBillRuleSaveReqVO updateReqVO);

    /**
     * 删除预约收费规则
     *
     * @param id 编号
     */
    void deleteResvBillRule(Long id);

    /**
     * 获得预约收费规则
     *
     * @param id 编号
     * @return 预约收费规则
     */
    ResvBillRuleDO getResvBillRule(Long id);

    /**
     * 获得预约收费规则分页
     *
     * @param pageReqVO 分页查询
     * @return 预约收费规则分页
     */
    PageResult<ResvBillRuleDO> getResvBillRulePage(ResvBillRulePageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 获得预约收费规则分页VO
    * @Date 15:06 2024/7/29
    * @Param [pageReqVO]
    * @return cn.sdqingyun.smartpark.framework.common.pojo.PageResult<cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBillRuleDO>
    **/
    PageResult<ResvBillRuleRespVO> getResvBillRulePageVO(ResvBillRulePageReqVO pageReqVO);

}