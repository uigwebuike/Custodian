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
}
