package com.custodian.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Zanibal TraderWorkstation
 * Created by uigwebuike on 3/31/17.
 */
public class StringDateUtils {


    private static Date incrementDate(Date pDate, int pField, int pIncrement) {
        Calendar cal = java.util.Calendar.getInstance(TimeZone.getDefault());

        //Set the first date to be the beginning of the week
        Calendar calRoll = Calendar.getInstance();
        if (pDate != null) calRoll.setTime(pDate);

        calRoll.add(pField, pIncrement);
        return calRoll.getTime();
    }

    public static Date addYearsToDate(Date pDate, int pIncrement) {
        return incrementDate(pDate, Calendar.YEAR, pIncrement);
    }

    public static Date addMonthsToDate(Date pDate, int pIncrement) {
        return incrementDate(pDate, Calendar.MONTH, pIncrement);
    }

    public static Date addDaysToDate(Date pDate, int pIncrement) {
        return incrementDate(pDate, Calendar.DAY_OF_YEAR, pIncrement);
    }


    public static Date getTodaysDate() {

        //Set the first date to be the beginning of the week
        Calendar calRoll = Calendar.getInstance();

        calRoll.set(Calendar.HOUR, calRoll.getActualMinimum(Calendar.HOUR));
        calRoll.set(Calendar.AM_PM, Calendar.AM);
        calRoll.set(Calendar.MINUTE, calRoll.getActualMinimum(Calendar.MINUTE));
        calRoll.set(Calendar.SECOND, calRoll.getActualMinimum(Calendar.SECOND));
        calRoll.set(Calendar.MILLISECOND, calRoll.getActualMinimum(Calendar.MILLISECOND));

        return calRoll.getTime();
    }


}
