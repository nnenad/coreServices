/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.lib.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;
import com.java.web.core.Constants;

/**
 *
 * @author Vuk
 */
public class DateUtil {

    public static String getCurrentFormatedDate(int negativeNoDaysBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, negativeNoDaysBefore);
        DateFormat df = new SimpleDateFormat(Constants.CURRENT_DATE_FORMAT);
        String formattedDate = df.format(cal.getTime());
        return formattedDate;
    }

    public static String getCurrentFormatedDateTime(int negativeNoDaysBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, negativeNoDaysBefore);
        DateFormat df = new SimpleDateFormat(Constants.CURRENT_DATETIME_FORMAT);
        String formattedDate = df.format(cal.getTime());
        return formattedDate;
    }

    public static String getCurrentFormatedTime() {
        DateFormat df = new SimpleDateFormat(Constants.CURRENT_TIME_FORMAT);
        String formattedDate = df.format(new Date());
        return formattedDate;
    }

    public static String getFormatedDate(Date date) {
         DateFormat df = new SimpleDateFormat(Constants.CURRENT_DATE_FORMAT);
        String formattedDate = df.format(date);
        return formattedDate;
    }

    public static String getFormatedDateTime(Date date) {
         DateFormat df = new SimpleDateFormat(Constants.CURRENT_DATETIME_FORMAT);
        String formattedDate = df.format(date);
        return formattedDate;
    }

    public static String getFormatedTime(Date date) {
        DateFormat df = new SimpleDateFormat(Constants.CURRENT_TIME_FORMAT);
        String formattedDate = df.format(date);
        return formattedDate;
    }

    public static Date parseCurrentFormatedTime(String formatedDateString) throws ParseException {
        java.util.Date utilDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.CURRENT_TIME_FORMAT);
        utilDate = formatter.parse(formatedDateString);
        return utilDate;
    }
    public static Date parseCurrentFormatedDateTime(String formatedDateString) throws ParseException {
        java.util.Date utilDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.CURRENT_DATETIME_FORMAT);
        utilDate = formatter.parse(formatedDateString);
        return utilDate;
    }

    public static Date parseCurrentFormatedDate(String formatedDateString) throws ParseException {
        java.util.Date utilDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.CURRENT_DATE_FORMAT);
        utilDate = formatter.parse(formatedDateString);
        return utilDate;
    }
    public static HourMinSec calculateDifference(Date first, Date second){
        long milsDiff = first.getTime() - second.getTime();
        long seconds = (milsDiff - (milsDiff % 1000))/1000;
        long diffSeconds = seconds % 60;
        long minutes = (seconds - diffSeconds)/60;
        long diffMinutes = minutes % 60;
        long hours = (minutes - diffMinutes)/60;
        HourMinSec result = new HourMinSec();
        result.setHours(hours);
        result.setMins(diffMinutes);
        result.setSecs(diffSeconds);
        return result;
    }
    public static HourMinSec calculateDifferenceMills(long mills){
        long milsDiff = mills;
        long seconds = (milsDiff - (milsDiff % 1000))/1000;
        long diffSeconds = seconds % 60;
        long minutes = (seconds - diffSeconds)/60;
        long diffMinutes = minutes % 60;
        long hours = (minutes - diffMinutes)/60;
        long diffHours = hours%24;
        long days = (hours - diffHours)/24;
        HourMinSec result = new HourMinSec();
        result.setHours(hours);
        result.setMins(diffMinutes);
        result.setSecs(diffSeconds);
        return result;
    }
    public static String concatenateElapsedTime(HourMinSec hourMinSec){
        String hours = String.valueOf(hourMinSec.getHours());
        String min = String.valueOf(hourMinSec.getMins());
        String sec = String.valueOf(hourMinSec.getSecs());
        if(hours.length() == 1){
            hours = "0"+hours;
        }
        if(min.length() == 1){
            min = "0"+min;
        }
        if(sec.length() == 1){
            sec = "0"+sec;
        }
        return hours+":"+min+":"+sec;
    }
    public static String concatenateElapsedTimeHM(HourMinSec hourMinSec){
        String hours = String.valueOf(hourMinSec.getHours());
        String min = String.valueOf(hourMinSec.getMins());
        if(hours.length() == 1){
            hours = "0"+hours;
        }
        if(min.length() == 1){
            min = "0"+min;
        }
        return hours+":"+min+":00";
    }
    public static Date addToDateTime(Date initial, Date timeInitial) throws Exception{
        int hours = 0;
        int mins = 0;
        int secs = 0;
        secs = timeInitial.getSeconds();
        mins = timeInitial.getMinutes();
        hours = timeInitial.getHours();
        Calendar cal = Calendar.getInstance();
        cal.setTime(initial);
        cal.add(Calendar.HOUR, hours);

        cal.add(Calendar.MINUTE, mins);

        cal.add(Calendar.SECOND, secs);

        DateFormat df = new SimpleDateFormat(Constants.CURRENT_DATETIME_FORMAT);
        String formattedDate = df.format(cal.getTime());
        Date utilDate = df.parse(formattedDate);
        return utilDate;
    }
    public static Date addTwoDates(Date first, Date second){
        long sum = first.getTime() + second.getTime();
        Date sumDate = new Date(sum);
        return sumDate;
    }
    public static Date reduceTimeByPrecentage(Date time, int precentage){
        long miliseconds = time.getTime();
        long result = Math.round((miliseconds*precentage)/100);
        Date reducedDate = new Date(result);
        return reducedDate;
    }
    public static Date cubtractTwoDates(Date first,Date second){
        long deduction = first.getTime() - second.getTime();
        Date deductionDate = new Date(deduction);
        return deductionDate;
    }
}
