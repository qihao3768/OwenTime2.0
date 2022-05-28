package com.example.owentime.util

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class DateUtil {
    /**
     * 获得年份
     * @param date
     * @return
     */
    fun getYear(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.YEAR]
    }

    /**
     * 获得月份
     * @param date
     * @return
     */
    fun getMonth(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.MONTH] + 1
    }

    /**
     * 获得星期几
     * @param date
     * @return
     */
    fun getWeek(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.WEEK_OF_YEAR]
    }

    /**
     * 获得日期
     * @param date
     * @return
     */
    fun getDay(date: Date?): Int {
        val c = Calendar.getInstance()
        c.time = date
        return c[Calendar.DATE]
    }

    /**
     * 获得天数差
     * @param begin
     * @param end
     * @return
     */
    fun getDayDiff(begin: Date, end: Date): Long {
        var day: Long = 1
        if (end.time < begin.time) {
            day = -1
        } else if (end.time == begin.time) {
            day = 1
        } else {
            day += (end.time - begin.time) / (24 * 60 * 60 * 1000)
        }
        return day
    }

    companion object {
        private const val FORMAT = "yyyy-MM-dd HH:mm:ss"
        private val datetimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        private val timeFormat = SimpleDateFormat("HH:mm:ss")

        /**
         * 字符串转时间
         * @param str
         * @param format
         * @return
         */
        @JvmOverloads
        fun str2Date(str: String?, format: String? = null): Date? {
            var format = format
            if (str == null || str.length == 0) {
                return null
            }
            if (format == null || format.length == 0) {
                format = FORMAT
            }
            var date: Date? = null
            try {
                val sdf = SimpleDateFormat(format)
                date = sdf.parse(str)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return date
        }

        @JvmOverloads
        fun str2Calendar(str: String?, format: String? = null): Calendar? {
            val date = str2Date(str, format) ?: return null
            val c = Calendar.getInstance()
            c.time = date
            return c
        }

        @JvmOverloads
        fun date2Str(c: Calendar?, format: String? = null): String? {
            return if (c == null) {
                null
            } else date2Str(c.time, format)
        }

        @JvmOverloads
        fun date2Str(
            d: Date?,
            format: String? = null
        ): String? { // yyyy-MM-dd HH:mm:ss
            var format = format
            if (d == null) {
                return null
            }
            if (format == null || format.length == 0) {
                format = FORMAT
            }
            val sdf = SimpleDateFormat(format)
            return sdf.format(d)
        }

        val curDateStr: String
            get() {
                val c = Calendar.getInstance()
                c.time = Date()
                return (c[Calendar.YEAR].toString() + "-" + (c[Calendar.MONTH] + 1) + "-" + c[Calendar.DAY_OF_MONTH] + "-"
                        + c[Calendar.HOUR_OF_DAY] + ":" + c[Calendar.MINUTE] + ":" + c[Calendar.SECOND])
            }

        /**
         * 获得当前日期的字符串格式
         *
         * @param format
         * @return
         */
        fun getCurDateStr(format: String?): String? {
            val c = Calendar.getInstance()
            return date2Str(c, format)
        }

        /**
         * 格式到秒
         *
         * @param time
         * @return
         */
        fun getMillon(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time)
        }

        /**
         * 格式到天
         *
         * @param time
         * @return
         */
        fun getDay(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd").format(time)
        }

        /**
         * 格式到毫秒
         *
         * @param time
         * @return
         */
        fun getSMillon(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time)
        }

        /**
         * 字符串转换到时间格式
         *
         * @param dateStr
         * 需要转换的字符串
         * @param formatStr
         * 需要格式的目标字符串 举例 yyyy-MM-dd
         * @return Date 返回转换后的时间
         * @throws ParseException
         * 转换异常
         */
        fun StringToDate(dateStr: String?, formatStr: String?): Date? {
            val sdf: DateFormat = SimpleDateFormat(formatStr)
            var date: Date? = null
            try {
                date = sdf.parse(dateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }

        /**
         * 把字符串转化为时间格式
         *
         * @param timestamp
         * @return
         */
        fun getStandardTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("MM月dd日 HH:mm")
            val date = Date(timestamp * 1000)
            sdf.format(date)
            return sdf.format(date)
        }

        /**
         * 获得当前日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
         *
         * @return
         */
        fun currentDatetime(): String {
            return datetimeFormat.format(now())
        }

        /**
         * 格式化日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
         *
         * @return
         */
        fun formatDatetime(date: Date?): String {
            return datetimeFormat.format(date)
        }

        /**
         * 获得当前时间 时间格式HH:mm:ss
         *
         * @return
         */
        fun currentTime(): String {
            return timeFormat.format(now())
        }

        /**
         * 格式化时间 时间格式HH:mm:ss
         *
         * @return
         */
        fun formatTime(date: Date?): String {
            return timeFormat.format(date)
        }

        /**
         * 获得当前时间的``
         *
         *
         */
        fun now(): Date {
            return Date()
        }

        fun calendar(): Calendar {
            val cal = GregorianCalendar.getInstance(Locale.CHINESE)
            cal.firstDayOfWeek = Calendar.MONDAY
            return cal
        }

        /**
         * 获得当前时间的毫秒数
         *
         * 详见[System.currentTimeMillis]
         *
         * @return
         */
        fun millis(): Long {
            return System.currentTimeMillis()
        }

        /**
         *
         * 获得当前Chinese月份
         *
         * @return
         */
        fun month(): Int {
            return calendar()[Calendar.MONTH] + 1
        }

        /**
         * 获得月份中的第几天
         *
         * @return
         */
        fun dayOfMonth(): Int {
            return calendar()[Calendar.DAY_OF_MONTH]
        }

        /**
         * 今天是星期的第几天
         *
         * @return
         */
        fun dayOfWeek(): Int {
            return calendar()[Calendar.DAY_OF_WEEK]
        }

        /**
         * 今天是年中的第几天
         *
         * @return
         */
        fun dayOfYear(): Int {
            return calendar()[Calendar.DAY_OF_YEAR]
        }

        /**
         * 判断原日期是否在目标日期之前
         *
         * @param src
         * @param dst
         * @return
         */
        fun isBefore(src: Date, dst: Date?): Boolean {
            return src.before(dst)
        }

        /**
         * 判断原日期是否在目标日期之后
         *
         * @param src
         * @param dst
         * @return
         */
        fun isAfter(src: Date, dst: Date?): Boolean {
            return src.after(dst)
        }

        /**
         * 判断两日期是否相同
         *
         * @param date1
         * @param date2
         * @return
         */
        fun isEqual(date1: Date, date2: Date?): Boolean {
            return date1.compareTo(date2) == 0
        }

        /**
         * 判断某个日期是否在某个日期范围
         *
         * @param beginDate
         * 日期范围开始
         * @param endDate
         * 日期范围结束
         * @param src
         * 需要判断的日期
         * @return
         */
        fun between(beginDate: Date, endDate: Date, src: Date?): Boolean {
            return beginDate.before(src) && endDate.after(src)
        }

        /**
         * 获得当前月的最后一天
         *
         * HH:mm:ss为0，毫秒为999
         *
         * @return
         */
        fun lastDayOfMonth(): Date {
            val cal = calendar()
            cal[Calendar.DAY_OF_MONTH] = 0 // M月置零
            cal[Calendar.HOUR_OF_DAY] = 0 // H置零
            cal[Calendar.MINUTE] = 0 // m置零
            cal[Calendar.SECOND] = 0 // s置零
            cal[Calendar.MILLISECOND] = 0 // S置零
            cal[Calendar.MONTH] = cal[Calendar.MONTH] + 1 // 月份+1
            cal[Calendar.MILLISECOND] = -1 // 毫秒-1
            return cal.time
        }

        /**
         * 获得当前月的第一天
         *
         * HH:mm:ss SS为零
         *
         * @return
         */
        fun firstDayOfMonth(): Date {
            val cal = calendar()
            cal[Calendar.DAY_OF_MONTH] = 1 // M月置1
            cal[Calendar.HOUR_OF_DAY] = 0 // H置零
            cal[Calendar.MINUTE] = 0 // m置零
            cal[Calendar.SECOND] = 0 // s置零
            cal[Calendar.MILLISECOND] = 0 // S置零
            return cal.time
        }

        private fun weekDay(week: Int): Date {
            val cal = calendar()
            cal[Calendar.DAY_OF_WEEK] = week
            return cal.time
        }

        /**
         * 获得周五日期
         *
         * 注：日历工厂方法[.calendar]设置类每个星期的第一天为Monday，US等每星期第一天为sunday
         *
         * @return
         */
        fun friday(): Date {
            return weekDay(Calendar.FRIDAY)
        }

        /**
         * 获得周六日期
         *
         * 注：日历工厂方法[.calendar]设置类每个星期的第一天为Monday，US等每星期第一天为sunday
         *
         * @return
         */
        fun saturday(): Date {
            return weekDay(Calendar.SATURDAY)
        }

        /**
         * 获得周日日期 注：日历工厂方法[.calendar]设置类每个星期的第一天为Monday，US等每星期第一天为sunday
         *
         * @return
         */
        fun sunday(): Date {
            return weekDay(Calendar.SUNDAY)
        }

        /**
         * 将字符串日期时间转换成java.util.Date类型 日期时间格式yyyy-MM-dd HH:mm:ss
         *
         * @param datetime
         * @return
         */
        @Throws(ParseException::class)
        fun parseDatetime(datetime: String?): Date {
            return datetimeFormat.parse(datetime)
        }

        /**
         * 将字符串日期转换成java.util.Date类型 日期时间格式yyyy-MM-dd
         *
         * @param date
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun parseDate(date: String?): Date {
            return dateFormat.parse(date)
        }

        /**
         * 将字符串日期转换成java.util.Date类型 时间格式 HH:mm:ss
         *
         * @param time
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun parseTime(time: String?): Date {
            return timeFormat.parse(time)
        }

        /**
         * 根据自定义pattern将字符串日期转换成java.util.Date类型
         *
         * @param datetime
         * @param pattern
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun parseDatetime(datetime: String?, pattern: String?): Date {
            val format = datetimeFormat.clone() as SimpleDateFormat
            format.applyPattern(pattern)
            return format.parse(datetime)
        }




        /**
         * 比较时间大小
         * @param begin
         * @param end
         * @return
         */
        fun compareDate(begin: String?, end: String?): Int {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            try {
                val beginDate = df.parse(begin)
                val endDate = df.parse(end)
                return if (beginDate.time < endDate.time) {
                    1
                } else if (beginDate.time > endDate.time) {
                    -1
                } else {
                    0
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return 0
        }

        /**
         * 获得年份
         * @param
         * @return
         */
        fun startDate(offYear: Int): Calendar {
            val c = Calendar.getInstance()
            c[c[Calendar.YEAR] + offYear, 0] = 1
            return c
        }

        //    public static String date2TimeStamp(String date, String format) {
        //        try {
        //            SimpleDateFormat sdf = new SimpleDateFormat(format);
        //            return String.valueOf(sdf.parse(date).getTime() / 1000);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //        return "";
        //    }
        fun getTimeByDate(time: String?): Long {
//        Date date=str2Date(time,"HH:mm:ss");
            val format = SimpleDateFormat("HH:mm:ss")
            return try {
                format.timeZone = TimeZone.getTimeZone("GMT")
                val date1 = format.parse(time)
                //日期转时间戳（毫秒）
                date1.time
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }
    }
}