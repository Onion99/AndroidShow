package com.onion.tutorial.time;


import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class LocalTimeSample {

    public static void main(String[] args) {
        // 获取一个时钟，该时钟使用最佳可用系统时钟返回当前时刻，并使用默认时区转换为日期和时间。
        Clock clock = Clock.systemDefaultZone();
        System.out.println("获取时钟的当前毫秒:"+clock.millis());//1654600712690
        System.out.println("获取时钟的时区:"+clock.getZone());//Asia/Shanghai
        System.out.println("获取时钟的日期:"+ Date.from(clock.instant()));//Tue Jun 07 19:21:24 CST 2022
        // 从一个ID中获取ZoneId的实例
        ZoneId zoneBerlin = ZoneId.of("Europe/Berlin");
        ZoneId zoneShanghai = ZoneId.of("Asia/Shanghai");
        System.out.println("获取时区的时间偏移量:"+zoneShanghai.getRules());//ZoneRules[currentStandardOffset=+01:00]
        System.out.println("获取时区的时间:"+ LocalTime.now(zoneShanghai));//19:28:31.705121200
        long hoursBetween = ChronoUnit.HOURS.between(LocalTime.now(zoneBerlin), LocalTime.now(zoneShanghai));
        long minutesBetween = ChronoUnit.MINUTES.between(LocalTime.now(zoneBerlin), LocalTime.now(zoneShanghai));
        long secondsBetween = ChronoUnit.SECONDS.between(LocalTime.now(zoneBerlin), LocalTime.now(zoneShanghai));
        System.out.println("获取时区间的时间差-小时:"+ hoursBetween);
        System.out.println("获取时区间的时间差-分钟:"+ minutesBetween);
        System.out.println("获取时区间的时间差-秒:"+ secondsBetween);
        LocalTime now = LocalTime.now();
        System.out.println("获取当前时间:"+now);
        LocalTime late = LocalTime.of(23, 19, 19);
        System.out.println("创建指定时间:"+late);

        DateTimeFormatter germanFormatter = DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMAN);

        LocalTime formatTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println("格式化指定时间:"+formatTime);
    }
}
