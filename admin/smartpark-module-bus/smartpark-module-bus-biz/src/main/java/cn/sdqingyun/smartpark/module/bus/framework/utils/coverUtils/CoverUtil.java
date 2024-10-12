package cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils;

import com.alibaba.nacos.common.utils.StringUtils;

/**
 * @ClassName CoverUtil
 * @Description 字典转换
 * @Author SUNk
 * @Date 2024/7/9 16:15
 * @ModifyDate 2024/7/9 16:15
 * @Version 1.0
 */
public class CoverUtil {
    public static String coverVillageType(String villageType) {
        if (StringUtils.isEmpty(villageType)) {
            return null;
        }
        switch (villageType) {
            case "超级管理员":
                return "0";
            case "管理员":
                return "10";
            case "普通员工":
                return "20";
            default:
                return "0";
        }
    }

    public static String coverIdCardType(String idCardType) {
        if (StringUtils.isEmpty(idCardType)) {
            return null;
        }
        switch (idCardType) {
            case "身份证":
                return "idcard";
            case "护照":
                return "passport";
            case "军人证":
                return "idcard";
            case "港澳通行证":
                return "gangaotai";
            case "台湾通行证":
                return "gangaotai";
            default:
                return "idcard";
        }
    }

    public static String coverVillageSex(String sex) {
        if (StringUtils.isEmpty(sex)) {
            return null;
        }
        switch (sex) {
            case "男":
                return "0";
            case "女":
                return "1";
            case "未知":
                return "2";
            default:
                return "0";
        }
    }
}
