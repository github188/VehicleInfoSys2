//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jiuling.vehicleinfosys2.util;

import cn.jiuling.vehicleinfosys2.vo.TimeIntervalVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public DateUtils() {
    }

    public static String formateTime(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(d);
        return str;
    }

    public static String formateTime1(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(d);
        return str;
    }

    public static String formateTime2(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String str = sdf.format(d);
        return str;
    }

    public static String formateTime3(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(d);
        return str;
    }

    public static String formateOracleTime(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String str = sdf.format(d);
        return str;
    }

    public static void main(String[] args) {
        TimeIntervalVo timeIntervalVo = formateTime("18:05","12:00",new Date().getTime());
        System.out.println(formateTime(new Date(timeIntervalVo.getStartTime())));
        System.out.println(formateTime(new Date(timeIntervalVo.getEndTime())));
    }

    public static TimeIntervalVo formateTime(String startTime, String endTime, long resultTime) {

        Calendar c = Calendar.getInstance();

        c.setTimeInMillis(resultTime);

        Integer year = c.get(Calendar.YEAR);
        Integer month = c.get(Calendar.MONTH);
        Integer day = c.get(Calendar.DATE);
        Integer hours = c.get(Calendar.HOUR_OF_DAY);

        String[] startTimeArr = parseTimeBySeparator(startTime);
        String[] endTimeArr = parseTimeBySeparator(endTime);

        Integer startTimeHours = Integer.parseInt(startTimeArr[0]),
                startTimeMin=0,
                endTimeHours = Integer.parseInt(endTimeArr[0]),
                endTimeMin=0;
        if(startTimeArr.length > 1) {
            startTimeMin = Integer.parseInt(startTimeArr[1]);
        }
        if(endTimeArr.length > 1) {
            endTimeMin = Integer.parseInt(endTimeArr[1]);
        }

        c.set(year, month, day, startTimeHours, startTimeMin, 0);
        long beginTime = c.getTimeInMillis();

        c.set(year, month, day, endTimeHours, endTimeMin, 0);
        long overTime = c.getTimeInMillis();

        if (startTimeHours > endTimeHours) {
            if (hours <= endTimeHours) {
                c.set(year, month, day - 1, startTimeHours, startTimeMin, 0);
                beginTime = c.getTimeInMillis();
            } else if (hours >= startTimeHours) {
                c.set(year, month, day + 1, endTimeHours, endTimeMin, 0);
                overTime = c.getTimeInMillis();
            } else if (hours < startTimeHours && hours > endTimeHours) {
                c.set(year, month, day - 1, startTimeHours, startTimeMin, 0);
                beginTime = c.getTimeInMillis();
            }
        }

        TimeIntervalVo t = new TimeIntervalVo();

        t.setStartTime(beginTime);
        t.setEndTime(overTime);

        return t;
    }

    public static String[] parseTimeBySeparator(String time) {
        return time.split(Constant.TIME_SEPARATOR);
    }
}
