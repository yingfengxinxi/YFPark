package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/9/13 17:01
 */
public interface WorkOrderMyBoardService {


    /**
     *
     * @param departmentIds
     * @param application
     * @return
     */
    List<JSONObject> topStatic(String departmentIds, String application);

    /**
     *
     * @param application
     * @return
     */
    JSONObject workOrderNumsTread(String application) throws ParseException;

    /**
     *
     * @param application
     * @return
     */
    JSONObject satisfactionStatic(String application);

    /**
     *
     * @param application
     * @return
     */
    List<JSONObject> halfYearTread(String application) throws Exception;

    /**
     *
     * @param application
     * @return
     */
    JSONObject workOrderTypeRatio(String application);

    /**
     *
     * @param application
     * @return
     */
    JSONObject orderTypeRatio(String application);
}
