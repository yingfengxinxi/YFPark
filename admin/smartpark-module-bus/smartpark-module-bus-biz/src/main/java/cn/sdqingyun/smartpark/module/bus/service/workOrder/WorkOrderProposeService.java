package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderDetailVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeDO;
import com.alibaba.fastjson.JSONObject;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构工单数据 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderProposeService {

    /**
     * 创建机构工单数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderProposeSaveReqVO createReqVO) throws Exception;

    /**
     * 更新机构工单数据
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderProposeSaveReqVO updateReqVO);

    /**
     * 删除机构工单数据
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得机构工单数据
     *
     * @param id 编号
     * @return 机构工单数据
     */
    WorkOrderDetailVO workDetail(Long id);

    /**
     * 获得机构工单数据分页
     *
     * @param pageReqVO 分页查询
     * @return 机构工单数据分页
     */
    PageResult<WorkOrderProposeRespVO> getPage(WorkOrderProposePageReqVO pageReqVO);

    /**
     *
     * @param pageReqVO
     * @return
     */
    List<JSONObject> getStatic(WorkOrderProposePageReqVO pageReqVO);

    /**
     *
     * @param id
     * @param remark
     * @param images
     * @param uid
     * @param status
     */
    void handelWorkOrder(Long id,String remark,String images,Long uid,String status);
}