package com.java.web.core.lib.date;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSpan implements Comparable<TimeSpan> {

    private long _miliseconds = 0;

    protected TimeSpan() {
    }

    protected TimeSpan(long ms) {
        _miliseconds = ms;
    }

    public static TimeSpan zero() {
        return new TimeSpan();
    }

    public static TimeSpan fromDate(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        TimeSpan result = TimeSpan.zero();
        result.addHours( c.get(Calendar.HOUR_OF_DAY) );
        result.addMinutes( c.get(Calendar.MINUTE) );
        result.addSeconds( c.get(Calendar.SECOND) );
        result.addMiliseconds( c.get(Calendar.MILLISECOND) );
        return result;
    }

    public static TimeSpan fromMiliseconds(long ms) {
        return new TimeSpan(ms);
    }

    public static TimeSpan fromSeconds(long sec) {
        return new TimeSpan(sec * 1000);
    }
    public static TimeSpan fromMinutes(int min) {
        return new TimeSpan(min * 60 * 1000);
    }
    public static TimeSpan fromHours(int h) {
        return new TimeSpan(h * 3600 * 1000);
    }

    public static TimeSpan create(int days, int hours, int minutes, int seconds, int miliseconds) {
        long value = miliseconds
                + 1000 * seconds
                + 1000 * 60 * minutes
                + 1000 * 60 * 60 * hours
                + 1000 * 60 * 60 * 24 * days;
        return new TimeSpan(value);
    }

    public boolean isNegative() {
        return this.totalMiliseconds() < 0;
    }

    public boolean isPositive() {
        return this.totalMiliseconds() > 0;
    }

    public boolean isZero() {
        return this.totalMiliseconds() == 0;
    }


    public double totalDays() {
        double result = (double)_miliseconds / (double) (1000 * 60 * 60 * 24);
        return roundTwoDecimals(result);
    }

    public double totalHours() {
        double result = (double)_miliseconds / (double) (1000 * 60 * 60);
        return roundTwoDecimals(result);
    }

    public double totalMinutes() {
        double result = (double)_miliseconds / (double) (1000 * 60);
        return roundTwoDecimals(result);
    }

    public double totalSeconds() {
        double result = (double) _miliseconds / (double)1000;
        return roundTwoDecimals(result);
    }

    public long totalMiliseconds() {
        return _miliseconds;
    }



    public int miliseconds() {
        return (int)(_miliseconds % (long)1000);
    }
    public int seconds() {
        return ((int)(Math.floor(totalSeconds()))) % 60;
    }
    public int minutes() {
        return ((int)(Math.floor(totalMinutes()))) % 60;
    }
    public int hours() {
        return ((int)(Math.floor(totalHours())));
    }


    public Date toDate() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        c.set(Calendar.HOUR_OF_DAY, this.hours());
        c.set(Calendar.MINUTE, this.minutes());
        c.set(Calendar.SECOND, this.seconds());
        c.set(Calendar.MILLISECOND, this.miliseconds());
        return c.getTime();
    }

    @Override
    public String toString() {
        String result = String.format("%02d:%02d:%02d.%03d", hours(), minutes(), seconds(), miliseconds());
        return result;
    }


    public void add(TimeSpan ts) {
        _miliseconds += ts._miliseconds;
    }

    public void subtract(TimeSpan ts){
         _miliseconds -= ts._miliseconds;
    }

    public void addHours(int v) {
        _miliseconds += v * 3600000;
    }
    public void addMinutes(int v) {
        _miliseconds += v * 60000;
    }
    public void addSeconds(int v) {
        _miliseconds += v * 1000;
    }
    public void addMiliseconds(long miliseconds) {
        _miliseconds += miliseconds;
    }

    public int compareTo(TimeSpan o) {
        if (o == null) {
            return 1;
        }
        if (_miliseconds < o._miliseconds) {
            return -1;
        }
        if (_miliseconds > o._miliseconds) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof TimeSpan)) {
            return false;
        }
        TimeSpan castOther = (TimeSpan) other;

        return _miliseconds == castOther._miliseconds;
    }

    @Override
    public int hashCode() {
        return (int)_miliseconds;
    }
    public void reduceByPrecentage(int precentage){
        _miliseconds = Math.round(_miliseconds*precentage/100);
    }
    public double roundTwoDecimals(double d) {
        	DecimalFormat twoDForm = new DecimalFormat("#.#");
		return Double.valueOf(twoDForm.format(d));
}
}
