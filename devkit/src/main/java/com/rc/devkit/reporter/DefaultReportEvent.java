package com.rc.devkit.reporter;

import java.text.SimpleDateFormat;

public class DefaultReportEvent extends ReportEventCore implements ReportEvent
{
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public DefaultReportEvent(Reporter parent)
    {
        super(parent);
    }

    @Override
    protected void processReportLog(ReportLog reportLog)
    {
        reportLog.setSimpleDateFormat(simpleDateFormat);
    }

    @Override
    public void clicked()
    {
        addTextLog("clicked");
    }

    @Override
    public void invoked()
    {
        addTextLog("invoked");
    }

    @Override
    public void succedded()
    {
        addTextLog("succedded");
    }

    @Override
    public void done()
    {
        addTextLog("done");
    }

    @Override
    public void progress(int current, int max)
    {
        addProgress(current, max);
    }

    @Override
    public void reportValue(String title, Object val)
    {
        addTextLog(title + val.toString());
    }
}
