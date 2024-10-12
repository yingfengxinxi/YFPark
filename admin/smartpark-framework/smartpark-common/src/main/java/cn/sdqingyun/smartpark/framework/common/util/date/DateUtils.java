package cn.sdqingyun.smartpark.framework.common.util.date;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

/**
 * 时间工具类
 *
 * @author 智慧园区
 */
public class DateUtils {

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    /**
     * 秒转换成毫秒
     */
    public static final long SECOND_MILLIS = 1000;

    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String works[] = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};


    /**
     * 将 LocalDateTime 转换成 Date
     *
     * @param date LocalDateTime
     * @return LocalDateTime
     */
    public static Date of(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        // 将此日期时间与时区相结合以创建 ZonedDateTime
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());
        // 本地时间线 LocalDateTime 到即时时间线 Instant 时间戳
        Instant instant = zonedDateTime.toInstant();
        // UTC时间(世界协调时间,UTC + 00:00)转北京(北京,UTC + 8:00)时间
        return Date.from(instant);
    }

    /**
     * 将 Date 转换成 LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }
        // 转为时间戳
        Instant instant = date.toInstant();
        // UTC时间(世界协调时间,UTC + 00:00)转北京(北京,UTC + 8:00)时间
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date addTime(Duration duration) {
        return new Date(System.currentTimeMillis() + duration.toMillis());
    }

    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }

    /**
     * 创建指定时间
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day) {
        return buildTime(year, mouth, day, 0, 0, 0);
    }

    /**
     * 创建指定时间
     *
     * @param year   年
     * @param mouth  月
     * @param day    日
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day,
                                 int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0); // 一般情况下，都是 0 毫秒
        return calendar.getTime();
    }

    public static Date max(Date a, Date b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.isAfter(b) ? a : b;
    }

    /**
     * 是否今天
     *
     * @param date 日期
     * @return 是否
     */
    public static boolean isToday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now());
    }


    /**
     * 是否今天
     *
     * @param date 日期
     * @return 是否
     */
    public static boolean isToday(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTime(date);
        return today.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == target.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == target.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 是否昨天
     *
     * @param date 日期
     * @return 是否
     */
    public static boolean isYesterday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now().minusDays(1));
    }

    /**
     * 计算两个时间点相差月份
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long differenceMonthCount(Date startTime, Date endTime) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String text1 = sim.format(startTime);
        Temporal temporal1 = LocalDate.parse(text1);
        String text2 = sim.format(endTime);
        Temporal temporal2 = LocalDate.parse(text2);
        // 方法返回为相差月份
        long l = ChronoUnit.MONTHS.between(temporal1, temporal2);
        return l;
    }

    /**
     * 计算相差天数
     *
     * @return
     */
    public static Long getDayCount(Date day1, Date day2) {
        Date endDay = day2;
        Date starDay = day1;
        Long starTime = starDay.getTime();
        Long endTime = endDay.getTime();
        //时间戳相差的毫秒数
        Long num = endTime - starTime;
        return num / 24 / 60 / 60 / 1000;
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public static List<String> getDays(Date startTime, Date endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startTime);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endTime);
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }


        return days;
    }

    /**
     * 获取两个日期之间的所有月份 (年月)
     *
     * @param startTime
     * @param endTime
     * @return：YYYY-MM
     */
    public static List<String> getMonthBetweenDate(Date startTime, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();

        //用Calendar 进行日期比较判断
        Calendar calendar = Calendar.getInstance();
        while (startTime.getTime() <= endTime.getTime()) {
            // 把日期添加到集合
            list.add(sdf.format(startTime));
            // 设置日期
            calendar.setTime(startTime);
            //把日期增加一天
            calendar.add(Calendar.MONTH, 1);
            // 获取增加后的日期
            startTime = calendar.getTime();
        }

        return list;
    }




    /**
     * 指定日期增加月份
     *
     * @param startDate
     * @param monthsToAdd
     * @return
     */
    public static String getMonthDate(Date startDate, int monthsToAdd) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String format = sim.format(startDate);
        // 假设我们有一个日期
        LocalDate date = LocalDate.of(Integer.valueOf(format.split("-")[0]), Integer.valueOf(format.split("-")[1]), Integer.valueOf(format.split("-")[2]));

        // 假设我们要增加3个月
        //int monthsToAdd = 3;

        // 增加月份
        LocalDate newDate = date.plusMonths(monthsToAdd);

        // 输出结果
        System.out.println("Original date: " + date.format(DateTimeFormatter.ISO_DATE));
        System.out.println("New date: " + newDate.format(DateTimeFormatter.ISO_DATE));
        return newDate.format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * 指定时间-1天
     *
     * @param startDate
     * @return
     */
    public static String getDateJDay(Date startDate, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate); // 设置为当前日期
        calendar.add(Calendar.DAY_OF_MONTH, -day); // 减去一天
        Date previousDate = calendar.getTime(); // 得到减去一天后的日期
        System.out.println(previousDate);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        return sim.format(previousDate);
    }

    /**
     * 指定时间增加天数
     *
     * @param startDate
     * @param day
     * @return
     */
    public static String getDateJiaDay(Date startDate, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate); // 设置为当前日期
        calendar.add(Calendar.DAY_OF_MONTH, +day); // 加上一天
        Date previousDate = calendar.getTime(); // 得到减去一天后的日期
        System.out.println(previousDate);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        return sim.format(previousDate);
    }

    public static Date getDayTime() {
        SimpleDateFormat sim = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);
        String format = sim.format(new Date());
        try {
            return sim.parse(format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 格式化日期
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date getSimpleDateFormat(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a", Locale.ENGLISH);
        Date date = formatter.parse(dateString);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String format = sim.format(date);

        return sim.parse(format);
    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    /**
     * @param weeksAfter 第几周
     * @param week       周几
     * @return
     */
    public static Date getStartOfWeekAfter(int weeksAfter, int week) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Integer amount = weeksAfter * 7;
        calendar.add(Calendar.DATE, amount); // 当前日期加上14天，即两周后的日期
        calendar.set(Calendar.DAY_OF_WEEK, week); // 设置为周一
        calendar.add(Calendar.DAY_OF_MONTH, +1); // 加上一天
        return sim.parse(sim.format(calendar.getTime()));
    }

    /**
     * 指定时间加上指定小时
     *
     * @param currentDate
     * @param oneHourMillis
     * @return
     */
    public static Date addHourMillis(Date currentDate, Integer oneHourMillis) {
        //2.创建日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);//4.把当前时间设置给日历对象
        calendar.add(Calendar.HOUR, +oneHourMillis);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar.getTime();
        System.out.println(sim.format(date));
        return date;
    }

    /**
     * 指定时间减去指定小时
     *
     * @param currentDate
     * @param oneHourMillis
     * @return
     */
    public static Date reduceHourMillis(Date currentDate, Integer oneHourMillis) {
        //2.创建日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);//4.把当前时间设置给日历对象
        calendar.add(Calendar.HOUR, -oneHourMillis);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar.getTime();
        System.out.println(sim.format(date));
        return date;
    }

    /**
     * 指定时间减去指定分钟
     *
     * @param currentDate
     * @param minute
     * @return
     */
    public static Date reduceMinute(Date currentDate, Integer minute) {
        //2.创建日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);//4.把当前时间设置给日历对象
        calendar.add(Calendar.MINUTE, -minute);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar.getTime();
        System.out.println(sim.format(date));
        return date;
    }

    /**
     * 指定时间周几
     *
     * @param date
     * @return
     */
    public static String getDayOfWeek(Date date) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String time = sim.format(date);
        String[] times = time.split("-");
        int year = Integer.parseInt(times[0]);
        int month = Integer.parseInt(times[1]);
        int day = Integer.parseInt(times[2]);
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime with = dateTime.withYear(year).withMonth(month).withDayOfMonth(day);
        DayOfWeek dayOfWeek = with.getDayOfWeek();
        int value = dayOfWeek.getValue();
        return works[value];
    }

    public static String getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1); // 将日期回退一个月

        String month = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
        // 输出上个月的年份、月份和日期
        System.out.println("Year: " + calendar.get(Calendar.YEAR));
        System.out.println("Month: " + (calendar.get(Calendar.MONTH) + 1)); // 月份是从0开始的，所以需要+1
        System.out.println("Day: " + calendar.get(Calendar.DAY_OF_MONTH));
        return month;
    }

    //data 业务时间
    public static boolean isOutTime(Date data){
        //24小时
        long house=60*60*24;
        //系统当前时间,
        long currentTime = System.currentTimeMillis()/1000;
        //业务时间
        long endTime=data.getTime()/1000;
        if((endTime + house) < currentTime ){
            System.out.println("超过了24小时");
            return true;
        }else{
            System.out.println("没有超过24小时");
            return false;
        }
    }
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(DateUtils.isOutTime(sim.parse("2024-10-08 08:01:00")));
    }

}
