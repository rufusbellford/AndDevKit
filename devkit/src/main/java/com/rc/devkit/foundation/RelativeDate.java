package com.rc.devkit.foundation;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RelativeDate extends Date
{
    //================================================================================
    // Contructors
    //================================================================================
    public RelativeDate()
    {
        super();
    }

    public RelativeDate(long milliseconds)
    {
        super(milliseconds);
    }



    //================================================================================
    // Public Methods
    //================================================================================

    /**
     *
     * @return days from now in text, like: tommorrow, etc
     */
    public String daysFromNowText()
    {
        long day =  86400000; // 1000 * 60 * 60 * 24;
        Date currentZeroedDate = copyOnlyDate(Calendar.getInstance()).getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        Date selectedZeroedDate = copyOnlyDate(calendar).getTime();

        long timeDiff = selectedZeroedDate.getTime() - currentZeroedDate.getTime();

        if (timeDiff < 0 || timeDiff > 3 * day) {
            return null;
        }
        else
        {
            if (timeDiff == 0) {
                return "dzisiaj";
            }
            else if (timeDiff == 1 * day) {
                return "jutro";
            }
            else if (timeDiff == 2 * day) {
                return "pojutrze";
            }
            else {
                return "popojutrze";
            }
        }
    }

    /**
     * Warning: Can work wrong on year changing dates.
     *
     * @param days number of days from current date represented by this object.
     * @return Object of Date
     */
    public RelativeDate daysFrom(int days)
    {
        // TODO: Fixed for year changing
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear + days);
        return new RelativeDate(calendar.getTime().getTime());
    }

    public RelativeDate hoursFrom(int additionalHours)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this);
        int currentHour = calendar.get(Calendar.HOUR);
        calendar.set(Calendar.HOUR, currentHour + additionalHours);
        return new RelativeDate(calendar.getTime().getTime());
    }

    public RelativeDate minutesFrom(int additionalMinutes)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this);
        int currentMinute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, currentMinute + additionalMinutes);
        return new RelativeDate(calendar.getTime().getTime());
    }

    public static int daysDifference(Date firstDate, Date secondDate)
    {
        return daysDifference(firstDate.getTime(), secondDate.getTime());
    }

    public static int daysDifference(long firstDateTime, long secondDateTime)
    {
        long day =  86400000; // 1000 * 60 * 60 * 24;
        long diff = secondDateTime - firstDateTime;
        return (int)(diff / day);
    }

    //================================================================================
    // Private Methods
    //================================================================================
    private Calendar copyOnlyDate(Calendar calendar)
    {
        Calendar newCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return newCalendar;
    }
}
