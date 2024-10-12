package cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Contract 流程工具类
 *
 * @author HUIHUI
 */
public class ContractAuditStatusUtils {

    /**
     * Contract 审批结果转换
     *
     * @param bpmResult Contract 审批结果
     */
    public static Integer convertBpmResultToAuditStatus(Integer bpmResult) {
        return 1;
    }

    /**
     * 加
     * @param villageList
     * @param villageId
     * @return
     */
    public static String convertVillageList(String villageList,Long villageId){
        if(StringUtils.isBlank(villageList)){
            return villageId.toString();
        }

        if(villageList.contains(villageId.toString())){
            return villageList;
        }

        return villageList+","+villageId;
    }

    /**
     * 加
     * @param oldBuildBingder
     * @param newBuildBingder
     * @return
     */
    //oldBuildBingder = [{"id": 80, "parkId": 1, "roomId": 101, "buildId": 1, "roomName": "101", "buildName": "一号楼1", "layerName": "2层", "level_key": "3-80", "contractId": "", "level1_key": "1-1", "level2_key": "2-6", "rentalArea": 101, "roomNumber": 101, "villageName": "舜泰广场", "villageRoomId": "101"}]
    public static String convertBuildBingder(String oldBuildBingder,String newBuildBingder){
        if(StringUtils.isEmpty( oldBuildBingder )){
            return newBuildBingder;
        }
        if(StringUtils.isEmpty( newBuildBingder )){
            return oldBuildBingder;
        }

        // 将字符串转换为JSON数组
        JSONArray oldArray = JSONArray.parseArray(oldBuildBingder);
        JSONArray newArray = JSONArray.parseArray(newBuildBingder);

        // 创建一个Set用于存储已有的roomId
        Set<Integer> roomIds = new HashSet<>();
        JSONArray resultArray = new JSONArray();

        // 将老的数组对象先添加到结果集，并记录已有的roomId
        for (int i = 0; i < oldArray.size(); i++) {
            JSONObject obj = oldArray.getJSONObject(i);
            int roomId = obj.getInteger("roomId");
            roomIds.add(roomId);
            resultArray.add(obj);
        }

        // 将新的数组对象进行检查，如果roomId不存在则添加到结果集
        for (int i = 0; i < newArray.size(); i++) {
            JSONObject obj = newArray.getJSONObject(i);
            int roomId = obj.getInteger("roomId");
            if (!roomIds.contains(roomId)) {
                resultArray.add(obj);
            }
        }

        // 返回合并后的结果集，转换回字符串
        return resultArray.toJSONString();
    }

    /**
     * 减
     * @param villageList
     * @param villageId
     * @return
     */
    public static String subtractVillageId(String villageList, Long villageId) {
        if (StringUtils.isBlank(villageList)) {
            return "";
        }

        List<String> villages = new ArrayList<>( Arrays.asList(villageList.split(",")));
        villages.remove(villageId.toString());

        if (villages.isEmpty()) {
            return "";
        }

        return String.join(",", villages);
    }

    /**
     * 减
     * @param oldBuildBingder
     * @param newBuildBingder
     * @return
     */
    public static String subtractBuildBinder(String oldBuildBingder, String newBuildBingder) {
        if (StringUtils.isEmpty(oldBuildBingder)) {
            return "";
        }
        if (StringUtils.isEmpty(newBuildBingder)) {
            return oldBuildBingder;
        }

        // 将字符串转换为JSON数组
        JSONArray oldArray = JSONArray.parseArray(oldBuildBingder);
        JSONArray newArray = JSONArray.parseArray(newBuildBingder);

        JSONArray resultArray = new JSONArray();

        // 将老的数组对象进行检查，如果不包含在新的数组对象中则保留
        for (int i = 0; i < oldArray.size(); i++) {
            JSONObject oldObj = oldArray.getJSONObject(i);
            int oldRoomId = oldObj.getInteger("roomId");

            boolean found = false;
            for (int j = 0; j < newArray.size(); j++) {
                JSONObject newObj = newArray.getJSONObject(j);
                int newRoomId = newObj.getInteger("roomId");

                if (oldRoomId == newRoomId) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                resultArray.add(oldObj);
            }
        }

        // 如果结果数组为空，则返回空字符串
        if (resultArray.isEmpty()) {
            return "";
        }

        // 返回减去后的结果集，转换回字符串
        return resultArray.toJSONString();
    }
}
