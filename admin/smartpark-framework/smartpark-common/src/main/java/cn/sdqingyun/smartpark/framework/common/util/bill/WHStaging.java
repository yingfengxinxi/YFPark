//package cn.sdqingyun.smartpark.framework.common.util.bill;
//
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.text.ParsePosition;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
///**
// * @author wangheng
// * @email 372786530@qq.com
// * Date 2019/8/22
// */
//public class WHStaging {
//
//    private static toDateUtil dateUtil = new toDateUtil();
//
//    //计算交易明细
//    public static Object jisuan(String borrowDateStr,int x,int y,BigDecimal money,BigDecimal monthRate,String type) {
//        long period = x + y;
//        Date borrowDate = dateUtil.dateFromString(borrowDateStr);
//        List<Date> repaymentDates = getRepaymentDatesByBorrowingDay(borrowDate, period);
//        if("0".equals(type) || "5".equals(type)) {
//            return repaymentList(repaymentDates, x, y, money, monthRate);
//        }else if("1".equals(type)){
//            return repaymentList2(repaymentDates, x, y, money, monthRate);
//        }else if("2".equals(type)){
//            return repaymentList3(repaymentDates, x, y, money, monthRate);
//        }else {
//            return repaymentList4(repaymentDates, x, y, money, monthRate);
//        }
//    }
//
//    //获取时间集合
//    public static Object date(String borrowDateStr,int num) {
//        Date borrowDate = dateUtil.dateFromString(borrowDateStr);
//        List<Date> repaymentDates = getRepaymentDatesByBorrowingDay(borrowDate, num);
//        List<String> dates = new ArrayList<String>();
//        for(int i = 0;i<num;i++) {
//            dates.add(dateUtil.fromDate(repaymentDates.get(i)));
//        }
//        return dates;
//    }
//
//    public static void main(String[] args) {
//        // TODO 设置已知数
//        // 借款日期
//        String borrowDateStr = "2019-3-31";
//        // 通过借款日字符串获取还款日。 ++
//        //Integer repaymentDay =
//        // dateUtil.getDay(dateUtil.dateFromString(borrowDateStr));
//        // 月利率
//        //BigDecimal monthRate = new BigDecimal("0.006"); // 0.006 ~ 0.015
//        // 弹性分期
//        int x = 10;
//        // 固定分期
//        //int y = 4;
//        // 分期数
//        // 本金
//        //BigDecimal money = BigDecimal.valueOf(12);
//
//        //String type = "1";
//
//        //计算交易明细集合
//        //System.out.println(jisuan(borrowDateStr, x, y, money, monthRate,type));
//
//        //计算时间集合
//        System.out.println(date(borrowDateStr,x));
//
//        //String str = dateIntervalMonth(borrowDateStr, 11);
//        //System.out.println(str);
//    }
//
//
//
//    /**
//     * 传入日期 往前或往后 顺延月数
//     *
//     * params: String date:传入日期(yyyy-MM-dd);Integer num:顺延月数(月份减1为-1，加1为1)
//     * result: String(yyyy-MM-dd)
//     */
//    public static String dateIntervalMonth(String date,Integer num){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ParsePosition pos = new ParsePosition(0);//表示索引从第几个开始解析字符串
//        Date strtodate = formatter.parse(date, pos);
//
//        Calendar calendar = Calendar.getInstance();//日历对象
//        calendar.setTime(strtodate);//设置当前日期
//        calendar.add(Calendar.MONTH, num);//月份减1为-1，加1为1
//        Date d = calendar.getTime();
//        return formatter.format(d);
//    }
//
//
//
//    /**
//     * 通过 借款日与分期数确定每期还款日期，每期还款日期为次月借款日，如果 次月借款日不存在则提前还，不顺延下月，宁少31天不多31天。
//     * @param borrowingDate 借款日期
//     * @param period 分期数。
//     * @return 返回还款日期的日期数组
//     */
//    public static List<Date> getRepaymentDatesByBorrowingDay(Date borrowingDate,long period){
//        // 通过借款日获取还款日 （几号）
//        //Integer repaymentDay = dateUtil.getDay(borrowingDate);
//        // 返回的数组
//        ArrayList<Date> repaymentDates = new ArrayList<Date>();
//        for (int i = 1 ; i <= period ;i++){
//            Date repaymentDate = dateUtil.dateAddNumber('m',borrowingDate,i);
//            repaymentDates.add(repaymentDate);
//        }
//        return  repaymentDates;
//    }
//    /**
//     * 用借款日期和还款日期集合获取每期的天数数组
//     * @param list
//     * @param borrowDate
//     * @return
//     */
//    public static Integer[] gapDayFromRepaymentDates(List<Date> list,Date borrowDate){
//        Integer[] gapDays = new Integer[list.size()];
//        for (int i=0;i<list.size();i++){
//            Integer gap = dateUtil.intervalDate(borrowDate,list.get(i));
//            borrowDate = list.get(i);
//            gapDays[i] = gap;
//        }
//        return gapDays;
//    }
//
//    /**
//     *
//     * 根据还款日期列表及分期方式计算每期费用（前零后高）
//     *
//     * @param repaymentDates  还款日期列表
//     * @param x               弹性分期数
//     * @param y               固定分期数
//     * @param money           总额
//     * @param monthRate       月利率
//     * @return
//     */
//    public static List<Map<String, Object>> repaymentList2(List<Date> repaymentDates,int x,int y,BigDecimal money,BigDecimal monthRate) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        BigDecimal train =null;//培训费
//        BigDecimal xy = new BigDecimal(x+y);
//        BigDecimal serviceTotal = new BigDecimal( new java.text.DecimalFormat("0.00").format(new BigDecimal((money.multiply(monthRate).multiply(xy).toString())))); //总服务费=总金额*月利率*分期期数
//        System.out.println("serviceTotal=================="+serviceTotal);
//        BigDecimal service = new BigDecimal(new java.text.DecimalFormat("0.00").format(serviceTotal.divide(BigDecimal.valueOf(y), 2, RoundingMode.UP)));
//        if(x!=0) {
//            train = BigDecimal.valueOf(0);
//            for(int i=0;i<x;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//                map.put("train", 0);
//                map.put("service", 0);
//                map.put("total", 0);
//                map.put("mechanismService", new BigDecimal("0"));
//                String a=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",a);
//                list.add(map);
//            }
//            int number = x ;
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            for(int j=0;j<y;j++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", ++number);
//                String b=dateUtil.fromDate(repaymentDates.get(number-1));
//                map.put("date",b);
//                map.put("service", service);
//                map.put("mechanismService", new BigDecimal("0"));
//                if(number<y+x) {
//                    map.put("total", train.add(service));
//                    map.put("train",new java.text.DecimalFormat("0.00").format(new BigDecimal((money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)).toString())));
//                }
//                //最后一笔
//                else if (number == x + y) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = new BigDecimal(new java.text.DecimalFormat("0.00").format(new BigDecimal((money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP))).toString()))));
//                    ;
//                    map.put("total", train.add(service));
//                    map.put("train", train);
//
//                }
//                else {
//                    break;
//                }
//
//                list.add(map);
//            }
//        }else {
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            for(int i=0;i<y;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//
//                map.put("service", service);
//                map.put("mechanismService", new BigDecimal("0"));
//                String c=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",c);
//                if(i==y-1) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)));
//                    map.put("total", train.add(service));
//                    map.put("train", train);
//                }else {
//                    map.put("train", train);
//                    map.put("total", train.add(service));
//                }
//
//                list.add(map);
//            }
//
//        }
//        return list;
//
//    }
//
//    /**
//     *
//     * 根据还款日期列表及分期方式计算每期费用(传统模式)
//     *
//     * @param repaymentDates  还款日期列表
//     * @param x               弹性分期数
//     * @param y               固定分期数
//     * @param money           总额
//     * @param monthRate       月利率
//     * @return
//     */
//    public static List<Map<String, Object>> repaymentList(List<Date> repaymentDates,int x,int y,BigDecimal money,BigDecimal monthRate) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        BigDecimal train =null;//培训费
//        BigDecimal service = new BigDecimal( new java.text.DecimalFormat("0.00").format(new BigDecimal((money.multiply(monthRate).toString())))); //服务费=总金额*月利率
//        if(x!=0) {
//            train = BigDecimal.valueOf(0);
//            for(int i=0;i<x;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//                map.put("train", 0);
//                map.put("service", service);
//                map.put("mechanismService", new BigDecimal("0"));
//                map.put("total", train.add(service));
//                String a=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",a);
//                list.add(map);
//            }
//            int number = x ;
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            for(int j=0;j<y;j++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", ++number);
//                String b=dateUtil.fromDate(repaymentDates.get(number-1));
//                map.put("date",b);
//                map.put("service", service);
//                map.put("mechanismService", new BigDecimal("0"));
//                if(number<y+x) {
//                    map.put("total", train.add(service));
//                    map.put("train",new java.text.DecimalFormat("0.00").format(new BigDecimal((money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)).toString())));
//                }
//                //最后一笔
//                else if (number == x + y) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = new BigDecimal(new java.text.DecimalFormat("0.00").format(new BigDecimal((money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP))).toString()))));
//                    ;
//                    map.put("total", train.add(service));
//                    map.put("train", train);
//
//                }
//                else {
//                    break;
//                }
//
//                list.add(map);
//            }
//        }else {
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            for(int i=0;i<y;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//
//                map.put("service", service);
//                map.put("mechanismService", new BigDecimal("0"));
//                String c=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",c);
//                if(i==y-1) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)));
//                    map.put("total", train.add(service));
//                    map.put("train", train);
//                }else {
//                    map.put("train", train);
//                    map.put("total", train.add(service));
//                }
//
//                list.add(map);
//            }
//
//        }
//        return list;
//
//    }
//
//
//    /**
//     *
//     * 根据还款日期列表及分期方式计算每期费用(传统模式机构贴息)
//     *
//     * @param repaymentDates  还款日期列表
//     * @param x               弹性分期数
//     * @param y               固定分期数
//     * @param money           总额
//     * @param monthRate       月利率
//     * @return
//     */
//    public static List<Map<String, Object>> repaymentList3(List<Date> repaymentDates,int x,int y,BigDecimal money,BigDecimal monthRate) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        BigDecimal train =null;//培训费
//        BigDecimal service = new BigDecimal( new java.text.DecimalFormat("0.00").format(new BigDecimal((money.multiply(monthRate).toString())))); //服务费=总金额*月利率
//        if(x!=0) {
//            train = BigDecimal.valueOf(0);
//            for(int i=0;i<x;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//                map.put("train", new BigDecimal("0"));
//                map.put("service", new BigDecimal("0")); //学员
//                map.put("mechanismService", new BigDecimal("0"));
//                map.put("total", train);
//                String a=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",a);
//                list.add(map);
//            }
//            int number = x ;
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            BigDecimal  number1 = new BigDecimal(number+1);
//            for(int j=0;j<y;j++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("mechanismService", service);
//                if(j == 0) {
//                    map.put("mechanismService", service.multiply(number1));
//                }
//                map.put("number", ++number);
//                String b=dateUtil.fromDate(repaymentDates.get(number-1));
//                map.put("date",b);
//                map.put("service", new BigDecimal("0"));
//                if(number<y+x) {
//                    map.put("total", train);
//                    map.put("train",new java.text.DecimalFormat("0.00").format(new BigDecimal((money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)).toString())));
//                }
//                //最后一笔
//                else if (number == x + y) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = new BigDecimal(new java.text.DecimalFormat("0.00").format(new BigDecimal((money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP))).toString()))));
//                    ;
//                    map.put("total", train);
//                    map.put("train", train);
//
//                }
//                else {
//                    break;
//                }
//
//                list.add(map);
//            }
//        }else {
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            for(int i=0;i<y;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//
//                map.put("service", new BigDecimal("0"));
//                map.put("mechanismService", service);
//                String c=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",c);
//                if(i==y-1) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)));
//                    map.put("total", train);
//                    map.put("train", train);
//                }else {
//                    map.put("train", train);
//                    map.put("total", train);
//                }
//
//                list.add(map);
//            }
//
//        }
//        return list;
//
//    }
//
//    /**
//     *
//     * 根据还款日期列表及分期方式计算每期费用（前零后高机构贴息）
//     *
//     * @param repaymentDates  还款日期列表
//     * @param x               弹性分期数
//     * @param y               固定分期数
//     * @param money           总额
//     * @param monthRate       月利率
//     * @return
//     */
//    public static List<Map<String, Object>> repaymentList4(List<Date> repaymentDates,int x,int y,BigDecimal money,BigDecimal monthRate) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        BigDecimal train =null;//培训费
//        BigDecimal xy = new BigDecimal(x+y);
//        BigDecimal serviceTotal = new BigDecimal( new java.text.DecimalFormat("0.00").format(new BigDecimal((money.multiply(monthRate).multiply(xy).toString())))); //总服务费=总金额*月利率*分期期数
//        System.out.println("serviceTotal=================="+serviceTotal);
//        BigDecimal service = new BigDecimal(new java.text.DecimalFormat("0.00").format(serviceTotal.divide(BigDecimal.valueOf(y), 2, RoundingMode.UP)));
//        if(x!=0) {
//            train = BigDecimal.valueOf(0);
//            for(int i=0;i<x;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//                map.put("train", new BigDecimal("0"));
//                map.put("service", new BigDecimal("0"));
//                map.put("total", new BigDecimal("0"));
//                map.put("mechanismService", new BigDecimal("0"));
//                String a=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",a);
//                list.add(map);
//            }
//            int number = x ;
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            for(int j=0;j<y;j++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", ++number);
//                String b=dateUtil.fromDate(repaymentDates.get(number-1));
//                map.put("date",b);
//                map.put("service", new BigDecimal("0"));
//                map.put("mechanismService", service);
//                if(number<y+x) {
//                    map.put("total", train);
//                    map.put("train",new java.text.DecimalFormat("0.00").format(new BigDecimal((money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)).toString())));
//                }
//                //最后一笔
//                else if (number == x + y) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = new BigDecimal(new java.text.DecimalFormat("0.00").format(new BigDecimal((money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP))).toString()))));
//                    ;
//                    map.put("total", train);
//                    map.put("train", train);
//
//                }
//                else {
//                    break;
//                }
//
//                list.add(map);
//            }
//        }else {
//            train = money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP);
//            for(int i=0;i<y;i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("number", i+1);
//
//                map.put("service", new BigDecimal("0"));
//                map.put("mechanismService", service);
//                String c=dateUtil.fromDate(repaymentDates.get(i));
//                map.put("date",c);
//                if(i==y-1) {
//                    BigDecimal n = new BigDecimal(0); //转换y-1
//                    int value = y-1;
//                    n = BigDecimal.valueOf((int) value);
//                    train = money.subtract(n.multiply(money.divide(BigDecimal.valueOf(y),2, RoundingMode.HALF_UP)));
//                    map.put("total", train);
//                    map.put("train", train);
//                }else {
//                    map.put("train", train);
//                    map.put("total", train);
//                }
//
//                list.add(map);
//            }
//
//        }
//        return list;
//
//    }
//}
//
//class toDateUtil{
//    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//    private static Calendar calendar = Calendar.getInstance(Locale.CHINA);
//    //private static final Logger logger = LogManager.getLogger(DateUtil.class);
//
//    /**
//     * 对日期进行计算（加为正，减为负）年月日
//     * @param type 操作的单位（年y，月m，日d）
//     * @param date 基础日期
//     * @param number 操作数
//     * @return 操作后的日期
//     */
//    public Date dateAddNumber(char type, Date date, Integer number) {
//        calendar.setTime(date);
//        switch (type){
//            case 'y':
//                calendar.add(Calendar.YEAR,number);
//                break;
//            case 'm':
//                calendar.add(Calendar.MONTH,number);
//                break;
//            case 'd':
//                calendar.add(Calendar.DATE,number);
//                break;
//            default:
//                calendar.add(Calendar.DATE,number);
//                break;
//        }
//        return calendar.getTime();
//    }
//
//    /**
//     * 形状如yyyy-MM-dd的日期字符串转Java Date类
//     * @param dateStr
//     * @return 转换后的日期
//     */
//    public Date dateFromString(String dateStr){
//        Date date = new Date();
//        try {
//            return simpleDateFormat.parse(dateStr);
//        } catch (Exception e){
//            //logger.error(e);
//        }
//        return date;
//    }
//    /**
//     * Java Date类转形状如yyyy-MM-dd的日期字符串转
//     * @param date
//     * @return 转换后的字符串
//     */
//    public String fromDate(Date date){
//        SimpleDateFormat printFormat = new SimpleDateFormat("yyyy-MM-dd");
//        return printFormat.format(date);
//    }
//
//    /**
//     * 计算两个日期之间的天数
//     * @param start 开始日期
//     * @param end 结束日期
//     * @return 两个日期之间的间隔天数，如果开始日期在后，转换后的日期为负数。
//     */
//    public Integer intervalDate(Date start, Date end) {
//        calendar.setTime(start);
//        long time1 = calendar.getTimeInMillis();
//        calendar.setTime(end);
//        long time2 = calendar.getTimeInMillis();
//        long between_days=(time2-time1)/(1000*3600*24);
//        return Integer.parseInt(String.valueOf(between_days));
//    }
//    public Integer getDay(Date date){
//        calendar.setTime(date);
//        return calendar.get(Calendar.DAY_OF_MONTH);
//    }
//
//    /**
//     * 设置日期的年月日
//     * @param type 设置日期的单位，年月日。
//     * @param date 操作的基础日期
//     * @param number 操作数
//     * @return 返回设置后的日期
//     */
//    public Date setDateWithNumber(char type,Date date,Integer number){
//        calendar.setTime(date);
//        switch (type){
//            case 'y':
//                calendar.set(Calendar.YEAR,number);
//                break;
//            case 'm':
//                calendar.set(Calendar.MONTH,number);
//                break;
//            case 'd':
//                calendar.set(Calendar.DAY_OF_MONTH,number);
//                break;
//            default:
//                calendar.set(Calendar.DAY_OF_MONTH,number);
//
//        }
//        return calendar.getTime();
//    }
//
//}
//
