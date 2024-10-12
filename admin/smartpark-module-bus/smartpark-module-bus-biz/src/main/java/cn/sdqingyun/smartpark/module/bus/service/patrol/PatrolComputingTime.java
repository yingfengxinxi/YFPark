package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.framework.common.util.computingTime.WorkOrderComputingTimeUtils;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentLogDO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/8/29 10:38
 */
public class PatrolComputingTime extends WorkOrderComputingTimeUtils {



}
