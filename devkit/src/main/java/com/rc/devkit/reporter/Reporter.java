package com.rc.devkit.reporter;

import java.util.ArrayList;
import java.util.List;

public class Reporter implements Output
{
    private final int LOG_SIZE = 100;
    private List<ReportLog> logHistory = new ArrayList<ReportLog>(LOG_SIZE);
    private static Reporter instance = null;
    private ReportEvent reportEvent;

    private static synchronized Reporter instance()
    {
        if (instance == null)
        {
            instance = new Reporter();
            instance.reportEvent = new DefaultReportEvent(instance);
        }

        return instance;
    }

    public static ReportEvent report()
    {
        return Reporter.instance().reportEvent;
    }

    public static Output history()
    {
        return Reporter.instance();
    }

    @Override
    public void showHistory()
    {
        android.util.Log.e("Reporter", "HISTORY:START");
        for (ReportLog log : logHistory) {
            android.util.Log.e("Reporter", log.toString());
        }
        android.util.Log.e("Reporter", "HISTORY:END");
    }

    public void addLog(ReportLog entry)
    {
        if (logHistory.size() == LOG_SIZE) {
            removeOldestEntry();
        }

        logHistory.add(entry);
    }

    private void removeOldestEntry()
    {
        logHistory.remove(0);
    }
}
