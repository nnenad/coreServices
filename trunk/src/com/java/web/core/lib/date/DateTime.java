package com.java.web.core.lib.date;

import com.java.web.core.internationalization.MlManager;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.GregorianCalendar;

public class DateTime implements Comparable<DateTime> {

    private Calendar _cal = Calendar.getInstance();

    protected DateTime() {
    }

    protected DateTime(Date dt) {
        _cal.setTime(dt);
    }

    protected DateTime(Calendar c) {
        _cal = c;
    }

    public static DateTime now() {
        return new DateTime();
    }

    public static DateTime fromDate(Date dt) {
        return new DateTime(dt);
    }

    public static DateTime fromYMDString(String ymd) throws ParseException {
        Date dt = parseAnyFormat(ymd, new String[]{
                    "yyyy-MM-dd HH:mm:ss.SSS",
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm",
                    "yyyy-MM-dd",
                    "HH:mm:ss"
                });
        return new DateTime(dt);
    }

    public static DateTime fromDMYString(String ymd) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Date dt = format.parse(ymd);
        return new DateTime(dt);
    }

    public static DateTime parse(String value, String format) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        Date dt = fmt.parse(value);
        return new DateTime(dt);
    }

    public static Date parseAnyFormat(String txt, String[] formats) throws ParseException {
        Date result = null;
        ParseException ex = null;
        for (String fmt : formats) {
            boolean ok = false;
            SimpleDateFormat format = new SimpleDateFormat(fmt);
            try {
                result = format.parse(txt);
                ok = true;
            } catch (ParseException ex2) {
                ex = ex2;
                ok = false;
            }
            if (ok) {
                break;
            }
        }
        if (result == null && ex != null) {
            throw ex;
        }
        return result;
    }

    @Override
    public String toString() {
        String result = this.toString("yyyy-MM-dd HH:mm:ss.SSS");
        return result;
    }

    public String toString(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String result = formatter.format(_cal.getTime());
        return result;
    }

    public Date toDate() {
        return _cal.getTime();
    }

    public DateTime duplicate() {
        DateTime result = new DateTime();
        result.setTime(this.getTime());
        return result;
    }

    public void truncateTime() {
        _cal.set(Calendar.MILLISECOND, 0);
        _cal.set(Calendar.SECOND, 0);
        _cal.set(Calendar.MINUTE, 0);
        _cal.set(Calendar.HOUR_OF_DAY, 0);
    }

    public DateTime date() {
        DateTime result = this.duplicate();
        result.truncateTime();
        return result;
    }

    public TimeSpan time() {
        long value = this.getTime() - this.date().getTime();
        TimeSpan result = TimeSpan.fromMiliseconds(value);
        return result;
    }

    public long getTime() {
        long value = _cal.getTimeInMillis();
        return value;
    }

    public void setTime(long time) {
        _cal.setTimeInMillis(time);
    }

    public void add(TimeSpan ts) {
        long value = this.getTime() + ts.totalMiliseconds();
        this.setTime(value);
    }

    public void addDays(int amount) {
        _cal.add(Calendar.DAY_OF_YEAR, amount);
    }

    public void addHours(int amount) {
        _cal.add(Calendar.HOUR_OF_DAY, amount);
    }

    public void addMiliseconds(int amount) {
        _cal.add(Calendar.MILLISECOND, amount);
    }

    public void addMinutes(int amount) {
        _cal.add(Calendar.MINUTE, amount);
    }

    public void addMonths(int amount) {
        _cal.add(Calendar.MONTH, amount);
    }

    public void addSeconds(int amount) {
        _cal.add(Calendar.SECOND, amount);
    }

    public void addYears(int amount) {
        _cal.add(Calendar.YEAR, amount);
    }

    public TimeSpan subtract(DateTime dt) {
        long value = this.getTime() - dt.getTime();
        TimeSpan result = TimeSpan.fromMiliseconds(value);
        return result;
    }

    public void subtract(TimeSpan ts) {
        long value = this.getTime() + ts.totalMiliseconds();
        this.setTime(value);
    }

    public void setMilisecond(int ms) {
        _cal.set(Calendar.MILLISECOND, ms);
    }

    public void setSecond(int second) {
        _cal.set(Calendar.SECOND, second);
    }

    public void setMinute(int minute) {
        _cal.set(Calendar.MINUTE, minute);
    }

    public void setHour(int hour) {
        _cal.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setDay(int day) {
        _cal.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setMonth(int month) {
        _cal.set(Calendar.MONTH, month);
    }

    public void setYear(int year) {
        _cal.set(Calendar.YEAR, year);
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof DateTime)) {
            return false;
        }
        DateTime castOther = (DateTime) other;

        return _cal.getTime().equals(castOther._cal.getTime());
    }

    @Override
    public int hashCode() {
        int result = _cal.hashCode();
        return result;
    }

    public int compareTo(DateTime o) {
        if (o == null) {
            return 1;
        }
        return _cal.getTime().compareTo(o._cal.getTime());
    }

    public static String getLocalizedMonthName(int month) {
        String month1 = null;
        String[] months = new DateFormatSymbols(MlManager.getCurrentLocale()).getMonths();
        for (int i = 0; i < months.length; i++) {
            if(i == month)
            month1 = months[i];
        }
        return month1;
    }
    public static int getNoOfDays(int year, int month){
        Calendar cal = new GregorianCalendar(year,month,1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public static int getFirstDayStartPosition(int year, int month){
        Calendar cal = new GregorianCalendar(year,month,0);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        return weekday;
    }
    public static int getWeekofTheYearPosition(int year, int month,int day){
        Calendar cal = new GregorianCalendar(year,month,day);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int weekNo = cal.get(Calendar.WEEK_OF_YEAR);
        return weekNo;
    }
    public static int getDayOfTheWeek(int year, int month,int day){
        Calendar cal = new GregorianCalendar(year,month,day-1);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int dayNo = cal.get(Calendar.DAY_OF_WEEK);
        return dayNo;
    }
    public int getDayOfTheMonth(){
        return _cal.get(Calendar.DAY_OF_MONTH);
    }
    public int getMonth(){
        int month = _cal.get(Calendar.MONTH)+1;
        return month;
    }
    public int getYear(){
        return _cal.get(Calendar.YEAR);
    }
}
