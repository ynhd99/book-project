package com.example.room.utils.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private DateUtils() {
        throw new AssertionError();
    }

    public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT2 = "yyyyMMddHHmmss";
    public static final String FORMAT3 = "yyyy-MM-dd";
    public static final String FORMAT4 = "yyyyMMdd";
    private static String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm",
            "yyyyMMddHHmmss", "HHmmss"};

    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }

        return formatDate;
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    public static String getYearMonth() {
        return formatDate(new Date(), "yyyy-MM");
    }

    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        } else {
            try {
                return parseDate(str.toString(), parsePatterns);
            } catch (ParseException arg1) {
                return null;
            }
        }
    }

    public static long pastDays(Date date) {
        long t = (new Date()).getTime() - date.getTime();
        return t / 86400000L;
    }

    public static Date getDateStart(Date date) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
            } catch (ParseException arg2) {
                arg2.printStackTrace();
            }

            return date;
        }
    }

    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
            } catch (ParseException arg2) {
                arg2.printStackTrace();
            }

            return date;
        }
    }

    public static String date2String(Date date, String dateFormat) {
        try {
            SimpleDateFormat e = new SimpleDateFormat(dateFormat);
            return e.format(date);
        } catch (NullPointerException arg2) {
            return null;
        } catch (Exception arg3) {
            arg3.printStackTrace();
            return null;
        }
    }

    public static Date string2Date(String date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);

        try {
            return df.parse(date);
        } catch (ParseException arg3) {
            arg3.printStackTrace();
            return null;
        } catch (NullPointerException arg4) {
            return null;
        }
    }

    public static Integer strToInt(String accoTime) {
        if ("ZERO".equals(accoTime)) {
            return 0;
        } else if ("ONE".equals(accoTime)) {
            return 1;
        } else if ("TWO".equals(accoTime)) {
            return 2;
        } else if ("THREE".equals(accoTime)) {
            return 3;
        } else if ("FOUR".equals(accoTime)) {
            return 4;
        } else if ("FIVE".equals(accoTime)) {
            return 5;
        } else if ("SIX".equals(accoTime)) {
            return 6;
        } else if ("SEVEN".equals(accoTime)) {
            return 7;
        } else if ("EIGHT".equals(accoTime)) {
            return 8;
        } else if ("NINE".equals(accoTime)) {
            return 9;
        } else if ("TEN".equals(accoTime)) {
            return 10;
        } else if ("ELEVEN".equals(accoTime)) {
            return 11;
        } else if ("TWELVE".equals(accoTime)) {
            return 12;
        } else if ("THIRTEEN".equals(accoTime)) {
            return 13;
        } else if ("FOURTEEN".equals(accoTime)) {
            return 14;
        } else if ("FIFTEEN".equals(accoTime)) {
            return 15;
        } else if ("SIXTEEN".equals(accoTime)) {
            return 16;
        } else if ("SEVENTEEN".equals(accoTime)) {
            return 17;
        } else if ("EIGHTEEN".equals(accoTime)) {
            return 18;
        } else if ("NINETEEN".equals(accoTime)) {
            return 19;
        } else if ("TWENTY".equals(accoTime)) {
            return 20;
        } else if ("TWENTYONE".equals(accoTime)) {
            return 21;
        } else if ("TWENTYTWO".equals(accoTime)) {
            return 22;
        } else if ("TWENTYTHREE".equals(accoTime)) {
            return 23;
        }
        return 0;
    }

    public static String strToBigStr(String accoTime) {
        if ("0".equals(accoTime)) {
            return "ZERO";
        } else if ("1".equals(accoTime)) {
            return "ONE";
        } else if ("2".equals(accoTime)) {
            return "TWO";
        } else if ("3".equals(accoTime)) {
            return "THREE";
        } else if ("4".equals(accoTime)) {
            return "FOUR";
        } else if ("5".equals(accoTime)) {
            return "FIVE";
        } else if ("6".equals(accoTime)) {
            return "SIX";
        } else if ("7".equals(accoTime)) {
            return "SEVEN";
        } else if ("8".equals(accoTime)) {
            return "EIGHT";
        } else if ("9".equals(accoTime)) {
            return "NINE";
        } else if ("10".equals(accoTime)) {
            return "TEN";
        } else if ("11".equals(accoTime)) {
            return "ELEVEN";
        } else if ("12".equals(accoTime)) {
            return "TWELVE";
        } else if ("13".equals(accoTime)) {
            return "THIRTEEN";
        } else if ("14".equals(accoTime)) {
            return "FOURTEEN";
        } else if ("15".equals(accoTime)) {
            return "FIFTEEN";
        } else if ("16".equals(accoTime)) {
            return "SIXTEEN";
        } else if ("17".equals(accoTime)) {
            return "SEVENTEEN";
        } else if ("18".equals(accoTime)) {
            return "EIGHTEEN";
        } else if ("19".equals(accoTime)) {
            return "NINETEEN";
        } else if ("20".equals(accoTime)) {
            return "TWENTY";
        } else if ("21".equals(accoTime)) {
            return "TWENTYONE";
        } else if ("22".equals(accoTime)) {
            return "TWENTYTWO";
        } else if ("23".equals(accoTime)) {
            return "TWENTYTHREE";
        }

        return "ZERO";
    }

    public static int getWeekNumOfYear(String strDate, String format) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Date curDate = sformat.parse(strDate);
        calendar.setTime(curDate);
        int iWeekNum = calendar.get(3);
        return iWeekNum;
    }

    public static String getFirstDayOfWeek(int yearNum, int weekNum, String format)
            throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(1, yearNum);
        cal.set(3, weekNum);
        cal.set(7, 1);
        return (new SimpleDateFormat(format)).format(cal.getTime());
    }

    public static String getLastDayOfWeek(int yearNum, int weekNum, String format)
            throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(1, yearNum);
        cal.set(3, weekNum);
        cal.set(7, 7);
        return (new SimpleDateFormat(format)).format(cal.getTime());
    }

    public static String getFirstDayOfWeek(String date, String format) throws ParseException {
        return getFirstDayOfWeek(Integer.parseInt(date.substring(0, 4)),
                getWeekNumOfYear(date, format), format);
    }

    public static String getFirstDayOfWeek(String date, String format, String format2)
            throws ParseException {
        return getFirstDayOfWeek(Integer.parseInt(date.substring(0, 4)),
                getWeekNumOfYear(date, format), format2);
    }

    public static String getFirstDayOfPreWeek(String date, String format) throws ParseException {
        Date d = sumDate(string2Date(date, format), -7);
        return getFirstDayOfWeek(Integer.parseInt(date2String(d, "yyyy")),
                getWeekNumOfYear(date2String(d, format), format), format);
    }

    public static String getLastDayOfPreWeek(String date, String format) throws Exception {
        Date d = sumDate(string2Date(date, format), -7);
        return getLastDayOfWeek(Integer.parseInt(date2String(d, "yyyy")),
                getWeekNumOfYear(date2String(d, format), format), format);
    }

    public static String getLastDayOfWeek(String date, String format) throws Exception {
        return getLastDayOfWeek(Integer.parseInt(date.substring(0, 4)),
                getWeekNumOfYear(date, format), format);
    }

    public static String getLastDayOfWeek(String date, String format, String format2)
            throws Exception {
        return getLastDayOfWeek(Integer.parseInt(date.substring(0, 4)),
                getWeekNumOfYear(date, format), format2);
    }

    public static String getLastDayOfMonth(int year, int month) {
        return getLastDayOfMonth(year, month, "yyyy-MM-dd ");
    }

    public static String getLastDayOfMonth(int year, int month, String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        cal.set(5, cal.getActualMaximum(5));
        return (new SimpleDateFormat(format)).format(cal.getTime());
    }

    public static String getLastDayOfMonth(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(string2Date(date, format));
        return getLastDayOfMonth(calendar.get(1), calendar.get(2) + 1);
    }

    public static String getLastDayOfMonth(String date, String format, String format2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(string2Date(date, format));
        return getLastDayOfMonth(calendar.get(1), calendar.get(2) + 1, format2);
    }

    public static String getLastDayOfPreMonth(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(string2Date(date, format));
        return getLastDayOfMonth(calendar.get(1), calendar.get(2));
    }

    public static String getFirstDayOfMonth(int year, int month) {
        return getFirstDayOfMonth(year, month, "yyyy-MM-dd ");
    }

    public static String getFirstDayOfMonth(int year, int month, String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        cal.set(5, cal.getMinimum(5));
        return (new SimpleDateFormat(format)).format(cal.getTime());
    }

    public static String getFirstDayOfMonth(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(string2Date(date, format));
        return getFirstDayOfMonth(calendar.get(1), calendar.get(2) + 1);
    }

    public static String getFirstDayOfMonth(String date, String format, String format2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(string2Date(date, format));
        return getFirstDayOfMonth(calendar.get(1), calendar.get(2) + 1, format2);
    }

    public static String getFirstDayOfPreMonth(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(string2Date(date, format));
        return getFirstDayOfMonth(calendar.get(1), calendar.get(2));
    }

    public static long subDate(Date date1, Date date2) {
        long l = date2.getTime() - date1.getTime();
        return l / 1000L;
    }

    public static long daysBetween(Date date1, Date date2) {
        return subDate(date1, date2) / 86400L;
    }

    public static long weeksBetween(Date date1, Date date2) {
        return daysBetween(date1, date2) / 7L;
    }

    /**
     * 获取两个日期相差月日期数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 结果
     * @throws ParseException
     */
    public static List<String> getMonthBetween(Date start, Date end) {
        ArrayList<String> result = new ArrayList<String>();

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(start);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(end);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            String monthItem = String.valueOf(curr.get(Calendar.MONTH) + 1);
            result.add(curr.get(Calendar.YEAR) + "-" + (monthItem.length() == 1 ? ("0" + monthItem) : monthItem));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static long monthsBetween(Date date1, Date date2) {
        int year1 = Integer.parseInt(formatDate(date1, "yyyy"));
        int year2 = Integer.parseInt(formatDate(date2, "yyyy"));
        int month1 = Integer.parseInt(formatDate(date1, "MM"));
        int month2 = Integer.parseInt(formatDate(date2, "MM"));
        return (long) ((year2 - year1) * 12 + (month2 - month1));
    }

    public static Date sumDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.get(5) + day);
        return calendar.getTime();
    }

    public static String changeFormat(String date, String format1, String format2) {
        SimpleDateFormat df = new SimpleDateFormat(format1);
        SimpleDateFormat df2 = new SimpleDateFormat(format2);

        try {
            return df2.format(df.parse(date));
        } catch (ParseException arg5) {
            arg5.printStackTrace();
            return "";
        }
    }

    public static boolean isLager(Date large, Date small) {
        return large != null && (small == null || large.getTime() > small.getTime());
    }

    public static long string2long(String datatime, String fm) {
        long time = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat(fm);

        try {
            time = sdf.parse(datatime).getTime();
        } catch (ParseException arg5) {
            arg5.printStackTrace();
        }

        return time;
    }

    //	public static void main(String[] args) throws Exception {
    //		System.out.println(System.currentTimeMillis());
    //		long time = string2long("2016-08-24 15:10:30", "yyyy-MM-dd HH:mm:ss");
    //		System.out.println(System.currentTimeMillis() - time > 1800000L);
    //	}

    /**
     * 判断指定日期的周历
     *
     * @param date
     * @return
     */
    public static int getDayofWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取今天凌晨
     *
     * @return
     */
    public static Date getTodayMorning() {
        Date todayMorning = DateUtils.parseDate(DateUtils.getDate());
        return todayMorning;
    }

    public static Date getTodayMorning(Date today) throws ParseException {
        Date todayMorning = DateUtils.parseDate(DateUtils.formatDate(today, DateUtils.FORMAT3), DateUtils.FORMAT3);
        return todayMorning;
    }

    /**
     * 获取明天凌晨
     *
     * @return
     */
    public static Date getTomorrowMorning() {
        Date todayMorning = DateUtils.getTodayMorning();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayMorning);
        calendar.add(calendar.DATE, 1);
        Date tomorrowMorning = calendar.getTime();
        return tomorrowMorning;
    }

    public static Date getTomorrowMorning(Date current) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(calendar.DATE, 1);
        Date tomorrowMorning = calendar.getTime();
        return tomorrowMorning;
    }

    /**
     * 两个"yyyy-MM-dd"  格式日期比较
     *
     * @param bussDate
     * @param date
     * @return
     * @throws Exception
     */
    public static boolean compareDate(Date bussDate, Date date) throws Exception {

        //判断是否是补盘操作
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = sdf.parse(sdf.format(date));
        return bussDate.before(nowDate);
    }

    /**
     * 获取 yyyy-MM-dd 格式的当前时间
     *
     * @return
     */
    public static Date getNowDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(getDate());
    }

    /**
     * 获取上个月最后一天
     *
     * @return
     * @throws Exception
     */
    public static Date getLastDayOfLastMonth() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        String lastDay = format.format(cale.getTime());
        return format.parse(lastDay);
    }


}
