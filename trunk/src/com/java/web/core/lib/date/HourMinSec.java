/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.lib.date;

/**
 *
 * @author Vuk
 */
public class HourMinSec {
    private long hours;
    private long mins;
    private long secs;
    private long days;
    private long months;
    private long years;

    public HourMinSec(){
        
    }
    /**
     * @return the hours
     */
    public long getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(long hours) {
        this.hours = hours;
    }

    /**
     * @return the mins
     */
    public long getMins() {
        return mins;
    }

    /**
     * @param mins the mins to set
     */
    public void setMins(long mins) {
        this.mins = mins;
    }

    /**
     * @return the secs
     */
    public long getSecs() {
        return secs;
    }

    /**
     * @param secs the secs to set
     */
    public void setSecs(long secs) {
        this.secs = secs;
    }

    /**
     * @return the days
     */
    public long getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(long days) {
        this.days = days;
    }

    /**
     * @return the months
     */
    public long getMonths() {
        return months;
    }

    /**
     * @param months the months to set
     */
    public void setMonths(long months) {
        this.months = months;
    }

    /**
     * @return the years
     */
    public long getYears() {
        return years;
    }
}
