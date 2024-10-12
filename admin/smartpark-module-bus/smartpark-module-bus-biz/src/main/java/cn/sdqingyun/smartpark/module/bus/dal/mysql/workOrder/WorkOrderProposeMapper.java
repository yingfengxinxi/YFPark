package cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeDO;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * 机构工单数据 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface WorkOrderProposeMapper extends BaseMapperX<WorkOrderProposeDO> {

    /**
     *
     * @param application
     * @param dateList
     * @return
     */
    List<JSONObject> getDateProposeList(@Param("application") String application, @Param("dateList") List<String> dateList);
}