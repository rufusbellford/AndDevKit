package com.rc.devkit.shakemenu;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rc.devkit.reporter.Reporter;

public class ShakedMenu extends AlertDialog
{
    private LinearLayout layout;

    protected ShakedMenu(Context context)
    {
        super(context);
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        addTitle();
        addReportButton();
        this.setView(layout);
    }

    private void addTitle()
    {
        TextView textView = new TextView(getContext());
        textView.setText("Shaked Menu");
        textView.setTextSize(30);
        layout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
    }

    private void addReportButton()
    {
        TextView button = new TextView(getContext());
        button.setBackgroundColor(Color.RED);
        button.setText("Report!");
        button.setTextSize(30);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                report();
            }
        });

        layout.addView(button, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
    }

    private void report()
    {
        Reporter.history().showHistory();
        this.dismiss();
    }

    public static ShakedMenu newInstance(Context context)
    {
        ShakedMenu menu = new ShakedMenu(context);
        return menu;
    }



}
