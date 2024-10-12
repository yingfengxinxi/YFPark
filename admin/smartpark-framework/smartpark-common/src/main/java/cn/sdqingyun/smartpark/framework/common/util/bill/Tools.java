package cn.sdqingyun.smartpark.framework.common.util.bill;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tools {

    /**
     * 保留两位小数
     *
     * @param decimal
     * @return
     */
    public static String DecimalFormat(Double decimal) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(decimal);
    }

    public static String DecimalFormat(BigDecimal decimal) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(decimal);
    }

    public static String DecimalFormat(BigDecimal decimal, int unitPricePoint) {
        StringBuilder sb = new StringBuilder();
        sb.append("#0.");
        for (int i = 0; i < unitPricePoint; i++) {
            sb.append("0");
        }
        DecimalFormat df = new DecimalFormat(sb.toString());
        return df.format(decimal);
    }
    public static double roundToDecimalPlaces(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long temp = Math.round(value);
        return temp / factor;
    }
    public static void main(String[] args) {

        String s = DecimalFormat(new BigDecimal("0.321231231"),4);
        System.out.println(s);
    }

    public static String DecimalFormat(String decimal) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(Double.valueOf(decimal));
    }

    /**
     * 随机生成六位数验证码
     *
     * @return
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;//(Math.random()*(999999-100000)+100000)
    }

    /**
     * 随机生成四位数验证码
     *
     * @return
     */
    public static int getRandomNum4() {
        Random r = new Random();
        return r.nextInt(9000) + 1000;
    }

    /**
     * @param @param  num 传入的数字
     * @param @param  i 输出的位数
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: numTf
     * @Description: TODO()
     */
    public static String numTf(int num, int i) {
        int length = String.valueOf(num).length();
        String numEnd = "";
        if (length < i) {
            //需要补的位数的0
            int tempNum = i - length;
            String temp = "";
            for (int n = 0; n < tempNum; n++) {
                temp += "0";
            }
            return numEnd = temp + String.valueOf(num);
        }
        if (length > i) {
            return "输入的数大于最大位数";
        }
        return numEnd;
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     * @return
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 往文件里的内容
     *
     * @param filePath 文件路径
     * @param content  写入的内容
     */
    public static void writeFile(String fileP, String content) {
        String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
        filePath = filePath.replaceAll("file:/", "");
        filePath = filePath.replaceAll("%20", " ");
        filePath = filePath.trim() + fileP.trim();
        if (filePath.indexOf(":") != 1) {
            filePath = File.separator + filePath;
        }
        try {
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
