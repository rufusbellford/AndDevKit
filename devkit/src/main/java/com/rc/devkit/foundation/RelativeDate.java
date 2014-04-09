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
        long day = 1000 * 60 * 60 * 24;
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
    public Date daysFrom(int days)
    {
        // TODO: Fixed for year changing
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear + days);
        return calendar.getTime();
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
