package com.rc.devkit.reporter;

public interface ReportEvent
{
    public void clicked();
    public void invoked();
    public void succedded();
    public void done();
    public void progress(int current, int max);
    public void reportValue(String title, Object val);
}
