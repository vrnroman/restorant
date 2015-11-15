package ru.vrn.velichkin.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Roman
 */
public class DateUtils {
    
    //Can't create an instance.
    private DateUtils() {}
    
    /**
     * Testing: is param date is in past?
     * @param date
     * @return 
     */
    public static final boolean isDateInPast(Date date) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, currentDate.getActualMinimum(Calendar.HOUR_OF_DAY));
        currentDate.set(Calendar.MINUTE, currentDate.getActualMinimum(Calendar.MINUTE));
        currentDate.set(Calendar.SECOND, currentDate.getActualMinimum(Calendar.SECOND));
        currentDate.set(Calendar.MILLISECOND, currentDate.getActualMinimum(Calendar.MILLISECOND));
        return currentDate.after(date);
    }
    
}
