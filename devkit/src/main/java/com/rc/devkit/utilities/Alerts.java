package com.rc.devkit.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class Alerts
{
    private static AlertDialog.Builder builderWithListeners(Context context, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(true);

        if (positiveListener != null){
            builder.setPositiveButton(android.R.string.yes, positiveListener);
        }

        if (negativeListener != null)
        {
            builder.setNegativeButton(android.R.string.no, negativeListener);
        }

        return builder;
    }

    public static void showInAlert(Context context, String title, String content, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener)
    {
        AlertDialog.Builder builder = builderWithListeners(context, positiveListener, negativeListener);
        builder.setTitle(title).setMessage(content);
        builder.show();
    }

    public static void showSiteInAlert(Context context, String url, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener)
    {
        AlertDialog.Builder builder = builderWithListeners(context, positiveListener, negativeListener);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setPadding(10, 10, 10, 10);

        final ProgressBar progressBar = new ProgressBar(context);
        FrameLayout.LayoutParams progressBarParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBarParams.gravity = android.view.Gravity.CENTER;
        progressBar.setIndeterminate(true);
        frameLayout.addView(progressBar, progressBarParams);

        final WebView webView = new WebView(context);
        FrameLayout.LayoutParams webViewBarParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webViewBarParams.gravity = android.view.Gravity.CENTER;
        webView.setBackgroundResource(android.R.color.white);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

        webView.setVisibility(View.GONE);
        frameLayout.addView(webView, webViewBarParams);
        alertDialog.setContentView(frameLayout);
        webView.loadUrl(url);
    }
}
